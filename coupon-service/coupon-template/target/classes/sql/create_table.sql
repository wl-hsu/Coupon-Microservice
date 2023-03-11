
-- create coupon_template table
CREATE TABLE IF NOT EXISTS `coupon_data`.`coupon_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'auto increment primary key',
  `available` boolean NOT NULL DEFAULT false COMMENT 'Whether it is available; true: available, false: not available',
  `expired` boolean NOT NULL DEFAULT false COMMENT 'Is it expired; true: yes, false: no',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT 'coupon name',
  `logo` varchar(256) NOT NULL DEFAULT '' COMMENT 'coupon logo',
  `intro` varchar(256) NOT NULL DEFAULT '' COMMENT 'Coupon Description',
  `category` varchar(64) NOT NULL DEFAULT '' COMMENT 'Coupon classification',
  `product_line` int(11) NOT NULL DEFAULT '0' COMMENT 'product line',
  `coupon_count` int(11) NOT NULL DEFAULT '0' COMMENT 'total',
  `create_time` datetime NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT 'creation time',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'create user',
  `template_key` varchar(128) NOT NULL DEFAULT '' COMMENT 'Coding of Coupon Templates',
  `target` int(11) NOT NULL DEFAULT '0' COMMENT 'Target users',
  `rule` varchar(1024) NOT NULL DEFAULT '' COMMENT 'Coupon Rules: json representation of TemplateRule',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category`),
  KEY `idx_user_id` (`user_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='Coupon Template Sheet';

-- clean table
-- truncate coupon_template;
