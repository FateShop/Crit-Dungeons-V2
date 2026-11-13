# Crit Addons

## What This Is

Crit Addons is a modular Minecraft 26.2 dungeon automation addon for NoammAddons 1.3.5 cheat, developed in https://github.com/FateShop/Crit-Dungeons-V2. It adds an interactive dungeon map, time-optimized movement, exact cached secret routes, existing-system loadout switching, Auto Clear/Auto Blood Rush, and Blood Camp under a new **Crit Addons** tab in Noamm's menu.

It supports F1–F7 and M1–M7, automating dungeon traversal and eligible combat objectives through Blood portal entry. It stops at boss entry and does not automate boss fights. The user configures equipment through Noamm's existing loadout workflow.

## Core Value

Execute reliable, time-efficient dungeon tasks through existing Noamm functionality, without duplicating it or misrepresenting unknown map information, route optimality, or verification.

## Authoritative User Inputs

These files preserve the user's full submitted requirements, including details and examples that must not be silently simplified:

- [Initial design answers](inputs/01-development-requirements.md)
- [Navigation, optimization, and Blood Camp answers](inputs/02-additional-requirements.md)
- [Control, recovery, and verification answers](inputs/03-execution-requirements.md)

Later explicit answers refine earlier ones. Unresolved behavior must be surfaced before implementation choices weaken a requirement. The original chat additionally requires separate research and implementation agents for each of the six feature areas and extensive design questioning through GSD.

## Requirements

### Validated

None yet. The repository is an addon template; no Crit feature has been implemented, installed, or validated in game during this project initialization.

### Active

#### Integration and delivery

- [ ] Target Minecraft 26.2, JDK 25, and `NoammAddons-1.3.5-26.2-cheat.jar`; 26.3 support is not currently required.
- [ ] Reuse every applicable existing Noamm feature or partial implementation. Inspect the exact dependency before deciding functionality is absent; public source alone omits some cheat code.
- [ ] Put all new user-facing features and settings under **Crit Addons**, using the existing addon category and feature framework.
- [ ] Use Noamm notifications to name required features/settings that the player must enable; do not implement another toast system.
- [ ] Use a distinct researcher and a distinct implementation agent for each feature area: map, movement, secrets, loadouts, Clear/Rush, and Blood Camp. Coordinate shared contracts and file ownership; do not let agents independently recreate shared services.
- [ ] For every completed feature or bug fix, publish a semantic-version Git tag and GitHub Release with a compiled downloadable 26.2 JAR and notes describing additions, changes, and removals. Begin with `v0.1.0`; no release is required for planning, documentation, or incomplete work.

#### Interactive dungeon map

- [ ] Reuse Noamm Dungeon Map's Cheater Map discovery, identities, rendering, geometry, and updates; display all information that it actually knows without inventing unknown rooms or layouts.
- [ ] Provide a configurable popup toggle key, default **M**. Keep the popup open after issuing commands so multiple actions can be queued.
- [ ] Left click queues navigation to the fastest safely reachable position inside the selected room; Ctrl-left queues mob-clear only; Shift-right queues secret collection.
- [ ] Execute map tasks FIFO. New tasks do not replace an active task. During Auto Clear/Blood Rush, manual tasks wait behind the entire automatic run.
- [ ] Boss entry discards all pending tasks, including manual tasks waiting behind an automatic run.

#### Movement and Etherwarp

