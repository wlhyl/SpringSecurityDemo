CREATE TABLE `sys_role` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `role` varchar(100) NOT NULL,
  `deleted` tinyint(1) unsigned NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
