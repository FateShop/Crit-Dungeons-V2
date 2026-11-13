# Crit Addons — Additional Development Requirements (Questions 1–12)

## 1. Unavailable Map Information

Use the same dungeon map information and behavior as **"Cheater Map" under Noamm Addons' "Dungeon Map" feature**.

The interactive dungeon map should use the room identification, discovery information, rendering, and layout handling available through that feature.

Do not independently invent unknown room identities or layouts.

The goal is to display the dungeon using the same available information and map behavior as Noamm's Cheater Map, while adding Crit Addons' interactive functionality.

The map must continue supporting:

- Left Click: Navigate to a room.
- Ctrl + Left Click: Clear a room.
- Shift + Right Click: Collect a room's secrets.

If Cheater Map receives new information during a run, the interactive map should reflect those updates.

## 2. Map Navigation Destination

When the player left-clicks a room on the interactive map, navigate to the **nearest safe position inside that room**.

Do not automatically target the room center or stop at its entrance.

The destination should:

- Be inside the selected room.
- Be safely reachable.
- Avoid hazardous or obstructed positions.
- Minimize travel time from the player's current location.

Use the movement pathfinder to determine the fastest valid route to that position.

## 3. Interactive Map Behavior

The dungeon map should open and close using a configurable keybind.

**Default keybind: M**

Pressing M toggles the map popup.

After clicking a room, **the map must remain open**.

This allows multiple actions to be queued without repeatedly reopening the map.

For example, the player should be able to queue navigation, clearing, and secret collection commands for several different rooms before closing the popup.

Clicking a room should add its requested action to the task queue without automatically closing the map.

## 4. Manual Tasks vs. Automatic Runs

When Auto Clear or Blood Rush is already running, manually selected map tasks should wait until the **entire automatic run finishes**.

Do not interrupt an automatic run between rooms to execute manually queued commands.

The correct execution order is:

1. Continue the currently active Auto Clear or Blood Rush run.
2. Complete the entire automatic run.
3. Execute manually queued map commands in the order they were received.

Queued tasks should remain pending while the automatic run is active.

Do not interrupt or replace the active run merely because the player clicks another room.

Esc remains the emergency cancellation control.

## 5. Permitted Movement Methods & Door Handling

Allow the movement/pathfinding system to use all of the following:

- Walking.
- Jumping.
- Etherwarp.
- Ordinary Aspect of the Void teleportation.
- Wither Impact.

Choose the combination of movement methods that produces the **fastest realistic completion time**, not necessarily the fewest teleports or shortest physical distance.

Movement should still look like it is being performed by a real player.

The mod should also automatically handle necessary dungeon progression mechanics, including:

- Obtaining required dungeon keys from minibosses.
- Opening Wither Doors.
- Opening Blood Doors.
- Navigating through doors after they become accessible.

Include the time required for these interactions when evaluating routes.

## 6. Etherwarp, Ping Independence, Targeting, and Packet-Based Actions

This requirement is particularly important.

### 6.1 Etherwarp configuration

I use an **Aspect of the Void (AOTV)** with Etherwarp.

Assume a maximum Etherwarp range of **61 blocks**.

The system should be designed to operate across different player ping conditions. My own ping is generally low, but it should not depend on one fixed latency value.

### 6.2 Zero-ping-style Etherwarp execution

I want zero-ping-style Etherwarp behavior.

**Do not wait for an individual server confirmation packet after every Etherwarp during normal execution of an already verified and cached route.**

Instead, use two separate operating phases.

**Phase A — Route verification**

When evaluating a previously unverified route:

1. Calculate possible Etherwarp destinations.
2. Determine valid aiming positions.
3. Execute and verify candidate teleports using server-confirmed results.
4. Record successfully verified transitions.
5. Cache the verified route and its associated movement/aiming information.

Verification should establish whether the proposed movement transitions work under the applicable game mechanics.

**Phase B — Cached route execution**

Once a route has been successfully verified and cached:

1. Load its verified teleport sequence.
2. Use the cached aiming and targeting information.
3. Execute the Etherwarp sequence without waiting for a separate arrival confirmation after each hop.
4. Avoid unnecessary per-hop confirmation delays.
5. Monitor the actual player state for failures and recalculate when necessary.

Do not blindly assume that a previously verified route is still valid after relevant world geometry, player state, or server conditions change.

### 6.3 Noamm Etherwarp Overlay integration

Use the existing **Noamm Etherwarp Overlay** functionality as the reference for:

- Determining where the player must aim.
- Determining whether a proposed Etherwarp destination is valid.
- Resolving the appropriate target position.
- Checking the actual targeting geometry.