- [ ] Optimize predicted completion time, including aiming, movement, item switching, interactions, door/key progression, and realistic execution costs; do not optimize solely distance or hop count.
- [ ] Support walking, jumping, Etherwarp, ordinary AOTV teleportation, and Wither Impact through a shared planner/executor. Keep movement natural and controlled.
- [ ] Obtain required progression keys and open Wither/Blood doors normally; account for those costs and state transitions.
- [ ] Identify Etherwarp-enabled AOTV by actual item identity; use a maximum Etherwarp range of 61 blocks and support differing ping conditions without hardcoding the user's latency.
- [ ] Reuse Noamm Etherwarp Overlay targeting and validity geometry. Apply crouched eye height to departure, intermediate and final raycasts and aiming, including slabs and other landing surfaces.
- [ ] Use supported Minecraft 26.2 networking interfaces for Etherwarp item use, orientation and sneak state; Wither Impact item use; and Dark Claymore attack/swing. Physical mouse simulation is not the primary action mechanism. Respect sequencing, cooldowns, and server state validation.
- [ ] Verify unverified transitions through server-confirmed execution, recording successful movement and aim information.
- [ ] Execute verified cached Etherwarp sequences without a mandatory individual arrival-confirmation wait after each hop. Still monitor actual state, respect valid sequencing, and repair or invalidate routes when geometry, capabilities, or relevant server/player conditions change.
- [ ] On failure, inspect actual state and recalculate before retrying. Bound recovery; do not endlessly repeat failed actions or cache failed transitions as verified.

#### Exact secret routes

- [ ] Physically collect all secret types and prerequisites, including chests, items, bats, levers and room-specific mechanics, through existing Noamm components wherever available.
- [ ] Prove minimum predicted completion time within an explicitly defined movement graph and timing model that includes all permitted movement methods and interaction/prerequisite costs.
- [ ] Use exact search, dynamic programming, branch-and-bound or another proof-producing method; exhaustive permutation enumeration is not required. Do not claim an approximate result is optimal.
- [ ] Resolve relevant unknown geometry/connectivity before claiming optimality. Represent the graph boundary, costs, assumptions and proof status explicitly; do not imply a guarantee over unpredictable real server latency.
- [ ] Consider every valid doorway as both entry and exit, including all valid entry/exit combinations. Cache optimal room routes for those combinations and let broader traversal select among them.
- [ ] Distinguish exploration, optimization, verification, and execution in visible progress. Display room, graph/status information, evaluated states, missing data and verification progress. Esc cancels computation and execution.
- [ ] Ordinary route execution waits for a proven optimum. Physical Etherwarp exploration to acquire missing world/collision/targeting/connectivity information is explicitly permitted while calculating; it is not approximate secret execution.
- [ ] Physically test first-use movement transitions; failed transitions update the graph and require recalculation. Account for changed starting state after exploration and verification.
- [ ] Persist room identity/orientation, doorway pairing, movement sequence, teleport/aim coordinates, yaw/pitch, verified transitions, predicted costs, and relevant geometry/targeting validity. Apply room transforms and invalidate stale data.
- [ ] Preserve route geometry and objective order when teammates collect secrets. Skip interactions for secrets confirmed already collected without recalculating the whole route.

#### Loadouts and combat resources

- [ ] Reuse Noamm's `/loadout` menu handling and selection logic; provide **LCM Loadout** and **RCM Loadout** settings restricted to 1–9. Do not build an independent armor/equipment/pet manager.
- [ ] Open `/loadout`, select the configured loadout through existing logic, and confirm application before dependent combat resumes. Do not treat merely closing a menu as proof that gear applied.
- [ ] Use LCM for minibosses, Blood and other explicitly designated LCM encounters; RCM for normal mob clearing. Boss automation remains excluded even if a loadout rule mentions boss contexts in earlier messages.
- [ ] Identify AOTV, any supported Wither Blade (Hyperion, Astraea, Scylla, Valkyrie), Dark Claymore and Gyrokinetic Wand using reliable item identifiers, coordinated with the selected loadout.
- [ ] Stop automation completely with an explanatory Noamm notification when required items, essential mana, Gravity Storm, or the configured loadout are unavailable or rejected. Do not silently substitute a weaker action.

#### Auto Clear and Auto Blood Rush

