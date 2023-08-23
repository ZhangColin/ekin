-- 角色表
CREATE TABLE `sys_roles` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '上级角色',
  `name` varchar(32) NOT NULL COMMENT '角色名称',
  `rules` text COMMENT '权限规则Ids',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态[0:禁用, 1:启用]',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 用户角色关联表
CREATE TABLE `sys_user_roles` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NULL COMMENT '用户Id',
  `role_id` bigint NULL COMMENT '角色Id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_user_role_user_id_role_id`(`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 菜单权限规则表
CREATE TABLE `sys_menu_rules` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单规则Id',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '上级菜单规则Id',
  `type` varchar(8) NOT NULL DEFAULT 'menuRule' COMMENT '类型[1:目录 menu_dir, 2:菜单项 menuRule, 3:操作 operate]',
  `title` varchar(64) NOT NULL DEFAULT '' COMMENT '标题',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '规则名称',
  `path` varchar(64) NULL COMMENT '路由路径',
  `component` varchar(64) NULL COMMENT '组件路径',
  `icon` varchar(64) NULL COMMENT '图标',
  `menu_type` varchar(8) NULL COMMENT '菜单类型[1:选项卡 tab, 2:链接 link, 3:iframe iframe]',
  `url` varchar(256) NULL COMMENT 'Url',
  `keepalive` tinyint NULL COMMENT '缓存[0:关闭, 1:开启]',
  `remark` nvarchar(256) NULL COMMENT '备注',
  `sequence` int NOT NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态[0:禁用, 1:启用]',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_menu_rules_parent_id`(`parent_id`),
  INDEX `index_menu_rules_sequence`(`sequence`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限规则表';

