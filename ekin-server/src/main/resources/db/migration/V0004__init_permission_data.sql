-- 角色
INSERT INTO `sys_roles`(`id`, `parent_id`, `name`, `rules`)
VALUES
    (1, 0, '超级管理员', '*'),
    (2, 1, '普通用户', '1');

-- 用户角色
INSERT INTO `sys_user_roles`(`user_id`, `role_id`)
VALUES
    (1265586318612762624, 1);

-- 菜单权限
INSERT INTO `sys_menu_rules`(`id`, `parent_id`, `type`, `title`, `name`, `path`, `component`, `icon`, `menu_type`, `keepalive`, `sequence`)
VALUES (1, 0, 'menuRule', '控制台', 'dashboard/dashboard', 'dashboard', '/src/views/dashboard.vue', 'fa fa-dashboard', 'tab', 1, 0);

INSERT INTO `sys_menu_rules`(`id`, `parent_id`, `type`, `title`, `name`, `path`, `icon`, `sequence`)
VALUES (2, 0, 'menu_dir', '系统管理', 'system', 'system', 'fa fa-group', 999);

INSERT INTO `sys_menu_rules`(`id`, `parent_id`, `type`, `title`, `name`, `path`, `component`, `icon`, `menu_type`, `keepalive`, `sequence`)
VALUES (3, 2, 'menuRule', '角色管理', 'system/role', 'system/role', '/src/views/system/role/index.vue', 'fa fa-group', 'tab', 1, 0);
INSERT INTO `sys_menu_rules`(`id`, `parent_id`, `type`, `title`, `name`)
VALUES
    (4, 3, 'operate', '查看', 'system/role/search'),
    (5, 3, 'operate', '添加', 'system/role/add'),
    (6, 3, 'operate', '编辑', 'system/role/edit'),
    (7, 3, 'operate', '删除', 'system/role/delete');

INSERT INTO `sys_menu_rules`(`id`, `parent_id`, `type`, `title`, `name`, `path`, `component`, `icon`, `menu_type`, `keepalive`, `sequence`)
VALUES (8, 2, 'menuRule', '用户管理', 'system/user', 'system/user', '/src/views/system/user/index.vue', 'el-icon-UserFilled', 'tab', 1, 1);
INSERT INTO `sys_menu_rules`(`id`, `parent_id`, `type`, `title`, `name`)
VALUES
    (9, 8, 'operate', '查看', 'system/user/search'),
    (10, 8, 'operate', '添加', 'system/user/add'),
    (11, 8, 'operate', '编辑', 'system/user/edit'),
    (12, 8, 'operate', '删除', 'system/user/delete');

INSERT INTO `sys_menu_rules`(`id`, `parent_id`, `type`, `title`, `name`, `path`, `component`, `icon`, `menu_type`, `keepalive`, `sequence`)
VALUES (13, 2, 'menuRule', '菜单权限管理', 'system/menuRule', 'system/menuRule', '/src/views/system/menuRule/index.vue', 'el-icon-Grid', 'tab', 1, 2);
INSERT INTO `sys_menu_rules`(`id`, `parent_id`, `type`, `title`, `name`)
VALUES
    (14, 13, 'operate', '查看', 'system/menuRule/search'),
    (15, 13, 'operate', '添加', 'system/menuRule/add'),
    (16, 13, 'operate', '编辑', 'system/menuRule/edit'),
    (17, 13, 'operate', '删除', 'system/menuRule/delete'),
    (18, 13, 'operate', '排序', 'system/menuRule/sortable');
