const AUTH_SERVICE_URL = "http://localhost:8082";
async function login() {
      const email = document.getElementById("email").value;
      const password = document.getElementById("password").value;
      const errorDiv = document.getElementById("error");

      try {
        const response = await fetch(`${AUTH_SERVICE_URL}/auth/login`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({email,password})
        });

        if (response.ok) {
          const data = await response.json();
          sessionStorage.setItem("token", data.token);
          errorDiv.classList.remove("text-danger");
          errorDiv.classList.add("text-success");
          errorDiv.textContent = "¡Login exitoso!";
          window.location.href = 'sensores.html';
          // Aquí podrías redirigir: window.location.href = "/dashboard.html";
        } else {
          const errorText = await response.text();
          errorDiv.classList.remove("text-success");
          errorDiv.classList.add("text-danger");
          errorDiv.textContent = errorText;
        }
      } catch (error) {
        errorDiv.textContent = "Error de conexión con el servidor.";
      }
    }