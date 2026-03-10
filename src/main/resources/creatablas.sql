-- 1. Crear la base de datos
CREATE DATABASE IF NOT EXISTS placas_db;

-- Creación de usuarios con contraseñas seguras (idealmente asignadas fuera del script)
create user 'cdlp'@'%' identified by 'admin';
-- Asignación de permisos
-- Se otorgan permisos específicos en lugar de todos los permisos a todas las tablas futuras
grant select, insert, update, delete on placas_db.* to 'cdlp'@'%';
flush privileges;

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


INSERT INTO categoria (id, nombre, descripcion) VALUES 
(1, 'Placas Conmemorativas', 'Placas para homenajes y recuerdos'),
(2, 'Trofeos y Reconocimientos', 'Trofeos para premiaciones y logros');


INSERT INTO placa (nombre, descripcion, material, tamanio, precio, imagen_nombre, disponible, categoria_id) VALUES 
('Placa de Cerámica', 'Placa con acabado en cerámica', 'Cerámica', '20x30cm', 25000.00, 'img00000000000001.jpg', TRUE, 1),
('Placa de Granito', 'Grabado resistente en granito', 'Granito', '25x35cm', 35000.00, 'img00000000000002.jpg', TRUE, 1),
('Placa de Mármol', 'Elegante placa en mármol', 'Mármol', '25x35cm', 40000.00, 'img00000000000003.jpg', TRUE, 1),
('Placa de Aluminio Pulido', 'Acabado brillante y duradero', 'Aluminio', '20x30cm', 22000.00, 'img00000000000004.jpg', TRUE, 1),
('Placa de Foto Cerámica', 'Placa con fotografía incluida', 'Cerámica', '20x30cm', 30000.00, 'img00000000000005.jpg', TRUE, 1),
('Placa de Aluminio Fundido', 'Estilo clásico y pesado', 'Aluminio', '30x40cm', 45000.00, 'img00000000000006.jpg', TRUE, 1),
('Placa para Bufete', 'Placa formal para abogados', 'Bronce/Aluminio', '40x20cm', 50000.00, 'img00000000000007.jpg', TRUE, 1),
('Placa para Constructoras', 'Placa institucional', 'Metal', '50x40cm', 60000.00, 'img00000000000008.jpg', TRUE, 1),
('Placa en Vidrio', 'Diseño moderno y minimalista', 'Vidrio', '30x30cm', 35000.00, 'img00000000000009.jpg', TRUE, 1);


INSERT INTO placa (nombre, descripcion, material, tamanio, precio, imagen_nombre, disponible, categoria_id) VALUES 
('Esmeralda Base Mármol', 'Trofeo de prestigio', 'Cristal/Mármol', '25cm', 45000.00, 'img00000000000010.jpg', TRUE, 2),
('Gota Acostada de Mármol', 'Diseño curvo exclusivo', 'Mármol', '20cm', 38000.00, 'img00000000000011.jpg', TRUE, 2),
('Gota Base de Mármol', 'Trofeo vertical de gota', 'Mármol/Vidrio', '30cm', 42000.00, 'img00000000000012.jpg', TRUE, 2),
('Reconocimiento de Aluminio', 'Placa sobre base de madera', 'Aluminio', '20x25cm', 28000.00, 'img00000000000013.jpg', TRUE, 2),
('Reconocimiento Aluminio Pulido', 'Acabado de alta gama', 'Aluminio', '25x20cm', 32000.00, 'img00000000000014.jpg', TRUE, 2),
('Reconocimiento en Acrílico', 'Diseño moderno transparente', 'Acrílico', '20x30cm', 25000.00, 'img00000000000015.jpg', TRUE, 2);