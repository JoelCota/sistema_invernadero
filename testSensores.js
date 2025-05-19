const sensores = [
  { sensorId: 1 },
  { sensorId: 2 },
  { sensorId: 3 }
];

function valorRandom(min = 0, max = 30) {
  return (Math.random() * (max - min) + min).toFixed(2);
}

function enviarDatosSensor(sensorId, valor) {
  const url = "http://localhost:8080/lectura";

  // Convertimos valor a string porque quieres que en JSON venga como string
  const data = { 
    valor: String(valor), 
    sensorId: Number(sensorId) 
  };

  return fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  })
  .then(response => {
    if (!response.ok) throw new Error(`Error: ${response.status}`);
    return response.json();
  });
}

async function enviarTodosLosSensores() {
  for (let i = 0; i < sensores.length; i++) {
    // ejemplo: uso valor -10 para demostrar
    const valor = -5; 
    try {
      const resultado = await enviarDatosSensor(sensores[i].sensorId, valor);
      console.log(`Sensor ${sensores[i].sensorId} enviado con valor ${valor}:`, resultado);
    } catch (error) {
      console.error(`Error al enviar sensor ${sensores[i].sensorId}:`, error);
    }
  }
}

enviarTodosLosSensores();
