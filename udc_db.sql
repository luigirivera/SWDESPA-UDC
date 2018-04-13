-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: udc_db
-- ------------------------------------------------------
-- Server version	5.7.20

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
-- Table structure for table `APPOINTMENT`
--

DROP TABLE IF EXISTS `APPOINTMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `APPOINTMENT` (
  `APPOINTMENTid` int(11) NOT NULL AUTO_INCREMENT,
  `DOCTORid` int(11) NOT NULL,
  `CLIENTid` int(11) NOT NULL,
  `RECURRINGid` int(11) DEFAULT NULL,
  PRIMARY KEY (`APPOINTMENTid`),
  KEY `appointment_client_idx` (`CLIENTid`),
  KEY `appointment_recurring_idx` (`RECURRINGid`),
  KEY `appointment_doctor_idx` (`DOCTORid`),
  CONSTRAINT `appointment_client` FOREIGN KEY (`CLIENTid`) REFERENCES `CLIENT` (`CLIENTid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `appointment_doctor` FOREIGN KEY (`DOCTORid`) REFERENCES `DOCTOR` (`DOCTORid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `appointment_recurring` FOREIGN KEY (`RECURRINGid`) REFERENCES `RECURRING` (`RECURRINGid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `APPOINTMENT`
--

LOCK TABLES `APPOINTMENT` WRITE;
/*!40000 ALTER TABLE `APPOINTMENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `APPOINTMENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT`
--

DROP TABLE IF EXISTS `CLIENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT` (
  `CLIENTid` int(11) NOT NULL AUTO_INCREMENT,
  `USERid` int(11) NOT NULL,
  PRIMARY KEY (`CLIENTid`),
  KEY `client_user_idx` (`USERid`),
  CONSTRAINT `client_user` FOREIGN KEY (`USERid`) REFERENCES `USER` (`USERid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT`
--

LOCK TABLES `CLIENT` WRITE;
/*!40000 ALTER TABLE `CLIENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DOCTOR`
--

DROP TABLE IF EXISTS `DOCTOR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DOCTOR` (
  `DOCTORid` int(11) NOT NULL AUTO_INCREMENT,
  `color` varchar(6) NOT NULL,
  `USERid` int(11) NOT NULL,
  PRIMARY KEY (`DOCTORid`),
  KEY `doctor_user_idx` (`USERid`),
  CONSTRAINT `doctor_user` FOREIGN KEY (`USERid`) REFERENCES `USER` (`USERid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DOCTOR`
--

LOCK TABLES `DOCTOR` WRITE;
/*!40000 ALTER TABLE `DOCTOR` DISABLE KEYS */;
/*!40000 ALTER TABLE `DOCTOR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RECURRING`
--

DROP TABLE IF EXISTS `RECURRING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RECURRING` (
  `RECURRINGid` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`RECURRINGid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RECURRING`
--

LOCK TABLES `RECURRING` WRITE;
/*!40000 ALTER TABLE `RECURRING` DISABLE KEYS */;
/*!40000 ALTER TABLE `RECURRING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SECRETARY`
--

DROP TABLE IF EXISTS `SECRETARY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SECRETARY` (
  `SECRETARYid` int(11) NOT NULL AUTO_INCREMENT,
  `USERid` int(11) NOT NULL,
  PRIMARY KEY (`SECRETARYid`),
  KEY `secretary_user_idx` (`USERid`),
  CONSTRAINT `secretary_user` FOREIGN KEY (`USERid`) REFERENCES `USER` (`USERid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SECRETARY`
--

LOCK TABLES `SECRETARY` WRITE;
/*!40000 ALTER TABLE `SECRETARY` DISABLE KEYS */;
/*!40000 ALTER TABLE `SECRETARY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SLOT`
--

DROP TABLE IF EXISTS `SLOT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SLOT` (
  `SLOTid` int(11) NOT NULL AUTO_INCREMENT,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  `APPOINTMENTid` int(11) DEFAULT NULL,
  `RECURRINGid` int(11) DEFAULT NULL,
  PRIMARY KEY (`SLOTid`),
  KEY `slot_appointment_idx` (`APPOINTMENTid`),
  KEY `slot_recurring_idx` (`RECURRINGid`),
  CONSTRAINT `slot_appointment` FOREIGN KEY (`APPOINTMENTid`) REFERENCES `APPOINTMENT` (`APPOINTMENTid`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `slot_recurring` FOREIGN KEY (`RECURRINGid`) REFERENCES `RECURRING` (`RECURRINGid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SLOT`
--

LOCK TABLES `SLOT` WRITE;
/*!40000 ALTER TABLE `SLOT` DISABLE KEYS */;
/*!40000 ALTER TABLE `SLOT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SLOT_DOC`
--

DROP TABLE IF EXISTS `SLOT_DOC`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SLOT_DOC` (
  `SLOTid` int(11) NOT NULL,
  `DOCTORid` int(11) NOT NULL,
  PRIMARY KEY (`SLOTid`),
  KEY `sd_doctor_idx` (`DOCTORid`),
  CONSTRAINT `sd_doctor` FOREIGN KEY (`DOCTORid`) REFERENCES `DOCTOR` (`DOCTORid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sd_slot` FOREIGN KEY (`SLOTid`) REFERENCES `SLOT` (`SLOTid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SLOT_DOC`
--

LOCK TABLES `SLOT_DOC` WRITE;
/*!40000 ALTER TABLE `SLOT_DOC` DISABLE KEYS */;
/*!40000 ALTER TABLE `SLOT_DOC` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `USERid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  PRIMARY KEY (`USERid`),
  UNIQUE KEY `USERusername_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-11 23:15:06
