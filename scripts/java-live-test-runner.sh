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

watch_fingerprint() {
  find "$watch_path" -type f -name "*.java" -exec sha256sum {} + 2>/dev/null | sort | sha256sum | awk '{print $1}'
}

case "$mode" in
  once)
    run_java_test
    ;;
  watch)
    echo "[java-live-test] Starte initialen Headless-Test"
    run_watch_cycle
    echo "[java-live-test] Beobachte $watch_path auf Aenderungen"

    if command -v inotifywait >/dev/null 2>&1; then
      while inotifywait -qq -r -e close_write,create,delete,move "$watch_path"; do
        echo "[java-live-test] Aenderung erkannt, teste erneut..."
        run_watch_cycle
      done
    else
      echo "[java-live-test] Hinweis: inotifywait fehlt, nutze Polling-Fallback (2s)"
      last_fingerprint="$(watch_fingerprint)"

      while true; do
        sleep 2
        current_fingerprint="$(watch_fingerprint)"

        if [[ "$current_fingerprint" != "$last_fingerprint" ]]; then
          echo "[java-live-test] Aenderung erkannt, teste erneut..."
          run_watch_cycle
          last_fingerprint="$current_fingerprint"
        fi
      done
    fi
    ;;
  *)
    echo "[java-live-test] Unbekannter Modus: $mode"
    echo "[java-live-test] Erlaubte Modi: once | watch"
    exit 1
    ;;
esac