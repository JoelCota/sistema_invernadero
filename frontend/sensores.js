document.getElementById('sensorForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const tipo = document.getElementById('tipoSensor').value.trim();
    const ubicacion = document.getElementById('ubicacion').value.trim();
    const valorMinimoStr = document.getElementById('valorMinimo').value;
    const valorMaximoStr = document.getElementById('valorMaximo').value;
    const alarmaActiva = document.getElementById('alarmaActiva').checked;

    let errores = [];

    // ValidaciÃ³n de tipo
    if (tipo === "") {
      errores.push("Debes seleccionar un tipo de sensor.");
    }

    // ValidaciÃ³n de ubicaciÃ³n
    if (ubicacion === "") {
      errores.push("La ubicaciÃ³n no puede estar vacÃ­a.");
    }

    // ValidaciÃ³n de valores numÃ©ricos
    const valorMinimo = parseFloat(valorMinimoStr);
    const valorMaximo = parseFloat(valorMaximoStr);

    if (isNaN(valorMinimo) || isNaN(valorMaximo)) {
      errores.push("Los valores mÃ­nimo y mÃ¡ximo deben ser nÃºmeros vÃ¡lidos.");
    } else if (valorMinimo >= valorMaximo) {
      errores.push("El valor mÃ­nimo debe ser menor que el valor mÃ¡ximo.");
    }

    // Mostrar errores si existen
    if (errores.length > 0) {
      alert("Corrige los siguientes errores:\n\n- " + errores.join("\n- "));
      return;
    }

    // Si todo estÃ¡ bien, construir el objeto
    const data = {
      tipo,
      ubicacion,
      valorMinimo,
      valorMaximo,
      alarmaActiva
    };

    // SimulaciÃ³n: mostrar datos por consola
    console.log("Datos del sensor a registrar:", data);
    const token = sessionStorage.getItem('token');
    fetch('http://localhost:8085/api/sensores', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
         'Authorization': 'Bearer ${token}'
      },
      body: JSON.stringify(data)
    }).then(response => {
     if (response.ok) {
      // Mostrar mensaje de éxito
      alert('Sensor registrado correctamente')
      mostrarMensaje('Sensor registrado correctamente', 'success');
      sensorForm.reset();
      
      // Recargar lista de sensores si existe la tabla
      if (document.getElementById('tablaSensores')) {
        cargarSensores();
      }
    } else {
      const errorData = response.text();
      mostrarMensaje(`Error al registrar sensor: ${errorData}`, 'danger');
    }
  })
}); 
 // URL base de la API
const API_URL = 'http://localhost:8085';

// Variables globales
let sensores = [];
let datosHistoricos = {};

// Función para inicializar la página
document.addEventListener('DOMContentLoaded', function() {
  // Cargar lista de sensores si existe la tabla
  verificarToken()
  const tablaSensores = document.getElementById('tablaSensores');
  if (tablaSensores) {
    cargarSensores();
  }
});

// Función para cargar la lista de sensores registrados
async function cargarSensores() {
  try {
    const response = await fetch('http://localhost:8083/api/sensores');
    if (response.ok) {
      sensores = await response.json();
      mostrarSensoresEnTabla(sensores);
    } else {
      mostrarMensaje('Error al cargar los sensores', 'danger');
    }
  } catch (error) {
    mostrarMensaje('Error de conexión con el servidor', 'danger');
    console.error('Error:', error);
  }
}

// Función para mostrar los sensores en la tabla
function mostrarSensoresEnTabla(sensores) {
  const tablaSensores = document.getElementById('tablaSensores');
  const tbody = tablaSensores.querySelector('tbody');
  tbody.innerHTML = '';
  sensores.forEach(sensor => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${sensor.id || '-'}</td>
      <td>${sensor.tipo}</td>
      <td>${sensor.ubicacion}</td>
      <td>${sensor.valorMinimo}</td>
      <td>${sensor.valorMaximo}</td>
      <td>${sensor.alarmaActiva ? 'Sí' : 'No'}</td>
     
    `;
    tbody.appendChild(tr);
  });
  
}

// Función general para mostrar mensajes
function mostrarMensaje(texto, tipo) {
  const mensajeDiv = document.getElementById('mensaje');
  if (!mensajeDiv) return;
  
  mensajeDiv.textContent = texto;
  mensajeDiv.className = `alert alert-${tipo}`;
  mensajeDiv.classList.remove('d-none');
  
  // Ocultar el mensaje después de 5 segundos
  setTimeout(() => {
    mensajeDiv.classList.add('d-none');
  }, 5000);
}


function verificarToken(){
  const token = sessionStorage.getItem('token');
        if (!token) {
            window.location.href = 'login.html';
        };
}