- [ ] Start automatically when a mode is enabled and dungeon state is valid. Enabling the other mode immediately cleans up and replaces the active mode; never execute both concurrently.
- [ ] Prioritize Blood progression. Blood Rush clears every required combat room on its route before progressing, even if a door is open, then performs Blood Camp.
- [ ] Auto Clear handles eligible normal combat rooms, skips objectives in puzzles/traps/fairy and other non-combat special rooms, but may traverse those rooms when needed.
- [ ] Target room-completion enemies, progression blockers, and hostile enemies actively attacking the player; avoid unrelated mobs. Detect teammate-completed rooms, remove unnecessary clear objectives and replan as appropriate.
- [ ] On its first Blood visit, Auto Clear triggers the Watcher, returns to the room immediately before Blood, collects that room's secrets during preparation, and performs Blood Camp. Do not leave for other clearing during this wait.
- [ ] Auto Clear does not collect additional secrets solely to raise score; the adjacent-room Blood preparation activity is the explicit exception. Map Clear Room remains mob-only.
- [ ] Enter the portal as soon as Blood is complete and the configured score threshold is met, abandoning unnecessary remaining clears. If no required clears remain and score is insufficient, wait directly in front of the portal; Blood Rush also waits for sufficient score.
- [ ] Expose editable score thresholds, default 270 for F1–F3/M1–M3 and 300 for F4–F7/M4–M7. Reuse Noamm's score and Blood-completion state.

#### Blood Camp

- [ ] Trigger the Watcher and remain in the adjacent room, collecting its secrets while maintaining no line of sight into Blood before the doorway phase. If no reachable position satisfies the requirement, stop and report an error; do not weaken it to partial visibility.
- [ ] Reuse Noamm's existing Blood Camp counter/timer and actual Kill indication, not an independent elapsed-time approximation. Explain any reliability limits of that dependency.
- [ ] At one second remaining, immediately suspend unfinished secrets and prioritize Etherwarping to the center of the Blood doorway, not room center.
- [ ] Cast Gyrokinetic Wand **Gravity Storm**, targeting the Blood room's appropriate center, at one second remaining or on arrival at the doorway. Gyro is mandatory; do not continue without it.
- [ ] Start Dark Claymore packet-based LCM attacks on Noamm's Kill indication. Kill the initial group and subsequent spawns, reusing Noamm's tracking/timing components where applicable.
- [ ] Account for floor-specific geometry, spawn and timing differences across normal/master floors. Do not assume universal fixed positions or guarantee dialogue skipping beyond what observed mechanics support.
- [ ] Enter the portal only when both Blood completion and configured score are satisfied; stop and clear state on boss entry.

#### Control, recovery and diagnostics

- [ ] Esc is the emergency user cancellation control: stop the active work/calculation, clear queued tasks, and prevent unexpected execution. Manual movement and ordinary interactions do not cancel.
- [ ] Mode toggles may explicitly replace modes. Automatic lifecycle termination and fatal errors are distinct from ordinary user input cancellation.
- [ ] Opening a GUI pauses movement and combat while preserving objectives, queues, path/progress state; closing it resumes. A required loadout operation may close the conflicting GUI and own the menu interaction until completion.
- [ ] Death suspends automation and preserves work. On revival reevaluate state, recalculate from the new position and resume.
- [ ] Disconnect, dungeon exit, run end and boss entry terminate automation, clear tasks and reset execution state. Do not carry tasks into later runs.
- [ ] Unreachable objectives after bounded recalculation stop failed execution and generate a detailed diagnostic report; do not skip or mark them complete. Record floor, room identity/transform/coordinates, player pose, goal, attempted routes, Etherwarp targets/yaw/pitch, failed edges, collision/targeting data, relevant server responses, loaded/unloaded geometry, cache status, recovery counts, reason and timestamps where available.

### Out of Scope

- Minecraft 26.3 support — the user currently requires only 26.2.
- Automated boss combat — automation terminates at boss entry.
- Remaking existing Noamm features or partial systems — reuse is mandatory.
- Inspecting, referencing, copying or reusing historical custom projects — forbidden without new explicit permission, including older Crit Addons and Etherwarp implementations.
- Invented room identities or unavailable layouts — use Noamm Cheater Map's actual information.
- Approximate secret routes presented or executed as exact optimal task routes — exploration and first-use verification are separate permitted activities.
- Independent loadout equipment management, toast delivery, score calculation or Blood Camp clock — reuse the existing systems.
- Auto-solving puzzles/trap objectives as part of Auto Clear; extra secret collection to raise score — excluded except the specified adjacent-room Blood preparation.
- Planning/documentation-only releases and incomplete-feature releases — completed features/fixes receive releases.

