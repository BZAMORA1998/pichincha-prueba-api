/*Elimina las tablas*/
DROP TABLE IF EXISTS tbl_transacciones;
DROP TABLE IF EXISTS tbl_cuenta_x_personas;
DROP TABLE IF EXISTS tbl_personas;
DROP TABLE IF EXISTS tbl_cuentas;
DROP TABLE IF EXISTS tbl_tipos_identificacion;
DROP TABLE IF EXISTS tbl_generos;

/*tbl_cuentas*/
create table tbl_cuentas
(
secuencia_cuenta int primary key auto_increment,
nombre varchar(100) not null,
descripcion varchar(500) not null,
es_activo  varchar(1) not null,
fecha_ingreso TIMESTAMP not null,
fecha_modificacion TIMESTAMP null
);

insert into tbl_cuentas (nombre, descripcion,es_activo,fecha_ingreso) 
values("CUENTA 1","DESCRIPCION 1","S",now());
insert into tbl_cuentas (nombre, descripcion,es_activo,fecha_ingreso) 
values("CUENTA 2","DESCRIPCION 2","S",now());
insert into tbl_cuentas (nombre, descripcion,es_activo,fecha_ingreso) 
values("CUENTA 3","DESCRIPCION 3","S",now());


/*tbl_tipos_identificacion*/
create table tbl_tipos_identificacion
(
secuencia_tipo_identificacion int primary key auto_increment,
nombre varchar(50) not null,
es_activo varchar(1) not null
);

insert into tbl_tipos_identificacion(nombre, es_activo) 
values("CÉDULA","S");
insert into tbl_tipos_identificacion(nombre, es_activo) 
values("RUC","S");
insert into tbl_tipos_identificacion(nombre, es_activo) 
values("PASAPORTE","S");

/*tbl_generos*/
create table tbl_generos
(
secuencia_genero int primary key auto_increment,
nombre varchar(50) not null,
es_activo  varchar(1) not null
);

insert into tbl_generos (nombre, es_activo) 
values("MASCULINO","S");
insert into tbl_generos (nombre, es_activo) 
values("FEMENINO","S");

/*tbl_personas*/
create table tbl_personas
(
secuencia_persona int primary key auto_increment,
secuencia_tipo_identificacion int not null,
numero_identificacion varchar(50) not null unique,
primer_nombre varchar(50) not null,
segundo_nombre varchar(50),
primer_apellido varchar(50) not null,
segundo_apellido varchar(50),
fecha_ingreso TIMESTAMP not null,
fecha_modificacion TIMESTAMP null,
secuencia_genero int not null,
es_activo  varchar(1) not null
);
ALTER TABLE tbl_personas ADD FOREIGN KEY (secuencia_tipo_identificacion) REFERENCES tbl_tipos_identificacion(secuencia_tipo_identificacion);
ALTER TABLE tbl_personas ADD FOREIGN KEY (secuencia_genero) REFERENCES tbl_generos(secuencia_genero);

insert into tbl_personas (secuencia_tipo_identificacion, numero_identificacion,
primer_nombre,segundo_nombre,primer_apellido,segundo_apellido,fecha_ingreso,
secuencia_genero,
es_activo) 
values(1,"0928914464","BRYAN","STEVEN","ZAMORA","LITARDO",now(),1,"S");

/*tbl_cuenta_x_personas*/
create table tbl_cuenta_x_personas
(
secuencia_cuenta int not null,
secuencia_persona int not null,
PRIMARY KEY (secuencia_cuenta, secuencia_persona),
fecha_ingreso date not null,
fecha_modificacion date,
es_activo  varchar(1) not null
);

ALTER TABLE tbl_cuenta_x_personas ADD FOREIGN KEY (secuencia_cuenta) REFERENCES tbl_cuentas(secuencia_cuenta);
ALTER TABLE tbl_cuenta_x_personas ADD FOREIGN KEY (secuencia_persona) REFERENCES tbl_personas(secuencia_persona);

insert into tbl_cuenta_x_personas (secuencia_cuenta, secuencia_persona,
fecha_ingreso,es_activo) 
values(1,1,now(),"S");
insert into tbl_cuenta_x_personas (secuencia_cuenta, secuencia_persona,
fecha_ingreso,es_activo) 
values(2,1,now(),"S");
insert into tbl_cuenta_x_personas (secuencia_cuenta, secuencia_persona,
fecha_ingreso,es_activo) 
values(3,1,now(),"S");

/*tbl_transacciones*/
create table tbl_transacciones
(
secuencia_transaccion int primary key auto_increment,
secuencia_cuenta int, 
secuencia_persona int,
descripcion varchar(500),
valor_total double,
fecha_ingreso TIMESTAMP not null,
fecha_modificacion TIMESTAMP null,
es_activo  varchar(1) not null
);

ALTER TABLE tbl_transacciones ADD FOREIGN KEY (secuencia_cuenta,secuencia_persona) REFERENCES tbl_cuenta_x_personas(secuencia_cuenta,secuencia_persona);

insert into tbl_transacciones (secuencia_cuenta,
secuencia_persona,descripcion,valor_total,fecha_ingreso,es_activo) 
values(1,1,"COMPRA DE VIVERES",10,now(),"S");
insert into tbl_transacciones (secuencia_cuenta,
secuencia_persona,descripcion,valor_total,fecha_ingreso,es_activo) 
values(2,1,"COMPRA DE LEGUMBRE",20,now(),"S");
insert into tbl_transacciones (secuencia_cuenta,
secuencia_persona,descripcion,valor_total,fecha_ingreso,es_activo) 
values(3,1,"COMPRA DE FRUTA",30,now(),"S");

