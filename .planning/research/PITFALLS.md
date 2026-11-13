# Research pitfalls and phase gates

Research date: 2026-11-12. Findings are from pinned-source/binary inspection; live gameplay remains untested.

| Risk / early warning | Prevention | Address before |
| --- | --- | --- |
| Public HEAD differs from shipped 1.3.5; cheat source is incomplete | Pin source tag and inspect exact binary before declaring APIs absent | Integration and every bridge |
| Etherwarp hypothetical origins silently use pending ZeroPingCamera position/live crouch state | Adapt existing geometry through a narrow bridge; isolate hypothetical validation from live prediction; test crouched slab/landing cases | Movement graph expansion |
| Cached BFS blood topology mistaken for fastest executable route | Reuse topology while adding timed action costs; handle incomplete scans | Clear/Rush routing |
| Approximate or incomplete graph called mathematically optimal | Define graph/candidate coverage, resource/prerequisite costs and proof scope; unresolved relevant geometry blocks optimality claims | Secret optimizer |
| Secret coordinates mistaken for executable objectives | Include usable approach positions, keys/levers, interaction state and moving bats | Secret actions and proof model |
| Prior success mistaken for indefinite cached validity or unlimited packet throughput | Revalidate applicable geometry/capabilities, respect sequencing/cooldowns, detect corrections and invalidate failures | Cached movement executor |
| Noamm priority queue assumed FIFO; independent feature actions fight | Crit FIFO/resource coordinator above existing scheduling; avoid global resets | Coordinator |
| Menu closure mistaken for confirmed loadout | Observe authoritative application/rejection updates; do not resume combat on guessed success | Loadouts |
| Noamm Blood timer treated as guaranteed dialogue skip | Follow existing Kill indication and document accuracy limits; validate floor-specific behavior | Blood Camp |
| One-second deadline impossible during GUI pause, exact first-use search or distant secrets | Resolve policy with user; preserve visibility/mandatory Gyro constraints and report infeasibility | Blood controller |
| Esc/fatal stop immediately restarts a still-enabled mode | Specify rearming before enabling automatic execution | Coordinator |
| Missing room geometry treated as a planner bug without data context | Distinguish unavailable information in diagnostics while preserving the user's stop/report requirement | Recovery |

Remaining product decisions: popup-versus-GUI deadline handling; rearm semantics; Auto Clear's post-camp transition when score is insufficient. Do not silently select defaults for these.
