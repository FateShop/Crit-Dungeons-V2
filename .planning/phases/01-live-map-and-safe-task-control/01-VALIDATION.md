---
phase: 01
slug: live-map-and-safe-task-control
status: draft
nyquist_compliant: false
wave_0_complete: false
created: 2026-11-12
---

# Phase 1 — Validation Strategy

## Test Infrastructure

Proposed dependency-free JVM invariant harness, isolated from Minecraft/Noamm loading. No harness or test task exists yet. Execution must select and verify JDK 25 and confirm the Stonecutter task paths before running these commands.

| Property | Value |
| --- | --- |
| Config | Wave 0 adds a pure test source set and JavaExec task in build.gradle.kts |
| Quick command | `./gradlew.bat :26.2:controlTest` (proposed) |
| Full command | `./gradlew.bat :26.2:controlTest :26.2:build` (proposed) |
| Runtime | Unmeasured; target warm harness feedback under 30 seconds; cold builds excluded |

## Sampling Rate

- After each implementation task commit: pure harness and affected ABI checks.
- After each plan wave and before phase verification: full 26.2 checks/build.
- Measure feedback latency during execution; no watch mode. Do not claim automated tests establish live mechanics.

## Per-Task Verification Map

Task IDs below match the six executable plans. Wave 0 infrastructure is task 01-01-01, executed in Wave 1 before dependent implementation. Commands remain proposed until that task establishes and verifies the Gradle harness; no existing test success is implied.

| Task ID | Requirements | Verification | Wave 0 dependency | Status |
| --- | --- | --- | --- | --- |
| 01-01-01 | CORE-02, CORE-05 | JDK/task inventory, artifact hash/ABI, actual ownership record, RED map fixture | Establishes controlTest and map fixture | Planned |
| 01-01-02 | CORE-03, MAP-02 | controlTest --args=map-settings; target compile; real Noamm persistence | 01-01-01 | Planned |
| 01-01-03 | MAP-01, MAP-02 | controlTest --args=map; 26.2 build; live render path | 01-01-01 | Planned |
| 01-02-01 | TASK-02–05, DIAG-03 | controlTest --args=session; stop/rearm/composite pause/terminal traces | 01-01-01 | Planned |
| 01-02-02 | TASK-07 | controlTest --args=leases; atomic conflicts, stale tokens, scoped sentinel | 01-01-01 | Planned |
| 01-03-01 | CORE-02–04, MAP-01–02 | controlTest --args=map-contract; compile; prerequisite combinations/real data guards | 01-01-01 | Planned |
| 01-03-02 | MAP-01–02 | controlTest --args=map-layout; target build; GUI-scale screenshots | 01-01-01 | Planned |
| 01-04-01 | TASK-02–03, TASK-07 | controlTest --args=input; target compile; mapped descriptors and physical-input scenarios | 01-01-01 plus 01-02 contract fixtures | Planned |
| 01-04-02 | TASK-02–05, TASK-07, CORE-04 | controlTest --args=client-adapters; full harness/build; actual mixin/lifecycle/coexistence checks | 01-01-01 plus 01-02 contract fixtures | Planned |
| 01-06-01 | TASK-02–05, TASK-07 | controlTest --args=dev-fixture; verifyControlFixtureIsolation; actual dev start/status | 01-04 production adapters | Planned |
| 01-06-02 | TASK-02–05, TASK-07 | Full harness/build/isolation; actual fatal/rearm/sentinel and physical scenario evidence | 01-06-01 fixture | Planned |
| 01-05-01 | All Phase 1 IDs | Verify-Phase1.ps1; actual map/control/floor scenario evidence | 01-06 fixture and earlier implementation checks | Planned |
| 01-05-02 | REL-01–02, CORE-02 | Full harness/build; Verify-Release.ps1 -Stage Candidate | 01-01-01 | Planned |
| 01-05-03 | REL-01–02 | Verify-Release.ps1 -Stage Published; remote tag/download hash check | Candidate evidence/script | Planned |

## Wave 0 Requirements

- [ ] Define pure coordinator/input/lease fixtures and executable controlTest task.
- [ ] Cover cancellation/rearm, composite pauses, revival revalidation, run boundaries, stale callbacks and unrelated-owner isolation.
- [ ] Establish exact artifact ABI checks and mapped 26.2 screen/input descriptors.
- [ ] Add live scenario and release evidence templates.
- [ ] Locate/select JDK 25; establish authorized publishing route before release.

## Manual-Only Verifications

Controlled client session entry point: `.\gradlew.bat :26.2:runControlFixture`, then `/crit-control-fixture start` and `status`. The isolated `src/devControl/` fixture directly queues active probe-A and pending probe-B through the production coordinator. Bounded guarded heartbeats show pause/resume/revalidation without movement/combat/item-use effects. Use commands `fatal`, `selection-off`, `selection-on`, `sentinel` and `reset`; real input/GUI/death/lifecycle adapters remain authoritative. The sentinel is an unrelated Noamm shared-queue action; Crit yields using a read-only synchronized ActionUtils busy snapshot and never resets that queue. `.\gradlew.bat :26.2:verifyControlFixtureIsolation` checks compiled fixture exclusion from final JAR and ordinary run configuration. Commands must be absent in ordinary client runs. Live scenario evidence records actual IDs, heartbeat counts, current epoch, reasons and revalidation snapshots; fixtures never establish later gameplay capability.

| Behavior | Requirements | Instructions |
| --- | --- | --- |
| Actual map presentation and discovery | CORE-03, MAP-01, MAP-02 | Open/rebind M, inspect known and unknown rooms and new discovery; centered popup preserves Noamm appearance. Later room commands remain unavailable. |
| Dependency toasts | CORE-04 | Disable required Dungeon Map/Map Enabled/Cheater Map prerequisites separately; confirm exact existing Noamm notifications. |
| Physical input and screens | TASK-02, TASK-03 | Hold/release movement, mouse and interaction controls; test focus changes and GUI overlap; confirm silent pauses, no synthetic-input oscillation, Esc clears work even with popup open. |
| Death and run lifecycle | TASK-04, TASK-05 | Exercise actual death/revival, boss entry, exit, run end and disconnect; verify no stale work and preserved configuration after relaunch. |
| Noamm coexistence | TASK-07 | Cancel a controlled Crit task while unrelated Noamm work is pending; verify unrelated work survives. |
| Installable release | REL-01, REL-02 | Verify source/version/hash against downloadable 26.2 JAR and semantic v0.1.0 release; additions/changes/removals and unperformed live tests are explicit. |

Live checks must record supported floor coverage rather than assume universal behavior. Controlled tasks exercise coordination only; they do not complete later movement/combat/secrets features. Later Blood deadline, no-LOS/impossible-timing and post-camp decisions remain affected-phase gates.

## Validation Sign-Off

- [ ] Actual plan tasks mapped to automated verification or explicit Wave 0 dependencies.
- [ ] No three consecutive implementation tasks without automated feedback.
- [ ] All missing harness/ABI references supplied by Wave 0.
- [ ] Measured warm feedback target met; no watch-mode flags.
- [ ] Required live checks and release evidence recorded honestly.
- [ ] Set nyquist_compliant only after plan review; wave_0_complete only after execution.

**Approval:** Task mapping complete; independent plan-checker review pending. Keep nyquist_compliant false until that review passes. wave_0_complete remains false until infrastructure executes. No build, installation or live test performed during planning.
