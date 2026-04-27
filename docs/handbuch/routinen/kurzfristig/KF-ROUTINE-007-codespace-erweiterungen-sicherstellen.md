# KF-ROUTINE-007: Codespace-Erweiterungen sicherstellen

## Metadata
- **ID:** KF-ROUTINE-007
- **Kategorie:** kurzfristig
- **Haeufigkeit:** bei jedem Codespace-Start
- **Zeitaufwand:** 2-5 Minuten
- **Verantwortlicher:** Autor der Aenderung
- **Abhaengigkeiten:** qualitaets-gates-automatisierung.md, review-prozess.md
- **Version:** 1.0
- **Letzte Aktualisierung:** 27.04.2026

## Ziel
Sicherstellen, dass im aktiven Codespace jederzeit alle Pflicht-Erweiterungen aus der Devcontainer-Konfiguration installiert und verfuegbar sind und der GUI-Starttest fuer `src/volleyball/MainWindow.java` im Dockerpfad reproduzierbar ausfuehrbar bleibt.

## Vorbedingungen
- Codespace ist gestartet.
- Repository ist im Projektroot geoeffnet.
- Devcontainer-Datei `.devcontainer/devcontainer.json` enthaelt die Pflichtliste unter `customizations.vscode.extensions`.

## Schritte
1. Bei Codespace-Erstellung automatisch `bash scripts/bootstrap.sh` ausfuehren lassen (via `postCreateCommand`).
2. Bei jedem weiteren Start automatisch `bash scripts/ensure-devcontainer-runtime.sh` ausfuehren lassen, um Docker-in-Docker und Java 21 zu validieren.
3. Danach automatisch `bash scripts/ensure-vscode-extensions.sh` ausfuehren lassen (via `postStartCommand`).
4. Fuer manuelle Pruefung bei Bedarf ausfuehren: `bash scripts/ensure-devcontainer-runtime.sh` und `bash scripts/ensure-vscode-extensions.sh`.
5. Bei geaenderter Erweiterungsliste in `.devcontainer/devcontainer.json` die Routine direkt erneut ausfuehren.
6. GUI-Starttest im Codespace verifizieren: `bash scripts/test-java-gui-docker.sh`.
7. Ergebnis in der Shell verifizieren: Ausgabe endet mit `Devcontainer-Anforderungen erfuellt`, `Pflicht-Erweiterungen verfuegbar` und einer erfolgreichen GUI-Startmeldung.

## Erfolgskriterien
- Das Runtime-Skript bestaetigt `docker-in-docker aktiv, Java-Feature auf 21 konfiguriert`.
- Das Skript meldet fuer jede Pflicht-Erweiterung entweder `OK` oder fuehrt eine erfolgreiche Nachinstallation aus.
- Die Abschlussmeldung `Pflicht-Erweiterungen verfuegbar` erscheint ohne Fehler.
- `bash scripts/test-java-gui-docker.sh` endet erfolgreich und bestaetigt den GUI-Start technisch.
- Bei fehlender VS Code CLI im Codespace bricht das Skript mit klarer Fehlermeldung ab.

## Fehlerbehandlung
- Fehler `Devcontainer-Konfiguration fehlt`: Pfad und Dateiname `.devcontainer/devcontainer.json` pruefen.
- Fehler `docker-in-docker:2 ist nicht aktiviert`: Feature-Block in `.devcontainer/devcontainer.json` korrigieren.
- Hinweis `Java-Laufzeit ist aktuell nicht 21`: Java-Feature bleibt korrekt konfiguriert; Codespace/Devcontainer neu bauen, damit Java 21 aktiv wird.
- Fehler `jq ist nicht installiert`: Devcontainer neu bauen oder Tool manuell installieren.
- Fehler `VS Code CLI ('code') ist im Codespace nicht verfuegbar`: Codespace neu starten und VS Code-Verbindung pruefen.
- Fehler `Erweiterung fehlt nach Installationsversuch`: Extension-ID in Devcontainer pruefen und Installationsausgabe kontrollieren.
- Fehler `Docker-Daemon nicht erreichbar`: Codespace neu starten und `bash scripts/ensure-devcontainer-runtime.sh` erneut ausfuehren.
- Fehler `GUI-Start fehlgeschlagen`: `bash scripts/test-java-gui-docker.sh` erneut ausfuehren und die Docker-Build-/Run-Logs pruefen.

## Ausgaben/Ergebnisse
- Konsistente Erweiterungsbasis in jedem Codespace-Startzyklus.
- Reproduzierbarer GUI-Startcheck fuer `MainWindow` im Docker-Codespace.
- Nachweisbarer Installations- und Verifikationslauf im Terminal.

## Verknuepfungen
- [qualitaets-gates-automatisierung.md](../../prozesse/qualitaets-gates-automatisierung.md)
- [review-prozess.md](../../prozesse/review-prozess.md)
- [devcontainer.json](../../../../.devcontainer/devcontainer.json)
- [java-live-test.md](../../anleitungen/java-live-test.md)

## Changelog
- v1.1 (27.04.2026): GUI-Starttest im Dockerpfad und verfeinerte Verifikationsschritte ergaenzt
- v1.0 (23.04.2026): Initiale Routine fuer persistente Erweiterungsabsicherung erstellt
