INSERT INTO `sys_users`
    (`id`, `username`, `real_name`, `password`, `slat`, `status`)
VALUES
    (1265586318612762624, 'admin', 'admin', '21a1394b50c280e79196076be19f5de4', 'IOXImWgA', 1);

INSERT INTO `sys_roles`(`id`, `name`, `code`) VALUES (1265585799404064768, '超级管理员', 'admin');

INSERT INTO `sys_user_roles`(`id`, `user_id`, `role_code`) VALUES (1, 1265586318612762624, 'admin');
