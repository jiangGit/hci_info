-- =============================================================================
-- Diagram Name: Noname2
-- Created on: 2012/9/25 11:50:38
-- Diagram Version: 
-- =============================================================================
SET FOREIGN_KEY_CHECKS=0;

-- Drop table project
DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `detail` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin,
  `state_date` date,
  `end_date` date,
  `state` int(11),
  `is_public` tinyint(4) NOT NULL,
  `department_id` int(11),
  PRIMARY KEY(`id`),
  INDEX `department_id`(`department_id`)
)
ENGINE=MYISAM
ROW_FORMAT=dynamic
CHARACTER SET utf8 
COLLATE utf8_bin ;

-- Drop table member_extend
DROP TABLE IF EXISTS `member_extend`;

CREATE TABLE `member_extend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sex` tinyint(4) NOT NULL,
  `familyaddress` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin,
  `nativeplace` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin,
  `folk` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin,
  `card_id` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin,
  `homepage` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin,
  `birthday` date,
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin,
  `mobile` varchar(13) CHARACTER SET utf8 COLLATE utf8_bin,
  `mobileshort` varchar(6) CHARACTER SET utf8 COLLATE utf8_bin,
  `grade` int(11),
  `academy` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin,
  `major` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin,
  PRIMARY KEY(`id`)
)
ENGINE=MYISAM
ROW_FORMAT=dynamic
CHARACTER SET utf8 
COLLATE utf8_bin ;

-- Drop table api
DROP TABLE IF EXISTS `api`;

CREATE TABLE `api` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `note` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin,
  `state` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY(`id`),
  UNIQUE INDEX `key`(`api_key`)
)
ENGINE=MYISAM
COMMENT = 'API'
ROW_FORMAT=dynamic
CHARACTER SET utf8 
COLLATE utf8_bin ;

-- Drop table department
DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `detail` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin,
  PRIMARY KEY(`id`)
)
ENGINE=MYISAM
ROW_FORMAT=dynamic
CHARACTER SET utf8 
COLLATE utf8_bin ;

-- Drop table member
DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'Ñ§ºÅ',
  `name` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `type` int(11),
  `state` int(11),
  `role` int(11),
  `is_public` tinyint(4) NOT NULL,
  `extend_id` int(11) NOT NULL,
  PRIMARY KEY(`id`),
  UNIQUE INDEX `student_id`(`student_id`),
  UNIQUE INDEX `extend`(`extend_id`)
)
ENGINE=MYISAM
ROW_FORMAT=dynamic
CHARACTER SET utf8 
COLLATE utf8_bin ;

-- Drop table project_member
DROP TABLE IF EXISTS `project_member`;

CREATE TABLE `project_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `job` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'Ö°Ôð',
  `type` int(11) NOT NULL,
  PRIMARY KEY(`id`),
  UNIQUE INDEX `key`(`project_id`, `member_id`),
  INDEX `project_id`(`project_id`),
  INDEX `member_id`(`member_id`)
)
ENGINE=MYISAM
ROW_FORMAT=dynamic
CHARACTER SET utf8 
COLLATE utf8_bin ;

-- Drop table department_member
DROP TABLE IF EXISTS `department_member`;

CREATE TABLE `department_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY(`id`),
  UNIQUE INDEX `key`(`department_id`, `member_id`),
  INDEX `department_id`(`department_id`),
  INDEX `member_id`(`member_id`)
)
ENGINE=MYISAM
CHARACTER SET utf8 
COLLATE utf8_bin ;

-- Drop table member_password
DROP TABLE IF EXISTS `member_password`;

CREATE TABLE `member_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY(`id`),
  UNIQUE INDEX `member_id`(`member_id`)
)
ENGINE=MYISAM
ROW_FORMAT=dynamic
CHARACTER SET utf8 
COLLATE utf8_bin ;

SET FOREIGN_KEY_CHECKS=1;
