-- 组织机构表
CREATE TABLE `sys_organizations` (
  `id` bigint NOT NULL COMMENT '部门Id',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父机构ID',
  `name` varchar(32) NOT NULL COMMENT '机构/部门名称',
  `type` tinyint NOT NULL DEFAULT 1 COMMENT '机构类型',
  `code` varchar(32) NOT NULL DEFAULT '' COMMENT '机构编码',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `enabled` bit(1) NOT NULL COMMENT '状态',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_organization_parent_id` (`parent_id`),
  INDEX `index_organization_code` (`code`),
  INDEX `index_organization_sort` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织机构表';

-- 岗位表
CREATE TABLE `sys_positions` (
  `id` bigint NOT NULL COMMENT '岗位Id',
  `organization_id` bigint NULL COMMENT '组织Id',
  `name` varchar(64) NOT NULL COMMENT '岗位名称',
  `enabled` bit(1) NOT NULL COMMENT '状态',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE key `index_role_organization_id` (`organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位表';

-- 用户组织机构关联表
CREATE TABLE `sys_user_organizations` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `user_id` bigint NULL COMMENT '用户Id',
  `organization_id` bigint NULL COMMENT '组织Id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_user_organization_user_id_organization_id`(`user_id`, `organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户组织机构关联表';
