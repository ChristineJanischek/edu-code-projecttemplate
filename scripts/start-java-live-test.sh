#!/usr/bin/env bash
set -euo pipefail

# shellcheck source=scripts/lib/compose.sh
source "$(dirname "$0")/lib/compose.sh"
# shellcheck source=scripts/lib/java-live-test-local.sh
source "$(dirname "$0")/lib/java-live-test-local.sh"

if has_compose_cmd && docker_daemon_available; then
	compose_cmd --profile java-live-test up -d --build java-live-test

	echo "[java-live-test] Container gestartet"
	echo "[java-live-test] Logs anzeigen: bash scripts/logs-java-live-test.sh"
	exit 0
fi

echo "[java-live-test] Hinweis: Docker/Compose nicht nutzbar, starte lokalen Watch-Modus" >&2
if ! java_live_test_local_start; then
	echo "[java-live-test] Fehler: Lokaler Watch-Modus konnte nicht gestartet werden" >&2
	exit 1
fi

echo "[java-live-test] Lokaler Watch gestartet"
echo "[java-live-test] Logs anzeigen: bash scripts/logs-java-live-test.sh"