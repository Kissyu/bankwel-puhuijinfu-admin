DROP TABLE IF EXISTS `phjf_app_version_info`;
CREATE TABLE `phjf_app_version_info` (
  `seq_uuid` varchar(36) NOT NULL,
  `avi_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `package_name` varchar(255) NOT NULL DEFAULT '' COMMENT '包名',
  `app_version` varchar(32) NOT NULL DEFAULT '' COMMENT '版本,如：1.1',
  `is_update` varchar(4) NOT NULL DEFAULT '0' COMMENT '是否强制更新：Y-更新；N-不更新',
  `download_url` varchar(255) NOT NULL DEFAULT '' COMMENT 'HTTP下载地址',
  `change_content` text COMMENT '更新内容',
  `channel` varchar(32) NOT NULL DEFAULT '' COMMENT '渠道',
  `is_show` varchar(4) NOT NULL DEFAULT '1' COMMENT '显示状态：Y-显示；N-不显示',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态：1-待发布  2-已发布  3-已下架',
  `publish_date` date NOT NULL DEFAULT NULL COMMENT '发布日期',
  `under_date` date NOT NULL DEFAULT NULL COMMENT '下架日期',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  PRIMARY KEY (`avi_id`),
  KEY `idx_phjf_app_version_info_01` (`package_name`),
  KEY `idx_phjf_app_version_info_02` (`app_version`),
  KEY `idx_phjf_app_version_info_03` (`is_show`),
  KEY `idx_phjf_app_version_info_04` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='APP版本信息表';


DROP TABLE IF EXISTS `phjf_bank_deposit_info`;
CREATE TABLE `phjf_bank_deposit_info` (
  `seq_uuid` varchar(36) NOT NULL,
  `bdi_id` int(11) NOT NULL AUTO_INCREMENT,
  `bd_code` varchar(32) NOT NULL DEFAULT '' COMMENT '储蓄产品内部编码',
  `language` varchar(50) NOT NULL DEFAULT 'ZH_SIMP' COMMENT '语言：ZH_SIMP中文简体、维吾尔ZH_UEY',
  `bank_code` varchar(32) NOT NULL DEFAULT '' COMMENT '银行编码',
  `term_type` int(4) NOT NULL DEFAULT '0' COMMENT '类型：1 定期',
  `term` varchar(32) NOT NULL DEFAULT '' COMMENT '期限：10-3个月、20-6个月、30-1年、40-2年、50-3年、60-5年',
  `rate` decimal(11,2) DEFAULT '0.00' COMMENT '利率：年化',
  `upper_limit` decimal(11,2) DEFAULT '0.00' COMMENT '存款上限',
  `lower_limit` decimal(11,2) DEFAULT '0.00' COMMENT '存款下限',
  `currency` varchar(32) NOT NULL DEFAULT '' COMMENT '币种（CNY人民币）',
  `is_redeem` int(4) NOT NULL DEFAULT '0' COMMENT '是否可赎回：1是、0否',
  `is_top` int(4) NOT NULL DEFAULT '0' COMMENT '是否置顶：1是、0否',
  `is_recom` int(4) NOT NULL DEFAULT '0' COMMENT '是否推荐：1是、0否',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_show` int(4) NOT NULL DEFAULT '1' COMMENT '显示状态：1显示；0不显示',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态：1待发布  2已发布  3已下架',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  PRIMARY KEY (`bd_id`),
  KEY `idx_phjf_bank_deposit_info_01` (`bd_code`),
  KEY `idx_phjf_bank_deposit_info_02` (`language`),
  KEY `idx_phjf_bank_deposit_info_03` (`is_top`),
  KEY `idx_phjf_bank_deposit_info_04` (`is_recom`),
  KEY `idx_phjf_bank_deposit_info_05` (`is_show`),
  KEY `idx_phjf_bank_deposit_info_06` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='银行存款产品信息表';


DROP TABLE IF EXISTS `phjf_bank_type`;
CREATE TABLE `phjf_bank_type` (
  `seq_uuid` varchar(36) NOT NULL,
  `bt_id` int(11) NOT NULL AUTO_INCREMENT,
  `bt_code` varchar(32) NOT NULL DEFAULT '' COMMENT '内部编码',
  `language` varchar(32) NOT NULL DEFAULT 'ZH_SIMP' COMMENT '语言：ZH_SIMP中文简体、维吾尔语ZH_UEY',
  `logo` varchar(500) NOT NULL DEFAULT '' COMMENT 'LOGO地址',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '类型名称',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_show` int(4) NOT NULL DEFAULT '1' COMMENT '显示状态：1显示；0不显示',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态：1-有效  4-无效',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  PRIMARY KEY (`bt_id`),
  KEY `idx_phjf_bank_type_01` (`bt_code`),
  KEY `idx_phjf_bank_type_02` (`language`),
  KEY `idx_phjf_bank_type_03` (`is_show`),
  KEY `idx_phjf_bank_type_04` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='银行类型信息表';


DROP TABLE IF EXISTS `phjf_bank_info`;
CREATE TABLE `phjf_bank_info` (
  `seq_uuid` varchar(36) NOT NULL,
  `bi_id` int(11) NOT NULL AUTO_INCREMENT,
  `bank_code` varchar(32) NOT NULL DEFAULT '' COMMENT '银行代码',
  `bt_code` varchar(32) NOT NULL DEFAULT '' COMMENT '所属银行类型',
  `language` varchar(50) NOT NULL DEFAULT 'ZH_SIMP' COMMENT '语言：ZH_SIMP中文简体、维吾尔ZH_UEY',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '银行机构名称',
  `parent_bank_code` varchar(255) NOT NULL DEFAULT '0' COMMENT '上级银行代码',
  `bank_path` varchar(255) NOT NULL DEFAULT '0' COMMENT '银行层级路径（用-分隔）',
  `contact` varchar(255) NOT NULL DEFAULT '' COMMENT '联系人',
  `address` varchar(255) NOT NULL DEFAULT '' COMMENT '地址',
  `email` varchar(255) NOT NULL DEFAULT '' COMMENT '邮箱',
  `telephone` varchar(255) NOT NULL DEFAULT '' COMMENT '固定电话',
  `mobilephone` varchar(255) NOT NULL DEFAULT '' COMMENT '移动电话',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `app_recom_code` varchar(32) NOT NULL DEFAULT '' COMMENT '推广码',
  `is_show` int(4) NOT NULL DEFAULT '1' COMMENT '显示状态：1显示；0不显示',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态：1-有效  4-无效',
  `start_date` date NOT NULL DEFAULT NULL COMMENT '开始合作日期',
  `end_date` date NOT NULL DEFAULT NULL COMMENT '终止合作日期',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  PRIMARY KEY (`bank_id`),
  KEY `idx_phjf_bank_info_01` (`bt_code`),
  KEY `idx_phjf_bank_info_02` (`bank_code`),
  KEY `idx_phjf_bank_info_03` (`language`),
  KEY `idx_phjf_bank_info_04` (`parent_bank_code`),
  KEY `idx_phjf_bank_info_05` (`app_recom_code`),
  KEY `idx_phjf_bank_info_06` (`is_show`),
  KEY `idx_phjf_bank_info_07` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='银行机构信息表';



DROP TABLE IF EXISTS `phjf_financial_product`;
CREATE TABLE `phjf_financial_product` (
  `seq_uuid` varchar(36) NOT NULL,
  `fp_id` int(11) NOT NULL AUTO_INCREMENT,
  `fp_code` varchar(32) NOT NULL DEFAULT '' COMMENT '理财产品内部编码',
  `language` varchar(32) NOT NULL DEFAULT 'ZH_SIMP' COMMENT '语言：ZH_SIMP中文简体、维吾尔语ZH_UEY',
  `fp_type` varchar(32) NOT NULL DEFAULT 'bank' COMMENT '理财产品类型：bank银行理财产品',
  `bt_code` varchar(32) NOT NULL DEFAULT '' COMMENT '产品所属银行',
  `title` varchar(32) NOT NULL DEFAULT '' COMMENT '理财产品名称',
  `fin_type` varchar(32) NOT NULL DEFAULT '' COMMENT '理财产品类型：（待定）',
  `inve_yrate` varchar(32) NOT NULL DEFAULT '' COMMENT '年化收益',
  `inve_term` varchar(32) NOT NULL DEFAULT '' COMMENT '期限（单位：月） 3-3个月、6-6个月、12-12个月、24-2年、36-3年、60-5年、120-10年',
  `amount_min` int(10) NOT NULL DEFAULT '0' COMMENT '起售金额',
  `raise_start_date` date NOT NULL DEFAULT NULL COMMENT '募集开始日期',
  `raise_end_date` date NOT NULL DEFAULT NULL COMMENT '募集结束日期',
  `value_days` int(2) NOT NULL DEFAULT '0' COMMENT '起息工作日',
  `currency` varchar(32) NOT NULL DEFAULT '' COMMENT '币种（CNY人民币）',
  `fin_code` varchar(32) NOT NULL DEFAULT '' COMMENT '第三方产品代码',
  `is_cp` int(4) NOT NULL DEFAULT '0' COMMENT '是否保本：1保本 2不保本',
  `risk_level` int(4) NOT NULL COMMENT '风险等级：1一级 2二级 3三级...',
  `balance` varchar(32) NOT NULL DEFAULT '' COMMENT '剩余额度',
  `remark` varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
  `share_count` int(11) NOT NULL DEFAULT '0' COMMENT '分享次数',
  `click_count` int(11) NOT NULL DEFAULT '0' COMMENT '点击次数',
  `buy_count` int(11) NOT NULL DEFAULT '0' COMMENT '购买次数',
  `is_recom` int(4) NOT NULL DEFAULT '0' COMMENT '是否推荐：1-是  0-否',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_show` int(4) NOT NULL DEFAULT '1' COMMENT '显示状态：1显示；0不显示',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态：1-待发布  2-已发布  -3已下架',
  `publish_date` date NOT NULL DEFAULT NULL COMMENT '起售日期',
  `under_date` date NOT NULL DEFAULT NULL COMMENT '下架日期',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  PRIMARY KEY (`fp_id`),
  KEY `idx_phjf_financial_product_01` (`fp_code`),
  KEY `idx_phjf_financial_product_02` (`language`),
  KEY `idx_phjf_financial_product_03` (`fp_type`),
  KEY `idx_phjf_financial_product_04` (`bt_code`),
  KEY `idx_phjf_financial_product_05` (`is_show`),
  KEY `idx_phjf_financial_product_06` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='理财产品信息表';


DROP TABLE IF EXISTS `phjf_main_plate`;
CREATE TABLE `phjf_main_plate` (
  `seq_uuid` varchar(36) NOT NULL,
  `mp_id` int(11) NOT NULL AUTO_INCREMENT,
  `plate_code` varchar(32) NOT NULL DEFAULT '' COMMENT '业务板块内部编码',
  `language` varchar(32) NOT NULL DEFAULT 'ZH_SIMP' COMMENT '语言：ZH_SIMP中文简体、维吾尔语ZH_UEY',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '业务板块名称',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注信息',
  `order_num` int(4) NOT NULL DEFAULT '1' COMMENT '显示序号',
  `is_show` int(4) NOT NULL DEFAULT '1' COMMENT '显示状态：1显示；0不显示',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态：1-有效  4-无效',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  PRIMARY KEY (`plate_id`),
  KEY `idx_phjf_main_plate_01` (`plate_code`),
  KEY `idx_phjf_main_plate_02` (`language`),
  KEY `idx_phjf_main_plate_03` (`is_show`),
  KEY `idx_phjf_main_plate_04` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页业务板块';



DROP TABLE IF EXISTS `phjf_managepoint_optinfo`;
CREATE TABLE `phjf_managepoint_optinfo` (
  `seq_uuid` varchar(36) NOT NULL,
  `mo_id` int(11) NOT NULL AUTO_INCREMENT,
  `mp_code` varchar(32) NOT NULL DEFAULT '0' COMMENT '办理点内部编码',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '姓名',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '账户名',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '登录密码(MD5后)',
  `telephone` varchar(255) NOT NULL DEFAULT '' COMMENT '固定电话',
  `mobilephone` varchar(255) NOT NULL DEFAULT '' COMMENT '移动电话',
  `token` varchar(255) NOT NULL DEFAULT '' COMMENT '鉴权TOKEN',
  `is_show` int(4) NOT NULL DEFAULT '1' COMMENT '显示状态：1显示；0不显示',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态：1-有效  4-无效',
  `frozen_time` date NOT NULL DEFAULT NULL COMMENT '账户冻结日期',
  `token_exp_time` datetime DEFAULT NULL COMMENT 'token有效期',
  `last_login_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  PRIMARY KEY (`opt_id`),
  KEY `idx_phjf_managepoint_optinfo_01` (`mp_code`),
  KEY `idx_phjf_managepoint_optinfo_02` (`is_show`),
  KEY `idx_phjf_managepoint_optinfo_03` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='办理点业务操作人员信息表';


DROP TABLE IF EXISTS `phjf_message_centre`;
CREATE TABLE `phjf_message_centre` (
  `seq_uuid` varchar(36) NOT NULL,
  `mc_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '接收用户ID',
  `title` varchar(32) NOT NULL DEFAULT '' COMMENT '名称',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '内容',
  `sender` varchar(32) NOT NULL DEFAULT '' COMMENT '发送方',
  `is_show` int(4) NOT NULL DEFAULT '1' COMMENT '显示状态：1-显示  0-不显示',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态：1-有效  4-无效',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  PRIMARY KEY (`mc_id`),
  KEY `idx_phjf_message_centre_01` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息中心信息表';


DROP TABLE IF EXISTS `phjf_mobile_recharge_product`;
CREATE TABLE `phjf_mobile_recharge_product` (
  `seq_uuid` varchar(36) NOT NULL,
  `mrp_id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` int(4) NOT NULL DEFAULT '0' COMMENT '金额,单位元',
  `pay_amount` decimal(6,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态：1-有效 4-无效',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  PRIMARY KEY (`mr_id`),
  KEY `idx_phjf_mobile_recharg_product_01` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手机充值产品信息表';





DROP TABLE IF EXISTS `phjf_news_plate`;
CREATE TABLE `phjf_news_plate` (
  `seq_uuid` varchar(36) NOT NULL,
  `np_id` int(11) NOT NULL AUTO_INCREMENT,
  `plate_code` varchar(32) NOT NULL DEFAULT '' COMMENT '内部编码',
  `language` varchar(32) NOT NULL DEFAULT 'ZH_SIMP' COMMENT '语言：ZH_SIMP中文简体、维吾尔语ZH_UEY',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '名称：政策资讯、生活资讯',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_show` int(4) NOT NULL DEFAULT '1' COMMENT '显示状态：1显示；0不显示',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态：1-有效  4-无效',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  PRIMARY KEY (`plate_id`),
  KEY `idx_phjf_news_plate_01` (`plate_code`),
  KEY `idx_phjf_news_plate_02` (`is_show`),
  KEY `idx_phjf_news_plate_03` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻板块信息表';


DROP TABLE IF EXISTS `phjf_sms_log`;
CREATE TABLE `phjf_sms_log` (
  `seq_uuid` varchar(36) NOT NULL,
  `sl_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(32) NOT NULL DEFAULT '' COMMENT '短信类型（reg注册；forgetPass忘记密码; ）',
  `mobilephone` varchar(11) NOT NULL DEFAULT '' COMMENT '接收电话号码',
  `content` varchar(500) NOT NULL DEFAULT '' COMMENT '短信内容',
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '验证码',
  `exp_time` datetime NOT NULL DEFAULT NULL COMMENT '失效时间',
  `result_type` int(4) NOT NULL DEFAULT '0' COMMENT '回执状态：1成功接收  0未回执',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态：1-有效  4-无效',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  PRIMARY KEY (`log_id`),
  KEY `idx_phjf_sms_log_01` (`type`),
  KEY `idx_phjf_sms_log_02` (`mobilephone`),
  KEY `idx_phjf_sms_log_03` (`status`),
  KEY `idx_phjf_sms_log_04` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=284 DEFAULT CHARSET=utf8 COMMENT='短信发送历史表';



DROP TABLE IF EXISTS `phjf_user_two_account`;
CREATE TABLE `phjf_user_two_account` (
  `seq_uuid` varchar(36) NOT NULL,
  `uta_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `account_holder` varchar(255) NOT NULL DEFAULT '' COMMENT '开户人',
  `one_account_no` varchar(255) NOT NULL DEFAULT '' COMMENT '一类户卡号',
  `one_bt_code` varchar(32) NOT NULL DEFAULT '' COMMENT '一类户开户行',
  `two_account_no` varchar(255) NOT NULL DEFAULT '' COMMENT '二类户卡号',
  `two_bt_code` varchar(32) NOT NULL DEFAULT '' COMMENT '二类户开户行',
  `Reserve_mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '预留手机号',
  `apply_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `callback_time` datetime NOT NULL DEFAULT NULL COMMENT '回调时间',
  `is_show` int(4) NOT NULL DEFAULT '1' COMMENT '显示状态：1显示；0不显示',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态：1成功 4失败',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  PRIMARY KEY (`ubc_id`),
  KEY `idx_phjf_user_bank_01` (`user_id`),
  KEY `idx_phjf_user_bank_02` (`is_show`),
  KEY `idx_phjf_user_bank_03` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户二类户信息表';


DROP TABLE IF EXISTS `phjf_user_bankcard_appointment`;
CREATE TABLE `phjf_user_bankcard_appointment` (
  `seq_uuid` varchar(36) NOT NULL,
  `uba_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `mp_code` varchar(32) NOT NULL DEFAULT '' COMMENT '办理点ID',
  `bt_code` varchar(32) NOT NULL DEFAULT '' COMMENT '开户行',
  `bank_code` varchar(32) DEFAULT '' COMMENT '银行编码',
  `true_name` varchar(32) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `id_num` varchar(32) NOT NULL DEFAULT '' COMMENT '身份证号',
  `mobilephone` varchar(32) NOT NULL DEFAULT '' COMMENT '手机号',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态：1已提交  2银行处理中  3已预约  4已退回  5已完成  6已取消',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `deal_time` datetime NOT NULL DEFAULT NULL COMMENT '处理时间',
  `bank_deal_time` datetime NOT NULL DEFAULT NULL COMMENT '银行处理时间',
  `am_time` datetime NOT NULL DEFAULT NULL COMMENT '预约时间',
  `return_time` datetime NOT NULL DEFAULT NULL COMMENT '退回时间',
  `finish_time` datetime NOT NULL DEFAULT NULL COMMENT '完成时间',
  `cancel_time` datetime NOT NULL DEFAULT NULL COMMENT '取消时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  PRIMARY KEY (`ba_id`),
  KEY `idx_phjf_user_bankcard_appointment_01` (`user_id`),
  KEY `idx_phjf_user_bankcard_appointment_02` (`mp_code`),
  KEY `idx_phjf_user_bankcard_appointment_03` (`bt_code`),
  KEY `idx_phjf_user_bankcard_appointment_04` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户银行预约开户信息表';


DROP TABLE IF EXISTS `phjf_user_feedback`;
CREATE TABLE `phjf_user_feedback` (
  `seq_uuid` varchar(36) NOT NULL,
  `uf_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `type` varchar(32) NOT NULL DEFAULT 'normal' COMMENT '反馈类型（预留字段）',
  `content` varchar(500) NOT NULL DEFAULT '' COMMENT '反馈信息',
  `recontent` varchar(500) NOT NULL DEFAULT '' COMMENT '回馈信息',
  `result` int(4) NOT NULL DEFAULT '0' COMMENT '回馈状态：1待处理 2已阅读 3已反馈',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态：1-有效  4-无效',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  PRIMARY KEY (`feedback_id`),
  KEY `idx_phjf_user_feedback_01` (`type`),
  KEY `idx_phjf_user_feedback_02` (`user_id`),
  KEY `idx_phjf_user_feedback_03` (`result`),
  KEY `idx_phjf_user_feedback_04` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='意见反馈信息表';


DROP TABLE IF EXISTS `phjf_user_info`;
CREATE TABLE `phjf_user_info` (
  `seq_uuid` varchar(36) NOT NULL,
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '账户名（按规则生成，但登录时可根据手机号/邮箱/账号名登录）',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '登录密码(MD5后)',
  `hand_password` varchar(255) DEFAULT '' COMMENT '手势密码(MD5后)',
  `head_img` varchar(255) DEFAULT '' COMMENT '头像',
  `mobilephone` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `true_name` varchar(255) DEFAULT '' COMMENT '姓名',
  `id_type` varchar(255) DEFAULT '' COMMENT '证件类型: 1:身份证、9:其他',
  `id_num` varchar(255) DEFAULT '' COMMENT '证件号',
  `nickname` varchar(255) DEFAULT '' COMMENT '昵称',
  `gender` varchar(32) DEFAULT '' COMMENT '性别：male男、female女、none不限',
  `address` varchar(255) DEFAULT '' COMMENT '常住地',
  `language` varchar(32) NOT NULL DEFAULT 'ZH_SIMP' COMMENT '用户当前语言：ZH_SIMP中文简体、维吾尔语ZH_UEY',
  `pay_qr_code` varchar(255) DEFAULT '' COMMENT '付款二维码',
  `receive_qr_code` varchar(255) DEFAULT '' COMMENT '收款二维码',
  `token` varchar(255) NOT NULL DEFAULT '' COMMENT '鉴权TOKEN',
  `is_show` int(4) DEFAULT '1' COMMENT '显示状态：1显示；0不显示',
  `status` int(4) DEFAULT '1' COMMENT '状态：1-有效  4-无效',
  `device_num` varchar(100) NOT NULL DEFAULT '0' COMMENT '设备号',
  `reg_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `frozen_time` datetime DEFAULT NULL COMMENT '账户冻结时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `token_exp_time` datetime DEFAULT NULL COMMENT '失效时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` varchar(11) NOT NULL DEFAULT '' COMMENT '创建人',
  `modify_opt` varchar(11) DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`user_id`),
  KEY `idx_phjf_user_info_01` (`user_name`),
  KEY `idx_phjf_user_info_02` (`user_type`),
  KEY `idx_phjf_user_info_03` (`mobilephone`),
  KEY `idx_phjf_user_info_04` (`token`),
  KEY `idx_phjf_user_info_05` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';


DROP TABLE IF EXISTS `phjf_user_lifePay`;
CREATE TABLE `phjf_user_lifePay` (
  `seq_uuid` varchar(36) NOT NULL,
  `ul_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `recharge_mobilephone` varchar(32) NOT NULL DEFAULT '' COMMENT '充值手机号',
  `business_id` int(11) NOT NULL DEFAULT '0' COMMENT '业务ID 手机充值产品ID',
  `mi_id` int(11) NOT NULL DEFAULT '0' COMMENT '机构ID',
  `customer_no` varchar(100) NOT NULL DEFAULT '' COMMENT '客户编号',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '金额,单位元',
  `pay_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `type` int(4) NOT NULL DEFAULT '0' COMMENT '类型 1-手机话费  2-电费  3-水费  4-燃气费  5-有线电视  6-固话宽带',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态：1-处理中 2-成功 3-失败',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  PRIMARY KEY (`ul_id`),
  KEY `idx_phjf_user_lifePay_01` (`user_id`),
  KEY `idx_phjf_user_lifePay_02` (`business_id`),
  KEY `idx_phjf_user_lifePay_03` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户生活缴费信息表';


drop table sys_interface_log ;
create table sys_interface_log(
  `seq_uuid` varchar(36) NOT NULL,
	`fromSystem`  varchar(20) NOT NULL COMMENT '来源系统标识。',
  `toSystem`    varchar(20) NOT NULL COMMENT '响应系统标识。',
	`bus_type`    varchar(20) NOT NULL COMMENT '交易类型。',
	`requestContent` text NULL DEFAULT '' COMMENT '交易消息',
	`requestParam` text NULL DEFAULT '' COMMENT '交易参数',
  `responseContent` text NULL DEFAULT '' COMMENT '反回消息',
  `responseCode` text NULL DEFAULT '' COMMENT '交易状态码',
	`sysError`		text NULL DEFAULT '' COMMENT '系统异常信息',
  `status`      int(4) NOT NULL COMMENT '状态 1待处理、2正在处理、3成功、4失败',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_opt` int(11) NOT NULL COMMENT '创建人',
  `modify_opt` int(11) NOT NULL DEFAULT 0 COMMENT '修改人',
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统交易记录表';


DROP TABLE IF EXISTS `phjf_cms_banner`;
CREATE TABLE `phjf_cms_banner` (
  `banner_id` int(32) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT '' COMMENT 'code',
  `language` varchar(50) DEFAULT 'ZH_SIMP' COMMENT '语言：ZH_SIMP中文简体、维吾尔ZH_UEY',
  `img_path` varchar(1000) DEFAULT '' COMMENT '图片地址',
  `contents` varchar(2000) DEFAULT '' COMMENT '文字叙述',
  `url` varchar(2000) DEFAULT '' COMMENT '链接地址',
  `remarks` varchar(500) DEFAULT '' COMMENT '备注',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `add_by` varchar(50) DEFAULT '' COMMENT '添加人',
  `update_by` varchar(50) DEFAULT '' COMMENT '修改人',
  `is_show` int(1) DEFAULT '1' COMMENT '是否显示（0否1是）',
  `status` int(1) DEFAULT '1' COMMENT '状态（0无效1有效）',
  `orders` int(11) DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`banner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='官网首页banner';

-- ----------------------------
--  Table structure for `phjf_cms_company_culture`
-- ----------------------------
DROP TABLE IF EXISTS `phjf_cms_company_culture`;
CREATE TABLE `phjf_cms_company_culture` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id标识',
  `code` varchar(64) DEFAULT NULL,
  `language` varchar(32) DEFAULT NULL COMMENT '语言：ZH_SIMP中文简体、维吾尔语ZH_UEY',
  `title` varchar(128) DEFAULT '' COMMENT '标题',
  `description` varchar(1024) DEFAULT '' COMMENT '文字描述',
  `orders` int(11) DEFAULT '1' COMMENT '排序',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `status` smallint(6) DEFAULT '1' COMMENT '状态1：正常 0：删除',
  `is_show` smallint(6) DEFAULT '1' COMMENT '是否显示  1：显示 0：不显示',
  `nav_code` varchar(50) DEFAULT '' COMMENT '所属导航',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='官网公司文化';

-- ----------------------------
--  Table structure for `phjf_cms_contact_info`
-- ----------------------------
DROP TABLE IF EXISTS `phjf_cms_contact_info`;
CREATE TABLE `phjf_cms_contact_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id标识',
  `code` varchar(64) DEFAULT NULL,
  `language` varchar(32) DEFAULT NULL,
  `title` varchar(128) DEFAULT NULL COMMENT '标题',
  `mobile` varchar(15) DEFAULT NULL COMMENT '电话',
  `fix` varchar(15) DEFAULT NULL COMMENT '传真',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `address` varchar(128) DEFAULT NULL COMMENT '地址',
  `is_show` smallint(6) DEFAULT '1' COMMENT '是否显示：1：显示 0：不显示',
  `status` smallint(6) DEFAULT NULL COMMENT '状态 1：正常 0：删除',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `nav_code` varchar(50) DEFAULT NULL COMMENT '所属栏目',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `phjf_cms_content`
-- ----------------------------
DROP TABLE IF EXISTS `phjf_cms_content`;
CREATE TABLE `phjf_cms_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id标识',
  `code` varchar(64) DEFAULT '',
  `language` varchar(32) DEFAULT '' COMMENT '语言：ZH_SIMP中文简体、维吾尔语ZH_UEY',
  `title` varchar(32) DEFAULT '' COMMENT '标题',
  `pictures` varchar(256) DEFAULT NULL COMMENT '配图url（多张图片用英文逗号隔开）',
  `content` text COMMENT '文本内容',
  `status` smallint(6) DEFAULT '1' COMMENT '状态：1：正常 0：删除',
  `is_show` smallint(6) DEFAULT '1' COMMENT '是否显示 1：显示 0：不显示',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改改时间',
  `nav_code` varchar(50) DEFAULT '' COMMENT '所属导航',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `phjf_cms_manager`
-- ----------------------------
DROP TABLE IF EXISTS `phjf_cms_manager`;
CREATE TABLE `phjf_cms_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id标识',
  `code` varchar(64) DEFAULT NULL COMMENT '标识码',
  `language` varchar(32) DEFAULT NULL COMMENT '语言',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `position` varchar(128) DEFAULT NULL COMMENT '职位',
  `picture` varchar(128) DEFAULT NULL COMMENT '头像的url',
  `description` varchar(256) DEFAULT NULL COMMENT '介绍',
  `status` int(1) DEFAULT '1' COMMENT '状态1：正常 0：不显示',
  `is_show` int(1) DEFAULT '1' COMMENT '是否显示 1：显示 0：不显示',
  `orders` int(11) DEFAULT '0' COMMENT '排序',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `nav_code` varchar(50) DEFAULT '' COMMENT '所属导航',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='团队';

-- ----------------------------
--  Table structure for `phjf_cms_nav`
-- ----------------------------
DROP TABLE IF EXISTS `phjf_cms_nav`;
CREATE TABLE `phjf_cms_nav` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id标识',
  `name` varchar(64) DEFAULT NULL COMMENT '导航名称',
  `code` varchar(64) DEFAULT NULL COMMENT '编码标识',
  `language` varchar(255) DEFAULT NULL COMMENT '语言：ZH_SIMP中文简体、维吾尔ZH_UEY',
  `url` varchar(128) DEFAULT NULL COMMENT '访问路径',
  `pictures` varchar(256) DEFAULT NULL COMMENT '底图',
  `orders` int(11) DEFAULT '0' COMMENT '排序',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` smallint(6) DEFAULT '1' COMMENT '状态：1：正常，0：删除',
  `is_show` smallint(6) DEFAULT '1' COMMENT '是否显示：1：显示 ，0：不显示',
  `location` varchar(255) DEFAULT '1' COMMENT '所在位置1：左侧，2：顶部(可以多选)',
  `nav_code` varchar(255) DEFAULT '' COMMENT '所属导航code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `phjf_cms_program`
-- ----------------------------
DROP TABLE IF EXISTS `phjf_cms_program`;
CREATE TABLE `phjf_cms_program` (
  `program_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id标识',
  `nav_code` varchar(64) DEFAULT NULL COMMENT '编码标识',
  `code` varchar(64) DEFAULT NULL COMMENT '编码标识',
  `language` varchar(255) DEFAULT 'ZH_SIMP' COMMENT '语言：ZH_SIMP中文简体、维吾尔ZH_UEY',
  `name` varchar(64) DEFAULT NULL COMMENT '导航名称',
  `logo` varchar(255) DEFAULT '' COMMENT '图片class名称',
  `url` varchar(2000) DEFAULT '' COMMENT 'url',
  `orders` int(11) DEFAULT '0' COMMENT '排序',
  `status` int(6) DEFAULT '1' COMMENT '状态：1：正常，0：删除',
  `is_show` int(6) DEFAULT '1' COMMENT '是否显示：1：显示 ，0：不显示',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `add_by` varchar(64) DEFAULT NULL COMMENT '导航名称',
  `update_by` varchar(255) DEFAULT NULL COMMENT '图标地址',
  PRIMARY KEY (`program_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='官网栏目';

-- ----------------------------
--  Table structure for `phjf_cms_vocational_work`
-- ----------------------------
DROP TABLE IF EXISTS `phjf_cms_vocational_work`;
CREATE TABLE `phjf_cms_vocational_work` (
  `work_id` int(255) NOT NULL AUTO_INCREMENT,
  `program_code` varchar(255) DEFAULT '' COMMENT '栏目ID',
  `code` varchar(255) DEFAULT '' COMMENT 'code',
  `language` varchar(255) DEFAULT 'ZH_SIMP' COMMENT '语言：ZH_SIMP中文简体、维吾尔ZH_UEY',
  `name` varchar(1000) DEFAULT '' COMMENT '名称',
  `logo` varchar(2000) DEFAULT '' COMMENT '图片',
  `icon_class` varchar(2000) DEFAULT '' COMMENT '图片类名',
  `outline` varchar(2000) DEFAULT '' COMMENT '概要',
  `contents` varchar(2000) DEFAULT '' COMMENT '内容',
  `orders` int(11) DEFAULT '0' COMMENT '排序',
  `is_show` int(1) DEFAULT '1' COMMENT '是否显示（0否1是）',
  `status` int(1) DEFAULT '1' COMMENT '状态（0无效1有效）',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `add_by` varchar(50) DEFAULT '' COMMENT '新增人',
  `update_by` varchar(50) DEFAULT '' COMMENT '更新人',
  PRIMARY KEY (`work_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='官网业务模块';


INSERT INTO `bankwel_phjf_2017`.`phjf_user_loan_apply` (`seq_uuid`, `ulp_id`, `user_id`, `loan_code`, `true_name`, `id_type`, `id_num`, `mobilephone`, `loan_use_type`, `loan_amount`, `loan_use_term`, `remark`, `pass_time`, `is_show`, `status`, `create_time`, `modify_time`, `create_opt`, `modify_opt`) VALUES ('1', '1', '7', '1', '杨建龙', '1', '411082199210020660', '15837854747', '1', '2', '1', '', NULL, 'Y', '1', '2017-12-01 13:15:00', '2017-12-01 13:43:32', '', '');


INSERT INTO phjf_managepoint_bank (`seq_uuid`,`mp_code`,`bt_code`,`bank_code`,`is_open_account`)
SELECT UUID(), mp_code, 'BT11982', 'B20021','Y' from phjf_managepoint_info WHERE is_show = 'Y' and `status` = 1;

-- 添加是否确认的字典
INSERT INTO `sys_datalibrary` (`seq_uuid`, `seq_id`, `sys_id`, `type`, `parent_code`, `code`, `language`, `name`, `l_path`, `show_order`, `remark`, `create_time`, `modify_time`, `create_opt`, `modify_opt`)
 VALUES (UUID(), 8300, 'Phjf', 'ptype', '', 'sys_isConfirm', 'ZH_SIMP', '是否确认', NULL, 1, '', '2018-2-27 10:04:41', '2018-2-27 10:04:41', '', '');
INSERT INTO `sys_datalibrary` (`seq_uuid`, `seq_id`, `sys_id`, `type`, `parent_code`, `code`, `language`, `name`, `l_path`, `show_order`, `remark`, `create_time`, `modify_time`, `create_opt`, `modify_opt`)
 VALUES (UUID(), 8301, 'Phjf', 'param', 'sys_isConfirm', 'Y', 'ZH_SIMP', '已确认', NULL, 1, '', '2018-2-27 10:04:41', '2018-2-27 10:04:41', '', '');
INSERT INTO `sys_datalibrary` (`seq_uuid`, `seq_id`, `sys_id`, `type`, `parent_code`, `code`, `language`, `name`, `l_path`, `show_order`, `remark`, `create_time`, `modify_time`, `create_opt`, `modify_opt`)
 VALUES (UUID(), 8302, 'Phjf', 'param', 'sys_isConfirm', 'N', 'ZH_SIMP', '未确认', NULL, 1, '', '2018-2-27 10:04:41', '2018-2-27 10:04:41', '', '');

-- 添加是否确认的字段
ALTER TABLE `phjf_managepoint_info` ADD COLUMN `is_confirm` varchar(4) DEFAULT 'N' COMMENT '是否确认：Y-已确认、N-未确认';
ALTER TABLE `phjf_bank_atm` ADD COLUMN `is_confirm` varchar(4) DEFAULT 'N' COMMENT '是否确认：Y-已确认、N-未确认';
ALTER TABLE `phjf_bank_info` ADD COLUMN `is_confirm` varchar(4) DEFAULT 'N' COMMENT '是否确认：Y-已确认、N-未确认';

-- 添加第三方的编码的字段
ALTER TABLE `phjf_bank_info` ADD COLUMN `third_bank_code` varchar(32)  COMMENT '银行网点第三方编码';
ALTER TABLE `phjf_bank_atm` ADD COLUMN `third_atm_code` varchar(32)  COMMENT 'ATM第三方编码';
ALTER TABLE `phjf_bank_atm` MODIFY COLUMN `bank_code` varchar(32)  COMMENT '银行网点编码';

--添加系统编码字典
INSERT INTO `sys_datalibrary` (`seq_uuid`, `seq_id`, `sys_id`, `type`, `parent_code`, `code`, `language`, `name`, `show_order`, `remark`) VALUES (UUID(), '9900', 'Phjf', 'ptype', '', 'system_code', 'ZH_SIMP', '系统编码', 1, '');
INSERT INTO `sys_datalibrary` (`seq_uuid`, `seq_id`, `sys_id`, `type`, `parent_code`, `code`, `language`, `name`, `show_order`, `remark`) VALUES (UUID(), '9901', 'Phjf', 'param', 'system_code', 'phjf_mainclient', 'ZH_SIMP', '普惠金服用户端', 1, '');
INSERT INTO `sys_datalibrary` (`seq_uuid`, `seq_id`, `sys_id`, `type`, `parent_code`, `code`, `language`, `name`, `show_order`, `remark`) VALUES (UUID(), '9902', 'Phjf', 'param', 'system_code', 'phjf_merchclient', 'ZH_SIMP', '普惠金服用户端', 2, '');
INSERT INTO `sys_datalibrary` (`seq_uuid`, `seq_id`, `sys_id`, `type`, `parent_code`, `code`, `language`, `name`, `show_order`, `remark`) VALUES (UUID(), '9903', 'Phjf', 'param', 'system_code', 'PhjfAdmin', 'ZH_SIMP', '普惠金服后台内容管理系统', 2, '');
