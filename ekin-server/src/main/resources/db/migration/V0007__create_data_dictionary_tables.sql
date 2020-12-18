-- 数据字典表
CREATE TABLE `sys_dicts` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '字典名称',
  `code` varchar(32) NOT NULL COMMENT '字典编码',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE key `index_dict_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据字典表';

INSERT INTO `sys_dicts`(`id`, `name`, `code`, `description`) VALUES (1, '性别', 'gender', '');

-- 数据字典项表
CREATE TABLE `sys_dict_items` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dict_id` bigint NULL COMMENT '字典Id',
  `label` varchar(64) NOT NULL COMMENT '标签',
  `value` varchar(255) NOT NULL COMMENT '值',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_dict_item_dict_id`(`dict_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据字典项表';

INSERT INTO `sys_dict_items`(`id`, `dict_id`, `label`, `value`, `sort`) VALUES (1, 1, '男', '1', 1);
INSERT INTO `sys_dict_items`(`id`, `dict_id`, `label`, `value`, `sort`) VALUES (2, 1, '女', '2', 2);