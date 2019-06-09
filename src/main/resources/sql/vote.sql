/*
 Navicat Premium Data Transfer

 Source Server         : mysql-local
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : vote

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 09/06/2019 15:09:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `departmentID` bigint(20) NOT NULL AUTO_INCREMENT,
  `departmentName` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '科室名称',
  `leaderID` bigint(20) NOT NULL DEFAULT '0' COMMENT '科长用户ID',
  PRIMARY KEY (`departmentID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for messageBoard
-- ----------------------------
DROP TABLE IF EXISTS `messageBoard`;
CREATE TABLE `messageBoard` (
  `messageBoardID` bigint(20) NOT NULL AUTO_INCREMENT,
  `voteID` bigint(20) NOT NULL DEFAULT '0' COMMENT '投票ID',
  `content` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '留言内容',
  `userID` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`messageBoardID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `noticeID` bigint(20) NOT NULL AUTO_INCREMENT,
  `noticeValue` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '公告数据',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`noticeID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userID` bigint(20) NOT NULL AUTO_INCREMENT,
  `userName` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '密码',
  `sex` tinyint(10) NOT NULL DEFAULT '1' COMMENT '1-男 2-女',
  `realName` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '真实姓名',
  `phone` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '电话',
  `roleType` tinyint(10) NOT NULL DEFAULT '1' COMMENT '1-普通用户 2-临时科长 3-科长 4-管理员',
  `departmentID` bigint(20) NOT NULL DEFAULT '0' COMMENT '科室ID',
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for vote
-- ----------------------------
DROP TABLE IF EXISTS `vote`;
CREATE TABLE `vote` (
  `voteID` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '标题',
  `desc` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '描述',
  `beginTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '开始时间',
  `endTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束时间',
  `voteType` tinyint(10) NOT NULL DEFAULT '1' COMMENT '投票类型 1-候选人投票 2-海选投票 3-科室内部投票',
  `voteMultipleNum` bigint(20) NOT NULL DEFAULT '1' COMMENT '投票最多选择数量',
  `departmentID` bigint(20) NOT NULL DEFAULT '0' COMMENT '科室内部投票的科室ID',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `createBy` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人ID',
  PRIMARY KEY (`voteID`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for voteDetail
-- ----------------------------
DROP TABLE IF EXISTS `voteDetail`;
CREATE TABLE `voteDetail` (
  `voteDetailID` bigint(20) NOT NULL AUTO_INCREMENT,
  `voteID` bigint(20) NOT NULL DEFAULT '0' COMMENT '投票ID',
  `content` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '投票内容',
  PRIMARY KEY (`voteDetailID`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for voteResult
-- ----------------------------
DROP TABLE IF EXISTS `voteResult`;
CREATE TABLE `voteResult` (
  `voteResultID` bigint(20) NOT NULL AUTO_INCREMENT,
  `voteID` bigint(20) NOT NULL DEFAULT '0' COMMENT '投票ID',
  `voteDetailIDs` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '投票明细IDs,逗号拼接',
  `resultType` tinyint(10) NOT NULL DEFAULT '1' COMMENT '投票类型 1-支持 2-弃权',
  `userID` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '投票用户ID 已加密处理',
  PRIMARY KEY (`voteResultID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
