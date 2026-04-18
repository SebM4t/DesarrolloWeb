CREATE DATABASE IF NOT EXISTS placas_db;

-- Creación de usuarios con contraseñas seguras (idealmente asignadas fuera del script)
create user 'cdlp'@'%' identified by 'admin';
-- Asignación de permisos
-- Se otorgan permisos específicos en lugar de todos los permisos a todas las tablas futuras
create user 'cdlp'@'%' identified by 'admin';

grant select, insert, update, delete on placas_db.* to 'cdlp'@'%';
flush privileges;

USE placas_db;

CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(100)
);

CREATE TABLE placa (
    id_placa INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50)  NOT NULL,
    descripcion VARCHAR(100),
    material VARCHAR(100) NOT NULL,
    tamanio VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    imagen_nombre VARCHAR(250),
    disponible BOOLEAN DEFAULT TRUE,
    id_categoria INT,
    CONSTRAINT fk_categoria

        FOREIGN KEY (categoria_id)
        REFERENCES categoria(id)
);

        FOREIGN KEY (id_categoria)
        REFERENCES categoria(id_categoria)
);


INSERT INTO categoria (id_categoria, nombre, descripcion) VALUES 
(1, 'Placas Conmemorativas', 'Placas para homenajes y recuerdos'),
(2, 'Trofeos y Reconocimientos', 'Trofeos para premiaciones y logros'),
(3, 'Placas para Mascotas', 'Placas para mascotas'),
(4, 'Placas para Empresas', 'Placas para empresas');


INSERT INTO placa (nombre, descripcion, material, tamanio, precio, imagen_nombre, disponible, id_categoria) VALUES 
('Placa de Cerámica', 'Placa con acabado en cerámica', 'Cerámica', '20x30cm', 25000.00, 'img00000000000001.jpg', TRUE, 1),
('Placa de Granito', 'Grabado resistente en granito', 'Granito', '25x35cm', 35000.00, 'img00000000000002.jpg', TRUE, 1),
('Placa de Mármol', 'Elegante placa en mármol', 'Mármol', '25x35cm', 40000.00, 'img00000000000003.jpg', TRUE, 1),
('Placa de Aluminio Pulido', 'Acabado brillante y duradero', 'Aluminio', '20x30cm', 22000.00, 'img00000000000004.jpg', TRUE, 1),
('Placa de Foto Cerámica', 'Placa con fotografía incluida', 'Cerámica', '20x30cm', 30000.00, 'img00000000000005.jpg', TRUE, 1),
('Placa de Aluminio Fundido', 'Estilo clásico y pesado', 'Aluminio', '30x40cm', 45000.00, 'img00000000000006.jpg', TRUE, 1),
('Placa para Bufete', 'Placa formal para abogados', 'Bronce/Aluminio', '40x20cm', 50000.00, 'img00000000000007.jpg', TRUE, 1),
('Placa para Constructoras', 'Placa institucional', 'Metal', '50x40cm', 60000.00, 'img00000000000008.jpg', TRUE, 1),
('Placa en Vidrio', 'Diseño moderno y minimalista', 'Vidrio', '30x30cm', 35000.00, 'img00000000000009.jpg', TRUE, 1);


INSERT INTO placa (nombre, descripcion, material, tamanio, precio, imagen_nombre, disponible, id_categoria) VALUES 
('Esmeralda Base Mármol', 'Trofeo de prestigio', 'Cristal/Mármol', '25cm', 45000.00, 'img00000000000010.jpg', TRUE, 2),
('Gota Acostada de Mármol', 'Diseño curvo exclusivo', 'Mármol', '20cm', 38000.00, 'img00000000000011.jpg', TRUE, 2),
('Gota Base de Mármol', 'Trofeo vertical de gota', 'Mármol/Vidrio', '30cm', 42000.00, 'img00000000000012.jpg', TRUE, 2),
('Reconocimiento de Aluminio', 'Placa sobre base de madera', 'Aluminio', '20x25cm', 28000.00, 'img00000000000013.jpg', TRUE, 2),
('Reconocimiento Aluminio Pulido', 'Acabado de alta gama', 'Aluminio', '25x20cm', 32000.00, 'img00000000000014.jpg', TRUE, 2),
('Reconocimiento en Acrílico', 'Diseño moderno transparente', 'Acrílico', '20x30cm', 25000.00, 'img00000000000015.jpg', TRUE, 2);

INSERT INTO placa (nombre, descripcion, material, tamanio, precio, imagen_nombre, disponible, id_categoria) VALUES 
('Placa de Granito', 'Placa con fotografía', 'Granito', '25x40cm', 115000.00, 'img00000000000016.jpg', TRUE, 3),
('Placa de Mármol', 'Placa con fotografía', 'Mármol', '20x30cm', 33000.00, 'img00000000000017.jpg', TRUE, 3),
('Placa de Foto Cerámica', 'Placa con fotografía incluida', 'Cerámica', '15x30cm', 60000.00, 'img00000000000018.jpg', TRUE, 3);

