CREATE DATABASE IF NOT EXISTS invernadero
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE invernadero;

CREATE TABLE IF NOT EXISTS usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  nombre_completo VARCHAR(255) NOT NULL,
  telefono VARCHAR(20),
  password VARCHAR(255) NOT NULL,
  rol ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER'
);

INSERT INTO usuarios (email, nombre_completo, telefono, password, rol)
VALUES (
  'admin@admin.com',
  'Administrador',
  '5551234567',
  '$2a$10$XjN1s8Z1j0i2B4bQWrq1OeUz5GTdPuNeOP0ah/ulE8Sxd5uLULUFm', 
  'ADMIN'
);
