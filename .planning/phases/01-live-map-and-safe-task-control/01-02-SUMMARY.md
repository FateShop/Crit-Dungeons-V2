---
phase: 01-live-map-and-safe-task-control
plan: "02"
subsystem: session-control
requires: ["01-01"]
provides: [SessionCoordinator, TaskContract, CancellationToken, ResourceLeases]
affects: ["01-04", "01-06"]
---

# Phase 1 plan 2 execution

The client-thread SessionCoordinator owns the active objective and manual FIFO. Epoch and execution generation fence delayed effects. Independent GUI, manual and death pauses preserve progress and require accepted actual-state revalidation. Stop latches preserve configuration and prevent passive same-run restart. Five resource types support atomic grants, scoped release and yielding to externally busy resources.

| Task | RED | GREEN |
| --- | --- | --- |
| Session transitions | 26fcd94 | 409e62e |
| Resource leases | 0e791d6 | 8edee3b |

## Self-Check: PASSED

JDK 25.0.1 ran the complete 26.2 JVM contract suite and compiled production Kotlin. Map, session and resource assertions passed. Live client binding and coexistence remain assigned to later plans.
