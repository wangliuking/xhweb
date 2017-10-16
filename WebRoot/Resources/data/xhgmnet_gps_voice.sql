-- phpMyAdmin SQL Dump
-- version 3.4.10.1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2017 年 10 月 16 日 04:49
-- 服务器版本: 5.5.20
-- PHP 版本: 5.3.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `xhgmnet_gps_voice`
--

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_bs_call_detail`
--

DROP TABLE IF EXISTS `xhgmnet_bs_call_detail`;
CREATE TABLE IF NOT EXISTS `xhgmnet_bs_call_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `startTime` datetime NOT NULL COMMENT '测量开始时间',
  `bsId` int(11) NOT NULL COMMENT '交换中心ID',
  `groupCallNumber` int(11) NOT NULL COMMENT '组呼个数',
  `groupCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '组呼持续时间',
  `singleCallNumber` int(11) NOT NULL COMMENT '个呼个数',
  `singleCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '个呼持续时间',
  `emergentCallTotals` int(11) NOT NULL COMMENT '紧急呼叫个数',
  `emergentCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '紧急呼叫持续时间',
  `phoneCallTotals` int(11) NOT NULL DEFAULT '0' COMMENT '电话呼叫个数',
  `phoneCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '电话呼叫持续时间 ',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='基站话务详情' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_bs_call_totals`
--

DROP TABLE IF EXISTS `xhgmnet_bs_call_totals`;
CREATE TABLE IF NOT EXISTS `xhgmnet_bs_call_totals` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `startTime` datetime NOT NULL COMMENT '测量开始时间',
  `bsId` int(11) NOT NULL COMMENT '基站ID',
  `activityCallTotals` int(11) NOT NULL COMMENT '活动呼叫总数',
  `activityCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '活动呼叫总持续时间',
  `averageCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '平均呼叫持续时间',
  `pttTotals` int(11) NOT NULL COMMENT '总PTT数',
  `waitTotals` int(11) NOT NULL COMMENT '排队数量',
  `waitTime` varchar(10) CHARACTER SET utf8mb4 NOT NULL COMMENT '排队持续时间',
  `maxRegisterGroup` int(11) NOT NULL DEFAULT '0' COMMENT '最大组注册数',
  `maxRegisterUser` int(11) NOT NULL COMMENT '最大用户注册数',
  `ccUpMax` int(11) NOT NULL COMMENT '控制信道占用率上行最大值',
  `ccUpMin` int(11) NOT NULL COMMENT '控制信道占用率上行最大值',
  `ccUpAverage` int(11) NOT NULL COMMENT '控制信道占用率上行平均值 ',
  `tcRate` int(11) NOT NULL COMMENT '业务信道占用率',
  `ccDownMax` int(11) NOT NULL COMMENT '控制信道占用率下行最大值',
  `ccDownMin` int(11) NOT NULL COMMENT '控制信道占用率下行最小值',
  `ccDownAverage` int(11) NOT NULL COMMENT '控制信道占用率下行最小值',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='基站话务总量' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_calllist01`
--

