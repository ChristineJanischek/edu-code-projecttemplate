# PFLICHTENHEFT: Systemanforderung (konsolidiert)

Dokumentversion: 2.0  
Datum: 16.04.2026  
Status: Verbindlich  
Geltung: Gesamtes Repository

---

## 1. Zweck und Geltungsbereich

Dieses Dokument ist die zentrale und verbindliche Systemanforderung fuer das Projekt.
Es konsolidiert Systemanforderungen aus Produktsicht und Repository/Governance-Anforderungen in einer redundanzarmen SSOT-Quelle.

In Scope:
- Webanwendung, API, Java-Domaenenlogik, Datenhaltung, Dokumentationssystem
- Entwicklungsprozess im Repository inklusive Quality Gates

Out of Scope:
- Nicht dokumentierte Zusatzsysteme ausserhalb des Repositories
- Externe Betriebsprozesse ohne Bezug zu diesem Projekt

---

## 2. Quellenbasis fuer die Konsolidierung

Die Anforderungen wurden zusammengefuehrt aus:
- uploads/README.md (Systemziele, Architektur, Security, Qualitaet)
- docs/handbuch/ARCHITEKTUR.md (Architektur- und Governance-Rahmen)
- docs/handbuch/prozesse/qualitaets-gates-automatisierung.md (Pflicht-Gates)
- docs/handbuch/prozesse/review-prozess.md (Review- und Freigabekriterien)
- docs/handbuch/prozesse/redundanz-management.md (DRY/Redundanz-Regeln)
- Repository-Regeln aus .github/copilot-instructions.md (verbindliche Repo-Qualitaetsregeln)

---

## 3. SSOT-Regel

Dieses Pflichtenheft ist die normative Quelle fuer Anforderungen.
Andere Dokumente duerfen ergaenzen, aber nicht widersprechen.
Bei Konflikten hat dieses Dokument Prioritaet.

---

## 4. Anforderungskatalog

### 4.1 Funktionale Systemanforderungen (SYS)

- SYS-01 Webanwendung: Das System stellt eine webbasierte Anwendung mit responsiver Nutzung auf Desktop und Mobile bereit.
- SYS-02 Frontend: Das Frontend nutzt die im Repository vorgesehenen Web-Komponenten unter webapp fuer UI und Interaktion.
- SYS-03 API-Layer: Das System stellt eine Python-API bereit, die Anwendungsfunktionen gekapselt bereitstellt.
- SYS-04 Domaenenlogik: Das System enthaelt Java-basierte Domaenenlogik mit MVC-orientierter Trennung in Modell, Steuerung und View-Komponenten.
- SYS-05 Persistenz: Das System ermoeglicht persistente Datenhaltung (MySQL als Standardpfad; dateibasierte Daten fuer einfache Service-Daten sind zulaessig).
- SYS-06 Live-Testfaehigkeit: Das System ist lokal in einer lauffaehigen Testumgebung per Docker Compose startbar.
- SYS-07 Routine-Wissensbasis: Das System ermoeglicht die strukturierte Verwaltung von Routinen in kurz-, mittel- und langfristigen Kategorien.
- SYS-08 Template-basierte Erfassung: Neue Routinen werden ueber standardisierte Templates erfasst.
- SYS-09 Reviewfaehigkeit: Neue und geaenderte Inhalte sind fuer einen formalen Review- und Freigabeprozess aufbereitbar.

### 4.2 Repository- und Prozessanforderungen (REP)

- REP-01 Git-first: Alle Aenderungen erfolgen versioniert im Repository.
- REP-02 PR-Pflicht: Aenderungen werden ueber Branch + Pull Request mit Review freigegeben.
- REP-03 Quality-Gate-Pflicht: Vor Abschluss muessen alle drei Pflichtpruefungen erfolgreich sein:
  - bash scripts/validate-security.sh
  - bash scripts/validate-architecture.sh
  - bash scripts/validate-docs.sh
- REP-04 Dokumentationspflicht: Relevante Codeaenderungen muessen im Handbuch dokumentiert werden.
- REP-05 Changelog/Versionierung: Fachlich relevante Dokumentaenderungen muessen nachvollziehbar versioniert werden.
- REP-06 Redundanzfreiheit: Anforderungen, Prozesse und Routinen duerfen nicht unnoetig dupliziert werden; bei Ueberlappung ist zu konsolidieren oder zu referenzieren.
- REP-07 SSOT-Pflege: Jede Information soll nur einmal als Primarquelle gepflegt und sonst referenziert werden.

### 4.3 Architektur- und Designanforderungen (ARC)