## Context and Reuse Evidence

The current repo began at template commit `4b6ea4618041eac6e51096d01a39a05876e92293` on `main`. Its Kotlin/Fabric/Stonecutter build already declares Noamm 1.3.5. Six separate read-only feature researchers inspected the current project, public Noamm source and/or the installed exact cheat artifact. No previous custom codebase was examined.

The public Noamm release tag `1.3.5` points to `460cfaf5d8760fa1146f5d2433715b7eca9bf319`; source HEAD `24fab30d1603d47062c8db968e02e491d324d408` differs in some utilities. Use the release tag and installed binary for compatibility contracts. The public tree declares a cheat source set without publishing all its code, so absence in public source is not proof of absence in the installed artifact.

| Existing Noamm component | Reuse and integration consequence |
| --- | --- |
| `MapRenderer`, `MapLeapRenderer`, `MapConfig` | Existing map drawing, interactive presentation patterns and Cheater Map settings; add room actions around them. |
| `DungeonScanner`, `UniqueRoom`, `ScanUtils`, `MapUtils` | Room/door discovery, identities, orientation, secret coordinates and map/world transforms; no independent dungeon scanner. |
| `DungeonTree.getBloodRush()` | Existing blood-route topology; its cached BFS is not a timed movement or combat optimizer, and scanning may still be incomplete. |
| `EtherwarpHelper`, `TeleportUtils`, Etherwarp feature | Existing geometry, item range, eligibility and targeting. In pinned 1.3.5, hypothetical-position calls can be overridden by ZeroPingCamera prediction and use live crouch state; use a narrow compatible bridge rather than copying the raycaster or naively expanding graph edges through this API. |
| `PlayerUtils`, packet events, networking compatibility helpers | Existing input/rotation/item primitives and authoritative observations. Inspect actual packet behavior before reusing a high-level click helper for the user's packet requirement. |
| `RoomData`, secret events, DungeonWaypoints, installed `Secrets` | Existing secret data, transforms, completion detection and cheat interactions. Audit exact interaction functionality before adding any replacement. |
| `PogObject` | Existing typed persistent storage available for route caches. |
| `LoadoutKeybinds`, command/menu utilities | Existing menu selection and eligibility logic; a minimal invoker can expose private `click(index)`. It returns no success acknowledgement, so application confirmation needs coordination and live validation. |
| `StarMobESP`, `DoorKeys`, door/key listener state | Existing required-enemy/miniboss classification, key tracking and door state; private data may require narrow accessors. |
| Installed `BloodCamp` | Existing blood mob/spawn tracking, countdown, Kill timing and triggerbot. Private timing/target state needs an accessor. Triggerbot alone does not provide the whole requested camp sequence and pauses for GUIs. |
| `GyroHelper`, `MageBeam` | These provide targeting/rendering assistance, not a complete automatic Gyro cast or sustained LCM executor. |
| `DungeonListener`, `ScoreTracker`, dungeon/lifecycle events | Existing Blood completion, score, floor, room and boss/run transitions. Score is calculated by Noamm; do not claim a separate server-authoritative score acknowledgement. |
| `NotificationManager.push` | Existing notifications and deduplication; use exact dependency feature/setting names. |
| `ActionUtils` | Existing scheduling can be reused, but its priority queue does not guarantee FIFO task ordering. Crit needs a coordinated FIFO above it and must not cancel unrelated Noamm actions globally. |

## Constraints

- **Compatibility:** Minecraft 26.2, NoammAddons 1.3.5 cheat, JDK 25. Installed instance: `C:\Users\Michael\AppData\Roaming\PrismLauncher\instances\26.2`; installed dependency is under its `minecraft\mods` folder.
- **Repository:** `https://github.com/FateShop/Crit-Dungeons-V2`; source changes and releases belong here.
- **Reuse:** New orchestration, optimizers, adapters and action coordination must build on verified Noamm functionality. Favor minimal version-specific bridges where existing useful logic is private.
- **Truthfulness:** Exact optimality is relative to a complete defined graph/model. Unknown relevant possibilities prevent the claim. Cached empirical success is not proof of future server acceptance or unrestricted packet frequency.
- **Responsiveness:** Heavy optimization must preserve client responsiveness, visible progress and Esc cancellation; client/world access and networking must obey thread constraints.
- **Delivery:** Release a working installable JAR with each completed feature/fix; documentation commits are exempt. GitHub CLI was unauthenticated during initial inspection; establish an authorized release-capable route before publishing, without claiming publication until verified.
- **Validation:** No game launch, dependency execution, new JAR installation, build or live dungeon test has occurred in this initialization. Live mechanics and loadout acknowledgements remain to be validated.

