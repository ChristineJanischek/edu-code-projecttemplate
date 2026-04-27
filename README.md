# edu-code-projecttemplate
Vorlage für Einzelprojekte der Informatik in schulischem Kontext.

## Schnellstart im Codespace

```bash
bash scripts/bootstrap.sh
```

Danach stehen Runtime-Checks und Pflicht-Erweiterungen automatisch bereit.

## Java Live-Test und GUI-Starttest

Die Schritt-fuer-Schritt-Anleitung fuer Headless-Test, Docker-Live-Test und GUI-Starttest im Codespace steht hier:

- [docs/handbuch/anleitungen/java-live-test.md](docs/handbuch/anleitungen/java-live-test.md)

Sichtbare Swing-GUI im Codespace starten:

```bash
bash scripts/start-java-gui-visible.sh
```

Sichtbare GUI wieder stoppen (jederzeit moeglich):

```bash
bash scripts/stop-java-gui-visible.sh
```

Hinweis: Nach Aenderungen an `.devcontainer/devcontainer.json` den Codespace einmal mit **Rebuild Container** neu aufbauen.
