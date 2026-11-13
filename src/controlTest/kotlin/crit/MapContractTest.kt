package crit

import com.github.noamm9.untitled.features.impl.critaddons.MapContract
import com.github.noamm9.untitled.features.impl.critaddons.PopupSurface
import com.github.noamm9.untitled.features.impl.critaddons.PopupRequest

fun runMapContract(suite: String) {
    val savedBinding = mutableMapOf("Popup Key" to 77)
    fun matches(code: Int) = savedBinding["Popup Key"] == code
    check(MapContract.press(PopupSurface.GAME, matches(77)) == PopupRequest.OPEN)
    savedBinding["Popup Key"] = 80
    check(MapContract.press(PopupSurface.GAME, matches(77)) == PopupRequest.NONE)
    check(MapContract.press(PopupSurface.OTHER, matches(80)) == PopupRequest.NONE)
    if (suite == "map-settings") return
    val request = MapContract.press(PopupSurface.GAME, matches(80))
    check(request == PopupRequest.OPEN)
    var calls = 0
    MapContract.drawLive { example, extraInfo ->
        check(!example && !extraInfo)
        calls++
        128f to 140f
    }
    check(calls == 1)
    check(MapContract.press(PopupSurface.MAP, matches(80)) == PopupRequest.CLOSE)
}
