#!/usr/bin/env bash

java_live_test_pid_file() {
  echo "build/java-live-test.pid"
}

java_live_test_log_file() {
  echo "build/java-live-test.log"
}

java_live_test_local_is_running() {
  local pid_file pid
  pid_file="$(java_live_test_pid_file)"

  [[ -f "$pid_file" ]] || return 1
  pid="$(cat "$pid_file" 2>/dev/null || true)"

  [[ "$pid" =~ ^[0-9]+$ ]] || return 1
  kill -0 "$pid" >/dev/null 2>&1
}

java_live_test_local_start() {
  local pid_file log_file pid

  if java_live_test_local_is_running; then
    return 0
  fi

  mkdir -p build
  pid_file="$(java_live_test_pid_file)"
  log_file="$(java_live_test_log_file)"

  nohup bash ./scripts/java-live-test-runner.sh watch >"$log_file" 2>&1 &
  pid=$!
  echo "$pid" >"$pid_file"

  if ! kill -0 "$pid" >/dev/null 2>&1; then
    return 1
  fi

  return 0
}

java_live_test_local_stop() {
  local pid_file pid
  pid_file="$(java_live_test_pid_file)"

  if [[ ! -f "$pid_file" ]]; then
    return 1
  fi

  pid="$(cat "$pid_file" 2>/dev/null || true)"

  if [[ ! "$pid" =~ ^[0-9]+$ ]]; then
    rm -f "$pid_file"
    return 1
  fi

  if kill -0 "$pid" >/dev/null 2>&1; then
    kill "$pid"
  fi

  rm -f "$pid_file"
  return 0
}
