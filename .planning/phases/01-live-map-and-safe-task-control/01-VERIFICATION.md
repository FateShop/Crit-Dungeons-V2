---
phase: 01-live-map-and-safe-task-control
verified: 2026-11-13T09:23:00Z
status: human_needed
score: 9/13 must-haves verified
overrides_applied: 0
human_verification:
  - test: Inspect the real map, menu, prerequisites and binding persistence
    expected: Actual Noamm discovery renders correctly; controls are under Crit Addons; named existing toasts and saved bindings work across relaunch.
    why_human: No authenticated dungeon or screenshots were available; pure contracts do not establish presentation or actual persistence.
  - test: Exercise physical input, GUI overlap, death and revival with the development fixture
    expected: Heartbeats pause without input suppression or objective loss and resume only after all reasons clear and actual-state revalidation.
    why_human: Real callbacks, ghost state and applied client behavior were not exercised.
  - test: Exercise Esc, fatal stop, explicit rearm and actual lifecycle transitions
    expected: Work clears without same-run passive restart; a genuine next run starts fresh and no old callback can act.
    why_human: Simulated epochs are covered, but actual boss, end, exit, disconnect and subsequent run were not observed.
  - test: Verify unrelated Noamm action survival and final release runtime
    expected: The sentinel completes despite Crit cancellation; Crit yields and revalidates; the release loads with no development fixture command.
    why_human: Queue coexistence and final downloaded JAR loading were not tested in the authenticated intended client.
---

# Phase 1: Live Map and Safe Task Control Verification Report

**Phase Goal:** As a dungeon player using Crit Addons, I want to inspect the live dungeon map and safely control a coordinated task session, so that I can understand the dungeon and retain control over automation.

**Verified:** 2026-11-13T09:23:00Z  
**Status:** human_needed  
**Re-verification:** No — initial verification  
**Source:** `4a2692201cdf62be22b5f5f1ad93d3fcbb744a9d`, version `0.1.0`

No previous VERIFICATION.md or accepted overrides existed. All six plans and the five roadmap criteria were inspected. `gsd-sdk query user-story.validate --story <goal> --pick valid` returned `true`. The code supports the intended flow, and independent automated checks pass. The actual player outcome remains uncertain until the live checks below are observed. Missing optional live evidence is a WARNING, not an observed implementation failure.

## User Flow Coverage

| Step | Expected | Evidence in codebase | Status |
| --- | --- | --- | --- |
| Find controls | Crit Addons contains Dungeon Map Popup and Popup Key | `MixinCategoryType.java`, `MixinCategoryPanel.java`, `DungeonMapPopup.kt:9-10`; real Noamm Feature/KeybindSetting reuse | UNCERTAIN — menu and saved settings unobserved |
| Open map | Current configurable key opens a centered popup and names missing prerequisites | `DungeonMapPopup.kt:22-34`, `MapPrerequisites.kt:7-12`, `MapContract.kt`; press-only gameplay/map surface dispatch | UNCERTAIN — physical binding/toast presentation unobserved |
| Inspect discovery | Only actual Noamm-known tiles/identities appear and update | `DungeonMapScreen.kt:64-65` calls `NoammMapAdapter`; adapter reads current scanner and invokes actual renderer with example=false | UNCERTAIN — actual dungeon drawing/discovery unobserved |
| Retain control | Input/GUI/death pause, actual-state resume, Esc clears work, terminal events prevent carry-over | `ClientControlAdapter`, registered native observers, `SessionCoordinator`, `LifecycleAdapter`; runnable opt-in production-backed fixture | UNCERTAIN — real client scenarios unobserved |
| Obtain increment | Downloadable source-matched 26.2 JAR excludes later actions/fixture | Independent Published check passed; immutable source tag, remote JAR metadata and SHA256 agree | VERIFIED for delivery/provenance; installed-client behavior remains unobserved |
| Outcome | Understand the dungeon and retain control over automation | Complete code paths exist; no real dungeon session was observed | UNCERTAIN — human decision/testing required |

## Goal Achievement

### Observable Truths

Roadmap contract wording is retained for rows 1–5. Rows 6–13 add nonduplicative plan detail; other PLAN truths refine the matching roadmap row rather than reducing its scope.

