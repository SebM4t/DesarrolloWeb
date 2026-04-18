DROP DATABASE placas_db;
CREATE DATABASE IF NOT EXISTS placas_db;
USE placas_db;
CREATE USER IF NOT EXISTS 'cdlp'@'%' IDENTIFIED BY 'admin';
GRANT SELECT, INSERT, UPDATE, DELETE ON placas_db.* TO 'cdlp'@'%';
FLUSH PRIVILEGES;

-- 2. Estructura de Tablas de Productos
CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(100)
);

CREATE TABLE placa (
    id_placa INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100),
    material VARCHAR(100) NOT NULL,
    tamanio VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    imagen_nombre VARCHAR(250),
    disponible BOOLEAN DEFAULT TRUE,
    id_categoria INT,
    CONSTRAINT fk_categoria 
        FOREIGN KEY (id_categoria) 
        REFERENCES categoria(id_categoria)
);

CREATE TABLE usuario (
  id_usuario INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(30) NOT NULL UNIQUE,
  password VARCHAR(512) NOT NULL,
  nombre VARCHAR(20) NOT NULL,
  apellidos VARCHAR(30) NOT NULL,
  correo VARCHAR(75) NULL UNIQUE,
  telefono VARCHAR(25) NULL,
  ruta_imagen VARCHAR(1024),
  activo BOOLEAN DEFAULT TRUE,
  fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CHECK (correo LIKE '%@%.%')
);

CREATE TABLE rol (
  id_rol INT AUTO_INCREMENT PRIMARY KEY,
  rol VARCHAR(20) UNIQUE,
  fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE usuario_rol (
  id_usuario INT NOT NULL,
  id_rol INT NOT NULL,
  fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_usuario, id_rol),
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
  FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE
);

CREATE TABLE ruta (
    id_ruta INT AUTO_INCREMENT PRIMARY KEY,
    ruta VARCHAR(255) NOT NULL,
    id_rol INT NULL,
    requiere_rol BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol),
    CHECK (id_rol IS NOT NULL OR requiere_rol = FALSE)
);

INSERT INTO categoria (id_categoria, nombre, descripcion) VALUES  
(1, 'Placas Conmemorativas', 'Placas para homenajes y recuerdos'),
(2, 'Trofeos y Reconocimientos', 'Trofeos para premiaciones y logros'),
(3, 'Placas para Mascotas', 'Placas para mascotas'),
(4, 'Placas para Empresas', 'Placas para empresas');

INSERT INTO placa (nombre, descripcion, material, tamanio, precio, imagen_nombre, disponible, id_categoria) VALUES 
('Placa de Cerámica', 'Placa con acabado en cerámica', 'Cerámica', '20x30cm', 25000.00, 'img00000000000001.jpg', TRUE, 1),
('Placa de Granito', 'Grabado resistente en granito', 'Granito', '25x35cm', 35000.00, 'img00000000000002.jpg', TRUE, 1);
-- ... continúa con tus demás registros ...

INSERT INTO usuario (username, password, nombre, apellidos, correo, telefono, ruta_imagen, activo) VALUES  
('andres','$2a$10$P1.w58XvnaYQUQgZUCk4aO/RTRl8EValluCqB3S2VMLTbRt.tlre.','Andrés','Gómez Rojas','andres@gmail.com','8888-1111','https://img2.rtve.es/i/?w=1600&i=1677587980597.jpg',1),
('laura','$2a$10$GkEj.ZzmQa/aEfDmtLIh3udIH5fMphx/35d0EYeqZL5uzgCJ0lQRi','Laura','Fernández Soto','laura@gmail.com','8888-2222','https://img.freepik.com/fotos-premium/perfil-usuario-png-mujer-negocios-profesional-pegatina-fondo-transparente_53876-1049017.jpg',1),
('carlos','$2a$10$koGR7eS22Pv5KdaVJKDcge04ZB53iMiw76.UjHPY.XyVYlYqXnPbO','Carlos','Ramírez Vargas','carlos@gmail.com','8888-3333','https://img.freepik.com/foto-gratis/cruzado-confiado-fresco-latin-cruz_1368-2266.jpg?semt=ais_hybrid&w=740&q=80',1);

INSERT INTO rol (rol) VALUES ('ADMIN'), ('VENDEDOR'), ('USER');

INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (1,1), (1,2), (1,3), (2,2), (2,3), (3,3);

-- Rutas
INSERT INTO ruta (ruta, id_rol) VALUES ('/admin/**', 1), ('/usuarios/**', 1), ('/config/**', 1);
INSERT INTO ruta (ruta, id_rol) VALUES ('/catalogo/crear', 2), ('/catalogo/editar/**', 2), ('/pedidos/**', 2);
INSERT INTO ruta (ruta, id_rol) VALUES ('/mascotas/**', 3), ('/cuenta/**', 3), ('/carrito/**', 3);
INSERT INTO ruta (ruta,requiere_rol) VALUES 
('/',false),
('/index',false),
('/errores/**',false),
('/carrito/**',false),
('/registro/**',false),
('/403',false),
('/fav/**',false),
('/js/**',false),
('/css/**',false),
('/webjars/**',false);


-- Consultas finales
SELECT * FROM usuario;