-- 角色
INSERT INTO `sys_roles`(`id`, `name`) SELECT 3, 'HR' FROM DUAL WHERE NOT EXISTS (SELECT * FROM `sys_roles` WHERE `id` = 3);;
