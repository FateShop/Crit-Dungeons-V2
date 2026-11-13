package com.github.noamm9.untitled.dev

import com.github.noamm9.NoammAddons
import com.github.noamm9.untitled.crit.control.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.minecraft.network.chat.Component

object CritControlFixture: ClientModInitializer {
    private val adapter = ClientControlAdapter
    private val fixture = FixtureSession(adapter.coordinator) { lease, action -> adapter.effect(lease, Resource.MOVEMENT, action) }
    private val onTick: () -> Unit = { fixture.tick(System.currentTimeMillis()) }
    override fun onInitializeClient() {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment || !java.lang.Boolean.getBoolean("crit.controlFixture")) return
        adapter.addTickListener(onTick)
        ClientLifecycleEvents.CLIENT_STOPPING.register { fixture.reset(); adapter.removeTickListener(onTick) }
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(literal("crit-control-fixture")
                .then(literal("start").executes { context ->
                    val started = adapter.lifecycle.mapAllowed && fixture.start(System.currentTimeMillis())
                    context.source.sendFeedback(Component.literal("fixture start=$started ${status()}")); 1
                })
                .then(literal("status").executes { context -> context.source.sendFeedback(Component.literal(status())); 1 }))
        }
        NoammAddons.logger.info("Crit development control fixture registered; production input/lifecycle remain authoritative")
    }
    private fun status(): String {
        val s = adapter.coordinator.state
        return "epoch=${s.runEpoch} generation=${s.executionGeneration} active=${s.activeTaskId} pending=${s.pendingTaskIds} pauses=${s.pauseReasons} revalidation=${s.requiresRevalidation} heartbeats=${fixture.heartbeats} acknowledged=${fixture.revalidations} actual=${fixture.lastActual}"
    }
}
