







# _________________________________________
# ________________________________________
# ________________________________________

select * from h_currencies ;
select * from h_departments ;
select * from h_hotels ;
select * from h_hotels_meta ;
select * from h_tickets ;
select * from h_tickets_departments ;
select * from h_tickets_meta ;
select * from h_tickets_priorities ;
select * from h_tickets_status ;
select * from h_users ;
select * from h_users_meta ;
select * from sys_authority ;
select * from sys_config ;
select * from sys_users ;
select * from sys_users_meta ;
select * from user_authority ;
select * from sys_users ;


# ________________________________________

drop table sys_users ;
drop table h_tickets_departments ;
drop table h_hotels_meta ;
drop table sys_users_meta ;
drop table user_authority ;
drop table sys_config ;
drop table sys_authority ;
drop table h_users_meta ;
drop table h_tickets_meta ;
drop table h_tickets ;
drop table h_tickets_priorities ;
drop table h_tickets_status ;
drop table h_users ;
drop table h_departments ;
drop table h_hotels ;
drop table h_currencies ;
drop table sys_users ;

# ________________________________________

CREATE TABLE `h_currencies` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `decimals` int(11) NOT NULL,
  `position` enum('before','after') DEFAULT 'after',
  `symbol` varchar(3) DEFAULT NULL,
  `currency_id` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;

CREATE TABLE `h_hotels` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Clave Primaria',
  `name` varchar(255) NOT NULL COMMENT 'Nombre del hotel',
  `comercial_name` varchar(255) DEFAULT NULL COMMENT 'Nombre comercial del hotel (para las facturas)',
  `address` text NOT NULL COMMENT 'Dirección del hotel',
  `post_code` text NOT NULL COMMENT 'Código postal del hotel',
  `city` text NOT NULL COMMENT 'Ciudad',
  `email` varchar(255) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `web` varchar(255) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Indica si el hotel está activo en el sistema (1) o no (0). Por defecto está activo',
  `ID_h_currencies` int(11) NOT NULL COMMENT 'FK De la tabla h_currencies',
  PRIMARY KEY (`ID`),
  KEY `fk_currencies_idx` (`ID_h_currencies`),
  CONSTRAINT `fk_currencies` FOREIGN KEY (`ID_h_currencies`) REFERENCES `h_currencies` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Tabla con la información relativa al hotel' ;

CREATE TABLE `h_departments` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_h_hotels` int(11) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `h_hotels_idx` (`ID_h_hotels`),
  CONSTRAINT `h_hotels` FOREIGN KEY (`ID_h_hotels`) REFERENCES `h_hotels` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;

CREATE TABLE `h_hotels_meta` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_h_hotels` int(11) NOT NULL COMMENT 'FK Del hotel en la tabla h_hotels',
  `key` varchar(45) NOT NULL,
  `value` text,
  PRIMARY KEY (`ID`),
  KEY `fk_h_hotels_idx` (`ID_h_hotels`),
  CONSTRAINT `fk_h_hotels` FOREIGN KEY (`ID_h_hotels`) REFERENCES `h_hotels` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Información extra relacionada con el hotel.' ;

