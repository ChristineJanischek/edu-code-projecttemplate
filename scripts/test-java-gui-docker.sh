#!/usr/bin/env bash
set -euo pipefail

# shellcheck source=scripts/lib/compose.sh
source "$(dirname "$0")/lib/compose.sh"

if ! require_compose_cmd; then
  echo "[java-gui-test] Fehler: Docker Compose nicht verfuegbar" >&2
  echo "[java-gui-test] Hinweis: Nutze fuer lokale GUI-Tests: java -cp build/java volleyball.MainWindow" >&2
  exit 1
fi

if ! docker_daemon_available; then
  echo "[java-gui-test] Fehler: Docker-Daemon nicht erreichbar" >&2
  echo "[java-gui-test] Hinweis: Codespace neu starten oder Docker-Verfuegbarkeit pruefen" >&2
  exit 1
fi

compose_cmd --profile java-live-test build java-live-test

compose_cmd --profile java-live-test run --rm --no-deps -e JAVA_TOOL_OPTIONS= java-live-test bash -lc '
set -euo pipefail
mkdir -p build/java
javac -d build/java src/volleyball/*.java

status=0
timeout 8s xvfb-run -a java -cp build/java volleyball.MainWindow || status=$?

if [[ "$status" -eq 0 ]]; then
  echo "[java-gui-test] GUI gestartet und beendet"
  exit 0
fi

if [[ "$status" -eq 124 ]]; then
  echo "[java-gui-test] GUI-Start erfolgreich (8s Laufzeitfenster erreicht)"
  exit 0
fi

echo "[java-gui-test] Fehler: GUI-Start fehlgeschlagen (Exit: $status)" >&2
exit "$status"
'
