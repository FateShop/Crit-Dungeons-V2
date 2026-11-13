# Requirements: Crit Addons

**Defined:** 2026-11-12
**Status:** User-approved v1 scope; no requirements are implemented or verified.
**Core Value:** Execute reliable, time-efficient dungeon tasks through existing Noamm functionality, without duplicating it or misrepresenting unknown map information, route optimality, or verification.

The detailed acceptance contract is PROJECT.md plus the three preserved user inputs. The requirements below index that contract; concise wording does not weaken its details. Later explicit user decisions govern refinements. All proposed v1 scope comes from the user; no features were silently deferred.

## v1 Requirements

### Integration

- [ ] **CORE-01**: Player can use Crit Addons on Minecraft 26.2 with NoammAddons 1.3.5 cheat across F1–F7 and M1–M7.
- [x] **CORE-02**: Every feature and partial implementation reuses applicable Noamm functionality, with exact-dependency evidence before anything is declared absent.
- [x] **CORE-03**: Player finds every new feature and setting under the Crit Addons tab in Noamm's menu.
- [x] **CORE-04**: Missing required Noamm features/settings produce existing Noamm toasts naming what the player must enable.
- [ ] **CORE-05**: Each of the six feature areas has distinct research and implementation agents, with explicit shared-service ownership and no inspection of older projects without new permission.

### Interactive map

- [x] **MAP-01**: Popup displays Noamm Cheater Map's actual available rooms, identities and layout, updating during the run without inventing unavailable information.
- [x] **MAP-02**: Player toggles the popup with configurable default M; issuing commands leaves it open.
- [ ] **MAP-03**: Left click queues travel to the fastest safely reachable position inside the selected room.
- [ ] **MAP-04**: Ctrl-left click queues only the selected room's mob-clear requirement.
- [ ] **MAP-05**: Shift-right click queues physical secret collection in the selected room.

### Task control and lifecycle

- [ ] **TASK-01**: Manual tasks execute FIFO without interrupting an active task; tasks queued during automatic modes wait behind the entire automatic run.
- [ ] **TASK-02**: Esc cancels current execution/calculation and clears queued tasks without an unexpected restart; ordinary manual movement does not cancel automation.
- [ ] **TASK-03**: Opening a GUI pauses movement/combat while preserving work; closing it resumes, except the loadout coordinator may close conflicting screens for a required switch.
- [ ] **TASK-04**: Death suspends and preserves work; revival reevaluates state, replans from the actual position and resumes.
- [ ] **TASK-05**: Disconnect, dungeon exit, run end and boss entry terminate automation, clear tasks and reset state without carrying work to another run.
- [ ] **TASK-06**: Enabling either automatic mode starts it in a valid dungeon; enabling the other immediately cleans up and replaces it without concurrent execution.
- [ ] **TASK-07**: Movement, aiming, hotbar, GUI and combat operations share exclusive coordinated ownership and cancellation without canceling unrelated Noamm work.

### Time-optimized movement

- [ ] **NAV-01**: Routes minimize predicted completion time over walking, jumping, Etherwarp, ordinary AOTV and Wither Impact, including aiming, switching and interactions, with natural controlled movement.
- [ ] **NAV-02**: Routing obtains necessary keys and opens Wither/Blood doors normally, including progression costs and confirmed accessibility.
- [ ] **NAV-03**: Etherwarp reuses Noamm Overlay geometry with a 61-block maximum and crouched eye height for every ray/aim/landing calculation, including slabs and intermediate hops.
- [ ] **NAV-04**: Etherwarp, Wither Impact and Dark Claymore actions use supported Minecraft 26.2 networking and correct item/orientation/input state, respecting sequencing and cooldowns rather than physical mouse simulation.
- [ ] **NAV-05**: Unverified transitions are physically tested with server-confirmed results; failed transitions update the graph and are never cached as verified.
- [ ] **NAV-06**: Valid verified cached sequences execute without mandatory per-hop confirmation waits while monitoring actual state and supporting variable ping.
- [ ] **NAV-07**: Geometry, targeting, capability and relevant player/server changes invalidate or repair affected cache data before unsafe reuse.
- [ ] **NAV-08**: Failed actions reevaluate actual state and replan before retrying, preserve remaining tasks during recovery and use bounded attempts instead of endless repetition.

### Exact secret routes

