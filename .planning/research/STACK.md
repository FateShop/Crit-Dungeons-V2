# Stack research

Research date: 2026-11-12. Consolidated from six completed read-only feature investigations; no additional domain research or old-project inspection.

- Use the repository's Kotlin/Fabric/Stonecutter addon build with JDK 25; target Minecraft 26.2 and NoammAddons 1.3.5 cheat only.
- Public release source: [Noamm tag 1.3.5](https://github.com/Noamm9/NoammAddons/tree/1.3.5), commit `460cfaf5d8760fa1146f5d2433715b7eca9bf319`. Do not substitute mutable HEAD `24fab30d` for release contracts.
- Exact installed artifact inspected by researchers: `C:\Users\Michael\AppData\Roaming\PrismLauncher\instances\26.2\minecraft\mods\NoammAddons-1.3.5-26.2-cheat.jar`. Bytecode inventory/method inspection was read-only; the artifact was not executed.
- Public source omits some cheat implementations. Inspect the shipped artifact before claiming any functionality is absent.
- Reuse Noamm Feature/settings/category integration, `NotificationManager`, `PogObject`, world/room transforms, item identification, movement geometry, event scheduling and supported networking interfaces.
- Add narrow Mixin accessors/invokers where useful existing implementations are private. Do not copy Noamm systems into Crit packages.
- Implement exact algorithms over a documented finite graph/model. No extra optimizer dependency has been selected or justified yet.
- Preserve the repository's Minecraft-version handling, but ship only the required 26.2 target. Release JARs remain separate from planning commits.

Confidence: high for declared build and inspected integration inventory; live behavior and all-floor compatibility remain unverified.
