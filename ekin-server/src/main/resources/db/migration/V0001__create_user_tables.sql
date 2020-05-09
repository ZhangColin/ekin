-- 用户表
CREATE TABLE `sys_users` (
  `id` bigint NOT NULL COMMENT '用户Id',
  `username` varchar(32) NOT NULL COMMENT '登录账号',
  `phone` varchar(32) NOT NULL DEFAULT '' COMMENT '电话',
  `email` varchar(64) NOT NULL DEFAULT '' COMMENT '邮箱',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `real_name` varchar(32) NOT NULL COMMENT '真实姓名',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
  `birthday` date COMMENT '生日',
  `gender` tinyint NOT NULL DEFAULT 1 COMMENT '性别（0:未知 1：男 2：女）',
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

