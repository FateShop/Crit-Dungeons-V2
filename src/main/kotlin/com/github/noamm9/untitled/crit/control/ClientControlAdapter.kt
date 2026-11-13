package com.github.noamm9.untitled.crit.control

import net.minecraft.client.Minecraft

/** Raw observations are marshalled; no native input callback touches world or networking. */
object ClientControlAdapter {
    val coordinator = SessionCoordinator()
    private val input = ManualInputState()
    private var tick = 0L
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
    @JvmStatic fun tick() { tick++; updateManual() }
}