CREATE TABLE `h_tickets_priorities` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) NOT NULL,
  `order` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `description_UNIQUE` (`description`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;

CREATE TABLE `h_tickets_status` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) NOT NULL,
  `order` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `description_UNIQUE` (`description`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;

CREATE TABLE `h_users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_h_departments` int(11) NOT NULL COMMENT 'FK de la tabla h_departments',
  `username` varchar(45) NOT NULL COMMENT 'Nombre de usuario',
  `password` blob NOT NULL COMMENT 'Contraseña',
  `email` varchar(255) NOT NULL,
  `banned` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Indica si un usuario se ha baneado. Por ejp, intentó 3 veces acceso incorrecto',
  `name` varchar(255) NOT NULL COMMENT 'Nombre',
  `surname` varchar(255) NOT NULL COMMENT 'Primer Apellido',
  `password_expiration` datetime NOT NULL COMMENT 'Fecha de caducidad del password',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_h_departments_idx` (`ID_h_departments`),
  CONSTRAINT `fk_h_departments` FOREIGN KEY (`ID_h_departments`) REFERENCES `h_departments` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Información relativa a los usuarios que trabajan en los diferentes departamentos de un hotel' ;

CREATE TABLE `h_tickets` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_h_tickets_status` int(11) NOT NULL,
  `ID_h_tickets_channels` int(11) DEFAULT NULL,
  `ID_h_tickets_priorities` int(11) NOT NULL,
  `ID_h_users` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` text,
  `room` int(11) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_h_tickets_status_idx` (`ID_h_tickets_status`),
  KEY `fk_h_tickets_priorities_idx` (`ID_h_tickets_priorities`),
  KEY `fk_h_users_idx` (`ID_h_users`),
  CONSTRAINT `fk_h_tickets_priorities` FOREIGN KEY (`ID_h_tickets_priorities`) REFERENCES `h_tickets_priorities` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_h_tickets_status` FOREIGN KEY (`ID_h_tickets_status`) REFERENCES `h_tickets_status` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_h_users_tickets` FOREIGN KEY (`ID_h_users`) REFERENCES `h_users` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;

CREATE TABLE `h_tickets_departments` (
  `ID_h_tickets` int(11) NOT NULL,
  `ID_h_departments` int(11) NOT NULL,
  PRIMARY KEY (`ID_h_tickets`,`ID_h_departments`),
  KEY `fk_tickets_departments_idx` (`ID_h_departments`),
  CONSTRAINT `fk_departments_tickets` FOREIGN KEY (`ID_h_tickets`) REFERENCES `h_tickets` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_tickets_departments` FOREIGN KEY (`ID_h_departments`) REFERENCES `h_departments` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Contiene la relación entre tickets y departamentos' ;

CREATE TABLE `h_tickets_meta` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_h_tickets` int(11) NOT NULL,
  `key` varchar(45) NOT NULL,
  `value` text,
  PRIMARY KEY (`ID`),
  KEY `fk_h_tickets_idx` (`ID_h_tickets`),
  CONSTRAINT `fk_h_tickets` FOREIGN KEY (`ID_h_tickets`) REFERENCES `h_tickets` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;

CREATE TABLE `h_users_meta` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_h_users` int(11) NOT NULL,
  `key` varchar(45) NOT NULL,
  `value` text,
  PRIMARY KEY (`ID`),
  KEY `fk_h_users_idx` (`ID_h_users`),
  CONSTRAINT `fk_h_users` FOREIGN KEY (`ID_h_users`) REFERENCES `h_users` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;

CREATE TABLE `sys_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` tinytext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 ;

CREATE TABLE `sys_config` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(45) NOT NULL,
  `value` text,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Tabla que contendrá la configuración de nuestro sistema' ;

CREATE TABLE `sys_users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varbinary(255) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `banned` tinyint(1) DEFAULT '0',
  `name` varchar(100) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1 COMMENT='Contiene la información principal de los usuarios del sistema. Esto son aquellos usuarios propios de Ameniti y que no son ni huéspedes ni trabajadores de un departamento del hotel' ;

CREATE TABLE `user_authority` (
  `user_id` int(11) DEFAULT NULL,
  `authority_id` int(11) NOT NULL,
  KEY `fk_authority_idx` (`authority_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1  ;

CREATE TABLE `sys_users_meta` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_sys_user` int(11) NOT NULL,
  `key` varchar(45) NOT NULL,
  `value` text,
  PRIMARY KEY (`ID`),
  KEY `fk_sys_users_idx` (`ID_sys_user`),
  CONSTRAINT `fk_sys_users` FOREIGN KEY (`ID_sys_user`) REFERENCES `sys_users` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;

# ________________________________________

/*
123
$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra

aaa
$2a$04$QPan5ic3WG13JdA7ArRlXev.ZV33X7KXC8SIENAyvVymYR3QuboKe
*/

INSERT INTO sys_users (ID, username, password, email, banned,
name, surname, modified, created)
 VALUES (1, 'user', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',
 'user@example.com', false, 'juan', 'lopez', '2017-10-01 21:58:58.50807', '2017-10-01 21:58:58.50807');

INSERT INTO sys_users (ID, username, password, email, banned,
 name, surname, modified, created)
 VALUES (2, 'admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',
 'admin@example.com', false, 'pepe', 'sanchez', '2017-10-01 21:58:58.50807', '2017-10-01 21:58:58.50807');

INSERT INTO sys_authority (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO sys_authority (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 2);

# ________________________________________





INSERT INTO h_currencies (ID, decimals, position, symbol, currency_id)
 VALUES (1, 3, 'after','eur','eur');



INSERT INTO h_hotels (ID, name, comercial_name, address, post_code,
 city, email, phone, web, active, ID_h_currencies)
 VALUES (1, 'holidays inn', 'holidays inn malaga',
 'oxford street 22', '12345', 'malaga', 'aaa@holidaysinn.com', '123456789', 'www.holidaysinn.com', 1,1);








INSERT INTO h_departments (ID, ID_h_hotels, description)
 VALUES (1, 1, 'kitchen');







INSERT INTO h_users (ID, ID_h_departments, username, password, email, banned, name, surname, password_expiration)
 VALUES (1, 1, 'juan22', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',
 'juan22@example.com', 0, 'juanito', 'ordonez', '2027-10-01 21:58:58.50807');























# ________________________________________
# ________________________________________
# ________________________________________


