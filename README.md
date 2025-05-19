# Sistema Invernadero - Prueba de Concepto

Este proyecto es un sistema empresarial para monitoreo y gestión de un invernadero basado en una arquitectura de microservicios.

## Arquitectura General

El sistema está compuesto por 5 microservicios que se comunican entre sí y con un broker MQTT para la gestión y monitoreo de sensores:

- **invernadero-service**: Servicio principal para la gestión de sensores y lectura de datos.
- **alertas-service**: Servicio encargado de generar y enviar alertas (por ejemplo, SMS o email).
- **usuario-service**: Gestión de usuarios y autenticación.
- **gateway-service**: API Gateway para centralizar y enrutar solicitudes.
- **auth-service**: Servicio de autentificación para acceso al sistema.

Además, el sistema incluye:

- **mqtt-broker**: Broker MQTT (Mosquitto) para comunicación con los sensores físicos.
- **mysql**: Base de datos MySQL para almacenamiento persistente.

## Puertos expuestos

| Servicio            | Puerto externo | Descripción                 |
| ------------------- | -------------- | --------------------------- |
| gateway-service     | 8080           | Entrada principal a la API  |
| invernadero-service | 8083           | Gestión sensores y lecturas |
| alertas-service     | 8084           | Servicio de alertas         |
| usuario-service     | 8081           | Gestión de usuarios y auth  |
| auth-service        | 8082           | Configuración centralizada  |
| mqtt-broker         | 1883           | Broker MQTT (Mosquitto)     |
| mysql               | 3306           | Base de datos MySQL         |

Levantar la aplicación con Docker Compose
Se incluye un archivo docker-compose.yml que levanta los 5 microservicios, MQTT broker y MySQL con la red necesaria para comunicación interna.

Comandos básicos:

Construir imágenes:
- docker-compose build
  
Levantar los contenedores en modo interactivo:
- docker-compose up

Levantar en modo demonio (background):
- docker-compose up -d

Detener y eliminar contenedores:
- docker-compose down
