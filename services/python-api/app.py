import json
import os
from pathlib import Path

import pymysql
from flask import Flask, jsonify

app = Flask(__name__)


def load_json_db():
    json_path = Path(os.getenv("JSON_DB_PATH", "/app/data.json"))
    if not json_path.exists():
        return {"error": f"JSON-Datei nicht gefunden: {json_path}"}
    with json_path.open("r", encoding="utf-8") as f:
        return json.load(f)


def mysql_status():
    host = os.getenv("MYSQL_HOST", "mysql")
    port = int(os.getenv("MYSQL_PORT", "3306"))
    user = os.getenv("MYSQL_USER", "appuser")
    password = os.getenv("MYSQL_PASSWORD", "apppassword")
    database = os.getenv("MYSQL_DATABASE", "appdb")

    conn = None
    try:
        conn = pymysql.connect(
            host=host,
            port=port,
            user=user,
            password=password,
            database=database,
            connect_timeout=5,
        )
        with conn.cursor() as cur:
            cur.execute("SELECT COUNT(*) FROM demo_items")
            count = cur.fetchone()[0]
        return {"ok": True, "demo_items": count}
    except Exception as exc:
        return {"ok": False, "error": str(exc)}
    finally:
        if conn is not None:
            conn.close()


@app.get("/health")
def health():
    return jsonify(
        {
            "service": "python-api",
            "status": "ok",
            "mysql": mysql_status(),
            "json_loaded": "error" not in load_json_db(),
        }
    )


@app.get("/json-items")
def json_items():
    return jsonify(load_json_db())


@app.get("/db-check")
def db_check():
    return jsonify(mysql_status())


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8000)
