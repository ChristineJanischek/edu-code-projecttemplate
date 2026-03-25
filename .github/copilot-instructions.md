# Copilot Projektregeln (verbindlich)

Dieses Repository verfolgt langfristige Architektur- und Qualitaetsziele.

## Immer einhalten

- OOP und Kapselung zuerst: keine unkontrollierten mutable Rueckgaben/Setter fuer interne Zustaende.
- Erweiterbarkeit vor Kurzfrist-Loesungen: klare Trennung von Verantwortlichkeiten.
- Security by default: keine Secrets im Repo, keine schwachen Defaults, keine internen Fehlerdetails an Clients.
- Redundanzfreiheit: keine Copy-Paste-Loesungen; gemeinsame Logik extrahieren.
- SSOT in der Doku: jede relevante Codeaenderung aktualisiert das Handbuch.

## Pflicht vor Abschluss einer Aufgabe

- Fuehre aus: `bash scripts/validate-security.sh`
- Fuehre aus: `bash scripts/validate-architecture.sh`
- Fuehre aus: `bash scripts/validate-docs.sh`

Wenn ein Check fehlschlaegt, erst beheben, dann Ergebnis liefern.
