# Phase 1 implementation ownership

| Owner | Responsibility |
| --- | --- |
| `/root/impl_coordinator` then `/root/impl_coordinator_sol` | Gradle harness and dispatcher, shared task/resource contracts; replacement owner handles bootstrap/input/lifecycle and isolated development fixture |
| `/root/impl_map` then `/root/impl_map_sol` | Map feature, settings, screen, renderer adapter, map contracts and tests |
| `/root/impl_map_sol` | Replacement map owner for Plan 01-03 after `/root/impl_map` reached its usage limit; inherits its map edits and exclusively completes map layout, adapter and map tests |
| `/root` | Phase orchestration, verification and release coordination |

The Phase 1 researcher was `/root/phase1_research`. Movement, secrets, loadouts, Clear/Rush and Blood Camp receive separate implementation owners during their assigned phases.
