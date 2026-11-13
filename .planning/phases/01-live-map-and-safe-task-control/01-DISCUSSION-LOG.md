# Phase 1: Live Map and Safe Task Control - Discussion Log

> Audit trail only. Downstream research/planning/execution reads 01-CONTEXT.md, not this log.

**Date:** 2026-11-12
**Areas discussed:** Restart/settings separation, manual-input and GUI pauses, popup appearance, notification presentation.

## Restart and settings

| Question | Alternatives presented | Final choice |
| --- | --- | --- |
| After Esc, disable the setting or leave enabled but stopped? | Automatically turn off; leave enabled requiring off/on; freeform | Initial numeric selection favored disabling. User then clarified only the player should turn the setting off. The correction supersedes that selection. |
| After fatal error, disable the setting? | Disable; leave enabled but stopped | Same correction applies: settings remain enabled; execution stops. |
| After normal run/lifecycle termination? | Turn off; keep enabled for next run | Keep enabled, reset execution and queue. |
| Persist enabled state across Minecraft restart? | Remember enabled mode; start client with both off | Remember enabled mode and start fresh in a valid dungeon. |
| After Esc/error, resume next dungeon or require toggle? | Next dungeon, or deliberate off/on sooner; only off/on | Explicit voice answer selected automatic fresh start in the next dungeon run. No immediate same-run restart. |

The assistant's early wording conflated stopping current execution with disabling settings. The user corrected this distinction; future agents must use the corrected rule.

## Manual input and GUI pauses

| Question | Alternatives presented | Final choice |
| --- | --- | --- |
| Block player inputs or pause? | Block movement/attacks/item use/inventories while allowing Esc/M; pause while player interacts | Initial voice mention of blocking was followed by a clarification: freeze automation and wait for manual input to finish. Use pause-and-resume, not blocked input. |
| Automatically resume or explicit unpause? | Resume on released controls/closed screen; explicit unpause | Final voice answer selected automatic resume. |
| Show paused/resuming messages? | Yes; no | No, keep routine pauses silent. |
| More changes to this area? | Open invitation | User declined further changes in voice. |

Already-approved GUI preservation, loadout screen ownership and death/revival rules remain in force. No Blood-specific GUI override was agreed.

## Popup appearance

| Option | Description | Selected |
| --- | --- | --- |
| Centered medium popup | Game visible around the map | Yes |
| Large popup | Covers most of the screen | No |
| Freeform | Another preferred layout | No |

User additionally objected to further trivial/cosmetic questions. Routine visual details are agent discretion; do not continue the default cosmetic questionnaire merely to fill a question count.

## Status and dependency messages

- User explicitly rejected ordinary paused/resuming messages in voice.
- Existing Noamm dependency/error toasts are already required by approved scope. No cosmetic decision is needed to reuse them.
- Required fatal-error reporting and later optimization progress remain intact.

## Agent's Discretion

Routine layout sizing/spacing/backdrop and existing Noamm presentation conventions. Ask only about material gameplay decisions that cannot be resolved from approved requirements.

## Existing Later-Phase Questions

Blood deadline versus GUI/manual-input pauses, impossible-deadline handling, no-LOS secret feasibility and Auto Clear post-camp transition remain assigned to their affected phases. No new capability or scope deferral was introduced.
