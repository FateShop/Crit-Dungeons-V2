# Phase 1: Live Map and Safe Task Control - Context

**Gathered:** 2026-11-12
**Status:** Ready for planning

<domain>
## Phase Boundary

Deliver a live Noamm Cheater Map popup under Crit Addons, dependency notifications, and shared task/resource controls with correct cancellation, pause/resume and dungeon lifecycle handling. The first release is a usable map viewer/session-control increment, not a claim that later travel, combat or secret commands are implemented.

Phase 1 owns CORE-02–05, MAP-01–02, TASK-02–05, TASK-07, DIAG-03 and REL-01–02. Other phases implement executable room actions, movement, combat, exact secrets, Blood Camp and full automatic runs. Shared contracts must support those consumers without duplicating Noamm.
</domain>

<decisions>
## Implementation Decisions

### Settings versus execution

- **D-01:** Only the player turns an automatic mode off in settings. Esc, fatal errors and lifecycle termination must not programmatically disable that setting. This explicit correction supersedes the user's initial numeric answers that appeared to approve disabling it.
- **D-02:** Esc stops current automation/calculation and clears queued work. A fatal error stops current automation and clears queued work with the already-required explanation/diagnostics. Neither may immediately restart the still-enabled mode during the same run.
- **D-03:** After Esc or a fatal error, an enabled mode automatically starts fresh in the next dungeon run. Deliberately toggling a mode off/on is an explicit restart action; passive validity checks or closing a screen are not restart actions after cancellation.
- **D-04:** Boss entry, run completion, dungeon exit and disconnect terminate execution, clear queued tasks and preserve the enabled setting. A new valid dungeon starts fresh, never restoring unfinished tasks.
- **D-05:** Enabled mode selection persists across closing and relaunching Minecraft. Preserve configuration only, not an old execution/queue. No automated boss combat.

### Player input, screens and pauses

- **D-06:** Player input is not blocked. Manual input temporarily freezes automated movement/combat and preserves active objectives and queued work. The voice discussion initially mentioned blocking but explicitly clarified pause-and-resume afterward; follow the final clarification.
- **D-07:** Resume automatically when the player's manual input finishes, controls are released or the GUI closes, subject to remaining pause/lifecycle conditions. No explicit unpause control is requested. Resume must reevaluate actual state before continuing actions affected by manual movement.
- **D-08:** Ordinary manual-input and GUI pause/resume is silent: no transient paused/resuming toasts or status messages. This does not remove required dependency notifications, fatal-error explanations, diagnostics or later optimization progress.
- **D-09:** Retain the already-approved general GUI pause rule, including preserving work. A required loadout coordinator may close a conflicting screen for its operation. Do not introduce another screen-closing exception without an explicit gameplay decision.
- **D-10:** Death preserves work until revival, then reevaluates current state and replans before resume. Independent pause reasons cannot accidentally resume work while another reason still applies.

### Map appearance and interaction boundary

- **D-11:** Use a centered, medium-sized popup with the game visible around it. Keep Noamm room colors, identities and rendering. The user selected this layout and delegated further cosmetic details.
- **D-12:** Preserve configurable default M toggle and keep the popup open after commands, as already approved. Do not invent unavailable rooms or imply unimplemented room actions work in Phase 1.
- **D-13:** Reuse Noamm's existing notification presentation for dependencies and errors; no new notification engine or cosmetic-question rounds. Name the actual feature/setting the player must enable.

### Agent's discretion

- Choose routine dimensions, spacing, backdrop opacity and existing Noamm styling without further user questions.
- Use established Noamm presentation patterns for existing room information and unavailable actions. Do not add new capabilities merely to fill the UI.
- Keep discussion focused on substantive gameplay choices; the user explicitly rejected further trivial appearance questions.
- Define implementation details such as raw manual-input detection, input-source separation, resource release and idle detection in research/planning. Do not interpret the automation's own inputs as manual overrides or create endless pause/resume oscillation.
</decisions>

<canonical_refs>
## Canonical References

### Approved scope and rules