Avoid implementing an incompatible independent Etherwarp targeting model when the existing Noamm functionality can provide the relevant information.

### 6.4 Crouched eye-height calculations

All Etherwarp trajectory and targeting calculations must account for the **player's crouched eye height**.

Do not calculate Etherwarp aiming angles using the standing eye height.

This applies to:

- Initial departure calculations.
- Intermediate teleport destinations.
- Final destinations.
- Block raycasting and line-of-sight checks.
- Yaw and pitch calculations.

Correctly account for full blocks, slabs, half-height surfaces, and other valid landing geometries.

### 6.5 Packet-based ability execution

**IMPORTANT: Use Minecraft's appropriate client-to-server interaction packets for the following actions:**

**Etherwarp**
- Send the appropriate item-use and orientation/input packets needed to perform Etherwarp.
- Apply the intended targeting orientation and required sneak state.

**Wither Impact / RCM**
- Use the appropriate item-use packets for Wither Impact right-click actions.
- Handle required item selection and targeting.

**Dark Claymore / LCM**
- Use the appropriate attack/swing packets for left-click mage beam execution.
- Handle weapon selection and targeting orientation.

Do not rely on simulated physical mouse clicks as the primary execution mechanism for these actions.

Implement the packet operations through the supported Minecraft client networking interfaces, using the packet types appropriate to Minecraft 26.2.

Do not assume that the server accepts unlimited packet frequency. The executor must remain consistent with server-side sequencing, cooldowns, and state validation.

The objective is minimal unnecessary delay while maintaining reliable operation.

## 7. Exact Secret Route Optimization

Use an **exact mathematical optimization algorithm** that can prove the optimal route within a defined movement graph and timing model.

The route optimizer does not need to individually enumerate every possible route if a more efficient exact algorithm can establish optimality.

Suitable techniques may include:

- A* or Dijkstra for movement graph search.
- Dynamic programming.
- Branch-and-bound.
- Other exact combinatorial optimization methods.

The optimizer must account for all permitted movement methods, including walking, jumping, Etherwarp, normal AOTV teleportation, and Wither Impact.

The objective is **minimum predicted completion time**, not the fewest actions or shortest distance.

Include movement and interaction costs in the optimization.

Once an optimal route has been calculated and verified, cache it for future runs.

The guarantee of optimality applies to the defined movement graph and timing model rather than unpredictable variations in live server latency.

If the optimizer cannot prove a route optimal, it must not misrepresent the result as mathematically optimal.

## 8. Secret Route Starting & Ending Positions

During the initial calculation phase, the secret route optimizer must consider **every possible valid doorway as both a starting point and an ending point**.

Do not optimize a room's secret route for only one entrance or one exit.

For a room with multiple doorways, consider the possible entrance/exit combinations.

For each valid combination:

1. Start at the selected entrance doorway.
2. Determine the optimal sequence for collecting the room's secrets.
3. Finish at the selected exit doorway.
4. Calculate the predicted total route completion time.
5. Cache the resulting optimal route.

The optimizer should be capable of supporting different entrance and exit choices for the same room.

This is important because the best secret route may change depending on where the player enters and which room they need to visit afterward.

The broader dungeon pathfinder should be able to select the cached room route that best fits the overall dungeon traversal.

Room rotations and orientations must be accounted for when applying cached routes.

## 9. Teammates & Dungeon Progress

Handle teammate actions differently depending on whether the task is secret collection or room clearing.

### Secret collection

**Continue following the original planned secret route.**

Do not automatically recalculate the entire secret route just because a teammate has collected a secret.

Preserve the existing route and task order.

### Room clearing

**Automatically detect when teammates have already cleared a room.**

If a room is already cleared:

- Mark it as completed.
- Remove its remaining mob-clear objective.
- Skip unnecessary clearing actions.
- Recalculate the remaining clearing route where appropriate.

Auto Clear should avoid wasting time on rooms that teammates have already completed.

## 10. Special Dungeon Rooms

Auto Clear should **skip special rooms that do not contain normal mob-clear objectives**.

This includes:

- Puzzle rooms.
- Trap rooms.
- Fairy rooms.
- Other special rooms without standard combat-clearing requirements.

Auto Clear should focus on normal dungeon combat rooms and the required progression toward Blood and the boss portal.

Do not automatically solve puzzles or complete trap-room mechanics as part of Auto Clear.

The navigation system may still route through special rooms when necessary to reach another destination.

Skipping a special room's objective should not prevent traversal through it.

## 11. Blood Camp Automation

Blood Camp must account for differences between dungeon floors and Blood Room layouts.

