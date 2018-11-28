/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : oop_gradingsys

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-11-28 14:57:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for authentication
-- ----------------------------
DROP TABLE IF EXISTS `authentication`;
CREATE TABLE `authentication` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authentication
-- ----------------------------
INSERT INTO `authentication` VALUES ('test', '1234');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `courseid` int(40) NOT NULL AUTO_INCREMENT,
  `cname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`courseid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', 'CS591D1');
INSERT INTO `course` VALUES ('2', 'testcourse');

-- ----------------------------
-- Table structure for distribution
-- ----------------------------
DROP TABLE IF EXISTS `distribution`;
CREATE TABLE `distribution` (
  `courseid` int(40) NOT NULL,
  `gradableid` int(11) NOT NULL,
  `weighting_undergraduate` decimal(10,3) DEFAULT NULL,
  `weighting_graduate` decimal(10,3) DEFAULT NULL,
  `customized` int(10) unsigned zerofill DEFAULT '0000000000',
  PRIMARY KEY (`courseid`,`gradableid`),
  KEY `foreignkey2` (`gradableid`),
  CONSTRAINT `foreignkey1` FOREIGN KEY (`courseid`) REFERENCES `course` (`courseid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `foreignkey2` FOREIGN KEY (`gradableid`) REFERENCES `gradable` (`gradableid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of distribution
-- ----------------------------
INSERT INTO `distribution` VALUES ('1', '1', '0.300', '0.300', '0000000000');
INSERT INTO `distribution` VALUES ('1', '4', '0.300', '0.200', '0000000000');
INSERT INTO `distribution` VALUES ('1', '5', '0.400', '0.500', '0000000000');
INSERT INTO `distribution` VALUES ('1', '8', null, null, '0000000000');
INSERT INTO `distribution` VALUES ('2', '2', '0.400', '0.300', '0000000000');
INSERT INTO `distribution` VALUES ('2', '3', '0.600', '0.700', '0000000000');

-- ----------------------------
-- Table structure for gradable
-- ----------------------------
DROP TABLE IF EXISTS `gradable`;
CREATE TABLE `gradable` (
  `gradableid` int(11) NOT NULL AUTO_INCREMENT,
  `gname` varchar(255) DEFAULT NULL,
  `maxscore` decimal(11,3) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`gradableid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gradable
-- ----------------------------
INSERT INTO `gradable` VALUES ('1', 'D1HW1', '100.000', 'hw');
INSERT INTO `gradable` VALUES ('2', 'E2HW1', '50.000', 'hw');
INSERT INTO `gradable` VALUES ('3', 'E2EXAM1', '150.000', 'exam');
INSERT INTO `gradable` VALUES ('4', 'D1QUIZ', '30.000', 'quiz');
INSERT INTO `gradable` VALUES ('5', 'D1PROJECT', '500.000', 'project');
INSERT INTO `gradable` VALUES ('8', 'test', '100.000', 'hw');

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `gradableid` int(11) NOT NULL,
  `studentid` varchar(40) NOT NULL,
  `weighting` decimal(10,3) DEFAULT NULL,
  `sscore` decimal(11,3) DEFAULT NULL,
  PRIMARY KEY (`gradableid`,`studentid`),
  KEY `foreignkey3` (`studentid`),
  KEY `gradableid` (`gradableid`),
  CONSTRAINT `foreignkey3` FOREIGN KEY (`studentid`) REFERENCES `student` (`studentid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `foreignkey4` FOREIGN KEY (`gradableid`) REFERENCES `gradable` (`gradableid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES ('1', 'test1_U', '0.300', '100.000');
INSERT INTO `grade` VALUES ('1', 'test2_G', '0.300', '30.000');
INSERT INTO `grade` VALUES ('1', 'U89012077', '0.300', '40.000');
INSERT INTO `grade` VALUES ('2', 'test2_G', '0.300', '35.000');
INSERT INTO `grade` VALUES ('2', 'test3_U', '0.400', '50.000');
INSERT INTO `grade` VALUES ('2', 'U89012077', '0.300', '45.000');
INSERT INTO `grade` VALUES ('3', 'test2_G', '0.700', '100.000');
INSERT INTO `grade` VALUES ('3', 'test3_U', '0.600', '100.000');
INSERT INTO `grade` VALUES ('3', 'U89012077', '0.700', '130.000');
INSERT INTO `grade` VALUES ('4', 'test1_U', '0.300', '30.000');
INSERT INTO `grade` VALUES ('4', 'test2_G', '0.200', '20.000');
INSERT INTO `grade` VALUES ('4', 'U89012077', '0.200', '0.000');
INSERT INTO `grade` VALUES ('5', 'test1_U', '0.400', '400.000');
INSERT INTO `grade` VALUES ('5', 'test2_G', '0.500', '300.000');
INSERT INTO `grade` VALUES ('5', 'test3_U', '0.400', '200.000');
INSERT INTO `grade` VALUES ('5', 'U89012077', '0.500', '200.000');

-- ----------------------------
-- Table structure for grade_tag
-- ----------------------------
DROP TABLE IF EXISTS `grade_tag`;
CREATE TABLE `grade_tag` (
  `gradableid` int(11) NOT NULL,
  `studentid` varchar(40) NOT NULL,
  `tagid` int(11) NOT NULL,
  PRIMARY KEY (`gradableid`,`studentid`,`tagid`),
  KEY `foreignkey8` (`studentid`),
  KEY `foreignkey9` (`tagid`),
  CONSTRAINT `foreignkey7` FOREIGN KEY (`gradableid`) REFERENCES `grade` (`gradableid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `foreignkey8` FOREIGN KEY (`studentid`) REFERENCES `grade` (`studentid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `foreignkey9` FOREIGN KEY (`tagid`) REFERENCES `tag` (`tagid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grade_tag
-- ----------------------------
INSERT INTO `grade_tag` VALUES ('1', 'U89012077', '1');
INSERT INTO `grade_tag` VALUES ('5', 'test3_U', '1');
INSERT INTO `grade_tag` VALUES ('5', 'test3_U', '3');

-- ----------------------------
-- Table structure for register
-- ----------------------------
DROP TABLE IF EXISTS `register`;
CREATE TABLE `register` (
  `StudentId` varchar(10) NOT NULL,
  `CourseId` int(40) NOT NULL,
  PRIMARY KEY (`StudentId`,`CourseId`),
  KEY `foreignkey6` (`CourseId`),
  CONSTRAINT `foreignkey5` FOREIGN KEY (`StudentId`) REFERENCES `student` (`studentid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `foreignkey6` FOREIGN KEY (`CourseId`) REFERENCES `course` (`courseid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of register
-- ----------------------------
INSERT INTO `register` VALUES ('test1_U', '1');
INSERT INTO `register` VALUES ('test2_G', '1');
INSERT INTO `register` VALUES ('U89012077', '1');
INSERT INTO `register` VALUES ('test2_G', '2');
INSERT INTO `register` VALUES ('test3_U', '2');
INSERT INTO `register` VALUES ('U89012077', '2');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `studentid` varchar(40) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `type` varchar(40) DEFAULT NULL,
  `customized` int(255) unsigned zerofill DEFAULT '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000',
  PRIMARY KEY (`studentid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('test1_U', 'abc', 'qwe', 'ugrad', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000');
INSERT INTO `student` VALUES ('test2_G', 'hello', 'world', 'grad', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000');
INSERT INTO `student` VALUES ('test3_U', 'uuu', 'hhh', 'ugrad', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000');
INSERT INTO `student` VALUES ('U89012077', 'zhong', 'tu', 'grad', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000');

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `tagid` int(11) NOT NULL AUTO_INCREMENT,
  `tname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tagid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES ('1', 'abc');
INSERT INTO `tag` VALUES ('2', 'eee');
INSERT INTO `tag` VALUES ('3', 'ttt');
