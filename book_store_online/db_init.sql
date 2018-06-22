/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.0.67-community-nt : Database - bookstore
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE `book-store` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `book-store`;

/*Table structure for table `accounts` */

DROP TABLE IF EXISTS `accounts`;

CREATE TABLE `accounts` (
  `accountId` int(11) NOT NULL DEFAULT '0',
  `balance` int(11) DEFAULT '1000',
  PRIMARY KEY (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table structure for table `books` */

DROP TABLE IF EXISTS `books`;

CREATE TABLE `books` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(30) NOT NULL,
  `title` varchar(30) NOT NULL,
  `price` float NOT NULL,
  `publishingDate` date NOT NULL,
  `salesAmount` int(11) NOT NULL,
  `storeNumber` int(11) NOT NULL,
  `remark` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Data for the table `books` */

insert  into `books`(`Id`,`Author`,`Title`,`Price`,`Publishingdate`,`Salesamount`,`Storenumber`,`Remark`) values (1,'Tom','Java 编程',50,'2009-06-22',17,83,'Good Java Book'),(2,'Jerry','Oracle DBA',60,'2009-06-22',12,88,'Good Oracle Book'),(3,'Bob','Ruby',50,'2009-06-22',12,88,'Good 0'),(4,'Mike','Javascript',51,'2009-06-22',0,100,'Good 1'),(5,'Rose','Ajax',52,'2009-06-22',0,100,'Good 2'),(6,'Backham','Struts',53,'2009-06-22',0,100,'Good 3'),(7,'Zidon','Hibernate',54,'2009-06-22',2,12,'Good 4'),(8,'Ronaerdo','Spring',55,'2009-06-22',2,13,'Good 5'),(9,'Clinsman','Cvs',56,'2009-06-22',0,16,'Good 6'),(10,'Kaka','Seo',57,'2009-06-22',0,17,'Good 7'),(11,'Lauer','Lucence',58,'2009-06-22',0,18,'Good 8'),(12,'Kasi','Guice',59,'2009-06-22',0,19,'Good 9'),(13,'Prierlo','Mysql',60,'2009-06-22',6,14,'Good 10'),(14,'Haohaidong','DB2',61,'2009-06-22',9,12,'Good 11'),(15,'Feige','Sybase',62,'2009-06-22',8,14,'Good 12'),(16,'Tuoleisi','DBDesign',63,'2009-06-22',0,23,'Good 13'),(17,'Jielade','Eclipse',64,'2009-06-22',0,24,'Good 14'),(18,'Teli','Netbeans',65,'2009-06-22',0,25,'Good 15'),(19,'Lapade','C#',66,'2009-06-22',0,26,'Good 16'),(20,'Runi','JDBC',67,'2009-06-22',0,27,'Good 17'),(21,'JoeKeer','Php',68,'2009-06-22',0,28,'Good 18'),(22,'Jordan','MysqlFront',69,'2009-06-22',5,24,'Good 19'),(23,'Yaoming','NoteBook',70,'2009-06-22',5,25,'Good 20'),(24,'Yi','C',71,'2009-06-22',5,26,'Good 21'),(25,'Sun','Css',72,'2009-06-22',0,32,'Good 22'),(26,'Xuliang','JQuery',73,'2009-06-22',0,33,'Good 23'),(27,'Meixi','Ext',74,'2009-06-22',0,34,'Good 24'),(28,'Apple','iphone',75,'2009-06-22',0,35,'Good 25'),(29,'Aigo','dc',76,'2009-06-22',0,36,'Good 26'),(30,'Sony','psp',77,'2009-06-22',0,100,'Good 27'),(31,'IRiver','mp3',78,'2009-06-22',0,100,'Good 28'),(32,'Sansing','TV',79,'2009-06-22',0,100,'Good 29');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `email` varchar(255) NOT NULL DEFAULT '',
  `creditCard` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


/*Table structure for table `trades` */

DROP TABLE IF EXISTS `trades`;

CREATE TABLE `trades` (
  `tradeId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL DEFAULT '0',
  `tradeTime` date NOT NULL DEFAULT '0000-00-00',
  `name` varchar(255) NOT NULL DEFAULT '',
  `telephone` varchar(255) NOT NULL DEFAULT '',
  `address` varchar(255) NOT NULL DEFAULT '',
  `status` varchar(255) NOT NULL DEFAULT '已付款',
  PRIMARY KEY (`tradeId`),
  KEY `userId` (`userId`),
  CONSTRAINT `trades_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


/*Table structure for table `tradeitems` */

DROP TABLE IF EXISTS `tradeitems`;

CREATE TABLE `tradeitems` (
  `itemId` int(11) NOT NULL AUTO_INCREMENT,
  `bookId` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `tradeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`itemId`),
  KEY `bookId` (`bookId`),
  KEY `tradeId` (`tradeId`),
  CONSTRAINT `tradeitems_ibfk_1` FOREIGN KEY (`bookId`) REFERENCES `books` (`Id`),
  CONSTRAINT `tradeitems_ibfk_2` FOREIGN KEY (`tradeId`) REFERENCES `trades` (`tradeId`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;


DELIMITER ||
/*Trigger `trig_new_user` */

CREATE TRIGGER trig_net_user 
	AFTER INSERT ON users
  FOR EACH ROW
	BEGIN
    INSERT INTO accounts(accountId, balance) VALUES(NEW.userId,1000);
	END
  ||

/*Trigger `trig_delete_user` */

CREATE TRIGGER trig_delete_user 
	AFTER DELETE ON users
  FOR EACH ROW
	BEGIN
    DELETE FROM accounts WHERE accountId = OLD.userId;
	END
  ||
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
