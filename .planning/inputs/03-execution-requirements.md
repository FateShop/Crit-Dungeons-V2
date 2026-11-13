# Crit Addons — Development Requirements (Questions 1–12)

## 1. Queued Tasks at Boss Entry

When Auto Clear or Blood Rush enters the boss encounter, **discard all pending manual tasks**.

The expected behavior is:

1. Detect successful entry into the boss encounter.
2. Stop the currently running dungeon automation.
3. Clear the entire pending task queue.
4. Reset the active automation state.
5. Do not carry unfinished tasks into the next dungeon run.

Boss entry represents the end of the automated dungeon sequence.

## 2. Auto Clear and Secret Collection

**Auto Clear should not automatically collect secrets to reach the required dungeon score.**

Its primary objective is to clear eligible combat rooms.

Once Blood is complete and there are no more required clearing objectives, the mod should navigate to the Blood portal and wait directly in front of it.

If the dungeon score is below the configured threshold, remain in front of the portal until the score becomes sufficient.

When both conditions are satisfied:

- Blood is complete.
- The dungeon score meets or exceeds the configured threshold.

Enter the portal immediately.

Auto Clear should not begin searching for additional secrets simply because the score is too low.

The map's Ctrl + Left Click Clear Room command also remains mob-only.

## 3. Auto Clear's First Blood Visit

When Auto Clear initially reaches Blood, it should:

1. Trigger the Watcher encounter.
2. Return to the room immediately before Blood.
3. Begin collecting the secrets in that adjacent room.
4. Prepare for the Blood Camp sequence using Noamm's existing timer.

**Do not leave Blood to clear other dungeon rooms during this waiting period.**

The adjacent room's secrets are the intended activity while Blood is being prepared.

After the appropriate Blood Camp timing events, return to the Blood doorway and execute the Blood Camp routine.

## 4. Blood Camp Timing and Line of Sight

While collecting secrets in the room immediately before Blood, prioritize the Blood Camp timer.

### Secret collection deadline

When Noamm's Blood Camp timer reaches **1 second remaining**:

1. Immediately suspend any unfinished secret collection.
2. Stop the current secret interaction sequence.
3. Prioritize the movement to the center of the Blood Room doorway.
4. Execute the required Gyro timing and positioning.
5. Continue with Blood Camp combat when the timer indicates the appropriate event.

Do not delay Blood Camp to finish the remaining secrets.

### Line-of-sight requirement

Before the one-second doorway movement, the player must remain at a position with **no line of sight into the Blood Room**.

If the mod cannot find a valid reachable position satisfying this requirement, **stop automation and report an error**.

Do not substitute a position that has partial line of sight.

The implementation should treat the absence of a valid position as a problem requiring investigation, rather than silently weakening the positioning requirement.

## 5. Clearing Targets and Blood Rush

### Combat target selection

The automation should attack:

- Enemies required to complete the current room.
- Enemies preventing access to required progression objectives.
- Any hostile enemy actively attacking the player.

Do not unnecessarily attack unrelated mobs that do not meet these criteria.

Combat target selection should prioritize room completion and player safety.

### Blood Rush clearing requirements

During Blood Rush, every room along the required route must be cleared before progressing to the next room.

Do not bypass an uncleared room merely because a doorway appears open.

The intended sequence is:

1. Enter the room.
2. Identify the enemies required for completion.
3. Kill the required enemies.
4. Confirm that the room is cleared.
5. Continue toward the next room.

Repeat until reaching Blood.

## 6. Weapons, Item Detection, and Gyro

### RCM weapon selection

Support **any Wither Blade**, including:

- Hyperion.
- Astraea.
- Scylla.
- Valkyrie.

The RCM system should identify and use an appropriate available Wither Blade.

### Automatic item identification

Automatically locate the following items by their actual item identities rather than requiring manually configured hotbar slots:

- Etherwarp-enabled Aspect of the Void.
- Wither Blade used for RCM.
- Dark Claymore used for LCM.
- Gyrokinetic Wand.

Avoid relying on item display names alone when a more reliable item identifier is available.

The item-handling system should coordinate with the selected Noamm `/loadout` configuration.

### Gyro ability

Blood Camp should use **Gyrokinetic Wand's Gravity Storm ability**.

This is the mob-grouping ability, not Cells Alignment.

Aim Gravity Storm at the appropriate center of the Blood Room, taking the current floor's room geometry into account.

## 7. Missing Items and Unavailable Abilities

