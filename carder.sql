-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.27-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for carder
CREATE DATABASE IF NOT EXISTS `carder` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;
USE `carder`;

-- Dumping structure for table carder.attendance
CREATE TABLE IF NOT EXISTS `attendance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attendance` int(11) DEFAULT NULL,
  `common_status` int(11) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `attend_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKokwy6wgnosxh1ydshrvd16sxr` (`attend_user`),
  CONSTRAINT `FKokwy6wgnosxh1ydshrvd16sxr` FOREIGN KEY (`attend_user`) REFERENCES `user` (`emp_number`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- Data exporting was unselected.

-- Dumping structure for table carder.department
CREATE TABLE IF NOT EXISTS `department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `common_status` int(11) DEFAULT NULL,
  `department_contact_number` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `department_description` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `department_title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- Data exporting was unselected.

-- Dumping structure for table carder.emp_leave
CREATE TABLE IF NOT EXISTS `emp_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `common_status` int(11) DEFAULT NULL,
  `leave_date` datetime(6) DEFAULT NULL,
  `reason` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `leave_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK584753pbfqn9l6ws6o65bj8cr` (`leave_user`),
  CONSTRAINT `FK584753pbfqn9l6ws6o65bj8cr` FOREIGN KEY (`leave_user`) REFERENCES `user` (`emp_number`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- Data exporting was unselected.

-- Dumping structure for table carder.holiday
CREATE TABLE IF NOT EXISTS `holiday` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `common_status` int(11) DEFAULT NULL,
  `holiday` datetime(6) DEFAULT NULL,
  `reason` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- Data exporting was unselected.

-- Dumping structure for table carder.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `common_status` int(11) DEFAULT NULL,
  `emp_number` bigint(20) DEFAULT NULL,
  `mobile` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `nic` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_role` int(11) DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_c1u1muochy6pulk9j61hbtsi1` (`emp_number`),
  KEY `FKgkh2fko1e4ydv1y6vtrwdc6my` (`department_id`),
  CONSTRAINT `FKgkh2fko1e4ydv1y6vtrwdc6my` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- Data exporting was unselected.

-- Dumping structure for table carder.vacate_list
CREATE TABLE IF NOT EXISTS `vacate_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `common_status` int(11) DEFAULT NULL,
  `vacate_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe5wc89ohroaflote4hkfkpeba` (`vacate_user`),
  CONSTRAINT `FKe5wc89ohroaflote4hkfkpeba` FOREIGN KEY (`vacate_user`) REFERENCES `user` (`emp_number`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- Data exporting was unselected.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
