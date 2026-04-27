#!/usr/bin/env bash
set -euo pipefail

# shellcheck source=scripts/lib/gui-visible.sh
source "$(dirname "$0")/lib/gui-visible.sh"

if [[ "${CODESPACES:-false}" != "true" ]]; then
  echo "[gui-visible] Hinweis: Nicht im Codespace. GUI-Bridge wird nicht automatisch gestartet."
  exit 0
fi

if gui_bridge_is_running; then
  echo "[gui-visible] GUI-Bridge bereits aktiv"
  echo "[gui-visible] noVNC URL: $(gui_visible_url)"
  exit 0
fi

if ! gui_bridge_start; then
  echo "[gui-visible] Fehler: GUI-Bridge konnte nicht gestartet werden" >&2
  exit 1
fi

echo "[gui-visible] GUI-Bridge aktiv"
echo "[gui-visible] noVNC URL: $(gui_visible_url)"