If a required item is missing, mana is insufficient, or a loadout switch is rejected, **stop automation completely**.

Display a clear notification explaining why automation stopped.

Do not silently skip the missing action.

Examples include:

- A required AOTV cannot be found.
- No supported RCM weapon is available when needed.
- Dark Claymore is unavailable for a required LCM attack.
- Insufficient mana prevents an essential ability.
- The configured LCM or RCM loadout cannot be equipped.

### Gyro is mandatory

If Gyrokinetic Wand or Gravity Storm is unavailable during Blood Camp, **stop automation**.

Do not continue Blood Camp without Gyro.

Do not fall back to attacking with Dark Claymore alone.

## 8. Starting Automation and Switching Modes

### Automatic activation

Auto Clear and Blood Rush should start automatically when enabled in the settings, provided the player is in a valid dungeon state.

A separate Start button or keybind is not required.

### Switching between modes

If one mode is already running and the other is enabled, **switch immediately**.

For example:

- Auto Clear is running.
- Blood Rush is enabled.
- Stop the current Auto Clear execution and begin Blood Rush.

The reverse should also work.

Avoid allowing both automation modes to execute simultaneously.

Ensure that any previous execution state is cleaned up when switching modes so that the two modes do not conflict.

## 9. Inventory Screens and Manual Input

### GUI behavior

When the player opens an inventory or another GUI, temporarily pause automated movement and combat.

Preserve:

- The active objective.
- The current task.
- Remaining queued tasks.
- Relevant pathfinding and progress information.

When the GUI closes, automatically resume execution.

Opening a GUI should not be treated as an emergency cancellation.

### Loadout switching

If the mod must switch loadouts while a GUI is open, it is allowed to **automatically close that GUI** to execute the necessary loadout change.

The loadout coordinator should:

1. Determine that a loadout switch is required.
2. Close the conflicting screen.
3. Execute Noamm's `/loadout` workflow.
4. Select the configured loadout slot.
5. Confirm the switch succeeded.
6. Continue automation.

The automatic GUI pause should not permanently block a required loadout change.

## 10. Unreachable Objectives, Death, and Run Lifecycle

### Unreachable destinations

An objective that cannot be reached even after route recalculation should be treated as a **pathfinding bug**.

Do not silently skip the objective or pretend that it has been completed.

Generate a detailed debug report containing enough information to investigate the problem.

The report should include, where available:

- Current dungeon floor.
- Room identity, orientation, and coordinates.
- Player position and orientation.
- Intended destination.
- Attempted movement routes.
- Etherwarp coordinates, yaw, and pitch.
- Failed movement transitions.
- Collision and targeting information.
- Relevant server responses or rejection information.
- Loaded and unloaded geometry.
- Route-cache status.
- Number of recovery attempts.
- Failure reason and relevant timestamps.

Save the report to a diagnostic log or report file.

Stop the failed execution safely rather than repeatedly attempting an unreachable objective indefinitely.

### Death and revival

When the player dies:

1. Suspend automation.
2. Preserve the current objective and remaining task information.
3. Wait for the player to be revived.
4. Reevaluate the player's position and dungeon state.
5. Recalculate the route from the new position.
6. Resume automation.

Death should not permanently terminate the run.

### Termination conditions

Automatically terminate automation and clear queued tasks when:

- The player disconnects.
- The player leaves the dungeon.
- The dungeon run ends.
- The player enters the boss encounter.

These events should cleanly reset execution state.

## 11. Secrets Already Collected by Teammates

If a teammate collects a secret that appears later in the current route:

**Preserve the existing route, but skip the interaction for that secret.**

Do not recalculate the entire secret route.

The expected behavior is:

1. Continue following the original route order.
2. Detect whether the next secret has already been collected.
3. If it is collected, skip the unnecessary interaction.
4. Continue to the next planned objective.

Avoid attempting to open already-opened chests, collect unavailable items, or otherwise interact with secrets confirmed to be completed.

The route geometry and planned ordering should remain unchanged.

## 12. Exact Route Calculation, Physical Verification, and Missing Geometry

This is one of the most important requirements.

### 12.1 Exact optimal route calculation

The mod must calculate a **mathematically optimal route within its defined movement graph and timing model**.

Do not execute an approximate route while the optimizer is still searching for a proven optimum.

The standard sequence should be:

