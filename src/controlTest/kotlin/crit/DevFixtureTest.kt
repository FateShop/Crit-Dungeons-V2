package crit

import com.github.noamm9.untitled.crit.control.*
import com.github.noamm9.untitled.dev.FixtureSession

fun runDevFixture() {
    val coordinator = SessionCoordinator()
    coordinator.observeRun(1)
    val fixture = FixtureSession(coordinator, { lease, action -> coordinator.effect(lease, Resource.MOVEMENT, action) })
    check(fixture.start(0))
    check(coordinator.state.activeTaskId == "probe-A" && coordinator.state.pendingTaskIds == listOf("probe-B"))
    fixture.tick(1)
    check(fixture.heartbeats == 1)
    coordinator.pause(1, PauseReason.GUI, true)
    fixture.tick(2)
    check(fixture.heartbeats == 1 && coordinator.state.pendingTaskIds.size == 1)
    coordinator.pause(1, PauseReason.GUI, false)
    fixture.tick(3)
    check(fixture.heartbeats == 1)
    check(coordinator.revalidate(ObservedState))
    fixture.tick(4)
    check(fixture.heartbeats == 2 && fixture.revalidations == 1)
    coordinator.fatalStop(1)
    check(!fixture.start(5))
    coordinator.select(null)
    coordinator.select("fixture")
    check(fixture.start(6))
    repeat(220) { fixture.tick(7L + it) }
    check(coordinator.state.activeTaskId == null && fixture.heartbeats == 220)
    check(fixture.start(1000))
    fixture.tick(121001)
    check(coordinator.state.activeTaskId == null)
    check(fixture.start(122000))
    val old = coordinator.currentToken()!!
    val oldLease = coordinator.acquire(old, setOf(Resource.MOVEMENT))!!
    coordinator.externalBusy(Resource.entries.toSet())
    fixture.tick(122001)
    check(fixture.heartbeats == 0)
    var sentinel = 0
    coordinator.emergencyStop(1)
    sentinel++ // Unrelated queue ownership remains outside Crit cancellation.
    check(sentinel == 1 && !coordinator.effect(oldLease, Resource.MOVEMENT) { error("stale effect") })
    coordinator.terminate(1)
    coordinator.observeRun(2)
    coordinator.externalBusy(emptySet())
    check(fixture.start(123000))
    coordinator.pause(2, PauseReason.DEATH, true)
    fixture.tick(123001)
    check(fixture.heartbeats == 0)
    coordinator.pause(2, PauseReason.DEATH, false)
    check(coordinator.revalidate(ObservedState))
    fixture.tick(123002)
    check(fixture.heartbeats == 1)
    coordinator.terminate(2)
    check(coordinator.state.activeTaskId == null && coordinator.state.pendingTaskIds.isEmpty())
}
