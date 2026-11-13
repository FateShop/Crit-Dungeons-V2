package com.github.noamm9.untitled.crit.control

@ConsistentCopyVisibility
data class CancellationToken internal constructor(
    val ownerId: String, val taskId: String, val epoch: Long, val generation: Long
)
