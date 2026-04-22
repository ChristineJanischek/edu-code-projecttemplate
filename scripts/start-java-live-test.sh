#!/usr/bin/env bash
set -euo pipefail

# shellcheck source=scripts/lib/compose.sh
source "$(dirname "$0")/lib/compose.sh"

require_compose_cmd
compose_cmd --profile java-live-test up -d --build java-live-test

echo "[java-live-test] Container gestartet"
echo "[java-live-test] Logs anzeigen: bash scripts/logs-java-live-test.sh"