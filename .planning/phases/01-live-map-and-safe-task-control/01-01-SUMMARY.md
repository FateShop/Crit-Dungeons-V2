---
phase: 01-live-map-and-safe-task-control
plan: 01
subsystem: map
tags: [fabric, kotlin, noamm, map]
requires: []
provides: [live map popup, configurable binding, isolated map contract]
affects: [01-02, 01-03]
key-files:
  created: [src/main/kotlin/com/github/noamm9/untitled/features/impl/critaddons/DungeonMapPopup.kt, src/main/kotlin/com/github/noamm9/untitled/features/impl/critaddons/DungeonMapScreen.kt, docs/implementation-ownership.md, docs/noamm-reuse-evidence.md]
  modified: [build.gradle.kts, gradle.properties, src/main/resources/untitled.mixins.json]
---

# Phase 1 Plan 1 — Live map walking skeleton

Noamm owns the popup key, its persistence, and rendering of actual dungeon rooms. The popup consumes a genuine press in gameplay or its own screen and uses the current binding to close. Keyboard Esc retains the native dismissal path. The coordinator owns emergency handling in 01-04.

| Task | Commit | Evidence |
| --- | --- | --- |
| Harness and owners | 7d9b28e | Microsoft JDK 25.0.1, Gradle 9.8.2, expected missing OPEN behavior at MapContractTest line 10 |
| Category and persistent setting | 33e909e | map-settings fixture passed and both Kotlin/Java compiled for 26.2 |
| Native map popup | 17075f0 | map contract passed, 26.2 build succeeded and emitted Crit Addons-1.0-26.2-cheat.jar |

Exact dependency SHA-256 and pinned source are in docs/noamm-reuse-evidence.md. The shipped CategoryPanel directly reads the enum name, so a small render-expression mixin gives CRITADDONS its requested display name. The template sprint feature was removed.

Run the map suite with JDK 25 and `gradlew.bat :26.2:controlTest --args=map :26.2:build`. The isolated test exercises the binding-match boundary using fixture values. Actual config persistence across relaunch, menu discovery, Mixin application and live dungeon rendering await the target-client validation in 01-05. The staged JAR is an intermediate build.

## Self-Check: PASSED

Confirmed all three task commits and the generated JAR. Prerequisite notifications, complete viewport layout and additional map-state handling remain assigned to 01-03.
