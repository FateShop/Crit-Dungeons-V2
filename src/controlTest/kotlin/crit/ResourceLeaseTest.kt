package crit

import com.github.noamm9.untitled.crit.control.*

fun runResourceLeases() {
    val session = SessionCoordinator()
    session.observeRun(1)
    session.enqueue(1, ProbeTask("a"))
    val token = session.currentToken()!!
    val movement = setOf(Resource.MOVEMENT)
    val pair = movement + Resource.AIM
    val leases = ResourceLeases()
    val first = leases.acquire(token, movement)!!
    check(leases.acquire(token, pair) == null)
    val aim = leases.acquire(token, setOf(Resource.AIM))!!
    check(leases.release(aim) && !leases.release(aim) && leases.release(first))
    val lease = session.acquire(token, pair)!!
    var effects = 0
    check(!session.effect(lease, Resource.COMBAT) { effects++ })
    check(session.effect(lease, Resource.MOVEMENT) { effects++ })
    session.externalBusy(setOf(Resource.AIM))
    check(!session.effect(lease, Resource.AIM) { effects++ })
    check(session.acquire(token, pair) == null)
    session.externalBusy(emptySet())
    val replacement = session.acquire(token, pair)!!
    check(!session.release(lease))
    session.pause(1, PauseReason.GUI, true)
    check(!session.effect(replacement, Resource.MOVEMENT) { effects++ })
    check(!session.closeForRequiredLoadout(token) { effects++ })
    session.terminate(1)
    session.observeRun(2)
    check(!session.effect(replacement, Resource.MOVEMENT) { effects++ })
    check(effects == 1 && leases.acquire(token, pair) != null)
}
