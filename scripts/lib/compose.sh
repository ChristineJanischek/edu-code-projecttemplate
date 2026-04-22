#!/usr/bin/env bash

compose_cmd() {
  if command -v docker-compose >/dev/null 2>&1; then
    docker-compose "$@"
    return
  fi

  if command -v docker >/dev/null 2>&1; then
    docker compose "$@"
    return
  fi

  echo "[docker] Fehler: Weder docker-compose noch docker sind installiert" >&2
  return 1
}

require_compose_cmd() {
  if command -v docker-compose >/dev/null 2>&1; then
    return 0
  fi

  if command -v docker >/dev/null 2>&1; then
    return 0
  fi

  echo "[docker] Fehler: Kein Compose-Kommando verfuegbar" >&2
  return 1
}