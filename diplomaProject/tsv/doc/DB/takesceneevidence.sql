/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50536
Source Host           : localhost:3306
Source Database       : takesceneevidence

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2015-03-12 16:46:43
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `tb_question`
-- ----------------------------
DROP TABLE IF EXISTS `tb_question`;
CREATE TABLE `tb_question` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `qid` varchar(32) NOT NULL COMMENT '问题ID',
  `uid` varchar(32) NOT NULL,
  `desc` varchar(500) DEFAULT NULL,
  `title` varchar(250) DEFAULT NULL COMMENT '标题',
  `state` int(4) NOT NULL COMMENT '状态（1：未处理，2：已分配，3：处理完成，4：已关闭）',
  `closeTime` bigint(32) DEFAULT '0',
  `createTime` bigint(32) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `loc` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_question
-- ----------------------------
INSERT INTO `tb_question` VALUES ('1', 'qid', '00000', 'descreapt', 'text', '1', '0', '1234567890', null, '54.45,34.23');
INSERT INTO `tb_question` VALUES ('2', 'qid2', '44444', 'desc', 'title', '1', '0', '1426054489394', '', '23,34');
INSERT INTO `tb_question` VALUES ('3', 'qid3', '123456', 'desc', 'title', '1', '0', '1426054552113', '', '23,34');
INSERT INTO `tb_question` VALUES ('4', '038213', '44444', 'xxx', 'test', '1', '0', '1426141130738', 'officers_xxx.jpg', '20,20');
INSERT INTO `tb_question` VALUES ('5', '023212', '44444', 'xxx', 'test', '1', '0', '1426141508831', 'officers_xxx.jpg', '20,20');
INSERT INTO `tb_question` VALUES ('6', '682036', '44444', 'xxx', 'test', '1', '0', '1426141544425', 'officers_xxx.jpg', '20,20');

-- ----------------------------
-- Table structure for `tb_question_log`
-- ----------------------------
DROP TABLE IF EXISTS `tb_question_log`;
CREATE TABLE `tb_question_log` (
  `creator` varchar(255) DEFAULT NULL,
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `qid` varchar(32) NOT NULL,
  `uid` varchar(32) NOT NULL,
  `state` int(4) NOT NULL COMMENT '状态（1：未处理，2：已分配，3：处理完成，4：已关闭）',
  `url` varchar(255) DEFAULT NULL,
  `createTime` bigint(32) NOT NULL,
  `loc` varchar(64) DEFAULT NULL,
  `desc` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_question_log
-- ----------------------------
INSERT INTO `tb_question_log` VALUES (null, '1', '431160', '44444', '1', 'officers_xxx.jpg', '1426141883613', '20,20', 'xxx');
INSERT INTO `tb_question_log` VALUES ('creator', '2', 'test', '123456', '1', '', '1426146153972', '23,34', 'desc');

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `uid` varchar(32) NOT NULL COMMENT '标识',
  `userName` varchar(32) NOT NULL COMMENT '用户名',
  `type` int(10) NOT NULL COMMENT '用户类型（1：执勤人员，2：任务分配，3：办事）',
  `pwd` varchar(64) NOT NULL,
  `tmsi` varchar(32) NOT NULL DEFAULT '0',
  `createTime` bigint(32) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', '12345', 'admin', '0', 'pwd', '0', '234567890');
INSERT INTO `tb_user` VALUES ('2', '00000', '13094415143', '2', 'pwd', '0', '23456789');
INSERT INTO `tb_user` VALUES ('3', '11111', '13881702666', '3', 'pwd', '0', '3467890');
INSERT INTO `tb_user` VALUES ('4', '22222', '13881702999', '1', 'pwd', '0', '234567890');
INSERT INTO `tb_user` VALUES ('5', '33333', '13881702777', '2', 'pwd', '0', '4567890');
INSERT INTO `tb_user` VALUES ('6', '44444', '13881702448', '1', 'pwd', '1426142103425', '56789045678');
