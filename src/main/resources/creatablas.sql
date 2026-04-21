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
    imagen_nombre VARCHAR(500),
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

CREATE TABLE material (
    id_material INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL
);

CREATE TABLE tamanio (
    id_tamanio INT AUTO_INCREMENT PRIMARY KEY,
    dimensiones VARCHAR(50) NOT NULL,  -- Ej: "20x30 cm"
    precio_adicional DECIMAL(10,2) NOT NULL DEFAULT 0
);

CREATE TABLE soporte (
    id_soporte INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(60) NOT NULL,
    telefono VARCHAR(25) NOT NULL,
    correo VARCHAR(25) NOT NULL,
    mensaje_soporte VARCHAR(250) NOT NULL
);

create table factura (
  id_factura INT NOT NULL AUTO_INCREMENT,
  id_usuario INT NOT NULL,
  fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
  total decimal(12,2) check (total>0),
  estado ENUM('Activa', 'Pagada', 'Anulada') NOT NULL,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_factura),  
  index ndx_id_usuario (id_usuario),
  foreign key fk_factura_usuario (id_usuario) references usuario(id_usuario)
);

CREATE TABLE constante (
    id_constante INT AUTO_INCREMENT NOT NULL,
    atributo VARCHAR(25) NOT NULL,
    valor VARCHAR(150) NOT NULL,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id_constante),
    UNIQUE (atributo)
);

create table venta (
  id_venta INT NOT NULL AUTO_INCREMENT,
  id_factura INT NOT NULL,
  id_placa INT NULL,
  id_material INT NULL,
  id_tamanio INT NULL,
  precio_historico decimal(12,2) check (precio_historico>= 0),
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_venta),
  index ndx_factura (id_factura),
  index ndx_placa (id_placa),
  UNIQUE (id_factura, id_placa),
  foreign key fk_venta_factura (id_factura) references factura(id_factura),
  foreign key fk_venta_placa (id_placa) references placa(id_placa)
  );

