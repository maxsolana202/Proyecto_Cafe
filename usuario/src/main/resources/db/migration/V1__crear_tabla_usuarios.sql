CREATE TABLE usuarios (
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    nombre    VARCHAR(100) NOT NULL,
    email     VARCHAR(100) NOT NULL UNIQUE,
    password  VARCHAR(100) NOT NULL,
    telefono  VARCHAR(20)  NOT NULL,
    direccion VARCHAR(200),
    rol       VARCHAR(50)  NOT NULL,
    activo    BOOLEAN      NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id)
);
