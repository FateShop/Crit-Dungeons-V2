package com.github.noamm9.untitled.dev

import com.github.noamm9.untitled.crit.control.*

/** The live fixture and JVM traces both consume the production coordinator and guards. */
class FixtureSession(private val coordinator: SessionCoordinator,
    private val guarded: (ResourceLease, () -> Unit) -> Boolean) {
    companion object { const val OWNER = "crit-control-fixture" }
    var heartbeats = 0; private set
    var revalidations = 0; private set
    var lastActual: ActualState? = null; private set
    private var started = 0L
    private var active = false
    fun start(now: Long): Boolean {
        val epoch = coordinator.state.runEpoch ?: return false
        if (coordinator.state.activeTaskId != null || coordinator.state.stoppedRunEpoch == epoch) return false
        heartbeats = 0; revalidations = 0; lastActual = null
        started = now
        active = coordinator.enqueue(epoch, Probe("probe-A", 200)) && coordinator.enqueue(epoch, Probe("probe-B", 20))
        return active
    }
    fun reset() { coordinator.cancelOwner(OWNER); active = false }
    fun tick(now: Long) {
        if (!active) return
        if (now - started >= 120000) { reset(); return }
        val token = coordinator.currentToken() ?: return
        if (token.ownerId != OWNER) return
        val lease = coordinator.acquire(token, Resource.entries.toSet()) ?: return
        try {
            if (guarded(lease) { heartbeats++ }) {
                val limit = if (token.taskId == "probe-A") 200 else 220
                if (heartbeats >= limit) coordinator.complete(token)
            }
        } finally { coordinator.release(lease) }
    }
    private inner class Probe(override val taskId: String, val lifetime: Int): TaskContract {
        override val ownerId = OWNER
        override fun preserve() = Unit
        override fun cancel() = Unit
        override fun revalidate(actual: ActualState): Boolean {
            lastActual = actual; revalidations++; return true
        }
    }
}
