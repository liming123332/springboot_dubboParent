/*
 Navicat Premium Data Transfer

 Source Server         : Test
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : myclassdb

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 05/04/2019 17:57:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for my_class
-- ----------------------------
DROP TABLE IF EXISTS `my_class`;
CREATE TABLE `my_class`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of my_class
-- ----------------------------
INSERT INTO `my_class` VALUES (1, 'java1810');
INSERT INTO `my_class` VALUES (2, 'java1811');
INSERT INTO `my_class` VALUES (3, 'java1833');
INSERT INTO `my_class` VALUES (4, 'java1912j');

SET FOREIGN_KEY_CHECKS = 1;
