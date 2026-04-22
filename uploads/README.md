# EDU-CODE-PROJECTTEMPLATE

**Eine produktionsreife Vorlage für moderne Webanwendungen** mit Full-Stack-Technologien, professioneller Dokumentation und Best Practices.

> Für schulische und kommerzielle Informatik-Projekte

---

## 📋 Systemanforderungen (Pflichtenheft)

### Kernanforderungen
Das System implementiert folgende **zentrale Anforderungen**:

| Anforderung | Status | Details |
|-------------|--------|---------|
| **Webbasierte Appanwendung** | ✅ | Responsive & Mobile-optimiert (React, JavaScript, PHP) |
| **Redundanzfreies System** | ✅ | App und Repo (DRY-Prinzip), keine Copy-Paste |
| **Lückenlose Dokumentation** | ✅ | [Handbuch](docs/handbuch/) & [Pflichtenheft](docs/handbuch/PFLICHTENHEFT.md) |
| **Sicherheit** | ✅ | Application Security & Betriebssicherheit |
| **OOP-Prinzipien** | ✅ | Abstraktion, Vererbung, Kapselung, Polymorphie |
| **Wiederverwendbarkeit** | ✅ | APIs & eigene Libraries |
| **Zerlegung (Divide & Conquer)** | ✅ | Modulare Architektur |
| **Erweiterbarkeit** | ✅ | Modulares Design, Objektorientiert |
| **Machbarkeit** | ✅ | Testbare Einheiten (Unit Tests) |
| **Wartbarkeit** | ✅ | Keine Redundanz, Backup-System, Best Practices |
| **Automatisierung** | ✅ | Routinen für alle Prozesse |
| **Persistenz** | ✅ | Datenbankoperationen & Lebensdauer-Management |
| **MVC-Architektur** | ✅ | Modell-View-Controller durchgängig |
| **Best Practices** | ✅ | Für alle Entwicklungsschritte |
| **Live-Testumgebung** | ✅ | Permanent verfügbar (Docker Compose) |

---

## 🏗️ Architektur

```
Frontend (React/JavaScript/PHP - responsive)
    ↓
API Layer (Python Flask/FastAPI - RESTful)
    ↓
Service Layer (Python/Java - Business Logic)
    ↓
Data Layer (MySQL - Persistent Storage)
```

**Technologie-Stack:**
- 🐍 Python (API & Backend)
- ☕ Java (Business Logic & Desktop)
- 🌐 JavaScript/React (Frontend)
- 🐘 PHP (Web Views)
- 💾 MySQL (Datenbank)
- 🐳 Docker (Container & Deployment)

---

## 📚 Dokumentation

### Wichtige Dokumente

| Dokument | Beschreibung |
|----------|-------------|
| [PFLICHTENHEFT.md](docs/handbuch/PFLICHTENHEFT.md) | ⭐ **Systemanforderungen & Spezifikationen** |
| [ARCHITEKTUR.md](docs/handbuch/ARCHITEKTUR.md) | Technische Architektur & Diagramme |
| [Handbuch Index](docs/handbuch/INDEX.md) | Übersicht aller Dokumentationen |
| [README.md](docs/handbuch/README.md) | Quick-Start & Übersicht |
| [legacy-analysis/README.md](legacy-analysis/README.md) | 🔒 **Legacy-Code Analyse** (Upload & Analyse) |

---

## � Legacy-System Analyse

Das Projekt enthält ein **gesichertes Upload-Verzeichnis** für die Analyse von Legacy-Code:

| Bereich | Beschreibung |
|---------|-------------|
| **Upload** | [`/legacy-analysis/`](legacy-analysis/README.md) — 🔒 Geschützt, wird nicht committed |
| **Dokumentation** | [`/docs/handbuch/legacy-analysis/`](docs/handbuch/legacy-analysis/README.md) — Analyseergebnisse (committed) |

