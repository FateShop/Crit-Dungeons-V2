# Architecture research

Research date: 2026-11-12. Proposed design grounded in the six completed inspections, not an implemented architecture.

1. **Noamm bridges:** Version-specific adapters expose existing rooms, map drawing, secret interactions, required-enemy tracking, loadout actions and Blood timing. Keep narrow private access explicit and test against the exact artifact.
2. **Shared task coordinator:** One FIFO for manual tasks and one exclusive active automatic mode. Serialize movement, rotation, hotbar, GUI and combat resources. Preserve work on GUI/death pauses; cancel Crit work without globally resetting unrelated Noamm scheduling.
3. **Movement service:** Immutable geometry snapshots and candidate graph generation feed exact timed searches. Reuse Noamm targeting, distinguish speculative prediction from authoritative arrival, and own confirmation/verification/cached packet sequencing and recovery.
4. **Secret service:** Use Noamm canonical room/secret transforms and prerequisites. Optimize every valid doorway pairing using exact methods; expose exploration, calculation, proof, verification and execution states separately. Persist proven route data through existing storage, keeping per-run completion separate.
5. **Feature controllers:** Map, loadout, Clear/Rush and Blood controllers consume shared contracts. No feature may independently compete for rotation, movement, item slots or networking.
6. **Diagnostics and release:** Record proof scope, graph/cache versions, failures and missing geometry. Every completed feature/fix must have a reproducible 26.2 JAR, matching tag and release notes; planning-only work has no tag/release.

Recommended dependency order: integration/category/release setup and contracts; coordinator and map presentation; movement; exact secrets and loadouts (parallel where independent); Blood Camp and Clear/Rush integration; all-floor validation. Exact phase structure requires the user's requirements and structure-mode approval.

Concurrency: maintain a distinct implementation agent per requested feature, separate from its researcher. Assign shared coordinator ownership explicitly; implement shared contracts once and then parallelize independent consumers.

Heavy optimization must not block the client thread. World access/network actions obey Minecraft thread requirements; background search uses snapshots and cancellable jobs. Geometry/world changes invalidate snapshots and applicable proof/verification states.
