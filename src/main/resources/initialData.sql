CREATE SCHEMA IF NOT EXISTS `orchiddb`;
USE `orchiddb`;

INSERT IGNORE INTO `user` (`id`, `email`, `full_name`, `password`, `role`)
VALUES
	(1,'admin@orchid.com','Orchid Admin','$2a$11$SSZGEKDsAWH8EdDmOPTuxOu01ioALM2E7FooC4TadlsU5a2/NQ.ua','ADMIN'),
	(2,'device@orchid.com','Orchid Device','$2a$11$nnCJnS7gGhtN5RYT4nwYvOPz2II7bK1fdbrS6KIQpRl.ZAknFD.Gy','ADMIN'),
	(3,'dialogflow@orchid.com','Orchid DialogFlow','$2a$11$riaQGQWOSB9yGIvpztorl.fHTeH2V/HHcdNRhxVIJUUnMoPvdg/wK','ADMIN');


INSERT IGNORE INTO `instance` (`id`, `dialogflowref`, `name`, `title`, `created_by`)
VALUES
	(1,'home','XLVHOME01','Home',1);


INSERT IGNORE INTO `esp_device` (`id`, `device_channel`, `device_id`, `device_secret`, `dialogflowref`, `enabled`, `local_ip_address`, `title`, `instance_id`)
VALUES
	(1,'ESPXD01:XLVHOME01:103836-V90G1','ESPXD01','RVNQWEQwMTpYTFZIT01FMDE=','room1',b'1',NULL,'My Room',1);


INSERT IGNORE INTO `esp_hardware` (`id`, `dialogflowref`, `enabled`, `hardware_id`, `last_logged`, `last_value`, `title`, `type`, `esp_device`)
VALUES
	(1,'fan1',b'1','RELAY1','2018-12-09 23:18:31',1,'Fan 1','Relay',1),
	(2,'fan2',b'1','RELAY2','2018-12-09 23:18:31',1,'Fan 2','Relay',1);


INSERT IGNORE INTO `esp_device_log` (`id`, `data`, `data_type`, `logged_at`, `esp_device`, `esp_hardware`)
VALUES
	(1,'1','LOG','2018-12-09 15:09:24',1,1),
	(2,'0','LOG','2018-12-09 15:09:26',1,1),
	(3,'1','LOG','2018-12-09 15:09:28',1,1),
	(4,'0','LOG','2018-12-09 15:09:30',1,1),
	(5,'1','LOG','2018-12-09 15:09:49',1,2),
	(6,'0','LOG','2018-12-09 15:09:50',1,2),
	(7,'1','LOG','2018-12-09 15:09:52',1,2),
	(8,'1','LOG','2018-12-09 15:09:53',1,1),
	(9,'0','LOG','2018-12-09 15:09:54',1,1),
	(10,'0','LOG','2018-12-09 15:09:54',1,2);