-- 角色
INSERT INTO `sys_roles`(`id`, `name`) VALUES (1, '超级管理员');
INSERT INTO `sys_roles`(`id`, `name`) VALUES (2, '员工');

-- 用户角色
INSERT INTO `sys_user_roles`(`user_id`, `role_id`) VALUES (1265586318612762624, 1);

-- 菜单
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (1, 0, '系统管理', 'system', 'component', 0, 99);
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (2, 1, '组织管理', 'organization', 'tree', 0, 10);
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (3, 1, '用户管理', 'user', 'user', 0, 20);
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (4, 1, '角色管理', 'role', 'peoples', 0, 30);
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (5, 1, '菜单管理', 'menu', 'tree-table', 0, 40);
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (6, 1, '资源管理', 'resource', 'list', 0, 50);
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (7, 1, '字典管理', 'dictionary', 'education', 0, 60);

-- 角色菜单
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (1, 1);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (1, 2);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (1, 3);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (1, 4);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (1, 5);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (1, 6);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (1, 7);

-- 资源分类
INSERT INTO `sys_resource_categories`(`id`, `name`, `sort`) VALUES (1, '系统管理', 99);

-- 资源
INSERT INTO `sys_resources`(`id`, `category_id`, `name`, `code`, `url`, `sort`) VALUES (1, 1, '组织管理', 'system:organizations', '/system/organizations', 10);
INSERT INTO `sys_resources`(`id`, `category_id`, `name`, `code`, `url`, `sort`) VALUES (2, 1, '用户管理', 'system:users', '/system/users', 20);
INSERT INTO `sys_resources`(`id`, `category_id`, `name`, `code`, `url`, `sort`) VALUES (3, 1, '角色管理', 'system:roles', '/system/roles', 30);
INSERT INTO `sys_resources`(`id`, `category_id`, `name`, `code`, `url`, `sort`) VALUES (4, 1, '菜单管理', 'system:menus', '/system/menus', 40);
INSERT INTO `sys_resources`(`id`, `category_id`, `name`, `code`, `url`, `sort`) VALUES (5, 1, '资源管理', 'system:resources', '/system/resources', 50);
INSERT INTO `sys_resources`(`id`, `category_id`, `name`, `code`, `url`, `sort`) VALUES (6, 1, '字典管理', 'system:dictionaries', '/system/dictionaries', 60);

-- 角色资源
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (1, 1);
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (1, 2);
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (1, 3);
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (1, 4);
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (1, 5);
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (1, 6);
