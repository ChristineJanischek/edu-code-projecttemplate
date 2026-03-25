# Prozess: Qualitaets-Gates & Automatisierung

**Version:** 1.0  
**Status:** Aktiv  
**Gueltig ab:** 25.03.2026

---

## Ziel

Sicherstellen, dass jede Code- oder Doku-Erweiterung automatisiert auf OOP, Erweiterbarkeit, Wartbarkeit, Sicherheit, Wiederverwendbarkeit, Redundanzfreiheit und Dokumentationskonsistenz geprueft wird.

## Pflicht-Gates

1. Security-Gate: `bash scripts/validate-security.sh`
2. Architecture-Gate: `bash scripts/validate-architecture.sh`
3. Documentation-Gate: `bash scripts/validate-docs.sh`

Alle drei Gates sind lokal und in CI verpflichtend.

## Workflow

1. Aenderung in Feature-Branch durchfuehren
2. Lokale Gates ausfuehren
3. PR mit Template-Checklist erstellen
4. CI-Gates muessen gruen sein
5. Review + Merge

## Abbruchkriterien

Ein Merge ist zu stoppen, wenn mindestens eines der folgenden Kriterien zutrifft:

- Sicherheitsverstoß (Secret, schwacher Default, Error-Leak)
- Architekturverstoß (Schichtbruch, Encapsulation-Verletzung)
- Doku-Luecke (Code geaendert, Handbuch nicht aktualisiert)

## Messkriterien

- CI-Erfolgsrate der Pflicht-Gates
- Anzahl geblockter Merges durch Gate-Fehler
- Anteil Aenderungen mit aktualisierter Doku

## Changelog

- v1.0 (25.03.2026): Initiale Prozessdefinition fuer automatische Qualitaets-Gates
