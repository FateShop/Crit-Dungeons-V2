package com.github.noamm9.untitled.features.impl.critaddons

import com.github.noamm9.features.impl.dungeon.map.DungeonMap
import com.github.noamm9.features.impl.dungeon.map.MapConfig
import com.github.noamm9.ui.notification.NotificationManager

internal fun mapReadyToOpen(): Boolean {
    val missing = MapContract.prerequisiteMessage(
        DungeonMap.enabled, MapConfig.mapEnabled.value, MapConfig.dungeonMapCheater.value
    ) ?: return true
    NotificationManager.push("Dungeon Map Popup", missing)
    return false
}
