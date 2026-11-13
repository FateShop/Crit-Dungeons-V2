# Roadmap: Crit Addons

**Status:** Approved by user — Phase 1 planned and ready to execute
**Granularity:** standard
**Structure:** Vertical MVP; all 53 approved v1 requirements retained.

## Phases

- [ ] **Phase 1: Live Map and Safe Task Control** - Inspect the real dungeon map and control a coordinated task session.
- [ ] **Phase 2: Time-Optimized Room Travel** - Queue reliable room navigation with verified movement caching, confirmed loadouts and key-progression combat.
- [ ] **Phase 3: Mob-Only Room Clearing** - Queue room clears using the existing travel, loadout and combat services.
- [ ] **Phase 4: Proven Secret Collection** - Queue physically executed, exact room secret routes with visible proof and verification.
- [ ] **Phase 5: Floor-Aware Blood Camp** - Complete adjacent-room preparation and timed Blood combat.
- [ ] **Phase 6: Complete Auto Clear and Blood Rush** - Run either automatic workflow through score-gated portal entry on every supported floor.

## Delivery and Ownership Contract

Each phase delivers a usable player workflow, rather than a horizontal technical layer. Later phases consume earlier capabilities without duplicating requirement ownership. Every applicable requirement remains binding on its consumers.

The implementation agents below are distinct from each other and from all six completed feature researchers. At execution, record actual agent identities and owned files before edits. The shared coordinator owner alone changes FIFO, resource leases, cancellation and lifecycle contracts; consumers request coordinated changes rather than recreating services. No historical custom projects may be inspected without new permission.

| Responsibility | Implementation owner | Primary phases |
| --- | --- | --- |
| Shared coordinator, lifecycle, diagnostics and release integration | `impl-coordinator` | 1–6 |
| Interactive map | `impl-map` | 1–4 |
| Movement and verified transition cache | `impl-movement` | 2; integration thereafter |
| Loadouts and combat-resource adapters | `impl-loadouts` | 2; integration thereafter |
| Exact secrets and proof cache | `impl-secrets` | 4; preparation integration in 5 |
| Blood Camp | `impl-blood` | 5; integration in 6 |
| Auto Clear / Blood Rush and shared combat policy | `impl-clear-rush` | progression combat in 2; room clearing in 3; automatic modes in 6 |

Reuse the exact Noamm 1.3.5 cheat artifact and pinned compatible source; private functionality uses narrow bridges. All features/settings live under Crit Addons and use existing Noamm notifications, score, map, loadout logic and Blood timing. Heavy search uses cancellable snapshots without blocking the client or violating world/network thread requirements.

Every completed feature or fix receives its own semantic tag, GitHub Release, compiled downloadable Minecraft 26.2 JAR and additions/changes/removals notes. Start with v0.1.0 for the first completed feature; later versions follow actual completed increments, not phase numbers. Setup/docs and incomplete features do not trigger releases. Record source/version/JAR provenance and verification evidence, explicitly disclosing unperformed live tests. A hard unmet requirement prevents calling the affected feature complete. Phase 1 releases a live map viewer/session-control increment, not the complete interactive command map; its release notes must identify room actions as subsequent work. Each released capability must satisfy its applicable floor scope; Phase 6 validates the combined workflows rather than postponing all-floor support for earlier releases. The user approved this roadmap. Planning commits are authorized; completed features/fixes still require the release checks above.

## Phase Details

