DROP TABLE IF EXISTS `phjf_web_nav`;
CREATE TABLE `phjf_web_nav` (
  `seq_uuid`         varchar(36) NOT NULL,
  `nav_id`           int(11) NOT NULL AUTO_INCREMENT COMMENT 'id标识',
  `nav_name`         varchar(64) DEFAULT '' COMMENT '导航名称',
  `nav_code`         varchar(64) DEFAULT NULL COMMENT '导航编码',
  `language`         varchar(255) DEFAULT NULL COMMENT '语言：ZH_SIMP中文简体、维吾尔ZH_UEY',
  `parent_nav_code`  varchar(255) DEFAULT '' COMMENT '上级导航code',
  `nav_path`         varchar(255) DEFAULT '' COMMENT '导航层级路径（用-分隔）',
  `url`              varchar(128) DEFAULT '' COMMENT '访问路径',
  `orders`           int(11) DEFAULT '0' COMMENT '排序',
  `is_show`          varchar(4) DEFAULT 'Y' COMMENT '是否显示：Y：显示 ，N：不显示',
  `status`           varchar(4) DEFAULT '1' COMMENT '状态：1：有效，4：无效',
  `create_time`      datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time`      datetime DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP  COMMENT '修改时间',
  `create_user`      varchar(50) DEFAULT '' COMMENT '创建人',
  `modify_user`      varchar(50) DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`nav_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站导航';

DROP TABLE IF EXISTS `phjf_web_banner`;
CREATE TABLE `phjf_web_banner` (
  `seq_uuid`        varchar(36) NOT NULL,
  `banner_id`       int(32) NOT NULL AUTO_INCREMENT,
  `banner_code`     varchar(50) DEFAULT '' COMMENT 'code',
  `language`        varchar(50) DEFAULT '' COMMENT '语言：ZH_SIMP中文简体、维吾尔ZH_UEY',
  `img_url`        varchar(1000) DEFAULT '' COMMENT '图片地址',
  `contents`        varchar(2000) DEFAULT '' COMMENT '文字叙述',
  `url`             varchar(2000) DEFAULT '' COMMENT '链接地址',
  `orders`          int(11) DEFAULT '0' COMMENT '排序',
  `is_show`         varchar(4) DEFAULT 'Y' COMMENT '是否显示（N否Y是）',
  `status`          varchar(4) DEFAULT '1' COMMENT '状态（1无效4有效）',
  `create_time`      datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time`      datetime DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP  COMMENT '修改时间',
  `create_user`      varchar(50) DEFAULT '' COMMENT '创建人',
  `modify_user`      varchar(50) DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`banner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站首页banner';

DROP TABLE IF EXISTS `phjf_web_news_plate`;
CREATE TABLE `phjf_web_news_plate` (
  `seq_uuid`        varchar(36) NOT NULL,
  `np_id`           int(11 ) NOT NULL AUTO_INCREMENT,
  `np_code`         varchar(32) NOT NULL DEFAULT '' COMMENT '内部编码',
  `language`        varchar(32) NOT NULL DEFAULT 'ZH_SIMP' COMMENT '语言：ZH_SIMP中文简体、维吾尔语ZH_UEY',
  `np_name`         varchar(100) NOT NULL DEFAULT '' COMMENT '板块名称',
  `type`            varchar(32) NOT NULL COMMENT '模块（1-首页新闻、2-聚焦新闻、3-政策栏目、4-政务办事、5-便民服务）',
  `location`        varchar(32) NOT NULL COMMENT '展示位置（middle-中间、right-右侧、bottom-下方）',
  `nav_code`        varchar(32) DEFAULT '' COMMENT '所属导航',
  `order_num`       int(11) DEFAULT '0' COMMENT '排序号',
  `is_show`         varchar(4) DEFAULT 'Y' COMMENT '显示状态：Y-显示、n-不显示',
  `status`          varchar(4) DEFAULT '1' COMMENT '状态：1-有效、4-失效',
  `create_time`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_user`      varchar(11) DEFAULT '' COMMENT '创建人',
  `modify_user`      varchar(11) DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`np_id`),
  KEY `idx_phjf_news_plate_01` (`np_code`),
  KEY `idx_phjf_news_plate_02` (`language`),
  KEY `idx_phjf_news_plate_03` (`is_show`),
  KEY `idx_phjf_news_plate_04` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站新闻板块信息表';

DROP TABLE IF EXISTS `phjf_web_news_info`;
CREATE TABLE `phjf_web_news_info` (
  `seq_uuid`          varchar(36) NOT NULL,
  `ni_id`             int(11) NOT NULL AUTO_INCREMENT,
  `np_code`           varchar(32) NOT NULL DEFAULT '' COMMENT '板块编码',
  `ni_code`           varchar(32) NOT NULL DEFAULT '' COMMENT '内部编码',
  `language`          varchar(32) NOT NULL DEFAULT 'ZH_SIMP' COMMENT '语言：ZH_SIMP-中文简体、ZH_UEY-维吾尔语',
  `title`             varchar(255) NOT NULL DEFAULT '' COMMENT '标题',
  `logo`              varchar(255) DEFAULT '' COMMENT 'LOGO地址',
  `publish_date`      date DEFAULT NULL COMMENT '发表日期',
  `auto_publish_date` date DEFAULT NULL COMMENT '自动发布日期',
  `tags`              varchar(100) DEFAULT '' COMMENT '标签：热点、实用,用逗号(,)分隔',
  `source`            varchar(50) DEFAULT '' COMMENT '来源',
  `content`           text COMMENT '内容',
  `is_top`            varchar(4) DEFAULT 'N' COMMENT '是否置顶: Y-是、N-否',
  `is_recom`          varchar(4) DEFAULT 'N' COMMENT '是否推荐：Y-推荐、N-不推荐',
  `is_show`           varchar(4) DEFAULT 'Y' COMMENT '显示状态：Y-显示、N-不显示',
  `status`            varchar(4) DEFAULT '1' COMMENT '状态：1-待发布、2-已发布、3-已下架',
  `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_user`        varchar(11) DEFAULT '' COMMENT '创建人',
  `modify_user`        varchar(11) DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`ni_id`),
  KEY `idx_phjf_news_article_01` (`ni_code`),
  KEY `idx_phjf_news_article_02` (`language`),
  KEY `idx_phjf_news_article_03` (`np_code`),
  KEY `idx_phjf_news_article_04` (`is_top`),
  KEY `idx_phjf_news_article_05` (`is_recom`),
  KEY `idx_phjf_news_article_06` (`is_show`),
  KEY `idx_phjf_news_article_07` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站新闻信息表';

DROP TABLE IF EXISTS `phjf_web_app_download_info`;
CREATE TABLE `phjf_web_app_download_info` (
  `seq_uuid`     varchar(36) NOT NULL,
  `adi_id`       int(11) NOT NULL AUTO_INCREMENT COMMENT 'id标识',
  `adi_code`     varchar(64) DEFAULT '' COMMENT '标识码',
  `language`     varchar(32) NOT NULL DEFAULT 'ZH_SIMP' COMMENT '语言：ZH_SIMP-中文简体、ZH_UEY-维吾尔语',
  `user_type`    varchar(64) DEFAULT '' COMMENT '用户类型（ios-苹果用户、android-安卓用户）',
  `app_type`     varchar(64) DEFAULT '' COMMENT 'app类型（b-商户端App、c-客户端App）',
  `qr_code_url`  varchar(128) DEFAULT '' COMMENT '二维码图片',
  `description`  varchar(256) DEFAULT '' COMMENT '介绍',
  `status`       varchar(4) DEFAULT '1' COMMENT '状态1：有效 4：无效',
  `is_show`      varchar(4) DEFAULT 'Y' COMMENT '是否显示 Y：显示 N：不显示',
  `orders`       int(11) DEFAULT '0' COMMENT '排序',
  `create_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_user`  varchar(11) DEFAULT '' COMMENT '创建人',
  `modify_user`  varchar(11) DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`adi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='App下载信息';

DROP TABLE IF EXISTS `phjf_web_partner`;
CREATE TABLE `phjf_web_partner` (
  `seq_uuid`      varchar(36) NOT NULL,
  `partner_id`    int(11) NOT NULL AUTO_INCREMENT COMMENT 'id标识',
  `partner_code`  varchar(64) DEFAULT '' COMMENT '标识码',
  `language`      varchar(32) DEFAULT '' COMMENT '语言(ZH_SIMP-中文、ZH_UEY-维语)',
  `partner_name`  varchar(64) DEFAULT NULL COMMENT '名称',
  `picture`       varchar(128) DEFAULT '' COMMENT '合作伙伴图片',
  `orders`        int(11) DEFAULT '0' COMMENT '排序',
  `status`        varchar(4) DEFAULT '1' COMMENT '状态1：有效 4：无效',
  `is_show`       varchar(4) DEFAULT 'Y' COMMENT '是否显示 Y：显示 N：不显示',
  `create_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_user`   varchar(11) DEFAULT '' COMMENT '创建人',
  `modify_user`   varchar(11) DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`partner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站合作伙伴信息';

DROP TABLE IF EXISTS `phjf_web_links`;
CREATE TABLE `phjf_web_links` (
  `seq_uuid`     varchar(36) NOT NULL,
  `links_id`     int(11) NOT NULL AUTO_INCREMENT COMMENT 'id标识',
  `links_code`   varchar(64) DEFAULT '' COMMENT '标识码',
  `language`     varchar(32) DEFAULT '' COMMENT '语言',
  `links_name`         varchar(64) DEFAULT '' COMMENT '名称',
  `url`          varchar(255) DEFAULT '' COMMENT '链接地址',
  `status`       varchar(4) DEFAULT '1' COMMENT '状态1：有效 4：无效',
  `is_show`      varchar(4) DEFAULT 'Y' COMMENT '是否显示 Y：显示 N：不显示',
  `orders`       int(11) DEFAULT '0' COMMENT '排序',
  `create_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_user`   varchar(11) DEFAULT '' COMMENT '创建人',
  `modify_user`   varchar(11) DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`links_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站友情链接';

DROP TABLE IF EXISTS `phjf_web_about_us`;
CREATE TABLE `phjf_web_about_us` (
  `seq_uuid`    varchar(36) NOT NULL,
  `au_id`       int(11) NOT NULL AUTO_INCREMENT COMMENT 'id标识',
  `au_code`     varchar(64) DEFAULT '' COMMENT '编码',
  `au_name`        varchar(64) DEFAULT '' COMMENT '名称',
  `nav_code`    varchar(64) DEFAULT '' COMMENT '所属导航',
  `language`    varchar(255) DEFAULT '' COMMENT '语言：ZH_SIMP中文简体、维吾尔ZH_UEY',
  `content`     text COMMENT '内容',
  `orders`      int(11) DEFAULT '0' COMMENT '排序',
  `is_show`     varchar(4) DEFAULT 'Y' COMMENT '是否显示：Y：显示 ，N：不显示',
  `status`      varchar(4) DEFAULT '1' COMMENT '状态：1：有效，4：无效',
  `create_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_user`   varchar(11) DEFAULT '' COMMENT '创建人',
  `modify_user`   varchar(11) DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`au_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站关于我们信息';













