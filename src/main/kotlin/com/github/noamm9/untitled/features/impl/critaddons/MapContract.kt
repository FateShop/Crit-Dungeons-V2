package com.github.noamm9.untitled.features.impl.critaddons

enum class PopupSurface { GAME, MAP, OTHER }
enum class PopupRequest { OPEN, CLOSE, NONE }
internal enum class MapAvailability { OUTSIDE, EMPTY, LIVE }

internal object MapContract {
    fun prerequisiteMessage(feature: Boolean, map: Boolean, cheater: Boolean): String? {
        if (!feature) return "Enable Dungeon Map in Noamm, then open the map again."
        val missing = listOfNotNull("Map Enabled".takeUnless { map }, "Cheater Map".takeUnless { cheater })
        return if (missing.isEmpty()) null
        else "Enable ${missing.joinToString(" and ")} in Noamm's Dungeon Map settings, then open the map again."
    }

    fun availability(activeDungeon: Boolean, knownTile: Boolean) = when {
        !activeDungeon -> MapAvailability.OUTSIDE
        knownTile -> MapAvailability.LIVE
        else -> MapAvailability.EMPTY
    }
    fun press(surface: PopupSurface, matchesBinding: Boolean): PopupRequest {
        if (!matchesBinding) return PopupRequest.NONE
        return when (surface) {
            PopupSurface.GAME -> PopupRequest.OPEN
            PopupSurface.MAP -> PopupRequest.CLOSE
            PopupSurface.OTHER -> PopupRequest.NONE
        }
    }

    fun drawLive(draw: (Boolean, Boolean) -> Pair<Float, Float>) = draw(false, false)
}
