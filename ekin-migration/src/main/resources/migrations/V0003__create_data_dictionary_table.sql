-- 数据字典表
DROP TABLE IF EXISTS `sys_dictionaries`;
CREATE TABLE `sys_dictionaries` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '字典名称',
  `code` varchar(32) NOT NULL COMMENT '字典编码',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`) USING BTREE,
  UNIQUE key `index_dictionary_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据字典表';


-- 数据字典项表
DROP TABLE IF EXISTS `esys_dictionary_items`;
CREATE TABLE `sys_user_roles` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dictionary_id` bigint NULL COMMENT '字典Id',
  `label` varchar(64) NOT NULL COMMENT '标签',
  `value` varchar(255) NOT NULL COMMENT '值',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_dictionary_dictionary_id`(`dictionary_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