| # | Truth | Status | Evidence |
| --- | --- | --- | --- |
| 1 | Player opens the configurable default-M popup, sees only actual Noamm-known room identities/layout, and sees discovery updates without fabricated rooms. | UNCERTAIN (WARNING) | `DungeonMapPopup.kt:10,22-34`; `DungeonMapScreen.kt:64-65`; `NoammMapAdapter.kt:15-31`. Real renderer and scanner are wired; real drawing, discovery, persistence and binding remain unobserved. |
| 2 | Player finds new controls under Crit Addons; missing required Noamm settings produce existing notifications naming the required settings. | UNCERTAIN (WARNING) | Category mixins and feature package are connected; `MapPrerequisites.kt:7-12` calls `NotificationManager.push`; eight prerequisite combinations tested. Actual menu/toast presentation unobserved. |
| 3 | In a controlled coordinated task session, Esc clears work without spontaneous restart; GUI/death pauses preserve objectives and resume according to explicitly agreed rules, while ordinary manual movement does not cancel. | UNCERTAIN (WARNING) | `SessionCoordinator.kt:34-74` fences/cancels or preserves; `ClientControlAdapter.kt:24-30,47-57,71-83` observes real inputs/death and captures actual pose. Pure session/input/fixture cases pass; physical cases unperformed. |
| 4 | Disconnect, dungeon exit, run end and boss entry clear Crit state; simultaneous resource requests cannot compete for movement, aim, hotbar, GUI or combat, and unrelated Noamm work continues. | UNCERTAIN (WARNING) | `LifecycleAdapter.kt:11-35`, leases and `ClientControlAdapter.kt:59-75`; effect holds the shared queue lock and never resets Noamm. Actual transition and unrelated-action coexistence unobserved. |
| 5 | The completed map/control feature is installable from its verified v0.1.0 release JAR with source/version evidence and honest validation notes; distinct research/implementation ownership and reuse evidence are reviewable. | VERIFIED | Published verification independently downloads and inspects JAR; source/version/target/hash match. Ownership and exact dependency evidence exist. Installability verified structurally, not as a final-JAR gameplay claim. |
| 6 | Terminal lifecycle fences stale callbacks; new runs start fresh. | VERIFIED | `CancellationToken` epoch/generation, monotonic `observeRun`, `terminate`, `currentToken` and effect checks; session/client-adapter regressions reject old epochs/tokens. |
| 7 | Centered medium popup fits returned map bounds and retains Noamm appearance. | VERIFIED at code/geometry level | `MapContractLayout.kt`, `NoammMapAdapter.kt:21-34`, `MapLayoutTest.kt`; complete uniform fit, small-window fallback and stack unwind. Visual appearance remains in human item 1. |
| 8 | Rebinding/repeat/typing and unavailable room actions behave truthfully. | VERIFIED at code level | Noamm `KeybindSetting.matches`; only PRESS accepted; OTHER surfaces ignored; Esc excluded from toggle; `DungeonMapScreen.mouseClicked` consumes clicks and footer declares actions unavailable. Real binding/relaunch checks remain in human item 1. |
| 9 | Required map/control checks have reviewable results and unperformed live cases are explicitly identified. | VERIFIED | Independent verification below; `docs/phase1-live-validation.md` names every missing scenario and all fourteen unobserved floors, startup snapshot and capture/auth limitations. |
| 10 | Only map-viewer/session-control increment receives v0.1.0, with no claim of later room actions. | VERIFIED | Release notes Additions/Changes/Removals, Fabric description and popup explicitly exclude travel/clear/secrets/automatic modes/Blood Camp. |
| 11 | Development-only fixture queues active and pending tasks through the actual production coordinator. | VERIFIED | `CritControlFixture.kt:15-27`, `FixtureSession.kt:14-20`; actual adapter coordinator passed to fixture; two enqueue calls; task identity assertions pass. |
| 12 | Bounded guarded probe effects exercise control without later room gameplay, with real observations authoritative. | VERIFIED at code/fixture level | `FixtureSession.kt:23-42`: guarded memory heartbeat, 200+20 ticks, 120-second bound; fixture uses `ClientControlAdapter.effect`; no injected gameplay/input/death/lifecycle facts. Live proof remains in human items 2–4. |
| 13 | Fixture classes, metadata and commands are unavailable in the ordinary production addon. | VERIFIED | Separate devControl source set and double runtime opt-in; `verifyControlFixtureIsolation` passed and independent release archive inspection rejects dev/fixture classes. |

