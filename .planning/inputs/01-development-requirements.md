# Crit Addons — Development Requirements (Questions 1–10)

### 1. Minecraft Version & Noamm Addons

The mod should target **Minecraft 26.2**.

My currently installed Noamm Addons version is **1.3.5**, with the following JAR filename:

`NoammAddons-1.3.5-26.2-cheat.jar`

My Prism Launcher instance is located at:

`C:\Users\Michael\AppData\Roaming\PrismLauncher\instances\26.2`

All development, builds, and compatibility testing should target this Minecraft instance and the specified Noamm Addons version. Support for 26.3 is not currently required.

### 2. Dungeon Scope

The mod should support **every Catacombs dungeon floor**, including:

- Normal Mode: F1, F2, F3, F4, F5, F6, and F7.
- Master Mode: M1, M2, M3, M4, M5, M6, and M7.

All dungeon automation features should be designed to work across these floors, accounting for their different room layouts, enemies, and mechanics.

**Automation must stop after entering the boss fight.**

The mod should handle dungeon traversal, clearing rooms, collecting secrets, and reaching the boss room, but it should not automatically fight dungeon bosses.

Once the player enters the boss encounter, dungeon automation should end.

### 3. Existing Code & Project Scope

**DO NOT inspect, reference, copy, or reuse code from previous projects unless I explicitly instruct you to do so.**

This includes older versions of Crit Addons, previous Etherwarp pathfinding implementations, and any other historical codebases.

Development should start from the currently provided project and its intended dependencies.

Do not independently search for previous implementations or assume that old code should be incorporated.

If you believe an older implementation would be useful, ask for explicit permission before examining it.

### 4. GitHub Releases & Versioning

Create a new GitHub Release **for every completed feature or bug fix**, not for every individual commit.

Each release should include:

- A new semantic version number.
- A Git tag matching that version.
- A compiled, downloadable JAR.
- Release notes describing the completed changes.

Use semantic versioning, starting with a version such as `v0.1.0` and progressing through versions such as `v0.1.1`, `v0.2.0`, etc.

Planning changes, documentation updates, and incomplete work do not require individual releases.

The objective is to ensure every completed feature or fix has a corresponding versioned JAR that can be installed and tested.

### 5. Interactive Dungeon Map

The interactive dungeon map should display **the entire dungeon layout immediately**, including rooms that have not yet been explored.

Do not restrict the display to discovered rooms.

The map should be interactive, allowing the player to select individual rooms and issue commands.

The default keybind for opening and closing the map should be:

**M**

This keybind must be configurable through the mod's settings.

The map should function as a popup or overlay that the player can open during a dungeon run.

The existing planned room interactions should be supported:

- **Left Click:** Navigate to the selected room.
- **Ctrl + Left Click:** Clear the selected room.
- **Shift + Right Click:** Complete the selected room's secrets.

The full-layout requirement is intentional. However, the implementation must distinguish genuinely known room information from information unavailable to the client; it must not invent room identities or coordinates.

### 6. Map Commands & Task Queue

When the player issues a new map command while another automated task is running, the mod should **queue the new task**.

Do not interrupt or replace the current task.

The expected behavior is:

1. Continue executing the currently active task.
2. Add newly requested actions to a task queue.
3. Once the current task finishes, begin the next queued task.
4. Continue until the queue is empty or automation is stopped.

The **Clear Room** command must only perform the actions necessary to satisfy that room's mob-clear requirement.

It should **not collect secrets** as part of clearing a room.

Secret collection must remain a separate action, triggered through Shift + Right Click.

This separation allows the player to choose whether to navigate, clear enemies, or collect secrets independently.

### 7. Movement & Pathfinding

The primary pathfinding objective is to **minimize actual travel/completion time**, not simply the number of Etherwarp teleports.

The mod should evaluate movement based on how quickly the player can reach the destination.

A route involving more teleports may be preferable if it completes faster overall.

**Movement should still look like a real person is performing it.**

Movement should appear natural and controlled rather than producing obviously unnatural movement patterns.

The route planner should account for realistic movement execution, including targeting, travel time, and interactions.

