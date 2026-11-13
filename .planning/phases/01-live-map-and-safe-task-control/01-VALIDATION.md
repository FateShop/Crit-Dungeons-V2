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

Plans and task IDs are not assigned yet. The planner must replace these behavior groups with actual task IDs and Wave 0 dependencies before claiming Nyquist compliance.

| Behavior group | Requirements | Threat / secure behavior | Verification | Status |
| --- | --- | --- | --- | --- |
| Exact reuse, menu and prerequisites | CORE-02, CORE-03, CORE-04 | Dependency drift: compare pinned source, installed hash and ABI | Static ABI review; live named dependency toasts | Pending, Wave 0 |
| Live map and binding | MAP-01, MAP-02 | Reject unknown data; no invented room state | Binding edge tests in controlTest; live render/discovery/rebind | Pending, Wave 0 |
| Cancellation and rearm | TASK-02, DIAG-03 | Stale tasks: epoch/generation fence; preserve enabled settings | controlTest: Esc/fatal, same-run latch, explicit toggle, next run | Pending, Wave 0 |
| Composite pauses | TASK-03, TASK-04 | Input feedback: source separation; all pause reasons must clear | controlTest overlapping GUI/manual/death; revalidation before action | Pending, Wave 0 |
| Terminal lifecycle | TASK-05 | Old callbacks cannot act in another run | controlTest terminal/duplicate/out-of-order event traces | Pending, Wave 0 |
| Exclusive resources | TASK-07 | Scoped cancellation; no global Noamm ActionUtils.reset | controlTest atomic leases/stale tokens plus live unrelated-action sentinel | Pending, Wave 0 |
| Ownership | CORE-05 | Distinct implementers/researchers and shared coordinator ownership | Record actual identities and file ownership before edits | Pending |
| Release provenance | REL-01, REL-02 | Matching source/tag/JAR; no credentials in evidence | Manifest/hash review and download verification of completed v0.1.0 increment | Pending |

## Wave 0 Requirements

- [ ] Define pure coordinator/input/lease fixtures and executable controlTest task.
- [ ] Cover cancellation/rearm, composite pauses, revival revalidation, run boundaries, stale callbacks and unrelated-owner isolation.
- [ ] Establish exact artifact ABI checks and mapped 26.2 screen/input descriptors.
- [ ] Add live scenario and release evidence templates.
- [ ] Locate/select JDK 25; establish authorized publishing route before release.

## Manual-Only Verifications

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

**Approval:** Pending planning and plan-checker review. No build, installation or live test performed.
