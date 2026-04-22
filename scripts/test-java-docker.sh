#!/usr/bin/env bash
set -euo pipefail

# shellcheck source=scripts/lib/compose.sh
source "$(dirname "$0")/lib/compose.sh"

require_compose_cmd
compose_cmd --profile java-live-test build java-live-test
compose_cmd --profile java-live-test run --rm --no-deps java-live-test bash ./scripts/java-live-test-runner.sh once

echo "[java-docker-test] Docker-Headless-Test erfolgreich"