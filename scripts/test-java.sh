#!/usr/bin/env bash
set -euo pipefail

mkdir -p build/java
javac -d build/java src/volleyball/*.java

echo "[java] Kompilierung erfolgreich"
