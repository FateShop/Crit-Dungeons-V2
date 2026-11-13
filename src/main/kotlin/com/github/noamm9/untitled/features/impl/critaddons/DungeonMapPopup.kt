package com.github.noamm9.untitled.features.impl.critaddons

import com.github.noamm9.config.types.KeybindSetting
import com.github.noamm9.features.Feature
import com.github.noamm9.event.impl.KeyboardEvent
import com.github.noamm9.event.impl.MouseClickEvent
import com.mojang.blaze3d.platform.InputConstants

object DungeonMapPopup : Feature("View Noamm's current dungeon map.", toggled = true) {
    internal val popupKey by KeybindSetting("Popup Key", InputConstants.KEY_M)

    override fun init() {
        register<KeyboardEvent.KeyPressed> {
            val key = event.keyEvent.key
            if (key != InputConstants.KEY_ESCAPE && toggleFrom(key, false, event.action)) event.cancel()
        }
        register<MouseClickEvent> {
            if (toggleFrom(event.button, true, event.action)) event.cancel()
        }
    }

    private fun toggleFrom(code: Int, mouse: Boolean, action: Int): Boolean {
        if (action != InputConstants.PRESS || mc.player == null) return false
        val surface = when (mc.gui.screen()) {
            null -> PopupSurface.GAME
            is DungeonMapScreen -> PopupSurface.MAP
            else -> PopupSurface.OTHER
        }
        when (MapContract.press(surface, popupKey.matches(code, mouse))) {
            PopupRequest.OPEN -> if (mapReadyToOpen()) mc.gui.setScreen(DungeonMapScreen())
            PopupRequest.CLOSE -> mc.gui.setScreen(null)
            PopupRequest.NONE -> return false
        }
        return true
    }

    override fun onDisable() {
        super.onDisable()
        if (mc.gui.screen() is DungeonMapScreen) mc.gui.setScreen(null)
    }
}
