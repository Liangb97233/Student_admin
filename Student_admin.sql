/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.7.36 : Database - student_admin
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`student_admin` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;

USE `student_admin`;

/*Table structure for table `s_user` */

DROP TABLE IF EXISTS `s_user`;

CREATE TABLE `s_user` (
  `username` char(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '姓名',
  `uid` char(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '学号',
  `ident` char(30) COLLATE utf8mb4_bin DEFAULT 'stu' COMMENT '身份 1为学生 2为管理员',
  `password` char(30) COLLATE utf8mb4_bin DEFAULT '123456'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `s_user` */

insert  into `s_user`(`username`,`uid`,`ident`,`password`) values 
('张三','123456','adm','123456'),
('gg','111111','stu','123456'),
('王五','222222','stu','123456'),
('赵六','333333','stu','123456'),
('孙七','444444','stu','123456'),
('彦祖','555555','stu','123456');

/*Table structure for table `score` */

DROP TABLE IF EXISTS `score`;

CREATE TABLE `score` (
  `uid` char(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `username` char(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `chinese` double DEFAULT NULL,
  `math` double DEFAULT NULL,
  `english` double DEFAULT NULL,
  `count` double DEFAULT NULL,
  `year` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `score` */

insert  into `score`(`uid`,`username`,`chinese`,`math`,`english`,`count`,`year`) values 
('123456','张三',80,77,88,231,1),
('111111','gg',80,77,88,231,1),
('222222','王五',80,77,88,231,1),
('333333','赵六',80,77,88,231,1),
('444444','孙七',80,77,88,231,1),
('555555','彦祖',80,77,88,231,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