**Score:** 9/13 truths verified; 4 UNCERTAIN (WARNING); 0 FAILED/BLOCKER; 0 overrides. Verified rows with a stated code-level boundary do not count the corresponding unperformed live checks as passed.

### Required Artifacts

All 15 declared artifact entries passed independent `gsd-sdk query verify.artifacts` across six plans. Manual review additionally checked substantive implementations and consumers, not merely line counts.

| Artifact | Expected | Status | Details |
| --- | --- | --- | --- |
| `DungeonMapPopup.kt` | Feature and persistent binding | VERIFIED | Real Feature/KeybindSetting, keyboard/mouse handlers and native screen creation |
| `DungeonMapScreen.kt`, `NoammMapAdapter.kt` | Current map render flow | VERIFIED code; live WARNING | Native render hook → availability → real scanner/renderer; no example rooms |
| `MapPrerequisites.kt` | Existing named dependency notifications | VERIFIED code; live WARNING | Actual Noamm settings queried; existing push API called |
| `MapContractLayout.kt`, `MapLayoutTest.kt` | Fit/fallback/layout invariants | VERIFIED | Multiple viewports/bounds and exception-safe transform unwind exercised |
| `SessionCoordinator.kt`, `CancellationToken.kt`, `TaskContract.kt` | One shared execution authority | VERIFIED | Actual queue, pause composition, stop latch, revalidation and stale-effect guards |
| `ResourceLeases.kt` | Exclusive owner-scoped arbitration | VERIFIED | Atomic conflict rejection, identity-sensitive release, external yield; suite passes |
| `ClientControlAdapter.kt`, `LifecycleAdapter.kt`, `ManualInputState.kt` | Native observations and authoritative state | VERIFIED code; live WARNING | Registered mixins, client-thread observations, actual snapshot capture, terminal latch |
| `CritControlFixture.kt`, `FixtureSession.kt` | Opt-in production-backed probe | VERIFIED | Real coordinator and adapter guard, bounded memory effects; excludes later gameplay |
| `docs/phase1-control-fixture.md` | Exact live invocation and expected results | VERIFIED | Concrete commands and scenarios correspond to registered command implementations |
| `docs/implementation-ownership.md` | Distinct actual owners | VERIFIED | Coordinator `/root/impl_coordinator_sol`, map `/root/impl_map_sol`, researcher `/root/phase1_research`; replacements recorded |
| `docs/phase1-live-validation.md` | Results and limits | VERIFIED | Separates pure fixtures, limited earlier startup, unobserved final release/floors/UI |
| `docs/releases/v0.1.0-evidence.json`, `scripts/Verify-Release.ps1` | Source/tag/download provenance | VERIFIED | Independent Candidate and Published executions passed |

Production code paths above are relative to `src/main/kotlin/com/github/noamm9/untitled/` (`features/impl/critaddons` or `crit/control`); fixture paths are under `src/devControl/kotlin/com/github/noamm9/untitled/dev/`.

### Key Link Verification

SDK key-link checks were executed for all six plans. Their literal symbol-only `from` values produced “Source file not found”; prose destinations also failed literal matching. These are checker limitations resolved by direct inspection below, not evidence of missing implementation.

| From | To | Via | Status | Details |
| --- | --- | --- | --- | --- |
| DungeonMapPopup | DungeonMapScreen | Actual key handler | WIRED | `toggleFrom` creates screen after prerequisite check |
| NoammMapAdapter | MapRenderer.draw | Public 1.3.5 call | WIRED | `MapContract.drawLive` supplies false,false; exact installed bytecode confirms scanner path for example=false |
| MapPrerequisites | NotificationManager.push | Actual settings | WIRED | Correct Dungeon Map / Map Enabled / Cheater Map names |
| DungeonMapScreen | NoammMapAdapter | Render hook | WIRED | Reads availability every render and draws with panel bounds |
| SessionCoordinator | CancellationToken | Epoch/generation | WIRED | Token generated for head task; effect requires matching current token |
| ResourceLeases | TaskContract | Coordinator effect guard | WIRED | Task owner/id in token; exact lease identity and resource checked |
| Native input mixins | ClientControlAdapter | Non-cancelling HEAD observers | WIRED code | All registered in mixins JSON; keyboard/mouse priority 2000 |
| LifecycleAdapter | SessionCoordinator | Client-thread facts | WIRED | Run epochs/terminate from sampled connection/dungeon/boss/end/start facts; no delayed singleton callback |
| Untitled | ClientControlAdapter | Initializer and Minecraft tick mixin | WIRED | Initialized independently of map feature toggle; tick remains registered |
| CritControlFixture | Production coordinator and adapter | Shared APIs | WIRED | Same coordinator passed to FixtureSession and same guarded effect called |
| runControlFixture | devControl | Isolated source set/property/JAR | WIRED | Gradle run and fabric.addMods point to opt-in fixture JAR |
| Release evidence | Remote tag/asset | Immutable SHA and independent download | WIRED | Published test resolves tag to candidate source and verifies remote archive |
| Live evidence | Requirement acceptance | Explicit scenario/floor matrix | WIRED | No missing scenario is represented as passed |

