CREATE DATABASE  IF NOT EXISTS `inwentarz_dev` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `inwentarz_dev`;

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
-- Table structure for table `autorzy`
--

DROP TABLE IF EXISTS `autorzy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `autorzy` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `imiona` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `nazwisko` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1056 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='Autorzy książek';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `autorzy_ksiazki`
--

DROP TABLE IF EXISTS `autorzy_ksiazki`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `autorzy_ksiazki` (
  `id_autora` int(10) unsigned NOT NULL DEFAULT '0',
  `id_ksiazki` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_autora`,`id_ksiazki`),
  KEY `FK_autorzy_ksiazki_2` (`id_ksiazki`),
  CONSTRAINT `FK_autorzy_ksiazki_1` FOREIGN KEY (`id_autora`) REFERENCES `autorzy` (`id`),
  CONSTRAINT `FK_autorzy_ksiazki_2` FOREIGN KEY (`id_ksiazki`) REFERENCES `ksiazki` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Autorzy książek; InnoDB free: 44032 kB';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `ksiazki`
--

DROP TABLE IF EXISTS `ksiazki`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ksiazki` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tytul` varchar(255) NOT NULL DEFAULT '',
  `status` int(10) unsigned NOT NULL DEFAULT '0',
  `data_wlaczenia` datetime DEFAULT NULL,
  `data_utworzenia` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `cena` decimal(10,2) DEFAULT NULL,
  `wartosc` decimal(10,2) DEFAULT NULL,
  `zrodlo` varchar(255) DEFAULT NULL,
  `data_aktualizacji` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `uwagi` text,
  `data_weryfikacji` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ksiazki_1` (`status`),
  KEY `FK_ksiazki_2` (`zrodlo`),
  CONSTRAINT `FK_ksiazki_1` FOREIGN KEY (`status`) REFERENCES `statusy_ksiazek` (`id`),
  CONSTRAINT `FK_ksiazki_2` FOREIGN KEY (`zrodlo`) REFERENCES `zrodla` (`nazwa`)
) ENGINE=InnoDB AUTO_INCREMENT=1551 DEFAULT CHARSET=utf8 COMMENT='Książki';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ksiazki_skontrum`
--

DROP TABLE IF EXISTS `ksiazki_skontrum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ksiazki_skontrum` (
  `id_skontrum` int(10) unsigned NOT NULL DEFAULT '0',
  `id_ksiazki` int(10) unsigned NOT NULL DEFAULT '0',
  `data_weryfikacji` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id_skontrum`,`id_ksiazki`),
  KEY `FK_ksiazki_skontrum_2` (`id_ksiazki`),
  CONSTRAINT `FK_ksiazki_skontrum_1` FOREIGN KEY (`id_skontrum`) REFERENCES `skontrum` (`id`),
  CONSTRAINT `FK_ksiazki_skontrum_2` FOREIGN KEY (`id_ksiazki`) REFERENCES `ksiazki` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rejestr_operacji`
--

DROP TABLE IF EXISTS `rejestr_operacji`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rejestr_operacji` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_ksiazki` int(10) unsigned NOT NULL DEFAULT '0',
  `typ` int(10) unsigned NOT NULL DEFAULT '0',
  `opis` varchar(1024) DEFAULT NULL,
  `data` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `FK_rejestr_operacji_1` (`typ`),
  KEY `FK_rejestr_operacji_2` (`id_ksiazki`),
  CONSTRAINT `FK_rejestr_operacji_2` FOREIGN KEY (`id_ksiazki`) REFERENCES `ksiazki` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9962 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `skontrum`
--

DROP TABLE IF EXISTS `skontrum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `skontrum` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `data_rozpoczecia` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `data_zakonczenia` datetime DEFAULT NULL,
  `status` varchar(45) NOT NULL DEFAULT '',
  `liczba_posiadanych_ksiazek` int(10) unsigned NOT NULL DEFAULT '0',
  `liczba_zweryfikowanych_ksiazek` int(10) unsigned NOT NULL DEFAULT '0',
  `liczba_niezweryfikowanych_ksiazek` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `statusy_ksiazek`
--

DROP TABLE IF EXISTS `statusy_ksiazek`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statusy_ksiazek` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nazwa` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='Statusy książek';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statusy_ksiazek`
--

LOCK TABLES `statusy_ksiazek` WRITE;
/*!40000 ALTER TABLE `statusy_ksiazek` DISABLE KEYS */;
INSERT INTO `statusy_ksiazek` VALUES (0,'Dostępna'),(1,'Wypożyczona'),(2,'Wycofana'),(99,'Usunieta');
/*!40000 ALTER TABLE `statusy_ksiazek` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zrodla`
--

DROP TABLE IF EXISTS `zrodla`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zrodla` (
  `nazwa` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`nazwa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Źródło pochodzenia książek';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zrodla`
--

LOCK TABLES `zrodla` WRITE;
/*!40000 ALTER TABLE `zrodla` DISABLE KEYS */;
INSERT INTO `zrodla` VALUES ('Kupno'),('Nagroda'),('Nieznane'),('Prezent'),('Wynagrodzenie'),('Znaleziona');
/*!40000 ALTER TABLE `zrodla` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
