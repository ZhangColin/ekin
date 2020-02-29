-- 角色表
CREATE TABLE `sys_roles` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `name` varchar(32) NOT NULL COMMENT '角色名称',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态(0：禁用  1：启用 ）',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_role_sort`(`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 用户角色关联表
CREATE TABLE `sys_user_roles` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NULL COMMENT '用户Id',
  `role_id` bigint NULL COMMENT '角色Id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_user_role_user_id_role_id`(`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 菜单表
CREATE TABLE `sys_menus` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单Id',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '上级菜单',
  `title` varchar(64) NOT NULL COMMENT '菜单名称',
  `name` varchar(32) NOT NULL COMMENT '前端名称',
  `icon` varchar(64) NOT NULL COMMENT '前端图标',
  `hidden` bit(1) NOT NULL DEFAULT b'0' COMMENT '隐藏',
  `level` int NOT NULL DEFAULT 0 COMMENT '菜单级数',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_menu_parent_id`(`parent_id`),
  INDEX `index_menu_sort`(`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- 角色菜单关联表
CREATE TABLE `sys_role_menus` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint NULL COMMENT '角色Id',
  `menu_id` bigint NULL COMMENT '权限Id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_role_menu_role_id_menu_id`(`role_id`, `menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

-- 资源分类表
CREATE TABLE `sys_resource_categories` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '资源分类Id',
  `name` varchar(32) NOT NULL COMMENT '资源分类名称',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_menu_sort`(`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源分类表';

-- 资源表
CREATE TABLE `sys_resources` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '资源Id',
  `category_id` bigint NULL COMMENT '资源分类Id',
  `name` varchar(32) NOT NULL COMMENT '资源名称',
  `code` varchar(128) NOT NULL DEFAULT '' COMMENT '权限编码',
  `url` varchar(128) NOT NULL DEFAULT '' COMMENT '资源Url',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_resource_sort`(`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源表';

-- 角色资源关联表
CREATE TABLE `sys_role_resources` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint NULL COMMENT '角色Id',
  `resource_id` bigint NULL COMMENT '权限Id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_role_resource_role_id_resource_id`(`role_id`, `resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色资源关联表';


