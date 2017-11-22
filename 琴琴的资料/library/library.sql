/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : library

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2017-11-22 19:42:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookname` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `price` int(10) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', '平凡的世界', '路遥', '39', '10');
INSERT INTO `book` VALUES ('2', '人生', '路遥', '28', '12');
INSERT INTO `book` VALUES ('3', '兄弟', '余华', '60', '5');
INSERT INTO `book` VALUES ('4', '活着', '余华', '30', '6');
INSERT INTO `book` VALUES ('5', '狼图腾', '姜戎', '35', '3');
INSERT INTO `book` VALUES ('6', '陆犯焉识', '严歌岑', '23', '5');
INSERT INTO `book` VALUES ('9', '丰乳肥臀', '莫言', '45', '5');

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bookid` (`bid`),
  KEY `userid` (`uid`),
  CONSTRAINT `bookid` FOREIGN KEY (`bid`) REFERENCES `book` (`id`),
  CONSTRAINT `userid` FOREIGN KEY (`uid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES ('1', '1', '2', '1');
INSERT INTO `borrow` VALUES ('3', '3', '3', '1');
INSERT INTO `borrow` VALUES ('4', '4', '4', '2');
INSERT INTO `borrow` VALUES ('5', '5', '2', '3');
INSERT INTO `borrow` VALUES ('6', '9', '9', '1');
INSERT INTO `borrow` VALUES ('7', '1', '1', '3');
INSERT INTO `borrow` VALUES ('9', '1', '1', '3');
INSERT INTO `borrow` VALUES ('10', '6', '2', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `isadmin` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `NAME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '琴琴', '123', '18', 'female', '1');
INSERT INTO `user` VALUES ('2', '琼琼', '123', '13', 'female', '0');
INSERT INTO `user` VALUES ('3', '婷婷', '123', '3', 'female', '0');
INSERT INTO `user` VALUES ('4', '芳芳', '123', '20', 'female', '0');
INSERT INTO `user` VALUES ('5', '健健', '123', '19', 'male', '0');
INSERT INTO `user` VALUES ('6', '红红', '123', '18', 'female', '0');
INSERT INTO `user` VALUES ('9', 'ss', '123', '21', 'female', null);
