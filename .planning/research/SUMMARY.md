# Project Research Summary

**Project:** Crit Addons
**Researched:** 2026-11-12
**Confidence:** High for inspected reuse inventory; medium for proposed architecture; live execution unverified.

Six distinct researchers inspected map, movement, secrets, loadouts, Clear/Rush and Blood Camp. Existing Noamm systems cover much of the data, presentation and low-level behavior. New work is principally coordinated task execution, time-cost movement planning, exact secret optimization, cache verification, combat orchestration and floor-specific integration. Reuse private functionality through narrow bridges where necessary, rather than copying partial features.

Use Kotlin/Fabric/Stonecutter with JDK 25, Minecraft 26.2 and the exact Noamm 1.3.5 cheat dependency. Pinned source and installed artifact both matter: mutable source HEAD differs and public cheat source is incomplete. See [STACK.md](STACK.md) and [FEATURES.md](FEATURES.md) for evidence boundaries and the inventory.

The main new foundation is a shared FIFO/resource coordinator consumed by all six features. Timed navigation precedes physical route verification; exact secret routing depends on a fully defined graph, prerequisites and all-doorway combinations. Do not confuse cached empirical success with future server acceptance or claim optimality while relevant graph information remains unknown. See [ARCHITECTURE.md](ARCHITECTURE.md).

Critical implementation gates include Noamm Etherwarp's live prediction/crouch dependence, loadout application acknowledgement, Blood timing accuracy, GUI/deadline conflicts, no-line-of-sight feasibility, and rearming after cancellation. See [PITFALLS.md](PITFALLS.md).

No scope has been deferred by research. No new gameplay code, build, installation or live dungeon validation was performed. The existing source tag and installed JAR paths are recorded in STACK.md; research documents contain no historical custom-project code.
