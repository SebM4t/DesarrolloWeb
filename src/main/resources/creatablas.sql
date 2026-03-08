-- 1. Crear la base de datos
CREATE DATABASE IF NOT EXISTS placas_db;

USE placas_db;

-- 2. Tabla de categorías
CREATE TABLE categoria (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    descripcion TEXT
);

-- 3. Tabla de placas
CREATE TABLE placa (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(150)  NOT NULL,
    descripcion  TEXT,
    material     VARCHAR(100)  NOT NULL,
    tamanio      VARCHAR(50)   NOT NULL,
    precio       DECIMAL(10,2) NOT NULL,
    imagen_nombre VARCHAR(255),
    disponible   BOOLEAN       DEFAULT TRUE,
    categoria_id BIGINT,
    CONSTRAINT fk_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES categoria(id)
);