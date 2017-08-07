-- phpMyAdmin SQL Dump
-- version 3.4.10.1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2017 年 07 月 25 日 02:18
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
(1, '2017-05-25 10:00:00', '12121212', 1, 1, '80020200', '80020201', 0, '25', NULL, '96', NULL, NULL),
(2, '2017-05-25 10:00:00', '12121212', 1, 2, '80020200', '80020201', 0, '25', NULL, '96', NULL, NULL),
(3, '2017-05-25 10:00:00', '12121212', 1, 3, '80020200', '80020201', 0, '25', NULL, '96', NULL, NULL),
(4, '2017-05-25 10:00:00', '12121212', 1, 4, '80020200', '80020201', 0, '25', NULL, '96', NULL, NULL),
(5, '2017-05-25 10:00:00', '12121212', 1, 5, '80020200', '80020201', 0, '25', NULL, '96', NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_calllist06`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='呼叫记录表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_calllist09`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='呼叫记录表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_calllist11`
--

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
-- 表的结构 `xhgmnet_gpsinfo01`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS信息表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsinfo10`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS信息表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_gpsinfo11`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 ROW_FORMAT=COMPACT COMMENT='GPS操作记录' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xhgmnet_group_call`
--

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
-- 表的结构 `xhgmnet_user_call`
--

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
