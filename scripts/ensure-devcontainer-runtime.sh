#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
DEVCONTAINER_FILE="$ROOT_DIR/.devcontainer/devcontainer.json"

fail() {
  echo "[devcontainer] Fehler: $1" >&2
  exit 1
}

info() {
  echo "[devcontainer] $1"
}

[[ -f "$DEVCONTAINER_FILE" ]] || fail "Devcontainer-Konfiguration fehlt: $DEVCONTAINER_FILE"
command -v jq >/dev/null 2>&1 || fail "jq ist nicht installiert"

if [[ "$(jq -r '.features | has("ghcr.io/devcontainers/features/docker-in-docker:2")' "$DEVCONTAINER_FILE")" != "true" ]]; then
  fail "Feature docker-in-docker:2 ist nicht aktiviert"
fi

if [[ "$(jq -r '.features["ghcr.io/devcontainers/features/java:1"].version // ""' "$DEVCONTAINER_FILE")" != "21" ]]; then
  fail "Feature java:1 ist nicht auf Version 21 gesetzt"
fi

command -v java >/dev/null 2>&1 || fail "Java ist nicht installiert"
java_version_line="$(java -version 2>&1 | head -n 1)"
if ! echo "$java_version_line" | grep -Eq '"21(\.|$)'; then
  info "Hinweis: Java-Laufzeit ist aktuell nicht 21 ($java_version_line)"
  info "Hinweis: Bitte Codespace/Devcontainer neu bauen, damit die konfigurierte Java-21-Feature-Version aktiv wird"
fi

command -v docker >/dev/null 2>&1 || fail "Docker CLI ist nicht installiert"
docker compose version >/dev/null 2>&1 || fail "Docker Compose ist nicht verfuegbar"

if docker info >/dev/null 2>&1; then
  info "Docker-Daemon erreichbar"
else
  info "Hinweis: Docker-Daemon aktuell nicht erreichbar (Codespace-Restriction moeglich)"
fi

info "Devcontainer-Anforderungen geprueft: docker-in-docker aktiv, Java-Feature auf 21 konfiguriert"
