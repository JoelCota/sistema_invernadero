const API_URL = 'http://localhost:8085';
// Funciones para la página de reportes y estadísticas
async function cargarSensoresParaReportes() {
  try {
     const response = await fetch(`${API_URL}/api/sensores`);
    if (response.ok) {
      sensores = await response.json();
      
      // Cargar el selector de sensores
      const selectorSensor = document.getElementById('selectorSensor');
      selectorSensor.innerHTML = '<option value="">Selecciona un sensor...</option>';
      
      sensores.forEach(sensor => {
        const option = document.createElement('option');
        option.value = sensor.id;
        option.textContent = `${sensor.tipo} - ${sensor.ubicacion}`;
        selectorSensor.appendChild(option);
      });
      
      // Agregar evento al selector
      selectorSensor.addEventListener('change', function() {
        if (this.value) {
          cargarDatosSensor(this.value);
        } else {
          limpiarGraficos();
        }
      });
      
    } else {
      mostrarMensaje('Error al cargar los sensores', 'danger');
    }
  } catch (error) {
    mostrarMensaje('Error de conexión con el servidor', 'danger');
    console.error('Error:', error);
  }
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

// Función para cargar los datos históricos de un sensor
async function cargarDatosSensor(sensorId) {
  try {
    const response = await fetch(`${API_URL}/api/sensores/${sensorId}/lecturas`);
    if (response.ok) {
      const datos = await response.json();
      datosHistoricos = datos;
      
      // Mostrar estadísticas
      mostrarEstadisticas(datos);
      
      // Generar gráficos
      generarGraficoLineal(datos);
      generarGraficoPromedioDiario(datos);
      
      // Mostrar datos en tabla
      mostrarDatosEnTabla(datos);
    } else {
      mostrarMensaje('Error al cargar los datos del sensor', 'danger');
    }
  } catch (error) {
    mostrarMensaje('Error de conexión con el servidor', 'danger');
    console.error('Error:', error);
  }
}

// Función para mostrar estadísticas básicas
function mostrarEstadisticas(datos) {
  if (!datos || datos.length === 0) {
    return;
  }
  
  // Calcular estadísticas
  const valores = datos.map(d => d.valor);
  const promedio = valores.reduce((a, b) => a + b, 0) / valores.length;
  const maximo = Math.max(...valores);
  const minimo = Math.min(...valores);
  
  // Última lectura
  const ultimaLectura = datos[datos.length - 1];
  const fechaUltimaLectura = new Date(ultimaLectura.timestamp);
  
  // Mostrar en la interfaz
  const statsContainer = document.getElementById('estadisticasBasicas');
  if (statsContainer) {
    statsContainer.innerHTML = `
      <div class="row">
        <div class="col-md-3">
          <div class="card text-white bg-primary mb-3">
            <div class="card-header">Promedio</div>
            <div class="card-body">
              <h5 class="card-title">${promedio.toFixed(2)}</h5>
            </div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="card text-white bg-success mb-3">
            <div class="card-header">Máximo</div>
            <div class="card-body">
              <h5 class="card-title">${maximo.toFixed(2)}</h5>
            </div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="card text-white bg-info mb-3">
            <div class="card-header">Mínimo</div>
            <div class="card-body">
              <h5 class="card-title">${minimo.toFixed(2)}</h5>
            </div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="card text-white bg-warning mb-3">
            <div class="card-header">Última Lectura</div>
            <div class="card-body">
              <h5 class="card-title">${ultimaLectura.valor.toFixed(2)}</h5>
              <p class="card-text">${fechaUltimaLectura.toLocaleString()}</p>
            </div>
          </div>
        </div>
      </div>
    `;
  }
}

// Función para generar un gráfico lineal
function generarGraficoLineal(datos) {
  if (!datos || datos.length === 0) return;

  const ctx = document.getElementById('graficoLineal').getContext('2d');
  
  // Destruir gráfico existente si hay uno
    if (window.graficoLineal instanceof Chart) {
    window.graficoLineal.destroy();
    }

  
  // Preparar datos para el gráfico
  const etiquetas = datos.map(d => new Date(d.fechaHoraLectura).toLocaleString());
  const valores = datos.map(d => d.valor);
  
  // Crear el gráfico
  window.graficoLineal = new Chart(ctx, {
    type: 'line',
    data: {
      labels: etiquetas,
      datasets: [{
        label: 'Valor del Sensor',
        data: valores,
        borderColor: 'rgba(75, 192, 192, 1)',
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderWidth: 1,
        tension: 0.1
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: false
        },
        x: {
          display: true,
          ticks: {
            maxRotation: 45,
            minRotation: 45
          }
        }
      }
    }
  });
}

// Función para generar un gráfico de promedio diario
function generarGraficoPromedioDiario(datos) {
  if (!datos || datos.length === 0) return;

  const ctx = document.getElementById('graficoPromedioDiario').getContext('2d');
  
  // Destruir gráfico existente si hay uno
  if (window.graficoPromedioDiario instanceof Chart) {
    window.graficoPromedioDiario.destroy();
  }
  
  // Agrupar datos por día y calcular promedios
  const datosPorDia = {};
  
  datos.forEach(d => {
    const fecha = new Date(d.fechaHoraLectura).toLocaleDateString();
    if (!datosPorDia[fecha]) {
      datosPorDia[fecha] = {
        suma: 0,
        cantidad: 0
      };
    }
    datosPorDia[fecha].suma += d.valor;
    datosPorDia[fecha].cantidad += 1;
  });
  
  const dias = Object.keys(datosPorDia);
  const promedios = dias.map(dia => datosPorDia[dia].suma / datosPorDia[dia].cantidad);
  
  // Crear el gráfico
  window.graficoPromedioDiario = new Chart(ctx, {
    type: 'bar',
    data: {
      labels: dias,
      datasets: [{
        label: 'Promedio Diario',
        data: promedios,
        backgroundColor: 'rgba(54, 162, 235, 0.5)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: false
        }
      }
    }
  });
}

// Función para mostrar datos en una tabla
function mostrarDatosEnTabla(datos) {
  if (!datos || datos.length === 0) return;
  
  const tabla = document.getElementById('tablaDatos');
  if (!tabla) return;
  
  const tbody = tabla.querySelector('tbody');
  tbody.innerHTML = '';
  console.log(datos)
  // Ordenar datos por fecha, del más reciente al más antiguo
  const datosOrdenados = [...datos].sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));
  
  // Mostrar los primeros 100 registros (para evitar problemas de rendimiento)
  datosOrdenados.slice(0, 100).forEach(dato => {
    const tr = document.createElement('tr');
    const fecha = new Date(dato.fechaHoraLectura);
    
    tr.innerHTML = `
      <td>${fecha.toLocaleDateString()}</td>
      <td>${fecha.toLocaleTimeString()}</td>
      <td>${dato.valor.toFixed(2)}</td>
    `;
    
    tbody.appendChild(tr);
  });

}

