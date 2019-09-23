CREATE DATABASE 'mysystem2_0';
USE mysystem2_0;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `pass` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rights` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uri` varchar(255) NOT NULL,
  `shortName` varchar(50) NOT NULL,
  `longName` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rights_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_right` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `rights_user_id_user_idx` (`id_user`),
  KEY `rights_user_id_right_idx` (`id_right`),
  CONSTRAINT `rights_user_id_right` FOREIGN KEY (`id_right`) REFERENCES `rights` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `rights_user_id_user` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_create` datetime NOT NULL,
  `txt` longtext NOT NULL,
  `id_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `message_id_user_idx` (`id_user`),
  CONSTRAINT `message_id_user` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `type_doc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dirPath` longtext NOT NULL,
  `shortName` varchar(50) NOT NULL,
  `longName` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `docs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `id_type` bigint(20) NOT NULL,
  `id_actual_version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `docs_id_type_idx` (`id_type`),
  CONSTRAINT `docs_id_type` FOREIGN KEY (`id_type`) REFERENCES `type_doc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `doc_versions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_create` datetime NOT NULL,
  `id_doc` bigint(20) NOT NULL,
  `lable_doc_ver` int(11) NOT NULL,
  `description` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `doc_version_id_doc_idx` (`id_doc`),
  KEY `doc_version_desc_idx` (`description`),
  CONSTRAINT `doc_version_desc` FOREIGN KEY (`description`) REFERENCES `message` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `doc_version_id_doc` FOREIGN KEY (`id_doc`) REFERENCES `docs` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `mysystem2_0`.`docs` 
ADD INDEX `docs_id_actual_version_idx` (`id_actual_version` ASC) VISIBLE;
;
ALTER TABLE `mysystem2_0`.`docs` 
ADD CONSTRAINT `docs_id_actual_version`
  FOREIGN KEY (`id_actual_version`)
  REFERENCES `mysystem2_0`.`doc_versions` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