**Workflow:**
1. Quellcode in `/legacy-analysis/` hochladen
2. Analysieren und verstehen
3. Ergebnisse in `/docs/handbuch/legacy-analysis/` dokumentieren
4. Quellcode-Upload nach Fertig löschen

Siehe [legacy-analysis/README.md](legacy-analysis/README.md) für Details.

---

### 1. Repository klonen
```bash
git clone https://github.com/ChristineJanischek/universal-webpy-app-project-template.git
cd universal-webpy-app-project-template
```

### 2. Abhängigkeiten installieren
```bash
bash scripts/bootstrap.sh
```

### 3. Services starten
```bash
bash scripts/start-services.sh
```

### 4. Services im Browser öffnen
- **Web Frontend:** http://localhost:8000
- **Python API:** http://localhost:5000
- **MySQL:** localhost:3306

---

## ✅ Quality Gates

Alle Anforderungen müssen die täglichen Validierungen bestehen:

```bash
# Security Checks
bash scripts/validate-security.sh

# Architecture Checks
bash scripts/validate-architecture.sh

# Documentation Checks
bash scripts/validate-docs.sh
```

**Prüfkriterien:**
- ✅ Keine Secrets im Repo
- ✅ Keine Code-Duplikate
- ✅ Alle Anforderungen dokumentiert
- ✅ Tests bestanden
- ✅ Security-Standards erfüllt

---

## 📦 Verzeichnisstruktur

```
├── src/                          # Java Source Code & Domänen
├── services/python-api/          # Python API Service
├── webapp/                       # Web Frontend (React/PHP/JS)
├── docs/handbuch/                # Dokumentation
│   └── PFLICHTENHEFT.md         # ⭐ Systemanforderungen
├── legacy-analysis/              # 🔒 Gesichertes Upload-Verzeichnis
│   └── README.md                 # Anleitung für Legacy-Code Analyse
├── docker/                       # Docker Konfigurationen
├── scripts/                      # Automation Scripts
└── docker-compose.yml            # Service Orchestration
```

---

## 🔒 Sicherheit

Das System implementiert mehrschichtige Sicherheit:

- **Application Security:**
  - Input-Validierung & Sanitization
  - SQL-Injection Prevention
  - XSS-Prevention
  - CSRF-Schutz
  - Sichere Authentifizierung

- **Operational Security:**
  - Tägliche Backups
  - Audit-Logging
  - Secrets-Management
  - SSL/TLS Encryption
  - Monitoring & Alerting

Mehr Details im [PFLICHTENHEFT.md](docs/handbuch/PFLICHTENHEFT.md#fa6-sicherheit-security--safety)

---

## 🧪 Testing & Quality Assurance

- **Unit Tests:** Python (pytest), JavaScript (Jest), Java (JUnit)
- **Integration Tests:** API-Endpoints
- **End-to-End Tests:** Kritische User-Flows
- **Performance Tests:** Dokumentiert
- **Live-Testumgebung:** Permanent verfügbar

---

## 🤝 Contributing

### Workflow
1. Feature Branch erstellen
2. Code schreiben (mit Tests!)
3. Alle Quality Gates bestehen lassen
4. Pull Request mit Beschreibung
5. Code Review & Merge

### Style Guide
- Clean Code Prinzipien
- DRY (Don't Repeat Yourself)
- SOLID Principles
- Konsistente Namenskonventionen

---

## 📝 Änderungsprotokoll

Siehe [CHANGELOG.md](CHANGELOG.md) für alle Versionen und Updates.

---

## 📄 Lizenz

[LICENSE](LICENSE)

---

## 📞 Support & Kontakt

Für Fragen oder Probleme:
1. Siehe [Handbuch](docs/handbuch/)
2. Check [PFLICHTENHEFT.md](docs/handbuch/PFLICHTENHEFT.md)
3. Öffne ein Issue auf GitHub

---

**Status:** ✅ Produktionsreif | **Version:** 2.0 | **Last Updated:** 14. April 2026