### Phase 1: Live Map and Safe Task Control
**Goal:** As a dungeon player using Crit Addons, I want to inspect the live dungeon map and safely control a coordinated task session, so that I can understand the dungeon and retain control over automation.
**Mode:** mvp
**Depends on:** Nothing (first phase)
**Requirements:** CORE-02, CORE-03, CORE-04, CORE-05, MAP-01, MAP-02, TASK-02, TASK-03, TASK-04, TASK-05, TASK-07, DIAG-03, REL-01, REL-02
**Success Criteria**:
1. Player opens the configurable default-M popup, sees only actual Noamm-known room identities/layout, and sees discovery updates without fabricated rooms.
2. Player finds new controls under Crit Addons; missing required Noamm settings produce existing notifications naming the required settings.
3. In a controlled coordinated task session, Esc clears work without spontaneous restart; GUI/death pauses preserve objectives and resume according to explicitly agreed rules, while ordinary manual movement does not cancel.
4. Disconnect, dungeon exit, run end and boss entry clear Crit state; simultaneous resource requests cannot compete for movement, aim, hotbar, GUI or combat, and unrelated Noamm work continues.
5. The completed map/control feature is installable from its verified v0.1.0 release JAR with source/version evidence and honest validation notes; distinct research/implementation ownership and reuse evidence are reviewable.
**Planning gates:** Phase 1 rearming and popup/GUI pause semantics are resolved by 01-CONTEXT.md decisions D-01–D-13. Record explicit decisions for Blood GUI deadlines, impossible deadlines, no-LOS secret feasibility and Auto Clear post-camp behavior before their affected implementation; ownership of DIAG-03 here does not permit silently defaulting later gates. A controlled task session validates coordinator behavior; it is not completion of travel, combat or secrets.
**Plans:** 6 plans in 5 waves

**Wave 1**
- [ ] 01-01-PLAN.md — Ownership, harness, persistent settings and live map skeleton.

**Wave 2 *(blocked on Wave 1 completion)***
- [ ] 01-02-PLAN.md — Coordinated session invariants and resource ownership.
- [ ] 01-03-PLAN.md — Map prerequisites, layout and binding edge cases.

**Wave 3 *(blocked on Wave 2 completion)***
- [ ] 01-04-PLAN.md — Physical input, lifecycle and scoped client integration.

**Wave 4 *(blocked on Wave 3 completion)***
- [ ] 01-06-PLAN.md — Isolated production-backed development control fixture.

**Wave 5 *(blocked on Wave 4 completion)***
- [ ] 01-05-PLAN.md — Acceptance evidence and verified v0.1.0 delivery.
**UI hint:** yes

### Phase 2: Time-Optimized Room Travel
**Goal:** Player can queue room travel that minimizes modeled completion time, equips required Noamm loadouts, handles key-progression combat and recovers from actual movement failures.
**Mode:** mvp
**Depends on:** Phase 1
**Requirements:** MAP-03, TASK-01, NAV-01, NAV-02, NAV-03, NAV-04, NAV-05, NAV-06, NAV-07, NAV-08, LOAD-01, LOAD-02, LOAD-03, LOAD-04, DIAG-01
**Success Criteria**:
1. Left-clicking several known rooms leaves the popup open and executes travel FIFO to each fastest safely reachable interior position without replacing active work; the coordinator also supports reserving an entire automatic run ahead of manual work.
2. Travel compares all permitted movement methods using aiming/switching/interaction costs. Key-progression combat acquires required keys and opens doors normally; player-configured LCM/RCM slots 1–9 reuse Noamm selection with confirmed application, reliable identity-based item detection and fatal missing-resource notifications.
3. Etherwarp uses Noamm targeting geometry, crouched departure/intermediate/final eyes and valid landing surfaces within 61 blocks; item use, orientation, sneak and the shared Dark Claymore attack/swing primitive use supported 26.2 networking with sequencing/cooldowns rather than primary mouse simulation.
4. First-use transitions receive server-confirmed physical verification; valid cached sequences run without mandatory per-hop arrival waits across differing ping, while changed geometry/capabilities/targeting or relevant state repairs or invalidates affected entries.
5. Failed movement replans from actual state with bounded recovery while preserving remaining work; an unreachable objective stops without false completion and produces the requested floor/room/pose/routes/aim/edges/collision/server/geometry/cache/recovery/timestamp report.
**Planning gates:** Define and validate hypothetical-position targeting versus live crouch/ZeroPingCamera behavior, timing calibration, server observations and packet sequencing. Empirical cache success never authorizes unlimited packet frequency or guarantees future acceptance. Loadout and required miniboss/progression combat must work here before NAV-02 can be complete; Phase 3 reuses these services for whole-room mob-clear policy. Confirm loadout application through actual observations rather than menu closure.
**Plans:** TBD
**UI hint:** yes

