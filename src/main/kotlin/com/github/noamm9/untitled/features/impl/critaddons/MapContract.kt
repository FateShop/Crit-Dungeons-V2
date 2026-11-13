package com.github.noamm9.untitled.features.impl.critaddons

enum class PopupSurface { GAME, MAP, OTHER }
enum class PopupRequest { OPEN, CLOSE, NONE }

internal object MapContract {
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
