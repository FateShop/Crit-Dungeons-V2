package crit

import com.github.noamm9.untitled.crit.control.*

fun runClientAdapters() {
    val coordinator = SessionCoordinator()
    val lifecycle = LifecycleAdapter(coordinator)
    lifecycle.observe(true, true, false, false, 1L)
    val epoch = coordinator.state.runEpoch!!
    lifecycle.observe(true, true, false, false, 1L)
    check(coordinator.state.runEpoch == epoch)
    lifecycle.observe(true, true, true, false, 1L)
    check(coordinator.state.runEpoch == null)
    lifecycle.observe(true, true, false, false, 1L)
    check(coordinator.state.runEpoch == null)
    lifecycle.observe(true, true, false, false, 2L)
    check(coordinator.state.runEpoch!! > epoch)
    lifecycle.observe(true, true, false, true, 2L)
    check(coordinator.state.runEpoch == null)
    lifecycle.observe(false, false, false, false, null)
    lifecycle.observe(true, true, false, false, 3L)
    check(coordinator.state.runEpoch!! > epoch)
    coordinator.emergencyStop(coordinator.state.runEpoch!!)
    lifecycle.observe(true, true, false, false, 3L)
    check(coordinator.state.stoppedRunEpoch == coordinator.state.runEpoch)
    check(!ExternalActionState(false, false, false).busy)
    check(ExternalActionState(true, false, false).busy)
    check(ExternalActionState(false, true, false).busy)
    check(ExternalActionState(false, false, true).busy)
}
