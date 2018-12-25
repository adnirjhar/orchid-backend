CREATE SCHEMA IF NOT EXISTS `orchiddb`;
USE `orchiddb`;

CREATE TABLE IF NOT EXISTS `application_config` (
  `config_name` varchar(255) NOT NULL,
  `config_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`config_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `instance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dialogflowref` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjps29jhf7o46blw2tlxlh53iy` (`created_by`),
  CONSTRAINT `FKjps29jhf7o46blw2tlxlh53iy` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `esp_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_channel` varchar(255) DEFAULT NULL,
  `device_id` varchar(255) DEFAULT NULL,
  `device_secret` varchar(255) DEFAULT NULL,
  `dialogflowref` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `local_ip_address` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `instance_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoegq41ma0ybwe578e4118qiy6` (`instance_id`),
  CONSTRAINT `FKoegq41ma0ybwe578e4118qiy6` FOREIGN KEY (`instance_id`) REFERENCES `instance` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `esp_hardware` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dialogflowref` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `hardware_id` varchar(255) DEFAULT NULL,
  `last_logged` datetime DEFAULT NULL,
  `last_value` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `esp_device` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7qvaylu298lew1wsweeeh0ivb` (`esp_device`),
  CONSTRAINT `FK7qvaylu298lew1wsweeeh0ivb` FOREIGN KEY (`esp_device`) REFERENCES `esp_device` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `esp_device_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` varchar(255) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `logged_at` datetime DEFAULT NULL,
  `esp_device` int(11) NOT NULL,
  `esp_hardware` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnp499c4k16f3439bkbb3klaue` (`esp_device`),
  KEY `FKl4syoytr8uvh5kbatyvpgrphb` (`esp_hardware`),
  CONSTRAINT `FKl4syoytr8uvh5kbatyvpgrphb` FOREIGN KEY (`esp_hardware`) REFERENCES `esp_hardware` (`id`),
  CONSTRAINT `FKnp499c4k16f3439bkbb3klaue` FOREIGN KEY (`esp_device`) REFERENCES `esp_device` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT IGNORE INTO `application_config` (`config_name`, `config_value`)
VALUES
  ('mqttPort','1883'),
  ('mqttServer','iot.eclipse.org'),
  ('orchidCommonChannel','orchidCommonChannelAddress'),
  ('orchidMQTTPassword','orchidCommonMQTTUserPassword'),
  ('orchidMQTTUsername','orchidCommonMQTTUser');
