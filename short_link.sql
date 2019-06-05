-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: 115.29.165.122    Database: short_link
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (124);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `short_link`
--

DROP TABLE IF EXISTS `short_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `short_link` (
  `id` int(11) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `full_link` varchar(2048) DEFAULT NULL,
  `short_link` varchar(64) NOT NULL,
  `uri` varchar(10) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sri9qu8bq3r8o7wlbh0xayw42` (`uri`),
  KEY `uri_index` (`uri`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `short_link`
--

LOCK TABLES `short_link` WRITE;
/*!40000 ALTER TABLE `short_link` DISABLE KEYS */;
INSERT INTO `short_link` VALUES (117,2,'http://t.cn/R34BxJm','t.0hi.cn/Q3ABzi','Q3ABzi','360借条UY'),(119,3,'http://t.cn/R34BxJm','t.0hi.cn/aqErmm','aqErmm','360借条UG'),(120,2,'http://t.cn/R34BxJm','t.0hi.cn/A7RJZn','A7RJZn','360借条UI'),(38,34,'https://ac.ppdai.com/activitypage?redirect=https://m.ppdai.com/act/ewm/loan5.html?source=APP701&regsourceid=APP701&activityId=83','t.0hi.cn/amEBF3','amEBF3','拍拍贷'),(12,42,'http://t.cn/R3lO5te','t.0hi.cn/YR36ze','YR36ze','南京小刘股票'),(93,0,'https://at.umeng.com/GvCKLv','t.0hi.cn/BJVv6j','BJVv6j','借啊'),(112,15,'http://t.cn/R34BxJm','t.0hi.cn/memuYz','memuYz','360借条XG'),(113,4,'http://t.cn/R34BxJm','t.0hi.cn/aEbA3q','aEbA3q','360借条PJ'),(43,44,'https://creditcard.ecitic.com/citiccard/newwap/pages/AppCreditCard/applayCard-process.html?sid=SJAYKTT31&pid=CS0002','t.0hi.cn/EBbMRz','EBbMRz','北京沃特尔'),(65,115,'https://creditcard.cmbc.com.cn/wsv2/?enstr=ZWekuNjXrSTFbh927Wf8vsYxvl6nr%2fj4w7oORRLweJ1MKruguhaZAIUVpFsj%2bC5dgZl%2fRWtZ%2b%2bC4D8kuRleMw58kuypFvlO5TDF3pfStfozioPUqRn9wOE4Id2Y%2beuDH7WdLrCiOfPgXxz5A%2bKCIFgRYysFrgRIwRkZYSjZayri3pAabuqHQPduk15hVWA%2bbDDm2Tj60tRGWksl2yoeXBMwEee5oFN0Ensx6bRHI48f2VrFU3%2fGg78SUFNPnyMv6ax0WGDIAmAty2zcin1Og%2ftHCuiYo61GP%2fGtqadaCscERMpuQZHs3qk0II74Huku9BHXfniRo3ICSsLy1M9Sv3A%3d%3d','t.0hi.cn/Q73Yfq','Q73Yfq','民生银行信用卡在职版'),(66,100,'https://creditcard.cmbc.com.cn/wsv2/?enstr=yZqkaytcwj5MBVpfI2%2b6G7fD45xdi%2bJyELRN1N1k7KvEV%2bCDWKmYfVyjNgiyq3WcTJRDnxeggBCnYX2dqVZsxMQY1Ne37m0rwwbvzw4Id4VRZGtnCrMVFo2aQTC685yn%2fRQbqxqHvio8C%2bmX94kRG1%2b%2ftiEt88OGGs1UBHYIIMwVxC3pX21viSyl8pF%2bH3XhJW80%2bLmfxMSQ60zWtpLF2IPzrga032gm6HHstNhgJCaO379n349u3O471dZ%2f3DQwHuwdJ7nR9w9bTU4JK4lF0BpN9r%2b2Cz1UG9QE2f9Y8rCxzl0fQzYGCYK2iw5AmCEC0oTYrA0Hk1F6nzmSWXoLxw%3d%3d','t.0hi.cn/qiIrae','qiIrae','民生银行信用卡学生版'),(67,91,'http://t.cn/R34BxJm','t.0hi.cn/QjYfiu','QjYfiu','庞_360借条'),(68,14,'http://jy.gkekj.cn/447','t.0hi.cn/MjEBZf','MjEBZf','庞_兴业银行'),(69,0,'http://athm.cn/tV4Ezfc','t.0hi.cn/FBrIry','FBrIry','庞_平安银行车卡'),(123,1,'https://blog.csdn.net/bc_vnetwork/article/details/51787473','t.0hi.cn/Eba2Mn','Eba2Mn','测试线程异常退出'),(79,5,'https://daikuan.homecreditcfc.cn/wcl-web/auth.do?UTM_source=7LINSJ&UTM_medium=SMS&UTM_campaign=2&UTM_content=Group2','t.0hi.cn/UNbAz2','UNbAz2','捷信公司');
/*!40000 ALTER TABLE `short_link` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-01  9:22:59
