#!/usr/bin/env bash
set -euo pipefail

# shellcheck source=scripts/lib/compose.sh
source "$(dirname "$0")/lib/compose.sh"

if [[ ! -f .env ]]; then
  cp .env.example .env
  echo "[start] .env aus .env.example erzeugt"
fi

if grep -q "CHANGE_ME" .env; then
  echo "[start] Fehler: .env enthaelt noch CHANGE_ME-Werte. Bitte zuerst Zugangsdaten setzen."
  exit 1
fi

require_compose_cmd
compose_cmd up -d --build

echo "[start] Warte auf MySQL-Init und python-api..."
sleep 5
compose_cmd restart python-api >/dev/null 2>&1 || true

echo "[start] Dienste gestartet"
echo "[start] PHP-Webapp:   http://localhost:${PHP_WEB_PORT:-8080}"
echo "[start] Python-API:   http://localhost:${PYTHON_API_PORT:-8000}/health"
