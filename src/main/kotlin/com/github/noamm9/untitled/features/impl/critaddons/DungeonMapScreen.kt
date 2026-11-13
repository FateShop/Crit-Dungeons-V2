package com.github.noamm9.untitled.features.impl.critaddons

import net.minecraft.ChatFormatting
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.Component
import net.minecraft.util.FormattedCharSequence

internal class DungeonMapScreen : Screen(Component.literal("Dungeon Map")) {
    private val sizeHint = Component.literal("Increase the window size or lower GUI Scale to view the map.")
    private val unavailable = Component.literal("Room actions are unavailable in this version.")
    private val secondary = 0xffd4d4d8.toInt()
    private val primary = 0xfffafafa.toInt()
    private val surface = 0xff27272a.toInt()

    override fun isPauseScreen() = false
    override fun mouseClicked(event: MouseButtonEvent, doubleClick: Boolean) = true

    private fun wrap(text: Component, width: Int, size: Int): List<FormattedCharSequence> =
        font.split(text, (width * font.lineHeight / size).coerceAtLeast(1))

    private fun lines(ctx: GuiGraphicsExtractor, text: List<FormattedCharSequence>, x: Int, y: Int,
                      size: Int, spacing: Int, color: Int) {
        val pose = ctx.pose()
        text.forEachIndexed { i, line ->
            withMapStack({ pose.pushMatrix() }, { pose.popMatrix() }) {
                pose.translate(x.toFloat(), (y + i * spacing).toFloat())
                pose.scale(size.toFloat() / font.lineHeight)
                ctx.text(font, line, 0, 0, color, false)
            }
        }
    }

    private fun sizeCorrection(ctx: GuiGraphicsExtractor, x: Int, y: Int, width: Int, height: Int) {
        val fit = (12 downTo 1).firstOrNull {
            width > 0 && wrap(sizeHint, width, it).size * (it + 2) <= height
        } ?: return
        lines(ctx, wrap(sizeHint, width, fit), x, y, fit, fit + 2, primary)
    }

    override fun extractRenderState(ctx: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, partialTick: Float) {
        val p = MapPanel(width, height)
        val x = p.x + p.padding
        val hint = Component.empty()
            .append(Component.literal(DungeonMapPopup.popupKey.displayName()).withColor(0xa78bfa))
            .append(": Close map · ")
            .append(Component.literal("Esc: Stop automation and clear queued work").withColor(0xf87171))
        val footer = wrap(hint, p.innerWidth, 12) + wrap(unavailable, p.innerWidth, 12)
        val bodyHeight = p.contentHeight(footer.size)
        ctx.fill(0, 0, width, height, 0x59000000)
        ctx.fill(p.x, p.y, p.x + p.width, p.y + p.height, 0xff18181b.toInt())
        if (bodyHeight <= 0 || p.innerWidth <= 0) {
            // Keep the correction readable even when the usual header and footer cannot fit.
            sizeCorrection(ctx, x, p.y + p.padding, p.innerWidth, p.height - 2 * p.padding)
            return
        }
        ctx.fill(p.x, p.y, p.x + p.width, p.contentY - p.gap, surface)
        lines(ctx, listOf(title.copy().withStyle(ChatFormatting.BOLD).visualOrderText), x, p.y + p.padding,
            20, 24, primary)
        val footerY = p.contentY + bodyHeight + p.gap
        ctx.fill(p.x, footerY - p.gap, p.x + p.width, p.y + p.height, surface)
        lines(ctx, footer, x, footerY, 12, 18, secondary)
        when (val state = NoammMapAdapter.availability()) {
            MapAvailability.LIVE -> NoammMapAdapter.draw(ctx, x, p.contentY, p.innerWidth, bodyHeight)
            else -> {
                val outside = state == MapAvailability.OUTSIDE
                val heading = if (outside) "No dungeon map" else "No map information yet"
                val body = if (outside) "Enter a dungeon to view Noamm's available map information."
                    else "Noamm has not supplied map information for this run. The map updates as information becomes available."
                val message = Component.literal(body)
                val bodySize = if (p.compact) 12 else 16
                val titleLines = wrap(Component.literal(heading).withStyle(ChatFormatting.BOLD), p.innerWidth, 20)
                val bodyLines = wrap(message, p.innerWidth, bodySize)
                val messageHeight = titleLines.size * 24 + p.gap + bodyLines.size * bodySize * 3 / 2
                if (messageHeight > bodyHeight) {
                    sizeCorrection(ctx, x, p.contentY, p.innerWidth, bodyHeight)
                    return
                }
                val separation = if (messageHeight + 24 - p.gap <= bodyHeight) 24 else p.gap
                lines(ctx, titleLines, x, p.contentY, 20, 24, primary)
                lines(ctx, bodyLines, x, p.contentY + titleLines.size * 24 + separation,
                    bodySize, bodySize * 3 / 2, primary)
            }
        }
    }
}
