package com.github.noamm9.untitled.features.impl.critaddons

import com.github.noamm9.features.impl.dungeon.map.MapConfig
import com.github.noamm9.features.impl.dungeon.map.MapRenderer
import com.github.noamm9.ui.utils.Resolution
import net.minecraft.client.gui.GuiGraphicsExtractor

internal object NoammMapAdapter {
    fun draw(ctx: GuiGraphicsExtractor, x: Int, y: Int, width: Int, height: Int) {
        if (width <= 0 || height <= 0) return
        // The pinned renderer returns 140 even with extraInfo=false when its background is extended.
        val mapHeight = if (MapConfig.mapExtraInfo.value) 140f else 128f
        val scale = minOf(width / 128f, height / mapHeight)
        Resolution.push(ctx)
        try {
            val pose = ctx.pose()
            pose.pushMatrix()
            try {
                pose.scale(1f / Resolution.scale)
                pose.translate(x + (width - 128f * scale) / 2f, y + (height - mapHeight * scale) / 2f)
                pose.scale(scale)
                MapContract.drawLive { example, extra -> MapRenderer.draw(ctx, example, extra) }
            } finally {
                pose.popMatrix()
            }
        } finally {
            Resolution.pop(ctx)
        }
    }
}