### Phase 3: Mob-Only Room Clearing
**Goal:** Player can request a mob-only room clear that applies and confirms the correct Noamm loadout before combat.
**Mode:** mvp
**Depends on:** Phase 2
**Requirements:** MAP-04, RUN-03
**Success Criteria**:
1. Ctrl-left queues travel and a mob-only clear without collecting secrets; completion enemies, progression blockers and attackers are targeted while unrelated mobs are avoided.
2. Player configures LCM/RCM values only from 1–9; required switching opens `/loadout`, reuses Noamm selection and confirms application before normal RCM or designated miniboss/LCM combat resumes.
3. AOTV with Etherwarp, every supported Wither Blade, Dark Claymore and Gyro are recognized by reliable item identifiers across hotbar changes and selected loadouts.
4. Missing items, essential mana, rejected loadouts or unavailable mandatory Gravity Storm stop all automation with an explanatory Noamm notification; closing a menu alone never counts as successful application.
5. Teammate-completed rooms remove unnecessary combat objectives and update routing without disrupting the coordinated FIFO or lifecycle behavior.
**Planning gates:** Establish observed loadout success/rejection evidence on the exact dependency and live mechanics before dependent combat; never substitute an independent equipment manager or weaker fallback.
**Plans:** TBD
**UI hint:** yes

### Phase 4: Proven Secret Collection
**Goal:** Player can queue complete physical room secret collection only after an exact modeled route is proven and its first-use movement is verified.
**Mode:** mvp
**Depends on:** Phase 2, Phase 3
**Requirements:** MAP-05, SEC-01, SEC-02, SEC-03, SEC-04, SEC-05, SEC-06, SEC-07, DIAG-02
**Success Criteria**:
1. Shift-right queues physical collection of all room secret types and prerequisites through existing Noamm data/interactions; ordinary secret execution waits for a proven optimum rather than executing an approximation.
2. Player can inspect a finite graph/model and proof of minimum predicted completion time for every valid doorway entry/exit pairing, including all permitted movement methods, resources, interaction/prerequisite costs and room transforms.
3. Missing relevant geometry/connectivity/targeting is shown explicitly and prevents an optimality claim; permitted physical Etherwarp exploration gathers data, and optimization/verification accounts for the actual changed starting state.
4. Responsive progress distinguishes exploration, graph construction, evaluated states, missing data, optimization, proof, empirical verification and execution; Esc cancels computation and physical work.
5. Proven routes physically verify first-use transitions and persist validity/proof context, doorway pairing, costs, sequence, destinations and yaw/pitch; failures change the graph and recalculate, while teammate-collected interactions are skipped without changing route geometry/order.
**Planning gates:** Agree the finite graph boundary, candidate coverage, doorway endpoints, timing assumptions, resource state and proof certificate before implementation. Unknown relevant possibilities cannot be excluded merely to finish search; proof is relative to the declared complete model, not unpredictable server latency.
**Plans:** TBD
**UI hint:** yes

