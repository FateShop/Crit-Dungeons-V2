package com.github.noamm9.untitled.dev

import com.github.noamm9.NoammAddons
import com.github.noamm9.untitled.crit.control.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.minecraft.network.chat.Component
import com.github.noamm9.utils.ActionUtils
import java.util.concurrent.atomic.AtomicInteger

object CritControlFixture: ClientModInitializer {
    private val adapter = ClientControlAdapter
    private val fixture = FixtureSession(adapter.coordinator) { lease, action -> adapter.effect(lease, Resource.MOVEMENT, action) }
    private var enabled = false
    private var startedEpoch: Long? = null
    private val onTick: () -> Unit = {
        val epoch = adapter.coordinator.state.runEpoch
        if (enabled && epoch != null && epoch != startedEpoch && adapter.lifecycle.mapAllowed) start()
        fixture.tick(System.currentTimeMillis())
    }
    private val sentinel = AtomicInteger()
    override fun onInitializeClient() {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment || !java.lang.Boolean.getBoolean("crit.controlFixture")) return
        adapter.addTickListener(onTick)
        ClientLifecycleEvents.CLIENT_STOPPING.register {
            fixture.reset(); adapter.removeTickListener(onTick)
            enabled = false; startedEpoch = null
            if (adapter.coordinator.state.selection == "fixture") adapter.select(null)
        }
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(literal("crit-control-fixture")
                .then(literal("start").executes { context ->
                    val started = start()
                    context.source.sendFeedback(Component.literal("fixture start=$started ${status()}")); 1
                })
                .then(literal("status").executes { context -> context.source.sendFeedback(Component.literal(status())); 1 })
                .then(literal("fatal").executes {
                    if (adapter.coordinator.currentToken()?.ownerId == FixtureSession.OWNER || adapter.coordinator.state.activeTaskId?.startsWith("probe-") == true)
                        adapter.fatal("Development fixture fatal probe", "Use selection-off then selection-on to explicitly rearm")
                    1
                })
                .then(literal("selection-off").executes { enabled = false; fixture.reset(); adapter.select(null); 1 })
                .then(literal("selection-on").executes {
                    adapter.select("fixture")
                    enabled = true
                    start()
                    1
                })
                .then(literal("reset").executes { enabled = false; fixture.reset(); 1 })
                .then(literal("sentinel").executes {
                    ActionUtils.queue(priority = 0, blockInput = false) {
                        ActionUtils.waitTicks(10) {
                            val count = sentinel.incrementAndGet()
                            NoammAddons.logger.info("Crit unrelated Noamm sentinel completed: $count")
                        }
                    }
                    1
                }))
        }
        NoammAddons.logger.info("Crit development control fixture registered; production input/lifecycle remain authoritative")
    }
    private fun start(): Boolean {
        val state = adapter.coordinator.state
        if (!adapter.lifecycle.mapAllowed || state.runEpoch == null || state.stoppedRunEpoch == state.runEpoch || state.activeTaskId != null) return false
        if (state.selection != null && state.selection != "fixture") return false
        adapter.select("fixture")
        val result = fixture.start(System.currentTimeMillis())
        if (result) { enabled = true; startedEpoch = state.runEpoch }
        return result
    }
    private fun status(): String {
        val s = adapter.coordinator.state
        val value = "epoch=${s.runEpoch} generation=${s.executionGeneration} active=${s.activeTaskId} pending=${s.pendingTaskIds} pauses=${s.pauseReasons} revalidation=${s.requiresRevalidation} heartbeats=${fixture.heartbeats} acknowledged=${fixture.revalidations} sentinel=${sentinel.get()} actual=${fixture.lastActual}"
        NoammAddons.logger.info(value)
        return value
    }
}
