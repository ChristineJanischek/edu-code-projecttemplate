# Java-App live testen – Schritt-für-Schritt-Anleitung

**Ziel:** Die Volleyball-Team-Manager-Java-App kompilieren, alle Modell-Tests ausführen und (optional) die Swing-GUI starten.

---

## Übersicht: Drei Test-Modi

| Modus | Voraussetzung | Einsatz |
|---|---|---|
| **Headless-Modell-Test** | Nur Java (kein Display) | Codespace, CI, Terminal |
| **GUI starten** | Java + lokales Display | Lokale Entwicklung, Windows/Mac |
| **Docker-Live-Test** | Docker Compose im Repo | Codespace, Terminal, Live-Neutest bei Aenderungen |

---

## Modus 1 – Headless-Test im Codespace (empfohlen)

Dieser Modus testet die gesamte Geschäftslogik der App ohne grafische Oberfläche.
Er funktioniert direkt im Terminal des Codespace.

### Schritt 1 – Voraussetzungen prüfen

```bash
java -version
```

Erwartete Ausgabe (Beispiel):
```
openjdk version "17.0.x" ...
```

Falls Java fehlt:
```bash
sdk install java 17-ms
```

### Schritt 2 – Kompilieren und Modell-Tests ausführen (ein Befehl)

```bash
bash scripts/test-java.sh
```

Dieser Befehl:
1. Kompiliert alle Java-Dateien aus `src/volleyball/` nach `build/java/`
2. Führt `ModelSmokeTest` aus – 13 Tests der Kernlogik ohne Display

Erwartete Ausgabe:
```
[java] Kompilierung erfolgreich
[java] Starte Headless-Modell-Smoke-Tests...
  PASS  Startaufstellung hat 6 Spieler
  PASS  Erster Spieler ist Armin
  ...
[java-model-test] Ergebnis: 13 bestanden, 0 fehlgeschlagen
[java-model-test] Alle Tests erfolgreich
```

### Was wird getestet?

| Test | Was er prüft |
|---|---|
| Startaufstellung hat 6 Spieler | Korrekte Initialisierung |
| Erster Spieler ist Armin | Reihenfolge der Startdaten |
| Ersatzbank hat 6 Spieler | Korrekte Initialisierung |
| Erster Ersatzspieler ist Chris | Reihenfolge der Startdaten |
| Kader hat 12 Spieler gesamt | `getKader()` kombiniert beide Listen |
| Nach Tausch: Index 0 = Batu | `tausche()` Logik korrekt |
| Nach Tausch: Index 1 = Armin | `tausche()` Logik korrekt |
| Einfügen: 7 Spieler | `einfuegen()` fügt hinzu |
| Eingefügter Spieler an Position 0 | Einfügeposition korrekt |
| Kader-Typ Kaderspieler an [0] | OOP-Typen korrekt |
| Kader-Typ Ersatzspieler an [6] | OOP-Typen korrekt |
| Ungueltiger Tausch kein Fehler | Robustheit bei falschen Indizes |
| holeSpielerliste(99) = leer | Fallback bei ungültiger Auswahl |

### Schritt 3 – Einzeln kompilieren (optional, zur Fehlersuche)

```bash
mkdir -p build/java
javac -d build/java src/volleyball/*.java
```

### Schritt 4 – Einzelnen Modell-Test direkt aufrufen (optional)

```bash
java -cp build/java volleyball.ModelSmokeTest
```

---

## Modus 2 – GUI starten (lokal mit Display)

Die Swing-Oberfläche benötigt eine grafische Umgebung.
Im Codespace ist kein Display vorhanden – dafür bitte die lokale Java-Installation nutzen.

### Voraussetzung

- Java 17+ lokal installiert (z.B. via SDKMAN oder offizieller Installer)
- Repository lokal geklont **oder** VS Code verbindet sich via Remote-Container mit X11-Forwarding

### Schritt 1 – Kompilieren

```bash
mkdir -p build/java
javac -d build/java src/volleyball/*.java
```

### Schritt 2 – GUI starten

```bash
java -cp build/java volleyball.MainWindow
```

Das Hauptfenster des Volleyball-Team-Managers öffnet sich.

### App-Funktionen in der GUI

| Funktion | Beschreibung |
|---|---|
| Spieler anzeigen | Zeigt Startaufstellung, Ersatzbank oder Kader |
| Spieler tauschen | Tauscht zwei Spielerpositionen in einer Liste |
| Spieler einfügen | Fügt neuen Spieler an gewünschter Position ein |
| Spieler entfernen | Entfernt Spieler aus der Liste |

---

## Modus 3 – Docker-Live-Test im Repo und Codespace

Dieser Modus startet einen eigenen Java-Container fuer `src/volleyball/`.
Der Container kompiliert die App headless, fuehrt `ModelSmokeTest` aus und beobachtet danach den Ordner `src/volleyball/` auf Aenderungen.
Bei jeder gespeicherten Aenderung wird der Test automatisch erneut ausgefuehrt.

### Voraussetzungen im Codespace

Die Voraussetzungen sind im Repository bereits vorbereitet:

- Der Devcontainer aktiviert Docker-in-Docker und Java 17.
- `bash scripts/bootstrap.sh` setzt die Skriptrechte.
- Der Compose-Service `java-live-test` ist in [docker-compose.yml](docker-compose.yml) hinterlegt.

### Schritt 1 – Codespace oder lokale Shell im Projektroot oeffnen

