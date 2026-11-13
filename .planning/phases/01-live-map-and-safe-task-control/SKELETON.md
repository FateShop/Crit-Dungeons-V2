# Walking Skeleton — Crit Addons

**Phase:** 1
**Generated:** 2026-11-12
**Status:** Planned; no implementation or runtime verification implied.

## Capability Proven End-to-End

The planned skeleton lets a dungeon player change a real Noamm-persisted Popup Key under Crit Addons, then use that key to open a native screen rendering Noamm's current real dungeon map through the compiled 26.2 addon.

## Architectural Decisions

| Decision | Choice | Rationale |
|---|---|---|
| Framework | Existing Kotlin/Fabric/Stonecutter template, JDK 25, Minecraft 26.2 | Approved target; no new scaffold or web framework |
| Data layer | Existing Noamm feature/settings persistence; live Noamm scanner and map model | Real configuration read/write and real map state; no database/server is needed |
| Auth | Existing Minecraft/platform authentication; existing authorized GitHub route for release | Addon adds no authentication system |
| Deployment | Target-only local build and exact Prism instance/dev client; completed increment published as v0.1.0 JAR | Real client execution and installable delivery |
| Directory layout | Current package root retained; features/impl/critaddons for map; crit/control for pure shared orchestration; mixins for narrow observers | Avoid gratuitous migration and duplicate systems |
| Integration | Public Noamm 1.3.5 cheat APIs, pinned source and installed artifact ABI | Preserve exact intended dependency behavior |
| Shared execution | One coordinator owner, ephemeral epochs/generations/leases and composite pauses | Every later consumer reuses the same control contract |
| Controlled live validation | Isolated devControl source set; :26.2:runControlFixture and /crit-control-fixture commands | Actual production coordinator/adapters with bounded heartbeat effects; absent from production JAR |
| Noamm coexistence | Read-only ActionUtils queue/running/blocked snapshot under its existing lock | Crit yields without cancelling unrelated shared work; arbitrary direct writers require consumer audits |

## Stack Touched in Phase 1

- [ ] Existing scaffold/build extended with isolated dependency-free controlTest harness.
- [ ] Native routing equivalent: real Popup Key opens/closes a real Screen through Noamm Feature discovery.
- [ ] Persistence equivalent: actual Noamm-managed Popup Key read/write survives relaunch; no independent DB introduced.
- [ ] UI interaction calls actual public map renderer with live Noamm data.
- [ ] Local full-stack execution: select JDK 25, confirm `:26.2:` task inventory, run `.\gradlew.bat :26.2:controlTest :26.2:build`; launch the target dev client or verified intended instance and exercise the popup.
- [ ] Completed map/control increment is delivered through matching source/tag/release/downloaded JAR evidence.

These boxes track execution, not planning completion. No build, launch, install or live success is claimed here.

The development session uses `/crit-control-fixture start` to queue active probe-A and pending probe-B, `status` for guarded heartbeat/revalidation evidence, `fatal` and `selection-off`/`selection-on` for production stop/rearm API tests, `sentinel` for unrelated Noamm queue survival, and `reset` for owner-scoped cleanup. It observes actual input/screens/death/run events; it never simulates them as live proof. `.\gradlew.bat :26.2:verifyControlFixtureIsolation` validates the final artifact excludes its classes/resources and ordinary runs cannot expose commands. No gameplay movement/combat/room tasks are added.

## Out of Scope for This Phase

Executable room travel/clearing/secrets, loadout switching, movement optimization, secret proof/caches, Blood Camp, automatic Clear/Rush and boss combat are not Phase 1 capabilities. The first six remain binding in their approved later phases; boss combat remains excluded. Map commands stay visibly unavailable and never fake success. Phase 1 only establishes their safe shared control contract and controlled-session validation.

## Subsequent Slice Plan

- Phase 2: Time-optimized queued room travel with shared movement, confirmed loadouts and progression combat.
- Phase 3: Mob-only room clears consuming those services.
- Phase 4: Physically executed exact secret collection and proof/cache states.
- Phase 5: Floor-aware Blood Camp with separately resolved deadline/visibility gates.
- Phase 6: Complete automatic modes through score-gated portal entry, with separately resolved post-camp policy.

## Source Coverage Audit

| Source | Item | Plans | Status |
|---|---|---|---|
| GOAL | Inspect live map and safely control coordinated task session | 01–06 | COVERED |
| REQ | CORE-02 | 01,03,04,05 | COVERED |
| REQ | CORE-03 | 01,03,05 | COVERED |
| REQ | CORE-04 | 03,04,05 | COVERED |
| REQ | CORE-05 | 01,02,05 | COVERED |
| REQ | MAP-01 | 01,03,05 | COVERED |
| REQ | MAP-02 | 01,03,05 | COVERED |
| REQ | TASK-02 | 02,04,06,05 | COVERED |
| REQ | TASK-03 | 02,04,05 | COVERED |
| REQ | TASK-04 | 02,04,05 | COVERED |
| REQ | TASK-05 | 02,04,05 | COVERED |
| REQ | TASK-07 | 02,04,05 | COVERED |
| REQ | DIAG-03 | 02,04,05 | COVERED; later gates preserved |
| REQ | REL-01 | 05 | COVERED |
| REQ | REL-02 | 05 | COVERED |
| RESEARCH | Exact JAR hash/source/ABI; public map APIs; no copied systems | 01,03,04,05 | COVERED |
| RESEARCH | Noamm persistent keybind/category and named prerequisite notifications | 01,03 | COVERED |
| RESEARCH | Run/generation/stop latch, ephemeral queue, pauses and revalidation | 02,04,06 | COVERED |
| RESEARCH | Atomic leases, scoped cancellation, unrelated Noamm sentinel/yield | 02,04,05 | COVERED |
| RESEARCH | Raw RELEASE/motion/focus descriptors, source separation, bounded idle | 04,06 | COVERED |
| RESEARCH | Client-thread lifecycle marshalling and real run boundaries | 04 | COVERED |
| RESEARCH | Renderer mutation/returned-height semantics and balanced stacks | 01,03 | COVERED |
| RESEARCH | Dependency-free harness, mapped ABI checks and explicit live validation | 01–06 | COVERED |
| RESEARCH | JDK25 and authorized release route, exact source/JAR/download evidence | 01,05 | COVERED |
| CONTEXT | D-01, D-02, D-03, D-04, D-05 | 02,04,05 | COVERED |
| CONTEXT | D-06, D-07, D-08, D-09, D-10 | 02,04 | COVERED |
| CONTEXT | D-11, D-12 | 01,03,05 | COVERED |
| CONTEXT | D-13 | 03,04,05 | COVERED |

Later-phase product gates and scope exclusions are intentionally not implemented by these plans. No approved Phase 1 item is omitted.


## Plan Dependency Graph

01 → {02, 03} → 04 → 06 → 05. Wave 1 builds the map skeleton; Wave 2 runs coordinator and map refinement independently; Wave 3 binds production adapters; Wave 4 adds the isolated development fixture; Wave 5 validates and publishes the completed increment. Plan 06 owns only fixture/source-set/run/isolation work; Plan 04 remains production input/lifecycle/coexistence integration.
