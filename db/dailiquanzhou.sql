-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.0.96-community-nt - MySQL Community Edition (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  8.2.0.4675
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 quanzhou 的数据库结构
CREATE DATABASE IF NOT EXISTS `quanzhou` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `quanzhou`;


-- 导出  表 quanzhou.admininfo 结构
CREATE TABLE IF NOT EXISTS `admininfo` (
  `id` int(11) NOT NULL auto_increment COMMENT '编号',
  `adminId` int(11) NOT NULL COMMENT '管理员Id',
  `headIcon` varchar(150) NOT NULL default 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0' COMMENT '头像',
  `sysType` int(11) NOT NULL COMMENT '管理员类型 0超级管理员 1普通管理员',
  `password` varchar(150) NOT NULL COMMENT '密码',
  `nickName` varchar(50) NOT NULL COMMENT '代理商姓名',
  `telephone` varchar(50) NOT NULL COMMENT '电话',
  `loginTime` varchar(50) NOT NULL COMMENT '登陆时间',
  `totalCards` int(11) NOT NULL default '0' COMMENT '总房卡数量',
  `surplusCards` int(11) NOT NULL default '0' COMMENT '剩余房卡数量',
  `gamerId` int(11) NOT NULL COMMENT '代理商的游戏ID',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='代理用户列表';

-- 正在导出表  quanzhou.admininfo 的数据：~12 rows (大约)
/*!40000 ALTER TABLE `admininfo` DISABLE KEYS */;
INSERT INTO `admininfo` (`id`, `adminId`, `headIcon`, `sysType`, `password`, `nickName`, `telephone`, `loginTime`, `totalCards`, `surplusCards`, `gamerId`) VALUES
	(1, 666, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', '小石潭记', '18589728089', '2017-04-28 17:35:30', 281, 281, 0),
	(2, 888, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 1, 'YWRtaW4=', '老人与海', '18589728089', '2017-04-28 17:50:55', 100, 100, 51800001),
	(3, 8888, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin', '18589728089', '2017-06-14 17:45:37', 100, 100, 51800003),
	(4, 1004, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin1', '18589728089', '2017-05-01 19:09:57', 100, 100, 51800005),
	(5, 1005, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin1', '18589728089', '2017-05-01 19:09:57', 100, 100, 51800007),
	(6, 1006, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin1', '18589728089', '2017-05-01 19:09:57', 100, 100, 51800009),
	(7, 1007, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin1', '18589728089', '2017-05-01 19:09:57', 100, 100, 51800011),
	(8, 1008, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin1', '18589728089', '2017-05-01 19:09:57', 100, 100, 51800013),
	(10, 1010, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin1', '18589728089', '2017-05-01 19:09:57', 100, 100, 51800014),
	(11, 1011, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin1', '18589728089', '2017-05-01 19:09:57', 100, 100, 51800015),
	(13, 1231, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 1, 'YWFh', '阿德', '15685485689', '2017-04-28 18:08:43', 1000, 1000, 51800016),
	(14, 6665, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 1, 'cmV3cndl', 'rerew', '15896589658', '2017-04-28 18:10:35', 2112, 1212, 51800017);
/*!40000 ALTER TABLE `admininfo` ENABLE KEYS */;


-- 导出  表 quanzhou.adminpayrecord 结构
CREATE TABLE IF NOT EXISTS `adminpayrecord` (
  `id` int(11) NOT NULL auto_increment COMMENT '编号',
  `adminId` int(11) NOT NULL COMMENT '代理商id',
  `payTime` varchar(50) NOT NULL COMMENT '购买时间',
  `payMoney` double NOT NULL COMMENT '购买的金额',
  `state` int(11) NOT NULL default '0' COMMENT '购买的状态（成功0、失败1、待处理2)',
  `payType` varchar(50) NOT NULL COMMENT '购买的类型',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='代理商购买记录列表';

-- 正在导出表  quanzhou.adminpayrecord 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `adminpayrecord` DISABLE KEYS */;
INSERT INTO `adminpayrecord` (`id`, `adminId`, `payTime`, `payMoney`, `state`, `payType`) VALUES
	(1, 12121, '2017-05-02 14:16:27', 100, 0, '100块'),
	(2, 6789, '2017-05-02 14:16:27', 500, 0, '100块'),
	(3, 12121, '2017-05-02 14:16:27', 100, 0, '100块'),
	(4, 1345, '2017-05-02 14:16:27', 600, 0, '100块'),
	(6, 666, '2017-05-09 14:03:02', 121, 0, '100块');
/*!40000 ALTER TABLE `adminpayrecord` ENABLE KEYS */;


-- 导出  表 quanzhou.agent 结构
CREATE TABLE IF NOT EXISTS `agent` (
  `id` int(11) NOT NULL default '0',
  `bili` double NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理比例';

-- 正在导出表  quanzhou.agent 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `agent` DISABLE KEYS */;
INSERT INTO `agent` (`id`, `bili`) VALUES
	(1, 0.2);
/*!40000 ALTER TABLE `agent` ENABLE KEYS */;


-- 导出  表 quanzhou.cardtype 结构
CREATE TABLE IF NOT EXISTS `cardtype` (
  `id` int(11) NOT NULL auto_increment COMMENT '编号',
  `typeName` varchar(50) NOT NULL COMMENT '类型',
  `cardMoney` double NOT NULL COMMENT '金额',
  `cardNum` int(11) NOT NULL COMMENT '房卡数',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='房卡类型';

-- 正在导出表  quanzhou.cardtype 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `cardtype` DISABLE KEYS */;
INSERT INTO `cardtype` (`id`, `typeName`, `cardMoney`, `cardNum`) VALUES
	(1, '100块', 100, 150),
	(2, '50块', 50, 60),
	(3, '600块', 600, 800);
/*!40000 ALTER TABLE `cardtype` ENABLE KEYS */;


-- 导出  表 quanzhou.consrecord 结构
CREATE TABLE IF NOT EXISTS `consrecord` (
  `id` int(11) NOT NULL auto_increment COMMENT '编号',
  `consMoney` double NOT NULL COMMENT '消费金额',
  `consType` varchar(50) NOT NULL COMMENT '消费类型',
  `consTime` varchar(50) NOT NULL COMMENT '消费时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消费记录列表';

-- 正在导出表  quanzhou.consrecord 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `consrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `consrecord` ENABLE KEYS */;


-- 导出  表 quanzhou.gamerecord 结构
CREATE TABLE IF NOT EXISTS `gamerecord` (
  `id` int(11) NOT NULL auto_increment COMMENT '唯一标识id',
  `roomId` bigint(20) NOT NULL COMMENT '房间ID',
  `juNum` int(11) NOT NULL COMMENT '局数',
  `type` varchar(50) default NULL COMMENT '游戏类型',
  `gamerId` bigint(20) NOT NULL COMMENT '总局数',
  `winPoints` int(11) NOT NULL COMMENT '总正积分',
  `nickName` varchar(512) NOT NULL COMMENT '玩家昵称',
  `endTime` varchar(50) NOT NULL COMMENT '结束时间',
  `replayId` varchar(50) NOT NULL COMMENT '回放ID',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8 COMMENT='玩家个人成绩表';

-- 正在导出表  quanzhou.gamerecord 的数据：~12 rows (大约)
/*!40000 ALTER TABLE `gamerecord` DISABLE KEYS */;
INSERT INTO `gamerecord` (`id`, `roomId`, `juNum`, `type`, `gamerId`, `winPoints`, `nickName`, `endTime`, `replayId`) VALUES
	(104, 800013, 1, 'qzmj', 51800100, 2, '玩家51800100', '2017-06-09 14:54:27', '80001312'),
	(105, 800013, 1, 'qzmj', 51800101, -2, '玩家51800101', '2017-06-09 14:54:27', '80001311'),
	(106, 800013, 1, 'qzmj', 51800099, -2, '玩家51800099', '2017-06-09 14:54:27', '80001313'),
	(107, 800013, 1, 'qzmj', 51800098, 2, '玩家51800098', '2017-06-09 14:54:27', '80001314'),
	(109, 800016, 1, 'qzmj', 51800125, 2, '玩家51800125', '2017-06-09 15:07:07', '80001612'),
	(110, 800016, 1, 'qzmj', 51800120, -2, '玩家51800120', '2017-06-09 15:07:07', '80001611'),
	(111, 800016, 1, 'qzmj', 51800123, -2, '玩家51800123', '2017-06-09 15:07:07', '80001613'),
	(112, 800016, 1, 'qzmj', 51800121, 2, '玩家51800121', '2017-06-09 15:07:07', '80001614'),
	(113, 800017, 1, 'qzmj', 51800133, 2, '玩家51800133', '2017-06-09 15:09:36', '80001712'),
	(114, 800017, 1, 'qzmj', 51800129, -2, '玩家51800129', '2017-06-09 15:09:36', '80001711'),
	(115, 800017, 1, 'qzmj', 51800131, -2, '玩家51800131', '2017-06-09 15:09:36', '80001713'),
	(116, 800017, 1, 'qzmj', 51800128, 2, '玩家51800128', '2017-06-09 15:09:36', '80001714');
/*!40000 ALTER TABLE `gamerecord` ENABLE KEYS */;


-- 导出  表 quanzhou.gamerinfo 结构
CREATE TABLE IF NOT EXISTS `gamerinfo` (
  `id` int(11) NOT NULL auto_increment COMMENT '唯一标识id',
  `openId` varchar(32) NOT NULL COMMENT '微信id',
  `nickName` varchar(128) NOT NULL COMMENT '昵称',
  `headFace` varchar(128) NOT NULL default 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0' COMMENT '头像',
  `ip` varchar(128) NOT NULL,
  `sex` int(11) default NULL COMMENT '性别,1男,2女',
  `totalCards` int(11) NOT NULL default '0' COMMENT '房卡总数',
  `surplusCards` int(11) NOT NULL default '0' COMMENT '剩余房卡数',
  `telephone` varchar(16) default NULL COMMENT '电话',
  `qqNum` varchar(16) default NULL COMMENT 'QQ',
  `enableLogin` int(1) default '0' COMMENT '是否可登录,默认为0可登陆,1为不可登录',
  `password` varchar(128) default NULL COMMENT '登录密码',
  `parentId` bigint(20) default NULL COMMENT '从谁的分享的链接进来的',
  `shareNum` int(11) default NULL COMMENT '分享的次数',
  `shareTime` varchar(50) default NULL COMMENT '分享的时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51800018 DEFAULT CHARSET=utf8 COMMENT='玩家信息表';

-- 正在导出表  quanzhou.gamerinfo 的数据：~11 rows (大约)
/*!40000 ALTER TABLE `gamerinfo` DISABLE KEYS */;
INSERT INTO `gamerinfo` (`id`, `openId`, `nickName`, `headFace`, `ip`, `sex`, `totalCards`, `surplusCards`, `telephone`, `qqNum`, `enableLogin`, `password`, `parentId`, `shareNum`, `shareTime`) VALUES
	(51800001, '51800001', '泉州客户', 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxJo8UgmxaGaTJ-j5yJJjFus.jpg', '127.0.0.1', 1, 1828, 1828, NULL, NULL, 0, NULL, NULL, NULL, NULL),
	(51800003, '51800003', '小石潭记', 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxNOeYNNw0rgDaPuJpQKnPwY.jpg', '127.0.0.1', 1, 10, 10, NULL, NULL, 0, NULL, 51800001, NULL, NULL),
	(51800005, '51800005', '不认识的玩家', 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxPJXQSyr7K0pU0N6MCnM2S8.jpg', '127.0.0.1', 1, 10, 10, NULL, NULL, 0, NULL, 51800001, NULL, NULL),
	(51800007, '51800007', '玩家51800007', 'http://192.168.75.1:8080/img/single-man.png', '127.0.0.1', 1, 10, 10, NULL, NULL, 0, NULL, 51800001, NULL, NULL),
	(51800009, '51800009', '玩家51800009', 'http://192.168.75.1:8080/img/single-man.png', '127.0.0.1', 1, 10, 10, NULL, NULL, 0, NULL, 51800003, NULL, NULL),
	(51800011, '51800011', '玩家51800011', 'http://192.168.75.1:8080/img/single-man.png', '127.0.0.1', 1, 10, 10, NULL, NULL, 0, NULL, 51800003, NULL, NULL),
	(51800013, '51800013', '玩家51800013', 'http://192.168.75.1:8080/img/single-man.png', '127.0.0.1', 1, 10, 10, NULL, NULL, 0, NULL, 51800003, NULL, NULL),
	(51800014, '51800003', '小石潭记', 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxNOeYNNw0rgDaPuJpQKnPwY.jpg', '127.0.0.1', 1, 10, 10, NULL, NULL, 0, NULL, 51800003, NULL, NULL),
	(51800015, '51800003', '小石潭记', 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxNOeYNNw0rgDaPuJpQKnPwY.jpg', '127.0.0.1', 1, 10, 10, NULL, NULL, 0, NULL, 51800003, NULL, NULL),
	(51800016, '51800003', '小石潭记', 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxNOeYNNw0rgDaPuJpQKnPwY.jpg', '127.0.0.1', 1, 10, 10, NULL, NULL, 0, NULL, 51800005, NULL, NULL),
	(51800017, '51800003', '小石潭记', 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxNOeYNNw0rgDaPuJpQKnPwY.jpg', '127.0.0.1', 1, 10, 10, NULL, NULL, 0, NULL, 51800005, NULL, NULL);
/*!40000 ALTER TABLE `gamerinfo` ENABLE KEYS */;


-- 导出  表 quanzhou.gamerpayrecord 结构
CREATE TABLE IF NOT EXISTS `gamerpayrecord` (
  `id` int(11) NOT NULL auto_increment COMMENT '编号',
  `payMoney` double NOT NULL COMMENT '充值金额',
  `payTime` varchar(50) NOT NULL COMMENT '充值时间',
  `adminId` int(11) NOT NULL COMMENT '充值代理商id',
  `headFace` varchar(128) NOT NULL COMMENT '充值玩家的头像',
  `gamerId` int(11) NOT NULL COMMENT '充值玩家的id',
  `payType` varchar(50) NOT NULL COMMENT '购买的类型',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='玩家充值列表';

-- 正在导出表  quanzhou.gamerpayrecord 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `gamerpayrecord` DISABLE KEYS */;
INSERT INTO `gamerpayrecord` (`id`, `payMoney`, `payTime`, `adminId`, `headFace`, `gamerId`, `payType`) VALUES
	(12, 1212, '2017-06-14 10:37:55', 8888, 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxJo8UgmxaGaTJ-j5yJJjFus.jpg', 51800001, '100块'),
	(13, 1212, '2017-06-14 10:37:55', 8888, 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxNOeYNNw0rgDaPuJpQKnPwY.jpg', 51800003, '100块'),
	(14, 1212, '2017-06-14 10:37:55', 8888, 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxPJXQSyr7K0pU0N6MCnM2S8.jpg', 51800005, '100块');
/*!40000 ALTER TABLE `gamerpayrecord` ENABLE KEYS */;


-- 导出  表 quanzhou.onlinepay 结构
CREATE TABLE IF NOT EXISTS `onlinepay` (
  `id` int(11) NOT NULL auto_increment,
  `gamerId` int(11) NOT NULL,
  `headFace` varchar(128) NOT NULL,
  `payType` varchar(50) NOT NULL,
  `payMoney` double NOT NULL,
  `state` int(11) NOT NULL COMMENT '0 未支付  1 支付成功',
  `payTime` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='在线充值记录';

-- 正在导出表  quanzhou.onlinepay 的数据：~9 rows (大约)
/*!40000 ALTER TABLE `onlinepay` DISABLE KEYS */;
INSERT INTO `onlinepay` (`id`, `gamerId`, `headFace`, `payType`, `payMoney`, `state`, `payTime`) VALUES
	(1, 518002, 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxJo8UgmxaGaTJ-j5yJJjFus.jpg', '10rmb', 10, 1, '2017-5-20 10:09:12'),
	(2, 518003, 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxPJXQSyr7K0pU0N6MCnM2S8.jpg', '10rmb', 10, 0, '2017-5-20 10:09:12'),
	(3, 518002, 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxNOeYNNw0rgDaPuJpQKnPwY.jpg', '10rmb', 10, 1, '2017-5-20 10:09:12'),
	(4, 518006, 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxPJXQSyr7K0pU0N6MCnM2S8.jpg', '10rmb', 50, 0, '2017-5-20 10:09:12'),
	(5, 518002, 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxPJXQSyr7K0pU0N6MCnM2S8.jpg', '10rmb', 10, 0, '2017-5-20 10:09:12'),
	(6, 518002, 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxPJXQSyr7K0pU0N6MCnM2S8.jpg', '10rmb', 40, 1, '2017-5-20 10:09:12'),
	(7, 518004, 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxPJXQSyr7K0pU0N6MCnM2S8.jpg', '10rmb', 10, 0, '2017-5-20 10:09:12'),
	(8, 518002, 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxPJXQSyr7K0pU0N6MCnM2S8.jpg', '10rmb', 10, 1, '2017-5-20 10:09:12'),
	(9, 51800003, 'http://www.uitrs.com/quanzhou/img/faceicons/oXe8AxPJXQSyr7K0pU0N6MCnM2S8.jpg', '100块', 0.01, 0, '2017-06-14 09:55:15');
/*!40000 ALTER TABLE `onlinepay` ENABLE KEYS */;


-- 导出  表 quanzhou.profit 结构
CREATE TABLE IF NOT EXISTS `profit` (
  `id` int(11) NOT NULL auto_increment,
  `winnerId` bigint(20) NOT NULL COMMENT '获利的玩家ID',
  `profit` double NOT NULL default '0' COMMENT '获利',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='获利表';

-- 正在导出表  quanzhou.profit 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `profit` DISABLE KEYS */;
INSERT INTO `profit` (`id`, `winnerId`, `profit`) VALUES
	(2, 51800002, 20),
	(3, 51800003, 50),
	(4, 51800003, 50),
	(5, 51800003, 50),
	(6, 51800001, 10),
	(8, 51800003, 50),
	(10, 51800003, 50),
	(11, 51800003, 50),
	(12, 51800003, 50),
	(13, 51800003, 50);
/*!40000 ALTER TABLE `profit` ENABLE KEYS */;


-- 导出  表 quanzhou.replayinfo 结构
CREATE TABLE IF NOT EXISTS `replayinfo` (
  `id` int(11) NOT NULL auto_increment,
  `replayId` int(11) NOT NULL COMMENT '回放ID',
  `record` varchar(20480) NOT NULL COMMENT '回放消息',
  `type` varchar(50) NOT NULL COMMENT '游戏类型',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  quanzhou.replayinfo 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `replayinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `replayinfo` ENABLE KEYS */;


-- 导出  表 quanzhou.roominfo 结构
CREATE TABLE IF NOT EXISTS `roominfo` (
  `id` int(11) NOT NULL auto_increment COMMENT '唯一标识id',
  `roomId` int(11) NOT NULL COMMENT '房间号',
  `gamerOneId` int(11) NOT NULL COMMENT '玩家1 ID',
  `gamerTwoId` int(11) NOT NULL COMMENT '玩家2 ID',
  `gamerThreeId` int(11) NOT NULL COMMENT '玩家3 ID',
  `gamerFourId` int(11) NOT NULL COMMENT '玩家4 ID',
  `createTime` varchar(50) NOT NULL COMMENT '创建时间',
  `endTime` varchar(50) default NULL COMMENT '结束时间',
  `useRounds` int(11) default NULL COMMENT '已使用局数',
  `totalRounds` int(11) NOT NULL COMMENT '总局数',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房间信息表';

-- 正在导出表  quanzhou.roominfo 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `roominfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `roominfo` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
