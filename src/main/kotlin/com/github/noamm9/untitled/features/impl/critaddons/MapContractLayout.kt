package com.github.noamm9.untitled.features.impl.critaddons

internal class MapPanel(viewWidth: Int, viewHeight: Int) {
    val width = minOf(360, viewWidth * 3 / 4) / 4 * 4
    val height = minOf(360, viewHeight * 3 / 4) / 4 * 4
    val x = (viewWidth - width) / 2
    val y = (viewHeight - height) / 2
    val compact = width < 300 || height < 300
    val padding = if (compact) 8 else 16
    val gap = if (compact) 4 else 8
    val innerWidth = (width - 2 * padding).coerceAtLeast(0)
    val contentY = y + padding + 24 + gap
    fun contentHeight(footerLines: Int) =
        height - 2 * padding - 24 - 2 * gap - footerLines * 18
}

/** Uniformly fits the dependency's complete returned rectangle; room geometry stays Noamm-owned. */
internal data class MapFit(val x: Float, val y: Float, val scale: Float) {
    companion object {
        fun inside(x: Int, y: Int, width: Int, height: Int, bounds: Pair<Float, Float>): MapFit {
            require(width > 0 && height > 0)
            val (mapWidth, mapHeight) = bounds
            require(mapWidth.isFinite() && mapHeight.isFinite() && mapWidth > 0 && mapHeight > 0)
            val scale = minOf(width / mapWidth, height / mapHeight)
            return MapFit(x + (width - mapWidth * scale) / 2f, y + (height - mapHeight * scale) / 2f, scale)
        }
    }
}

internal inline fun <R> withMapStack(push: () -> Unit, pop: () -> Unit, body: () -> R): R {
    push()
    try {
        return body()
    } finally {
        pop()
    }
}