1. Build the available movement graph.
2. Calculate the optimal route.
3. Prove that the selected route is optimal under the graph's cost model.
4. Physically verify the movement transitions.
5. Cache the verified route.
6. Execute cached routes on subsequent runs.

### 12.2 Visible calculation progress

While optimization is running, display useful progress information.

For example:

- Current room being analyzed.
- Movement graph generation status.
- Number of candidate paths or states evaluated.
- Route optimization status.
- Route verification status.
- Missing geometry or information currently being investigated.

The player must be able to cancel the calculation using **Esc**.

### 12.3 Physical route verification

The first time a route is calculated, the mod should **physically test the route**.

It should:

1. Navigate to the required starting position.
2. Perform the proposed Etherwarp transitions.
3. Verify that each teleport reaches its intended destination.
4. Detect targeting or collision failures.
5. Record successfully verified transitions.
6. Cache the verified route and targeting information.

If a transition fails, update the movement graph and recalculate the optimal route using the corrected information.

Do not cache a failed route as verified.

### 12.4 Exploration during optimization

**IMPORTANT: While a route is being calculated, the mod is allowed to physically Etherwarp through a room to obtain missing information.**

This is an explicit exception to the rule that ordinary task execution must wait until the optimal route has been proven.

If the optimizer encounters a part of the room that is:

- Not loaded.
- Not cached.
- Missing collision information.
- Missing viable Etherwarp destinations.
- Missing targeting information.
- Missing geometric connectivity information.

It should be able to use the Etherwarp pathfinder to navigate toward the relevant region and obtain the required information.

The intended behavior is:

1. Detect missing information required for optimization.
2. Identify the room region or geometry that needs investigation.
3. Calculate a valid exploration route using currently known information.
4. Physically Etherwarp toward that region.
5. Obtain the newly available world or collision data.
6. Update the movement graph and cache.
7. Continue calculating the exact optimal secret route.

This movement is for **information gathering and route verification**, not for prematurely executing an approximate secret route.

### 12.5 Optimization versus exploration

The system should distinguish between three separate activities:

**Exploration**
- Physically navigates to reveal missing room information.
- Updates available geometry and movement connectivity.

**Optimization**
- Calculates the mathematically optimal route using available verified information.
- Does not declare optimality while relevant unknown movement possibilities remain unresolved.

**Execution**
- Follows the resulting verified route.
- Reuses cached targeting and movement information whenever valid.

The player should be able to see which activity the mod is currently performing.

### 12.6 Cache behavior

Once the route is optimized and verified, store the relevant information for reuse, including:

- Room identity and orientation.
- Entrance and exit doorway combination.
- Movement sequence.
- Teleport destinations.
- Etherwarp aiming coordinates.
- Yaw and pitch.
- Verified movement transitions.
- Expected movement costs.
- Relevant geometry and targeting validity information.

On future runs of the same room configuration, reuse the cached route rather than repeating the entire calculation and exploration process.

Cached routes should follow the previously specified zero-ping-style execution behavior, without mandatory per-hop arrival confirmation waits.

However, detect actual failures and invalidate or repair cached data when necessary.

---

## Critical Implementation Requirements

The following points must be preserved:

1. Boss entry clears all pending tasks.
2. Auto Clear does not collect extra secrets to increase score; it waits at the Blood portal when necessary.
3. Blood Camp collects secrets in the room immediately before Blood while the timer runs.
4. At one second remaining, Blood Camp immediately prioritizes doorway positioning and Gyro.
5. Blood Rush clears each required room before progressing.
6. RCM supports all four Wither Blades, and equipment is automatically identified.
7. Gravity Storm is mandatory for Blood Camp.
8. Missing required equipment, mana, or loadout functionality stops automation.
9. Enabling Auto Clear or Blood Rush immediately activates that mode and may replace the other mode.
10. GUI interactions temporarily pause automation, except when the loadout coordinator must close a GUI to perform a required switch.
11. Unreachable objectives generate detailed debugging reports.
12. Death pauses automation until revival; disconnects, dungeon exit, run completion, and boss entry terminate it.
13. Already-collected secrets are skipped without changing the planned route.
14. Exact optimization must finish before normal route execution.
15. First-time verification physically tests movement transitions.
16. **During optimization, the mod may actively Etherwarp to missing or unloaded regions to gather the information needed to complete its calculations.**

Do not silently simplify these requirements. If a requested behavior cannot be implemented or mathematically guaranteed, explicitly identify the limitation instead of presenting an approximation as a completed implementation.