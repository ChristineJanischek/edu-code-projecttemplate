#!/usr/bin/env bash
set -euo pipefail

# shellcheck source=scripts/lib/gui-visible.sh
source "$(dirname "$0")/lib/gui-visible.sh"

bash "$(dirname "$0")/ensure-codespace-gui.sh"

mkdir -p build/java
javac -d build/java src/volleyball/*.java

gui_app_start

echo "[gui-visible] MainWindow gestartet"
echo "[gui-visible] noVNC URL: $(gui_visible_url)"
echo "[gui-visible] Logs: build/gui-visible/logs/java-mainwindow.log"
