-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.30 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  8.2.0.4675
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 mahjong.admininfo 结构
CREATE TABLE IF NOT EXISTS `admininfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识id',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `sysType` int(1) NOT NULL COMMENT '类型,0为超级管理员,1为普通管理员',
  `enableLogin` int(1) NOT NULL COMMENT '是否可登录,默认为0可登陆,1为不可登录',
  `telephone` varchar(16) DEFAULT NULL COMMENT '电话',
  `loginIp` varchar(32) DEFAULT NULL COMMENT '登录ip',
  `loginTime` date DEFAULT NULL COMMENT '登录时间',
  `totalCards` int(11) DEFAULT '0' COMMENT '总房卡数',
  `surplusCards` int(11) DEFAULT '0' COMMENT '剩余房卡数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1013 DEFAULT CHARSET=utf8 COMMENT='管理员信息表';

-- 正在导出表  mahjong.admininfo 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `admininfo` DISABLE KEYS */;
INSERT INTO `admininfo` (`id`, `username`, `password`, `sysType`, `enableLogin`, `telephone`, `loginIp`, `loginTime`, `totalCards`, `surplusCards`) VALUES
	(1010, 'admin', 'YWRtaW4=', 0, 0, '18589728089', '0:0:0:0:0:0:0:1', '2017-04-26', 0, 0),
	(1011, 'A1001', 'MTExMTEx', 1, 0, '15522223333', '192.168.0.114', '2017-04-26', 0, 67),
	(1012, 'A1002', 'MTExMTEx', 1, 0, NULL, NULL, NULL, 0, 100);
/*!40000 ALTER TABLE `admininfo` ENABLE KEYS */;


-- 导出  表 mahjong.gamerinfo 结构
CREATE TABLE IF NOT EXISTS `gamerinfo` (
  `id` int(11) NOT NULL COMMENT '唯一标识id',
  `openId` varchar(32) NOT NULL COMMENT '微信id',
  `nickName` varchar(128) NOT NULL COMMENT '昵称',
  `headFace` varchar(128) NOT NULL COMMENT '头像',
  `sex` int(11) NOT NULL DEFAULT '1' COMMENT '性别,1男,2女',
  `ip` varchar(50) NOT NULL DEFAULT '127.0.0.1' COMMENT 'ip地址',
  `totalCards` int(11) NOT NULL DEFAULT '3' COMMENT '房卡总数',
  `surplusCards` int(11) NOT NULL DEFAULT '3' COMMENT '剩余房卡数',
  `telephone` varchar(16) DEFAULT NULL COMMENT '电话',
  `qqNum` varchar(16) DEFAULT NULL COMMENT 'QQ',
  `enableLogin` int(1) NOT NULL DEFAULT '0' COMMENT '是否可登录,默认为0可登陆,1为不可登录',
  `password` varchar(128) DEFAULT NULL COMMENT '登录密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='玩家信息表';

-- 正在导出表  mahjong.gamerinfo 的数据：~19 rows (大约)
/*!40000 ALTER TABLE `gamerinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `gamerinfo` ENABLE KEYS */;


-- 导出  表 mahjong.gamerrecord 结构
CREATE TABLE IF NOT EXISTS `gamerrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识id',
  `gamerId` int(11) NOT NULL COMMENT '玩家ID',
  `totalRounds` int(11) NOT NULL COMMENT '总局数',
  `winPoints` int(11) NOT NULL COMMENT '总正积分',
  `lostPoints` int(11) DEFAULT NULL COMMENT '总负积分',
  `winRounds` int(11) NOT NULL COMMENT '胜利局数',
  `bestWin` int(11) NOT NULL COMMENT '最佳战绩',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='玩家个人成绩表';

-- 正在导出表  mahjong.gamerrecord 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `gamerrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `gamerrecord` ENABLE KEYS */;


-- 导出  表 mahjong.paycards 结构
CREATE TABLE IF NOT EXISTS `paycards` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识id',
  `typeName` varchar(128) NOT NULL COMMENT '类型名称',
  `payNum` double NOT NULL COMMENT '充值金额',
  `payCards` int(11) NOT NULL COMMENT '房卡数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='房卡销售配置表';

