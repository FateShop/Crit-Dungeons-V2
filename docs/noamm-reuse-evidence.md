# Noamm 1.3.5 map integration

The installed `NoammAddons-1.3.5-26.2-cheat.jar` has SHA-256 `6212888ddce467fb0da81bc3df9e285c39867fc8aa9547bb4976eec911f3f6fd`. Microsoft JDK 25.0.1 from PrismLauncher's `java-runtime-epsilon` includes javac and javap.

On the installed artifact, javap confirms the public three-argument `MapRenderer.draw(GuiGraphicsExtractor, boolean, boolean)` returning a Kotlin pair. At source commit `460cfaf5d8760fa1146f5d2433715b7eca9bf319`, the non-example path draws scanner rooms and unique-room text. Pass `example=false` and `extraInfo=false` in the popup. The two-argument overload enables extra information.

The isolated JVM suite exercises the popup contract. Client verification separately checks actual feature discovery, saved key binding after relaunch and real dungeon rendering with the installed dependency.

On JDK 25.0.1, Gradle 9.8.2 completed the 26.2 task inventory. The initial controlTest map run compiled its isolated Kotlin sources and failed at MapContractTest.kt line 10, where a pressed default binding must request OPEN.

## Production input/lifecycle boundaries (01-04)

JDK 25 javap on the exact installed artifact confirms public DungeonListener getters for dungeonStartTime (DualTime.real), dungeonEnded and thePlayer.isDead. Pinned DungeonListener.kt at 460cfaf5d8760fa1146f5d2433715b7eca9bf319 sets ended on run-end chat, emits RunEndedEvent from a coroutine, marks ghost via HAUNT_ABILITY/tab facts, and resets on WorldChangeEvent. Crit marshals the run-end observation to Minecraft's executor with its published source epoch; ticks sample authoritative facts rather than mutating Noamm state. The initial valid attachment creates one epoch, repeated validity never creates another, and boss/end remains terminal until an exit/world boundary or changed non-null start timestamp. setLevel invalidates the old world before replacement. No serverId-as-run-ID assumption is used.

The mapped 26.2 minecraft-merged.jar confirms KeyboardHandler.keyPress(long,int,KeyEvent), MouseHandler.onButton(long,MouseButtonInfo,int), onMove(long,double,double), onScroll(long,double,double), Window.onFocus(long,boolean), Minecraft.tick() and setLevel(ClientLevel). Non-cancelling HEAD observers run at priority 2000 for keyboard/mouse to precede normal cancellable consumers. Only raw callback press/release/motion enters ManualInputState; generated KeyMapping or rotation output does not. Focus clears stale held state and remains a pause until regain. Two client ticks debounce pointer motion. Gui.screen()/setScreen are the supported 26.2 GUI boundary. Only Crit's map is dismissed on terminal/world transition; unrelated screens remain intact.

ActionUtils exact private static fields: actionQueue:PriorityBlockingQueue, running:boolean, lock:Object, isBlocked:volatile boolean. Read-only static accessors snapshot all three busy facts while synchronized on lock; every Crit adapter effect refreshes actual lifecycle/pause facts and yields while any busy fact is true. Crit never calls ActionUtils.reset or mutates/removes shared actions. This supports coexistence at this queue boundary; arbitrary direct Noamm writers still require consumer-specific audits. Actual applied-mixin and live queue-sentinel evidence belongs to 01-05/01-06; successful compilation does not prove live application.
