<?php
$mysqlHost = getenv('MYSQL_HOST') ?: 'mysql';
$mysqlPort = getenv('MYSQL_PORT') ?: '3306';
$mysqlDb = getenv('MYSQL_DATABASE') ?: 'appdb';
$mysqlUser = getenv('MYSQL_USER') ?: 'appuser';
$mysqlPass = getenv('MYSQL_PASSWORD') ?: 'apppassword';
$pythonApiUrl = getenv('PYTHON_API_URL') ?: 'http://localhost:8000';

$mysqlMessage = 'Nicht getestet';

try {
    $mysqli = @new mysqli($mysqlHost, $mysqlUser, $mysqlPass, $mysqlDb, (int)$mysqlPort);
    if ($mysqli->connect_error) {
        $mysqlMessage = 'Fehler: ' . $mysqli->connect_error;
    } else {
        $result = $mysqli->query('SELECT COUNT(*) AS cnt FROM demo_items');
        $row = $result ? $result->fetch_assoc() : ['cnt' => 'n/a'];
        $mysqlMessage = 'OK, demo_items=' . $row['cnt'];
        $mysqli->close();
    }
} catch (Throwable $e) {
    $mysqlMessage = 'Exception: ' . $e->getMessage();
}
?>
<!doctype html>
<html lang="de">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>edu-code Live-Test Dashboard</title>
    <link rel="stylesheet" href="style.css" />
  </head>
  <body>
    <main class="container">
      <h1>Live-Test Dashboard</h1>
      <p>Diese Seite prueft PHP, JavaScript, Python-API und MySQL in einer gemeinsamen Testumgebung.</p>

      <section class="card">
        <h2>PHP -> MySQL</h2>
        <p id="phpMysqlStatus"><?php echo htmlspecialchars($mysqlMessage, ENT_QUOTES, 'UTF-8'); ?></p>
      </section>

      <section class="card">
        <h2>JavaScript -> Python-API</h2>
        <button id="refreshBtn">Status laden</button>
        <pre id="apiOutput">Noch kein API-Call ausgefuehrt.</pre>
      </section>
    </main>

    <script>
      window.PYTHON_API_URL = <?php echo json_encode($pythonApiUrl, JSON_UNESCAPED_SLASHES); ?>;
    </script>
    <script src="app.js"></script>
  </body>
</html>