Etherwarp remains a core part of the navigation system. Other movement methods, such as walking, jumping, and normal AOTV teleportation, may be considered where appropriate, but their exact permitted use has not yet been finalized.

Prioritize reliable execution and realistic-looking movement while still optimizing travel time.

### 8. Secrets & Route Optimization

The secret collection system should support automatically collecting **all dungeon secret types**, including:

- Secret chests.
- Dropped secret items.
- Secret bats.
- Levers and other required interactions.
- Other room-specific secret mechanics.

Secret collection should physically perform the required actions rather than simply marking secrets as completed.

**The route optimization system must calculate the mathematically optimal route.**

The objective is to find the fastest possible route for collecting all required secrets in a room.

The first calculation should evaluate the route possibilities until it can establish which valid route is optimal.

Do not simply accept an approximate route because a short calculation time limit has been reached.

After the optimal route has been calculated, it should be cached so that future runs of the same room do not require the entire optimization process again.

Cached routes must account for relevant room layouts, orientation, and movement constraints.

If an exact optimal route cannot be proven for a particular room, report that limitation rather than incorrectly claiming that an approximate route is mathematically optimal.

### 9. LCM & RCM Loadout Switching

The mod should use Noamm Addons' existing **`/loadout`** system for equipment switching.

I will manually configure which loadout slot corresponds to each combat style.

The mod should provide two configurable settings:

- **LCM Loadout:** A number from 1–9.
- **RCM Loadout:** A number from 1–9.

When a loadout switch is required, the mod should:

1. Execute `/loadout`.
2. Select the configured loadout slot using the corresponding number from 1–9.
3. Allow the loadout switch to complete before continuing the relevant combat actions.

Do not create a separate armor, equipment, pet, or weapon management system when the existing loadout functionality already handles these changes.

The mod should automatically select the appropriate combat loadout based on the upcoming activity.

Use **LCM** for miniboss encounters, blood-room combat, and other encounters designated for LCM.

Use **RCM** for normal dungeon mob clearing.

The individual contents of the loadouts will be configured manually through the existing Noamm system.

### 10. Automation Control & Failure Recovery

**Stopping automation**

The only user-controlled action that should stop automation is pressing **Escape (Esc)**.

Manual movement, opening inventory screens, or other normal interactions should not automatically cancel automation.

Esc should act as the emergency cancellation control.

When Esc is pressed, the mod should cancel the active automation task and clear any queued automation tasks so that no additional tasks execute unexpectedly.

**Failure recovery**

If an Etherwarp teleport, movement operation, or interaction fails, the mod should **recalculate the route and try again**.

Do not blindly repeat the exact same failed action without reevaluating the current situation.

The expected behavior is:

1. Detect that the attempted action did not succeed.
2. Determine the player's actual current position or relevant state.
3. Recalculate a valid route to the intended destination.
4. Resume the active task using the updated route.
5. Preserve the remaining queued tasks.

A failed movement action should not automatically cause the mod to abandon its objective.

Implement safeguards against endless recalculation loops or permanently unreachable destinations. If no valid recovery path exists, the mod should report the failure rather than repeatedly attempting impossible actions.

---

## Overall Development Expectations

Build Crit Addons as a modular Minecraft 26.2 dungeon automation addon compatible with Noamm Addons 1.3.5.

The main objectives are:

- Fast, reliable dungeon navigation using time-optimized pathfinding.
- Human-like movement execution.
- An interactive full-layout dungeon map with configurable controls.
- Queued navigation, mob-clearing, and secret-collection tasks.
- Mathematically optimal secret routes, cached for reuse.
- Automatic LCM/RCM switching through Noamm's existing loadout interface.
- Automatic dungeon execution across F1–F7 and M1–M7, ending at boss entry.
- Esc-only user cancellation and automatic route recalculation after failures.
- Semantic GitHub releases with compiled JARs for completed features and fixes.

**Important:** Do not inspect previous codebases without explicit permission. Do not silently substitute approximate functionality for requirements that explicitly demand optimality, and do not claim unavailable dungeon information is known.