INSERT INTO `sys_users`
    (`id`, `username`, `real_name`, `password`,  `status`)
VALUES
    (1265586318612762624, 'admin', 'admin', '$2a$10$1OUc.r.DmlMKTPiXwhhWw.Sr0McOHHLOCNd6/Wgia7oeHgRjCOYeq', 1);

INSERT INTO `sys_roles`(`id`, `name`, `code`) VALUES (1265585799404064768, '超级管理员', 'admin');

INSERT INTO `sys_user_roles`(`id`, `user_id`, `role_code`) VALUES (1, 1265586318612762624, 'admin');
