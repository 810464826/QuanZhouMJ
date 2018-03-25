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
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='代理用户列表';

-- 正在导出表  quanzhou.admininfo 的数据：~16 rows (大约)
/*!40000 ALTER TABLE `admininfo` DISABLE KEYS */;
INSERT INTO `admininfo` (`id`, `adminId`, `headIcon`, `sysType`, `password`, `nickName`, `telephone`, `loginTime`, `totalCards`, `surplusCards`) VALUES
	(1, 666, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', '小石潭记', '18589728089', '2017-04-28 17:35:30', 281, 281),
	(2, 888, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 1, 'YWRtaW4=', '老人与海', '18589728089', '2017-04-28 17:50:55', 100, 100),
	(3, 8888, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin', '18589728089', '2017-05-02 14:41:26', 100, 100),
	(4, 1004, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin1', '18589728089', '2017-05-01 19:09:57', 100, 100),
	(5, 1005, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin1', '18589728089', '2017-05-01 19:09:57', 100, 100),
	(6, 1006, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin1', '18589728089', '2017-05-01 19:09:57', 100, 100),
	(7, 1007, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin1', '18589728089', '2017-05-01 19:09:57', 100, 100),
	(8, 1008, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin1', '18589728089', '2017-05-01 19:09:57', 100, 100),
	(10, 1010, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin1', '18589728089', '2017-05-01 19:09:57', 100, 100),
	(11, 1011, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'YWRtaW4=', 'admin1', '18589728089', '2017-05-01 19:09:57', 100, 100),
	(13, 1231, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 1, 'YWFh', '阿德', '15685485689', '2017-04-28 18:08:43', 1000, 1000),
	(14, 6665, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 1, 'cmV3cndl', 'rerew', '15896589658', '2017-04-28 18:10:35', 2112, 1212),
	(15, 3334, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 1, 'cXFx', '111', '15869586841', '2017-05-09 09:29:19', 158, 118),
	(16, 333, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'cXFx', '1234', '15869586841', '2017-05-11 14:08:57', 64821509, 64821471),
	(17, 6, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'cXFx', 'qqq', '12312313131', '2017-05-11 14:21:59', 21312, 31231),
	(18, 7, 'http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0', 0, 'MzIzMjM=', '1233232', '14543232322', '2017-05-09 10:06:08', 213123, 312312);
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
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 COMMENT='玩家个人成绩表';

-- 正在导出表  quanzhou.gamerecord 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `gamerecord` DISABLE KEYS */;
INSERT INTO `gamerecord` (`id`, `roomId`, `juNum`, `type`, `gamerId`, `winPoints`, `nickName`, `endTime`) VALUES
	(84, 80001, 8, '三缺一', 52001, 77, '小石潭记1', '2017-05-02 14:16:27'),
	(85, 80001, 8, '三缺一', 52002, 77, '小石潭记2', '2017-05-02 14:16:27'),
	(86, 80001, 8, '三缺一', 52003, 77, '小石潭记3', '2017-05-02 14:16:27'),
	(87, 80001, 8, '三缺一', 52004, 77, '小石潭记4', '2017-05-02 14:16:27');
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
  `parentId` int(11) default NULL COMMENT '从谁的分享的链接进来的',
  `shareNum` int(11) default NULL COMMENT '分享的次数',
  `shareTime` varchar(50) default NULL COMMENT '分享的时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='玩家信息表';

-- 正在导出表  quanzhou.gamerinfo 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `gamerinfo` DISABLE KEYS */;
INSERT INTO `gamerinfo` (`id`, `openId`, `nickName`, `headFace`, `ip`, `sex`, `totalCards`, `surplusCards`, `telephone`, `qqNum`, `enableLogin`, `password`, `parentId`, `shareNum`, `shareTime`) VALUES
	(1, 'oXe8AxI4g8-VAib3N-6rugUvWlcU', '小石潭记', '', '123.', 0, 2051, 2001, '15869586587', '81064826', 0, 'qqqq', 1, 2, '2017-05-02 14:16:27'),
	(2, 'oXe8AxI4g8-VAib3N-6rugUvWlcU', '小石潭记1', '', '123.', 1, 149, 99, '15869586587', '81064826', 1, 'qqqq', 2, 3, '2017-05-02 14:16:27'),
	(3, 'oXe8AxI4g8-VAib3N-6rugUvWlcU', '小石潭记2', '', '123.', 1, 100, 50, '15869586587', '81064826', 1, 'qqqq', 3, 4, '2017-05-02 14:16:27'),
	(4, 'oXe8AxI4g8-VAib3N-6rugUvWlcU', '小石潭记3', '', '123.', 0, 100, 50, '15869586587', '81064826', 0, 'qqqq', 4, 5, '2017-05-02 14:16:27'),
	(5, 'oXe8AxI4g8-VAib3N-6rugUvWlcU', '小石潭记4', '', '123.', 1, 100, 50, '15869586587', '81064826', 1, 'qqqq', 5, 6, '2017-05-02 14:16:27');
/*!40000 ALTER TABLE `gamerinfo` ENABLE KEYS */;


-- 导出  表 quanzhou.gamerpayrecord 结构
CREATE TABLE IF NOT EXISTS `gamerpayrecord` (
  `id` int(11) NOT NULL auto_increment COMMENT '编号',
  `payMoney` double NOT NULL COMMENT '充值金额',
  `payTime` varchar(50) NOT NULL COMMENT '充值时间',
  `adminId` int(11) NOT NULL COMMENT '充值代理商id',
  `gamerId` int(11) NOT NULL COMMENT '充值玩家的id',
  `payType` varchar(50) NOT NULL COMMENT '购买的类型',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='玩家充值列表';

-- 正在导出表  quanzhou.gamerpayrecord 的数据：~6 rows (大约)
/*!40000 ALTER TABLE `gamerpayrecord` DISABLE KEYS */;
INSERT INTO `gamerpayrecord` (`id`, `payMoney`, `payTime`, `adminId`, `gamerId`, `payType`) VALUES
	(2, 500, '2017-05-02 14:16:27', 2121, 52001, '50块'),
	(3, 500, '2017-05-02 14:16:27', 2121, 52002, '50块'),
	(4, 500, '2017-05-02 14:16:27', 2121, 52003, '50块'),
	(7, 100, '2017-05-03 18:35:56', 16, 1, '100块'),
	(8, 1, '2017-05-08 14:21:40', 3334, 1, '100块'),
	(9, 1, '2017-05-08 14:21:48', 3334, 2, '100块');
/*!40000 ALTER TABLE `gamerpayrecord` ENABLE KEYS */;


-- 导出  表 quanzhou.onlinepay 结构
CREATE TABLE IF NOT EXISTS `onlinepay` (
  `id` int(11) NOT NULL auto_increment,
  `gamerId` int(11) NOT NULL,
  `payType` varchar(50) NOT NULL,
  `payMoney` double NOT NULL,
  `state` int(11) NOT NULL COMMENT '0 未支付  1 支付成功',
  `payTime` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='在线充值记录';

-- 正在导出表  quanzhou.onlinepay 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `onlinepay` DISABLE KEYS */;
INSERT INTO `onlinepay` (`id`, `gamerId`, `payType`, `payMoney`, `state`, `payTime`) VALUES
	(20, 1, '100块', 333, 0, '2017-05-11 16:34:52');
/*!40000 ALTER TABLE `onlinepay` ENABLE KEYS */;


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
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8 COMMENT='房间信息表';

-- 正在导出表  quanzhou.roominfo 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `roominfo` DISABLE KEYS */;
INSERT INTO `roominfo` (`id`, `roomId`, `gamerOneId`, `gamerTwoId`, `gamerThreeId`, `gamerFourId`, `createTime`, `endTime`, `useRounds`, `totalRounds`) VALUES
	(1, 80002, 12, 13, 14, 15, '2017-05-02 03:00:00', '2017-05-02 05:00:00', 6, 8),
	(112, 80003, 342, 234, 654, 15654, '2017-05-01 20:00:00', '2017-05-02 11:00:00', 6, 8),
	(113, 80004, 12, 13, 14, 15, '2017-05-01 15:00:00', '2017-05-02 09:00:00', 6, 8),
	(114, 80005, 43, 53, 23, 45, '2017-05-02 06:00:00', '2017-05-02 00:00:00', 6, 8);
/*!40000 ALTER TABLE `roominfo` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