### Phase 5: Floor-Aware Blood Camp
**Goal:** Player can complete no-LOS adjacent-room preparation and timed Gravity Storm/LCM Blood Camp using Noamm signals.
**Mode:** mvp
**Depends on:** Phase 3, Phase 4
**Requirements:** BLOOD-01, BLOOD-02, BLOOD-03, BLOOD-04, BLOOD-05, BLOOD-06
**Success Criteria**:
1. After triggering the Watcher, preparation physically collects adjacent-room secrets while maintaining no line of sight into Blood; impossible reachable no-LOS preparation stops and reports evidence.
2. Player sees the reused Noamm counter/timer and its reliability limits; at one second remaining unfinished secrets suspend immediately and movement prioritizes the center of the Blood doorway.
3. Mandatory Gravity Storm targets the appropriate Blood center at one second remaining or doorway arrival; missing availability stops the run rather than continuing without Gyro.
4. Noamm's actual Kill indication starts packet-based Dark Claymore LCM against the initial group and subsequent spawns with existing tracking/timing, without an independent approximation clock.
5. The sequence uses floor-specific doorway geometry, Blood targets, spawns and timing across F1–F7/M1–M7; observed mechanics bound any dialogue-skip claim.
**Planning gates:** Resolve GUI/popup pauses versus deadlines, impossible-deadline failure behavior and no-LOS feasibility for secret interactions before implementation. Validate Noamm timing/Kill reliability and floor variation; do not promise impossible one-second arrival or weaken visibility constraints. This phase supplies a coordinated camp task; automatic mode/score-to-portal completion belongs to Phase 6.
**Plans:** TBD
**UI hint:** yes

### Phase 6: Complete Auto Clear and Blood Rush
**Goal:** Player can enable either complete automatic dungeon workflow on all supported floors and enter the portal when Blood and score conditions hold.
**Mode:** mvp
**Depends on:** Phase 1, Phase 2, Phase 3, Phase 4, Phase 5
**Requirements:** CORE-01, TASK-06, RUN-01, RUN-02, RUN-04, RUN-05, RUN-06
**Success Criteria**:
1. On Minecraft 26.2 with Noamm 1.3.5 cheat, either enabled mode starts in a valid F1–F7/M1–M7 dungeon; enabling the other cleans up and replaces it without overlap, and manual FIFO work waits behind the complete automatic run.
2. Both modes prioritize Blood; Blood Rush clears every required eligible route room before progression even through already-open doors, while Auto Clear skips puzzle/trap/fairy/non-combat objectives but can traverse them.
3. Auto Clear's first Blood visit triggers the Watcher, returns immediately to the adjacent room, collects its secrets and stays for preparation/camping; it does not leave for other clears during preparation or farm additional secrets solely for score.
4. Player edits thresholds with defaults 270 on F1–F3/M1–M3 and 300 on F4–F7/M4–M7; both modes use Noamm score/Blood state to enter immediately when eligible, abandon unnecessary clears, and wait directly before the portal when Blood is complete, required clears are exhausted and score is insufficient.
5. End-to-end all-floor evidence exercises movement, secrets, loadouts, camp, recovery and lifecycle together; boss entry clears all tasks including queued manual work and never starts boss combat.
**Planning gates:** Explicitly resolve Auto Clear's post-camp transition to remaining eligible combat rooms while score is insufficient, preserving immediate eligible portal entry and no-extra-secret rules. Validate all-floor mechanics with floor-specific evidence; unsupported behavior is unfinished scope, not deferred or a universal-position assumption.
**Plans:** TBD
**UI hint:** yes

## Progress

| Phase | Plans Complete | Status | Completed |
| --- | --- | --- | --- |
| 1. Live Map and Safe Task Control | 0/6 | Ready to execute | - |
| 2. Time-Optimized Room Travel | 0/TBD | Not started | - |
| 3. Mob-Only Room Clearing | 0/TBD | Not started | - |
| 4. Proven Secret Collection | 0/TBD | Not started | - |
| 5. Floor-Aware Blood Camp | 0/TBD | Not started | - |
| 6. Complete Auto Clear and Blood Rush | 0/TBD | Not started | - |

**Coverage:** 53/53 approved v1 requirements mapped exactly once; no deferrals. Requirements, Vertical MVP structure and this roadmap are explicitly approved by the user.
