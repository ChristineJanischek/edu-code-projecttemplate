const output = document.getElementById("apiOutput");
const btn = document.getElementById("refreshBtn");

async function loadStatus() {
  output.textContent = "Lade...";
  try {
    const [healthResp, jsonResp] = await Promise.all([
      fetch("http://localhost:8000/health"),
      fetch("http://localhost:8000/json-items"),
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