### Data-Flow Trace (Level 4)

| Artifact | Data variable | Source | Produces real data | Status |
| --- | --- | --- | --- | --- |
| DungeonMapScreen | availability/current draw | `NoammMapAdapter.availability` → LocationUtils/lifecycle + DungeonScanner.dungeonList | Current Noamm state; no static/example map | FLOWING in code; actual client rendering unobserved |
| NoammMapAdapter | rendered rooms/text | `MapRenderer.draw(ctx,false,false)` | Exact installed javap shows false path reads DungeonScanner, renders rooms/text; preview path requires example=true | FLOWING in code |
| Fixture status/heartbeat | epoch/task/pauses/lastActual | Actual production coordinator and `captureActual` | Client player pose/dimension/floor/room; test fixture snapshot only in JVM suite | FLOWING in code; live snapshot unobserved |

No DB/API is relevant to this client addon. No hollow empty render props or hardcoded room dataset was found. Empty/outside views are deliberate truthful states. The `extraInfo=false` renderer argument follows the explicit dependency contract; MapConfig still governs its own map-extra setting and returned bounds are retained.

### Behavioral Spot-Checks

Executed by this verifier in its own process from the repository root; no game/server was launched.

| Behavior | Command | Result | Status |
| --- | --- | --- | --- |
| Shared map/control contract behaviors, target build and fixture exclusion | `powershell -NoProfile -File scripts/Verify-Phase1.ps1` | Exit 0; `Passed all`; `BUILD SUCCESSFUL in 4s`; fixture isolation passed | PASS |
| Candidate source/version/target/archive provenance | `powershell -NoProfile -File scripts/Verify-Release.ps1 -Stage Candidate` | Exit 0; candidate evidence passed for `4a2692201cdf62be22b5f5f1ad93d3fcbb744a9d` | PASS |
| Remote immutable tag and independently downloaded archive | `powershell -NoProfile -File scripts/Verify-Release.ps1 -Stage Published` | Exit 0; “Published tag and independently downloaded JAR verified.” | PASS |
| Patch whitespace integrity | `git diff --check` | Exit 0 | PASS |

Verify-Phase1 also independently checked JDK 25, exact dependency SHA256 and javap ABI, declared owners, registered observers and absence of prohibited shared reset/native suppression. Build/test inputs were up-to-date where appropriate, but the control suite and archive isolation ran during this verification. These warm checks each completed in under ten seconds.

### Probe Execution

No `scripts/*/tests/probe-*.sh`, phase-declared shell probes, or PASS/stage-marker probes exist. SKIPPED for shell probes. The actual PowerShell acceptance and Candidate/Published scripts were independently executed above; SUMMARY pass claims were not substituted. The live development fixture is not a shell probe and was not relaunched by this verifier.

### Release Evidence

- Release: https://github.com/FateShop/Crit-Dungeons-V2/releases/tag/v0.1.0
- Source tag resolves to `4a2692201cdf62be22b5f5f1ad93d3fcbb744a9d`.
- Download: `Crit.Addons-0.1.0-26.2-cheat.jar` (GitHub normalized asset filename; local archive retains spaces).
- Size: 103417 bytes. SHA256: `7bbf919360b06ef7f0c72659f5ffefaaae91e189ba3e9186b94e016a778bfebe`.
- Embedded Minecraft 26.2 / Noamm 1.3.5 / version 0.1.0; Java class target 69; required production classes/mixins present; fixture absent.
- Exact installed Noamm SHA256: `6212888ddce467fb0da81bc3df9e285c39867fc8aa9547bb4976eec911f3f6fd`; pinned source `460cfaf5d8760fa1146f5d2433715b7eca9bf319`.
- Earlier development startup initialized controls/fixture, with no observed startup Mixin exception, but preceded the final fixture refinement. No final downloaded JAR installation or authenticated gameplay was observed. Structurally installable does not mean live verified.

