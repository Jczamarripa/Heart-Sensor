CREATE DATABASE  IF NOT EXISTS `project` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `project`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: project
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.16-MariaDB

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
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors` (
  `doctor_id` char(10) NOT NULL,
  `doctor_first_name` varchar(50) NOT NULL,
  `doctor_last_name` varchar(40) NOT NULL,
  `doctor_password` varchar(50) NOT NULL,
  PRIMARY KEY (`doctor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors`
--

LOCK TABLES `doctors` WRITE;
/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` VALUES ('0314106080','Fermin','Palacios','102938');
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctors_patients`
--

DROP TABLE IF EXISTS `doctors_patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_patients` (
  `doctor_id` char(10) NOT NULL,
  `patient_id` char(10) NOT NULL,
  PRIMARY KEY (`doctor_id`,`patient_id`),
  KEY `fk_pat` (`patient_id`),
  CONSTRAINT `fk_doc` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`doctor_id`),
  CONSTRAINT `fk_pat` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors_patients`
--

LOCK TABLES `doctors_patients` WRITE;
/*!40000 ALTER TABLE `doctors_patients` DISABLE KEYS */;
INSERT INTO `doctors_patients` VALUES ('0314106080','0314106081'),('0314106080','0314106082'),('0314106080','0314106083'),('0314106080','0314106084'),('0314106080','0314106085');
/*!40000 ALTER TABLE `doctors_patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hearthrates`
--

DROP TABLE IF EXISTS `hearthrates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hearthrates` (
  `hr_id` int(11) NOT NULL AUTO_INCREMENT,
  `hr_patient_id` char(10) NOT NULL,
  `hr_pulse` decimal(5,2) NOT NULL,
  `hr_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`hr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hearthrates`
--

LOCK TABLES `hearthrates` WRITE;
/*!40000 ALTER TABLE `hearthrates` DISABLE KEYS */;
INSERT INTO `hearthrates` VALUES (1,'0314106081',117.36,'2016-11-24 22:42:35'),(2,'0314106081',104.00,'2016-11-24 22:42:36'),(3,'0314106081',101.37,'2016-11-24 22:42:37'),(4,'0314106081',102.63,'2016-11-24 22:42:38'),(5,'0314106081',115.83,'2016-11-24 22:42:39'),(6,'0314106081',113.25,'2016-11-24 22:42:40'),(7,'0314106081',113.76,'2016-11-24 22:42:41'),(8,'0314106081',117.16,'2016-11-24 22:42:42'),(9,'0314106081',109.35,'2016-11-24 22:42:44'),(10,'0314106081',113.54,'2016-11-24 22:42:45'),(11,'0314106081',114.49,'2016-11-25 00:29:29'),(12,'0314106081',113.78,'2016-11-25 00:29:30'),(13,'0314106081',104.15,'2016-11-25 00:29:31'),(14,'0314106081',103.89,'2016-11-25 00:29:33'),(15,'0314106081',100.69,'2016-11-25 00:29:34'),(16,'0314106081',112.54,'2016-11-25 00:29:35'),(17,'0314106081',113.05,'2016-11-25 00:29:36'),(18,'0314106081',100.40,'2016-11-25 00:29:37'),(19,'0314106081',108.76,'2016-11-25 00:29:38'),(20,'0314106081',118.11,'2016-11-25 00:29:39'),(21,'0314106081',103.70,'2016-11-25 00:32:54'),(22,'0314106081',106.15,'2016-11-25 00:32:55'),(23,'0314106081',110.19,'2016-11-25 00:32:56'),(24,'0314106081',104.15,'2016-11-25 00:32:57'),(25,'0314106081',107.25,'2016-11-25 00:32:58'),(26,'0314106081',100.45,'2016-11-25 00:32:59'),(27,'0314106081',106.99,'2016-11-25 00:33:00'),(28,'0314106081',107.75,'2016-11-25 00:33:01'),(29,'0314106081',108.16,'2016-11-25 00:33:02'),(30,'0314106081',114.48,'2016-11-25 00:33:03'),(31,'0314106081',104.64,'2016-11-25 00:33:17'),(32,'0314106081',113.68,'2016-11-25 00:33:19'),(33,'0314106081',118.42,'2016-11-25 00:33:20'),(34,'0314106081',103.70,'2016-11-25 00:33:21'),(35,'0314106081',103.32,'2016-11-25 00:33:22'),(36,'0314106081',115.04,'2016-11-25 00:33:23'),(37,'0314106081',108.67,'2016-11-25 00:33:24'),(38,'0314106081',118.63,'2016-11-25 00:33:25'),(39,'0314106081',113.90,'2016-11-25 00:33:26'),(40,'0314106081',108.01,'2016-11-25 00:33:27');
/*!40000 ALTER TABLE `hearthrates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patients` (
  `patient_id` char(10) NOT NULL,
  `patient_first_name` varchar(50) NOT NULL,
  `patient_last_name` varchar(40) NOT NULL,
  `patient_date_birth` date NOT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES ('0314106081','Hogilber','Quintana','1993-06-29'),('0314106082','Gerardo','Campos','1980-04-29'),('0314106083','Jorge','Leon','1993-03-10'),('0314106084','Julio','Zamarripa','1994-10-20'),('0314106085','Erick','Bernal','1995-07-29');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'project'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-24 16:52:50
