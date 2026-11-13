package com.github.noamm9.untitled.crit.control

/** Mutations and task hooks run on the client thread. Workers return results with their original token. */
class SessionCoordinator {
    private val tasks = ArrayDeque<TaskContract>()
    private var latestEpoch = Long.MIN_VALUE
    var state = SessionState()
        private set

    fun observeRun(epoch: Long): Boolean {
        if (state.runEpoch == epoch) return true
        if (epoch <= latestEpoch) return false
        clearWork()
        latestEpoch = epoch
        state = SessionState(runEpoch = epoch, executionGeneration = state.executionGeneration, selection = state.selection)
        return true
    }

    fun select(selection: String?) {
        if (selection == state.selection) return
        clearWork()
        state = state.copy(selection = selection, stoppedRunEpoch = null)
    }

    fun enqueue(epoch: Long, task: TaskContract): Boolean {
        if (state.runEpoch != epoch || state.stoppedRunEpoch == epoch || tasks.any { it.taskId == task.taskId }) return false
        tasks.addLast(task)
        publishTasks()
        if (tasks.size == 1 && state.pauseReasons.isNotEmpty()) state = state.copy(requiresRevalidation = true)
        return true
    }

    fun emergencyStop(epoch: Long) = stop(epoch)
    fun fatalStop(epoch: Long) = stop(epoch)

    private fun stop(epoch: Long) {
        if (state.runEpoch != epoch || state.stoppedRunEpoch == epoch) return
        state = state.copy(stoppedRunEpoch = epoch)
        clearWork()
    }

    fun terminate(epoch: Long) {
        if (state.runEpoch != epoch) return
        state = state.copy(runEpoch = null, stoppedRunEpoch = null, pauseReasons = emptySet())
        clearWork()
    }

    fun pause(epoch: Long, reason: PauseReason, enabled: Boolean) {
        if (state.runEpoch != epoch || (reason in state.pauseReasons) == enabled) return
        val first = enabled && state.pauseReasons.isEmpty()
        state = state.copy(pauseReasons = if (enabled) state.pauseReasons + reason else state.pauseReasons - reason)
        if (first) {
            fence()
            state = state.copy(requiresRevalidation = tasks.isNotEmpty())
            tasks.firstOrNull()?.preserve()
        }
    }

    fun revalidate(actual: ActualState): Boolean {
        if (state.pauseReasons.isNotEmpty() || !state.requiresRevalidation) return false
        val generation = state.executionGeneration
        if (tasks.firstOrNull()?.revalidate(actual) != true || state.executionGeneration != generation) return false
        state = state.copy(requiresRevalidation = false)
        return true
    }

    fun currentToken(): CancellationToken? {
        val task = tasks.firstOrNull() ?: return null
        val epoch = state.runEpoch ?: return null
        if (state.stoppedRunEpoch == epoch || state.pauseReasons.isNotEmpty() || state.requiresRevalidation) return null
        return CancellationToken(task.ownerId, task.taskId, epoch, state.executionGeneration)
    }

    fun complete(token: CancellationToken): Boolean {
        if (currentToken() != token) return false
        fence()
        tasks.removeFirst()
        publishTasks()
        return true
    }

    private fun fence() {
        state = state.copy(executionGeneration = state.executionGeneration + 1)
    }

    private fun clearWork() {
        fence()
        val cancelled = tasks.toList()
        tasks.clear()
        publishTasks()
        state = state.copy(requiresRevalidation = false)
        cancelled.forEach { it.cancel() }
    }

    private fun publishTasks() {
        state = state.copy(activeTaskId = tasks.firstOrNull()?.taskId, pendingTaskIds = tasks.drop(1).map { it.taskId })
    }
}
