package crit

import com.github.noamm9.untitled.crit.control.*

internal class ProbeTask(override val taskId: String) : TaskContract {
    override val ownerId = "fixture"
    var preserved = 0
    var cancelled = 0
    var valid = true
    override fun preserve() { preserved++ }
    override fun cancel() { cancelled++ }
    override fun revalidate(actual: ActualState) = valid
}

internal object ObservedState : ActualState

fun runSessionControl() {
    val session = SessionCoordinator()
    session.select("rush")
    check(session.observeRun(1))
    val active = ProbeTask("whole run")
    val pending = ProbeTask("manual")
    check(session.enqueue(1, active) && session.enqueue(1, pending))
    val original = session.currentToken()!!
    for (reason in PauseReason.entries) session.pause(1, reason, true)
    check(active.preserved == 1 && session.state.pendingTaskIds == listOf("manual"))
    for (reason in PauseReason.entries.reversed()) {
        check(!session.revalidate(ObservedState))
        session.pause(1, reason, false)
    }
    active.valid = false
    check(!session.revalidate(ObservedState) && session.currentToken() == null)
    active.valid = true
    check(session.revalidate(ObservedState) && session.currentToken() != original)
    session.emergencyStop(1)
    check(active.cancelled == 1 && pending.cancelled == 1 && session.state.selection == "rush")
    session.observeRun(1)
    session.pause(1, PauseReason.GUI, false)
    check(!session.enqueue(1, active))
    session.select(null)
    session.select("rush")
    check(session.enqueue(1, active))
    session.fatalStop(1)
    check(!session.enqueue(1, active))
    session.terminate(1)
    check(!session.observeRun(1))
    check(session.observeRun(2) && session.enqueue(2, pending))
    session.terminate(1)
    session.emergencyStop(1)
    check(session.state.activeTaskId == "manual" && !session.complete(original))
    check(session.complete(session.currentToken()!!) && session.state.activeTaskId == null)
}
