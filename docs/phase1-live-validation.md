# Phase 1 validation evidence

Recorded 2026-11-13. This evidence covers the map viewer and shared session controls only. Travel, clearing, secrets and automatic runs are unavailable in this increment.

## Automated evidence

Microsoft JDK 25.0.1, Gradle 9.8.2, Minecraft target 26.2 and Noamm 1.3.5 cheat. Exact installed dependency SHA-256: `6212888ddce467fb0da81bc3df9e285c39867fc8aa9547bb4976eec911f3f6fd`. The installed dependency was inspected with javap and its same-version development dependency loaded during startup. Pinned source contract: `460cfaf5d8760fa1146f5d2433715b7eca9bf319`.

`scripts/Verify-Phase1.ps1` checks JDK/target/dependency ABI and hash, implementation ownership, input mixin registrations, absence of shared ActionUtils reset/native input suppression, full contract suite, target build and development fixture archive isolation. Tests cover map key matching/layout/actual-data guards/prerequisites, stop/rearm/epochs, composite pauses, leases, focus/held input/motion debounce, terminal latch, heartbeat preservation/revalidation and stale effects. These are JVM contracts, not live-mechanics claims.

Independent review fixes are `a2aeb3f` (revalidate after shared action yield) and `f4bb3b3` (remove unproven delayed singleton run-end termination and serialize queue admission/effect). No unresolved automated hard failure is known at candidate preparation. Consumer-specific audits for direct Noamm action writers remain required in later features.

## Actual client observation

The agent launched `.\gradlew.bat :26.2:runControlFixture`. At 2026-11-13 04:14:29 local, `versions/26.2/run/logs/latest.log` recorded fixture registration and `Crit Addons client controls initialized`. At 04:14:32 sound and texture atlas initialization completed. Minecraft 26.2, Fabric Loader 0.19.5, Noamm 1.3.5 and the isolated development mod were present in the startup inventory. No Mixin application exception was observed through this startup/client-tick interval; this does not prove every callback's behavior. Source snapshot was the implementation after `d695351` with task2 work in progress, before final `f009acc` rearm refinement. Final candidate has separate source/JAR provenance in release evidence.

The default offline development identity had Mojang certificate HTTP 401, so no authenticated Hypixel session or dungeon could be entered. Root's computer tools found the Minecraft window, but window-state/capture failed with FrameArrived timeout even after activation. No screenshot was produced and visual checks are unperformed. No JAR was installed into the user's Prism instance by this validation. Private account/network logs are not published with the release.

## Requirement evidence map

| Requirement | Evidence | Live limitation |
| --- | --- | --- |
| CORE-02 | Exact artifact/source ABI, reused renderer/scanner/settings/notifications and read-only queue bridge | Relevant direct writers need later consumer audits |
| CORE-03 | Registered Crit Addons category and feature package; target compilation | Menu discovery/appearance not visually observed |
| CORE-04 | Exact prerequisite names and Noamm NotificationManager, map contract combinations | Actual toast display unobserved |
| CORE-05 | docs/implementation-ownership.md records distinct map/coordinator/research identities | Later five feature owners assigned in their phases |
| MAP-01 | Live Noamm renderer only, unknown/empty/terminal guards; map contract suite | Actual dungeon discovery/render match unobserved |
| MAP-02 | Configurable default M through Noamm setting, press-only surface matching, key/layout suite | Rebind/unbound/repeat/relaunch persistence unobserved |
| TASK-02 | Emergency stop latch/generation tests; native keyboard observer before normal dismissal | Real Esc callbacks in gameplay/screens unobserved |
| TASK-03 | Composite GUI/manual/focus pauses, preservation and actual-state revalidation guard | Real held input/mouse/focus, overlapping GUIs unobserved |
| TASK-04 | Death pause/revalidation fixtures; exact Noamm ghost facts inspected | Actual death/ghost/revival unobserved |
| TASK-05 | Monotonic epoch/terminal guards and stale-token tests | Actual boss/end/exit/disconnect/next-run unobserved |
| TASK-07 | Exclusive leases, external pause/revalidation, synchronized queue/effect admission | Actual unrelated Noamm sentinel survival unobserved |
| DIAG-03 | Locked Phase1 D-01–13 honored; no Blood overrides introduced | Later deadline/visibility/impossible timing/post-camp decisions stay open |
| REL-01 | Candidate semantic version/build; remote tag/release verification is a separate gate | Publication not claimed by this evidence file |
| REL-02 | Source/JAR/dependency manifest and deterministic Candidate/Published checks | Archive inspection is not gameplay validation |

## Scenario coverage

All requested visual/UI scenarios are unperformed: normal/small window and multiple GUI scales, Noamm extra-info on/off, default/rebound/unbound/repeat key, unrelated GUI typing, named prerequisite toasts, outside/empty/partial map, unavailable room clicks, stale map prevention, M close versus Esc. Pure map fixtures cover the underlying contracts; screenshots cannot be inferred from them.

All requested real controlled-session scenarios are unperformed: hold/release/motion/focus and synthetic separation, GUI/manual/death overlap, real revival snapshot revalidation, Esc/fatal same-run latch, explicit restart/new-run rearm, settings persistence across relaunch, boss/end/exit/disconnect clearing and actual unrelated queue sentinel. `docs/phase1-control-fixture.md` supplies exact commands and expected observations. No paused/resuming toast is implemented.

| Floors | Actual dungeon observation | Inspected scope |
| --- | --- | --- |
| F1, F2, F3, F4, F5, F6, F7 | None | Same Noamm map, lifecycle and control API; no Crit floor-specific geometry/combat |
| M1, M2, M3, M4, M5, M6, M7 | None | Same API paths; no extrapolated floor-specific success |

This release candidate has automated and limited startup evidence, with the live checks above explicitly unperformed. Any observed hard failure blocks delivery until repaired; absence of those observations is never counted as a pass. No claim of all-floor live verification or completed later gameplay is made.
