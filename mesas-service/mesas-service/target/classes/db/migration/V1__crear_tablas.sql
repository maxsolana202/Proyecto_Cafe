CREATE TABLE mesas (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero   INT         NOT NULL,
    capacidad INT        NOT NULL,
    estado   VARCHAR(50) NOT NULL,
    CONSTRAINT uk_mesas_numero UNIQUE (numero)
);