create table cuenta (
  id_cuenta int not null auto_increment,
  id_usuario int not null,
  ubicacion varchar(255),
  genero varchar(50),
  primary key (id_cuenta),
  unique (id_usuario),
  foreign key (id_usuario) references usuario(id_usuario)
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

('Placa en Vidrio', 'Diseño moderno y minimalista', 'Vidrio', '30x30cm', 35000.00, 'img00000000000009.jpg', TRUE, 1),

('Esmeralda Base Mármol', 'Trofeo de prestigio', 'Cristal/Mármol', '25cm', 45000.00, 'img00000000000010.jpg', TRUE, 2),

('Gota Acostada de Mármol', 'Diseño curvo exclusivo', 'Mármol', '20cm', 38000.00, 'img00000000000011.jpg', TRUE, 2),

('Gota Base de Mármol', 'Trofeo vertical de gota', 'Mármol/Vidrio', '30cm', 42000.00, 'img00000000000012.jpg', TRUE, 2),

('Reconocimiento de Aluminio', 'Placa sobre base de madera', 'Aluminio', '20x25cm', 28000.00, 'img00000000000013.jpg', TRUE, 2),

('Reconocimiento Aluminio Pulido', 'Acabado de alta gama', 'Aluminio', '25x20cm', 32000.00, 'img00000000000014.jpg', TRUE, 2),

('Reconocimiento en Acrílico', 'Diseño moderno transparente', 'Acrílico', '20x30cm', 25000.00, 'img00000000000015.jpg', TRUE, 2),

('Placa de Granito', 'Placa con fotografía', 'Granito', '25x40cm', 115000.00, 'img00000000000016.jpg', TRUE, 3),

('Placa de Mármol', 'Placa con fotografía', 'Mármol', '20x30cm', 33000.00, 'img00000000000017.jpg', TRUE, 3),

('Placa de Foto Cerámica', 'Placa con fotografía incluida', 'Cerámica', '15x30cm', 60000.00, 'img00000000000018.jpg', TRUE, 3),

('Placa de Aluminio Pulido', 'Placa de alto relieve', 'Aluminio', '20x30cm', 45000.00, 'img00000000000019.jpg', TRUE, 4),

('Placa de Aluminio Pulido', 'Placa con fotografía', 'Aluminio', '30x40cm', 130000.00, 'img00000000000020.jpg', TRUE, 4),

('Placa de Mármol', 'Placa con Placa de alto relieve', 'Mármol', '15x30cm', 35000.00, 'img00000000000021.jpg', TRUE, 4);




INSERT INTO usuario (username, password, nombre, apellidos, correo, telefono, ruta_imagen, activo) VALUES  
('andres','$2a$10$P1.w58XvnaYQUQgZUCk4aO/RTRl8EValluCqB3S2VMLTbRt.tlre.','Andrés','Gómez Rojas','andres@gmail.com','8888-1111','https://media.gq.com.mx/photos/67e2e0ced89a1ddf0a935ad1/master/w_2560%2Cc_limit/Hombres%2520alfa.jpg',1),
('laura','$2a$10$GkEj.ZzmQa/aEfDmtLIh3udIH5fMphx/35d0EYeqZL5uzgCJ0lQRi','Laura','Fernández Soto','laura@gmail.com','8888-2222','https://img.freepik.com/fotos-premium/perfil-usuario-png-mujer-negocios-profesional-pegatina-fondo-transparente_53876-1049017.jpg',1),
('carlos','$2a$10$koGR7eS22Pv5KdaVJKDcge04ZB53iMiw76.UjHPY.XyVYlYqXnPbO','Carlos','Ramírez Vargas','carlos@gmail.com','8888-3333','https://img.freepik.com/foto-gratis/cruzado-confiado-fresco-latin-cruz_1368-2266.jpg?semt=ais_hybrid&w=740&q=80',1);

INSERT INTO rol (rol) VALUES ('ADMIN'), ('VENDEDOR'), ('USER');

INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (1,1), (2,2), (2,3), (3,3);

-- Rutas
INSERT INTO ruta (ruta, requiere_rol) VALUES 
('/placa/**', false),
('/disenar/**', false),
('/galeria/**', false),
('/empresas/**', false),
('/mascotas/**', false),
('/funciones/**', false),
('/faq/**', false),
('/soporte/**', false),
('/material/**',false),
('/tamanio/**',false),
('/facturar/**',false),
('/carro/**',false),
('/paypal/**',false),
('/mantenimiento/**',false),
('/constante/**',false),
('/403',false),
('/fav/**',false),
('/js/**',false),
('/css/**',false),
('/webjars/**',false),
('/',false),
('/index',false),
('/errores/**',false),
('/registro/**',false);

INSERT INTO material (nombre, precio) VALUES
('Mármol Blanco',        40000.00),
('Mármol Negro',         45000.00),
('Granito Gris',         35000.00),
('Granito Negro',        38000.00),
('Cerámica Estándar',    25000.00),
('Foto Cerámica',        35000.00),
('Aluminio Pulido',      22000.00),
('Aluminio Fundido',     30000.00),
('Aluminio Anodizado',   28000.00),
('Acrílico Transparente',20000.00),
('Acrílico Negro',       22000.00),
('Bronce',               55000.00);


INSERT INTO tamanio (dimensiones, precio_adicional) VALUES
('10x15 cm',   0.00),
('15x20 cm',   3000.00),
('20x30 cm',   6000.00),
('25x35 cm',   9000.00),
('30x40 cm',   13000.00),
('40x50 cm',   18000.00),
('50x60 cm',   24000.00),
('60x80 cm',   32000.00),
('80x100 cm',  45000.00),
('15x15 cm',   3000.00),
('20x20 cm',   5000.00),
('30x30 cm',   10000.00),
('40x40 cm',   16000.00),
('50x50 cm',   22000.00),
('10x30 cm',   4000.00),
('15x40 cm',   7000.00),
('20x60 cm',   14000.00),
('30x90 cm',   28000.00);

INSERT INTO soporte (nombre_completo, telefono, correo, mensaje_soporte) VALUE
('Susana Ramirez', '70437656', 'susana@gmail.com', 'Buenas, me llegó una placa completamente diferente a la que pedí. Gracias.'),
('Kevin Sanchez', '80348828', 'kevin@gmail.com', 'Buenas, tengo problemas con el carrito. No me deja pagar mi pedido. Gracias.');

INSERT INTO factura (id_usuario,fecha,total,estado) VALUES
(1,'2025-06-05',211560,'Pagada'),
(2,'2025-06-07',554340,'Pagada'),
(3,'2025-07-07',871000,'Pagada'),
(1,'2025-07-15',244140,'Pagada'),
(2,'2025-07-17',414800,'Pagada'),
(3,'2025-07-21',420000,'Pagada');

INSERT INTO constante (atributo,valor) VALUES 
('dominio','localhost'),
('dolar','520.75'),
('paypal.client-id','AaDNEUcELb-wQi6_MOboN0a1Ug4BnD4Z2T2-_KIoDjIb8Rif6525nvRhDu-MS-YdKQ8PJqZi7R6T6e_k'),
('paypal.client-secret','EKBpJ1oXlwfcp60KyF9ednFM4i9G_RkzgPCpDXo_NbQbaO_bICxhs_a_mnepi7524BQeK_qdNPRmLt71'),
('paypal.mode','sandbox'),
('url_paypal_cancel','http://localhost/payment/cancel'),
('url_paypal_success','http://localhost/payment/success'),
('servidor.http','http://localhost:8080'),
('app.paypal.return-url','/paypal/order/capture'),
('app.paypal.cancel-url','/paypal/pago_cancel'),
('app.paypal.cancel-error','/paypal/pago_error'),
('app.paypal.cancel-success','/paypal/pago_success');

INSERT INTO venta (id_factura,id_placa,id_material, id_tamanio, precio_historico) values
(1,5,3,2,45000),
(1,9,5,6,15780),
(1,10,3,5,15000);

INSERT INTO cuenta (id_usuario,ubicacion, genero) values
(2,'Sabanilla','Femenino'),
(3,'Curridabat','Masculino');

-- Consultas finales
SELECT * FROM usuario;

select * from placa;
UPDATE placa SET id_categoria = 1, disponible = 1 WHERE id_categoria IS NULL;

SELECT id_placa, nombre, disponible, id_categoria FROM placa;

SELECT * FROM material;

ALTER TABLE placa MODIFY COLUMN imagen_nombre VARCHAR(500);
ALTER TABLE placa MODIFY COLUMN imagen_nombre TEXT;
DESCRIBE placa;	