package com.github.noamm9.untitled

import com.github.noamm9.NoammAddons
import net.fabricmc.api.ClientModInitializer
import com.github.noamm9.untitled.crit.control.ClientControlAdapter

object Untitled: ClientModInitializer {
    override fun onInitializeClient() {
        ClientControlAdapter.initialize()
        NoammAddons.logger.info("Crit Addons client controls initialized")
    }
}
