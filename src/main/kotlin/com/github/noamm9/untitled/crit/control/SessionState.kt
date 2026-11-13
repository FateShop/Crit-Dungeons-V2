package com.github.noamm9.untitled.crit.control

enum class PauseReason { GUI, MANUAL, DEATH, EXTERNAL }

data class SessionState(
    val runEpoch: Long? = null,
    val executionGeneration: Long = 0,
    val selection: String? = null,
    val stoppedRunEpoch: Long? = null,
    val pauseReasons: Set<PauseReason> = emptySet(),
    val requiresRevalidation: Boolean = false,
    val activeTaskId: String? = null,
    val pendingTaskIds: List<String> = emptyList()
)
