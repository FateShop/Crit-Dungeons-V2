---
phase: 01-live-map-and-safe-task-control
reviewed: 2026-11-13T09:13:00Z
depth: standard
diff_base: c34f8b1
reviewed_head: 83eda0c
files_reviewed: 34
files_reviewed_list:
  - build.gradle.kts
  - docs/implementation-ownership.md
  - docs/noamm-reuse-evidence.md
  - gradle.properties
  - src/controlTest/kotlin/crit/ClientAdapterTest.kt
  - src/controlTest/kotlin/crit/ControlTestMain.kt
  - src/controlTest/kotlin/crit/InputLifecycleTest.kt
  - src/controlTest/kotlin/crit/MapContractTest.kt
  - src/controlTest/kotlin/crit/MapLayoutTest.kt
  - src/controlTest/kotlin/crit/ResourceLeaseTest.kt
  - src/controlTest/kotlin/crit/SessionControlTest.kt
  - src/main/java/com/github/noamm9/untitled/mixins/ActionUtilsAccessor.java
  - src/main/java/com/github/noamm9/untitled/mixins/MixinCategoryPanel.java
  - src/main/java/com/github/noamm9/untitled/mixins/MixinCategoryType.java
  - src/main/java/com/github/noamm9/untitled/mixins/MixinCritKeyboard.java
  - src/main/java/com/github/noamm9/untitled/mixins/MixinCritMouse.java
  - src/main/java/com/github/noamm9/untitled/mixins/MixinCritWindow.java
  - src/main/java/com/github/noamm9/untitled/mixins/MixinMinecraft.java
  - src/main/kotlin/com/github/noamm9/untitled/Untitled.kt
  - src/main/kotlin/com/github/noamm9/untitled/crit/control/CancellationToken.kt
  - src/main/kotlin/com/github/noamm9/untitled/crit/control/ClientControlAdapter.kt
  - src/main/kotlin/com/github/noamm9/untitled/crit/control/LifecycleAdapter.kt
  - src/main/kotlin/com/github/noamm9/untitled/crit/control/ManualInputState.kt
  - src/main/kotlin/com/github/noamm9/untitled/crit/control/ResourceLeases.kt
  - src/main/kotlin/com/github/noamm9/untitled/crit/control/SessionCoordinator.kt
  - src/main/kotlin/com/github/noamm9/untitled/crit/control/SessionState.kt
  - src/main/kotlin/com/github/noamm9/untitled/crit/control/TaskContract.kt
  - src/main/kotlin/com/github/noamm9/untitled/features/impl/critaddons/DungeonMapPopup.kt
  - src/main/kotlin/com/github/noamm9/untitled/features/impl/critaddons/DungeonMapScreen.kt
  - src/main/kotlin/com/github/noamm9/untitled/features/impl/critaddons/MapContract.kt
  - src/main/kotlin/com/github/noamm9/untitled/features/impl/critaddons/MapContractLayout.kt
  - src/main/kotlin/com/github/noamm9/untitled/features/impl/critaddons/MapPrerequisites.kt
  - src/main/kotlin/com/github/noamm9/untitled/features/impl/critaddons/NoammMapAdapter.kt
  - src/main/resources/untitled.mixins.json
findings:
  critical: 3
  warning: 0
  info: 0
  total: 3
status: issues_found
remediation_commits: [a2aeb3f, f4bb3b3]
unresolved_findings_after_remediation: 0
---

# Phase 1: Code Review Report

**Reviewed:** 2026-11-13T09:13:00Z
**Depth:** standard
**Files Reviewed:** 34
**Status:** issues_found

## Narrative Findings (AI reviewer)

## Summary

Reviewed the Phase 1 map and shared controls delivered through `83eda0c`, using `c34f8b1` as the explicit base. Read all existing changed files, the deleted template feature, project rules, requirements, phase context and 01-01 through 01-04 summaries. No project skill directories were present. Reviewed the map renderer, Resolution scaling, input injection descriptors, ActionUtils fields and scheduling behavior, and DungeonListener run-end delivery against the exact installed NoammAddons 1.3.5 Minecraft 26.2 artifact using read-only JDK 25 javap inspection.

Three defects affect lifecycle isolation or coordination with Noamm. Findings and line numbers below describe the submitted `83eda0c` snapshot, not subsequent concurrent fixes. Plan 01-06 development-fixture additions are outside this snapshot and require their own review. No source files were modified by the reviewer, no dependency was executed, and no game was launched. No structural pre-pass was supplied.

## Critical Issues

### CR-01: External Noamm actions resume stale Crit work without revalidation

**Classification:** BLOCKER

**File:** `C:/Users/Michael/Documents/CritAddonDungeons/src/main/kotlin/com/github/noamm9/untitled/crit/control/SessionCoordinator.kt:80`

**Related files:** `src/main/kotlin/com/github/noamm9/untitled/crit/control/ResourceLeases.kt:28-31`; `src/main/kotlin/com/github/noamm9/untitled/crit/control/ClientControlAdapter.kt:76-83`

