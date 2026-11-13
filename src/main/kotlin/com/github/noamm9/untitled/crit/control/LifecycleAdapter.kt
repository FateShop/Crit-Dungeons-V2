package com.github.noamm9.untitled.crit.control

/** Client-thread observed boundaries; terminal state cannot be reopened by a repeated location. */
class LifecycleAdapter(private val coordinator: SessionCoordinator) {
    private var nextEpoch = 0L
    private var outside = true
    private var terminal = false
    private var start: Long? = null
    val terminated: Boolean get() = terminal
    val mapAllowed: Boolean get() = !terminal && !outside && coordinator.state.runEpoch != null
    fun observe(connected: Boolean, dungeon: Boolean, boss: Boolean, ended: Boolean, startId: Long?) {
        if (!connected || !dungeon) {
            coordinator.state.runEpoch?.let(coordinator::terminate)
            outside = true
            terminal = false
            start = null
            return
        }
        val freshStart = startId != null && start != null && startId != start
        if (startId != null) start = startId
        if (boss || ended) {
            terminal = true
            outside = false
            coordinator.state.runEpoch?.let(coordinator::terminate)
            return
        }
        if (outside || freshStart) {
            terminal = false
            outside = false
            coordinator.observeRun(++nextEpoch)
        }
    }
    fun terminate() {
        terminal = true
        coordinator.state.runEpoch?.let(coordinator::terminate)
    }
}

data class ExternalActionState(val queued: Boolean, val running: Boolean, val blocked: Boolean) {
    val busy: Boolean get() = queued || running || blocked
}
