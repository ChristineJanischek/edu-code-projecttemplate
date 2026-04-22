#!/usr/bin/env bash
set -euo pipefail

mode="${1:-once}"
watch_path="src/volleyball"

run_java_test() {
  ./scripts/test-java.sh
}

run_watch_cycle() {
  if run_java_test; then
    echo "[java-live-test] Headless-Test erfolgreich"
  else
    echo "[java-live-test] Headless-Test fehlgeschlagen; warte auf die naechste Aenderung"
  fi
}

case "$mode" in
  once)
    run_java_test
    ;;
  watch)
    if ! command -v inotifywait >/dev/null 2>&1; then
      echo "[java-live-test] Fehler: inotifywait fehlt im Container"
      exit 1
    fi

    echo "[java-live-test] Starte initialen Headless-Test"
    run_watch_cycle
    echo "[java-live-test] Beobachte $watch_path auf Aenderungen"

    while inotifywait -qq -r -e close_write,create,delete,move "$watch_path"; do
      echo "[java-live-test] Aenderung erkannt, teste erneut..."
      run_watch_cycle
    done
    ;;
  *)
    echo "[java-live-test] Unbekannter Modus: $mode"
    echo "[java-live-test] Erlaubte Modi: once | watch"
    exit 1
    ;;
esac