```bash
cd /workspaces/edu-code-projecttemplate
```

### Schritt 2 – Docker-Einmaltest ausfuehren

```bash
bash scripts/test-java-docker.sh
```

Dieser Befehl:
1. Baut das Image aus [docker/java-live-test/Dockerfile](docker/java-live-test/Dockerfile)
2. Startet einen Container nur fuer den Headless-Test
3. Kompiliert `src/volleyball/*.java`
4. Fuehrt `volleyball.ModelSmokeTest` im Container aus

Erwartung: Der Befehl endet erfolgreich mit den bekannten PASS-Meldungen aus dem Modell-Smoke-Test.

### Schritt 3 – Live-Test-Container starten

```bash
bash scripts/start-java-live-test.sh
```

Der Container bleibt danach aktiv und beobachtet den Java-Quellordner.

### Schritt 4 – Live-Logs verfolgen

```bash
bash scripts/logs-java-live-test.sh
```

Erwartete Logfolge beim Start:

```text
[java-live-test] Starte initialen Headless-Test
[java] Kompilierung erfolgreich
[java] Starte Headless-Modell-Smoke-Tests...
...
[java-live-test] Headless-Test erfolgreich
[java-live-test] Beobachte src/volleyball auf Aenderungen
```

### Schritt 5 – Aenderung live pruefen

1. Eine Datei unter `src/volleyball/` anpassen und speichern.
2. Die Logausgabe beobachten.
3. Der Container startet die Kompilierung und die Smoke-Tests automatisch erneut.

Erwartete Logfolge nach einer Aenderung:

```text
[java-live-test] Aenderung erkannt, teste erneut...
[java] Kompilierung erfolgreich
[java] Starte Headless-Modell-Smoke-Tests...
...
```

### Schritt 6 – Live-Test wieder stoppen

```bash
bash scripts/stop-java-live-test.sh
```

### Wann dieser Modus sinnvoll ist

| Situation | Empfohlener Modus |
|---|---|
| Schneller Einzelcheck in Docker | `bash scripts/test-java-docker.sh` |
| Laufende Entwicklung im Codespace | `bash scripts/start-java-live-test.sh` |
| GUI-Validierung auf lokalem Rechner | `java -cp build/java volleyball.MainWindow` |

---

## Alle Dienste gemeinsam testen (Java + Docker-Services)

Um Java-Test zusammen mit MySQL, Python-API und PHP-Webapp zu prüfen:

```bash
bash scripts/test-services.sh
```

Dieser Befehl schließt `scripts/test-java.sh` automatisch mit ein.

Voraussetzung: Docker-Dienste laufen bereits (`bash scripts/start-services.sh`).

---

## Troubleshooting

### Fehler: `error: package volleyball does not exist`

Ursache: Falsche `javac`-Ausführungsebene.

Lösung: Befehl muss im Projektroot ausgeführt werden:
```bash
cd /workspaces/edu-code-projecttemplate
javac -d build/java src/volleyball/*.java
```

### Fehler: `java.awt.HeadlessException`

Ursache: GUI-Start wird ohne Display versucht (z.B. in Codespace).

Lösung: Im Codespace statt GUI den Headless-Modell-Test verwenden:
```bash
bash scripts/test-java.sh
```

### Fehler: `class not found: volleyball.MainWindow`

Ursache: Classpath fehlt oder Kompilierung nicht ausgeführt.

Lösung:
```bash
mkdir -p build/java
javac -d build/java src/volleyball/*.java
java -cp build/java volleyball.MainWindow
```

### Fehler: `docker compose` meldet fehlende Berechtigung oder Daemon nicht erreichbar

Ursache: Docker-in-Docker ist im Codespace noch nicht bereit oder Docker laeuft lokal nicht.

Loesung:
```bash
docker version
bash scripts/test-java-docker.sh
```

Falls `docker version` fehlschlaegt, den Codespace neu laden oder lokal Docker Desktop/Engine starten.

### Fehler: Live-Test reagiert nicht auf Aenderungen

Ursache: Der Watch-Container laeuft nicht oder die Logs werden nicht verfolgt.

Loesung:
```bash
bash scripts/start-java-live-test.sh
bash scripts/logs-java-live-test.sh
```

---

## Projektstruktur der Java-App

```
src/volleyball/
├── MainWindow.java                    ← View: Hauptfenster (Swing GUI)
├── SpielerWindow.java                 ← View: Spielerübersicht
├── SpielerEinfuegeWindow.java         ← View: Spieler einfügen
├── SpielerTauschWindow.java           ← View: Spieler tauschen
├── TeamManagerController.java         ← Controller: UI-Logik & Delegation
├── VolleyballspielerTeamManager.java  ← Model: Geschäftslogik & Datenhaltung
├── Spieler.java                       ← Abstrakte Basisklasse
├── Kaderspieler.java                  ← Konkrete Klasse: Startaufstellung
├── Ersatzspieler.java                 ← Konkrete Klasse: Ersatzbank
└── ModelSmokeTest.java                ← Headless-Test der Modell-Klasse
```

**Architekturmuster:** MVC (Model-View-Controller)

---

**Erstellt:** 26.03.2026  
**Aktualisiert:** 22.04.2026  
**Zugehörige Skripte:** `scripts/test-java.sh`, `scripts/test-java-docker.sh`, `scripts/start-java-live-test.sh`, `scripts/logs-java-live-test.sh`, `scripts/stop-java-live-test.sh`, `scripts/test-services.sh`
