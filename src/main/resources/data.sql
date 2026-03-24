-- Datos iniciales para categorías

DELETE FROM categorias;

ALTER TABLE categorias ALTER COLUMN id RESTART WITH 1;

-- Categorías de productos
INSERT INTO categorias (nombre, descripcion, activo, fecha_creacion, fecha_actualizacion)
VALUES
('Electrónica', 'Dispositivos y productos electrónicos', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Computación', 'Computadoras, laptops y accesorios', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Periféricos', 'Mouse, teclados, webcams', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Almacenamiento', 'Discos duros, SSDs, memorias USB', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Audio', 'Auriculares, bocinas, micrófonos', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Video', 'Monitores, webcams, proyectores', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Redes', 'Routers, switches, cables de red', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Componentes PC', 'RAM, procesadores, tarjetas gráficas', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Impresión', 'Impresoras, scanners, multifuncionales', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Gaming', 'Accesorios y hardware para gaming', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);