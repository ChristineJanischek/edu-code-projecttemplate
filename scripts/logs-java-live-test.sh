#!/usr/bin/env bash
set -euo pipefail

# shellcheck source=scripts/lib/compose.sh
source "$(dirname "$0")/lib/compose.sh"
# shellcheck source=scripts/lib/java-live-test-local.sh
source "$(dirname "$0")/lib/java-live-test-local.sh"

if has_compose_cmd && docker_daemon_available; then
	compose_cmd logs -f java-live-test
	exit 0
fi

if ! java_live_test_local_is_running; then
	echo "[java-live-test] Fehler: Weder Docker-Container noch lokaler Watch laufen" >&2
	echo "[java-live-test] Starte zuerst: bash scripts/start-java-live-test.sh" >&2
	exit 1
fi

tail -f "$(java_live_test_log_file)"