**Issue:** `externalBusy` only revokes leases. It does not fence the execution generation, preserve the task, or set `requiresRevalidation`. A task can obtain token T, yield while an unrelated Noamm action teleports the player or changes the hotbar, then reacquire with the same token T when the queue empties and execute its old route/aim/item assumptions. Even an old worker result remains current. The existing lease test explicitly reacquires using the unchanged token after external activity. This violates the actual-state resume boundary required by TASK-07 and the shared contract used by later movement/combat consumers.

**Fix:** Compose external activity as an independent pause reason. On the first transition to busy, fence tokens, revoke leases, preserve active work and require revalidation. Clearing only that reason must not clear GUI/manual/death pauses. Require successful actual-state revalidation and a fresh token before another effect. Add a regression asserting the old token fails after external activity clears and that revalidation observes the changed actual state.

**Remediation observed during review:** Coordinator committed `a2aeb3f` to introduce the EXTERNAL pause and update the lease regression. Read the changed implementation: it fences/preserves through the ordinary pause machinery and requires revalidation before a fresh token is available. Coordinator reports the full control suite and target build pass. This finding is resolved at source level; live coexistence remains an acceptance check.

### CR-02: Delayed run-end events can terminate the next run

**Classification:** BLOCKER

**File:** `C:/Users/Michael/Documents/CritAddonDungeons/src/main/kotlin/com/github/noamm9/untitled/crit/control/ClientControlAdapter.kt:22-24`

**Issue:** The listener reads `publishedEpoch` when Noamm dispatches `RunEndedEvent`, rather than carrying the source run identity. Exact installed bytecode shows DungeonListener sets `dungeonEnded=true` in its chat handler, then launches a coroutine (`DungeonListener$init$2$3`) that later posts a singleton event without a run ID. Schedule the old run-end coroutine, cross the world boundary, start a new epoch, then dispatch the old event: the listener captures the new epoch, its equality guard passes, and `lifecycle.terminate()` clears the new run and latches it terminal. The guard protects only the later Minecraft executor hop; it does not protect the earlier asynchronous Noamm delivery.

**Fix:** Remove the redundant unidentified event termination and use the authoritative `dungeonEnded` state already sampled before every guarded effect and tick; Noamm resets this state on world change. Alternatively, observe a synchronous source boundary and carry its world/start identity through every asynchronous hop, checking that identity and current terminal facts on the client thread. Exercise old-run event delivery after new-run attachment in a regression.

**Remediation observed during review:** Coordinator committed `f4bb3b3` removing the event observer. Read the replacement implementation: lifecycle decisions now use client-thread actual-state sampling already present in `refreshFacts`. Coordinator reports the full control suite and target build pass. This finding is resolved at source level; no live next-run transition was exercised by this review.

### CR-03: Shared queue admission can race the supposedly guarded effect

**Classification:** BLOCKER

**File:** `C:/Users/Michael/Documents/CritAddonDungeons/src/main/kotlin/com/github/noamm9/untitled/crit/control/ClientControlAdapter.kt:69-77`

**Issue:** `externalBusy()` releases ActionUtils' lock after reading idle state, before `coordinator.effect` invokes the Crit action. Another thread can call Noamm `ActionUtils.queue` in that gap. Exact installed bytecode confirms `queue` acquires the same lock, enqueues, sets `running=true`, and launches a coroutine; `run` invokes the queued action outside the lock. Thus the following valid interleaving exists: Crit observes idle, Noamm queues/starts an action, Crit performs its action. The two writers can concurrently control movement/aim/hotbar/combat despite the advertised exclusive boundary. Lease identity checks cannot prevent this race because they do not inspect Noamm again.

**Fix:** Hold the shared admission lock across the idle/busy decision and the synchronous guarded Crit effect. This prevents a new `queue` call from admitting work between the check and effect. Continue rejecting effects while a previously admitted action is running; do not cancel or remove Noamm actions. Document that effect closures are synchronous and that unrelated direct writers still need separate audits. Add a latch/barrier concurrency test demonstrating queue admission cannot interleave between the idle observation and the guarded synchronous effect.

**Remediation observed during review:** Coordinator committed `f4bb3b3` wrapping the busy decision and effect in the same synchronized block. Read that implementation and confirmed the shared monitor spans the synchronous effect. Coordinator reports the full control suite, target build and fixture-isolation checks pass. This finding is resolved at source level. Those checks do not establish live applied-mixin behavior or replace the suggested deterministic concurrency regression.

---

_Reviewer: /root/review_sol (gsd-code-reviewer)_
_Review scope is the submitted 01-01 through 01-04 snapshot. All three discovered findings have source-level remediation in a2aeb3f/f4bb3b3, with zero unresolved findings from this review. Counts retain the defects discovered in the submitted snapshot. Plan 01-06 remains outside this review; build success alone does not establish applied-mixin or live-client correctness._
