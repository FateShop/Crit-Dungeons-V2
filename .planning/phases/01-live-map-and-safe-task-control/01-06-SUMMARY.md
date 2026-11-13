---
phase: 01-live-map-and-safe-task-control
plan: "06"
subsystem: development-fixture
tags: [fabric, kotlin, testing, isolation]
requires: [01-04]
provides: [isolated production-backed control probes, live command guide, archive isolation gate]
affects: [01-05]
tech-stack:
  added: []
  patterns: [separate development mod source set, bounded memory effects]
key-files:
  created: [src/devControl/kotlin/com/github/noamm9/untitled/dev/CritControlFixture.kt, src/devControl/kotlin/com/github/noamm9/untitled/dev/FixtureSession.kt, src/devControl/resources/fabric.mod.json, docs/phase1-control-fixture.md]
  modified: [build.gradle.kts, src/controlTest/kotlin/crit/DevFixtureTest.kt]
key-decisions:
  - "Use isolated devControl mod plus development/property guards, with no fixture classes or metadata in the production archive."
requirements-completed: []
duration: 9min
completed: 2026-11-13
---

# Phase 1 Plan 6: Isolated control fixture Summary

An opt-in Fabric development mod now exercises the production coordinator through bounded guarded heartbeats and unrelated Noamm queue sentinel commands.

## Task Commits

| Task | Commits |
| --- | --- |
| Production-backed session and isolation | 742765c RED; d695351 GREEN |
| Fatal/rearm/sentinel scenarios and guide | f009acc |
| Review-driven production corrections | a2aeb3f; f4bb3b3 |

## Validation and Invocation

The complete JVM suite, target 26.2 build, devControl compilation and verifyControlFixtureIsolation all pass on Microsoft JDK 25.0.1. The isolation task reads the actual production Jar task's archive output and verifies no fixture classes/metadata and no ordinary run source/property inclusion. RED failed on missing FixtureSession before implementation. The fixture uses the real SessionCoordinator/TaskContract and the same guarded effect APIs as production; its heartbeat never performs gameplay actions.

Exact launch: `.\gradlew.bat :26.2:runControlFixture`. Commands are `/crit-control-fixture start`, `status`, `fatal`, `selection-off`, `selection-on`, `sentinel` and `reset`. The guide documents expected real input, GUI, death, terminal/new-run and coexistence observations. probe-A runs for 200 unpaused ticks, then probe-B for 20, bounded by 120 wall seconds. Every resume acknowledges actual pose/room state. Fixture selection remains ephemeral, explicit restart uses production selection APIs, and genuine next epochs can rearm enabled fixture preferences. Reset uses owner-scoped cancellation; shutdown removes its production tick callback and selection.

## Actual Launch Evidence and Limits

The development launch reached Minecraft 26.2 render/client initialization at 2026-11-13 04:14 local with both `Crit development control fixture registered` and `Crit Addons client controls initialized` in `versions/26.2/run/logs/latest.log`; sound/atlas initialization followed. No Mixin application exception appeared during startup and client ticks, including ActionUtils accessor use. This is limited startup evidence, not full applied-injection/live semantics proof. The launch used the development snapshot before the final task2 rearm refinement; final source passed compilation/isolation afterward.

Default offline development identity caused Mojang profile-certificate HTTP 401; authenticated Hypixel/dungeon scenarios were not performed. Root's UI capture attempts found the Minecraft window but failed FrameArrived/capture timeout, so no screenshot validation is claimed. No live death/revival, dungeon map, sentinel survival or all-floor result is claimed. The running development process is available to the coordinator; final candidate launch still belongs to 01-05.

## Deviations from Plan

- [Rule 1 - Bug] Independent review found that shared queue busy state revoked leases without invalidating resumed planning. Added EXTERNAL pause/fencing and actual-state revalidation; lease regression passes (a2aeb3f).
- [Rule 1 - Bug] Removed the redundant coroutine singleton RunEndedEvent listener: dispatch-time epoch cannot identify the source run. Client ticks/effects sample authoritative current ended state instead. Held the ActionUtils lock across the synchronous guarded effect to prevent queue-admission races (f4bb3b3).
- [Rule 2 - Missing critical functionality] Added SessionCoordinator.cancelOwner so fixture reset and wall-bound cleanup cancel only fixture tasks, preserving unrelated work (a2aeb3f).
- [Rule 3 - Blocking] Corrected 26.2 Fabric ClientCommands and lifecycle package names against official docs/exact dependency. An eager producer-backed fabric.addMods path prevented launch dependency calculation; replaced it with an explicit isolated fixture archive location. Launch then reached client startup.

## Known Stubs

No gameplay stubs. Probe preserve/cancel hooks intentionally have no side effects because all probe work is an in-memory heartbeat guarded per tick; real input/lifecycle facts are never synthesized by the live fixture. The pure sentinel assertion models ownership only and does not establish actual Noamm queue execution.

## Self-Check: PASSED

Confirmed all development files, scenario guide, test dispatcher, task commits, target archive and isolation task output. No tracked file deletions. Generated run/build outputs remain ignored. Requirement completion awaits 01-05 acceptance/release.