## Open Design Decisions and Feasibility Gates

1. **Rearming after Esc/fatal errors:** Modes start when enabled, but cancellation must not immediately restart them on the next tick. Define explicit rearm semantics before coordinator execution; do not silently choose them.
2. **Popup/GUI pause versus Blood deadlines:** The user's map popup is a GUI and GUI pauses movement/combat, while Blood requires immediate one-second positioning. Resolve popup treatment and deadline behavior without silently overriding either rule.
3. **Auto Clear after camping:** Specify the transition to remaining eligible combat rooms after Blood Camp when score is insufficient; portal entry still preempts unnecessary clearing once both conditions hold.
4. **Deadline feasibility:** First-use exact secret optimization, verification/exploration, line-of-sight constraints, GUI pauses and distant movement may prevent the one-second doorway schedule. Establish observable failure behavior rather than guaranteeing physically impossible timing.
5. **Exactness boundary:** Define a finite graph, legal action/candidate coverage, doorway endpoint representation, prerequisites, ability resources, timing calibration, unknown-data handling and proof certificate. Never assert an optimum over movement possibilities excluded without disclosure.
6. **Blood waiting-room visibility:** Secret collection itself must preserve the no-line-of-sight requirement. Determine whether each relevant room permits the requested actions under that constraint; stop with evidence when not feasible.
7. **Authoritative integration:** Validate loadout success/rejection observations, cached zero-ping sequencing and recovery, floor-specific Blood Kill behavior, and private API bridges against the exact dependency and live mechanics.

## Key Decisions

| Decision | Rationale | Outcome |
| --- | --- | --- |
| Reuse Noamm; do not inspect older projects | Explicit user boundary and integration requirement | Locked by user |
| Six distinct feature researchers and separate feature implementers | User-requested ownership and research discipline | Initial research complete; implementation pending |
| Exact secret optimum over defined movement/timing graph | Guarantees can be stated precisely without claiming control over live latency | User approved; model pending |
| All doorway entry/exit combinations with persistent verified caches | Dungeon traversal needs more than one fixed room route | User approved |
| Confirm first-use transitions; omit mandatory per-hop waits on valid cached execution | User's zero-ping-style execution requirement | User approved; mechanics validation pending |
| FIFO manual tasks wait behind entire automatic run; boss entry clears them | Explicit task and lifecycle ordering | User approved |
| Auto Clear does not farm extra secrets for score | User separates clear objectives and secret objectives, except Blood staging | User approved |
| Gravity Storm mandatory; missing resources stop rather than degrade | Explicit required Blood Camp and failure behavior | User approved |
| Semantic releases per completed feature/fix | Every finished increment needs an installable, attributable JAR | User approved; release setup pending |

## Evolution

This document evolves at phase transitions and milestone boundaries.

After each phase transition:
1. Move invalidated requirements to Out of Scope with a reason.
2. Move shipped, verified requirements to Validated with phase evidence.
3. Add newly agreed requirements to Active.
4. Record consequential decisions and revisit assumptions against current Noamm behavior.
5. Check that What This Is still describes the actual addon.

After each milestone:
1. Review every section and the authoritative user inputs.
2. Check that Core Value still represents the user's priority.
3. Reevaluate exclusions only with the user's scope decisions.
4. Update compatibility, validation, release and integration context.

---
*Last updated: 2026-11-12 after approved project definition and three rounds of detailed design answers. Workflow configuration, scoped requirement approval, roadmap approval and implementation are still pending.*
