<!-- GSD:project-start source:PROJECT.md -->
## Project

**Crit Addons**

Crit Addons is a modular Minecraft 26.2 dungeon automation addon for NoammAddons 1.3.5 cheat, developed in https://github.com/FateShop/Crit-Dungeons-V2. It adds an interactive dungeon map, time-optimized movement, exact cached secret routes, existing-system loadout switching, Auto Clear/Auto Blood Rush, and Blood Camp under a new **Crit Addons** tab in Noamm's menu.

It supports F1–F7 and M1–M7, automating dungeon traversal and eligible combat objectives through Blood portal entry. It stops at boss entry and does not automate boss fights. The user configures equipment through Noamm's existing loadout workflow.

**Core Value:** Execute reliable, time-efficient dungeon tasks through existing Noamm functionality, without duplicating it or misrepresenting unknown map information, route optimality, or verification.

### Constraints

- **Compatibility:** Minecraft 26.2, NoammAddons 1.3.5 cheat, JDK 25. Installed instance: `C:\Users\Michael\AppData\Roaming\PrismLauncher\instances\26.2`; installed dependency is under its `minecraft\mods` folder.
- **Repository:** `https://github.com/FateShop/Crit-Dungeons-V2`; source changes and releases belong here.
- **Reuse:** New orchestration, optimizers, adapters and action coordination must build on verified Noamm functionality. Favor minimal version-specific bridges where existing useful logic is private.
- **Truthfulness:** Exact optimality is relative to a complete defined graph/model. Unknown relevant possibilities prevent the claim. Cached empirical success is not proof of future server acceptance or unrestricted packet frequency.
- **Responsiveness:** Heavy optimization must preserve client responsiveness, visible progress and Esc cancellation; client/world access and networking must obey thread constraints.
- **Delivery:** Release a working installable JAR with each completed feature/fix; documentation commits are exempt. GitHub CLI was unauthenticated during initial inspection; establish an authorized release-capable route before publishing, without claiming publication until verified.
- **Validation:** No game launch, dependency execution, new JAR installation, build or live dungeon test has occurred in this initialization. Live mechanics and loadout acknowledgements remain to be validated.
<!-- GSD:project-end -->

## Mandatory Project Rules

- Read `.planning/STATE.md`, `PROJECT.md`, `REQUIREMENTS.md` and the active phase context before work. The approved six-phase roadmap uses Vertical MVP increments; all 53 requirements remain binding.
- Do not inspect, reference, copy or reuse historical custom projects (including older Crit Addons) without new explicit user permission. Work only from this repository and intended dependencies.
- Reuse every applicable Noamm feature or partial implementation. Check the exact 1.3.5 cheat artifact as well as pinned source before declaring anything absent. Do not duplicate existing map, targeting, loadout, secret, timer, score, notification or persistence logic.
- New features/settings belong under **Crit Addons**. Use existing Noamm toasts naming required features/settings to enable.
- Each of map, movement, secrets, loadouts, Clear/Rush and Blood Camp must have a distinct implementation agent, separate from its researcher. Record agent identities and file ownership; one coordinator owner maintains shared task/resource contracts. Do not overwrite other agents' work.
- Exact secret optimality requires a documented complete finite graph/timing model and proof. Unknown relevant possibilities prevent the claim. Exploration, optimization, physical verification and execution remain separate visible states.
- Verified cached Etherwarp routes omit mandatory per-hop arrival waits but still enforce valid sequencing, failure detection and cache validity. Reuse Noamm crouched-eye targeting and supported 26.2 networking; do not replace requirements with mouse simulation.
- Preserve the user-approved emergency, GUI, death and lifecycle behavior. Do not silently decide unresolved restart, popup/deadline, visibility, impossible-timing or post-camp policies; resolve the affected planning gates first.
- Every completed feature or bug fix requires a semantic tag and GitHub Release with its compiled 26.2 JAR, source/version evidence and additions/changes/removals notes, beginning at v0.1.0. Planning/docs/incomplete work require no release or automatic phase tag. Record live-verification limitations honestly.
- No boss combat. Boss entry, dungeon exit, disconnect and run end terminate automation and clear queued tasks. Never carry work into a later run.
- Use `codex/` for new branch names. Autonomous execution applies within agreed requirements; meaningful unresolved product decisions still need the user's answer.

<!-- GSD:stack-start source:research/STACK.md -->
## Technology Stack

- Use the repository's Kotlin/Fabric/Stonecutter addon build with JDK 25; target Minecraft 26.2 and NoammAddons 1.3.5 cheat only.
- Public release source: [Noamm tag 1.3.5](https://github.com/Noamm9/NoammAddons/tree/1.3.5), commit `460cfaf5d8760fa1146f5d2433715b7eca9bf319`. Do not substitute mutable HEAD `24fab30d` for release contracts.
- Exact installed artifact inspected by researchers: `C:\Users\Michael\AppData\Roaming\PrismLauncher\instances\26.2\minecraft\mods\NoammAddons-1.3.5-26.2-cheat.jar`. Bytecode inventory/method inspection was read-only; the artifact was not executed.
- Public source omits some cheat implementations. Inspect the shipped artifact before claiming any functionality is absent.
- Reuse Noamm Feature/settings/category integration, `NotificationManager`, `PogObject`, world/room transforms, item identification, movement geometry, event scheduling and supported networking interfaces.
- Add narrow Mixin accessors/invokers where useful existing implementations are private. Do not copy Noamm systems into Crit packages.
- Implement exact algorithms over a documented finite graph/model. No extra optimizer dependency has been selected or justified yet.
- Preserve the repository's Minecraft-version handling, but ship only the required 26.2 target. Release JARs remain separate from planning commits.
<!-- GSD:stack-end -->

<!-- GSD:conventions-start source:CONVENTIONS.md -->
## Conventions

Conventions not yet established. Will populate as patterns emerge during development.
<!-- GSD:conventions-end -->

<!-- GSD:architecture-start source:ARCHITECTURE.md -->
## Architecture

Architecture not yet mapped. Follow existing patterns found in the codebase.
<!-- GSD:architecture-end -->

<!-- GSD:skills-start source:skills/ -->
## Project Skills

No project skills found. Add skills to any of: `.claude/skills/`, `.agents/skills/`, `.cursor/skills/`, `.github/skills/`, or `.codex/skills/` with a `SKILL.md` index file.
<!-- GSD:skills-end -->

<!-- GSD:workflow-start source:GSD defaults -->
## GSD Workflow Enforcement

Before using Edit, Write, or other file-changing tools, start work through a GSD command so planning artifacts and execution context stay in sync.

Use these entry points:
- `/gsd-quick` for small fixes, doc updates, and ad-hoc tasks
- `/gsd-debug` for investigation and bug fixing
- `/gsd-execute-phase` for planned phase work

Do not make direct repo edits outside a GSD workflow unless the user explicitly asks to bypass it.
<!-- GSD:workflow-end -->



<!-- GSD:profile-start -->
## Developer Profile

> Profile not yet configured. Run `/gsd-profile-user` to generate your developer profile.
> This section is managed by `generate-claude-profile` -- do not edit manually.
<!-- GSD:profile-end -->
