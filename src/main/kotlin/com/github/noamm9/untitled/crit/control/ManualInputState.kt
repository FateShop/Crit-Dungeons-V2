package com.github.noamm9.untitled.crit.control

/** Only native callback observations enter here; effective game key states are never sampled. */
class ManualInputState {
    private val keys = mutableSetOf<Int>()
    private val buttons = mutableSetOf<Int>()
    private var focused = true
    private var motionTick = Long.MIN_VALUE
    fun key(key: Int, action: Int): Boolean = update(keys, key, action)
    fun button(button: Int, action: Int): Boolean = update(buttons, button, action)
    private fun update(held: MutableSet<Int>, code: Int, action: Int): Boolean = when (action) {
        0 -> { held.remove(code); false }
        1 -> held.add(code)
        else -> false
    }
    fun motion(tick: Long) { motionTick = tick }
    fun focus(active: Boolean) {
        if (focused != active) { keys.clear(); buttons.clear(); motionTick = Long.MIN_VALUE }
        focused = active
    }
    fun paused(tick: Long): Boolean = !focused || keys.isNotEmpty() || buttons.isNotEmpty() ||
        (motionTick != Long.MIN_VALUE && tick - motionTick < 2)
}
