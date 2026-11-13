package com.github.noamm9.untitled.crit.control

enum class Resource { MOVEMENT, AIM, HOTBAR, GUI, COMBAT }

class ResourceLease internal constructor(val token: CancellationToken, resources: Set<Resource>) {
    internal val resources: Set<Resource> = resources.toSet()
}

/** Client-thread table. Each successful grant has a distinct identity even on reacquisition. */
class ResourceLeases {
    private val owners = mutableMapOf<Resource, ResourceLease>()
    private var external = emptySet<Resource>()

    fun acquire(token: CancellationToken, resources: Set<Resource>): ResourceLease? {
        if (resources.isEmpty() || resources.any { it in owners || it in external }) return null
        return ResourceLease(token, resources).also { lease -> resources.forEach { owners[it] = lease } }
    }

    fun owns(lease: ResourceLease, resource: Resource) = owners[resource] === lease

    fun release(lease: ResourceLease): Boolean {
        if (!lease.resources.all { owns(lease, it) }) return false
        lease.resources.forEach(owners::remove)
        return true
    }

    internal fun externalBusy(resources: Set<Resource>) {
        external = resources.toSet()
        resources.mapNotNull(owners::get).distinct().forEach(::release)
    }

    internal fun revoke() = owners.clear()
}
