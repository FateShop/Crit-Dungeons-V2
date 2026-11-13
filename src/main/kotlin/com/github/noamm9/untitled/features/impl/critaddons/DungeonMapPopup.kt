package com.github.noamm9.untitled.features.impl.critaddons

import com.github.noamm9.config.types.KeybindSetting
import com.github.noamm9.features.Feature
import com.mojang.blaze3d.platform.InputConstants

object DungeonMapPopup : Feature("View Noamm's current dungeon map.", toggled = true) {
    internal val popupKey by KeybindSetting("Popup Key", InputConstants.KEY_M)
}
