-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2018-12-03 08:49:47
-- 服务器版本： 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dblogin`
--

-- --------------------------------------------------------

--
-- 表的结构 `school_major`
--

CREATE TABLE IF NOT EXISTS `school_major` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `schoolid` int(11) NOT NULL,
  `schoolname` varchar(13) NOT NULL,
  `specialtyname` varchar(13) NOT NULL COMMENT '专业名字',
  `localprovince` varchar(14) NOT NULL COMMENT '考生所在地',
  `studenttype` varchar(11) NOT NULL COMMENT '文理科',
  `year` int(11) NOT NULL COMMENT '年份',
  `batch` varchar(11) NOT NULL COMMENT '录取批次',
  `var` varchar(11) NOT NULL COMMENT '平均分',
  `max` varchar(11) NOT NULL COMMENT '最高分',
  `min` varchar(11) NOT NULL COMMENT '最低分',
  `url` text NOT NULL COMMENT '详情url',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='专业录取分数线' AUTO_INCREMENT=11 ;

-- --------------------------------------------------------

--
-- 表的结构 `school_ranking`
--

CREATE TABLE IF NOT EXISTS `school_ranking` (
  `schoolid` int(11) NOT NULL,
  `schoolname` varchar(12) NOT NULL,
  `clicks` bigint(11) NOT NULL,
  `monthclicks` bigint(11) NOT NULL,
  `weekclicks` bigint(16) NOT NULL COMMENT '周点击',
  `province` varchar(12) NOT NULL COMMENT '省份',
  `schooltype` varchar(12) NOT NULL COMMENT '学校类型',
  `schoolproperty` varchar(12) NOT NULL COMMENT '学校类别',
  `f985` int(4) NOT NULL COMMENT '是否985',
  `f211` int(12) NOT NULL COMMENT '是否211',
  `level` varchar(12) NOT NULL COMMENT '学校科属（本专）',
  `schoolnature` varchar(12) NOT NULL COMMENT '学校（公办私办）',
  `shoufei` text NOT NULL COMMENT '收费',
  `jianjie` varchar(15) NOT NULL,
  `schoolcode` varchar(11) NOT NULL COMMENT '学校编码',
  `ranking` int(11) NOT NULL COMMENT '全国热度排名',
  `rankingCollegetype` int(11) NOT NULL COMMENT '类别热度排名',
  `guanwang` text NOT NULL COMMENT '官网',
  PRIMARY KEY (`schoolid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学校热度排名';

-- --------------------------------------------------------

--
-- 表的结构 `school_score`
--

CREATE TABLE IF NOT EXISTS `school_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `schoolid` int(11) unsigned NOT NULL,
  `schoolname` varchar(12) NOT NULL,
  `localprovince` varchar(11) NOT NULL COMMENT '省份',
  `province` varchar(11) NOT NULL COMMENT '大学所在地',
  `studenttype` varchar(12) NOT NULL COMMENT '文理科',
  `year` int(6) NOT NULL COMMENT '年份',
  `batch` varchar(11) NOT NULL COMMENT '批次',
  `var` varchar(6) NOT NULL COMMENT '平均分',
  `max` varchar(11) NOT NULL COMMENT '最高分',
  `min` varchar(6) NOT NULL COMMENT '最低分',
  `fencha` varchar(6) NOT NULL COMMENT '平均分跟省控线分差',
  `provincescore` varchar(6) NOT NULL COMMENT '省控线',
  `url` varchar(12) NOT NULL COMMENT 'url链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='学校对各省录取分数线' AUTO_INCREMENT=134357 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
