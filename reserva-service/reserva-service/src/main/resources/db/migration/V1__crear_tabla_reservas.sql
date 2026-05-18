CREATE TABLE reservas (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre_cliente VARCHAR(100) NOT NULL,
                          correo_cliente VARCHAR(100) NOT NULL,
                          fecha_reserva DATE NOT NULL,
                          cantidad_personas INT NOT NULL,
                          estado VARCHAR(50) NOT NULL
);