#!/usr/bin/env bash

GUI_DISPLAY=":99"
GUI_RFB_PORT="5901"
GUI_WEB_PORT="6080"
GUI_STATE_DIR="build/gui-visible"
GUI_PID_DIR="$GUI_STATE_DIR/pids"
GUI_LOG_DIR="$GUI_STATE_DIR/logs"

_gui_pid_file() {
  local name="$1"
  echo "$GUI_PID_DIR/${name}.pid"
}

gui_visible_url() {
  if [[ -n "${CODESPACE_NAME:-}" && -n "${GITHUB_CODESPACES_PORT_FORWARDING_DOMAIN:-}" ]]; then
    echo "https://${CODESPACE_NAME}-${GUI_WEB_PORT}.${GITHUB_CODESPACES_PORT_FORWARDING_DOMAIN}"
    return 0
  fi

  echo "http://localhost:${GUI_WEB_PORT}/vnc.html"
}

gui_required_commands() {
  printf '%s\n' Xvfb x11vnc websockify fluxbox
}

gui_missing_commands() {
  local cmd
  local missing=()

  while IFS= read -r cmd; do
    if ! command -v "$cmd" >/dev/null 2>&1; then
      missing+=("$cmd")
    fi
  done < <(gui_required_commands)

  printf '%s\n' "${missing[@]}"
}

gui_install_missing_dependencies() {
  local missing
  mapfile -t missing < <(gui_missing_commands)

  if [[ ${#missing[@]} -eq 0 ]]; then
    return 0
  fi

  echo "[gui-visible] Installiere fehlende Pakete: ${missing[*]}"

  if command -v sudo >/dev/null 2>&1; then
    sudo apt-get update >/dev/null
    sudo apt-get install -y --no-install-recommends xvfb x11vnc novnc websockify fluxbox xterm >/dev/null
  else
    apt-get update >/dev/null
    apt-get install -y --no-install-recommends xvfb x11vnc novnc websockify fluxbox xterm >/dev/null
  fi
}

gui_novnc_web_dir() {
  if [[ -d "/usr/share/novnc" ]]; then
    echo "/usr/share/novnc"
    return 0
  fi

  if [[ -d "/usr/share/novnc/utils" ]]; then
    echo "/usr/share/novnc"
    return 0
  fi

  return 1
}

gui_prepare_dirs() {
  mkdir -p "$GUI_PID_DIR" "$GUI_LOG_DIR"
}

gui_is_pid_running() {
  local pid_file="$1"
  local pid

  [[ -f "$pid_file" ]] || return 1
  pid="$(cat "$pid_file" 2>/dev/null || true)"
  [[ "$pid" =~ ^[0-9]+$ ]] || return 1
  kill -0 "$pid" >/dev/null 2>&1
}

gui_write_pid() {
  local name="$1"
  local pid="$2"
  echo "$pid" > "$(_gui_pid_file "$name")"
}

gui_start_process() {
  local name="$1"
  shift

  local pid_file
  pid_file="$(_gui_pid_file "$name")"

  if gui_is_pid_running "$pid_file"; then
    return 0
  fi

  nohup "$@" >"$GUI_LOG_DIR/${name}.log" 2>&1 &
  gui_write_pid "$name" "$!"

  if ! gui_is_pid_running "$pid_file"; then
    echo "[gui-visible] Fehler: Prozess '${name}' konnte nicht gestartet werden" >&2
    return 1
  fi

  return 0
}

gui_bridge_is_running() {
  gui_is_pid_running "$(_gui_pid_file xvfb)" || return 1
  gui_is_pid_running "$(_gui_pid_file fluxbox)" || return 1
  gui_is_pid_running "$(_gui_pid_file x11vnc)" || return 1
  gui_is_pid_running "$(_gui_pid_file websockify)" || return 1
}

gui_bridge_start() {
  local web_dir

  gui_prepare_dirs
  gui_install_missing_dependencies

  web_dir="$(gui_novnc_web_dir)" || {
    echo "[gui-visible] Fehler: noVNC Web-Verzeichnis nicht gefunden" >&2
    return 1
  }

  gui_start_process xvfb Xvfb "$GUI_DISPLAY" -screen 0 1440x900x24
  gui_start_process fluxbox env DISPLAY="$GUI_DISPLAY" fluxbox
  gui_start_process x11vnc x11vnc -display "$GUI_DISPLAY" -forever -shared -rfbport "$GUI_RFB_PORT" -nopw -listen 0.0.0.0
  gui_start_process websockify websockify --web="$web_dir" "$GUI_WEB_PORT" "127.0.0.1:${GUI_RFB_PORT}"

  if ! gui_bridge_is_running; then
    echo "[gui-visible] Fehler: GUI-Bridge ist nicht vollstaendig aktiv" >&2
    return 1
  fi

  return 0
}

gui_stop_process() {
  local name="$1"
  local pid_file
  local pid

  pid_file="$(_gui_pid_file "$name")"
  [[ -f "$pid_file" ]] || return 0

  pid="$(cat "$pid_file" 2>/dev/null || true)"
  if [[ "$pid" =~ ^[0-9]+$ ]] && kill -0 "$pid" >/dev/null 2>&1; then
    kill "$pid" >/dev/null 2>&1 || true
  fi

  rm -f "$pid_file"
}

gui_bridge_stop() {
  gui_stop_process websockify
  gui_stop_process x11vnc
  gui_stop_process fluxbox
  gui_stop_process xvfb
}

gui_app_pid_file() {
  echo "$(_gui_pid_file java-mainwindow)"
}

gui_app_stop() {
  gui_stop_process java-mainwindow
}

gui_app_start() {
  local pid_file

  pid_file="$(gui_app_pid_file)"
  if gui_is_pid_running "$pid_file"; then
    gui_app_stop
  fi

  nohup env DISPLAY="$GUI_DISPLAY" java -cp build/java volleyball.MainWindow >"$GUI_LOG_DIR/java-mainwindow.log" 2>&1 &
  gui_write_pid java-mainwindow "$!"

  if ! gui_is_pid_running "$pid_file"; then
    echo "[gui-visible] Fehler: MainWindow konnte nicht gestartet werden" >&2
    return 1
  fi

  return 0
}
