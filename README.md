# bilibili-counterfeit

## MySQL Details
### Get Details of a Table
```
>> SHOW CREATE TABLE t_user;
>> SHOW TABLE STATUS WHERE NAME = 't_user';
```

### Character Set & Collation
#### utf8mb4_0900_ai_ci
utf8mb4_0900_ai_ci is a character set and collation in the MySQL database, where:
- __utf8mb4:__ This is a character set in MySQL that supports the full range of Unicode characters, including emojis and other supplementary characters. It is an extension of the utf8 character set, which only supports a subset of Unicode characters. The utf8mb4 character set is commonly used to handle text data that may contain a wide range of characters from different languages and symbols.
- __0900:__ This is the version number of the collation. In this case, 0900 refers to Unicode collation version 9.0.0. MySQL uses Unicode collation versions to handle sorting and comparison of characters based on Unicode rules.
- __ai:__ accent-insensitive 
- __ci:__ case-insensitive

## Database Setup
### Create Database
```
CREATE DATABASE bilibili;

SHOW DATABASES;

USE bilibili;
```

### Data Management
```
>> DELETE FROM t_user WHERE PHONE = '3411477';
```

### MyBatis Mapper Template
```
@Mapper
public interface UserDao {
    User getUserByPhone(String phone);
}
```

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD MAPPER 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.syh.bilibili.dao.UserDao">
    <select id="getUserByPhone" parameterType="java.lang.String" resultType="com.syh.bilibili.domain.User">
        SELECT
            *
        FROM
            t_user
        WHERE
            phone = #{phone}
    </select>
</mapper>
```

### Table SQL
```
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for t_auth_element_operation
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_element_operation`;
CREATE TABLE `t_auth_element_operation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'primary key id',
  `elementName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'page element name',
  `elementCode` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'page element unique encoding',
  `operationType` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'operation type: [0 - clickable]  [1 - visible]',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  `updateTime` datetime DEFAULT NULL COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='access control - page element operation table';


-- ----------------------------
-- Table structure for t_auth_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_menu`;
CREATE TABLE `t_auth_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'primary key id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'menu project name',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'unique encoding',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  `updateTime` datetime DEFAULT NULL COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='access control - page access table';


-- ----------------------------
-- Table structure for t_auth_role
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role`;
CREATE TABLE `t_auth_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'primary key id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'role name',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'role code',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  `updateTime` datetime DEFAULT NULL COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='access control - role table';


-- ----------------------------
-- Table structure for t_auth_role_element_operation
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role_element_operation`;
CREATE TABLE `t_auth_role_element_operation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'primary key id',
  `roleId` bigint DEFAULT NULL COMMENT 'role id',
  `elementOperationId` bigint DEFAULT NULL COMMENT 'element operation id',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='access control - role and elment operation association table';


-- ----------------------------
-- Table structure for t_auth_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role_menu`;
CREATE TABLE `t_auth_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'primary key id',
  `roleId` bigint DEFAULT NULL COMMENT 'role id',
  `menuId` bigint DEFAULT NULL COMMENT 'page menu id',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='access control - role and page menu association table';


-- ----------------------------
-- Table structure for t_following_group
-- ----------------------------
DROP TABLE IF EXISTS `t_following_group`;
CREATE TABLE `t_following_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT 'following group',
  `type` varchar(5) DEFAULT NULL COMMENT 'following group category: [0 - special]  [1 - silent] [2 - default] [3 - customized]',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  `updateTime` datetime DEFAULT NULL COMMENT 'update time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='user following group';


-- ----------------------------
-- Table structure for t_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `t_refresh_token`;
CREATE TABLE `t_refresh_token` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'primary key id',
  `userId` bigint DEFAULT NULL COMMENT 'user id',
  `refreshToken` varchar(500) DEFAULT NULL COMMENT 'refresh token',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='refresh table record table';


-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'primary key ID',
  `phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'phone number',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'email address',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'password',
  `salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'password salt',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  `updateTime` datetime DEFAULT NULL COMMENT 'update time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Table that stores information about registered users';


-- ----------------------------
-- Table structure for t_user_following
-- ----------------------------
DROP TABLE IF EXISTS `t_user_following`;
CREATE TABLE `t_user_following` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'primary key id',
  `userId` bigint DEFAULT NULL COMMENT 'user id',
  `followingId` int DEFAULT NULL COMMENT 'followed user id',
  `groupId` int DEFAULT NULL COMMENT 'follow group id',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='user follow table';


-- ----------------------------
-- Table structure for t_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'primary key id',
  `userId` bigint DEFAULT NULL COMMENT 'user id',
  `nickname` varchar(100) DEFAULT NULL COMMENT 'nickname',
  `avatar` varchar(255) DEFAULT NULL COMMENT 'avatar',
  `sign` text COMMENT 'sign',
  `gender` varchar(10) DEFAULT NULL COMMENT 'gender',
  `birthday` varchar(20) DEFAULT NULL COMMENT 'birthday',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  `updateTime` datetime DEFAULT NULL COMMENT 'update time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ----------------------------
-- Table structure for t_user_moments
-- ----------------------------
DROP TABLE IF EXISTS `t_user_moments`;
CREATE TABLE `t_user_moments` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'primary key id',
  `userId` bigint DEFAULT NULL COMMENT 'user id',
  `type` varchar(5) DEFAULT NULL COMMENT 'moment type: 0 video, 1 live stream, 2 column moment',
  `contentId` bigint DEFAULT NULL COMMENT 'content details id',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  `updateTime` datetime DEFAULT NULL COMMENT 'update time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='user moments table';


-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint DEFAULT NULL COMMENT 'user id',
  `roleId` bigint DEFAULT NULL COMMENT 'role id',
  `createTime` datetime DEFAULT NULL COMMENT 'create time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='user role association table';


SET FOREIGN_KEY_CHECKS = 1;
```

## Hot Deployment in Intellij
- Preferences --> Compiler --> Build project automatically  
- [ Command + Option + Shift + / ] --> Registry  
  Select _compiler.automake.allow.when.app.running_ & _compiler.document.save.enabled_  
- In Run/Debug configuration, check 'On Update action' and 'On frame deactivation', make sure they are 'Update classes and resources'  
- Add maven dependency spring-boot-devtools  
- Add configuration __spring.devtools.restart.enabled=true__ in application.properties

## Login Authentication
User authentication based on Session & Cookie

User authentication based on JWT   
JWT - Json Web Token  
JWT = header + payload + signature

Note:  
Difference between local storage and cookie  

Web storage = Local storage + Session storage

## Spring Notes
### Request
```
ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
String token = requestAttributes.getRequest().getHeader("token");
```
