

DROP TABLE IF EXISTS `auth_resource`;
CREATE TABLE `auth_resource` (
  `seq_uuid` varchar(36) NOT NULL DEFAULT '',
  `seq_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` varchar(32) NOT NULL COMMENT '类型：菜单-menu、按钮-button',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '名称',
  `url` varchar(640) DEFAULT '' COMMENT '链接地址',
  `parent_seq_id` int(11) NOT NULL COMMENT '父编号',
  `level` int(11) DEFAULT NULL COMMENT '层级',
  `order_no` varchar(10) DEFAULT '1' COMMENT '序号',
  `treepath` varchar(256) DEFAULT NULL COMMENT '树形路径，用/分隔',
  `apply_center_seq_id` int(11) NOT NULL COMMENT '应用中心ID',
  `is_allot` varchar(4) NOT NULL DEFAULT 'Y' COMMENT '是否可分配，Y-可分配  N-不可分配',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `status` varchar(10) NOT NULL DEFAULT '1' COMMENT '状态：有效-1、无效-4',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`seq_id`),
  KEY `idx_auth_resource_01` (`apply_center_seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源信息表';


DROP TABLE IF EXISTS `auth_resource_service`;
CREATE TABLE `auth_resource_service` (
  `seq_uuid` varchar(36) NOT NULL,
  `seq_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type_seq_id` int(11) NOT NULL COMMENT '资源类型ID',
  `service_seq_id` int(11) NOT NULL COMMENT '服务ID',
  `apply_center_seq_id` int(11) NOT NULL COMMENT '应用中心ID',
  `status` varchar(10) NOT NULL DEFAULT '1' COMMENT '状态：有效-1、无效-4',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`seq_id`),
  KEY `idx_auth_resource_service_01` (`type_seq_id`),
  KEY `idx_auth_resource_service_02` (`service_seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单类型服务信息表';


DROP TABLE IF EXISTS `auth_service`;
CREATE TABLE `auth_service` (
  `seq_uuid` varchar(36) NOT NULL DEFAULT '',
  `seq_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '名称',
  `url` varchar(640) DEFAULT '' COMMENT '链接地址',
  `apply_center_seq_id` int(11) NOT NULL COMMENT '应用中心ID',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `status` varchar(10) NOT NULL DEFAULT '1' COMMENT '状态：有效-1、无效-4',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`seq_id`),
  KEY `idx_auth_service_01` (`apply_center_seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务信息表';


DROP TABLE IF EXISTS `auth_operator`;
CREATE TABLE `auth_operator` (
  `seq_uuid` varchar(36) NOT NULL,
  `seq_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `operate_name` varchar(32) NOT NULL COMMENT '账户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `true_name` varchar(16) NOT NULL COMMENT '真实姓名',
  `email` varchar(64) NOT NULL DEFAULT '' COMMENT '邮箱',
  `mobile` varchar(16) NOT NULL COMMENT '联系电话',
  `branch_seq_id` int(11) DEFAULT NULL COMMENT '所属组织机构',
  `opt_type` varchar(16) NOT NULL DEFAULT 'normal' COMMENT '类型：管理员-admin、普通用户-normal ',
  `apply_center_seq_id` int(11) DEFAULT NULL COMMENT '应用中心ID',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `status` varchar(10) NOT NULL DEFAULT '1' COMMENT '状态：有效-1、无效-4',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`seq_id`),
  KEY `idx_auth_operator_01` (`branch_seq_id`),
  KEY `idx_auth_operator_02` (`apply_center_seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作账户信息表';


DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `seq_uuid` varchar(36) NOT NULL,
  `seq_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `apply_center_seq_id` int(11) NOT NULL COMMENT '应用中心ID',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `status` varchar(10) NOT NULL DEFAULT '1' COMMENT '状态：有效-1、无效-4',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`seq_id`),
  KEY `idx_auth_role_01` (`apply_center_seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色信息表';


DROP TABLE IF EXISTS `auth_operator_auth`;
CREATE TABLE `auth_operator_auth` (
  `seq_uuid` varchar(36) NOT NULL,
  `seq_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `operator_seq_id` int(11) NOT NULL COMMENT '账户ID',
  `branch_seq_id` int(11) DEFAULT NULL COMMENT '组织机构ID',
  `role_seq_id` int(11) NOT NULL COMMENT '角色ID',
  `apply_center_seq_id` int(11) NOT NULL COMMENT '应用中心ID',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `status` varchar(10) NOT NULL DEFAULT '1' COMMENT '状态：有效-1、无效-4',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`seq_id`),
  KEY `idx_auth_operator_auth_01` (`operator_seq_id`),
  KEY `idx_auth_operator_auth_02` (`apply_center_seq_id`),
  KEY `idx_auth_operator_auth_03` (`branch_seq_id`),
  KEY `idx_auth_operator_auth_04` (`role_seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作账户权限信息表';


DROP TABLE IF EXISTS `auth_role_resource`;
CREATE TABLE `auth_role_resource` (
  `seq_uuid` varchar(36) NOT NULL,
  `seq_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `apply_center_seq_id` int(11) NOT NULL COMMENT '应用系统ID',
  `role_seq_id` int(11) NOT NULL COMMENT '角色ID',
  `resource_seq_id` int(11) NOT NULL COMMENT '资源ID',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `status` varchar(10) NOT NULL DEFAULT '1' COMMENT '状态：有效-1、无效-4',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`seq_id`),
  KEY `idx_auth_role_resource_01` (`apply_center_seq_id`),
  KEY `idx_auth_role_resource_02` (`role_seq_id`),
  KEY `idx_auth_role_resource_03` (`resource_seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源信息表';


DROP TABLE IF EXISTS `auth_branch`;
CREATE TABLE `auth_branch` (
  `seq_uuid` varchar(36) NOT NULL,
  `seq_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `abbr_name` varchar(64) NOT NULL COMMENT '简称',
  `parent_seq_id` int(11) NOT NULL COMMENT '父编号',
  `level` int(11) DEFAULT NULL COMMENT '层级',
  `order_no` varchar(10) DEFAULT '1' COMMENT '序号',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `treepath` varchar(255) DEFAULT NULL COMMENT '树形路径，用/分隔',
  `apply_center_seq_id` int(11) COMMENT '应用中心ID',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `status` varchar(10) NOT NULL DEFAULT '1' COMMENT '状态：有效-1、无效-4',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`seq_id`),
  KEY `idx_auth_branch_01` (`apply_center_seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构信息表';


DROP TABLE IF EXISTS `sys_datalibrary`;
CREATE TABLE `sys_datalibrary` (
  `seq_uuid` varchar(36) NOT NULL,
  `seq_id`      int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `sys_id`      varchar(32) NOT NULL DEFAULT '' COMMENT '系统编码',
  `channel_id`  varchar(32) NOT NULL DEFAULT '' COMMENT '',
  `type`        varchar(32) NOT NULL COMMENT '' COMMENT '类型：ptype参数分类 param参数',
  `parent_code` varchar(64) DEFAULT NULL COMMENT '',
  `code`        varchar(64) NOT NULL COMMENT '编码',
  `name`        varchar(64) NOT NULL COMMENT '名称',
  `l_path`      varchar(200) DEFAULT NULL,
  `show_order`  int(10) DEFAULT 1 COMMENT '顺序',
  `apply_center_seq_id` int(11) NOT NULL COMMENT '应用中心ID',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `status` varchar(10) NOT NULL DEFAULT '1' COMMENT '状态：有效-1、无效-4',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`seq_id`),
  KEY `idx_sys_datalibrary_01` (`sys_id`),
  KEY `idx_sys_datalibrary_02` (`type`),
  KEY `idx_sys_datalibrary_03` (`parent_code`),
  KEY `idx_sys_datalibrary_04` (`code`),
  KEY `idx_sys_datalibrary_05` (`l_path`),
  KEY `idx_sys_datalibrary_06` (`apply_center_seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统数据字典信息';


DROP TABLE IF EXISTS `sys_apply_center`;
CREATE TABLE `sys_apply_center` (
  `seq_uuid` varchar(36) NOT NULL,
  `seq_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `branch_seq_id` int(11) DEFAULT NULL COMMENT '组织机构ID',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `status` varchar(10) NOT NULL DEFAULT '1' COMMENT '状态：有效-1、无效-4',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用中心信息表';

DROP TABLE IF EXISTS `auth_branch_apply`;
CREATE TABLE `auth_branch_apply` (
  `seq_uuid` varchar(36) NOT NULL,
  `seq_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `branch_seq_id` int(11) NOT NULL COMMENT '组织机构ID',
  `apply_center_seq_id` int(11) NOT NULL COMMENT '应用中心ID',
  `status` varchar(10) NOT NULL DEFAULT '1' COMMENT '状态：有效-1、无效-4',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`seq_id`),
  KEY `idx_auth_branch_apply_01` (`branch_seq_id`),
  KEY `idx_auth_branch_apply_02` (`apply_center_seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构应用中心信息表';

DROP TABLE IF EXISTS `sys_sensitiveWords_library`;
CREATE TABLE `sys_sensitiveWords_library` (
  `seq_uuid` varchar(36) NOT NULL,
  `sw_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '敏感词编号',
  `words` varchar(100) NOT NULL DEFAULT '' COMMENT '敏感词',
  `language` varchar(32) NOT NULL DEFAULT 'ZH_SIMP' COMMENT '语言：ZH_SIMP中文简体、维吾尔语ZH_UEY',
  `is_show` varchar(4) DEFAULT 'Y' COMMENT '显示状态：Y-显示、N-不显示',
  `status` varchar(4) DEFAULT '1' COMMENT '状态：1有效、4无效',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `use_platform` varchar(32) NOT NULL DEFAULT 'PHJF' COMMENT '使用平台：PHJF普惠金服平台',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` varchar(11) NOT NULL DEFAULT '' COMMENT '创建人',
  `modify_opt` varchar(11) NOT NULL DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`sw_id`),
  KEY `idx_sys_sensitiveWords_library_01` (`language`),
  KEY `idx_sys_sensitiveWords_library_02` (`words`),
  KEY `idx_sys_sensitiveWords_library_03` (`use_platform`),
  KEY `idx_sys_sensitiveWords_library_04` (`is_show`),
  KEY `idx_sys_sensitiveWords_library_05` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统敏感词库';