DROP TABLE IF EXISTS `xhgmnet_calllist01`;
CREATE TABLE IF NOT EXISTS `xhgmnet_calllist01` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Call_Time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '呼叫起始时间',
  `Call_id` varchar(40) NOT NULL COMMENT '呼叫流水号',
  `Call_sys_id` tinyint(1) NOT NULL COMMENT '0=Motrola；1=eTra；',
  `Use_Time` int(11) NOT NULL DEFAULT '0' COMMENT '呼叫用时',
  `Caller` varchar(10) NOT NULL COMMENT '主叫号码',
  `Called` varchar(10) NOT NULL COMMENT '被叫号码',
  `Call_Type` int(2) NOT NULL DEFAULT '0' COMMENT '呼叫类型0~9',
  `Call_TS_Id` varchar(20) DEFAULT NULL COMMENT '接入基站id',
  `Call_BS_BS` varchar(20) DEFAULT NULL COMMENT '接入载频id',
  `Call_RSSI` varchar(10) DEFAULT NULL COMMENT '接入场强',
  `Call_Result` varchar(10) DEFAULT NULL COMMENT '结束原因',
  `Call_Path` varchar(100) DEFAULT NULL COMMENT '音频地址',
  PRIMARY KEY (`id`),
  KEY `Call_id` (`Call_id`),
  KEY `Call_Time` (`Call_Time`),
  KEY `Caller` (`Caller`),
  KEY `Called` (`Called`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='呼叫记录表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_calllist02`
--

DROP TABLE IF EXISTS `xhgmnet_calllist02`;
CREATE TABLE IF NOT EXISTS `xhgmnet_calllist02` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Call_Time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '呼叫起始时间',
  `Call_id` varchar(40) NOT NULL COMMENT '呼叫流水号',
  `Call_sys_id` tinyint(1) NOT NULL COMMENT '0=Motrola；1=eTra；',
  `Use_Time` int(11) NOT NULL DEFAULT '0' COMMENT '呼叫用时',
  `Caller` varchar(10) NOT NULL COMMENT '主叫号码',
  `Called` varchar(10) NOT NULL COMMENT '被叫号码',
  `Call_Type` int(2) NOT NULL DEFAULT '0' COMMENT '呼叫类型0~9',
  `Call_TS_Id` varchar(20) DEFAULT NULL COMMENT '接入基站id',
  `Call_BS_BS` varchar(20) DEFAULT NULL COMMENT '接入载频id',
  `Call_RSSI` varchar(10) DEFAULT NULL COMMENT '接入场强',
  `Call_Result` varchar(10) DEFAULT NULL COMMENT '结束原因',
  `Call_Path` varchar(100) DEFAULT NULL COMMENT '音频地址',
  PRIMARY KEY (`id`),
  KEY `Call_id` (`Call_id`),
  KEY `Call_Time` (`Call_Time`),
  KEY `Caller` (`Caller`),
  KEY `Called` (`Called`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='呼叫记录表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_calllist03`
--

DROP TABLE IF EXISTS `xhgmnet_calllist03`;
CREATE TABLE IF NOT EXISTS `xhgmnet_calllist03` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Call_Time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '呼叫起始时间',
  `Call_id` varchar(40) NOT NULL COMMENT '呼叫流水号',
  `Call_sys_id` tinyint(1) NOT NULL COMMENT '0=Motrola；1=eTra；',
  `Use_Time` int(11) NOT NULL DEFAULT '0' COMMENT '呼叫用时',
  `Caller` varchar(10) NOT NULL COMMENT '主叫号码',
  `Called` varchar(10) NOT NULL COMMENT '被叫号码',
  `Call_Type` int(2) NOT NULL DEFAULT '0' COMMENT '呼叫类型0~9',
  `Call_TS_Id` varchar(20) DEFAULT NULL COMMENT '接入基站id',
  `Call_BS_BS` varchar(20) DEFAULT NULL COMMENT '接入载频id',
  `Call_RSSI` varchar(10) DEFAULT NULL COMMENT '接入场强',
  `Call_Result` varchar(10) DEFAULT NULL COMMENT '结束原因',
  `Call_Path` varchar(100) DEFAULT NULL COMMENT '音频地址',
  PRIMARY KEY (`id`),
  KEY `Call_id` (`Call_id`),
  KEY `Call_Time` (`Call_Time`),
  KEY `Caller` (`Caller`),
  KEY `Called` (`Called`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='呼叫记录表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_calllist04`
--

DROP TABLE IF EXISTS `xhgmnet_calllist04`;
CREATE TABLE IF NOT EXISTS `xhgmnet_calllist04` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Call_Time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '呼叫起始时间',
  `Call_id` varchar(40) NOT NULL COMMENT '呼叫流水号',
  `Call_sys_id` tinyint(1) NOT NULL COMMENT '0=Motrola；1=eTra；',
  `Use_Time` int(11) NOT NULL DEFAULT '0' COMMENT '呼叫用时',
  `Caller` varchar(10) NOT NULL COMMENT '主叫号码',
  `Called` varchar(10) NOT NULL COMMENT '被叫号码',
  `Call_Type` int(2) NOT NULL DEFAULT '0' COMMENT '呼叫类型0~9',
  `Call_TS_Id` varchar(20) DEFAULT NULL COMMENT '接入基站id',
  `Call_BS_BS` varchar(20) DEFAULT NULL COMMENT '接入载频id',
  `Call_RSSI` varchar(10) DEFAULT NULL COMMENT '接入场强',
  `Call_Result` varchar(10) DEFAULT NULL COMMENT '结束原因',
  `Call_Path` varchar(100) DEFAULT NULL COMMENT '音频地址',
  PRIMARY KEY (`id`),
  KEY `Call_id` (`Call_id`),
  KEY `Call_Time` (`Call_Time`),
  KEY `Caller` (`Caller`),
  KEY `Called` (`Called`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='呼叫记录表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_calllist05`
--

DROP TABLE IF EXISTS `xhgmnet_calllist05`;
CREATE TABLE IF NOT EXISTS `xhgmnet_calllist05` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Call_Time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '呼叫起始时间',
  `Call_id` varchar(40) NOT NULL COMMENT '呼叫流水号',
  `Call_sys_id` tinyint(1) NOT NULL COMMENT '0=Motrola；1=eTra；',
  `Use_Time` int(11) NOT NULL DEFAULT '0' COMMENT '呼叫用时',
  `Caller` varchar(10) NOT NULL COMMENT '主叫号码',
  `Called` varchar(10) NOT NULL COMMENT '被叫号码',
  `Call_Type` int(2) NOT NULL DEFAULT '0' COMMENT '呼叫类型0~9',
  `Call_TS_Id` varchar(20) DEFAULT NULL COMMENT '接入基站id',
  `Call_BS_BS` varchar(20) DEFAULT NULL COMMENT '接入载频id',
  `Call_RSSI` varchar(10) DEFAULT NULL COMMENT '接入场强',
  `Call_Result` varchar(10) DEFAULT NULL COMMENT '结束原因',
  `Call_Path` varchar(100) DEFAULT NULL COMMENT '音频地址',
  PRIMARY KEY (`id`),
  KEY `Call_id` (`Call_id`),
  KEY `Call_Time` (`Call_Time`),
  KEY `Caller` (`Caller`),
  KEY `Called` (`Called`)
) ENGINE=InnoDB  DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='呼叫记录表' AUTO_INCREMENT=6 ;

--
-- 转存表中的数据 `xhgmnet_calllist05`
--

INSERT INTO `xhgmnet_calllist05` (`id`, `Call_Time`, `Call_id`, `Call_sys_id`, `Use_Time`, `Caller`, `Called`, `Call_Type`, `Call_TS_Id`, `Call_BS_BS`, `Call_RSSI`, `Call_Result`, `Call_Path`) VALUES
(1, '2017-05-25 10:00:00', '12121212', 1, 1, '80020200', '80020201', 0, '25', NULL, '96', NULL, 'wav/Ring.wav'),
(2, '2017-05-25 10:00:00', '12121212', 1, 2, '80020200', '80020201', 0, '25', NULL, '96', NULL, 'wav/Ring.wav'),
(3, '2017-05-25 10:00:00', '12121212', 1, 3, '80020200', '80020201', 0, '25', NULL, '96', NULL, 'wav/Ring.wav'),
(4, '2017-05-25 10:00:00', '12121212', 1, 4, '80020200', '80020201', 0, '25', NULL, '96', NULL, 'wav/Ring.wav'),
(5, '2017-05-25 10:00:00', '12121212', 1, 5, '80020200', '80020201', 0, '25', NULL, '96', NULL, 'wav/Ring.wav');

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_calllist06`
--

DROP TABLE IF EXISTS `xhgmnet_calllist06`;
CREATE TABLE IF NOT EXISTS `xhgmnet_calllist06` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Call_Time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '呼叫起始时间',
  `Call_id` varchar(40) NOT NULL COMMENT '呼叫流水号',
  `Call_sys_id` tinyint(1) NOT NULL COMMENT '0=Motrola；1=eTra；',
  `Use_Time` int(11) NOT NULL DEFAULT '0' COMMENT '呼叫用时',
  `Caller` varchar(10) NOT NULL COMMENT '主叫号码',
  `Called` varchar(10) NOT NULL COMMENT '被叫号码',
  `Call_Type` int(2) NOT NULL DEFAULT '0' COMMENT '呼叫类型0~9',
  `Call_TS_Id` varchar(20) DEFAULT NULL COMMENT '接入基站id',
  `Call_BS_BS` varchar(20) DEFAULT NULL COMMENT '接入载频id',
  `Call_RSSI` varchar(10) DEFAULT NULL COMMENT '接入场强',
  `Call_Result` varchar(10) DEFAULT NULL COMMENT '结束原因',
  `Call_Path` varchar(100) DEFAULT NULL COMMENT '音频地址',
  PRIMARY KEY (`id`),
  KEY `Call_id` (`Call_id`),
  KEY `Call_Time` (`Call_Time`),
  KEY `Caller` (`Caller`),
  KEY `Called` (`Called`)
) ENGINE=InnoDB  DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='呼叫记录表' AUTO_INCREMENT=8 ;

--
-- 转存表中的数据 `xhgmnet_calllist06`
--

INSERT INTO `xhgmnet_calllist06` (`id`, `Call_Time`, `Call_id`, `Call_sys_id`, `Use_Time`, `Caller`, `Called`, `Call_Type`, `Call_TS_Id`, `Call_BS_BS`, `Call_RSSI`, `Call_Result`, `Call_Path`) VALUES
(1, '2017-06-13 02:00:00', '', 0, 30, '', '', 0, NULL, NULL, NULL, NULL, NULL),
(2, '2017-06-12 03:00:00', '', 0, 10, '', '', 0, NULL, NULL, NULL, NULL, NULL),
(3, '2017-06-13 04:00:00', '', 0, 10, '', '', 0, NULL, NULL, NULL, NULL, NULL),
(4, '2017-06-13 05:00:00', '', 0, 80, '', '', 0, NULL, NULL, NULL, NULL, NULL),
(5, '2017-06-13 08:00:00', '', 0, 10, '', '', 0, NULL, NULL, NULL, NULL, NULL),
(6, '2017-06-13 09:00:00', '', 0, 50, '', '', 0, NULL, NULL, NULL, NULL, NULL),
(7, '2017-06-09 15:00:00', '', 0, 10, '', '', 0, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_calllist07`
--

DROP TABLE IF EXISTS `xhgmnet_calllist07`;
CREATE TABLE IF NOT EXISTS `xhgmnet_calllist07` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Call_Time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '呼叫起始时间',
  `Call_id` varchar(40) NOT NULL COMMENT '呼叫流水号',
  `Call_sys_id` tinyint(1) NOT NULL COMMENT '0=Motrola；1=eTra；',
  `Use_Time` int(11) NOT NULL DEFAULT '0' COMMENT '呼叫用时',
  `Caller` varchar(10) NOT NULL COMMENT '主叫号码',
  `Called` varchar(10) NOT NULL COMMENT '被叫号码',
  `Call_Type` int(2) NOT NULL DEFAULT '0' COMMENT '呼叫类型0~9',
  `Call_TS_Id` varchar(20) DEFAULT NULL COMMENT '接入基站id',
  `Call_BS_BS` varchar(20) DEFAULT NULL COMMENT '接入载频id',
  `Call_RSSI` varchar(10) DEFAULT NULL COMMENT '接入场强',
  `Call_Result` varchar(10) DEFAULT NULL COMMENT '结束原因',
  `Call_Path` varchar(100) DEFAULT NULL COMMENT '音频地址',
  PRIMARY KEY (`id`),
  KEY `Call_id` (`Call_id`),
  KEY `Call_Time` (`Call_Time`),
  KEY `Caller` (`Caller`),
  KEY `Called` (`Called`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='呼叫记录表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_calllist08`
--

DROP TABLE IF EXISTS `xhgmnet_calllist08`;
CREATE TABLE IF NOT EXISTS `xhgmnet_calllist08` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Call_Time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '呼叫起始时间',
  `Call_id` varchar(40) NOT NULL COMMENT '呼叫流水号',
  `Call_sys_id` tinyint(1) NOT NULL COMMENT '0=Motrola；1=eTra；',
  `Use_Time` int(11) NOT NULL DEFAULT '0' COMMENT '呼叫用时',
  `Caller` varchar(10) NOT NULL COMMENT '主叫号码',
  `Called` varchar(10) NOT NULL COMMENT '被叫号码',
  `Call_Type` int(2) NOT NULL DEFAULT '0' COMMENT '呼叫类型0~9',
  `Call_TS_Id` varchar(20) DEFAULT NULL COMMENT '接入基站id',
  `Call_BS_BS` varchar(20) DEFAULT NULL COMMENT '接入载频id',
  `Call_RSSI` varchar(10) DEFAULT NULL COMMENT '接入场强',
  `Call_Result` varchar(10) DEFAULT NULL COMMENT '结束原因',
  `Call_Path` varchar(100) DEFAULT NULL COMMENT '音频地址',
  PRIMARY KEY (`id`),
  KEY `Call_id` (`Call_id`),
  KEY `Call_Time` (`Call_Time`),
  KEY `Caller` (`Caller`),
  KEY `Called` (`Called`)
) ENGINE=InnoDB  DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='呼叫记录表' AUTO_INCREMENT=3 ;

--
-- 转存表中的数据 `xhgmnet_calllist08`
--

INSERT INTO `xhgmnet_calllist08` (`id`, `Call_Time`, `Call_id`, `Call_sys_id`, `Use_Time`, `Caller`, `Called`, `Call_Type`, `Call_TS_Id`, `Call_BS_BS`, `Call_RSSI`, `Call_Result`, `Call_Path`) VALUES
(1, '2017-08-08 10:00:00', '11', 1, 0, '80020200', '20020205', 0, '1', '11', '11', '11', '11'),
(2, '2017-08-08 00:00:00', '1111', 1, 0, '80020020', '80020206', 0, '2', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_calllist09`
--

DROP TABLE IF EXISTS `xhgmnet_calllist09`;
CREATE TABLE IF NOT EXISTS `xhgmnet_calllist09` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Call_Time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '呼叫起始时间',
  `Call_id` varchar(40) NOT NULL COMMENT '呼叫流水号',
  `Call_sys_id` tinyint(1) NOT NULL COMMENT '0=Motrola；1=eTra；',
  `Use_Time` int(11) NOT NULL DEFAULT '0' COMMENT '呼叫用时',
  `Caller` varchar(10) NOT NULL COMMENT '主叫号码',
  `Called` varchar(10) NOT NULL COMMENT '被叫号码',
  `Call_Type` int(2) NOT NULL DEFAULT '0' COMMENT '呼叫类型0~9',
  `Call_TS_Id` varchar(20) DEFAULT NULL COMMENT '接入基站id',
  `Call_BS_BS` varchar(20) DEFAULT NULL COMMENT '接入载频id',
  `Call_RSSI` varchar(10) DEFAULT NULL COMMENT '接入场强',
  `Call_Result` varchar(10) DEFAULT NULL COMMENT '结束原因',
  `Call_Path` varchar(100) DEFAULT NULL COMMENT '音频地址',
  PRIMARY KEY (`id`),
  KEY `Call_id` (`Call_id`),
  KEY `Call_Time` (`Call_Time`),
  KEY `Caller` (`Caller`),
  KEY `Called` (`Called`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='呼叫记录表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_calllist10`
--

DROP TABLE IF EXISTS `xhgmnet_calllist10`;
CREATE TABLE IF NOT EXISTS `xhgmnet_calllist10` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Call_Time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '呼叫起始时间',
  `Call_id` varchar(40) NOT NULL COMMENT '呼叫流水号',
  `Call_sys_id` tinyint(1) NOT NULL COMMENT '0=Motrola；1=eTra；',
  `Use_Time` int(11) NOT NULL DEFAULT '0' COMMENT '呼叫用时',
  `Caller` varchar(10) NOT NULL COMMENT '主叫号码',
  `Called` varchar(10) NOT NULL COMMENT '被叫号码',
  `Call_Type` int(2) NOT NULL DEFAULT '0' COMMENT '呼叫类型0~9',
  `Call_TS_Id` varchar(20) DEFAULT NULL COMMENT '接入基站id',
  `Call_BS_BS` varchar(20) DEFAULT NULL COMMENT '接入载频id',
  `Call_RSSI` varchar(10) DEFAULT NULL COMMENT '接入场强',
  `Call_Result` varchar(10) DEFAULT NULL COMMENT '结束原因',
  `Call_Path` varchar(100) DEFAULT NULL COMMENT '音频地址',
  PRIMARY KEY (`id`),
  KEY `Call_id` (`Call_id`),
  KEY `Call_Time` (`Call_Time`),
  KEY `Caller` (`Caller`),
  KEY `Called` (`Called`)
) ENGINE=InnoDB  DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='呼叫记录表' AUTO_INCREMENT=2413065 ;

--
-- 转存表中的数据 `xhgmnet_calllist10`
--

INSERT INTO `xhgmnet_calllist10` (`id`, `Call_Time`, `Call_id`, `Call_sys_id`, `Use_Time`, `Caller`, `Called`, `Call_Type`, `Call_TS_Id`, `Call_BS_BS`, `Call_RSSI`, `Call_Result`, `Call_Path`) VALUES
(2412965, '2017-10-14 16:39:34', '5800CD5A-B543-4FA9-A81D-5F13AD89E690', 1, 5, '8015902', '8008955', 12, '85', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412966, '2017-10-14 16:39:24', '5800CD5A-B543-4FA9-A81D-5F13AD89E690', 1, 5, '8015902', '8008955', 12, '85', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412967, '2017-10-14 16:39:31', '5800CD5A-B543-4FA9-A81D-5F13AD89E690', 1, 2, '8015926', '8008955', 12, '85', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412968, '2017-10-14 16:39:34', '480341DB-D014-4C37-8F05-234157B9EA89', 1, 1, '8016730', '8008984', 12, '7', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412969, '2017-10-14 16:39:25', '480341DB-D014-4C37-8F05-234157B9EA89', 1, 1, '8016790', '8008984', 12, '7', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412970, '2017-10-14 16:39:27', '480341DB-D014-4C37-8F05-234157B9EA89', 1, 7, '8016790', '8008984', 12, '7', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412971, '2017-10-14 16:39:33', '7835043A-B8C1-46F5-B243-B9C8EF594B68', 1, 4, '8015228', '8008954', 12, '5', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412972, '2017-10-14 16:39:34', '2EB6FA75-9801-4DB3-957A-6CBBDFBFA254', 1, 1, '8020758', '8007992', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412973, '2017-10-14 16:39:30', '5FE68435-0FD6-4E11-90C3-618DC5B44CB2', 1, 1, '8016680', '8008966', 12, '97', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412974, '2017-10-14 16:39:27', '2EB6FA75-9801-4DB3-957A-6CBBDFBFA254', 1, 2, '8020758', '8007992', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412975, '2017-10-14 16:39:27', '5FE68435-0FD6-4E11-90C3-618DC5B44CB2', 1, 3, '8016677', '8008966', 12, '97', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412976, '2017-10-14 16:39:27', '7835043A-B8C1-46F5-B243-B9C8EF594B68', 1, 3, '8015228', '8008954', 12, '5', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412977, '2017-10-14 16:39:31', '7835043A-B8C1-46F5-B243-B9C8EF594B68', 1, 1, '8015416', '8008954', 12, '5', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412978, '2017-10-14 16:39:32', '2EB6FA75-9801-4DB3-957A-6CBBDFBFA254', 1, 1, '8007142', '8007992', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412979, '2017-10-14 16:39:39', '18907FE9-D940-4EEE-8FB2-2F876E6C175E', 1, 5, '8013204', '8008934', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412980, '2017-10-14 16:39:41', '33D0D88F-8B10-40D6-8FA7-12CB12D7DB58', 1, 2, '8015723', '8008952', 12, '5', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412981, '2017-10-14 16:39:38', '33D0D88F-8B10-40D6-8FA7-12CB12D7DB58', 1, 3, '8015031', '8008952', 12, '5', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412982, '2017-10-14 16:39:43', '14FE852D-7C6A-45E0-AE90-CC28281E989C', 1, 1, '7088624', '7088989', 12, '121', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412983, '2017-10-14 16:39:36', '14FE852D-7C6A-45E0-AE90-CC28281E989C', 1, 4, '7088781', '7088989', 12, '121', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412984, '2017-10-14 16:39:39', '5800CD5A-B543-4FA9-A81D-5F13AD89E690', 1, 3, '8015926', '8008955', 12, '85', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412985, '2017-10-14 16:39:41', '7835043A-B8C1-46F5-B243-B9C8EF594B68', 1, 2, '8015228', '8008954', 12, '5', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412986, '2017-10-14 16:39:35', '5FE68435-0FD6-4E11-90C3-618DC5B44CB2', 1, 5, '8016680', '8008966', 12, '97', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412987, '2017-10-14 16:39:39', '2EB6FA75-9801-4DB3-957A-6CBBDFBFA254', 1, 1, '8007142', '8007992', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412988, '2017-10-14 16:39:38', '7835043A-B8C1-46F5-B243-B9C8EF594B68', 1, 2, '8015416', '8008954', 12, '5', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412989, '2017-10-14 16:39:46', '18907FE9-D940-4EEE-8FB2-2F876E6C175E', 1, 7, '8013865', '8008934', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412990, '2017-10-14 16:39:54', '18907FE9-D940-4EEE-8FB2-2F876E6C175E', 1, 1, '8013216', '8008934', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412991, '2017-10-14 16:39:45', '18907FE9-D940-4EEE-8FB2-2F876E6C175E', 1, 1, '8013865', '8008934', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412992, '2017-10-14 16:39:39', 'A7D39B54-6B59-43FC-BCED-7BCFF749DA15', 1, 7, '8014901', '8008945', 12, '1', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412993, '2017-10-14 16:39:46', 'A7D39B54-6B59-43FC-BCED-7BCFF749DA15', 1, 6, '8014302', '8008945', 12, '1', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412994, '2017-10-14 16:39:49', '33D0D88F-8B10-40D6-8FA7-12CB12D7DB58', 1, 1, '8015723', '8008952', 12, '5', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412995, '2017-10-14 16:39:44', '33D0D88F-8B10-40D6-8FA7-12CB12D7DB58', 1, 4, '8015031', '8008952', 12, '5', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412996, '2017-10-14 16:39:46', '5800CD5A-B543-4FA9-A81D-5F13AD89E690', 1, 1, '8015902', '8008955', 12, '85', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412997, '2017-10-14 16:39:49', '5800CD5A-B543-4FA9-A81D-5F13AD89E690', 1, 3, '8015879', '8008955', 12, '85', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412998, '2017-10-14 16:39:36', '480341DB-D014-4C37-8F05-234157B9EA89', 1, 11, '8016790', '8008984', 12, '7', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2412999, '2017-10-14 16:39:50', '480341DB-D014-4C37-8F05-234157B9EA89', 1, 5, '8016730', '8008984', 12, '7', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413000, '2017-10-14 16:39:44', '7835043A-B8C1-46F5-B243-B9C8EF594B68', 1, 4, '8015416', '8008954', 12, '5', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413001, '2017-10-14 16:39:51', '2EB6FA75-9801-4DB3-957A-6CBBDFBFA254', 1, 1, '8007142', '8007992', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413002, '2017-10-14 16:39:50', '7835043A-B8C1-46F5-B243-B9C8EF594B68', 1, 3, '8015228', '8008954', 12, '5', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413003, '2017-10-14 16:39:40', '5FE68435-0FD6-4E11-90C3-618DC5B44CB2', 1, 10, '8016677', '8008966', 12, '97', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413004, '2017-10-14 16:39:41', '2EB6FA75-9801-4DB3-957A-6CBBDFBFA254', 1, 9, '8020758', '8007992', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413005, '2017-10-14 16:39:41', 'D90202C7-E177-43D8-9A9F-6848FCFB8D21', 1, 9, '8011503', '8008917', 12, '7', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413006, '2017-10-14 16:39:43', 'DC85E6AF-61F1-4ED8-9EEC-CF1DA2B562D8', 1, 1, '1345144', '1345918', 12, '20', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413007, '2017-10-14 16:39:49', 'A665CF2C-3B03-4018-B6FC-327E193CD7AB', 1, 2, '8013332', '8008937', 12, '86', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413008, '2017-10-14 16:39:55', '07EE4058-63A4-48E0-8F03-F9BB07BE9FA8', 1, 2, '8014142', '8008946', 12, '1', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413009, '2017-10-14 16:39:52', '07EE4058-63A4-48E0-8F03-F9BB07BE9FA8', 1, 1, '8014142', '8008946', 12, '1', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413010, '2017-10-14 16:39:56', '18907FE9-D940-4EEE-8FB2-2F876E6C175E', 1, 5, '8013216', '8008934', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413011, '2017-10-14 16:39:56', '480341DB-D014-4C37-8F05-234157B9EA89', 1, 4, '8016790', '8008984', 12, '7', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413012, '2017-10-14 16:39:51', '5FE68435-0FD6-4E11-90C3-618DC5B44CB2', 1, 7, '8016680', '8008966', 12, '97', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413013, '2017-10-14 16:39:54', '7835043A-B8C1-46F5-B243-B9C8EF594B68', 1, 5, '8015416', '8008954', 12, '5', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413014, '2017-10-14 16:40:01', '07EE4058-63A4-48E0-8F03-F9BB07BE9FA8', 1, 0, '8014408', '8008946', 12, '1', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413015, '2017-10-14 16:40:01', '18907FE9-D940-4EEE-8FB2-2F876E6C175E', 1, 2, '8011548', '8008934', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413016, '2017-10-14 16:40:06', '18907FE9-D940-4EEE-8FB2-2F876E6C175E', 1, 6, '8011548', '8008934', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413017, '2017-10-14 16:40:05', '18907FE9-D940-4EEE-8FB2-2F876E6C175E', 1, 1, '8013766', '8008934', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413018, '2017-10-14 16:40:05', 'A7D39B54-6B59-43FC-BCED-7BCFF749DA15', 1, 3, '8014302', '8008945', 12, '1', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413019, '2017-10-14 16:39:53', 'A7D39B54-6B59-43FC-BCED-7BCFF749DA15', 1, 11, '8014901', '8008945', 12, '1', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413020, '2017-10-14 16:40:04', '7835043A-B8C1-46F5-B243-B9C8EF594B68', 1, 6, '8015416', '8008954', 12, '5', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413021, '2017-10-14 16:40:01', '7835043A-B8C1-46F5-B243-B9C8EF594B68', 1, 2, '8015228', '8008954', 12, '5', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413022, '2017-10-14 16:39:53', 'D998E048-0364-4F3F-AEAF-2C5DE8EADC19', 1, 3, '7088624', '7088983', 12, '8', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413023, '2017-10-14 16:39:59', 'D998E048-0364-4F3F-AEAF-2C5DE8EADC19', 1, 7, '7088781', '7088983', 12, '8', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413024, '2017-10-14 16:40:04', 'FAA2CB78-8806-49F2-B5A3-781D0FD54212', 1, 5, '8013646', '8008937', 12, '86', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413025, '2017-10-14 16:39:59', '42C36DC6-B214-48C7-AD1D-57B67169AAB3', 1, 4, '8015303', '8008955', 12, '85', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413026, '2017-10-14 16:39:59', 'FAA2CB78-8806-49F2-B5A3-781D0FD54212', 1, 2, '8013332', '8008937', 12, '86', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413027, '2017-10-14 16:40:04', 'D3D78FEB-2A29-4FAE-814D-B6E14B1A4442', 1, 3, '8014503', '8008947', 12, '3', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413028, '2017-10-14 16:40:08', 'D3D78FEB-2A29-4FAE-814D-B6E14B1A4442', 1, 1, '8014947', '8008947', 12, '3', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413029, '2017-10-14 16:40:10', 'A7D39B54-6B59-43FC-BCED-7BCFF749DA15', 1, 9, '8014901', '8008945', 12, '1', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413030, '2017-10-14 16:40:17', '480341DB-D014-4C37-8F05-234157B9EA89', 1, 1, '8016035', '8008984', 12, '7', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413031, '2017-10-14 16:40:02', '480341DB-D014-4C37-8F05-234157B9EA89', 1, 14, '8016617', '8008984', 12, '7', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413032, '2017-10-14 16:40:02', '07EE4058-63A4-48E0-8F03-F9BB07BE9FA8', 1, 15, '8014142', '8008946', 12, '1', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413033, '2017-10-14 16:40:25', 'A7D39B54-6B59-43FC-BCED-7BCFF749DA15', 1, 3, '8014981', '8008945', 12, '1', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413034, '2017-10-14 16:40:21', 'A7D39B54-6B59-43FC-BCED-7BCFF749DA15', 1, 4, '8014302', '8008945', 12, '1', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413035, '2017-10-14 16:40:19', '480341DB-D014-4C37-8F05-234157B9EA89', 1, 3, '8016617', '8008984', 12, '7', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413036, '2017-10-14 16:40:19', '07EE4058-63A4-48E0-8F03-F9BB07BE9FA8', 1, 2, '8014408', '8008946', 12, '1', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413037, '2017-10-14 16:40:11', '01BBC103-91A6-41AB-B177-48CD4A2825AF', 1, 3, '8013521', '8008936', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413038, '2017-10-14 16:40:17', '01BBC103-91A6-41AB-B177-48CD4A2825AF', 1, 3, '8013983', '8008936', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413039, '2017-10-14 16:40:12', '174BB803-8FA8-49C1-B9F8-0D90599F460D', 1, 4, '8011456', '8008916', 12, '118', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413040, '2017-10-14 16:40:24', '174BB803-8FA8-49C1-B9F8-0D90599F460D', 1, 4, '8011028', '8008916', 12, '118', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413041, '2017-10-14 16:40:17', '174BB803-8FA8-49C1-B9F8-0D90599F460D', 1, 2, '8011028', '8008916', 12, '118', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413042, '2017-10-14 16:40:19', '174BB803-8FA8-49C1-B9F8-0D90599F460D', 1, 5, '8011456', '8008916', 12, '118', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413043, '2017-10-14 16:40:25', '1D6A0CD8-09FF-4A1C-A194-F7193B23FE32', 1, 1, '7088781', '7088985', 12, '122', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413044, '2017-10-14 16:40:14', '1D6A0CD8-09FF-4A1C-A194-F7193B23FE32', 1, 2, '7088686', '7088985', 12, '122', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413045, '2017-10-14 16:40:21', '1D6A0CD8-09FF-4A1C-A194-F7193B23FE32', 1, 3, '7088686', '7088985', 12, '122', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413046, '2017-10-14 16:40:17', '1D6A0CD8-09FF-4A1C-A194-F7193B23FE32', 1, 1, '7088781', '7088985', 12, '122', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413047, '2017-10-14 16:40:16', '1C6A8001-40BE-4068-8BE9-F23D98817F1C', 1, 4, '8015416', '8008954', 12, '5', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413048, '2017-10-14 16:40:19', '672E9157-D8F5-4EF6-AB52-CCC6308D2BFE', 1, 6, '8013204', '8008934', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413049, '2017-10-14 16:40:20', '32E3B662-00FA-4DF8-8EF8-173DCDBB0467', 1, 3, '8013103', '8008933', 12, '85', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413050, '2017-10-14 16:40:24', '32E3B662-00FA-4DF8-8EF8-173DCDBB0467', 1, 2, '8013805', '8008933', 12, '85', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413051, '2017-10-14 16:40:24', '3BF02197-CAAA-4875-A172-D22062FDAF8E', 1, 3, '8014870', '8008944', 12, '3', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413052, '2017-10-14 16:40:20', '3BF02197-CAAA-4875-A172-D22062FDAF8E', 1, 3, '8014870', '8008944', 12, '3', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413053, '2017-10-14 16:40:29', '27BCAA38-B116-4837-BD18-57C23715F2C5', 1, 2, '8012471', '8008926', 12, '92', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413054, '2017-10-14 16:40:25', '27BCAA38-B116-4837-BD18-57C23715F2C5', 1, 3, '8012403', '8008926', 12, '92', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413055, '2017-10-14 16:40:21', '27BCAA38-B116-4837-BD18-57C23715F2C5', 1, 2, '8012471', '8008926', 12, '92', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413056, '2017-10-14 16:40:23', '6BE06784-D768-47B4-8A6B-8E6C28DCA0EF', 1, 3, '8020631', '8007961', 12, '71', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413057, '2017-10-14 16:40:29', '6BE06784-D768-47B4-8A6B-8E6C28DCA0EF', 1, 1, '8007477', '8007961', 12, '71', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413058, '2017-10-14 16:40:29', 'A7D39B54-6B59-43FC-BCED-7BCFF749DA15', 1, 4, '8014901', '8008945', 12, '1', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413059, '2017-10-14 16:40:35', 'EFTHGYJJKKKKKKK', 1, 100, '8014302', '8008945', 12, '1', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413060, '2017-10-14 16:40:22', '07EE4058-63A4-48E0-8F03-F9BB07BE9FA8', 1, 15, '8014142', '8008946', 12, '1', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413061, '2017-10-14 16:40:21', '01BBC103-91A6-41AB-B177-48CD4A2825AF', 1, 17, '8013521', '8008936', 12, '60', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413062, '2017-10-14 16:40:29', '174BB803-8FA8-49C1-B9F8-0D90599F460D', 1, 3, '8011456', '8008916', 12, '118', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413063, '2017-10-14 16:40:31', '1D6A0CD8-09FF-4A1C-A194-F7193B23FE32', 1, 3, '7088781', '7088985', 12, '122', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav'),
(2413064, '2017-10-14 16:40:32', '6BE06784-D768-47B4-8A6B-8E6C28DCA0EF', 1, 7, '8020631', '8007961', 12, '71', '', NULL, '', 'wav/2017/10/14/16/38/40/Ring.wav');

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_calllist11`
--

DROP TABLE IF EXISTS `xhgmnet_calllist11`;
CREATE TABLE IF NOT EXISTS `xhgmnet_calllist11` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Call_Time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '呼叫起始时间',
  `Call_id` varchar(40) NOT NULL COMMENT '呼叫流水号',
  `Call_sys_id` tinyint(1) NOT NULL COMMENT '0=Motrola；1=eTra；',
  `Use_Time` int(11) NOT NULL DEFAULT '0' COMMENT '呼叫用时',
  `Caller` varchar(10) NOT NULL COMMENT '主叫号码',
  `Called` varchar(10) NOT NULL COMMENT '被叫号码',
  `Call_Type` int(2) NOT NULL DEFAULT '0' COMMENT '呼叫类型0~9',
  `Call_TS_Id` varchar(20) DEFAULT NULL COMMENT '接入基站id',
  `Call_BS_BS` varchar(20) DEFAULT NULL COMMENT '接入载频id',
  `Call_RSSI` varchar(10) DEFAULT NULL COMMENT '接入场强',
  `Call_Result` varchar(10) DEFAULT NULL COMMENT '结束原因',
  `Call_Path` varchar(100) DEFAULT NULL COMMENT '音频地址',
  PRIMARY KEY (`id`),
  KEY `Call_id` (`Call_id`),
  KEY `Call_Time` (`Call_Time`),
  KEY `Caller` (`Caller`),
  KEY `Called` (`Called`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='呼叫记录表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_calllist12`
--

DROP TABLE IF EXISTS `xhgmnet_calllist12`;
CREATE TABLE IF NOT EXISTS `xhgmnet_calllist12` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Call_Time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '呼叫起始时间',
  `Call_id` varchar(40) NOT NULL COMMENT '呼叫流水号',
  `Call_sys_id` tinyint(1) NOT NULL COMMENT '0=Motrola；1=eTra；',
  `Use_Time` int(11) NOT NULL DEFAULT '0' COMMENT '呼叫用时',
  `Caller` varchar(10) NOT NULL COMMENT '主叫号码',
  `Called` varchar(10) NOT NULL COMMENT '被叫号码',
  `Call_Type` int(2) NOT NULL DEFAULT '0' COMMENT '呼叫类型0~9',
  `Call_TS_Id` varchar(20) DEFAULT NULL COMMENT '接入基站id',
  `Call_BS_BS` varchar(20) DEFAULT NULL COMMENT '接入载频id',
  `Call_RSSI` varchar(10) DEFAULT NULL COMMENT '接入场强',
  `Call_Result` varchar(10) DEFAULT NULL COMMENT '结束原因',
  `Call_Path` varchar(100) DEFAULT NULL COMMENT '音频地址',
  PRIMARY KEY (`id`),
  KEY `Call_id` (`Call_id`),
  KEY `Call_Time` (`Call_Time`),
  KEY `Caller` (`Caller`),
  KEY `Called` (`Called`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='呼叫记录表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_channel_call_detail`
--

DROP TABLE IF EXISTS `xhgmnet_channel_call_detail`;
CREATE TABLE IF NOT EXISTS `xhgmnet_channel_call_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `startTime` datetime NOT NULL COMMENT '测量开始时间',
  `bsId` int(11) NOT NULL COMMENT '交换中心ID',
  `channleId` int(11) NOT NULL COMMENT '信道ID',
  `groupCallNumber` int(11) NOT NULL COMMENT '组呼个数',
  `groupCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '组呼持续时间',
  `singleCallNumber` int(11) NOT NULL COMMENT '个呼个数',
  `singleCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '个呼持续时间',
  `emergentCallTotals` int(11) NOT NULL COMMENT '紧急呼叫个数',
  `emergentCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '紧急呼叫持续时间',
  `phoneCallTotals` int(11) NOT NULL DEFAULT '0' COMMENT '电话呼叫个数',
  `phoneCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '电话呼叫持续时间 ',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='信道话务量详情' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_channel_call_totals`
--

DROP TABLE IF EXISTS `xhgmnet_channel_call_totals`;
CREATE TABLE IF NOT EXISTS `xhgmnet_channel_call_totals` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `startTime` datetime NOT NULL COMMENT '测量开始时间',
  `bsId` int(11) NOT NULL COMMENT '基站ID',
  `channelId` int(11) NOT NULL COMMENT '信道ID',
  `activityCallTotals` int(11) NOT NULL COMMENT '活动呼叫总数',
  `activityCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '活动呼叫总持续时间',
  `averageCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '平均呼叫持续时间',
  `percent` varchar(10) NOT NULL COMMENT '使用百分比',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='信道话务量总计' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_dispatcher_call_detail`
--

DROP TABLE IF EXISTS `xhgmnet_dispatcher_call_detail`;
CREATE TABLE IF NOT EXISTS `xhgmnet_dispatcher_call_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `startTime` datetime NOT NULL COMMENT '测量开始时间',
  `dispatcherId` int(11) NOT NULL COMMENT '调度台ID',
  `groupCallNumber` int(11) NOT NULL COMMENT '组呼个数',
  `groupCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '组呼持续时间',
  `singleCallNumber` int(11) NOT NULL COMMENT '个呼个数',
  `singleCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '个呼持续时间',
  `emergentCallTotals` int(11) NOT NULL COMMENT '紧急呼叫个数',
  `emergentCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '紧急呼叫持续时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='调度台话务详情' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_dispatcher_call_totals`
--

DROP TABLE IF EXISTS `xhgmnet_dispatcher_call_totals`;
CREATE TABLE IF NOT EXISTS `xhgmnet_dispatcher_call_totals` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `startTime` datetime NOT NULL COMMENT '测量开始时间',
  `dispatcherId` int(11) NOT NULL COMMENT '调度台ID',
  `activityCallTotals` int(11) NOT NULL COMMENT '活动呼叫总数',
  `activityCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '活动呼叫总持续时间',
  `averageCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '平均呼叫持续时间',
  `pttTotals` int(11) NOT NULL COMMENT '总PTT数',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='调度台话务总计' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsinfo`
--

DROP TABLE IF EXISTS `xhgmnet_gpsinfo`;
CREATE TABLE IF NOT EXISTS `xhgmnet_gpsinfo` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息写入时间',
  `infoType` varchar(255) DEFAULT NULL COMMENT '信息类型',
  `srcId` bigint(20) NOT NULL COMMENT '上传信息源ID',
  `dstId` bigint(20) NOT NULL COMMENT '上传信息目标ID',
  `longitude` double NOT NULL COMMENT '经度',
  `latitude` double NOT NULL COMMENT '纬度',
  `heigh` bigint(20) DEFAULT NULL COMMENT '高度',
  `infoTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息产生的时间',
  `speed` int(11) DEFAULT NULL COMMENT '速度（Km/h）',
  `direction` int(11) DEFAULT NULL COMMENT '方向（度）',
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `srcId` (`srcId`),
  KEY `dstId` (`dstId`),
  KEY `infoTime` (`infoTime`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS信息表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsinfo01`
--

DROP TABLE IF EXISTS `xhgmnet_gpsinfo01`;
CREATE TABLE IF NOT EXISTS `xhgmnet_gpsinfo01` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息写入时间',
  `infoType` varchar(255) DEFAULT NULL COMMENT '信息类型',
  `srcId` bigint(20) NOT NULL COMMENT '上传信息源ID',
  `dstId` bigint(20) NOT NULL COMMENT '上传信息目标ID',
  `longitude` double NOT NULL COMMENT '经度',
  `latitude` double NOT NULL COMMENT '纬度',
  `heigh` bigint(20) DEFAULT NULL COMMENT '高度',
  `infoTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息产生的时间',
  `speed` int(11) DEFAULT NULL COMMENT '速度（Km/h）',
  `direction` int(11) DEFAULT NULL COMMENT '方向（度）',
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `srcId` (`srcId`),
  KEY `dstId` (`dstId`),
  KEY `infoTime` (`infoTime`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS信息表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsinfo02`
--

DROP TABLE IF EXISTS `xhgmnet_gpsinfo02`;
CREATE TABLE IF NOT EXISTS `xhgmnet_gpsinfo02` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息写入时间',
  `infoType` varchar(255) DEFAULT NULL COMMENT '信息类型',
  `srcId` bigint(20) NOT NULL COMMENT '上传信息源ID',
  `dstId` bigint(20) NOT NULL COMMENT '上传信息目标ID',
  `longitude` double NOT NULL COMMENT '经度',
  `latitude` double NOT NULL COMMENT '纬度',
  `heigh` bigint(20) DEFAULT NULL COMMENT '高度',
  `infoTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息产生的时间',
  `speed` int(11) DEFAULT NULL COMMENT '速度（Km/h）',
  `direction` int(11) DEFAULT NULL COMMENT '方向（度）',
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `srcId` (`srcId`),
  KEY `dstId` (`dstId`),
  KEY `infoTime` (`infoTime`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS信息表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsinfo03`
--

DROP TABLE IF EXISTS `xhgmnet_gpsinfo03`;
CREATE TABLE IF NOT EXISTS `xhgmnet_gpsinfo03` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息写入时间',
  `infoType` varchar(255) DEFAULT NULL COMMENT '信息类型',
  `srcId` bigint(20) NOT NULL COMMENT '上传信息源ID',
  `dstId` bigint(20) NOT NULL COMMENT '上传信息目标ID',
  `longitude` double NOT NULL COMMENT '经度',
  `latitude` double NOT NULL COMMENT '纬度',
  `heigh` bigint(20) DEFAULT NULL COMMENT '高度',
  `infoTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息产生的时间',
  `speed` int(11) DEFAULT NULL COMMENT '速度（Km/h）',
  `direction` int(11) DEFAULT NULL COMMENT '方向（度）',
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `srcId` (`srcId`),
  KEY `dstId` (`dstId`),
  KEY `infoTime` (`infoTime`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS信息表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsinfo04`
--

DROP TABLE IF EXISTS `xhgmnet_gpsinfo04`;
CREATE TABLE IF NOT EXISTS `xhgmnet_gpsinfo04` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息写入时间',
  `infoType` varchar(255) DEFAULT NULL COMMENT '信息类型',
  `srcId` bigint(20) NOT NULL COMMENT '上传信息源ID',
  `dstId` bigint(20) NOT NULL COMMENT '上传信息目标ID',
  `longitude` double NOT NULL COMMENT '经度',
  `latitude` double NOT NULL COMMENT '纬度',
  `heigh` bigint(20) DEFAULT NULL COMMENT '高度',
  `infoTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息产生的时间',
  `speed` int(11) DEFAULT NULL COMMENT '速度（Km/h）',
  `direction` int(11) DEFAULT NULL COMMENT '方向（度）',
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `srcId` (`srcId`),
  KEY `dstId` (`dstId`),
  KEY `infoTime` (`infoTime`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS信息表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsinfo05`
--

DROP TABLE IF EXISTS `xhgmnet_gpsinfo05`;
CREATE TABLE IF NOT EXISTS `xhgmnet_gpsinfo05` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息写入时间',
  `infoType` varchar(255) DEFAULT NULL COMMENT '信息类型',
  `srcId` bigint(20) NOT NULL COMMENT '上传信息源ID',
  `dstId` bigint(20) NOT NULL COMMENT '上传信息目标ID',
  `longitude` double NOT NULL COMMENT '经度',
  `latitude` double NOT NULL COMMENT '纬度',
  `heigh` bigint(20) DEFAULT NULL COMMENT '高度',
  `infoTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息产生的时间',
  `speed` int(11) DEFAULT NULL COMMENT '速度（Km/h）',
  `direction` int(11) DEFAULT NULL COMMENT '方向（度）',
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `srcId` (`srcId`),
  KEY `dstId` (`dstId`),
  KEY `infoTime` (`infoTime`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS信息表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsinfo06`
--

DROP TABLE IF EXISTS `xhgmnet_gpsinfo06`;
CREATE TABLE IF NOT EXISTS `xhgmnet_gpsinfo06` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息写入时间',
  `infoType` varchar(255) DEFAULT NULL COMMENT '信息类型',
  `srcId` bigint(20) NOT NULL COMMENT '上传信息源ID',
  `dstId` bigint(20) NOT NULL COMMENT '上传信息目标ID',
  `longitude` double NOT NULL COMMENT '经度',
  `latitude` double NOT NULL COMMENT '纬度',
  `heigh` bigint(20) DEFAULT NULL COMMENT '高度',
  `infoTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息产生的时间',
  `speed` int(11) DEFAULT NULL COMMENT '速度（Km/h）',
  `direction` int(11) DEFAULT NULL COMMENT '方向（度）',
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `srcId` (`srcId`),
  KEY `dstId` (`dstId`),
  KEY `infoTime` (`infoTime`)
) ENGINE=InnoDB  DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS信息表' AUTO_INCREMENT=124 ;

--
-- 转存表中的数据 `xhgmnet_gpsinfo06`
--

INSERT INTO `xhgmnet_gpsinfo06` (`id`, `writeTime`, `infoType`, `srcId`, `dstId`, `longitude`, `latitude`, `heigh`, `infoTime`, `speed`, `direction`, `typeId`) VALUES
(1, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(2, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(3, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(4, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(5, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(6, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(7, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(8, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(9, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(10, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(11, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(12, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(13, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(14, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(15, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(16, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(17, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(18, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(19, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(20, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(21, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(22, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(23, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(24, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(25, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(26, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(27, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(28, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(29, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(30, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(31, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(32, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(33, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(34, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(35, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(36, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(37, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(38, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(39, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(40, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(41, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(42, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(43, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(44, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(45, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(46, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(47, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(48, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(49, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(50, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(51, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(52, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(53, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(54, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(55, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(56, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(57, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(58, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(59, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(60, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(61, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(62, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(63, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(64, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(65, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(66, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(67, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(68, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(69, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(70, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(71, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(72, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(73, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(74, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(75, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(76, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(77, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(78, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(79, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(80, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(81, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(82, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(83, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(84, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(85, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(86, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(87, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(88, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(89, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(90, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(91, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(92, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(93, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(94, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(95, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(96, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(97, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(98, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(99, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(100, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(101, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(102, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(103, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(104, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(105, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(106, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(107, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(108, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(109, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(110, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(111, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(112, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(113, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(114, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(115, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(116, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(117, '2017-06-26 00:00:00', '11', 80020200, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(118, '2017-06-26 00:00:00', '11', 80020201, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(119, '2017-06-26 00:00:00', '11', 80020201, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(120, '2017-06-26 00:00:00', '11', 80020201, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(121, '2017-06-26 00:00:00', '11', 80020201, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(122, '2017-06-26 00:00:00', '11', 80020201, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1),
(123, '2017-06-26 00:00:00', '11', 80020201, 80020900, 104.369, 39.32568, 30, '0000-00-00 00:00:00', 10, 1, 1);

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsinfo07`
--

DROP TABLE IF EXISTS `xhgmnet_gpsinfo07`;
CREATE TABLE IF NOT EXISTS `xhgmnet_gpsinfo07` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息写入时间',
  `infoType` varchar(255) DEFAULT NULL COMMENT '信息类型',
  `srcId` bigint(20) NOT NULL COMMENT '上传信息源ID',
  `dstId` bigint(20) NOT NULL COMMENT '上传信息目标ID',
  `longitude` double NOT NULL COMMENT '经度',
  `latitude` double NOT NULL COMMENT '纬度',
  `heigh` bigint(20) DEFAULT NULL COMMENT '高度',
  `infoTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息产生的时间',
  `speed` int(11) DEFAULT NULL COMMENT '速度（Km/h）',
  `direction` int(11) DEFAULT NULL COMMENT '方向（度）',
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `srcId` (`srcId`),
  KEY `dstId` (`dstId`),
  KEY `infoTime` (`infoTime`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS信息表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsinfo08`
--

DROP TABLE IF EXISTS `xhgmnet_gpsinfo08`;
CREATE TABLE IF NOT EXISTS `xhgmnet_gpsinfo08` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息写入时间',
  `infoType` varchar(255) DEFAULT NULL COMMENT '信息类型',
  `srcId` bigint(20) NOT NULL COMMENT '上传信息源ID',
  `dstId` bigint(20) NOT NULL COMMENT '上传信息目标ID',
  `longitude` double NOT NULL COMMENT '经度',
  `latitude` double NOT NULL COMMENT '纬度',
  `heigh` bigint(20) DEFAULT NULL COMMENT '高度',
  `infoTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息产生的时间',
  `speed` int(11) DEFAULT NULL COMMENT '速度（Km/h）',
  `direction` int(11) DEFAULT NULL COMMENT '方向（度）',
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `srcId` (`srcId`),
  KEY `dstId` (`dstId`),
  KEY `infoTime` (`infoTime`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS信息表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsinfo09`
--

DROP TABLE IF EXISTS `xhgmnet_gpsinfo09`;
CREATE TABLE IF NOT EXISTS `xhgmnet_gpsinfo09` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息写入时间',
  `infoType` varchar(255) DEFAULT NULL COMMENT '信息类型',
  `srcId` bigint(20) NOT NULL COMMENT '上传信息源ID',
  `dstId` bigint(20) NOT NULL COMMENT '上传信息目标ID',
  `longitude` double NOT NULL COMMENT '经度',
  `latitude` double NOT NULL COMMENT '纬度',
  `heigh` bigint(20) DEFAULT NULL COMMENT '高度',
  `infoTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息产生的时间',
  `speed` int(11) DEFAULT NULL COMMENT '速度（Km/h）',
  `direction` int(11) DEFAULT NULL COMMENT '方向（度）',
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `srcId` (`srcId`),
  KEY `dstId` (`dstId`),
  KEY `infoTime` (`infoTime`)
) ENGINE=InnoDB  DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS信息表' AUTO_INCREMENT=13 ;

--
-- 转存表中的数据 `xhgmnet_gpsinfo09`
--

INSERT INTO `xhgmnet_gpsinfo09` (`id`, `writeTime`, `infoType`, `srcId`, `dstId`, `longitude`, `latitude`, `heigh`, `infoTime`, `speed`, `direction`, `typeId`) VALUES
(1, '2017-09-25 07:19:19', '1', 80020200, 80020900, 104.326, 34.365, 15, '2017-09-25 06:16:12', 2, NULL, 1),
(2, '2017-09-25 07:19:19', '1', 80020200, 80020900, 104.326, 34.365, 15, '2017-09-25 06:16:12', 2, NULL, 1),
(3, '2017-09-25 07:19:19', '1', 80020200, 80020900, 104.326, 34.365, 15, '2017-09-25 06:16:12', 2, NULL, 1),
(4, '2017-09-25 07:19:19', '1', 80020200, 80020900, 104.326, 34.365, 15, '2017-09-25 06:16:12', 2, NULL, 1),
(5, '2017-09-25 07:19:19', '1', 80020200, 80020900, 104.326, 34.365, 15, '2017-09-25 06:16:12', 2, NULL, 1),
(6, '2017-09-25 07:19:19', '1', 80020200, 80020900, 104.326, 34.365, 15, '2017-09-25 06:16:12', 2, NULL, 1),
(7, '2017-09-25 07:19:19', '1', 80020200, 80020900, 104.326, 34.365, 15, '2017-09-25 06:16:12', 2, NULL, 1),
(8, '2017-09-25 07:19:19', '1', 80020200, 80020900, 104.326, 34.365, 15, '2017-09-25 06:16:12', 2, NULL, 1),
(9, '2017-09-25 07:19:19', '1', 80020200, 80020900, 104.326, 34.365, 15, '2017-09-25 06:16:12', 2, NULL, 1),
(10, '2017-09-25 07:19:19', '1', 80020200, 80020900, 104.326, 34.365, 15, '2017-09-25 06:16:12', 2, NULL, 1),
(11, '2017-09-25 07:19:19', '1', 80020200, 80020900, 104.326, 34.365, 15, '2017-09-25 06:16:12', 2, NULL, 1),
(12, '2017-09-25 07:19:19', '1', 80020200, 80020900, 104.326, 34.365, 15, '2017-09-25 06:16:12', 2, NULL, 1);

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsinfo10`
--

DROP TABLE IF EXISTS `xhgmnet_gpsinfo10`;
CREATE TABLE IF NOT EXISTS `xhgmnet_gpsinfo10` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息写入时间',
  `infoType` varchar(255) DEFAULT NULL COMMENT '信息类型',
  `srcId` bigint(20) NOT NULL COMMENT '上传信息源ID',
  `dstId` bigint(20) NOT NULL COMMENT '上传信息目标ID',
  `longitude` double NOT NULL COMMENT '经度',
  `latitude` double NOT NULL COMMENT '纬度',
  `heigh` bigint(20) DEFAULT NULL COMMENT '高度',
  `infoTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息产生的时间',
  `speed` int(11) DEFAULT NULL COMMENT '速度（Km/h）',
  `direction` int(11) DEFAULT NULL COMMENT '方向（度）',
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `srcId` (`srcId`),
  KEY `dstId` (`dstId`),
  KEY `infoTime` (`infoTime`)
) ENGINE=InnoDB  DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS信息表' AUTO_INCREMENT=108476243 ;

--
-- 转存表中的数据 `xhgmnet_gpsinfo10`
--

INSERT INTO `xhgmnet_gpsinfo10` (`id`, `writeTime`, `infoType`, `srcId`, `dstId`, `longitude`, `latitude`, `heigh`, `infoTime`, `speed`, `direction`, `typeId`) VALUES
(108476124, '0000-00-00 00:00:00', '定距离触发', 8019135, 8009850, 104.06365871, 30.62253356, 0, '2016-11-18 18:52:23', 38, 4, 0),
(108476125, '0000-00-00 00:00:00', '集群模式', 8013347, 8009850, 0, 0, 0, '2016-11-18 18:52:23', 127, 15, 0),
(108476126, '0000-00-00 00:00:00', '定距离触发', 8017412, 8009850, 104.0700531, 30.3327477, 0, '2016-11-18 18:52:23', 35, 0, 0),
(108476127, '0000-00-00 00:00:00', '定时触发', 8016219, 8009850, 0, 0, 0, '2016-11-18 18:52:23', 127, 15, 0),
(108476128, '0000-00-00 00:00:00', '定时触发', 8013402, 8009850, 104.0961349, 30.60829639, 0, '2016-11-18 18:52:24', 0, 11, 0),
(108476129, '0000-00-00 00:00:00', '定距离触发', 8016232, 8009850, 104.05216813, 30.61633229, 0, '2016-11-18 18:52:24', 26, 6, 0),
(108476130, '0000-00-00 00:00:00', '定时触发', 8011414, 8009850, 103.97990942, 30.5853796, 0, '2016-11-18 18:52:24', 0, 5, 0),
(108476131, '0000-00-00 00:00:00', '定时触发', 8012038, 8009850, 104.03381109, 30.68089843, 0, '2016-11-18 18:52:24', 127, 15, 0),
(108476132, '0000-00-00 00:00:00', '恢复定位能力', 8015974, 8009850, 104.11351562, 30.65504193, 0, '2016-11-18 18:52:24', 127, 15, 0),
(108476133, '0000-00-00 00:00:00', '恢复定位能力', 8015974, 8009850, 104.11351562, 30.65504193, 0, '2016-11-18 18:52:24', 127, 15, 0),
(108476134, '0000-00-00 00:00:00', '定距离触发', 8016427, 8009850, 104.09371018, 30.55450201, 0, '2016-11-18 18:52:24', 18, 7, 0),
(108476142, '0000-00-00 00:00:00', '定距离触发', 8016147, 8009850, 104.06479597, 30.5813992, 0, '2016-11-18 18:52:24', 35, 4, 0),
(108476143, '0000-00-00 00:00:00', '定距离触发', 8015304, 8009850, 104.11154151, 30.65259576, 0, '2016-11-18 18:52:24', 40, 9, 0),
(108476144, '0000-00-00 00:00:00', '定距离触发', 8016534, 8009850, 104.06301498, 30.60529232, 0, '2016-11-18 18:52:24', 40, 4, 0),
(108476145, '0000-00-00 00:00:00', '定距离触发', 8013971, 8009850, 104.07314301, 30.65127611, 0, '2016-11-18 18:52:24', 0, 6, 0),
(108476146, '0000-00-00 00:00:00', '定距离触发', 8015404, 8009850, 104.16187048, 30.67393541, 0, '2016-11-18 18:52:24', 44, 15, 0),
(108476147, '0000-00-00 00:00:00', '定距离触发', 8012404, 8009850, 104.08534169, 30.69209933, 0, '2016-11-18 18:52:24', 35, 1, 0),
(108476148, '0000-00-00 00:00:00', '丢失定位能力', 8012236, 8009850, 104.07110453, 30.70348263, 0, '2016-11-18 18:52:24', 127, 15, 0),
(108476149, '0000-00-00 00:00:00', '恢复定位能力', 8016229, 8009850, 104.06947374, 30.60547471, 0, '2016-11-18 18:52:24', 127, 15, 0),
(108476150, '0000-00-00 00:00:00', '恢复定位能力', 8019138, 8009850, 104.03177261, 30.62923908, 0, '2016-11-18 18:52:24', 127, 15, 0),
(108476151, '0000-00-00 00:00:00', '定距离触发', 8014981, 8009850, 104.03851032, 30.65515995, 0, '2016-11-18 18:52:24', 8, 9, 0),
(108476152, '0000-00-00 00:00:00', '定时触发', 8012234, 8009850, 104.0861249, 30.68517923, 0, '2016-11-18 18:52:24', 127, 15, 0),
(108476153, '0000-00-00 00:00:00', '定时触发', 8011993, 8009850, 104.06479597, 30.614959, 0, '2016-11-18 18:52:24', 8, 15, 0),
(108476154, '0000-00-00 00:00:00', '定时触发', 8013519, 8009850, 104.14336324, 30.63068748, 0, '2016-11-18 18:52:24', 127, 15, 0),
(108476155, '0000-00-00 00:00:00', '定时触发', 7010248, 8000695, 104.02389765, 30.31550646, 0, '2016-11-18 18:51:46', 3, 0, 0),
(108476156, '0000-00-00 00:00:00', '定距离触发', 8016999, 8009850, 104.06365871, 30.56268811, 0, '2016-11-18 18:52:24', 4, 3, 0),
(108476160, '0000-00-00 00:00:00', '定时触发', 7005238, 8000695, 0, 0, 0, '2016-11-18 18:51:47', 0, 0, 0),
(108476161, '0000-00-00 00:00:00', '定时触发', 7005270, 8000695, 104.02552843, 30.70085406, 0, '2016-11-18 18:51:47', 1, 0, 0),
(108476162, '0000-00-00 00:00:00', '定距离触发', 8016516, 8009850, 103.9750278, 30.71841717, 0, '2016-11-18 18:52:26', 127, 15, 0),
(108476167, '0000-00-00 00:00:00', '定时触发', 7088771, 7088899, 103.96890163, 30.53394556, 0, '2016-11-18 18:51:39', 127, 15, 0),
(108476168, '0000-00-00 00:00:00', '定距离触发', 8013234, 8009850, 104.08209085, 30.6454289, 0, '2016-11-18 18:52:28', 18, 13, 0),
(108476169, '0000-00-00 00:00:00', '恢复定位能力', 8015982, 8009850, 104.11147714, 30.65483809, 0, '2016-11-18 18:52:28', 8, 1, 0),
(108476170, '0000-00-00 00:00:00', '定时触发', 7010249, 8000695, 104.02387619, 30.31554937, 0, '2016-11-18 18:51:50', 2, 0, 0),
(108476171, '0000-00-00 00:00:00', '定距离触发', 8016524, 8009850, 104.00191426, 30.58867335, 0, '2016-11-18 18:52:28', 40, 4, 0),
(108476172, '0000-00-00 00:00:00', '定距离触发', 8016985, 8009850, 104.03674006, 30.61628938, 0, '2016-11-18 18:52:28', 8, 9, 0),
(108476173, '0000-00-00 00:00:00', '定距离触发', 8013983, 8009850, 104.10078049, 30.61787724, 0, '2016-11-18 18:52:28', 127, 15, 0),
(108476174, '0000-00-00 00:00:00', '开机', 7088815, 7088899, 0, 0, 0, '2016-11-18 18:51:40', 127, 15, 0),
(108476175, '0000-00-00 00:00:00', '定距离触发', 8014420, 8009850, 104.04904604, 30.678581, 0, '2016-11-18 18:52:29', 22, 4, 0),
(108476176, '0000-00-00 00:00:00', '定距离触发', 8015114, 8009850, 104.10586596, 30.69622993, 0, '2016-11-18 18:52:29', 11, 1, 0),
(108476177, '0000-00-00 00:00:00', '定时触发', 7007317, 8000695, 104.06556845, 30.62728643, 0, '2016-11-18 18:51:51', 1, 0, 0),
(108476178, '0000-00-00 00:00:00', '定距离触发', 8014433, 8009850, 104.06716704, 30.6810379, 0, '2016-11-18 18:52:29', 35, 14, 0),
(108476179, '0000-00-00 00:00:00', '定时触发', 7005344, 8000695, 0, 0, 0, '2016-11-18 18:51:51', 0, 0, 0),
(108476180, '0000-00-00 00:00:00', '定距离触发', 8015990, 8009850, 104.10414934, 30.68105936, 0, '2016-11-18 18:52:29', 49, 15, 0),
(108476181, '0000-00-00 00:00:00', '定时触发', 7007319, 8000695, 104.0472436, 30.64695239, 0, '2016-11-18 18:51:51', 1, 0, 0),
(108476182, '0000-00-00 00:00:00', '恢复定位能力', 8011218, 8009850, 104.06302571, 30.64760685, 0, '2016-11-18 18:52:30', 127, 15, 0),
(108476183, '0000-00-00 00:00:00', '定时触发', 7007287, 8000695, 104.01973486, 30.6287241, 0, '2016-11-18 18:51:52', 1, 0, 0),
(108476184, '0000-00-00 00:00:00', '定时触发', 7007267, 8000695, 104.0376842, 30.61780214, 0, '2016-11-18 18:51:52', 10, 1, 0),
(108476185, '0000-00-00 00:00:00', '恢复定位能力', 8011943, 8009850, 104.01828647, 30.644871, 0, '2016-11-18 18:52:30', 127, 15, 0),
(108476186, '0000-00-00 00:00:00', '定时触发', 7007290, 8000695, 104.02482033, 30.63573003, 0, '2016-11-18 18:51:52', 1, 0, 0),
(108476191, '0000-00-00 00:00:00', '定时触发', 7080222, 8000695, 0, 0, 0, '2016-11-18 18:51:53', 0, 0, 0),
(108476192, '0000-00-00 00:00:00', '定距离触发', 8017122, 8009850, 104.06808972, 30.5139792, 0, '2016-11-18 18:52:31', 32, 15, 0),
(108476193, '0000-00-00 00:00:00', '定时触发', 8002167, 8000695, 0, 0, 0, '2016-11-18 18:51:53', 127, 15, 0),
(108476194, '0000-00-00 00:00:00', '定时触发', 7005294, 8000695, 0, 0, 0, '2016-11-18 18:51:53', 0, 0, 0),
(108476195, '0000-00-00 00:00:00', '定时触发', 7088756, 7088899, 104.03225541, 30.52652121, 0, '2016-11-18 18:51:43', 127, 15, 0),
(108476196, '0000-00-00 00:00:00', '定距离触发', 8016982, 8009850, 104.0429306, 30.56954384, 0, '2016-11-18 18:52:32', 52, 7, 0),
(108476197, '0000-00-00 00:00:00', '定距离触发', 8013968, 8009850, 104.10387039, 30.64763904, 0, '2016-11-18 18:52:32', 0, 3, 0),
(108476198, '0000-00-00 00:00:00', '定时触发', 8012111, 8009850, 104.06391621, 30.69787145, 0, '2016-11-18 18:52:32', 2, 0, 0),
(108476199, '0000-00-00 00:00:00', '定时触发', 7005232, 8000695, 0, 0, 0, '2016-11-18 18:51:54', 0, 0, 0),
(108476200, '0000-00-00 00:00:00', '定距离触发', 7088675, 7088899, 103.9101398, 30.5870533, 0, '2016-11-18 18:51:44', 32, 12, 0),
(108476201, '0000-00-00 00:00:00', '集群模式', 8014974, 8009850, 0, 0, 0, '2016-11-18 18:52:33', 127, 15, 0),
(108476202, '0000-00-00 00:00:00', '定时触发', 8016615, 8009850, 0, 0, 0, '2016-11-18 18:52:33', 127, 15, 0),
(108476203, '0000-00-00 00:00:00', '定距离触发', 8016720, 8009850, 104.04239416, 30.62504411, 0, '2016-11-18 18:52:33', 26, 1, 0),
(108476204, '0000-00-00 00:00:00', '定距离触发', 8012204, 8009850, 104.07188773, 30.68796873, 0, '2016-11-18 18:52:33', 35, 5, 0),
(108476205, '0000-00-00 00:00:00', '开机', 8014997, 8009850, 0, 0, 0, '2016-11-18 18:52:33', 127, 15, 0),
(108476207, '0000-00-00 00:00:00', '开机', 8012355, 8009850, 0, 0, 0, '2016-11-18 18:52:33', 127, 15, 0),
(108476208, '0000-00-00 00:00:00', '开机', 8015420, 8009850, 0, 0, 0, '2016-11-18 18:52:33', 127, 15, 0),
(108476209, '0000-00-00 00:00:00', '定时触发', 7003356, 8000695, 104.09636021, 30.63483953, 0, '2016-11-18 18:51:55', 127, 15, 0),
(108476210, '0000-00-00 00:00:00', '定距离触发', 8014317, 8009850, 104.04585958, 30.65609336, 0, '2016-11-18 18:52:34', 35, 13, 0),
(108476211, '0000-00-00 00:00:00', '定时触发', 7008245, 8000695, 104.04165387, 30.55905104, 0, '2016-11-18 18:51:56', 127, 15, 0),
(108476212, '0000-00-00 00:00:00', '定距离触发', 8011320, 8009850, 104.02995944, 30.63767195, 0, '2016-11-18 18:52:35', 127, 15, 0),
(108476213, '0000-00-00 00:00:00', '恢复定位能力', 8013966, 8009850, 104.07215595, 30.64891577, 0, '2016-11-18 18:52:35', 4, 8, 0),
(108476214, '0000-00-00 00:00:00', '定时触发', 8013316, 8009850, 104.10343051, 30.64744592, 0, '2016-11-18 18:52:35', 0, 2, 0),
(108476215, '0000-00-00 00:00:00', '定时触发', 7007278, 8000695, 103.99961829, 30.58970332, 0, '2016-11-18 18:51:58', 1, 0, 0),
(108476216, '0000-00-00 00:00:00', '定距离触发', 8014983, 8009850, 104.05386329, 30.67599535, 0, '2016-11-18 18:52:36', 22, 13, 0),
(108476217, '0000-00-00 00:00:00', '定距离触发', 8016601, 8009850, 104.03212667, 30.56547761, 0, '2016-11-18 18:52:36', 18, 13, 0),
(108476218, '0000-00-00 00:00:00', '定距离触发', 8016640, 8009850, 103.94349575, 30.62517285, 0, '2016-11-18 18:52:36', 52, 8, 0),
(108476219, '0000-00-00 00:00:00', '定时触发', 7092221, 8000695, 0, 0, 0, '2016-11-18 18:51:58', 0, 0, 0),
(108476220, '0000-00-00 00:00:00', '定距离触发', 8014317, 8009850, 104.04585958, 30.65609336, 0, '2016-11-18 18:52:37', 35, 13, 0),
(108476221, '0000-00-00 00:00:00', '定距离触发', 8014401, 8009850, 104.03919697, 30.67839861, 0, '2016-11-18 18:52:37', 8, 2, 0),
(108476222, '0000-00-00 00:00:00', '开机', 8011241, 8009850, 0, 0, 0, '2016-11-18 18:52:37', 127, 15, 0),
(108476223, '0000-00-00 00:00:00', '定距离触发', 8015412, 8009850, 104.1620636, 30.69097281, 0, '2016-11-18 18:52:37', 40, 4, 0),
(108476224, '0000-00-00 00:00:00', '定时触发', 8011002, 8009850, 0, 0, 0, '2016-11-18 18:52:37', 127, 15, 0),
(108476225, '0000-00-00 00:00:00', '定时触发', 7005251, 8000695, 0, 0, 0, '2016-11-18 18:51:59', 0, 0, 0),
(108476226, '0000-00-00 00:00:00', '定距离触发', 8012521, 8009850, 104.02124763, 30.70000648, 0, '2016-11-18 18:52:37', 11, 10, 0),
(108476227, '0000-00-00 00:00:00', '定距离触发', 8016514, 8009850, 103.94718647, 30.74963808, 0, '2016-11-18 18:52:37', 4, 12, 0),
(108476228, '0000-00-00 00:00:00', '定距离触发', 8014998, 8009850, 104.03700829, 30.66052437, 0, '2016-11-18 18:52:38', 15, 10, 0),
(108476229, '0000-00-00 00:00:00', '定距离触发', 8014212, 8009850, 103.998878, 30.68410635, 0, '2016-11-18 18:52:38', 29, 13, 0),
(108476230, '0000-00-00 00:00:00', '定时触发', 7005244, 8000695, 0, 0, 0, '2016-11-18 18:52:00', 0, 0, 0),
(108476231, '0000-00-00 00:00:00', '定距离触发', 8011971, 8009850, 104.07088995, 30.62301636, 0, '2016-11-18 18:52:38', 127, 15, 0),
(108476232, '0000-00-00 00:00:00', '定距离触发', 8016227, 8009850, 103.98200154, 30.78173876, 0, '2016-11-18 18:52:39', 29, 4, 0),
(108476233, '0000-00-00 00:00:00', '定时触发', 8011144, 8009850, 104.01210666, 30.62036633, 0, '2016-11-18 18:52:39', 127, 15, 0),
(108476234, '0000-00-00 00:00:00', '定距离触发', 8016225, 8009850, 104.07543898, 30.60675144, 0, '2016-11-18 18:52:39', 127, 15, 0),
(108476235, '0000-00-00 00:00:00', '定时触发', 7005281, 8000695, 0, 0, 0, '2016-11-18 18:52:01', 0, 0, 0),
(108476236, '0000-00-00 00:00:00', '定距离触发', 8013343, 8009850, 104.09701467, 30.64490318, 0, '2016-11-18 18:52:39', 26, 13, 0),
(108476237, '0000-00-00 00:00:00', '定距离触发', 8011982, 8009850, 104.02921915, 30.65009594, 0, '2016-11-18 18:52:39', 11, 4, 0),
(108476238, '0000-00-00 00:00:00', '定距离触发', 8012432, 8009850, 104.08332467, 30.68721771, 0, '2016-11-18 18:52:40', 22, 5, 0),
(108476239, '0000-00-00 00:00:00', '定距离触发', 7003350, 8000695, 104.07632947, 30.65729499, 0, '2016-11-18 18:52:02', 127, 15, 0),
(108476240, '0000-00-00 00:00:00', '定距离触发', 8013103, 8009850, 104.10411716, 30.64723134, 0, '2016-11-18 18:52:40', 38, 6, 0),
(108476241, '0000-00-00 00:00:00', '恢复定位能力', 8011944, 8009850, 104.01953101, 30.65289617, 0, '2016-11-18 18:52:40', 127, 15, 0),
(108476242, '0000-00-00 00:00:00', '定时触发', 8012028, 8009850, 104.08594251, 30.68534017, 0, '2016-11-18 18:52:40', 4, 15, 0);

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsinfo11`
--

DROP TABLE IF EXISTS `xhgmnet_gpsinfo11`;
CREATE TABLE IF NOT EXISTS `xhgmnet_gpsinfo11` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息写入时间',
  `infoType` varchar(255) DEFAULT NULL COMMENT '信息类型',
  `srcId` bigint(20) NOT NULL COMMENT '上传信息源ID',
  `dstId` bigint(20) NOT NULL COMMENT '上传信息目标ID',
  `longitude` double NOT NULL COMMENT '经度',
  `latitude` double NOT NULL COMMENT '纬度',
  `heigh` bigint(20) DEFAULT NULL COMMENT '高度',
  `infoTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息产生的时间',
  `speed` int(11) DEFAULT NULL COMMENT '速度（Km/h）',
  `direction` int(11) DEFAULT NULL COMMENT '方向（度）',
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `srcId` (`srcId`),
  KEY `dstId` (`dstId`),
  KEY `infoTime` (`infoTime`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS信息表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsinfo12`
--

DROP TABLE IF EXISTS `xhgmnet_gpsinfo12`;
CREATE TABLE IF NOT EXISTS `xhgmnet_gpsinfo12` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息写入时间',
  `infoType` varchar(255) DEFAULT NULL COMMENT '信息类型',
  `srcId` bigint(20) NOT NULL COMMENT '上传信息源ID',
  `dstId` bigint(20) NOT NULL COMMENT '上传信息目标ID',
  `longitude` double NOT NULL COMMENT '经度',
  `latitude` double NOT NULL COMMENT '纬度',
  `heigh` bigint(20) DEFAULT NULL COMMENT '高度',
  `infoTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '信息产生的时间',
  `speed` int(11) DEFAULT NULL COMMENT '速度（Km/h）',
  `direction` int(11) DEFAULT NULL COMMENT '方向（度）',
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `srcId` (`srcId`),
  KEY `dstId` (`dstId`),
  KEY `infoTime` (`infoTime`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS信息表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsoperation`
--

DROP TABLE IF EXISTS `xhgmnet_gpsoperation`;
CREATE TABLE IF NOT EXISTS `xhgmnet_gpsoperation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '操作时间',
  `operationType` varchar(255) DEFAULT NULL COMMENT '操作类型',
  `srcId` bigint(20) NOT NULL COMMENT '操作的源Id或用户',
  `dstId` bigint(20) NOT NULL COMMENT '操作的目标ID',
  `interval` int(11) DEFAULT NULL COMMENT '定时触发器（秒）',
  `distance` int(11) DEFAULT NULL COMMENT '定距离触发器（米）',
  `status` int(1) DEFAULT NULL COMMENT '操作状态0：成功 1：失败, 2:等待回复',
  PRIMARY KEY (`id`),
  KEY `gpsoperation_index` (`writeTime`,`srcId`,`dstId`)
) ENGINE=InnoDB  DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS操作记录' AUTO_INCREMENT=28137074 ;

--
-- 转存表中的数据 `xhgmnet_gpsoperation`
--

INSERT INTO `xhgmnet_gpsoperation` (`id`, `writeTime`, `operationType`, `srcId`, `dstId`, `interval`, `distance`, `status`) VALUES
(28136974, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8019140, NULL, NULL, 0),
(28136975, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8016528, NULL, NULL, 0),
(28136976, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8016524, NULL, NULL, 0),
(28136977, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8016629, NULL, NULL, 0),
(28136978, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8011241, NULL, NULL, 0),
(28136979, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8011057, NULL, NULL, 0),
(28136980, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012007, NULL, NULL, 0),
(28136981, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012243, NULL, NULL, 0),
(28136982, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8008432, NULL, NULL, 0),
(28136983, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012440, NULL, NULL, 0),
(28136984, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012316, NULL, NULL, 0),
(28136985, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012437, NULL, NULL, 0),
(28136986, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012234, NULL, NULL, 0),
(28136987, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012174, NULL, NULL, 0),
(28136988, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8016715, NULL, NULL, 0),
(28136989, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012719, NULL, NULL, 0),
(28136990, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012210, NULL, NULL, 0),
(28136991, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012402, NULL, NULL, 0),
(28136992, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8015135, NULL, NULL, 0),
(28136993, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012447, NULL, NULL, 0),
(28136994, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8008515, NULL, NULL, 0),
(28136995, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8008516, NULL, NULL, 0),
(28136996, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012213, NULL, NULL, 0),
(28136997, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8013726, NULL, NULL, 0),
(28136998, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8013125, NULL, NULL, 0),
(28136999, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8013123, NULL, NULL, 0),
(28137000, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8013729, NULL, NULL, 0),
(28137001, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8019121, NULL, NULL, 0),
(28137002, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8019165, NULL, NULL, 0),
(28137003, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8019130, NULL, NULL, 0),
(28137004, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8016717, NULL, NULL, 0),
(28137005, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8016412, NULL, NULL, 0),
(28137006, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8019133, NULL, NULL, 0),
(28137007, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8014235, NULL, NULL, 0),
(28137008, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8014054, NULL, NULL, 0),
(28137009, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8016117, NULL, NULL, 0),
(28137010, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8013329, NULL, NULL, 0),
(28137011, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8019124, NULL, NULL, 0),
(28137012, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012221, NULL, NULL, 0),
(28137013, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8015221, NULL, NULL, 0),
(28137014, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8016052, NULL, NULL, 0),
(28137015, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8014448, NULL, NULL, 0),
(28137016, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8016538, NULL, NULL, 0),
(28137017, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8011348, NULL, NULL, 0),
(28137018, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012985, NULL, NULL, 0),
(28137019, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8016535, NULL, NULL, 0),
(28137020, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8016644, NULL, NULL, 0),
(28137021, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012723, NULL, NULL, 0),
(28137022, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8009051, NULL, NULL, 0),
(28137023, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8016218, NULL, NULL, 0),
(28137024, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8013431, NULL, NULL, 0),
(28137025, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8019126, NULL, NULL, 0),
(28137026, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012038, NULL, NULL, 0),
(28137027, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8011212, NULL, NULL, 0),
(28137028, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8013237, NULL, NULL, 0),
(28137029, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8013234, NULL, NULL, 0),
(28137030, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012525, NULL, NULL, 0),
(28137031, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8011727, NULL, NULL, 0),
(28137032, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8011114, NULL, NULL, 0),
(28137033, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8011211, NULL, NULL, 0),
(28137034, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8012325, NULL, NULL, 0),
(28137035, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8016990, NULL, NULL, 0),
(28137036, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8016214, NULL, NULL, 0),
(28137037, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8013704, NULL, NULL, 0),
(28137038, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8013515, NULL, NULL, 0),
(28137039, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8011555, NULL, NULL, 0),
(28137040, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8016230, NULL, NULL, 0),
(28137041, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8013052, NULL, NULL, 0),
(28137042, '2016-11-07 02:08:38', '立即请求GPS', 8009850, 8019101, NULL, NULL, 0),
(28137043, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8015701, NULL, NULL, 0),
(28137044, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8011328, NULL, NULL, 0),
(28137045, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8010203, NULL, NULL, 0),
(28137046, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8015401, NULL, NULL, 0),
(28137047, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8015007, NULL, NULL, 0),
(28137048, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8008159, NULL, NULL, 0),
(28137049, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8015030, NULL, NULL, 0),
(28137050, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8015301, NULL, NULL, 0),
(28137051, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8008160, NULL, NULL, 0),
(28137052, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8008161, NULL, NULL, 0),
(28137053, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8008162, NULL, NULL, 0),
(28137054, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8008163, NULL, NULL, 0),
(28137055, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8013997, NULL, NULL, 0),
(28137056, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8015133, NULL, NULL, 0),
(28137057, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8019113, NULL, NULL, 0),
(28137058, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8011301, NULL, NULL, 0),
(28137059, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8011220, NULL, NULL, 0),
(28137060, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8015328, NULL, NULL, 0),
(28137061, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8016620, NULL, NULL, 0),
(28137062, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8011346, NULL, NULL, 0),
(28137063, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8014003, NULL, NULL, 0),
(28137064, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8014408, NULL, NULL, 0),
(28137065, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8019106, NULL, NULL, 0),
(28137066, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8013420, NULL, NULL, 0),
(28137067, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8014301, NULL, NULL, 0),
(28137068, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8011710, NULL, NULL, 0),
(28137069, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8019945, NULL, NULL, 0),
(28137070, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8019973, NULL, NULL, 0),
(28137071, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8014971, NULL, NULL, 0),
(28137072, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8014972, NULL, NULL, 0),
(28137073, '2016-11-07 02:11:38', '立即请求GPS', 8009850, 8011235, NULL, NULL, 0);

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_group_call`
--

DROP TABLE IF EXISTS `xhgmnet_group_call`;
CREATE TABLE IF NOT EXISTS `xhgmnet_group_call` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `startTime` datetime NOT NULL COMMENT '测量开始时间',
  `groupId` int(11) NOT NULL COMMENT '通话组ID',
  `activityCallTotals` int(11) NOT NULL COMMENT '活动呼叫总数',
  `activityCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '活动呼叫总持续时间',
  `averageCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '平均呼叫持续时间',
  `callTotals` int(11) NOT NULL COMMENT '呼叫总数',
  `callLossTotals` int(11) NOT NULL COMMENT '呼损总数',
  `callErrorTotals` int(11) NOT NULL COMMENT '未成功呼叫总数',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='通话组话务总计' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_msc_call`
--

DROP TABLE IF EXISTS `xhgmnet_msc_call`;
CREATE TABLE IF NOT EXISTS `xhgmnet_msc_call` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `startTime` datetime NOT NULL COMMENT '测量开始时间',
  `mscId` int(11) NOT NULL COMMENT '交换中心ID',
  `activityCallTotals` int(11) NOT NULL COMMENT '活动呼叫总数',
  `activityCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '活动呼叫总持续时间',
  `averageCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '平均呼叫持续时间',
  `pttTotals` int(11) NOT NULL COMMENT '总PTT数',
  `callTotals` int(11) NOT NULL COMMENT '呼叫总数',
  `callLossTotals` int(11) NOT NULL COMMENT '呼损总数',
  `callDropRate` varchar(10) CHARACTER SET utf8mb4 NOT NULL COMMENT '呼损率',
  `callErrorTotals` int(11) NOT NULL COMMENT '未成功呼叫总数',
  `maxRegisterUser` int(11) NOT NULL COMMENT '最大用户注册数',
  `waitTotals` int(11) NOT NULL COMMENT '排队数量',
  `waitTime` varchar(10) CHARACTER SET utf8mb4 NOT NULL COMMENT '排队持续时间',
  `maxRegisterGroup` int(11) NOT NULL DEFAULT '0' COMMENT '最大组注册数',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='交换中心话务总量' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_msc_call_loss`
--

DROP TABLE IF EXISTS `xhgmnet_msc_call_loss`;
CREATE TABLE IF NOT EXISTS `xhgmnet_msc_call_loss` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `startTime` datetime NOT NULL COMMENT '测量开始时间',
  `mscId` int(11) NOT NULL COMMENT '交换中心ID',
  `groupCallLossTotals` int(11) NOT NULL COMMENT '组呼呼损个数',
  `singleCallLossTotals` int(11) NOT NULL COMMENT '个呼呼损个数',
  `emergentCallLossTotals` int(11) NOT NULL COMMENT '紧急呼叫呼损个数',
  `phoneCallLossTotals` int(11) NOT NULL COMMENT '电话呼叫呼损个数',
  `fullDuplexCallLossTotals` int(11) NOT NULL COMMENT '全双工呼叫呼损个数',
  `halfDuplexCallLossTotals` int(11) NOT NULL COMMENT '半双工个呼呼损个数',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='交换中心呼损详情' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_msc_call_success`
--

DROP TABLE IF EXISTS `xhgmnet_msc_call_success`;
CREATE TABLE IF NOT EXISTS `xhgmnet_msc_call_success` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `startTime` datetime NOT NULL COMMENT '测量开始时间',
  `mscId` int(11) NOT NULL COMMENT '交换中心ID',
  `groupCallNumber` int(11) NOT NULL COMMENT '组呼个数',
  `groupCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '组呼持续时间',
  `singleCallNumber` int(11) NOT NULL COMMENT '个呼个数',
  `singleCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '个呼持续时间',
  `emergentCallTotals` int(11) NOT NULL COMMENT '紧急呼叫个数',
  `emergentCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '紧急呼叫持续时间',
  `phoneCallTotals` int(11) NOT NULL DEFAULT '0' COMMENT '电话呼叫个数',
  `phoneCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '电话呼叫持续时间 ',
  `fullDuplexCallTotals` int(11) NOT NULL COMMENT '全双工呼叫个数',
  `halfDuplexCallTotals` int(11) NOT NULL COMMENT '半双工个呼个数',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='交换中心成功呼叫详情' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_recvsms`
--

DROP TABLE IF EXISTS `xhgmnet_recvsms`;
CREATE TABLE IF NOT EXISTS `xhgmnet_recvsms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '短信写入时间',
  `srcId` bigint(20) NOT NULL COMMENT '短信源Id',
  `dstId` bigint(20) NOT NULL COMMENT '短信目标ID',
  `refNum` bigint(20) DEFAULT NULL COMMENT '参考号',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '短信内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='短信接收表' AUTO_INCREMENT=101 ;

--
-- 转存表中的数据 `xhgmnet_recvsms`
--

INSERT INTO `xhgmnet_recvsms` (`id`, `writeTime`, `srcId`, `dstId`, `refNum`, `content`) VALUES
(1, '2015-02-09 10:37:05', 1999152, 8000695, 9, '经济贫'),
(2, '2015-02-09 10:37:58', 1999152, 8000695, 9, '经济贫'),
(3, '2015-02-09 10:39:10', 1999152, 8000695, 9, '经济贫'),
(4, '2015-02-09 10:39:38', 1999152, 8000695, 9, '经济贫'),
(5, '2015-02-09 10:40:03', 1999152, 8000695, 9, '经济贫'),
(6, '2015-02-09 10:43:15', 1999152, 8000695, 12, '经济贫呒'),
(7, '2015-02-09 10:41:38', 1999152, 8009850, 12, '经济贫呒'),
(8, '2015-02-09 14:22:54', 1999151, 8009855, 7, 'aaaaml'),
(9, '2015-02-09 14:53:55', 1999151, 8009855, 16, '短线\r\n8jgnad.'),
(10, '2015-02-09 14:56:10', 1999151, 8009855, 11, 'jkljkljljl'),
(11, '2015-02-09 14:57:03', 1999151, 8009855, 5, 'test'),
(12, '2015-02-09 14:57:47', 1999151, 8009855, 4, '321'),
(13, '2015-02-10 09:31:33', 1999151, 8009855, 13, '短信测试'),
(14, '2015-02-10 11:53:49', 1999151, 8009855, 2, 'A'),
(15, '2015-02-10 13:19:22', 1999151, 8009855, 5, 'test'),
(16, '2015-02-10 19:09:08', 8011002, 8009855, 28, '交管局短信测试信息'),
(17, '2015-02-11 09:21:38', 1999150, 8009855, 13, '短信测试'),
(18, '2015-02-11 11:46:35', 1999151, 8009855, 4, '777'),
(19, '2015-02-11 11:47:11', 1999150, 8009855, 6, '经济'),
(20, '2015-02-11 11:52:08', 1999151, 8009855, 15, 'lalalalalalazl'),
(21, '2015-02-11 15:02:12', 1999151, 8009855, 10, '啊啊啊'),
(22, '2015-02-25 09:16:52', 8016524, 8009855, 22, '中午下班不关电'),
(23, '2015-03-20 16:33:12', 8011311, 8009855, 43, '系统测试中，收到请读取，谢谢'),
(24, '2015-03-21 09:33:04', 1999151, 8009855, 5, 'test'),
(25, '2015-03-21 09:46:40', 1999151, 8009855, 5, 'test'),
(26, '2015-03-21 09:46:54', 1999151, 8009855, 5, 'test'),
(27, '2015-03-21 09:48:42', 1999151, 8009855, 5, 'test'),
(28, '2015-03-30 14:17:28', 1999269, 8009850, 10, '进行为'),
(29, '2015-04-23 14:41:44', 7088801, 7088899, 13, '123中国abc'),
(30, '2015-04-23 14:44:10', 7088801, 7088899, 14, '123中国abcd'),
(31, '2015-04-23 14:44:37', 1999151, 8009855, 8, 'ddddddd'),
(32, '2015-04-23 15:04:36', 1999151, 7088899, 5, 'Pgbd'),
(33, '2015-04-23 15:06:23', 7088801, 7088899, 14, '123中国abcd'),
(34, '2015-04-23 15:41:48', 7088801, 7088899, 14, '123中国abcd'),
(35, '2015-04-23 15:42:04', 7088801, 7088899, 14, '123中国abcd'),
(36, '2015-04-23 15:42:51', 7088801, 7088899, 14, '123中国abcd'),
(37, '2015-04-23 15:43:53', 7088801, 7088899, 15, '123中国abc d'),
(38, '2015-04-23 15:44:56', 7088801, 7088899, 15, '123中国abc d'),
(39, '2015-04-23 15:45:11', 7088801, 7088899, 15, '123中国abc d'),
(40, '2015-04-23 15:46:24', 7088801, 7088899, 15, '123中国abc d'),
(41, '2015-04-23 15:47:52', 7088801, 7088899, 15, '123中国abc d'),
(42, '2015-04-23 15:48:24', 7088801, 7088899, 15, '123中国abc d'),
(43, '2015-04-23 15:48:33', 7088801, 7088899, 15, '123中国abc d'),
(44, '2015-04-23 15:49:01', 7088801, 7088899, 15, '123中国abc d'),
(45, '2015-04-23 15:49:12', 7088801, 7088899, 15, '123中国abc d'),
(46, '2015-04-23 15:49:22', 7088801, 7088899, 15, '123中国abc d'),
(47, '2015-04-23 15:49:35', 7088801, 7088899, 15, '123中国abc d'),
(48, '2015-04-23 15:58:15', 7088801, 7088899, 15, '123中国abc d'),
(49, '2015-04-23 16:17:50', 1999151, 7088899, 13, '短信测试'),
(50, '2015-04-23 16:50:06', 7088801, 7088899, 15, '123中国abc d'),
(51, '2015-04-23 19:04:57', 7088801, 7088899, 15, '123中国abc d'),
(52, '2015-04-23 19:07:13', 7088801, 7088899, 15, '123中国abc d'),
(53, '2015-04-23 19:08:04', 7088801, 7088899, 15, '123中国abc d'),
(54, '2015-04-23 19:10:39', 7088801, 7088899, 15, '123中国abc d'),
(55, '2015-04-23 19:12:33', 7088801, 7088899, 15, '123中国abc d'),
(56, '2015-04-23 19:13:53', 7088801, 7088899, 15, '123中国abc d'),
(57, '2015-04-24 09:06:50', 7088801, 7088899, 15, '123中国abc d'),
(58, '2015-04-24 09:08:19', 7088801, 7088899, 15, '123中国abc d'),
(59, '2015-04-24 09:08:51', 7088801, 7088899, 15, '123中国abc d'),
(60, '2015-04-24 09:23:03', 7088801, 7088899, 15, '123中国abc d'),
(61, '2015-04-24 09:23:42', 7088801, 7088899, 15, '123中国abc d'),
(62, '2015-04-24 09:24:16', 7088801, 7088899, 15, '123中国abc d'),
(63, '2015-04-24 09:26:59', 7088801, 7088899, 15, '123中国abc d'),
(64, '2015-04-24 09:30:41', 7088801, 7088899, 15, '123中国abc d'),
(65, '2015-04-24 09:33:58', 7088801, 7088899, 15, '123中国abc d'),
(66, '2015-04-24 09:35:27', 7088801, 7088899, 15, '123中国abc d'),
(67, '2015-04-24 09:41:15', 7088801, 7088899, 15, '123中国abc d'),
(68, '2015-04-24 09:41:52', 7088801, 7088899, 15, '123中国abc d'),
(69, '2015-04-24 11:29:41', 7088801, 7088899, 15, '123中国abc d'),
(70, '2015-04-24 11:34:32', 7088801, 7088899, 15, '123中国abc d'),
(71, '2015-04-24 11:51:39', 7088801, 7088899, 15, '123中国abc d'),
(72, '2015-04-24 12:22:26', 7088801, 7088899, 15, '123中国abc d'),
(73, '2015-04-24 12:23:10', 7088801, 7088899, 15, '123中国abc d'),
(74, '2015-04-24 13:33:15', 7088801, 7088899, 15, '123中国abc d'),
(75, '2015-04-24 18:19:11', 7088801, 7088899, 15, '123中国abc d'),
(76, '2015-04-24 18:44:39', 7088801, 7088899, 15, '123中国abc d'),
(77, '2015-04-24 20:52:44', 7088801, 7088899, 15, '123中国abc d'),
(78, '2015-04-24 20:53:23', 7088801, 7088899, 15, '123中国abc d'),
(79, '2015-04-24 20:54:20', 7088801, 7088899, 15, '123中国abc d'),
(80, '2015-04-25 11:32:28', 7088777, 7088899, 19, '测试中文短信'),
(81, '2015-04-25 11:44:55', 7088777, 7088899, 19, '测试中文短信'),
(82, '2015-04-25 15:24:28', 7088777, 7088899, 19, '测试中文短信'),
(83, '2015-04-26 11:18:08', 7088801, 7088899, 15, '123中国abc d'),
(84, '2015-04-30 16:58:49', 7088801, 7088899, 15, '123中国abc d'),
(85, '2015-04-30 17:01:56', 7088801, 7088899, 15, '123中国abc d'),
(86, '2015-05-04 19:55:52', 7088801, 7088899, 15, '123中国abc d'),
(87, '2015-05-05 11:23:25', 7088801, 7088899, 15, '123中国abc d'),
(88, '2015-05-12 10:33:01', 2017020, 8000695, 10, '阿斯爱'),
(89, '2015-05-12 10:33:23', 2017020, 8000695, 3, 'Mg'),
(90, '2015-05-12 10:44:07', 2017018, 8000695, 9, 'test res'),
(91, '2015-05-12 10:45:44', 2017018, 8000695, 9, 'test res'),
(92, '2015-05-12 16:37:17', 2017020, 8009855, 4, 'Mgw'),
(93, '2015-05-12 16:42:51', 2017018, 8000695, 4, 'aaa'),
(94, '2015-05-13 12:34:26', 7088801, 7088899, 15, '123中国abc d'),
(95, '2015-05-13 14:24:11', 10004, 8000695, 2, '1'),
(96, '2015-05-13 14:24:43', 10004, 8000695, 2, '1'),
(97, '2015-05-13 14:25:03', 10004, 8000695, 3, '13'),
(98, '2015-05-13 14:25:50', 10004, 8000695, 4, '123'),
(99, '2015-05-13 14:26:23', 10004, 8000695, 4, '123'),
(100, '2015-05-13 14:27:28', 10004, 8000695, 4, '123');

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_sendsms`
--

DROP TABLE IF EXISTS `xhgmnet_sendsms`;
CREATE TABLE IF NOT EXISTS `xhgmnet_sendsms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `writeTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '短信写入时间',
  `srcId` bigint(20) NOT NULL COMMENT '短信源Id',
  `dstId` bigint(20) NOT NULL COMMENT '短信目标ID',
  `refNum` int(11) DEFAULT NULL COMMENT '参考号',
  `content` varchar(255) CHARACTER SET gb2312 DEFAULT NULL COMMENT '短信内容',
  `status` int(2) DEFAULT NULL COMMENT '发送状态0：成功；1：失败；2：已消费；3：发送中；',
  `IG` int(2) DEFAULT NULL COMMENT '单发或组发 0：单发，1：组发',
  `serNum` bigint(20) DEFAULT NULL COMMENT '流水号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='短信发送表' AUTO_INCREMENT=111 ;

--
-- 转存表中的数据 `xhgmnet_sendsms`
--

INSERT INTO `xhgmnet_sendsms` (`id`, `writeTime`, `srcId`, `dstId`, `refNum`, `content`, `status`, `IG`, `serNum`) VALUES
(1, '2015-02-09 11:49:18', 100130, 1999154, 1, 'HELLPO', 1, 0, NULL),
(2, '2015-02-09 11:49:36', 100130, 1999260, 2, 'HELLPO', 0, 0, NULL),
(3, '2015-02-09 11:50:28', 100130, 1999908, 3, 'HELLPO8', 0, 1, NULL),
(4, '2015-02-09 14:19:56', 8009855, 1999151, 2, 'duanxinasas', 0, 0, NULL),
(5, '2015-02-09 14:19:56', 8009855, 1999151, 1, 'duanxinasas', 2, 0, NULL),
(6, '2015-02-09 14:20:01', 8009855, 1999151, 3, 'duanxinasas', 0, 0, NULL),
(7, '2015-02-09 14:20:01', 8009855, 1999151, 2, 'duanxinasas', 2, 0, NULL),
(8, '2015-02-09 14:20:56', 8009855, 1999151, 4, 'aaaa', 0, 0, NULL),
(9, '2015-02-09 14:20:56', 8009855, 1999151, 3, 'aaaa', 0, 0, NULL),
(10, '2015-02-09 14:22:54', 8009855, 1999151, 122, '', 0, 0, NULL),
(11, '2015-02-09 14:22:55', 8009855, 1999151, 5, '', 1, 0, NULL),
(12, '2015-02-09 14:23:41', 8009855, 1999151, 5, '1212121', 0, 0, NULL),
(13, '2015-02-09 14:23:41', 8009855, 1999151, 6, '1212121', 2, 0, NULL),
(14, '2015-02-09 14:35:01', 8009855, 1999151, 7, '123', 2, 0, NULL),
(16, '2015-02-09 14:42:29', 8009855, 1999151, 1, 'sdfsdfsd', 0, 0, NULL),
(17, '2015-02-09 14:43:04', 8009855, 1999151, 2, 'sdfsdfsdf', 0, 0, NULL),
(18, '2015-02-09 14:43:54', 8009855, 1999151, 3, '短信测试拉拉拉拉', 0, 0, NULL),
(19, '2015-02-09 14:45:19', 8009855, 1999151, 4, '短信测试拉拉拉拉', 0, 0, NULL),
(20, '2015-02-09 14:46:04', 8009855, 1999151, 5, 'ijhjkhjkhk', 0, 0, NULL),
(21, '2015-02-09 14:46:39', 8009855, 1999151, 6, 'kkkkkkkkkkkk', 0, 0, NULL),
(22, '2015-02-09 14:47:09', 8009855, 1999151, 7, 'duanxinasas', 0, 0, NULL),
(23, '2015-02-09 14:48:07', 8009855, 1999151, 1, '6t6t6t6t6', 0, 0, NULL),
(27, '2015-02-09 14:53:12', 8009855, 1999151, 9, 'jjj', 0, 0, NULL),
(28, '2015-02-09 14:53:32', 8009855, 1999151, 10, 'llllllll', 0, 0, NULL),
(29, '2015-02-09 14:53:55', 8009855, 1999151, 0, '', 0, 0, NULL),
(30, '2015-02-09 14:54:47', 8009855, 1999151, 11, '98989', 0, 0, NULL),
(31, '2015-02-09 14:55:17', 8009855, 1999151, 12, '9999', 0, 0, NULL),
(32, '2015-02-09 14:55:52', 8009855, 1999151, 13, 'jkljkljljl', 0, 0, NULL),
(33, '2015-02-09 14:56:10', 8009855, 1999151, 0, '', 0, 0, NULL),
(34, '2015-02-09 14:56:52', 8009855, 1999151, 14, 'test', 0, 0, NULL),
(35, '2015-02-09 14:57:03', 8009855, 1999151, 0, '', 0, 0, NULL),
(36, '2015-02-09 14:57:42', 8009855, 1999151, 15, '321', 0, 0, NULL),
(37, '2015-02-09 14:57:47', 8009855, 1999151, 0, '', 0, 0, NULL),
(38, '2015-02-10 09:31:33', 8009855, 1999151, 0, '', 0, 0, NULL),
(39, '2015-02-10 11:53:49', 8009855, 1999151, 0, '', 0, 0, NULL),
(40, '2015-02-10 13:19:22', 8009855, 1999151, 0, '', 0, 0, NULL),
(41, '2015-02-10 19:09:08', 8009855, 8011002, 0, '', 0, 0, NULL),
(42, '2015-02-11 09:21:38', 8009855, 1999150, 0, '', 0, 0, NULL),
(43, '2015-02-11 09:29:17', 8009855, 1999154, 2, '短信测试', 2, 0, NULL),
(44, '2015-02-11 09:38:27', 8009855, 1999154, 3, '短信测试', 2, 0, NULL),
(45, '2015-02-11 10:08:22', 8009855, 1999151, 4, '短信测试', 0, 0, NULL),
(47, '2015-02-11 10:11:57', 8009855, 1999151, 6, '123', 0, 0, NULL),
(49, '2015-02-11 10:12:12', 8009855, 1999151, 8, '456', 0, 0, NULL),
(50, '2015-02-11 10:19:17', 8009855, 1999151, 10, '789', 2, 0, NULL),
(51, '2015-02-11 10:29:10', 8009855, 1999151, 11, '1111', 2, 0, NULL),
(52, '2015-02-11 11:26:55', 8009855, 1999151, 12, '222', 0, 0, NULL),
(53, '2015-02-11 11:28:00', 8009855, 1999151, 13, '222', 2, 0, NULL),
(54, '2015-02-11 11:28:25', 8009855, 1999151, 14, '222', 2, 0, NULL),
(55, '2015-02-11 11:37:52', 8009855, 1999151, 15, '222', 2, 0, NULL),
(56, '2015-02-11 11:39:25', 8009855, 1999151, 16, '222', 2, 0, NULL),
(57, '2015-02-11 11:40:20', 8009855, 1999151, 17, '777', 2, 0, NULL),
(58, '2015-02-11 11:41:05', 8009855, 1999151, 18, '666', 0, 0, NULL),
(59, '2015-02-11 11:45:45', 8009855, 1999150, 19, '777', 0, 0, NULL),
(60, '2015-02-11 11:46:20', 8009855, 1999151, 20, '777', 2, 0, NULL),
(61, '2015-02-11 11:46:35', 8009855, 1999151, 22, '', 1, 0, NULL),
(62, '2015-02-11 11:47:12', 8009855, 1999150, 23, '', 1, 0, NULL),
(63, '2015-02-11 11:51:49', 8009855, 1999151, 24, 'lalalalalalazl', 2, 0, NULL),
(64, '2015-02-11 11:52:09', 8009855, 1999151, 26, '', 1, 0, NULL),
(65, '2015-02-11 12:39:58', 8009855, 1999151, 27, 'lksdjflksdjlflk', 2, 0, NULL),
(66, '2015-02-11 14:20:33', 8009855, 1999154, 28, '啊洒洒撒大大', 0, 0, NULL),
(67, '2015-02-11 14:21:08', 8009855, 1999151, 29, '啊洒洒撒大大', 2, 0, NULL),
(68, '2015-02-11 14:22:53', 8009855, 1999154, 30, '啊洒洒撒大大', 0, 0, NULL),
(69, '2015-02-11 14:42:53', 8009855, 1999154, 1, '啊洒洒撒大大', 0, 0, NULL),
(70, '2015-02-11 14:43:03', 8009855, 1999151, 2, '啊洒洒撒大大', 2, 0, NULL),
(71, '2015-02-11 14:43:33', 8009855, 1999151, 3, '啊啊啊', 2, 0, NULL),
(72, '2015-02-11 15:02:12', 8009855, 1999151, 5, '', 1, 0, NULL),
(73, '2015-02-11 17:00:08', 8009855, 1999151, 6, 'lksdjflksdjlflk', 0, 0, NULL),
(74, '2015-02-11 17:00:33', 8009855, 1999151, 7, 'asadsadsd', 2, 0, NULL),
(75, '2015-02-25 09:16:52', 8009855, 8016524, 4, '', 1, 0, NULL),
(76, '2015-03-21 09:20:21', 8009855, 1999151, 6, 'test', 2, 0, NULL),
(77, '2015-03-21 09:33:04', 8009855, 1999151, 8, '', 1, 0, NULL),
(78, '2015-03-21 10:02:23', 8009855, 1999151, 12, '18280000635', 2, 0, NULL),
(83, '2015-03-30 14:13:11', 8000995, 1999154, 5, '36985kk', 3, 0, NULL),
(84, '2015-03-30 14:15:42', 8009850, 1999154, 6, '123', 0, 0, NULL),
(85, '2015-03-30 14:15:47', 8009850, 1999154, 7, '123', 0, 0, NULL),
(86, '2015-03-30 14:15:50', 8009850, 1999154, 8, '123', 0, 0, NULL),
(87, '2015-03-30 14:15:56', 8009850, 1999154, 9, '123ghj', 0, 0, NULL),
(88, '2015-03-30 14:16:05', 8009850, 1999151, 10, '123ghj', 0, 0, NULL),
(89, '2015-03-30 14:17:52', 8009850, 1999269, 12, '123ghj', 0, 0, NULL),
(90, '2015-03-30 14:18:08', 8009850, 1999269, 13, '123ghj', 1, 1, NULL),
(91, '2015-03-30 14:18:31', 8009850, 1999908, 14, '123ghj', 0, 1, NULL),
(92, '2015-03-30 14:19:12', 8009850, 1999269, 15, '短信测试', 0, 0, NULL),
(93, '2015-04-23 13:54:08', 8009855, 1999151, 17, '11221212', 2, 0, NULL),
(94, '2015-04-23 13:57:03', 8009855, 1999151, 18, 'asdasd', 2, 0, NULL),
(95, '2015-04-23 13:59:33', 8009855, 1999151, 19, 'asas', 2, 0, NULL),
(96, '2015-04-23 14:08:02', 8009855, 1999151, 20, 'fffffffff', 2, 0, NULL),
(97, '2015-04-23 14:10:25', 8009855, 1999151, 21, 'ddddddd', 2, 0, NULL),
(98, '2015-04-23 14:13:14', 8009855, 1999151, 22, 'bbbbbb', 2, 0, NULL),
(99, '2015-04-23 16:18:49', 7088899, 7088801, 21, '123中国abc', 0, 0, NULL),
(100, '2015-04-23 17:05:26', 7088899, 7088801, 23, '123中国abc', 2, 0, NULL),
(101, '2015-04-23 17:07:41', 7088899, 7088801, 24, '123中国abc', 0, 0, NULL),
(102, '2015-04-23 17:08:15', 7088899, 7088801, 25, '123中国abc', 2, 0, NULL),
(103, '2015-04-23 17:14:39', 7088899, 7088801, 26, '123中国abc', 2, 0, NULL),
(104, '2015-04-23 19:03:25', 7088899, 7088801, 27, '123中国abc', 0, 0, NULL),
(105, '2015-04-23 19:03:26', 7088899, 7088801, 28, '123中国abc', 0, 0, NULL),
(106, '2015-04-23 19:03:26', 7088899, 7088801, 29, '123中国abc', 0, 0, NULL),
(107, '2015-04-23 19:17:55', 7088899, 7088801, 36, '123中国abc', 2, 0, NULL),
(108, '2015-04-24 11:30:41', 7088899, 7088801, 50, '123中国abc', 2, 0, NULL),
(109, '2015-04-24 11:40:41', 7088899, 7088801, 52, '123中国abc', 2, 0, NULL),
(110, '2015-04-24 11:43:59', 7088899, 7088801, 53, '长度测试啊，长度测试啊，长度测试啊，长度测试啊，长度测试啊，长度测试啊，长度测试啊，长度测试', 2, 0, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_user_call`
--

DROP TABLE IF EXISTS `xhgmnet_user_call`;
CREATE TABLE IF NOT EXISTS `xhgmnet_user_call` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `startTime` datetime NOT NULL COMMENT '测量开始时间',
  `userId` int(11) NOT NULL COMMENT '用户ID',
  `activityCallTotals` int(11) NOT NULL COMMENT '活动呼叫总数',
  `activityCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '活动呼叫总持续时间',
  `averageCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '平均呼叫持续时间',
  `callTotals` int(11) NOT NULL COMMENT '呼叫总数',
  `callLossTotals` int(11) NOT NULL COMMENT '呼损总数',
  `callErrorTotals` int(11) NOT NULL COMMENT '未成功呼叫总数',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户总计' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_vpn_call`
--

DROP TABLE IF EXISTS `xhgmnet_vpn_call`;
CREATE TABLE IF NOT EXISTS `xhgmnet_vpn_call` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `startTime` datetime NOT NULL COMMENT '测量开始时间',
  `vpnId` int(11) NOT NULL COMMENT '虚拟专网ID',
  `activityCallTotals` int(11) NOT NULL COMMENT '活动呼叫总数',
  `activityCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '活动呼叫总持续时间',
  `averageCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '平均呼叫持续时间',
  `callTotals` int(11) NOT NULL COMMENT '呼叫总数',
  `callLossTotals` int(11) NOT NULL COMMENT '呼损总数',
  `callDropRate` varchar(10) CHARACTER SET utf8mb4 NOT NULL COMMENT '呼损率',
  `callErrorTotals` int(11) NOT NULL COMMENT '未成功呼叫总数',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='虚拟专网话务量总计' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_vpn_call_detail`
--

DROP TABLE IF EXISTS `xhgmnet_vpn_call_detail`;
CREATE TABLE IF NOT EXISTS `xhgmnet_vpn_call_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `startTime` datetime NOT NULL COMMENT '测量开始时间',
  `vpnId` int(11) NOT NULL COMMENT '虚拟专网ID',
  `groupCallNumber` int(11) NOT NULL COMMENT '组呼个数',
  `groupCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '组呼持续时间',
  `singleCallNumber` int(11) NOT NULL COMMENT '个呼个数',
  `singleCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '个呼持续时间',
  `emergentCallTotals` int(11) NOT NULL COMMENT '紧急呼叫个数',
  `emergentCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '紧急呼叫持续时间',
  `phoneCallTotals` int(11) NOT NULL DEFAULT '0' COMMENT '电话呼叫个数',
  `phoneCallTime` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '电话呼叫持续时间 ',
  `fullDuplexCallTotals` int(11) NOT NULL COMMENT '全双工呼叫个数',
  `halfDuplexCallTotals` int(11) NOT NULL COMMENT '半双工个呼个数',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='虚拟专网呼叫详情' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_vpn_call_loss`
--

DROP TABLE IF EXISTS `xhgmnet_vpn_call_loss`;
CREATE TABLE IF NOT EXISTS `xhgmnet_vpn_call_loss` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `startTime` datetime NOT NULL COMMENT '测量开始时间',
  `vpnId` int(11) NOT NULL COMMENT '虚拟专网ID',
  `groupCallLossTotals` int(11) NOT NULL COMMENT '组呼呼损个数',
  `singleCallLossTotals` int(11) NOT NULL COMMENT '个呼呼损个数',
  `emergentCallLossTotals` int(11) NOT NULL COMMENT '紧急呼叫呼损个数',
  `phoneCallLossTotals` int(11) NOT NULL COMMENT '电话呼叫呼损个数',
  `fullDuplexCallLossTotals` int(11) NOT NULL COMMENT '全双工呼叫呼损个数',
  `halfDuplexCallLossTotals` int(11) NOT NULL COMMENT '半双工个呼呼损个数',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='虚拟专网呼损详情' AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
