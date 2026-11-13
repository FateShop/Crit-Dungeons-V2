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
}
