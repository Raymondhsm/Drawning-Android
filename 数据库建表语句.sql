/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3307(dawning)
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3307
 Source Schema         : dawning

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 13/12/2018 11:50:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `administrator_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `administrator_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `account` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`administrator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `article_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `article_link` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`article_id`) USING BTREE,
  INDEX `fk_article_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_article_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_comment
-- ----------------------------
DROP TABLE IF EXISTS `article_comment`;
CREATE TABLE `article_comment`  (
  `comment_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `comment_content` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `article_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `fk_article_comment_user`(`user_id`) USING BTREE,
  INDEX `fk_article_comment_article`(`article_id`) USING BTREE,
  CONSTRAINT `fk_article_comment_article` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_article_comment_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for artist
-- ----------------------------
DROP TABLE IF EXISTS `artist`;
CREATE TABLE `artist`  (
  `artist_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(10) UNSIGNED NOT NULL,
  `artist_type` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `artist_introduction` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`artist_id`) USING BTREE,
  INDEX `fk_artist_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_artist_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner`  (
  `banner_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `banner_link` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `banner_introduction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '该banner的状态',
  PRIMARY KEY (`banner_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for diary
-- ----------------------------
DROP TABLE IF EXISTS `diary`;
CREATE TABLE `diary`  (
  `diary_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `create_date` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `mood_id` int(10) UNSIGNED NULL DEFAULT NULL,
  `user_id` int(10) UNSIGNED NULL DEFAULT NULL,
  `diary_link` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`diary_id`) USING BTREE,
  INDEX `fk_diary_user`(`user_id`) USING BTREE,
  INDEX `fk_diary_mood`(`mood_id`) USING BTREE,
  CONSTRAINT `fk_diary_mood` FOREIGN KEY (`mood_id`) REFERENCES `mood` (`mood_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_diary_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for favorite_article
-- ----------------------------
DROP TABLE IF EXISTS `favorite_article`;
CREATE TABLE `favorite_article`  (
  `article_id` int(10) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`article_id`, `user_id`) USING BTREE,
  INDEX `fk_fav_article_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_fav_article_article` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_fav_article_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for favorite_program
-- ----------------------------
DROP TABLE IF EXISTS `favorite_program`;
CREATE TABLE `favorite_program`  (
  `user_id` int(10) UNSIGNED NOT NULL,
  `program_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`, `program_id`) USING BTREE,
  INDEX `fk_facorite_program_program`(`program_id`) USING BTREE,
  CONSTRAINT `fk_facorite_program_program` FOREIGN KEY (`program_id`) REFERENCES `program` (`program_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_favorite_program_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for follow_artist
-- ----------------------------
DROP TABLE IF EXISTS `follow_artist`;
CREATE TABLE `follow_artist`  (
  `user_id` int(10) UNSIGNED NOT NULL,
  `artist_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`, `artist_id`) USING BTREE,
  INDEX `fk_follow_artist_artist`(`artist_id`) USING BTREE,
  CONSTRAINT `fk_follow_artist_artist` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_follow_artist_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for meditation
-- ----------------------------
DROP TABLE IF EXISTS `meditation`;
CREATE TABLE `meditation`  (
  `meditation_id` int(11) NOT NULL,
  `date` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `duration` int(11) NULL DEFAULT NULL,
  `experimence` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mood_id` int(10) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `songlist_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`meditation_id`) USING BTREE,
  INDEX `fk_meditation_user`(`user_id`) USING BTREE,
  INDEX `fk_meditation_mood`(`mood_id`) USING BTREE,
  INDEX `fk_meditation_songlist`(`songlist_id`) USING BTREE,
  CONSTRAINT `fk_meditation_mood` FOREIGN KEY (`mood_id`) REFERENCES `mood` (`mood_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_meditation_songlist` FOREIGN KEY (`songlist_id`) REFERENCES `songlist` (`songlist_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_meditation_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mood
-- ----------------------------
DROP TABLE IF EXISTS `mood`;
CREATE TABLE `mood`  (
  `mood_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `mood_type` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mood_level` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`mood_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for program
-- ----------------------------
DROP TABLE IF EXISTS `program`;
CREATE TABLE `program`  (
  `program_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `program_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `category` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类',
  `program_introduction` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `radio_id` int(10) UNSIGNED NOT NULL,
  `upload_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `program_link` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `play_count` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '播放次数',
  `duration` time(0) NULL DEFAULT NULL COMMENT '节目时长',
  PRIMARY KEY (`program_id`) USING BTREE,
  INDEX `fk_program_radio`(`radio_id`) USING BTREE,
  CONSTRAINT `fk_program_radio` FOREIGN KEY (`radio_id`) REFERENCES `radio` (`radio_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for program_programlist
-- ----------------------------
DROP TABLE IF EXISTS `program_programlist`;
CREATE TABLE `program_programlist`  (
  `program_id` int(10) UNSIGNED NOT NULL,
  `programlist_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`program_id`, `programlist_id`) USING BTREE,
  INDEX `fk_program_programlist`(`programlist_id`) USING BTREE,
  CONSTRAINT `fk_program_programlist` FOREIGN KEY (`programlist_id`) REFERENCES `programlist` (`programlist_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_program_programlist_p` FOREIGN KEY (`program_id`) REFERENCES `program` (`program_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for programlist
-- ----------------------------
DROP TABLE IF EXISTS `programlist`;
CREATE TABLE `programlist`  (
  `programlist_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `programlist_introduction` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int(10) UNSIGNED NULL DEFAULT NULL,
  `cover_link` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面链接',
  PRIMARY KEY (`programlist_id`) USING BTREE,
  INDEX `fk_programlist_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_programlist_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for radio
-- ----------------------------
DROP TABLE IF EXISTS `radio`;
CREATE TABLE `radio`  (
  `radio_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `radio_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `radio_introduction` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `artist_id` int(10) UNSIGNED NOT NULL,
  `cover_link` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电台封面路径',
  PRIMARY KEY (`radio_id`) USING BTREE,
  INDEX `fk_radio_artist`(`artist_id`) USING BTREE,
  CONSTRAINT `fk_radio_artist` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for song
-- ----------------------------
DROP TABLE IF EXISTS `song`;
CREATE TABLE `song`  (
  `song_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `song_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `singer_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `song_introduction` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `song_link` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`song_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for song_songlist
-- ----------------------------
DROP TABLE IF EXISTS `song_songlist`;
CREATE TABLE `song_songlist`  (
  `song_id` int(10) UNSIGNED NOT NULL,
  `songlist_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`song_id`, `songlist_id`) USING BTREE,
  INDEX `fk_song_songlist_songlist`(`songlist_id`) USING BTREE,
  CONSTRAINT `fk_song_songlist_song` FOREIGN KEY (`song_id`) REFERENCES `song` (`song_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_song_songlist_songlist` FOREIGN KEY (`songlist_id`) REFERENCES `songlist` (`songlist_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for songlist
-- ----------------------------
DROP TABLE IF EXISTS `songlist`;
CREATE TABLE `songlist`  (
  `songlist_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `songlist_info` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `cover_link` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '歌单封面',
  PRIMARY KEY (`songlist_id`) USING BTREE,
  INDEX `fk_songlist_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_songlist_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for subscribe_radio
-- ----------------------------
DROP TABLE IF EXISTS `subscribe_radio`;
CREATE TABLE `subscribe_radio`  (
  `user_id` int(10) UNSIGNED NOT NULL,
  `radio_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`, `radio_id`) USING BTREE,
  INDEX `fk_subscribe_radio_radio`(`radio_id`) USING BTREE,
  CONSTRAINT `fk_subscribe_radio_radio` FOREIGN KEY (`radio_id`) REFERENCES `radio` (`radio_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_subscribe_radio_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(10) UNSIGNED NULL DEFAULT NULL,
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar_link` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_login
-- ----------------------------
DROP TABLE IF EXISTS `user_login`;
CREATE TABLE `user_login`  (
  `login_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(10) UNSIGNED NOT NULL,
  `identity_type` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'account' COMMENT '登录类型',
  `identifier` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `credential` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录令牌',
  `expire` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`login_id`) USING BTREE,
  INDEX `fk_user_login_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_user_login_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
