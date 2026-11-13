package com.github.noamm9.untitled.crit.control

import net.minecraft.client.Minecraft
import com.github.noamm9.utils.location.LocationUtils
import com.github.noamm9.utils.dungeons.DungeonListener
import com.github.noamm9.ui.notification.NotificationManager
import com.github.noamm9.untitled.mixins.ActionUtilsAccessor
import com.github.noamm9.event.EventBus.register
import com.github.noamm9.event.impl.DungeonEvent
import com.github.noamm9.utils.dungeons.map.utils.ScanUtils
import com.github.noamm9.untitled.features.impl.critaddons.DungeonMapScreen

/** Raw observations are marshalled; no native input callback touches world or networking. */
object ClientControlAdapter {
    val coordinator = SessionCoordinator()
    val lifecycle = LifecycleAdapter(coordinator)
    private val tickListeners = mutableSetOf<() -> Unit>()
    private val input = ManualInputState()
    private var tick = 0L
    @Volatile private var publishedEpoch: Long? = null
    fun initialize() {
        register<DungeonEvent.RunEndedEvent> {
            val epoch = publishedEpoch
            observe { if (epoch != null && coordinator.state.runEpoch == epoch) lifecycle.terminate() }
        }
    }
    private fun observe(action: () -> Unit) {
        val mc = Minecraft.getInstance()
        if (mc.isSameThread) action() else mc.execute(action)
    }
    @JvmStatic fun key(code: Int, action: Int) = observe {
        val pressed = input.key(code, action)
        if (code == 256 && pressed) coordinator.state.runEpoch?.let(coordinator::emergencyStop)
        updateManual()
    }
    @JvmStatic fun button(code: Int, action: Int) = observe { input.button(code, action); updateManual() }
    @JvmStatic fun motion() = observe { input.motion(tick); updateManual() }
    @JvmStatic fun focus(active: Boolean) = observe { input.focus(active); updateManual() }
    private fun updateManual() { coordinator.state.runEpoch?.let { coordinator.pause(it, PauseReason.MANUAL, input.paused(tick)) } }
    fun addTickListener(listener: () -> Unit) { tickListeners.add(listener) }
    fun removeTickListener(listener: () -> Unit) { tickListeners.remove(listener) }
    fun select(selection: String?) { coordinator.select(selection) }
    @JvmStatic fun worldChanging() {
        lifecycle.observe(false, false, false, false, null)
        publishedEpoch = null
        closeMap()
    }
    private fun closeMap() {
        val mc = Minecraft.getInstance()
        if (mc.gui.screen() is DungeonMapScreen) mc.gui.setScreen(null)
    }
    fun fatal(reason: String, correction: String) {
        coordinator.state.runEpoch?.let(coordinator::fatalStop)
        NotificationManager.push("Crit Addons stopped", "$reason. $correction. Current work was cleared.", 5000)
    }
    private fun refreshFacts() {
        val mc = Minecraft.getInstance()
        lifecycle.observe(mc.level != null && mc.connection != null, LocationUtils.inDungeon,
            LocationUtils.inBoss, DungeonListener.dungeonEnded, DungeonListener.dungeonStartTime?.real)
        publishedEpoch = coordinator.state.runEpoch
        if (lifecycle.terminated) closeMap()
        input.focus(mc.isWindowActive)
        updateManual()
        coordinator.state.runEpoch?.let {
            coordinator.pause(it, PauseReason.GUI, mc.gui.screen() != null)
            coordinator.pause(it, PauseReason.DEATH, mc.player?.isDeadOrDying != false || DungeonListener.thePlayer?.isDead == true)
        }
    }
    fun externalBusy(): Boolean = synchronized(ActionUtilsAccessor.critLock()) {
        ExternalActionState(ActionUtilsAccessor.critQueue().isNotEmpty(), ActionUtilsAccessor.critRunning(), ActionUtilsAccessor.critBlocked()).busy
    }
    /** Every side effect refreshes actual terminal/pause facts and the supported Noamm queue boundary. */
    fun effect(lease: ResourceLease, resource: Resource, perform: () -> Unit): Boolean {
        check(Minecraft.getInstance().isSameThread)
        refreshFacts()
        coordinator.externalBusy(if (externalBusy()) Resource.entries.toSet() else emptySet())
        return coordinator.effect(lease, resource, perform)
    }
    @JvmStatic fun tick() {
        tick++
        refreshFacts()
        coordinator.externalBusy(if (externalBusy()) Resource.entries.toSet() else emptySet())
        if (coordinator.state.requiresRevalidation && coordinator.state.pauseReasons.isEmpty()) captureActual()?.let(coordinator::revalidate)
        tickListeners.toList().forEach { it() }
    }
    fun captureActual(): ClientActualState? {
        val mc = Minecraft.getInstance()
        val p = mc.player ?: return null
        return ClientActualState(p.x, p.y, p.z, p.yRot, p.xRot, mc.level?.dimension()?.identifier().toString(),
            LocationUtils.dungeonFloor, ScanUtils.currentRoom?.let { "${it.name}@${it.centerPos}/rotation=${it.rotation}" }, coordinator.state.runEpoch ?: return null)
    }
}

data class ClientActualState(val x: Double, val y: Double, val z: Double, val yaw: Float, val pitch: Float,
    val dimension: String, val floor: String?, val room: String?, val epoch: Long): ActualState