### Requirements Coverage

All 14 Phase 1 requirements appear in PLAN frontmatter; none is orphaned. SATISFIED below is limited to applicable Phase 1 scope. Later consumer behavior is not claimed.

| Requirement | Source plans | Description | Status | Evidence |
| --- | --- | --- | --- | --- |
| CORE-02 | 01,03,04,05 | Reuse exact Noamm functionality | SATISFIED | Actual renderer/scanner/Feature/KeybindSetting/notifications and narrow read-only queue bridge; exact ABI/hash inspected |
| CORE-03 | 01,03,05 | Crit Addons controls | NEEDS HUMAN | Category and feature wiring exists; real menu display unobserved |
| CORE-04 | 03,04,05 | Existing named toasts | NEEDS HUMAN | Real push API/settings and contract tests; real toast unobserved |
| CORE-05 | 01,02,05,06 | Distinct ownership and no historical reuse | SATISFIED for Phase 1 | Actual researcher/map/coordinator identities recorded; later owners assigned by future phases; this verification inspected only current repo/intended dependency |
| MAP-01 | 01,03,05 | Actual updating known map | NEEDS HUMAN | Real scanner/renderer flow; no fabricated map; actual dungeon drawing unobserved |
| MAP-02 | 01,03,05 | Configurable default M, popup stays open | NEEDS HUMAN | Real key setting/press handler and no room-action close; actual persistence/rebinding unobserved |
| TASK-02 | 02,04,05,06 | Esc/no restart/manual preservation | NEEDS HUMAN | Stop latch and input contracts pass; physical Esc/input unobserved |
| TASK-03 | 02,04,05,06 | GUI preservation/resume | NEEDS HUMAN | Composite pause/revalidation and narrow required-loadout contract; actual GUI behavior unobserved |
| TASK-04 | 02,04,05,06 | Death/revival actual-state resume | NEEDS HUMAN | Actual death facts/snapshot path and contracts; real ghost/revival unobserved |
| TASK-05 | 02,04,05,06 | Terminal clear/fresh run | NEEDS HUMAN | Lifecycle guard/epochs tested; real transitions unobserved |
| TASK-07 | 02,04,05,06 | Exclusive resources/unrelated work | NEEDS HUMAN | Lease guards and synchronized queue admission exist; unrelated sentinel fixture is not live-tested |
| DIAG-03 | 02,04,05 | Resolve affected product decisions | SATISFIED for Phase 1 | D-01–13 preserved; later Blood/visibility/deadline/post-camp gates remain explicit and are not silently decided |
| REL-01 | 05 | Semantic release and compiled asset | SATISFIED | Remote v0.1.0/tag/archive verified; additions/changes/removals notes present |
| REL-02 | 05 | Provenance and honest limits | SATISFIED | Independent downloaded hash/metadata/source agreement, explicit unperformed live/floor evidence |

### Anti-Patterns Found

No unreferenced `TBD`, `FIXME` or `XXX` debt markers were found in phase code/scripts/build inputs. No production placeholder handler, shared ActionUtils reset, native observation cancellation or empty hardcoded room data was found. Fixture no-op preserve/cancel hooks are intentional for memory-only probes; the real coordinator owns cancellation and preservation state.

| File | Line | Pattern | Severity | Impact |
| --- | --- | --- | --- | --- |
| `src/controlTest/kotlin/crit/MapContractTest.kt` | 23 | Mutable map stands in for saved binding | WARNING | This test passes without exercising Noamm persistence or a relaunch; those checks remain human-needed |
| `src/controlTest/kotlin/crit/DevFixtureTest.kt` | 40–43 | Local sentinel counter models unrelated ownership | WARNING | Does not call actual ActionUtils or establish real sentinel survival; live fixture supplies the real command |
| `src/controlTest/kotlin/crit/ClientAdapterTest.kt` | 5 | Pure lifecycle tests omit native Minecraft adapter | WARNING | Passing client-adapters suite does not prove applied callbacks/actual facts; target compile/startup and live checks have distinct limits |

