# Noamm 1.3.5 map integration

The installed `NoammAddons-1.3.5-26.2-cheat.jar` has SHA-256 `6212888ddce467fb0da81bc3df9e285c39867fc8aa9547bb4976eec911f3f6fd`. Microsoft JDK 25.0.1 from PrismLauncher's `java-runtime-epsilon` includes javac and javap.

On the installed artifact, javap confirms the public three-argument `MapRenderer.draw(GuiGraphicsExtractor, boolean, boolean)` returning a Kotlin pair. At source commit `460cfaf5d8760fa1146f5d2433715b7eca9bf319`, the non-example path draws scanner rooms and unique-room text. Pass `example=false` and `extraInfo=false` in the popup. The two-argument overload enables extra information.

The isolated JVM suite exercises the popup contract. Client verification separately checks actual feature discovery, saved key binding after relaunch and real dungeon rendering with the installed dependency.

On JDK 25.0.1, Gradle 9.8.2 completed the 26.2 task inventory. The initial controlTest map run compiled its isolated Kotlin sources and failed at MapContractTest.kt line 10, where a pressed default binding must request OPEN.
