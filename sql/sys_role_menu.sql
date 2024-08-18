CREATE TABLE `sys_role_menu` (
  `role_id` int(20) unsigned NOT NULL,
  `menu_id` int(20) unsigned NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`),
  CONSTRAINT `role_menu_to_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`),
  CONSTRAINT `role_menu_to_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
