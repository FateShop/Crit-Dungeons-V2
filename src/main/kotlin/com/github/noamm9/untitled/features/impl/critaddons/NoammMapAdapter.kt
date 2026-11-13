package com.github.noamm9.untitled.features.impl.critaddons

import com.github.noamm9.features.impl.dungeon.map.MapConfig
import com.github.noamm9.features.impl.dungeon.map.MapRenderer
import com.github.noamm9.ui.utils.Resolution
import com.github.noamm9.utils.dungeons.map.core.Unknown
import com.github.noamm9.utils.dungeons.map.handlers.DungeonScanner
import com.github.noamm9.utils.location.LocationUtils
import net.minecraft.client.gui.GuiGraphicsExtractor

internal object NoammMapAdapter {
    // Seed from the exact pinned 1.3.5 contract, then retain actual returned bounds per mode.
    // The renderer draws while measuring, so a separate measuring draw would duplicate effects.
    private val bounds = mutableMapOf(false to (128f to 128f), true to (128f to 140f))
    fun availability(): MapAvailability {
        val active = LocationUtils.inDungeon && !LocationUtils.inBoss &&
            com.github.noamm9.untitled.crit.control.ClientControlAdapter.lifecycle.mapAllowed
        return MapContract.availability(active, active && DungeonScanner.dungeonList.any { it !is Unknown })
    }

    fun draw(ctx: GuiGraphicsExtractor, x: Int, y: Int, width: Int, height: Int) {
        if (width <= 0 || height <= 0) return
        val extra = MapConfig.mapExtraInfo.value
        val fit = MapFit.inside(x, y, width, height, bounds.getValue(extra))
        withMapStack({ Resolution.push(ctx) }, { Resolution.pop(ctx) }) {
            val pose = ctx.pose()
            withMapStack({ pose.pushMatrix() }, { pose.popMatrix() }) {
                pose.scale(1f / Resolution.scale)
                pose.translate(fit.x, fit.y)
                pose.scale(fit.scale)
                bounds[extra] = MapContract.drawLive { example, extraInfo -> MapRenderer.draw(ctx, example, extraInfo) }
            }
        }
    }
}
