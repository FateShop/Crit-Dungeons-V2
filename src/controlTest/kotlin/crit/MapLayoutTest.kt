package crit

import com.github.noamm9.untitled.features.impl.critaddons.MapFit
import com.github.noamm9.untitled.features.impl.critaddons.MapPanel
import com.github.noamm9.untitled.features.impl.critaddons.withMapStack
import kotlin.math.abs

fun runMapLayout() {
    for ((width, height) in listOf(1920 to 1080, 960 to 540, 640 to 360, 427 to 240, 320 to 180, 160 to 90)) {
        val panel = MapPanel(width, height)
        check(panel.width % 4 == 0 && panel.height % 4 == 0)
        check(panel.width <= 360 && panel.width <= width * .75)
        check(panel.height <= 360 && panel.height <= height * .75)
        check(abs(panel.x - (width - panel.width) / 2f) <= .5f)
        check(abs(panel.y - (height - panel.height) / 2f) <= .5f)
        check(panel.x > 0 && panel.y > 0)
        val bodyHeight = panel.contentHeight(3)
        if (bodyHeight <= 0) continue // Production uses the size correction and native keyboard exits.
        for (bounds in listOf(128f to 128f, 128f to 140f, 200f to 100f)) {
            val fit = MapFit.inside(panel.x + panel.padding, panel.contentY, panel.innerWidth, bodyHeight, bounds)
            val usedWidth = bounds.first * fit.scale
            val usedHeight = bounds.second * fit.scale
            check(usedWidth <= panel.innerWidth + .001f && usedHeight <= bodyHeight + .001f)
            check(abs((fit.x - panel.x - panel.padding) * 2 + usedWidth - panel.innerWidth) < .001f)
            check(abs((fit.y - panel.contentY) * 2 + usedHeight - bodyHeight) < .001f)
        }
    }
    check(MapPanel(960, 540).width == 360)
    check(MapPanel(160, 90).contentHeight(3) <= 0)
    val events = mutableListOf<String>()
    val failure = runCatching {
        withMapStack({ events += "resolution+" }, { events += "resolution-" }) {
            withMapStack({ events += "pose+" }, { events += "pose-" }) { error("renderer failure") }
        }
    }
    check(failure.isFailure)
    check(events == listOf("resolution+", "pose+", "pose-", "resolution-"))
    check(runCatching { MapFit.inside(0, 0, 100, 100, Float.NaN to 128f) }.isFailure)
}