-- 正在导出表  mahjong.paycards 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `paycards` DISABLE KEYS */;
INSERT INTO `paycards` (`id`, `typeName`, `payNum`, `payCards`) VALUES
	(1, '10', 10, 10),
	(2, '20', 20, 22),
	(3, '20', 20, 20);
/*!40000 ALTER TABLE `paycards` ENABLE KEYS */;


-- 导出  表 mahjong.payrecord 结构
CREATE TABLE IF NOT EXISTS `payrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识id',
  `gamerId` int(11) NOT NULL COMMENT '玩家ID',
  `openId` varchar(32) NOT NULL COMMENT '微信ID',
  `payNum` int(11) NOT NULL COMMENT '充值金额',
  `payTime` datetime NOT NULL COMMENT '充值时间',
  `adminId` int(11) NOT NULL COMMENT '管理员ID',
  `gameType` varchar(50) NOT NULL COMMENT '充值游戏',
  `typeName` varchar(50) NOT NULL COMMENT '充值类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='充值记录表';

-- 正在导出表  mahjong.payrecord 的数据：~6 rows (大约)
/*!40000 ALTER TABLE `payrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `payrecord` ENABLE KEYS */;


-- 导出  表 mahjong.roominfo 结构
CREATE TABLE IF NOT EXISTS `roominfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识id',
  `roomId` int(11) NOT NULL COMMENT '房间号',
  `gamerOneId` int(11) NOT NULL COMMENT '玩家1 ID',
  `gamerTwoId` int(11) NOT NULL COMMENT '玩家2 ID',
  `gamerThreeId` int(11) NOT NULL COMMENT '玩家3 ID',
  `gamerFourId` int(11) NOT NULL COMMENT '玩家4 ID',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `endTime` datetime DEFAULT NULL COMMENT '结束时间',
  `typeName` varchar(50) DEFAULT NULL COMMENT '房间类型',
  `useRounds` int(11) DEFAULT NULL COMMENT '已使用局数',
  `totalRounds` int(11) NOT NULL COMMENT '总局数',
  `oneScore` int(11) DEFAULT NULL COMMENT '玩家1分数',
  `twoScore` int(11) DEFAULT NULL COMMENT '玩家2分数',
  `thressScore` int(11) DEFAULT NULL COMMENT '玩家3分数',
  `fourScore` int(11) DEFAULT NULL COMMENT '玩家4分数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=331 DEFAULT CHARSET=utf8 COMMENT='房间信息表';

-- 正在导出表  mahjong.roominfo 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `roominfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `roominfo` ENABLE KEYS */;


-- 导出  表 mahjong.roomrecord 结构
CREATE TABLE IF NOT EXISTS `roomrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识id',
  `roomId` bigint(20) NOT NULL COMMENT '房间ID',
  `juNum` int(11) NOT NULL COMMENT '局数',
  `type` varchar(50) DEFAULT NULL COMMENT '游戏类型',
  `gamerId` bigint(20) NOT NULL COMMENT '玩家openID',
  `winPoints` int(11) NOT NULL COMMENT '总正积分',
  `nickName` varchar(512) NOT NULL COMMENT '玩家昵称',
  `endTime` datetime NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=935 DEFAULT CHARSET=utf8 COMMENT='玩家个人成绩表';

-- 正在导出表  mahjong.roomrecord 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `roomrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `roomrecord` ENABLE KEYS */;


-- 导出  表 mahjong.sysnotice 结构
CREATE TABLE IF NOT EXISTS `sysnotice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识id',
  `noticeType` varchar(50) NOT NULL COMMENT '公告类型',
  `noticeContent` varchar(512) NOT NULL COMMENT '公告内容',
  `payTime` date NOT NULL COMMENT '创建时间',
  `adminId` int(11) NOT NULL COMMENT '管理员ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统公告';

-- 正在导出表  mahjong.sysnotice 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sysnotice` DISABLE KEYS */;
/*!40000 ALTER TABLE `sysnotice` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
