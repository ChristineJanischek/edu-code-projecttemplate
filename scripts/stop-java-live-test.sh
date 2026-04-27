#!/usr/bin/env bash
set -euo pipefail

# shellcheck source=scripts/lib/compose.sh
source "$(dirname "$0")/lib/compose.sh"
# shellcheck source=scripts/lib/java-live-test-local.sh
source "$(dirname "$0")/lib/java-live-test-local.sh"

if has_compose_cmd && docker_daemon_available; then
	compose_cmd --profile java-live-test stop java-live-test

	echo "[java-live-test] Container gestoppt"
	exit 0
fi

if java_live_test_local_stop; then
	echo "[java-live-test] Lokaler Watch gestoppt"
	exit 0
fi

echo "[java-live-test] Hinweis: Kein laufender Container oder lokaler Watch gefunden" >&2
exit 1
