/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50519
Source Host           : 127.0.0.1:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2018-12-25 14:31:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `groups`
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `groupid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `enable` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`groupid`),
  KEY `groups_1` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of groups
-- ----------------------------
INSERT INTO `groups` VALUES ('1', '厦门区', '0');
INSERT INTO `groups` VALUES ('2', '漳州区', '0');
INSERT INTO `groups` VALUES ('3', '泉州区', '0');
INSERT INTO `groups` VALUES ('4', '收银机组模板', '0');
INSERT INTO `groups` VALUES ('5', '资产组模板', '0');

-- ----------------------------
-- Table structure for `history`
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `historyid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `hostid` bigint(20) unsigned NOT NULL,
  `historysn` varchar(18) NOT NULL DEFAULT '',
  PRIMARY KEY (`historyid`),
  UNIQUE KEY `historysn` (`historysn`) USING BTREE,
  KEY `hostid` (`hostid`) USING BTREE,
  CONSTRAINT `c_history_1` FOREIGN KEY (`hostid`) REFERENCES `hosts` (`hostid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of history
-- ----------------------------

-- ----------------------------
-- Table structure for `history_items`
-- ----------------------------
DROP TABLE IF EXISTS `history_items`;
CREATE TABLE `history_items` (
  `historyitemid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `historysn` varchar(18) NOT NULL DEFAULT '',
  `itemid` bigint(20) unsigned NOT NULL,
  `value` varchar(255) NOT NULL DEFAULT '',
  `clock` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`historyitemid`),
  KEY `history_items_1` (`itemid`,`clock`) USING BTREE,
  KEY `historysn` (`historysn`),
  CONSTRAINT ` c_history_items_1` FOREIGN KEY (`itemid`) REFERENCES `items` (`itemid`) ON DELETE CASCADE,
  CONSTRAINT ` c_history_items_2` FOREIGN KEY (`historysn`) REFERENCES `history` (`historysn`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of history_items
-- ----------------------------

-- ----------------------------
-- Table structure for `hosts`
-- ----------------------------
DROP TABLE IF EXISTS `hosts`;
CREATE TABLE `hosts` (
  `hostid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `host` varchar(255) NOT NULL DEFAULT '',
  `status` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL DEFAULT '',
  `templateid` bigint(20) unsigned DEFAULT NULL,
  `description` varchar(255) NOT NULL DEFAULT '',
  `enable` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`hostid`),
  KEY `hosts_1` (`host`),
  KEY `hosts_2` (`status`),
  KEY `hosts_3` (`name`),
  KEY `c_hosts_1` (`templateid`),
  CONSTRAINT `c_hosts_1` FOREIGN KEY (`templateid`) REFERENCES `hosts` (`hostid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hosts
-- ----------------------------
INSERT INTO `hosts` VALUES ('1', 'XM0005', '0', '海天店', null, '', '0');
INSERT INTO `hosts` VALUES ('2', 'XM0004', '0', '康乐店', null, '', '0');
INSERT INTO `hosts` VALUES ('3', 'POS', '0', '收银机参数组', null, '', '0');

-- ----------------------------
-- Table structure for `hosts_groups`
-- ----------------------------
DROP TABLE IF EXISTS `hosts_groups`;
CREATE TABLE `hosts_groups` (
  `hostgroupid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `groupid` bigint(20) unsigned NOT NULL,
  `hostid` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`hostgroupid`),
  UNIQUE KEY `hosts_groups_1` (`hostid`,`groupid`),
  KEY `hosts_groups_2` (`groupid`),
  CONSTRAINT `c_hosts_groups_1` FOREIGN KEY (`hostid`) REFERENCES `hosts` (`hostid`) ON DELETE CASCADE,
  CONSTRAINT `c_hosts_groups_2` FOREIGN KEY (`groupid`) REFERENCES `groups` (`groupid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hosts_groups
-- ----------------------------
INSERT INTO `hosts_groups` VALUES ('1', '1', '1');
INSERT INTO `hosts_groups` VALUES ('2', '1', '2');

-- ----------------------------
-- Table structure for `hosts_templates`
-- ----------------------------
DROP TABLE IF EXISTS `hosts_templates`;
CREATE TABLE `hosts_templates` (
  `hosttemplateid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `hostid` bigint(20) unsigned NOT NULL,
  `templateid` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`hosttemplateid`),
  UNIQUE KEY `hosts_templates_1` (`hostid`,`templateid`),
  KEY `hosts_templates_2` (`templateid`),
  CONSTRAINT `c_hosts_templates_1` FOREIGN KEY (`hostid`) REFERENCES `hosts` (`hostid`) ON DELETE CASCADE,
  CONSTRAINT `c_hosts_templates_2` FOREIGN KEY (`templateid`) REFERENCES `hosts` (`hostid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hosts_templates
-- ----------------------------
INSERT INTO `hosts_templates` VALUES ('15', '1', '3');
INSERT INTO `hosts_templates` VALUES ('16', '2', '3');

-- ----------------------------
-- Table structure for `items`
-- ----------------------------
DROP TABLE IF EXISTS `items`;
CREATE TABLE `items` (
  `itemid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `hostid` bigint(20) unsigned NOT NULL,
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT '',
  `templateid` bigint(20) unsigned DEFAULT NULL COMMENT '对应本表模板itemid',
  `enable` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`itemid`),
  UNIQUE KEY `items_1` (`hostid`,`name`) USING BTREE,
  KEY `items_2` (`enable`),
  KEY `items_3` (`templateid`),
  CONSTRAINT `c_items_2` FOREIGN KEY (`templateid`) REFERENCES `items` (`itemid`) ON DELETE CASCADE,
  CONSTRAINT `c_items_1` FOREIGN KEY (`hostid`) REFERENCES `hosts` (`hostid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of items
-- ----------------------------
INSERT INTO `items` VALUES ('1', '3', '参数1', '', null, '0');
INSERT INTO `items` VALUES ('2', '3', '参数2', '', null, '0');
INSERT INTO `items` VALUES ('3', '3', '参数3', '', null, '0');

-- ----------------------------
-- Table structure for `rights`
-- ----------------------------
DROP TABLE IF EXISTS `rights`;
CREATE TABLE `rights` (
  `rightid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `groupid` bigint(20) unsigned NOT NULL COMMENT 'usrgrpid',
  `id` bigint(20) unsigned NOT NULL COMMENT 'groupid',
  PRIMARY KEY (`rightid`),
  KEY `rights_1` (`groupid`),
  KEY `rights_2` (`id`),
  CONSTRAINT `c_rights_1` FOREIGN KEY (`groupid`) REFERENCES `usrgrp` (`usrgrpid`) ON DELETE CASCADE,
  CONSTRAINT `c_rights_2` FOREIGN KEY (`id`) REFERENCES `groups` (`groupid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rights
-- ----------------------------
INSERT INTO `rights` VALUES ('2', '2', '1');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `userid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL DEFAULT '' COMMENT '帐号',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
  `role` int(11) NOT NULL DEFAULT '3' COMMENT '角色:管理员或用户',
  `enabled` int(11) NOT NULL DEFAULT '0' COMMENT '帐户可用',
  PRIMARY KEY (`userid`),
  UNIQUE KEY `users_1` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('5', 'admin', 'admin', '$2a$10$P4R6JlhTFKn2J3m9qFpJte1V7WJSBX5.ACNqrATTo/VCUEXUQF/oy', '1', '0');
INSERT INTO `users` VALUES ('31', '80006877', '张三', '$2a$10$dR/QJc1X48BlfO6zvnxZ1e6EKZKk8HAGymu5stD5sEdjc2WDA/6nS', '3', '0');

-- ----------------------------
-- Table structure for `users_groups`
-- ----------------------------
DROP TABLE IF EXISTS `users_groups`;
CREATE TABLE `users_groups` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userid` bigint(20) unsigned NOT NULL,
  `usrgrpid` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_groups_1` (`usrgrpid`,`userid`),
  KEY `users_groups_2` (`userid`),
  CONSTRAINT `c_users_groups_1` FOREIGN KEY (`usrgrpid`) REFERENCES `usrgrp` (`usrgrpid`) ON DELETE CASCADE,
  CONSTRAINT `c_users_groups_2` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users_groups
-- ----------------------------
INSERT INTO `users_groups` VALUES ('31', '5', '1');
INSERT INTO `users_groups` VALUES ('34', '31', '1');
INSERT INTO `users_groups` VALUES ('2', '5', '2');
INSERT INTO `users_groups` VALUES ('3', '5', '3');
INSERT INTO `users_groups` VALUES ('48', '5', '5');

-- ----------------------------
-- Table structure for `usrgrp`
-- ----------------------------
DROP TABLE IF EXISTS `usrgrp`;
CREATE TABLE `usrgrp` (
  `usrgrpid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `enable` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`usrgrpid`),
  UNIQUE KEY `usrgrp_1` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usrgrp
-- ----------------------------
INSERT INTO `usrgrp` VALUES ('1', '厦门区', '0');
INSERT INTO `usrgrp` VALUES ('2', '漳州区', '0');
INSERT INTO `usrgrp` VALUES ('3', '泉州区', '0');
INSERT INTO `usrgrp` VALUES ('5', '福州区', '0');
