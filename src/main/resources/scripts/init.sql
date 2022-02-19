CREATE DATABASE `news_database` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `useraccount` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_searches` (
  `id` int NOT NULL auto_increment primary key,
  `AccountID` int NOT NULL,
  `method` varchar(45) NOT NULL,
  `country` varchar(100) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `q` varchar(500) DEFAULT NULL,
  `sources` varchar(500) DEFAULT NULL,
  `from` varchar(45) DEFAULT NULL,
  `to` varchar(45) DEFAULT NULL,
  `language` varchar(45) DEFAULT NULL,
  CONSTRAINT `account_id_fkey` FOREIGN KEY (`AccountID`) REFERENCES `useraccount` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


