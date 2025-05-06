-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: jobstatistics
-- ------------------------------------------------------
-- Server version	8.4.4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cong_viec`
--

DROP TABLE IF EXISTS `cong_viec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cong_viec` (
  `SoCMND` bigint NOT NULL,
  `NgayVaoCongTy` date NOT NULL,
  `MaNganh` int NOT NULL,
  `TenCongTy` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ThoiGianLamViec` int DEFAULT NULL,
  PRIMARY KEY (`SoCMND`,`NgayVaoCongTy`,`MaNganh`),
  KEY `MaNganh` (`MaNganh`),
  CONSTRAINT `cong_viec_ibfk_1` FOREIGN KEY (`SoCMND`) REFERENCES `sinhvien` (`SoCMND`),
  CONSTRAINT `cong_viec_ibfk_2` FOREIGN KEY (`MaNganh`) REFERENCES `nganh` (`MaNganh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cong_viec`
--

LOCK TABLES `cong_viec` WRITE;
/*!40000 ALTER TABLE `cong_viec` DISABLE KEYS */;
/*!40000 ALTER TABLE `cong_viec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nganh`
--

DROP TABLE IF EXISTS `nganh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nganh` (
  `MaNganh` int NOT NULL,
  `TenNganh` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `LoaiNganh` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`MaNganh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nganh`
--

LOCK TABLES `nganh` WRITE;
/*!40000 ALTER TABLE `nganh` DISABLE KEYS */;
INSERT INTO `nganh` VALUES (1,'Công nghệ Thông tin','Đại học'),(2,'Kỹ thuật Cơ khí','Đại học'),(3,'Quản trị Kinh doanh','Đại học'),(4,'Điện tử Viễn thông','Đại học'),(5,'Kế toán','Cao đẳng'),(6,'Cơ khí Chế tạo máy','Trung cấp'),(7,'Thiết kế Đồ họa','Trung cấp'),(8,'Kỹ thuật phần mềm','Đại học');
/*!40000 ALTER TABLE `nganh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sinhvien`
--

DROP TABLE IF EXISTS `sinhvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sinhvien` (
  `SoCMND` bigint NOT NULL,
  `HoTen` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SoDT` bigint DEFAULT NULL,
  `DiaChi` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SoCMND`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sinhvien`
--

LOCK TABLES `sinhvien` WRITE;
/*!40000 ALTER TABLE `sinhvien` DISABLE KEYS */;
INSERT INTO `sinhvien` VALUES (11123456,'Trần Văn B','B@gmail.com',159632478,'Gia Lai'),(789456123,'Phan Thành Danh','danhheo@gmail.com',981521407,'Phú Tài'),(1234567895,'Nguyễn Văn A','a@gmail.com',456123789,'Sài Gòn'),(52204015993,'Trần Quốc Khánh','tranquockhanh010904@gmail.com',789544971,'Quy Nhơn, Bình Định'),(52204015994,'Test','test@email.com',789544972,'Quy Nhơn, Bình Định');
/*!40000 ALTER TABLE `sinhvien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tot_nghiep`
--

DROP TABLE IF EXISTS `tot_nghiep`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tot_nghiep` (
  `SoCMND` bigint NOT NULL,
  `MaTruong` int NOT NULL,
  `MaNganh` int NOT NULL,
  `HeTN` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `NgayTN` date DEFAULT NULL,
  `LoaiTN` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SoCMND`,`MaTruong`,`MaNganh`),
  KEY `MaTruong` (`MaTruong`),
  KEY `MaNganh` (`MaNganh`),
  CONSTRAINT `tot_nghiep_ibfk_1` FOREIGN KEY (`SoCMND`) REFERENCES `sinhvien` (`SoCMND`),
  CONSTRAINT `tot_nghiep_ibfk_2` FOREIGN KEY (`MaTruong`) REFERENCES `truong` (`MaTruong`),
  CONSTRAINT `tot_nghiep_ibfk_3` FOREIGN KEY (`MaNganh`) REFERENCES `nganh` (`MaNganh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tot_nghiep`
--

LOCK TABLES `tot_nghiep` WRITE;
/*!40000 ALTER TABLE `tot_nghiep` DISABLE KEYS */;
INSERT INTO `tot_nghiep` VALUES (11123456,4,4,'Chính Quy','2025-01-09','Đại Học'),(789456123,6,8,'Chính Quy','2022-12-22','Đại Học'),(1234567895,1,1,'Chính Quy','2025-04-01','Cao Đẳng'),(52204015993,1,1,'Chính Quy','2022-07-28','Đại Học'),(52204015994,2,1,'asdas','2025-05-01','dasdas');
/*!40000 ALTER TABLE `tot_nghiep` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `truong`
--

DROP TABLE IF EXISTS `truong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `truong` (
  `MaTruong` int NOT NULL,
  `TenTruong` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DiaChi` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SoDT` bigint DEFAULT NULL,
  PRIMARY KEY (`MaTruong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `truong`
--

LOCK TABLES `truong` WRITE;
/*!40000 ALTER TABLE `truong` DISABLE KEYS */;
INSERT INTO `truong` VALUES (1,'Đại học Bách Khoa Hà Nội','Số 1 Đại Cồ Việt, Hà Nội',123456789),(2,'Đại học Sư Phạm Kỹ Thuật TP.HCM','Số 1 Võ Văn Ngân, TP.HCM',987654321),(3,'Cao đẳng Công nghiệp Huế','Số 70 Nguyễn Huệ, Huế',456789123),(4,'Trung cấp Nghề Đà Nẵng','Số 45 Lê Duẩn, Đà Nẵng',321654987),(5,'Đại học Kinh tế Quốc dân','Số 207 Giải Phóng, Hà Nội',654123987),(6,'Đại học Quy Nhơn','170 An Dương Vương, Quy Nhơn',2563846156);
/*!40000 ALTER TABLE `truong` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-06 15:33:36
