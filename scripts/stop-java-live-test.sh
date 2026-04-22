#!/usr/bin/env bash
set -euo pipefail

# shellcheck source=scripts/lib/compose.sh
source "$(dirname "$0")/lib/compose.sh"

require_compose_cmd
compose_cmd --profile java-live-test stop java-live-test

echo "[java-live-test] Container gestoppt"