INSERT INTO placa (nombre, descripcion, material, tamanio, precio, imagen_nombre, disponible, id_categoria) VALUES 
('Placa de Aluminio Pulido', 'Placa de alto relieve', 'Aluminio', '20x30cm', 45000.00, 'img00000000000019.jpg', TRUE, 4),
('Placa de Aluminio Pulido', 'Placa con fotografía', 'Aluminio', '30x40cm', 130000.00, 'img00000000000020.jpg', TRUE, 4),
('Placa de Mármol', 'Placa con Placa de alto relieve', 'Mármol', '15x30cm', 35000.00, 'img00000000000021.jpg', TRUE, 4);

CREATE TABLE usuario (
  id_usuario INT IDENTITY(1,1) PRIMARY KEY,
  username VARCHAR(30) NOT NULL UNIQUE,
  password VARCHAR(512) NOT NULL,
  nombre VARCHAR(20) NOT NULL,
  apellidos VARCHAR(30) NOT NULL,
  correo VARCHAR(75) NULL UNIQUE,
  telefono VARCHAR(25) NULL,
  ruta_imagen VARCHAR(1024),
  activo BIT,
  fecha_creacion DATETIME DEFAULT GETDATE(),
  fecha_modificacion DATETIME DEFAULT GETDATE(),
  CHECK (correo LIKE '%@%.%')
);

CREATE TABLE rol (
  id_rol INT IDENTITY(1,1) PRIMARY KEY,
  rol VARCHAR(20) UNIQUE,
  fecha_creacion DATETIME DEFAULT GETDATE(),
  fecha_modificacion DATETIME DEFAULT GETDATE()
);

CREATE TABLE usuario_rol (
  id_usuario INT NOT NULL,
  id_rol INT NOT NULL,
  fecha_creacion DATETIME DEFAULT GETDATE(),
  fecha_modificacion DATETIME DEFAULT GETDATE(),
  PRIMARY KEY (id_usuario, id_rol),
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
  FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

CREATE TABLE ruta (
    id_ruta INT IDENTITY(1,1) NOT NULL,
    ruta VARCHAR(255) NOT NULL,
    id_rol INT NULL,
    requiere_rol BIT NOT NULL DEFAULT 1,
    fecha_creacion DATETIME DEFAULT GETDATE(),
    fecha_modificacion DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (id_ruta),
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol),
    CHECK (id_rol IS NOT NULL OR requiere_rol = 0)
);

INSERT INTO usuario (username, password, nombre, apellidos, correo, telefono, ruta_imagen, activo) VALUES 
('andres','$2a$10$P1.w58XvnaYQUQgZUCk4aO/RTRl8EValluCqB3S2VMLTbRt.tlre.','Andrés','Gómez Rojas','andres@gmail.com','8888-1111','https://img2.rtve.es/i/?w=1600&i=1677587980597.jpg',1),
('laura','$2a$10$GkEj.ZzmQa/aEfDmtLIh3udIH5fMphx/35d0EYeqZL5uzgCJ0lQRi','Laura','Fernández Soto','laura@gmail.com','8888-2222','https://img.freepik.com/fotos-premium/perfil-usuario-png-mujer-negocios-profesional-pegatina-fondo-transparente_53876-1049017.jpg',1),
('carlos','$2a$10$koGR7eS22Pv5KdaVJKDcge04ZB53iMiw76.UjHPY.XyVYlYqXnPbO','Carlos','Ramírez Vargas','carlos@gmail.com','8888-3333','https://img.freepik.com/foto-gratis/cruzado-confiado-fresco-latin-cruz_1368-2266.jpg?semt=ais_hybrid&w=740&q=80',1);

INSERT INTO rol (rol) VALUES 
('ADMIN'), 
('VENDEDOR'), 
('USER');

---usuario rol

INSERT INTO usuario_rol (id_usuario, id_rol) VALUES
(1,1), (1,2), (1,3), -- admin total
(2,2), (2,3),        -- vendedor + user
(3,3);               -- user normal

--- rol admin
INSERT INTO ruta (ruta, id_rol) VALUES 
('/admin/**', 1),
('/usuarios/**', 1),
('/config/**', 1);

--vendedor

INSERT INTO ruta (ruta, id_rol) VALUES 

-- Gestión de catálogo
('/catalogo/crear', 2),
('/catalogo/editar/**', 2),
('/catalogo/eliminar/**', 2),

-- Gestión de mascotas
('/mascotas/gestionar/**', 2),

-- Diseños 
('/disenar/crear', 2),
('/disenar/editar/**', 2),

-- Pedidos / ventas
('/pedidos/**', 2),
('/ventas/**', 2),

-- Reportes básicos
('/reportes/ventas', 2),
('/reportes/productos', 2),

-- Empresas 
('/empresas/**', 2),

-- Atención al cliente
('/soporte/responder/**', 2);

---user
INSERT INTO ruta (ruta, id_rol) VALUES 
('/mascotas/**', 3),
('/disenar/**', 3),
('/cuenta/**', 3),
('/carrito/**', 3);

---sin login
INSERT INTO ruta (ruta, requiere_rol) VALUES 
('/', 0),
('/inicio', 0),
('/catalogo', 0),
('/galeria', 0),
('/funciones', 0),
('/faq', 0),
('/soporte', 0),
('/registro/**', 0),
('/login', 0),
('/errores/**', 0),
('/403', 0),
('/js/**', 0),
('/css/**', 0),
('/webjars/**', 0);

SELECT * FROM usuario;
SELECT * FROM rol;
SELECT * FROM usuario_rol;
SELECT * FROM ruta;