- ARC-01 OOP und Kapselung zuerst: Interne Zustaende duerfen nicht unkontrolliert mutierbar nach aussen gereicht werden.
- ARC-02 Trennung von Verantwortlichkeiten: Komponenten sind klar nach Zustaendigkeit getrennt.
- ARC-03 Erweiterbarkeit vor Kurzfrist-Loesungen: Designentscheidungen muessen Erweiterungen ermoeglichen.
- ARC-04 Wiederverwendbarkeit: Gemeinsame Logik ist zu extrahieren statt zu kopieren.
- ARC-05 Keine Architekturverletzung: Schichtbrueche und inkonsistente Querverdrahtung sind zu vermeiden.

### 4.4 Sicherheitsanforderungen (SEC)

- SEC-01 Keine Secrets im Repo: Geheime Zugangsdaten duerfen nicht versioniert werden.
- SEC-02 Keine schwachen Defaults: Unsichere Standard-Credentials sind unzulaessig.
- SEC-03 Keine internen Fehlerdetails an Clients: Interne Exception-Details duerfen nicht ungefiltert ausgeliefert werden.
- SEC-04 Secure by Default: Neue Komponenten sind mit sicherem Standardverhalten zu implementieren.
- SEC-05 Betriebsstabilitaet: Sicherheitsrelevante Verstoesse blockieren Merge/Freigabe.

### 4.5 Qualitaets- und Wartbarkeitsanforderungen (NFA)

- NFA-01 Wartbarkeit: Struktur, Benennung und Verantwortlichkeiten sind konsistent zu halten.
- NFA-02 Nachvollziehbarkeit: Entscheidungen, Aenderungen und Freigaben sind revisionsfaehig zu dokumentieren.
- NFA-03 Testbarkeit: Kritische Funktionen sind in testbarer Form bereitzustellen (z. B. Java-Modelltests, API-Checks).
- NFA-04 Effizienz: Prozesse sollen mit geringem manuellen Overhead funktionieren und wiederholbare Routinepfade bieten.
- NFA-05 Skalierbarkeit der Doku: Das Handbuch muss mit wachsender Anzahl Routinen ohne Strukturbruch nutzbar bleiben.

---

## 5. Verbindliche Abnahmekriterien

Eine Aenderung gilt nur dann als anforderungskonform, wenn folgende Kriterien erfuellt sind:

1. Alle betroffenen Anforderungen aus Abschnitt 4 sind adressiert.
2. Alle drei Pflicht-Gates laufen erfolgreich:
   - bash scripts/validate-security.sh
   - bash scripts/validate-architecture.sh
   - bash scripts/validate-docs.sh
3. Dokumentationsaenderungen sind im Handbuch enthalten, falls fachlich oder technisch relevant.
4. Keine neue Redundanz wurde eingefuehrt (Code oder Doku).
5. Review erfolgte gemaess review-prozess.md.

---

## 6. Rueckverfolgbarkeit (Traceability)

| Konsolidierte Quelle | Ergebnis im Katalog |
|---|---|
| uploads/README.md Kernanforderungen (Web, MVC, Persistenz, Sicherheit, DRY, Live-Test, Doku) | SYS-01..SYS-09, SEC-01..SEC-05, NFA-01..NFA-05 |
| docs/handbuch/ARCHITEKTUR.md (SSOT, DRY, Git-first, Governance) | REP-01..REP-07, ARC-01..ARC-05 |
| prozesse/qualitaets-gates-automatisierung.md | REP-03, SEC-05, Abnahmekriterien 2 |
| prozesse/review-prozess.md | REP-02, Abnahmekriterien 5 |
| prozesse/redundanz-management.md | REP-06, REP-07 |
| Repo-Regeln (.github/copilot-instructions.md) | ARC-01..ARC-05, SEC-01..SEC-04, REP-03, REP-04 |

---

## 7. Pflegeprozess fuer Anforderungen

Bei jeder Aenderung mit Anforderungswirkung gilt:

1. Betroffene IDs aus Abschnitt 4 identifizieren.
2. Anpassung zuerst hier im Pflichtenheft vornehmen.
3. Danach ARCHITEKTUR.md, Prozessdokumente und weitere Handbuchseiten konsistent nachziehen.
4. Pflicht-Gates ausfuehren.
5. Versionshistorie aktualisieren.

---

## 8. Version History

- v1.0 (23.03.2026): Initiales Pflichtenheft erstellt
- v2.0 (16.04.2026): Vollstaendige Konsolidierung Systemanforderungen + Repository-Anforderungen als SSOT
