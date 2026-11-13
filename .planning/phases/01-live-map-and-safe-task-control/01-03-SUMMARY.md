---
phase: 01-live-map-and-safe-task-control
plan: "03"
subsystem: map
tags: [kotlin, fabric, noamm, native-gui]
requires:
  - phase: 01-01
    provides: live map popup and isolated map harness
provides:
  - named Noamm prerequisite explanations and truthful map availability
  - centered bounded popup with wrapped copy and uniform native map fit
  - resize and render-stack invariant tests
affects: [01-04, 01-05, 01-06]
tech-stack:
  added: []
  patterns: [pure layout boundary, balanced rendering stacks, measured dependency bounds]
key-files:
  created:
    - src/main/kotlin/com/github/noamm9/untitled/features/impl/critaddons/MapPrerequisites.kt
    - src/main/kotlin/com/github/noamm9/untitled/features/impl/critaddons/MapContractLayout.kt
    - src/controlTest/kotlin/crit/MapLayoutTest.kt
  modified:
    - src/main/kotlin/com/github/noamm9/untitled/features/impl/critaddons/DungeonMapPopup.kt
    - src/main/kotlin/com/github/noamm9/untitled/features/impl/critaddons/DungeonMapScreen.kt
    - src/main/kotlin/com/github/noamm9/untitled/features/impl/critaddons/NoammMapAdapter.kt
    - src/main/kotlin/com/github/noamm9/untitled/features/impl/critaddons/MapContract.kt
    - src/controlTest/kotlin/crit/MapContractTest.kt
    - src/controlTest/kotlin/crit/ControlTestMain.kt
    - docs/implementation-ownership.md
key-decisions:
  - Seed map bounds from exact pinned renderer contracts and retain actual draw return dimensions per extra-info mode without a duplicate measuring draw.
patterns-established:
  - MapPanel and MapFit are the production layout calculations exercised by the isolated harness.
requirements-completed: [CORE-02, CORE-03, CORE-04, MAP-01, MAP-02]
duration: 10min continuation
completed: 2026-11-13
---

# Phase 1 Plan 3: Map prerequisites and layout Summary

**Named Noamm prerequisite toasts and a centered native map popup with complete returned bounds, wrapped keyboard hints and safe inactive room clicks.**

## Performance

- Continuation duration: approximately 10 minutes; excludes the prior owner's interrupted execution.
- Task 1 committed: 2026-11-13T04:51:05Z.
- Continuation completed: 2026-11-13T09:02:00Z.
- Tasks: 2/2.
- Files created/modified: 10.

## Accomplishments

- Opening checks actual Dungeon Map, Map Enabled and Cheater Map settings and uses existing NotificationManager wording. All eight combinations are exercised by map contract tests.
- Outside/boss state shows contextual empty content; available partial tiles immediately use the real Noamm renderer. No preview, independent scanner, room labels or topology is added.
- Panel dimensions target 360×360, cap at 75% of each viewport dimension, round down to multiples of four and center after resize. Compact padding/gaps, measured text wrapping, size correction and actual binding hints preserve the approved UI boundary.
- Native room clicks are consumed with no dispatch or LeapMenu action. Screen remains open and multiplayer stays unpaused. Esc retains native screen dismissal, with coordinator cancellation wiring assigned to 01-04.
- Map fitting honors returned width and extra-info height with a uniform scale. Exact pinned bounds seed the first draw; actual returned dimensions are retained per mode because drawing twice to measure would duplicate renderer effects.

## Task Commits

1. **01-03-01: Explain actual prerequisites and map availability** — `8d7efc1` (feat), prior owner `/root/impl_map`.
2. **01-03-02: Fit native map and keyboard exits at supported sizes** — `ffa3761` (feat), replacement owner `/root/impl_map_sol`, inheriting the unfinished screen/layout edits.

## Verification

- JDK 25 from PrismLauncher's `java-runtime-epsilon` ran `:26.2:controlTest --args=map-layout`: passed.
- `:26.2:controlTest --args=all`: passed map, session and lease assertions.
- `:26.2:build`: passed after final source change. Intermediate output: `build/libs/Crit Addons-1.0-26.2-cheat.jar`; no release/version provenance claim.
- `git diff --check`: passed; no tracked file deletions.
- Layout cases include 1920×1080 through 160×90 logical viewports, 128×128 and 128×140 native map bounds, an arbitrary returned rectangle, size fallback, centering and balanced nested stack cleanup on a thrown render failure.
- Pinned Noamm MapRenderer.kt and Resolution.kt were re-read from source commit `460cfaf5d8760fa1146f5d2433715b7eca9bf319`. Context7 MCP and ctx7 CLI were unavailable; exact upstream source supplied the version-specific API evidence.

## Decisions Made

No gameplay policies changed. Production map fit and panel math live in one pure helper compiled directly by the harness. Replacement ownership is recorded separately from researcher `/root/phase1_research`.

## Deviations from Plan

The permitted pure layout helper is a separate `MapContractLayout.kt` file, matching the existing harness include boundary instead of adding Minecraft dependencies to isolated tests. The dispatcher gained one call to run the layout suite. No additional dependency or layout engine was introduced.

## Known Stubs

`DungeonMapScreen.kt` deliberately says **Room actions are unavailable in this version.** This is the approved Phase 1 boundary: executable travel, clears and secrets are owned by later phases. No stub prevents this plan's map-viewer goal.

## Outstanding Live Verification

No client was launched or JAR installed in this plan. Actual font/layout screenshots at GUI scales, extra-info on/off, real partial-map comparison, all-floor observations, toast presentation, held/rebound/unbound keys, persistence and world-click isolation require the 01-05 client acceptance evidence. Isolated binding fixtures do not prove actual input event delivery or saved configuration. Terminal/run-reset integration is owned by 01-04 and must prevent old-run map state.

No new network, authentication, filesystem or schema trust surface was introduced. Existing plan threats remain bounded by real renderer reuse, prerequisite naming, uniform fit, finally cleanup and screen click consumption.

## Next Phase Readiness

01-04 can wire raw input and lifecycle observations to the existing popup and coordinator. 01-05 remains responsible for live evidence and verified v0.1.0 publication. This plan is an intermediate increment and does not publish a release.

## Self-Check: PASSED

Confirmed all listed source files and both task commits exist; full control tests and final 26.2 build passed. No generated untracked output was added. The pre-existing untracked 01-PATTERNS.md belongs to orchestration and was left untouched.
