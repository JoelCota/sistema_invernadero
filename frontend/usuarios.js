document.addEventListener('DOMContentLoaded', () => {
      // Gestionar formulario
        const token = sessionStorage.getItem('token');
        if (!token) {
            window.location.href = 'login.html';
        };
      let sensoresMap = new Map(); // Map<sensorId, sensor>
      let usuariosMap = new Map();
      const form = document.getElementById('registroForm');
      const mensajeDiv = document.getElementById('mensaje');
      
      // Función para mostrar mensajes
      function mostrarMensaje(texto, tipo) {
        mensajeDiv.textContent = texto;
        mensajeDiv.className = `alert alert-${tipo} d-block`;
        setTimeout(() => {
          mensajeDiv.className = 'alert d-none';
        }, 5000);
      }
      
    async function cargarSensoresSelect() {
    try {
        const response = await fetch('http://localhost:8083/api/sensores', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
        });

        if (response.ok) {
        const sensores = await response.json();
        const sensorSelect = document.getElementById('sensorSelect');

        sensorSelect.innerHTML = '<option value="">Seleccionar sensor...</option>';
        sensoresMap.clear(); // Limpiar mapa

        sensores.forEach(sensor => {
            sensoresMap.set(sensor.id, sensor); // Guardar sensor en el Map
            const option = document.createElement('option');
            option.value = sensor.id;
            option.textContent = `${sensor.tipo} - ${sensor.ubicacion}`;
            sensorSelect.appendChild(option);
        });
        }
    } catch (error) {
        mostrarMensaje('Error al cargar los sensores', 'danger');
    }
    }

      // Función para cargar usuarios en el select
    async function cargarUsuariosSelect() {
        try {
            const response = await fetch('http://localhost:8081/usuarios', {
                headers: {
                'Authorization': `Bearer ${token}`
                }
            });
            
            if (response.ok) {
                const usuarios = await response.json();
                const usuarioSelect = document.getElementById('usuarioSelect');
                usuarioSelect.innerHTML = '<option value="">Seleccionar usuario...</option>';
                usuariosMap.clear();
                usuarios.forEach(usuario => {
                usuariosMap.set(usuario.id, usuario); // Guardar sensor en el Map
                const id = usuario.id;
                const nombre = usuario.nombre;
                const email = usuario.email;
                const option = document.createElement('option');
                option.value = id;
                option.textContent = `${nombre} (${email})`;
                usuarioSelect.appendChild(option);
            });
        } else {
            mostrarMensaje('Error al cargar usuarios', 'danger');
          }
        } catch (error) {
          mostrarMensaje('Error de conexión con el servidor', 'danger');
        }
    } 
      
      // Función para cargar suscripciones
        async function cargarSuscripciones() {
        try {
            const response = await fetch('http://localhost:8084/alertas', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
            });

            if (response.ok) {
            const suscripciones = await response.json();
            const tbody = document.querySelector('#tablaSuscripciones tbody');
            tbody.innerHTML = '';

          suscripciones.forEach(suscripcion => {
                const sensor = sensoresMap.get(suscripcion.sensorId);
                const usuario = usuariosMap.get(suscripcion.userId); // corrección aquí
                const ubicacion = sensor ? sensor.ubicacion : 'Desconocida';
                const email = usuario ? usuario.email : 'Desconocido';

                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${suscripcion.id}</td>
                    <td>${email}</td>
                    <td>${suscripcion.sensorId}</td>
                    <td>${ubicacion}</td>
                `;
                tbody.appendChild(tr);
            });

            }
        } catch (error) {
            mostrarMensaje('Error al cargar las suscripciones', 'danger');
        }
        }

      
      // Manejar envío del formulario de suscripción
      const suscripcionForm = document.getElementById('suscripcionForm');
      suscripcionForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        
        const usuarioId = document.getElementById('usuarioSelect').value;
        const sensorId = document.getElementById('sensorSelect').value;
        
        if (!usuarioId || !sensorId) {
          mostrarMensaje('Debe seleccionar un usuario y un sensor', 'warning');
          return;
        }
        
        try {
          const response = await fetch('http://localhost:8084/alertas/suscribir', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({
              idUsuario: parseInt(usuarioId),
              idSensor: parseInt(sensorId)
            })
          });
          
          if (response.ok) {
            mostrarMensaje('Suscripción creada correctamente', 'success');
            suscripcionForm.reset();
            cargarSuscripciones();
          } else {
            const error = await response.text();
            mostrarMensaje(error || 'Error al crear la suscripción', 'danger');
          }
        } catch (error) {
          mostrarMensaje('Error de conexión con el servidor', 'danger');
        }
      });
      
      // Inicializar todo lo relacionado con suscripciones
      cargarSensoresSelect();
      cargarUsuariosSelect(); // Pequeño retraso para asegurar que los usuarios se cargaron primero
      cargarSuscripciones();
    });

    
    const form = document.getElementById('registroForm');

    form.addEventListener('submit', async (event) => {
      event.preventDefault();
      event.stopPropagation();

      if (!form.checkValidity()) {
        form.classList.add('was-validated');
        return;
      }

      const nombre = document.getElementById('nombre').value.trim();
      const email = document.getElementById('email').value.trim();
      const password = document.getElementById('password').value.trim();
      const rol = document.getElementById('rol').value;
      const numeroCelular = document.getElementById('numeroCelular').value.trim();

      try {
        const response = await fetch('http://localhost:8081/usuarios/registro', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ nombre, email, password, rol, numeroCelular })
        });
        if (response.ok) {
          cargarUsuariosSelect()
          alert('Usuario registrado exitosamente!');
          form.reset();
          form.classList.remove('was-validated');
        } else {
          mostrarMensaje('Error al registrar usuario.')
        }
      } catch (error) {
         mostrarMensaje('Error de conexión con el servidor.');
      }
    });