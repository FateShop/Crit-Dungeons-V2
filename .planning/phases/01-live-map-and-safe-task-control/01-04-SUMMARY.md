---
phase: 01-live-map-and-safe-task-control
plan: "04"
subsystem: client-control
tags: [kotlin, fabric, mixin, lifecycle]
requires: [01-02, 01-03]
provides: [physical input observers, production lifecycle binding, guarded effects, terminal map guard]
affects: [01-06, 01-05]
tech-stack:
  added: []
  patterns: [native input provenance, epoch-fenced marshalling, read-only shared queue inspection]
key-files:
  created: [ManualInputState.kt, ClientControlAdapter.kt, LifecycleAdapter.kt, ActionUtilsAccessor.java, MixinCritKeyboard.java, MixinCritMouse.java, MixinCritWindow.java]
  modified: [Untitled.kt, MixinMinecraft.java, untitled.mixins.json, NoammMapAdapter.kt]
key-decisions:
  - "Run termination latches independently of Noamm inDungeon; world boundaries and actual changed start timestamps permit a fresh epoch."
requirements-completed: []
duration: 15min
completed: 2026-11-13
---

# Phase 1 Plan 4: Production input and lifecycle Summary

Native physical input and authoritative Noamm dungeon facts now fence Crit sessions and guarded effects without blocking vanilla input or resetting shared actions.

## Task Commits

| Task | RED | GREEN |
| --- | --- | --- |
| Physical input | 7701b65 | 4290947 |
| Lifecycle and guarded effects | 1eae2f6 | 83eda0c |

## Validation

JDK 25.0.1 ran input, client-adapters and the complete isolated control suite; target 26.2 Kotlin/Java compilation and build passed. git diff --check passed. Exact mapped Minecraft descriptors and installed Noamm private-field/getter contracts were inspected with javap. Pinned DungeonListener source confirms coroutine run-end emission, ghost/death facts and world reset. Detailed ABI evidence is in docs/noamm-reuse-evidence.md.

Input observers track callback-origin keys/buttons and two-tick pointer idle, with focus loss clearing stale held controls. Esc cancellation precedes normal screen processing through higher-priority non-cancelling observers. GUI/manual/death pause reasons compose through the production coordinator and resume uses actual pose, floor, dimension and Noamm current-room identity/transform. Settings are never mutated. Boss/end/exit/disconnect invalidate work; only the Crit popup is dismissed. Map availability excludes terminal runs, while Esc/fatal session stops do not disable viewing.

Read-only ActionUtils accessors expose actionQueue, running, lock and isBlocked; synchronized inspection yields before every guarded Crit effect. No shared reset/removal exists. Arbitrary direct Noamm writers need later consumer-specific audits.

## Deviations from Plan

- [Rule 2 - Missing critical functionality] Added MixinCritWindow for the exact Window.onFocus callback, because tick-only focus polling could miss loss/recovery and retain stale pressed controls. Registered and compiled with the other observers.
- [Rule 2 - Missing critical functionality] Added the shared terminal permission to NoammMapAdapter to prevent stale tiles after run end while inDungeon remains true; coordinated with the map owner.
- [Rule 3 - Blocking] Expanded isolated source inclusion and test dispatcher for the new pure adapters. The first target compile identified the 26.2 GUI API as mc.gui.screen(), corrected before the successful build.

## Live Limits and Remaining Verification

No game was launched, no JAR installed, and no release published by this plan. Compilation does not establish applied-mixin success, actual raw callback behavior, live ghost/revival semantics or queue sentinel coexistence. The concrete isolated fixture follows in 01-06; target-client acceptance/release remains 01-05. Requirement completion stays pending that evidence. Later Blood GUI/deadline, impossible timing, visibility and post-camp gates remain unresolved.

## Known Stubs

None in this plan's production adapters. Later gameplay consumers are intentionally outside Phase 1; no movement/combat/secret behavior is advertised.

## Self-Check: PASSED

Confirmed created production/test files, registered mixin entries, generated target JAR and all four task commits. No tracked file deletions.