Disconfirmation pass: the map/persistence player requirement is only partially established; the saved-binding and sentinel tests above cannot establish their live claims. The queue-admission race fix is visible in actual synchronized code, but there is no deterministic latch/barrier concurrency regression exercising the real ActionUtils admission boundary. No new observed hard defect was found. Independent review CR-01/02/03 fixes are present: external yield fences/revalidates, unidentified delayed run-end listener is removed, and the same queue monitor spans busy decision plus synchronous effect.

### Human Verification Required

These items merge all deferred PLAN human-check blocks with this verification's findings. They require observation/decision, not a fabricated pass. The earlier offline client/capture failures are disclosed in `docs/phase1-live-validation.md` and are not repeated as newly performed tests.

#### 1. Real map, menu, prerequisite and binding walkthrough

**Test:** On the exact client, find Crit Addons → Dungeon Map Popup → Popup Key. Test default/rebound/unbound/repeat binding, unrelated-GUI typing, save/relaunch, prerequisite combinations, outside/empty/partial discovery, current-key close versus Esc and unavailable click combinations. Compare a real discovery update with Noamm. Inspect normal/small windows, GUI scales, and extra-info modes.

**Expected:** Current binding persists; actual map stays centered/readable with visible game perimeter and complete bounds; no fabricated/stale rooms; existing toasts name settings without repeat flooding; clicks remain unavailable and leave popup open.

**Why human:** Real UI, setting storage/relaunch and dungeon drawing were not observed; screenshots failed to capture. Pure geometry and binding models do not prove them.

#### 2. Physical pauses, overlap and revival

**Test:** Launch the opt-in `:26.2:runControlFixture`, enter a real nonboss dungeon, use start/status, then exercise held/released keys/buttons, sustained mouse motion, focus changes, synthetic control separation, map/other GUI overlap and actual death/ghost/revival.

**Expected:** Probe-A stays active and probe-B pending; heartbeat freezes silently; manual input remains usable. All pause reasons must clear and actual pose/room snapshot be acknowledged before heartbeat resumes.

**Why human:** Physical callbacks and actual Noamm/Minecraft death/screen facts are not executed by the JVM contracts.

#### 3. Stop/rearm and real lifecycle

**Test:** Esc from gameplay/map/other GUI, fatal, repeated valid ticks/screen closing, selection-off/on, actual boss/end/exit/disconnect and a genuine next run. Verify only configuration persists across relaunch.

**Expected:** Esc/fatal clear current/pending work and old effects without changing saved preferences or passive restart. Explicit restart/new run rearm correctly. Terminal events discard work, preserve unrelated screens and never carry tasks into the next run.

**Why human:** Actual run boundaries, callback ordering and configuration-only relaunch are not demonstrated by synthetic epochs.

#### 4. Noamm coexistence and final downloaded release runtime

**Test:** Queue the real fixture sentinel during active probes and cancel Crit with Esc/fatal; inspect marker completion and revalidation. Review the downloaded JAR/tag/notes, load the release in an isolated equivalent/intended client without duplicate addon versions, and verify the development command is absent. Record normal/master floor observations honestly.

**Expected:** Unrelated Noamm work completes; Crit yields while busy and resumes from actual state; final JAR loads with correct feature/category and no fixture. Record any genuinely observed floor applicability without extrapolating all-floor success.

**Why human:** Independent archive provenance passed, but real ActionUtils coexistence and final-JAR applied-client behavior remain unobserved. Existing startup evidence is from an earlier development snapshot.

### Gaps Summary

No observed FAILED must-have, missing/stub artifact, unwired key link, or blocker debt marker was found. The known review defects have code-level fixes and relevant automated regressions. Four roadmap behavior truths remain UNCERTAIN, so the phase cannot receive `passed` or an unconditional claim that its player outcome was achieved. Human verification/decision is required; publication and automated verification are independently established.

Later phases were inspected for deferred scope: travel (2), room combat (3), exact secrets (4), Blood deadlines/visibility (5) and automatic/post-camp behavior (6) are explicit later work, not Phase 1 gaps. No current failed truth was deferred or removed from the score. This report makes no claim of all-floor live success.

---

_Verified: 2026-11-13T09:23:00Z_  
_Verifier: /root/verify_sol (gsd-verifier)_
