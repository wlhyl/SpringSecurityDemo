CREATE TABLE `sys_user_role` (
  `role_id` int(20) unsigned NOT NULL,
  `user_id` int(20) unsigned NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  CONSTRAINT `user_role_touser_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `user_role_to_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
