package com.github.noamm9.untitled.features.impl.critaddons

enum class PopupSurface { GAME, MAP, OTHER }
enum class PopupRequest { OPEN, CLOSE, NONE }

internal object MapContract {
    fun press(surface: PopupSurface, matchesBinding: Boolean): PopupRequest = PopupRequest.NONE

    fun drawLive(draw: (Boolean, Boolean) -> Pair<Float, Float>) = draw(false, false)
}
