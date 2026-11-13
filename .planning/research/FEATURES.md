# Feature research and reuse inventory

Research date: 2026-11-12. All six requested feature researchers completed read-only inspection. The user's submitted scope is authoritative; research does not add or defer features.

Paths below are relative to `src/main/kotlin/com/github/noamm9/` in [Noamm 1.3.5](https://github.com/Noamm9/NoammAddons/tree/1.3.5). Binary-only observations refer to the exact installed cheat JAR identified in STACK.md.

| Feature owner | Existing Noamm functionality | Genuinely new Crit coordination |
| --- | --- | --- |
| Interactive map | `features/impl/dungeon/map/MapRenderer.kt`, `MapConfig.kt`, `features/impl/dungeon/leapmenu/MapLeapRenderer.kt`; scanner, room models, calibration/transforms | Room hit-testing, action dispatch, FIFO task presentation and safe-room target selection. LeapMenu's teammate-head click is not room navigation. |
| Movement | `utils/items/EtherwarpHelper.kt`, `TeleportUtils.kt`, `utils/PlayerUtils.kt`, packet events, ZeroPingCamera | Timed movement graph/search, exploration, verified cache, multi-method action sequencing and bounded replanning. |
| Secrets | `utils/dungeons/map/core/RoomData.kt`, `UniqueRoom.kt`, `utils/dungeons/map/utils/ScanUtils.kt`, `DungeonWaypoints`, `DungeonEvent.SecretEvent`, installed `Secrets` | Exact prerequisite-aware objective routing for all doorway pairs, cache proof/verification state and shared execution. Audit the exact Secrets action APIs before building interactions. |
| Loadouts | `features/impl/general/LoadoutKeybinds.kt`, command and GUI utilities | Coordinate `/loadout`, existing private `click(index)`, application confirmation and combat resource ownership. Existing 12 slots are restricted to 1–9 in Crit settings. |
| Clear/Rush | `utils/dungeons/map/handlers/DungeonTree.kt:getBloodRush`, scanner/door/key state, `StarMobESP`, `DoorKeys`, dungeon lifecycle events | Mode exclusivity, timed room-objective scheduling, ordinary combat coordination, doors/keys and teammate completion updates. |
| Blood Camp | `features/impl/dungeon/BloodCamp.kt`, `DungeonListener`, `ScoreTracker`, Gyro targeting assistance | Adjacent-room preparation, no-LOS constraint, timer-driven doorway/Gyro/LCM sequence and portal navigation. |

`GyroHelper` is targeting visualization and `MageBeam` is a visual beam feature, not complete automatic ability/combat engines. Existing BloodCamp tracking and triggerbot are useful partial implementations, not the entire requested sequence.

Noamm `NotificationManager.push` supplies existing dependency toasts. `PogObject` supplies existing persistence. Neither should be remade.
