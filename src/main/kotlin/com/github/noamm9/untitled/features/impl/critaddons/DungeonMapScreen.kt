package com.github.noamm9.untitled.features.impl.critaddons

import com.github.noamm9.utils.location.LocationUtils
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

internal class DungeonMapScreen : Screen(Component.literal("Dungeon Map")) {
    override fun isPauseScreen() = false

    override fun extractRenderState(ctx: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, partialTick: Float) {
        val w = minOf(360, width * 3 / 4) / 4 * 4
        val h = minOf(360, height * 3 / 4) / 4 * 4
        val x = (width - w) / 2
        val y = (height - h) / 2
        ctx.fill(0, 0, width, height, 0x59000000)
        ctx.fill(x, y, x + w, y + h, 0xff18181b.toInt())
        ctx.text(font, title, x + 16, y + 16, -1)
        if (LocationUtils.inDungeon && !LocationUtils.inBoss) {
            NoammMapAdapter.draw(ctx, x + 16, y + 36, w - 32, h - 88)
        } else {
            ctx.text(font, "No dungeon map", x + 16, y + 36, -1)
        }
        val key = DungeonMapPopup.popupKey.displayName()
        ctx.text(font, "$key: Close map · Esc: Stop automation and clear queued work", x + 16, y + h - 40, -1)
        ctx.text(font, "Room actions are unavailable in this version.", x + 16, y + h - 24, -1)
    }
}
