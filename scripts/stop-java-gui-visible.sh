#!/usr/bin/env bash
set -euo pipefail

# shellcheck source=scripts/lib/gui-visible.sh
source "$(dirname "$0")/lib/gui-visible.sh"

gui_app_stop
gui_bridge_stop

echo "[gui-visible] MainWindow und GUI-Bridge gestoppt"
