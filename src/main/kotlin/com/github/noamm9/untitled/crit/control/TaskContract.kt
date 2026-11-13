package com.github.noamm9.untitled.crit.control

/** Consumers supply a fresh snapshot of the actual client state when resuming. */
interface ActualState

/** A whole automatic run can occupy one FIFO slot until it explicitly completes.
 * Deadline/GUI conflicts, impossible timing, visibility and post-camp policy are later-phase decisions.
 */
interface TaskContract {
    val taskId: String
    val ownerId: String
    val requiresLoadout: Boolean get() = false
    fun preserve()
    fun cancel()
    fun revalidate(actual: ActualState): Boolean
}
