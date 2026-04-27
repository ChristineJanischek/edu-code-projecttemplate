#!/usr/bin/env bash
set -euo pipefail

mkdir -p build/java
javac -d build/java src/volleyball/*.java

# Ressourcen (z. B. Icons) in den Klassenpfad uebernehmen.
if [[ -d src/volleyball/images ]]; then
	mkdir -p build/java/images
	cp -f src/volleyball/images/* build/java/images/
fi

echo "[java] Kompilierung erfolgreich"

echo "[java] Starte Headless-Modell-Smoke-Tests..."
java -cp build/java volleyball.ModelSmokeTest

echo "[java] Modell-Tests erfolgreich"