// Función para limpiar los gráficos
function limpiarGraficos() {
  const estadisticasBasicas = document.getElementById('estadisticasBasicas');
  if (estadisticasBasicas) {
    estadisticasBasicas.innerHTML = '';
  }
  
  // Destruir gráficos si existen
  if (window.graficoLineal) {
    window.graficoLineal.destroy();
    window.graficoLineal = null;
  }
  
  if (window.graficoPromedioDiario) {
    window.graficoPromedioDiario.destroy();
    window.graficoPromedioDiario = null;
  }
  
  // Limpiar tabla
  const tablaDatos = document.getElementById('tablaDatos');
  if (tablaDatos) {
    const tbody = tablaDatos.querySelector('tbody');
    if (tbody) {
      tbody.innerHTML = '';
    }
  }
}

// Función para exportar datos a CSV
function exportarDatosCSV() {
  if (!datosHistoricos || datosHistoricos.length === 0) {
    mostrarMensaje('No hay datos para exportar', 'warning');
    return;
  }
  
  // Crear contenido CSV
  let csvContent = 'data:text/csv;charset=utf-8,';
  csvContent += 'Fecha,Hora,Valor\n';
  
  datosHistoricos.forEach(dato => {
    const fecha = new Date(dato.fechaHoraLectura);
    csvContent += `${fecha.toLocaleDateString()},${fecha.toLocaleTimeString()},${dato.valor}\n`;
  });
  
  // Crear enlace de descarga
  const encodedUri = encodeURI(csvContent);
  const link = document.createElement('a');
  link.setAttribute('href', encodedUri);
  link.setAttribute('download', `datos_sensor_${document.getElementById('selectorSensor').value}_${new Date().toLocaleDateString()}.csv`);
  document.body.appendChild(link);
  
  // Simular clic
  link.click();
  
  // Limpiar
  document.body.removeChild(link);
}

// Función para inicializar la página
document.addEventListener('DOMContentLoaded', function() {
  // Inicializar gráficos si estamos en la página de reportes
  verificarToken();
    cargarSensoresParaReportes();
});
function verificarToken(){
  const token = sessionStorage.getItem('token');
        if (!token) {
            window.location.href = 'login.html';
        };
}