### 11.1 Initial position and secret collection

After reaching Blood, the player should remain **in the room outside the Blood Room**.

During the waiting period, the automation should complete the secrets in that adjacent room.

The player should be positioned so that there is **no line of sight into the Blood Room**.

The system should maintain this condition until the appropriate point in the Blood Camp sequence.

### 11.2 Blood Camp timer

Use **Noamm Addons' existing Blood Camp counter/timer**.

Do not create a separate independently timed Blood Camp system if Noamm already provides the required event timing.

Use the existing counter to determine when to begin positioning, Gyro, and attacks.

### 11.3 Doorway positioning

When the Blood Camp timer reaches **1 second remaining**, Etherwarp to the center of the Blood Room doorway.

The intended destination is the center of the doorway, not the center of the Blood Room itself.

Before that point, the player should remain outside without direct line of sight into Blood.

### 11.4 Gyrokinetic Wand

Use Gyrokinetic Wand targeting the center of the Blood Room.

The Gyro action should occur:

- At 1 second before the timer reaches zero, or
- When the player arrives at the center of the Blood Room doorway.

Coordinate the teleport and Gyro sequence so that the ability is used at the intended position and time.

### 11.5 Dark Claymore attacks

Use **Dark Claymore (LCM)** for Blood Camp attacks.

Start attacking when **Noamm's Blood Camp timer indicates "Kill."**

Do not begin attacking based only on an independently estimated elapsed time.

Use the timer's actual kill event as the trigger.

Dark Claymore attacks must use the packet-based LCM execution system described in Question 6.

### 11.6 Different dungeon floors

Blood Room layouts and mechanics can differ across floors.

The automation must account for these differences.

Use floor-specific room geometry, targeting, positioning, and timing information where required.

Do not assume that one fixed Blood Camp position or targeting configuration will work universally across F1–F7 and M1–M7.

## 12. Blood Portal Entry & Auto Clear Completion

### 12.1 Portal entry conditions

The automation should enter the Blood portal only when **both conditions** are satisfied:

1. Blood is complete.
2. The dungeon score is at or above the configured threshold.

Both conditions are mandatory.

Do not enter the portal if only one condition is satisfied.

### 12.2 Default score thresholds

Use the following defaults:

| Floors | Default required score |
|---|---:|
| F1–F3 | 270 |
| M1–M3 | 270 |
| F4–F7 | 300 |
| M4–M7 | 300 |

**These thresholds must be editable in the mod's settings.**

The player should be able to change the required score without modifying source code.

### 12.3 Blood Rush behavior

After Blood is complete, monitor the dungeon score.

Once the configured threshold is reached, enter the Blood portal.

If the score is below the threshold, do not enter yet.

### 12.4 Auto Clear behavior

Auto Clear should **enter the Blood portal as soon as both portal entry requirements have been satisfied**.

Do not continue clearing unnecessary rooms after the score threshold has been reached and Blood is complete.

If additional clearing is needed to reach the required score, continue working toward that score.

Once both requirements are satisfied:

1. Stop remaining unnecessary clearing objectives.
2. Navigate to the Blood portal.
3. Enter the portal.
4. Stop dungeon automation after entering the boss encounter.

Do not automate the boss fight itself.

---

## Implementation Priorities

The following requirements are particularly important and must not be overlooked:

1. **Noamm Integration:** Reuse the existing Cheater Map, Etherwarp Overlay, Blood Camp timer, and `/loadout` behavior wherever applicable.
2. **Packet-Based Actions:** Etherwarp, Wither Impact right-click, and Dark Claymore left-click must use the appropriate Minecraft network packet interfaces.
3. **Cached Etherwarp Execution:** Verified routes should execute without individual per-hop server confirmation waits, with failure detection and recovery.
4. **Correct Etherwarp Geometry:** Use crouched eye height, a 61-block maximum range, and correct block targeting.
5. **Exact Secret Optimization:** Prove optimality within a defined movement/timing model and cache routes for all relevant doorway combinations.
6. **Task Queuing:** Manual map commands should not interrupt active Auto Clear or Blood Rush runs.
7. **Blood Camp:** Use Noamm's timer, perform adjacent-room secrets while waiting without Blood Room line of sight, teleport to the doorway at 1 second, use Gyro, and attack on the "Kill" trigger.
8. **Boss Entry:** Require Blood completion and the editable score threshold, then enter the portal and terminate automation.

Do not silently replace these requirements with simpler implementations. If an integration or game mechanic cannot be implemented as described, identify the limitation explicitly and explain which part cannot be guaranteed.