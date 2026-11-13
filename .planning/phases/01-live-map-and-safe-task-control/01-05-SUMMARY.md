---
phase: 01-live-map-and-safe-task-control
plan: "05"
subsystem: release
tags: [fabric, release, provenance, verification]
requires: [01-06]
provides: [published v0.1.0 release, compiled 26.2 JAR, immutable source and downloaded asset evidence]
affects: [phase-1-verification, phase-2]
tech-stack:
  added: []
  patterns: [immutable source provenance, independently downloaded release verification]
key-files:
  created: [scripts/Verify-Phase1.ps1, scripts/Verify-Release.ps1, docs/phase1-live-validation.md, docs/releases/v0.1.0.md, docs/releases/v0.1.0-evidence.json]
  modified: [gradle.properties, src/main/resources/fabric.mod.json]
key-decisions:
  - "Release claims cover the map viewer/session-control increment and explicitly disclose all unperformed authentic UI/dungeon checks."
requirements-completed: []
duration: 6min
completed: 2026-11-13
---

# Phase 1 Plan 5: Verified v0.1.0 Delivery Summary

Published Crit Addons v0.1.0 with its Minecraft 26.2 JAR, immutable source tag and matching independently downloaded asset hash; authentic UI/dungeon verification remains human_needed.

## Task Commits

| Task | Commit | Result |
| --- | --- | --- |
| Acceptance script and actual evidence | f2393a6 | Automated target/ABI/ownership/harness/build gates pass; live limits recorded |
| Versioned source and candidate checks | 4a26922; 5edcfa5 evidence | Immutable 0.1.0 source and candidate JAR verified |
| Publish and verify delivery | Source tag v0.1.0 at 4a26922; final evidence metadata commit follows | Root published and ran Published verification successfully |

## Published Artifact

- Release: https://github.com/FateShop/Crit-Dungeons-V2/releases/tag/v0.1.0
- Source/tag target: `4a2692201cdf62be22b5f5f1ad93d3fcbb744a9d`
- Download: https://github.com/FateShop/Crit-Dungeons-V2/releases/download/v0.1.0/Crit.Addons-0.1.0-26.2-cheat.jar
- Local candidate: `build/libs/Crit Addons-0.1.0-26.2-cheat.jar`
- Download verification copy: `build/release-verification/Crit.Addons-0.1.0-26.2-cheat.jar`
- Both size: 103417 bytes
- Both SHA-256: `7bbf919360b06ef7f0c72659f5ffefaaae91e189ba3e9186b94e016a778bfebe`
- Target: Minecraft 26.2, Java 25, NoammAddons 1.3.5 cheat
- Exact installed dependency SHA-256: `6212888ddce467fb0da81bc3df9e285c39867fc8aa9547bb4976eec911f3f6fd`
- Pinned Noamm source: `460cfaf5d8760fa1146f5d2433715b7eca9bf319`

Source was committed before the target build. Later candidate/publication evidence commits modify documentation/metadata only; they do not replace the immutable tagged build source. GitHub normalized asset-name spaces to dots; the manifest records the actual remote name separately from the local spaced path.

## Validation

Microsoft OpenJDK 25.0.1 and Gradle 9.8.2 ran the full isolated JVM suite, target 26.2 build, fixture compilation and production archive exclusion checks successfully. Verify-Phase1 checks exact dependency hash/ABI, required ownership and input mixin registration and forbidden shared reset/input suppression. Verify-Release Candidate verifies immutable source/version, unchanged source build inputs, JAR SHA/size, embedded Fabric version/target/dependency, Java class major 69, production classes/mixins and fixture exclusion.

Root established authorized publication through the existing credential helper without exposing credentials, published the semantic tag/release, fetched its remote metadata/tag, independently downloaded the JAR, and ran Verify-Release Published successfully. The downloaded JAR's hash/size agree with the candidate. Notes include additions, changes, removals, build/install instructions, source/dependency pins and limitations. No 26.3 asset or later gameplay capability was released.

## Authentic Client Evidence and Remaining Work

The isolated development client reached Minecraft initialization with fixture and production controls registered and no observed Mixin startup exception. It was an offline development snapshot before the final fixture refinement, not an installed final-JAR gameplay test. Mojang profile-certificate 401 prevented authenticated dungeon scenarios. Root's Minecraft window capture attempts failed with FrameArrived timeout; no screenshots or visual confirmation are claimed.

All actual map/control/death/revival/lifecycle/sentinel, relaunch persistence and F1–F7/M1–M7 dungeon checks remain unperformed, listed individually in docs/phase1-live-validation.md. Archive inspection and pure tests do not establish those results. The target JAR was not installed into the user's Prism instance. Implementation and release delivery are recorded; phase verification remains **human_needed**. No requirement checkboxes are marked complete from unsupported live evidence.

Later Blood GUI/deadline, impossible timing, no-LOS feasibility and Auto Clear post-camp gates remain open for their assigned phases. No boss combat, room travel, mob clearing, secret collection, automatic modes or Blood Camp is claimed in v0.1.0.

## Deviations from Plan

- [Rule 3 - Blocking] Verification explicitly selects the installed JDK 25 rather than inheriting the shell's JDK 21. Java --version uses stdout to avoid Windows PowerShell native stderr handling. Automated checks then passed.
- GitHub asset-name normalization required manifest artifact.name to use the remote dotted name while artifact.path retains the local spaced filename. This is a provenance correction, not a rebuilt or overwritten asset.
- Authentic UI/dungeon checks were not automatable in this session because of offline authentication and computer-capture failure. They are recorded as unperformed and retained for human-needed phase verification; no substitute success is asserted.

## Known Stubs

None in the released map/control increment. Later room actions are explicitly unavailable rather than simulated. Empty actualFloorCoverage in release evidence truthfully denotes no observed dungeon floors.

## Self-Check: PASSED

Confirmed verification scripts, release notes/manifest, immutable source commit and semantic tag, local candidate and independently downloaded JAR. Candidate verification passes with the published manifest and both artifact hashes match. Root reported Published verification PASS. No tracked file deletions.
