/*
 Navicat Premium Data Transfer

 Source Server         : Wwing
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : express

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 08/10/2021 10:48:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `loginip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `logintime` datetime NULL DEFAULT NULL,
  `createtime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '123456', '0:0:0:0:0:0:0:1', '2021-10-08 00:00:00', '2021-09-29 12:47:04');

-- ----------------------------
-- Table structure for courier
-- ----------------------------
DROP TABLE IF EXISTS `courier`;
CREATE TABLE `courier`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `dname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sysphone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `idcard` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dpassword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `eno` int NULL DEFAULT NULL,
  `regtime` timestamp NULL DEFAULT NULL,
  `lasttime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `card`(`idcard`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1009 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of courier
-- ----------------------------
INSERT INTO `courier` VALUES (1001, '买买买', '13000000000', '130222199502215647', '123456', 10, '2021-10-02 11:38:52', '2021-10-08 10:39:21');
INSERT INTO `courier` VALUES (1002, '帅哥2', '13122222222', '131222198703245555', '123456', 9, '2021-10-02 11:47:23', '2021-10-02 11:47:27');
INSERT INTO `courier` VALUES (1003, '帅哥3', '13555555555', '253555199605215647', '123456', 8, '2021-09-17 11:48:16', '2021-10-02 11:48:22');
INSERT INTO `courier` VALUES (1004, '帅哥4', '15699999999', '330021199803269999', '123456', 7, '2021-09-21 11:48:49', '2021-10-02 11:48:53');
INSERT INTO `courier` VALUES (1005, '美女1', '18965478523', '560248199312252222', '123456', 6, '2021-09-01 11:49:29', '2021-10-02 11:49:33');

-- ----------------------------
-- Table structure for express
-- ----------------------------
DROP TABLE IF EXISTS `express`;
CREATE TABLE `express`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userphone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `intime` timestamp NULL DEFAULT NULL,
  `outime` timestamp NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `sysphone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code`(`code`) USING BTREE,
  UNIQUE INDEX `number`(`number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of express
-- ----------------------------
INSERT INTO `express` VALUES (4, '124597', 'hih', '15687945687', '顺丰快递', '156', '2021-09-20 10:16:21', '2021-09-21 10:16:26', 1, '12356478965');
INSERT INTO `express` VALUES (5, '126547', '张三', '18633332222', '京东快递', NULL, '2021-09-29 10:16:59', '2021-10-07 19:42:29', 1, '13000000000');
INSERT INTO `express` VALUES (6, '123848', '张三', '18633332222', '天天快递', '999', '2021-09-29 16:39:18', NULL, 0, '13000000000');
INSERT INTO `express` VALUES (8, '123865', '张三', '18633332222', '天天快递', '998', '2021-09-29 16:40:32', NULL, 0, '13000000000');
INSERT INTO `express` VALUES (9, '1231987', '张三', '18633332222', '天天快递', NULL, '2021-09-29 16:41:08', '2021-09-29 17:14:45', 1, '13000000000');
INSERT INTO `express` VALUES (16, '568', '李四', '13856978754', '天天快递', '154755', '2021-09-29 18:32:56', NULL, 0, '18898745632');
INSERT INTO `express` VALUES (18, '123487', 'Cat', '13303210654', 'FedEx国际', '306605', '2021-10-01 19:51:25', NULL, 0, '12356478965');
INSERT INTO `express` VALUES (19, '127894', '张三', '13813838387', '顺丰快递', '879961', '2021-10-01 22:36:12', NULL, 0, NULL);
INSERT INTO `express` VALUES (21, '265478', '张三', '18633332222', '顺丰速运', NULL, '2021-10-07 17:01:55', '2021-10-07 18:47:25', 1, '13000000000');
INSERT INTO `express` VALUES (22, '417852', '张三', '18633332222', '民邦快递', NULL, '2021-10-07 17:04:18', '2021-10-07 17:50:36', 1, '13000000000');
INSERT INTO `express` VALUES (24, '564789', '张三', '18633332222', '挪威邮政', NULL, '2021-10-07 18:47:52', '2021-10-08 10:40:15', 1, '13000000000');
INSERT INTO `express` VALUES (25, '236589', '张三', '18633332222', '奥地利邮政', NULL, '2021-10-08 10:33:10', '2021-10-08 10:39:40', 1, '13000000000');
INSERT INTO `express` VALUES (27, '382907', '张三', '18633332222', '苏宁快递', '973149', '2021-10-08 10:40:50', NULL, 0, '13000000000');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `uphone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `idno` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `upwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `regtime` timestamp NULL DEFAULT NULL,
  `lasttime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idno`(`idno`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1013 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1001, '啦啦啦', '13056489632', '132156789633655263', '123456', '2021-10-02 20:49:40', NULL);
INSERT INTO `user` VALUES (1002, '张三', '18633332222', '156320199603255556', '123', '2021-10-02 20:50:39', '2021-10-08 10:38:20');
INSERT INTO `user` VALUES (1003, '李四', '13856978754', '153205200502169999', '123', '2021-09-16 20:51:20', '2021-10-02 20:51:25');
INSERT INTO `user` VALUES (1004, '王五', '19999999999', '999999999999999999', '564', '2021-09-02 20:51:56', '2021-10-02 20:52:05');
INSERT INTO `user` VALUES (1005, '赵六', '17777777777', '888888888888888888', '123', '2021-09-10 20:52:35', '2021-10-02 20:52:40');
INSERT INTO `user` VALUES (1009, '赵六', '17777777779', '888888888888888880', '123', '2021-10-02 22:25:22', NULL);
INSERT INTO `user` VALUES (1010, '赵六', '17777777778', '888888888888888881', '123', '2021-09-09 22:25:53', NULL);
INSERT INTO `user` VALUES (1011, NULL, '13856978754', NULL, NULL, '2021-10-06 22:03:31', NULL);
INSERT INTO `user` VALUES (1012, NULL, '18633332222', NULL, NULL, '2021-10-06 22:06:31', NULL);
INSERT INTO `user` VALUES (1013, '广泛的', '18888888888', NULL, '123456', '2021-10-06 22:45:34', NULL);
INSERT INTO `user` VALUES (1014, NULL, '15012346987', NULL, NULL, '2021-10-07 12:01:27', NULL);
INSERT INTO `user` VALUES (1016, NULL, '18230265693', NULL, NULL, '2021-10-07 18:44:00', NULL);
INSERT INTO `user` VALUES (1017, '啊大大', '18635469632', NULL, '123456', '2021-10-07 18:45:04', '2021-10-07 18:45:21');
INSERT INTO `user` VALUES (1019, '的方式', '15644448888', NULL, '123456', '2021-10-08 10:36:49', NULL);

SET FOREIGN_KEY_CHECKS = 1;
