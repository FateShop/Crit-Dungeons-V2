package crit

import com.github.noamm9.untitled.features.impl.critaddons.MapContract
import com.github.noamm9.untitled.features.impl.critaddons.MapAvailability
import com.github.noamm9.untitled.features.impl.critaddons.PopupSurface
import com.github.noamm9.untitled.features.impl.critaddons.PopupRequest

fun runMapContract(suite: String) {
    for (mask in 0..7) {
        val message = MapContract.prerequisiteMessage(mask and 1 != 0, mask and 2 != 0, mask and 4 != 0)
        when {
            mask == 7 -> check(message == null)
            mask and 1 == 0 -> check(message == "Enable Dungeon Map in Noamm, then open the map again.")
            else -> {
                check(message!!.contains("Map Enabled") == (mask and 2 == 0))
                check(message.contains("Cheater Map") == (mask and 4 == 0))
            }
        }
    }
    check(MapContract.availability(false, true) == MapAvailability.OUTSIDE)
    check(MapContract.availability(true, false) == MapAvailability.EMPTY)
    check(MapContract.availability(true, true) == MapAvailability.LIVE)
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