- [ ] **SEC-01**: Secret tasks physically perform all required secret types and prerequisites using existing Noamm data, transforms, detection and interactions wherever available.
- [ ] **SEC-02**: Optimizer proves minimum predicted completion time over a documented finite graph/model covering all permitted movement methods, interactions, resources and prerequisites; approximate results are never claimed or executed as proven optimal task routes.
- [ ] **SEC-03**: Every valid doorway entry/exit combination receives an optimal room route usable by broader traversal, with room-relative transforms and orientation handling.
- [ ] **SEC-04**: Exploration may physically Etherwarp to gather missing geometry/connectivity/targeting data during calculation; unresolved relevant movement possibilities prevent optimality claims.
- [ ] **SEC-05**: Player sees exploration, graph construction, evaluated states, optimization, verification and missing-information progress while the client remains responsive and Esc remains effective.
- [ ] **SEC-06**: Proven routes undergo first-use physical movement verification; persist proof/validity context, doorway pairing, costs, sequence, destinations, aiming/yaw/pitch and verified transitions for later reuse.
- [ ] **SEC-07**: Teammate-collected secrets skip their interactions while preserving the original route geometry and objective order.

### Loadouts and items

- [ ] **LOAD-01**: Player configures LCM and RCM loadout numbers 1–9; switching opens `/loadout` and reuses Noamm selection logic instead of a new equipment manager.
- [ ] **LOAD-02**: Required loadout application is confirmed before dependent combat; use LCM for miniboss/Blood/designated encounters and RCM for normal clearing.
- [ ] **LOAD-03**: Automatically identify Etherwarp AOTV, any Wither Blade, Dark Claymore and Gyro by reliable actual item identities rather than display names or fixed hotbar slots.
- [ ] **LOAD-04**: Missing required items, essential mana, rejected loadouts or unavailable mandatory Gravity Storm stop automation completely with an explanatory Noamm notification.

### Auto Clear and Blood Rush

- [ ] **RUN-01**: Both modes prioritize progression toward Blood; Blood Rush confirms every eligible required-route room cleared before progressing, including when a door is already open.
- [ ] **RUN-02**: Auto Clear handles eligible combat rooms, skipping puzzle/trap/fairy/non-combat objectives while allowing necessary traversal through those rooms.
- [ ] **RUN-03**: Combat targets completion enemies, progression blockers and hostiles actively attacking the player; teammate-cleared rooms remove unnecessary objectives and update routing.
- [ ] **RUN-04**: Auto Clear's first Blood visit triggers the Watcher, returns to the adjacent room for secrets and remains there for preparation before the Blood Camp sequence.
- [ ] **RUN-05**: Auto Clear and map clearing do not farm extra secrets for score; adjacent-room Blood preparation is the explicit exception. With Blood complete and no remaining clears, wait directly before the portal if score is insufficient.
- [ ] **RUN-06**: Both modes enter the portal as soon as Blood is complete and the configured score is met, abandoning unnecessary clears; thresholds are editable with defaults 270 for F1–F3/M1–M3 and 300 for F4–F7/M4–M7, using Noamm state/score.

### Blood Camp

- [ ] **BLOOD-01**: Preparation collects adjacent-room secrets while maintaining no line of sight into Blood; inability to find a valid reachable no-LOS position stops automation and reports the error.
- [ ] **BLOOD-02**: Reuse Noamm's Blood Camp counter/timer and actual Kill indication instead of a separate clock, explicitly preserving its reliability limitations.
- [ ] **BLOOD-03**: At one second remaining, suspend unfinished secrets immediately and prioritize Etherwarp to the center of the Blood doorway.
- [ ] **BLOOD-04**: Cast mandatory Gyro Gravity Storm at the floor-appropriate Blood center at one second remaining or doorway arrival; do not fall back to non-Gyro camping.
- [ ] **BLOOD-05**: On Noamm's Kill indication, execute Dark Claymore LCM combat against the initial group and handle subsequent spawns using existing tracking/timing where applicable.
- [ ] **BLOOD-06**: Positioning, targeting, spawn handling and integration account for every supported normal/master floor rather than a universal fixed geometry.

### Diagnostics and unresolved behavior

- [ ] **DIAG-01**: An objective unreachable after bounded replanning stops safely without skipping or claiming completion and writes a detailed investigation report with the state/route/geometry/cache/server-response fields specified by the user.
- [ ] **DIAG-02**: Diagnostic and progress state distinguishes unavailable geometry, exploration, proof status, empirical verification and actual execution, without claiming guarantees unsupported by evidence.
- [ ] **DIAG-03**: Before affected implementation, resolve rearming after Esc/fatal stops, map/GUI pause versus Blood deadlines, Auto Clear's post-camp transition, no-LOS secret feasibility and impossible-deadline behavior explicitly rather than silently weakening requirements.

### Releases

- [ ] **REL-01**: Every completed feature or bug fix has a matching semantic Git tag and GitHub Release starting at v0.1.0, a downloadable compiled 26.2 JAR and notes describing additions, changes and removals.
- [ ] **REL-02**: Release evidence ties the JAR to its source/version and completed-feature verification; planning/docs/incomplete work produce no individual release or automatic phase tag, and unperformed live tests are disclosed.

## v2 Requirements

None agreed. No requested v1 functionality has been silently moved to a later release.

## Out of Scope

