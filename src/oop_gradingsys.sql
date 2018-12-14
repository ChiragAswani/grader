/*
Navicat MySQL Data Transfer
Source Server         : localhost_3306
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : oop_gradingsys
Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001
Date: 2018-12-05 15:13:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for authentication
-- ----------------------------
DROP TABLE IF EXISTS `authentication`;
CREATE TABLE `authentication` (
  `password` varchar(45) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authentication
-- ----------------------------
INSERT INTO `authentication` VALUES ('1234', 'test');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `courseid` int(11) NOT NULL,
  `categoryName` varchar(255) NOT NULL,
  `weight_undergraduate` decimal(10,2) DEFAULT NULL,
  `weight_graduate` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`courseid`,`categoryName`),
  KEY `categoryName` (`categoryName`),
  CONSTRAINT `foreignkey10` FOREIGN KEY (`courseid`) REFERENCES `course` (`courseid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', 'hw', null, null);
INSERT INTO `category` VALUES ('1', 'newCol', null, null);
INSERT INTO `category` VALUES ('1', 'project', null, null);
INSERT INTO `category` VALUES ('1', 'quiz', null, null);
INSERT INTO `category` VALUES ('2', 'exam', null, null);
INSERT INTO `category` VALUES ('2', 'hw', null, null);

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `courseid` int(40) NOT NULL AUTO_INCREMENT,
  `cname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`courseid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', 'CS591D1');
INSERT INTO `course` VALUES ('2', 'testcourse');
INSERT INTO `course` VALUES ('5', 'ahahaha');

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
INSERT INTO `distribution` VALUES ('1', '13', null, null, '0000000000');
INSERT INTO `distribution` VALUES ('1', '14', '2.130', '0.120', '0000000000');
INSERT INTO `distribution` VALUES ('1', '15', '1.230', '1.230', '0000000000');
INSERT INTO `distribution` VALUES ('2', '2', '0.400', '0.300', '0000000000');
INSERT INTO `distribution` VALUES ('2', '3', '0.600', '0.700', '0000000000');
INSERT INTO `distribution` VALUES ('5', '12', null, null, '0000000000');

-- ----------------------------
-- Table structure for gradable
-- ----------------------------
DROP TABLE IF EXISTS `gradable`;
CREATE TABLE `gradable` (
  `gradableid` int(11) NOT NULL AUTO_INCREMENT,
  `gname` varchar(255) DEFAULT NULL,
  `maxscore` decimal(11,3) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`gradableid`),
  KEY `foreignkey12` (`type`),
  CONSTRAINT `foreignkey12` FOREIGN KEY (`type`) REFERENCES `category` (`categoryName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gradable
-- ----------------------------
INSERT INTO `gradable` VALUES ('1', 'D1HW1', '100.000', 'hw');
INSERT INTO `gradable` VALUES ('2', 'E2HW1', '50.000', 'hw');
INSERT INTO `gradable` VALUES ('3', 'E2EXAM1', '150.000', 'exam');
INSERT INTO `gradable` VALUES ('4', 'D1QUIZ', '30.000', 'quiz');
INSERT INTO `gradable` VALUES ('5', 'D1PROJECT', '500.000', 'project');
INSERT INTO `gradable` VALUES ('8', 'test', '100.000', 'hw');
INSERT INTO `gradable` VALUES ('9', 'hello', null, null);
INSERT INTO `gradable` VALUES ('10', 'hello', null, null);
INSERT INTO `gradable` VALUES ('11', 'hello', null, null);
INSERT INTO `gradable` VALUES ('12', 'hello', null, null);
INSERT INTO `gradable` VALUES ('13', 'tttttzzzz', null, null);
INSERT INTO `gradable` VALUES ('14', 'tz', '2.130', null);
INSERT INTO `gradable` VALUES ('15', '123', '1.230', 'newCol');

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
  `customized` int(11) unsigned zerofill DEFAULT '00000000000',
  PRIMARY KEY (`StudentId`,`CourseId`),
  KEY `foreignkey6` (`CourseId`),
  CONSTRAINT `foreignkey5` FOREIGN KEY (`StudentId`) REFERENCES `student` (`studentid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `foreignkey6` FOREIGN KEY (`CourseId`) REFERENCES `course` (`courseid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of register
-- ----------------------------
INSERT INTO `register` VALUES ('11111', '1', '00000000000');
INSERT INTO `register` VALUES ('123213', '1', '00000000000');
INSERT INTO `register` VALUES ('2222', '1', '00000000000');
INSERT INTO `register` VALUES ('ahahahhaha', '1', '00000000000');
INSERT INTO `register` VALUES ('ahhh', '1', '00000000000');
INSERT INTO `register` VALUES ('hi', '1', '00000000000');
INSERT INTO `register` VALUES ('qqqqqq', '5', '00000000000');
INSERT INTO `register` VALUES ('qwert', '1', '00000000000');
INSERT INTO `register` VALUES ('student1', '1', '00000000000');
INSERT INTO `register` VALUES ('test1_U', '1', '00000000000');
INSERT INTO `register` VALUES ('test2_G', '1', '00000000000');
INSERT INTO `register` VALUES ('test2_G', '2', '00000000000');
INSERT INTO `register` VALUES ('test3_U', '2', '00000000000');
INSERT INTO `register` VALUES ('U889797', '1', '00000000000');
INSERT INTO `register` VALUES ('u8901', '1', '00000000000');
INSERT INTO `register` VALUES ('U89012077', '1', '00000000000');
INSERT INTO `register` VALUES ('U89012077', '2', '00000000000');
INSERT INTO `register` VALUES ('yuyuiya', '1', '00000000000');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `studentid` varchar(40) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `type` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`studentid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('11111', 'test1', 'test2', null);
INSERT INTO `student` VALUES ('123', 'zhong', 'tu', null);
INSERT INTO `student` VALUES ('123213', 'hello', 'world', null);
INSERT INTO `student` VALUES ('2222', 'abc', 'def', null);
INSERT INTO `student` VALUES ('333', 'qq', 'aaa', null);
INSERT INTO `student` VALUES ('ahahahhaha', '123', 'mm', null);
INSERT INTO `student` VALUES ('ahhh', 'nov', '29', null);
INSERT INTO `student` VALUES ('hi', 'hi', 'hi', null);
INSERT INTO `student` VALUES ('qqqqqq', 'firstname ahahaha', null, null);
INSERT INTO `student` VALUES ('qwert', 'aaaa', 'vvvv', null);
INSERT INTO `student` VALUES ('student1', 'student2', '6789', 'ugrad');
INSERT INTO `student` VALUES ('test course s', 'firstname ahahaha', null, null);
INSERT INTO `student` VALUES ('test1_U', 'abc', 'qwe', 'ugrad');
INSERT INTO `student` VALUES ('test2_G', 'hello', 'world', 'grad');
INSERT INTO `student` VALUES ('test3_U', 'uuu', 'hhh', 'ugrad');
INSERT INTO `student` VALUES ('U889797', 'Chirag', 'Aswani', null);
INSERT INTO `student` VALUES ('u8901', 'add', 'student', null);
INSERT INTO `student` VALUES ('U89012077', 'zhong', 'tu', 'grad');
INSERT INTO `student` VALUES ('yuyuiya', 'hello', 'world', null);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `tagid` int(11) NOT NULL AUTO_INCREMENT,
  `tname` varchar(45) DEFAULT NULL UNIQUE,
  PRIMARY KEY (`tagid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES ('1', 'abc');
INSERT INTO `tag` VALUES ('2', 'eee');
INSERT INTO `tag` VALUES ('3', 'ttt');