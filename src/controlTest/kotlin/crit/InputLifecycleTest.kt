package crit

import com.github.noamm9.untitled.crit.control.ManualInputState

fun runInputLifecycle() {
    val input = ManualInputState()
    check(!input.paused(0))
    check(input.key(65, 1))
    check(input.paused(100))
    input.key(65, 0)
    check(!input.paused(100))
    input.motion(5)
    check(input.paused(6))
    check(!input.paused(7))
    check(input.key(256, 1))
    check(!input.key(256, 2))
    input.focus(false)
    check(input.paused(100))
    input.focus(true)
    check(!input.paused(100))
    input.button(0, 1)
    check(input.paused(100))
    input.button(0, 0)
    check(!input.paused(100))
}
