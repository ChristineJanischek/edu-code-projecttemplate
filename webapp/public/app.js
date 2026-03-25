const output = document.getElementById("apiOutput");
const btn = document.getElementById("refreshBtn");
const apiBaseUrl = window.PYTHON_API_URL || "http://localhost:8000";

async function loadStatus() {
  output.textContent = "Lade...";
  try {
    const [healthResp, jsonResp] = await Promise.all([
      fetch(`${apiBaseUrl}/health`),
      fetch(`${apiBaseUrl}/json-items`),
    ]);

    const health = await healthResp.json();
    const jsonData = await jsonResp.json();

    output.textContent = JSON.stringify({ health, jsonData }, null, 2);
  } catch (err) {
    output.textContent = "Fehler beim API-Aufruf: " + err.message;
  }
}

btn.addEventListener("click", loadStatus);
loadStatus();