- `AGENTS.md` — mandatory reuse, project boundaries, ownership and release rules.
- `.planning/PROJECT.md` — full product scope, constraints and known feasibility gates. The settings/rearm decisions above resolve the earlier open question for Phase 1.
- `.planning/REQUIREMENTS.md` — all 53 approved requirements and traceability.
- `.planning/ROADMAP.md` — six approved Vertical MVP phases and Phase 1 completion criteria.
- `.planning/STATE.md` — current position and remaining cross-phase concerns.
- `.planning/inputs/01-development-requirements.md` — original environment, map, loadout, queue and recovery answers.
- `.planning/inputs/02-additional-requirements.md` — exact navigation, Blood timing and portal requirements.
- `.planning/inputs/03-execution-requirements.md` — failure, lifecycle, GUI and exact-verification details.

### Reuse and integration evidence

- `.planning/research/FEATURES.md` — completed six-feature Noamm reuse inventory.
- `.planning/research/ARCHITECTURE.md` — proposed coordinator and consumer contracts.
- `.planning/research/PITFALLS.md` — exactness, private API, lifecycle and timing hazards.
- `.planning/research/STACK.md` — exact release source/artifact and build environment.
- `src/main/java/com/github/noamm9/untitled/mixins/MixinCategoryType.java` — existing addon category registration pattern.
- `src/main/java/com/github/noamm9/untitled/mixins/MixinMinecraft.java` — current client-level integration example.
- `src/main/kotlin/com/github/noamm9/untitled/features/impl/untitled/ExampleFeature.kt` — existing Noamm Feature/settings/HUD and event lifecycle pattern.

No additional external specifications were introduced during this discussion. The preserved user inputs and these latest decisions form the product contract. Historical custom projects remain prohibited.
</canonical_refs>

<code_context>
## Existing Code Insights

### Reusable Assets

- Noamm MapRenderer/MapConfig/MapLeapRenderer already provide actual dungeon map rendering and centered scaled presentation. Reuse them rather than recreate map layout/room rendering.
- Noamm DungeonScanner/UniqueRoom/ScanUtils/MapUtils provide discovery and transforms; private state needs narrow compatible bridges only where necessary.
- Noamm NotificationManager already delivers dependency/error toasts; use exact installed 1.3.5 feature/setting names.
- Existing Noamm Feature types supply settings, keybinds, event registration and lifecycle integration. Existing storage can persist settings.

### Established Patterns

- The template's category mixin and feature package naming connect features to menu categories. Adapt them for Crit Addons rather than build another menu.
- Feature event registrations automatically unregister on feature disable; distinguish this settings lifecycle from a stopped-but-still-enabled automation session.
- Noamm's action queue is priority-based, not a guaranteed FIFO manual task queue. Shared Crit coordination is still necessary, while low-level Noamm scheduling remains reusable.

### Integration Points

- Dedicated map and coordinator implementation owners must be different agents from their researchers. Record agent identities/file ownership before edits.
- One coordinator owns Crit cancellation, run identity, pause reasons and resource arbitration. All later controllers consume it; no global cancellation of unrelated Noamm actions.
- Persist enabled settings separately from per-run stopped/cancelled/failed state and ephemeral task queues. A same-run stop latch must not clear merely because location becomes valid again.
</code_context>

<specifics>
## Specific Ideas

The popup is centered and medium-sized with gameplay visible around it. Ordinary pauses are silent and automatically resume after manual interaction. Persistent mode settings are changed only by the player; execution can stop independently of them.
</specifics>

<deferred>
## Later-Phase Decisions Already Identified

- **Blood deadline versus GUI/manual pauses:** Still requires an explicit substantive decision before the Blood controller is implemented. The user has not authorized ignoring manual interaction or auto-closing screens for Blood timing. Phase 1 must not invent such behavior.
- **Impossible one-second arrival and no-LOS secret feasibility:** Retain the existing stop/report constraints and investigate in the affected movement/secret/Blood phases. Do not promise impossible timing or weaken visibility.
- **Auto Clear post-camp transition:** Remains an explicit Phase 6 planning gate while score is insufficient.

No new features were requested or deferred from approved v1 scope. These are existing later-phase decisions, not scope reductions.
</deferred>

---
*Phase: 01-live-map-and-safe-task-control*
*Context gathered: 2026-11-12*
