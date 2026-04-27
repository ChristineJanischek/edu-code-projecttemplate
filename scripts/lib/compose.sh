#!/usr/bin/env bash

has_docker_compose_plugin() {
  if ! command -v docker >/dev/null 2>&1; then
    return 1
  fi

  docker compose version >/dev/null 2>&1
}

has_compose_cmd() {
  if command -v docker-compose >/dev/null 2>&1; then
    return 0
  fi

  if has_docker_compose_plugin; then
    return 0
  fi

  return 1
}

docker_daemon_available() {
  if ! command -v docker >/dev/null 2>&1; then
    return 1
  fi

  docker info >/dev/null 2>&1
}

compose_cmd() {
  if command -v docker-compose >/dev/null 2>&1; then
    docker-compose "$@"
    return
  fi

  if has_docker_compose_plugin; then
    docker compose "$@"
    return
  fi

  echo "[docker] Fehler: Weder docker-compose noch docker compose sind verfuegbar" >&2
  return 1
}

require_compose_cmd() {
  if has_compose_cmd; then
    return 0
  fi

  echo "[docker] Fehler: Kein Compose-Kommando verfuegbar (benoetigt: docker-compose oder docker compose)" >&2
  echo "[docker] Hinweis: Entweder Docker/Compose installieren oder lokale Tests mit 'bash scripts/test-java.sh' ausfuehren" >&2
  return 1
}