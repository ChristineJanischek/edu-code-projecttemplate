#!/usr/bin/env bash
set -euo pipefail

if [[ ! -f .env ]]; then
  cp .env.example .env
  echo "[start] .env aus .env.example erzeugt"
fi

docker compose up -d --build

echo "[start] Dienste gestartet"
echo "[start] PHP-Webapp:   http://localhost:${PHP_WEB_PORT:-8080}"
echo "[start] Python-API:   http://localhost:${PYTHON_API_PORT:-8000}/health"
