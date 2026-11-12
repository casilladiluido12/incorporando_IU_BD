-- Script de prueba para la Actividad Unidad 3
-- Ejecutar en MySQL Workbench 

CREATE DATABASE IF NOT EXISTS inventario;

-- Usuario propio para la práctica (opcional)
CREATE USER IF NOT EXISTS 'estudiante'@'localhost' IDENTIFIED BY 'Estudiante123*';
GRANT ALL PRIVILEGES ON inventario.* TO 'estudiante'@'localhost';
FLUSH PRIVILEGES;

-- Tabla genérica (no es obligatoria para la prueba de conexión)
USE inventario;
CREATE TABLE IF NOT EXISTS producto (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    nombre   VARCHAR(100) NOT NULL,
    cantidad INT NOT NULL DEFAULT 0
);

-- Insertar Registros genéricos
USE inventario;

INSERT INTO producto (nombre, cantidad) VALUES
('Teclado USB', 25),
('Mouse inalámbrico', 40),
('Monitor 24 pulgadas', 12),
('Cable HDMI 2m', 60),
('Disco SSD 500GB', 18),
('Memoria RAM 8GB', 30),
('Audífonos con micrófono', 22),
('Cámara web HD', 15),
('Router WiFi', 8),
('Impresora multifuncional', 5);

SELECT * FROM producto;