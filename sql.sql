-- MySQL dump 10.13  Distrib 8.0.17, for Linux (x86_64)
--
-- Host: localhost    Database: draft1
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Client`
--

DROP TABLE IF EXISTS `Client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Client` (
  `clientId` int(11) NOT NULL,
  `city` varchar(255) NOT NULL,
  `street` varchar(255) NOT NULL,
  `phoneNumber` int(11) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `lname` varchar(255) NOT NULL,
  `fname` varchar(255) NOT NULL,
  `employeeId` int(11) DEFAULT NULL,
  `organisation` varchar(255) DEFAULT NULL,
  `secondaryNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`clientId`),
  UNIQUE KEY `phoneNumber` (`phoneNumber`),
  KEY `employeeId` (`employeeId`),
  CONSTRAINT `Client_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `Employee` (`employeeId`),
  CONSTRAINT `Client_ibfk_2` FOREIGN KEY (`clientId`) REFERENCES `User` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Client`
--

LOCK TABLES `Client` WRITE;
/*!40000 ALTER TABLE `Client` DISABLE KEYS */;
INSERT INTO `Client` VALUES (7,'gsg','gsdg',341,'M','gsdg','gsg',NULL,'sgsg',NULL),(8,'gsgfafaff','gdsgsdg',3414,'M','gsdg','gsg',1,'sgsg',NULL),(13,'sdg','sdg',231,'M','ffad','rakshit',NULL,'fbbs',NULL),(14,'gadg','dsg',14141,'M','fdg','fsdf',NULL,'vsgsgs',NULL),(15,'fgad','gadgda',41441,'M','faga','fafa',NULL,'sgvsg',NULL),(16,'gds','gsdg',341341,'F','gsdg','afafaf1',9,'fsgs',NULL),(32,'faaf','fdfaf',241241,'M','fadfaf','affaf',NULL,'fdff',NULL);
/*!40000 ALTER TABLE `Client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ClientEmail`
--

DROP TABLE IF EXISTS `ClientEmail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ClientEmail` (
  `email` varchar(255) NOT NULL,
  `clientId` int(11) NOT NULL,
  PRIMARY KEY (`email`,`clientId`),
  KEY `clientId` (`clientId`),
  CONSTRAINT `ClientEmail_ibfk_1` FOREIGN KEY (`clientId`) REFERENCES `Client` (`clientId`),
  CONSTRAINT `chk_email` CHECK ((`email` like _utf8mb4'%_@__%.__%'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ClientEmail`
--

LOCK TABLES `ClientEmail` WRITE;
/*!40000 ALTER TABLE `ClientEmail` DISABLE KEYS */;
INSERT INTO `ClientEmail` VALUES ('fa21f@faf1.com',8),('faf@faf.com',8),('faf@faf.com',16),('safa@vcadfa.com',32);
/*!40000 ALTER TABLE `ClientEmail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ClientOrder`
--

DROP TABLE IF EXISTS `ClientOrder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ClientOrder` (
  `clientOrderId` int(11) NOT NULL AUTO_INCREMENT,
  `receivedDate` date NOT NULL,
  `dueDate` date DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `clientId` int(11) NOT NULL,
  `approvedByEmployeeId` int(11) DEFAULT NULL,
  `assignedToEmployeeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`clientOrderId`),
  KEY `clientId` (`clientId`),
  KEY `approvedByEmployeeId` (`approvedByEmployeeId`),
  KEY `assignedToEmployeeId` (`assignedToEmployeeId`),
  CONSTRAINT `ClientOrder_ibfk_1` FOREIGN KEY (`clientId`) REFERENCES `Client` (`clientId`),
  CONSTRAINT `ClientOrder_ibfk_2` FOREIGN KEY (`approvedByEmployeeId`) REFERENCES `Employee` (`employeeId`),
  CONSTRAINT `ClientOrder_ibfk_3` FOREIGN KEY (`assignedToEmployeeId`) REFERENCES `Employee` (`employeeId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ClientOrder`
--

LOCK TABLES `ClientOrder` WRITE;
/*!40000 ALTER TABLE `ClientOrder` DISABLE KEYS */;
INSERT INTO `ClientOrder` VALUES (1,'2019-10-14',NULL,'in consideration',NULL,NULL,8,NULL,NULL),(2,'2019-10-14',NULL,'in consideration',NULL,NULL,8,NULL,NULL),(3,'2019-10-14',NULL,'in consideration','fafffa',NULL,8,NULL,NULL),(4,'2019-10-14',NULL,'in consideration','dada',NULL,16,NULL,NULL),(5,'2019-10-14',NULL,'in consideration',NULL,NULL,16,NULL,NULL),(6,'2019-10-14',NULL,'in consideration',NULL,NULL,16,NULL,NULL),(7,'2019-10-14',NULL,'in consideration','fadsf',NULL,16,NULL,NULL),(8,'2019-10-14',NULL,'in consideration','fafa',NULL,16,NULL,NULL),(9,'2019-10-14',NULL,'in consideration',NULL,NULL,16,NULL,NULL),(10,'2019-10-16',NULL,'in consideration','dss',NULL,16,NULL,NULL);
/*!40000 ALTER TABLE `ClientOrder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ClientOrderLog`
--

DROP TABLE IF EXISTS `ClientOrderLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ClientOrderLog` (
  `eventTime` date NOT NULL,
  `discription` varchar(255) NOT NULL,
  `clientOrderId` int(11) NOT NULL,
  PRIMARY KEY (`eventTime`,`clientOrderId`),
  KEY `clientOrderId` (`clientOrderId`),
  CONSTRAINT `ClientOrderLog_ibfk_1` FOREIGN KEY (`clientOrderId`) REFERENCES `ClientOrder` (`clientOrderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ClientOrderLog`
--

LOCK TABLES `ClientOrderLog` WRITE;
/*!40000 ALTER TABLE `ClientOrderLog` DISABLE KEYS */;
/*!40000 ALTER TABLE `ClientOrderLog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ConsistsOf`
--

DROP TABLE IF EXISTS `ConsistsOf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ConsistsOf` (
  `quantity` int(11) NOT NULL,
  `clientOrderId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  PRIMARY KEY (`clientOrderId`,`productId`),
  KEY `productId` (`productId`),
  CONSTRAINT `ConsistsOf_ibfk_1` FOREIGN KEY (`clientOrderId`) REFERENCES `ClientOrder` (`clientOrderId`),
  CONSTRAINT `ConsistsOf_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `Products` (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ConsistsOf`
--

LOCK TABLES `ConsistsOf` WRITE;
/*!40000 ALTER TABLE `ConsistsOf` DISABLE KEYS */;
INSERT INTO `ConsistsOf` VALUES (8,3,1),(3,3,2),(5,3,3),(231,4,1),(3413,4,3),(445,7,1),(234,7,2),(3414,8,1),(41,8,3),(341,10,1);
/*!40000 ALTER TABLE `ConsistsOf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Employee`
--

DROP TABLE IF EXISTS `Employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Employee` (
  `employeeId` int(11) NOT NULL,
  `position` varchar(255) DEFAULT NULL,
  `salary` int(11) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  `dateOfJoining` date NOT NULL,
  `gender` varchar(255) NOT NULL,
  `lname` varchar(255) NOT NULL,
  `fname` varchar(255) NOT NULL,
  `plantId` int(11) DEFAULT NULL,
  PRIMARY KEY (`employeeId`),
  KEY `plantId` (`plantId`),
  CONSTRAINT `Employee_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `User` (`userId`) ON DELETE CASCADE,
  CONSTRAINT `Employee_ibfk_2` FOREIGN KEY (`plantId`) REFERENCES `Plant` (`plantId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Employee`
--

LOCK TABLES `Employee` WRITE;
/*!40000 ALTER TABLE `Employee` DISABLE KEYS */;
INSERT INTO `Employee` VALUES (1,'fafd',3,'fdasf','2019-10-10','M','ddg','rfaf',1),(3,'fsdf',3,'fds','2019-10-10','M','fsdf','fsdf',1),(4,'fdf',2,'sdg','2019-10-10','M','sdf','fsdf',1),(9,'fasfasf',13,'sdgdsg','2019-10-10','M','ssgs','rakshit',1),(10,'sgs',333,'sgdsg','2019-10-10','M','gsdgd','fafaf',1),(11,'sgdgs',231,'gsdgsdgaf','2019-10-10','M','sdgs','dfdfg',1),(35,'default Position',1,'fadfad','2019-10-10','M','fsdfdas','fafa',4),(36,'default Position',1,'fadfad','2019-10-10','M','fsdfdas','fafa',4);
/*!40000 ALTER TABLE `Employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EmployeeEmail`
--

DROP TABLE IF EXISTS `EmployeeEmail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `EmployeeEmail` (
  `email` varchar(255) NOT NULL,
  `employeeId` int(11) NOT NULL,
  PRIMARY KEY (`email`,`employeeId`),
  KEY `employeeId` (`employeeId`),
  CONSTRAINT `EmployeeEmail_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `Employee` (`employeeId`),
  CONSTRAINT `chk_email_Employee` CHECK ((`email` like _utf8mb4'%_@__%.__%'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EmployeeEmail`
--

LOCK TABLES `EmployeeEmail` WRITE;
/*!40000 ALTER TABLE `EmployeeEmail` DISABLE KEYS */;
/*!40000 ALTER TABLE `EmployeeEmail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EmployeePhoneNo`
--

DROP TABLE IF EXISTS `EmployeePhoneNo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `EmployeePhoneNo` (
  `phoneNo` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  PRIMARY KEY (`phoneNo`,`employeeId`),
  KEY `employeeId` (`employeeId`),
  CONSTRAINT `EmployeePhoneNo_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `Employee` (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EmployeePhoneNo`
--

LOCK TABLES `EmployeePhoneNo` WRITE;
/*!40000 ALTER TABLE `EmployeePhoneNo` DISABLE KEYS */;
INSERT INTO `EmployeePhoneNo` VALUES (411,3),(412,3),(414,3),(4234,3),(242342,3),(1231,4),(1233,4);
/*!40000 ALTER TABLE `EmployeePhoneNo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Feedback`
--

DROP TABLE IF EXISTS `Feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Feedback` (
  `feedbackId` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `remark` varchar(255) NOT NULL,
  `clientOrderId` int(11) NOT NULL,
  PRIMARY KEY (`feedbackId`),
  KEY `clientOrderId` (`clientOrderId`),
  CONSTRAINT `Feedback_ibfk_1` FOREIGN KEY (`clientOrderId`) REFERENCES `ClientOrder` (`clientOrderId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Feedback`
--

LOCK TABLES `Feedback` WRITE;
/*!40000 ALTER TABLE `Feedback` DISABLE KEYS */;
INSERT INTO `Feedback` VALUES (1,'2019-10-14','fafaf',7),(2,'2019-10-14','faff',7),(3,'2019-10-14','',7),(4,'2019-10-14','',7),(5,'2019-10-14','',7),(6,'2019-10-14','',7),(7,'2019-10-14','',7),(8,'2019-10-14','',7),(9,'2019-10-14','',7),(10,'2019-10-14','faffafa',7),(11,'2019-10-14','fafaffa',8),(12,'2019-10-16','gsg',10),(13,'2019-10-16','gsgd',10),(14,'2019-10-17','rfasfafaaf',4);
/*!40000 ALTER TABLE `Feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MadeOf`
--

DROP TABLE IF EXISTS `MadeOf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MadeOf` (
  `productId` int(11) NOT NULL,
  `rawMaterialId` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`productId`,`rawMaterialId`),
  KEY `rawMaterialId` (`rawMaterialId`),
  CONSTRAINT `MadeOf_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `Products` (`productId`),
  CONSTRAINT `MadeOf_ibfk_2` FOREIGN KEY (`rawMaterialId`) REFERENCES `RawMaterial` (`rawMaterialId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MadeOf`
--

LOCK TABLES `MadeOf` WRITE;
/*!40000 ALTER TABLE `MadeOf` DISABLE KEYS */;
INSERT INTO `MadeOf` VALUES (1,1,23),(1,2,2),(1,3,41),(1,4,3),(6,1,4);
/*!40000 ALTER TABLE `MadeOf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Manufactures`
--

DROP TABLE IF EXISTS `Manufactures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Manufactures` (
  `timeRequired` time NOT NULL,
  `productId` int(11) NOT NULL,
  `plantId` int(11) NOT NULL,
  PRIMARY KEY (`productId`,`plantId`),
  KEY `plantId` (`plantId`),
  CONSTRAINT `Manufactures_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `Products` (`productId`),
  CONSTRAINT `Manufactures_ibfk_2` FOREIGN KEY (`plantId`) REFERENCES `Plant` (`plantId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Manufactures`
--

LOCK TABLES `Manufactures` WRITE;
/*!40000 ALTER TABLE `Manufactures` DISABLE KEYS */;
INSERT INTO `Manufactures` VALUES ('09:00:00',1,1),('02:00:00',1,3),('02:00:00',1,7);
/*!40000 ALTER TABLE `Manufactures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Plant`
--

DROP TABLE IF EXISTS `Plant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Plant` (
  `address` varchar(255) NOT NULL,
  `plantId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `currentCapacity` int(11) NOT NULL,
  PRIMARY KEY (`plantId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Plant`
--

LOCK TABLES `Plant` WRITE;
/*!40000 ALTER TABLE `Plant` DISABLE KEYS */;
INSERT INTO `Plant` VALUES ('3e1ef',1,'afar41',2),('sdvds',2,'fsdf',2),('sdvds',3,'fsdf',2),('sdvds',4,'fsdf',2),('sdvds',5,'fsdffsdfs',2),('sdvds',6,'fsdf',2),('dfad',7,'faf',8),('fffaf',8,'4fadf',4141);
/*!40000 ALTER TABLE `Plant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Products`
--

DROP TABLE IF EXISTS `Products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Products` (
  `name` varchar(255) NOT NULL,
  `productId` int(11) NOT NULL AUTO_INCREMENT,
  `price` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  PRIMARY KEY (`productId`),
  UNIQUE KEY `name` (`name`),
  KEY `employeeId` (`employeeId`),
  CONSTRAINT `Products_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `Employee` (`employeeId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Products`
--

LOCK TABLES `Products` WRITE;
/*!40000 ALTER TABLE `Products` DISABLE KEYS */;
INSERT INTO `Products` VALUES ('faf',1,213,1),('faf2raksj',2,2322,3),('fafa',3,231,3),('1faf4',6,31,1);
/*!40000 ALTER TABLE `Products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RawMaterial`
--

DROP TABLE IF EXISTS `RawMaterial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RawMaterial` (
  `rawMaterialId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`rawMaterialId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RawMaterial`
--

LOCK TABLES `RawMaterial` WRITE;
/*!40000 ALTER TABLE `RawMaterial` DISABLE KEYS */;
INSERT INTO `RawMaterial` VALUES (1,'afaf',213),(2,'afaf12',213),(3,'afaf124',213),(4,'afaf1245',213);
/*!40000 ALTER TABLE `RawMaterial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Role`
--

DROP TABLE IF EXISTS `Role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Role` (
  `roleName` varchar(255) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`roleName`,`userId`),
  KEY `userId` (`userId`),
  CONSTRAINT `Role_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Role`
--

LOCK TABLES `Role` WRITE;
/*!40000 ALTER TABLE `Role` DISABLE KEYS */;
INSERT INTO `Role` VALUES ('client',7),('client',8),('Employee',9),('Employee',10),('Employee',11),('Admin',12),('Client',13),('Client',14),('Client',15),('Client',16),('Supplier',18),('Supplier',19),('Supplier',20),('Supplier',21),('Supplier',22),('Supplier',23),('Supplier',24),('Supplier',25),('Supplier',26),('Supplier',27),('Supplier',28),('Supplier',29),('Supplier',30),('Supplier',31),('Client',32),('Supplier',33),('Supplier',34),('Employee',35),('Employee',36);
/*!40000 ALTER TABLE `Role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Stores`
--

DROP TABLE IF EXISTS `Stores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Stores` (
  `quantity` int(11) NOT NULL,
  `rawMaterialId` int(11) NOT NULL,
  `plantId` int(11) NOT NULL,
  PRIMARY KEY (`rawMaterialId`,`plantId`),
  KEY `plantId` (`plantId`),
  CONSTRAINT `Stores_ibfk_1` FOREIGN KEY (`rawMaterialId`) REFERENCES `RawMaterial` (`rawMaterialId`),
  CONSTRAINT `Stores_ibfk_2` FOREIGN KEY (`plantId`) REFERENCES `Plant` (`plantId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Stores`
--

LOCK TABLES `Stores` WRITE;
/*!40000 ALTER TABLE `Stores` DISABLE KEYS */;
INSERT INTO `Stores` VALUES (1,1,1),(131,2,1),(10,3,1),(5,4,1);
/*!40000 ALTER TABLE `Stores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Supervisor`
--

DROP TABLE IF EXISTS `Supervisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Supervisor` (
  `employeeId` int(11) NOT NULL,
  `supervisorId` int(11) NOT NULL,
  PRIMARY KEY (`employeeId`,`supervisorId`),
  KEY `supervisorId` (`supervisorId`),
  CONSTRAINT `Supervisor_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `Employee` (`employeeId`),
  CONSTRAINT `Supervisor_ibfk_2` FOREIGN KEY (`supervisorId`) REFERENCES `Employee` (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Supervisor`
--

LOCK TABLES `Supervisor` WRITE;
/*!40000 ALTER TABLE `Supervisor` DISABLE KEYS */;
/*!40000 ALTER TABLE `Supervisor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Supplier`
--

DROP TABLE IF EXISTS `Supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Supplier` (
  `supplierId` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `contact` int(11) NOT NULL,
  PRIMARY KEY (`supplierId`),
  CONSTRAINT `Supplier_ibfk_1` FOREIGN KEY (`supplierId`) REFERENCES `User` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Supplier`
--

LOCK TABLES `Supplier` WRITE;
/*!40000 ALTER TABLE `Supplier` DISABLE KEYS */;
INSERT INTO `Supplier` VALUES (20,'sfsafas',4141),(21,'sfsafas',4141),(22,'sfsafas',4141),(23,'fdafda',41141),(24,'fdafda',41141),(25,'das',141),(26,'das',141),(27,'asf',144),(28,'asf',144),(29,'asf',144),(30,'asf',144),(31,'asf',144),(33,'afafaf',134141),(34,'afafaf',134141);
/*!40000 ALTER TABLE `Supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SupplierAddress`
--

DROP TABLE IF EXISTS `SupplierAddress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SupplierAddress` (
  `address` varchar(255) NOT NULL,
  `supplierId` int(11) NOT NULL,
  PRIMARY KEY (`address`,`supplierId`),
  KEY `supplierId` (`supplierId`),
  CONSTRAINT `SupplierAddress_ibfk_1` FOREIGN KEY (`supplierId`) REFERENCES `Supplier` (`supplierId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SupplierAddress`
--

LOCK TABLES `SupplierAddress` WRITE;
/*!40000 ALTER TABLE `SupplierAddress` DISABLE KEYS */;
/*!40000 ALTER TABLE `SupplierAddress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Supplies`
--

DROP TABLE IF EXISTS `Supplies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Supplies` (
  `rawMaterialId` int(11) NOT NULL AUTO_INCREMENT,
  `supplierId` int(11) NOT NULL,
  PRIMARY KEY (`rawMaterialId`,`supplierId`),
  KEY `supplierId` (`supplierId`),
  CONSTRAINT `Supplies_ibfk_1` FOREIGN KEY (`rawMaterialId`) REFERENCES `RawMaterial` (`rawMaterialId`),
  CONSTRAINT `Supplies_ibfk_2` FOREIGN KEY (`supplierId`) REFERENCES `Supplier` (`supplierId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Supplies`
--

LOCK TABLES `Supplies` WRITE;
/*!40000 ALTER TABLE `Supplies` DISABLE KEYS */;
INSERT INTO `Supplies` VALUES (2,26),(2,31),(3,31),(2,33);
/*!40000 ALTER TABLE `Supplies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SupplyOrder`
--

DROP TABLE IF EXISTS `SupplyOrder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SupplyOrder` (
  `supplyOrderId` int(11) NOT NULL AUTO_INCREMENT,
  `quantity` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `supplierId` int(11) NOT NULL,
  `rawMaterialId` int(11) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`supplyOrderId`),
  KEY `employeeId` (`employeeId`),
  KEY `supplierId` (`supplierId`),
  KEY `rawMaterialId` (`rawMaterialId`),
  CONSTRAINT `SupplyOrder_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `Employee` (`employeeId`),
  CONSTRAINT `SupplyOrder_ibfk_2` FOREIGN KEY (`supplierId`) REFERENCES `Supplier` (`supplierId`),
  CONSTRAINT `SupplyOrder_ibfk_3` FOREIGN KEY (`rawMaterialId`) REFERENCES `RawMaterial` (`rawMaterialId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SupplyOrder`
--

LOCK TABLES `SupplyOrder` WRITE;
/*!40000 ALTER TABLE `SupplyOrder` DISABLE KEYS */;
INSERT INTO `SupplyOrder` VALUES (1,1,11,31,2,'pending'),(2,31,11,31,2,'pending'),(3,324,11,31,3,'pending');
/*!40000 ALTER TABLE `SupplyOrder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'dafaf','faf'),(2,'fafaf','afaf'),(3,'dad','fsf'),(4,'faaf','dsf'),(5,'rqrq','ee'),(6,'fgg','r'),(7,'fgg','r'),(8,'fgg2','r'),(9,'rakshit','rakshit123'),(10,'raksht1','$2a$10$D6p52anY7egDm.VkQ4OLguSrjU9Ai.89g4haRV4s3Q/DY9I8q0x3i'),(11,'rakshit123','$2a$10$I.XNJZCRnZb1cXKdeH0UX.7/OwxxAD/hnF8A0gDrzVhHHWtEIyDDq'),(12,'superuser','$2a$10$mVVrEjiRjTZKBP9bASME9eTHr9SwjiMJTx06Pe9.ibnONwH9dDTCO'),(13,'rakshitclient','clientclient'),(14,'rakshitclient1','clientclient1'),(15,'rakshitclient2','clientclient3'),(16,'rakshitclient4','$2a$10$rVnU9UXL.89d9akOtqZWQ.pdC.hwepPEk2gs/go1sBpaxvKqFQ2xG'),(17,'faf','fsf'),(18,'faf','$2a$10$dzb5pHUX.P/DGWGXdHU4W.gCc08Uxhlqw3DbCrp67B8y28oUHJfz.'),(19,'faf','$2a$10$4uw6GYL1dT.gyOS5Hpmtm.1AXtb1Lz5TwhnHFldm5W0xYcOTEc2fO'),(20,'faf','$2a$10$x9HxMQmmGN2kgirjrjD3xuLIpgK0SD2QNKIEKvVL/jnyRTVjBhIKC'),(21,'faf','$2a$10$plwAsixSpNEd.frdoGcjgeoxI4RanmUD7gScGHqwprzBJUEv8Bh1.'),(22,'faf','$2a$10$quXAUqHY2tKlggwb9a9DXenMB26whTkDlv7xXP5rl41rn9ogmQQHa'),(23,'fafa','$2a$10$ynOX.Ew658memnSJpFIVv.mXoGScxd6rpQptyI4B1GTcj4BgAqiUW'),(24,'fafa','$2a$10$0Rlv7UjXsWKCAokQzSRWH.mK2xKEWAgmFj80FZIYXMGztl7BVHBOO'),(25,'das','$2a$10$mnCdLaR0R4yHOJQ2Xs8zX.DvzWL3Ui51DbUYYHw2CtCQM9795KRG2'),(26,'das','$2a$10$9i4FIBasmjlCMeZepaKO..07XgoffuKjr/ATEzzcfEuE2L1TerFIO'),(27,'asf','$2a$10$/TwgekIOpXVK3Zynq4jdlOqpY6XtjvNN0APLpy7JeHQPqpSLC09J2'),(28,'asf','$2a$10$/pXAIBuMWTl5lQxND89RjOHystUkd0kRgh3Kj6OVEHsznTG8CbQBK'),(29,'asf','$2a$10$VwRRXMvnrQUIJnPWcgLT7Ok37radvAHKvhgK12q7Yt6ZvhP.oxGoi'),(30,'asf','$2a$10$L.mK0zfHEq8QkVI.V5LYeu84ub/.IxYdB.mIzbjlAz/6.8nvFb4ra'),(31,'asf','$2a$10$lpkkucdZ976OfV03rRSx1uENjwNaNHZ15dna.m4WsR1VX1hPX8fp2'),(32,'rakshitclient5','$2a$10$OtnywyAIuUc/OsYkitCPD.Mo8wgSx0YinpW09oayUDqTR99/k2cOW'),(33,'supplier1','$2a$10$l/1wLjd7NVxOlFKjUSuTBewOAazOQkd2D/JBRRDXeeVoz/CHS8wo6'),(34,'supplier1','$2a$10$UqIIxOvM9qwmbweEXbAuXuRX1ex7fGlSzIx69zd35plk0gr1moqkS'),(35,'employee1','$2a$10$2q4BXt.xbV5rgjdxo01pZ..Nmh2rBjdiSi6QB4m/xCQaUJFKZMS.W'),(36,'employee1','$2a$10$6gbQKOattQAFvhheDk/3F.9BqpiX3VkEJEhJbEQVTXsEbKNjshoBi');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `View`
--

DROP TABLE IF EXISTS `View`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `View` (
  `clientId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  PRIMARY KEY (`clientId`,`productId`),
  KEY `productId` (`productId`),
  CONSTRAINT `View_ibfk_1` FOREIGN KEY (`clientId`) REFERENCES `Client` (`clientId`),
  CONSTRAINT `View_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `Products` (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `View`
--

LOCK TABLES `View` WRITE;
/*!40000 ALTER TABLE `View` DISABLE KEYS */;
/*!40000 ALTER TABLE `View` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-17 18:12:44
