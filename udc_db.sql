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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT`
--

LOCK TABLES `CLIENT` WRITE;
/*!40000 ALTER TABLE `CLIENT` DISABLE KEYS */;
INSERT INTO `CLIENT` VALUES (1,3);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DOCTOR`
--

LOCK TABLES `DOCTOR` WRITE;
/*!40000 ALTER TABLE `DOCTOR` DISABLE KEYS */;
INSERT INTO `DOCTOR` VALUES (1,'FF0000',1),(2,'00FF00',2);
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SLOT`
--

LOCK TABLES `SLOT` WRITE;
/*!40000 ALTER TABLE `SLOT` DISABLE KEYS */;
INSERT INTO `SLOT` VALUES (1,'2018-04-14 15:00:00','2018-04-14 15:30:00',NULL,NULL),(3,'2018-04-14 15:30:00','2018-04-14 16:00:00',NULL,NULL),(4,'2018-04-14 16:00:00','2018-04-14 16:30:00',NULL,NULL),(7,'2018-04-14 19:00:00','2018-04-14 19:30:00',NULL,NULL),(8,'2018-04-14 19:30:00','2018-04-14 20:00:00',NULL,NULL),(10,'2018-04-17 00:00:00','2018-04-17 00:30:00',NULL,NULL),(11,'2018-02-03 01:00:00','2018-02-03 01:30:00',NULL,NULL),(12,'2018-02-03 01:30:00','2018-02-03 02:00:00',NULL,NULL);
/*!40000 ALTER TABLE `SLOT` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `udc_db`.`SLOT_BEFORE_INSERT` BEFORE INSERT ON `SLOT` FOR EACH ROW
BEGIN
	IF NEW.end <= NEW.start THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'End must be after start';
    END IF;
    IF (SELECT COUNT(*) FROM SLOT WHERE 
    SLOT.SLOTid <> NEW.SLOTid AND
    (NEW.start >= SLOT.start AND NEW.start < SLOT.end OR
    NEW.end > SLOT.start AND NEW.end <= SLOT.end)) > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot have overlapping slots';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `udc_db`.`SLOT_BEFORE_UPDATE` BEFORE UPDATE ON `SLOT` FOR EACH ROW
BEGIN
	IF NEW.end <= NEW.start THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'End must be after start';
    END IF;
    IF (SELECT COUNT(*) FROM SLOT WHERE 
    SLOT.SLOTid <> NEW.SLOTid AND
    (NEW.start >= SLOT.start AND NEW.start < SLOT.end OR
    NEW.end > SLOT.start AND NEW.end <= SLOT.end)) > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot have overlapping slots';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `udc_db`.`SLOT_BEFORE_DELETE` BEFORE DELETE ON `SLOT` FOR EACH ROW
BEGIN
	IF OLD.APPOINTMENTid IS NOT NULL THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Appointment already reserved during this slot';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

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
INSERT INTO `SLOT_DOC` VALUES (1,1),(3,1),(4,1),(11,1),(12,1),(7,2),(8,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES (1,'doc1','aaa','Mantis','Toboggan'),(2,'doc2','aaa','Golden','God'),(3,'cli1','aaa','Jordan','Jordan');
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

-- Dump completed on 2018-04-17 21:57:51
