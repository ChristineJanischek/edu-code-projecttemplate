#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
DEVCONTAINER_FILE="$ROOT_DIR/.devcontainer/devcontainer.json"

fail() {
  echo "[vscode-ext] Fehler: $1" >&2
  exit 1
}

info() {
  echo "[vscode-ext] $1"
}

[[ -f "$DEVCONTAINER_FILE" ]] || fail "Devcontainer-Konfiguration fehlt: $DEVCONTAINER_FILE"

command -v jq >/dev/null 2>&1 || fail "jq ist nicht installiert"

if ! command -v code >/dev/null 2>&1; then
  if [[ "${CODESPACES:-false}" == "true" ]]; then
    fail "VS Code CLI ('code') ist im Codespace nicht verfuegbar"
  fi

  info "VS Code CLI ('code') nicht gefunden. Ueberspringe lokalen Extension-Check ausserhalb von Codespaces."
  exit 0
fi

mapfile -t required_extensions < <(jq -r '.customizations.vscode.extensions[]? // empty' "$DEVCONTAINER_FILE")

[[ ${#required_extensions[@]} -gt 0 ]] || fail "Keine Pflicht-Erweiterungen in $DEVCONTAINER_FILE definiert"

mapfile -t installed_extensions < <(code --list-extensions 2>/dev/null || true)

install_count=0
for extension in "${required_extensions[@]}"; do
  if printf '%s\n' "${installed_extensions[@]}" | grep -ixq "$extension"; then
    info "OK: $extension"
    continue
  fi

  info "Installiere fehlende Erweiterung: $extension"
  code --install-extension "$extension" --force >/dev/null
  install_count=$((install_count + 1))
done

mapfile -t installed_extensions_after < <(code --list-extensions 2>/dev/null || true)
for extension in "${required_extensions[@]}"; do
  if ! printf '%s\n' "${installed_extensions_after[@]}" | grep -ixq "$extension"; then
    fail "Erweiterung fehlt nach Installationsversuch: $extension"
  fi
done

info "Pflicht-Erweiterungen verfuegbar (${#required_extensions[@]}), neu installiert: $install_count"
