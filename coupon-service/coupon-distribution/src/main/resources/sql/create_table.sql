-- create coupon table
CREATE TABLE IF NOT EXISTS `coupon_data`.`coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `template_id` int(11) NOT NULL DEFAULT '0' COMMENT 'The primary key associated with the coupon template',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'ID of the user who take the coupon',
  `coupon_code` varchar(64) NOT NULL DEFAULT '' COMMENT 'coupon code',
  `assign_time` datetime NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT 'pick up time',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT 'Coupon status',
  PRIMARY KEY (`id`),
  KEY `idx_template_id` (`template_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='Coupons (records received by users)';

-- clear table
-- truncate coupon;