| Feature or approach | Reason |
| --- | --- |
| Minecraft 26.3 support | Only 26.2 is currently requested. |
| Boss combat | Boss entry terminates automation and clears tasks. |
| Historical custom codebase inspection/reuse without permission | Explicit user prohibition. |
| Rebuilding existing Noamm functionality | Mandatory reuse includes partial implementations. |
| Invented unknown rooms or false optimality claims | Explicit accuracy and mathematical-proof constraints. |
| Puzzle/trap objective automation through Auto Clear | Special non-combat objectives are excluded. |
| Extra Auto Clear secrets solely for score | Wait for score; adjacent-room Blood preparation is the explicit exception. |
| Independent loadout/notification/score/Blood-clock engines | Existing Noamm systems must be reused. |
| Unverified cached execution or unlimited packet rate | Verification, state validation and sequencing remain mandatory. |

## Acceptance Criteria

- Each requirement has phase-specific observable acceptance checks and meaningful automated tests where appropriate, plus live checks for actual Minecraft/server mechanics.
- Exactness checks establish the declared graph/model scope and proof, including prerequisites and all doorway pairings; unsupported claims fail acceptance.
- Reuse evidence identifies the exact Noamm dependency behavior and any narrow bridge; tests alone do not establish a feature was absent.
- All-floor compatibility, recovery, lifecycle and inter-feature resource coordination are part of the requested scope, not implicit assumptions.

## Definition of Done

A completed feature/fix delivers the full agreed behavior through existing Noamm components, passes its required checks, records any unperformed live verification or unresolved limitations honestly, and has its matching versioned source/tag/compiled JAR/release notes. An unresolved hard requirement cannot be labeled completed by substituting an approximation.

## Traceability

Each approved v1 ID has exactly one owning phase. Later phases consume and exercise earlier capabilities without duplicating ownership. The six-phase roadmap is user-approved; no requirement is implemented or verified.

| Requirement | Phase | Status |
| --- | --- | --- |
| BLOOD-01 | Phase 5 | Pending |
| BLOOD-02 | Phase 5 | Pending |
| BLOOD-03 | Phase 5 | Pending |
| BLOOD-04 | Phase 5 | Pending |
| BLOOD-05 | Phase 5 | Pending |
| BLOOD-06 | Phase 5 | Pending |
| CORE-01 | Phase 6 | Pending |
| CORE-02 | Phase 1 | Complete |
| CORE-03 | Phase 1 | Complete |
| CORE-04 | Phase 1 | Complete |
| CORE-05 | Phase 1 | Pending |
| DIAG-01 | Phase 2 | Pending |
| DIAG-02 | Phase 4 | Pending |
| DIAG-03 | Phase 1 | Pending |
| LOAD-01 | Phase 2 | Pending |
| LOAD-02 | Phase 2 | Pending |
| LOAD-03 | Phase 2 | Pending |
| LOAD-04 | Phase 2 | Pending |
| MAP-01 | Phase 1 | Complete |
| MAP-02 | Phase 1 | Complete |
| MAP-03 | Phase 2 | Pending |
| MAP-04 | Phase 3 | Pending |
| MAP-05 | Phase 4 | Pending |
| NAV-01 | Phase 2 | Pending |
| NAV-02 | Phase 2 | Pending |
| NAV-03 | Phase 2 | Pending |
| NAV-04 | Phase 2 | Pending |
| NAV-05 | Phase 2 | Pending |
| NAV-06 | Phase 2 | Pending |
| NAV-07 | Phase 2 | Pending |
| NAV-08 | Phase 2 | Pending |
| REL-01 | Phase 1 | Pending |
| REL-02 | Phase 1 | Pending |
| RUN-01 | Phase 6 | Pending |
| RUN-02 | Phase 6 | Pending |
| RUN-03 | Phase 3 | Pending |
| RUN-04 | Phase 6 | Pending |
| RUN-05 | Phase 6 | Pending |
| RUN-06 | Phase 6 | Pending |
| SEC-01 | Phase 4 | Pending |
| SEC-02 | Phase 4 | Pending |
| SEC-03 | Phase 4 | Pending |
| SEC-04 | Phase 4 | Pending |
| SEC-05 | Phase 4 | Pending |
| SEC-06 | Phase 4 | Pending |
| SEC-07 | Phase 4 | Pending |
| TASK-01 | Phase 2 | Pending |
| TASK-02 | Phase 1 | Pending |
| TASK-03 | Phase 1 | Pending |
| TASK-04 | Phase 1 | Pending |
| TASK-05 | Phase 1 | Pending |
| TASK-06 | Phase 6 | Pending |
| TASK-07 | Phase 1 | Pending |

**Coverage:** 53/53 approved v1 requirements mapped exactly once; 0 unmapped; 0 duplicates. No scope deferred.

---
*Last updated: 2026-11-12 after user approval of the six-phase Vertical MVP roadmap mapping all 53 approved requirements.*
