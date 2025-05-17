  document.getElementById('sensorForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const tipo = document.getElementById('tipoSensor').value.trim();
    const ubicacion = document.getElementById('ubicacion').value.trim();
    const valorMinimoStr = document.getElementById('valorMinimo').value;
    const valorMaximoStr = document.getElementById('valorMaximo').value;
    const alarmaActiva = document.getElementById('alarmaActiva').checked;

    let errores = [];

    // Validación de tipo
    if (tipo === "") {
      errores.push("Debes seleccionar un tipo de sensor.");
    }

    // Validación de ubicación
    if (ubicacion === "") {
      errores.push("La ubicación no puede estar vacía.");
    }

    // Validación de valores numéricos
    const valorMinimo = parseFloat(valorMinimoStr);
    const valorMaximo = parseFloat(valorMaximoStr);

    if (isNaN(valorMinimo) || isNaN(valorMaximo)) {
      errores.push("Los valores mínimo y máximo deben ser números válidos.");
    } else if (valorMinimo >= valorMaximo) {
      errores.push("El valor mínimo debe ser menor que el valor máximo.");
    }

    // Mostrar errores si existen
    if (errores.length > 0) {
      alert("Corrige los siguientes errores:\n\n- " + errores.join("\n- "));
      return;
    }

    // Si todo está bien, construir el objeto
    const data = {
      tipo,
      ubicacion,
      valorMinimo,
      valorMaximo,
      alarmaActiva
    };

    // Simulación: mostrar datos por consola
    console.log("Datos del sensor a registrar:", data);
    const token = sessionStorage.getItem('token');
    fetch('http://localhost:8080/api/sensores', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
         'Authorization': 'Bearer ${token}'
      },
      body: JSON.stringify(data)
    }).then(response => {
      if (response.ok) {
        alert("Sensor registrado correctamente.");
        document.getElementById('sensorForm').reset();
      } else {
        alert("Error al registrar el sensor.");
      }
    });
  });
