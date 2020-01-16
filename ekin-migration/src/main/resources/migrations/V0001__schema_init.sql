-- 组织机构表
CREATE TABLE `sys_departments` (
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
  INDEX `index_department_parent_id` (`parent_id`),
  INDEX `index_department_code` (`code`),
  INDEX `index_department_sort` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织机构表';

-- 岗位表
CREATE TABLE `sys_positions` (
  `id` bigint NOT NULL COMMENT '岗位Id',
  `department_id` bigint NULL COMMENT '组织Id',
  `name` varchar(64) NOT NULL COMMENT '岗位名称',
  `enabled` bit(1) NOT NULL COMMENT '状态',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE key `index_role_department_id` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位表';

-- 用户表
CREATE TABLE `sys_users` (
  `id` bigint NOT NULL COMMENT '用户Id',
  `username` varchar(32) NOT NULL COMMENT '登录账号',
  `phone` varchar(32) NOT NULL DEFAULT '' COMMENT '电话',
  `email` varchar(64) NOT NULL DEFAULT '' COMMENT '邮箱',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `real_name` varchar(32) NOT NULL COMMENT '真实姓名',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
  `birthday` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生日',
  `sex` tinyint NOT NULL DEFAULT 1 COMMENT '性别（1：男 2：女）',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态(1：正常  2：冻结 ）',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` bit(1) NOT NULL DEFAULT b'1',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
   PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_user_name`(`username`),
  INDEX `index_user_status`(`status`),
  INDEX `index_user_soft_deleted`(`active`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 用户组织机构关联表
CREATE TABLE `sys_user_departments` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `user_id` bigint NULL COMMENT '用户Id',
  `department_id` bigint NULL COMMENT '组织Id',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_user_department_user_id`(`user_id`),
  INDEX `index_user_department_department_id`(`department_id`),
  INDEX `index_user_department_user_id_department_id`(`user_id`, `department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户组织机构关联表';

-- 权限表
CREATE TABLE `sys_permissions` (
  `id` bigint NOT NULL COMMENT '权限Id',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '上级权限',
  `name` varchar(32) NOT NULL COMMENT '权限标题',
  `code` varchar(255) NOT NULL DEFAULT '' COMMENT '权限编码',
  `type` tinyint NOT NULL DEFAULT 1 COMMENT '类型',
  `component` varchar(64) NOT NULL COMMENT '组件',
  `component_name` varchar(64) NOT NULL COMMENT '组件名称',
  `icon` varchar(64) NOT NULL COMMENT '图标',
  `path` varchar(64) NOT NULL COMMENT '路径',
  `i_frame` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否外链',
  `cache` bit(1) NOT NULL DEFAULT b'0' COMMENT '缓存',
  `hidden` bit(1) NOT NULL DEFAULT b'0' COMMENT '隐藏',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_permission_parent_id`(`parent_id`),
  INDEX `index_permission_sort`(`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';


-- 角色表
CREATE TABLE `sys_roles` (
  `id` bigint NOT NULL COMMENT '角色Id',
  `name` varchar(32) NOT NULL COMMENT '角色名称',
  `code` varchar(32) NOT NULL COMMENT '角色编码',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`) USING BTREE,
  UNIQUE key `index_role_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';


-- 用户角色关联表
CREATE TABLE `sys_user_roles` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NULL COMMENT '用户Id',
  `role_code` varchar(32) NOT NULL COMMENT '角色编码',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_user_role_user_id`(`user_id`),
  INDEX `index_user_role_role_code`(`role_code`),
  INDEX `index_user_role_user_id_role_code`(`user_id`, `role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';


-- 角色权限关联表
CREATE TABLE `sys_role_permissions` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint NULL COMMENT '角色Id',
  `permission_id` bigint NULL COMMENT '权限Id',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_role_permission_role_id`(`role_id`),
  INDEX `index_role_permission_permission_id`(`permission_id`),
  INDEX `index_role_permission_role_id_permission_id`(`role_id`, `permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 用户操作日志表
CREATE TABLE `sys_operation_logs` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_type` tinyint NOT NULL DEFAULT 4 COMMENT '日志类型(1：登录  2：退出  3：注册  4：业务操作 ）',
  `url` varchar(255) NOT NULL COMMENT '请求url',
  `method` varchar(16) NOT NULL COMMENT '方法',
  `params` text NOT NULL COMMENT '参数',
  `request_ip` varchar(64) NOT NULL COMMENT '请求IP',
  `time` bigint NOT NULL COMMENT '请求耗时',
  `user_id` bigint NOT NULL COMMENT '用户Id',
  `success` tinyint NOT NULL DEFAULT 0 COMMENT '是否执行成功(1：成功  0：失败 ）',
  `exception_detail` text NOT NULL COMMENT '异常详情',
  `description` varchar(255) NOT NULL COMMENT '描述',
  `browser` varchar(255) NOT NULL COMMENT '浏览器',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_operation_log_type`(`log_type`),
  INDEX `index_operation_log_user_id`(`user_id`),
  INDEX `index_operation_log_url`(`url`),
  INDEX `index_operation_log_created`(`created`)
) ENGINE=InnoDB AUTO_INCREMENT = 100544  DEFAULT CHARSET=utf8mb4 COMMENT='用户操作日志表';
