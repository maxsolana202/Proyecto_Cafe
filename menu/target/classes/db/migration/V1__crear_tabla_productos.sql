CREATE TABLE productos (
                           id                 BIGINT       NOT NULL AUTO_INCREMENT,
                           nombre             VARCHAR(100) NOT NULL,
                           descripcion        VARCHAR(300),
                           precio             DOUBLE       NOT NULL,
                           categoria          VARCHAR(50)  NOT NULL,
                           imagen_url         VARCHAR(300),
                           disponible         BOOLEAN      NOT NULL DEFAULT TRUE,
                           tiempo_preparacion INT          NOT NULL,
                           PRIMARY KEY (id)
);