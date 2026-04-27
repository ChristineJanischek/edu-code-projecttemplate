#!/usr/bin/env bash
set -euo pipefail

if [[ ! -f .env ]]; then
  cp .env.example .env
  echo "[bootstrap] .env aus .env.example erzeugt"
fi

chmod +x scripts/*.sh

bash scripts/ensure-devcontainer-runtime.sh
bash scripts/ensure-vscode-extensions.sh

echo "[bootstrap] Fertig. Nutze: ./scripts/start-services.sh"
echo "[bootstrap] Java-Live-Test in Docker: ./scripts/start-java-live-test.sh"
