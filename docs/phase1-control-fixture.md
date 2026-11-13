# Phase 1 development control fixture

The fixture is an isolated Fabric development mod, excluded from the production addon JAR. Both Fabric's development-environment flag and `crit.controlFixture=true` are required. It calls the production SessionCoordinator, TaskContract, ClientControlAdapter effect guard and real input/lifecycle observers. It emits no gameplay movement, aiming, attacks, item use or screen closing.

With the project's JDK 25 configured, the agent launches:

```powershell
.\gradlew.bat :26.2:runControlFixture
```

The run uses the ordinary main addon plus the separate devControl mod. Normal run configurations have neither the fixture source set nor enable property. `:26.2:verifyControlFixtureIsolation` compiles the fixture and inspects the actual production Jar task's archive output for class/metadata exclusion. Fabric run configuration follows https://docs.fabricmc.net/develop/loom/options and client commands follow https://docs.fabricmc.net/develop/commands/basics; exact 26.2 dependency classes were checked during compilation.

## Commands

| Command suffix after `/crit-control-fixture` | Behavior |
| --- | --- |
| `start` | Requires a real nonboss valid epoch and empty session; queues probe-A then probe-B. Refuses a same-run stop latch. |
| `status` | Replies and logs epoch, generation, active/pending IDs, pauses, revalidation, heartbeat, acknowledged snapshots and unrelated sentinel count. |
| `fatal` | Stops fixture work through the production fatal path and existing Noamm explanation toast. |
| `selection-off` | Cancels fixture work and explicitly selects no mode in ephemeral production session state. |
| `selection-on` | Selects ephemeral fixture mode through the production explicit selection API; starts fresh when valid. |
| `sentinel` | Enqueues an unrelated Noamm ActionUtils action with blockInput=false; after ten Noamm ticks it increments/logs an independent marker. |
| `reset` | Cancels only tasks owned by crit-control-fixture. |

Probe-A requests all five exclusive resources and writes one guarded memory heartbeat per unpaused client tick for 200 ticks. Probe-B follows for 20 ticks. A 120-second wall bound cancels fixture work even during pauses. No saved settings are read or changed. Snapshot acknowledgement records actual production-captured pose, dimension, floor and Noamm room identity/rotation before heartbeat resumption. Shutdown removes the production tick callback and disposes ephemeral selection.

## Live scenarios

1. Enter an actual supported nonboss dungeon, run `start`, then `status`. Expect probe-A active and probe-B pending, one epoch, and heartbeat progress after chat closes. Keep the game visible during the bounded probe.
2. Open M or another GUI. Heartbeat must freeze while the popup keeps receiving Noamm map updates. Close with M; after releasing input and two idle ticks expect actual snapshot acknowledgement and heartbeat progress. No ordinary pause/resume toast appears.
3. Hold/release movement keys and mouse buttons, move the mouse continuously and release it, then lose/regain window focus. The player's own input is never blocked. Heartbeat pauses silently, preserves both task identities, and resumes only after remaining reasons clear and actual-state acknowledgement. Test GUI+manual and GUI+death overlap.
4. Die and revive in a real dungeon. Production Noamm ghost/death and Minecraft death facts must preserve the task/queue; revival records a new actual snapshot before continuing. The fixture never injects these facts.
5. Press Esc in gameplay, the map, and an unrelated GUI. Expect no active/pending work and no old-token heartbeat. Repeated valid ticks and closing screens cannot restart it. `start` refuses. `selection-off`, then `selection-on` explicitly rearms within that run. Repeat with `fatal`; verify the existing Noamm toast and unchanged saved user settings.
6. Queue `sentinel` while probes are active, then immediately Esc or fatal. The unrelated marker must complete/log despite Crit cancellation. Crit must yield while the shared queue is queued/running/blocked and require actual revalidation when it clears. Pure traces only verify contracts; actual sentinel coexistence requires this live test.
7. Exercise actual boss entry, run end, exit and disconnect, then a genuine next run. Old task IDs vanish and old tokens remain invalid. Boss/end cannot reopen from repeated inDungeon=true. Only configuration selection persists; no old task restores. Terminal transitions dismiss only the Crit map, never unrelated GUIs.
8. Relaunch ordinary production runtime. The fixture command must be absent; verify target JAR exclusion and actual mixin application from logs. Record normal/master floor coverage and any unperformed cases for 01-05.

Status commands themselves open chat and therefore add the ordinary GUI pause; compare logged snapshots and heartbeat values across intervals rather than expecting advancement while typing. The fixture validates session controls only. Blood deadline/GUI conflicts, no-LOS feasibility, impossible timing and post-camp policies remain later planning gates.
