-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 02 Cze 2019, 22:47
-- Wersja serwera: 10.1.39-MariaDB
-- Wersja PHP: 7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
drop database if exists apteka;
create database apteka;
use apteka;
--
-- Baza danych: `apteka`
--

--
-- Struktura tabeli dla tabeli `dostawa`
--

CREATE TABLE `dostawa` (
  `ID_dostawy` int(11) NOT NULL,
  `Data_dostarczenia` date NOT NULL,
  `Nazwa_hurtowni` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `Ilosc` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `dostawa`
--

INSERT INTO `dostawa` (`ID_dostawy`, `Data_dostarczenia`, `Nazwa_hurtowni`, `Ilosc`) VALUES
(3, '2019-06-30', 'Super Hurtownia', 5),
(4, '2019-11-15', 'DuplexPol', 55),
(5, '2019-06-19', 'Nowex', 8),
(6, '2019-06-17', 'Portwest', 11),
(7, '2019-07-10', 'La-Aurora', 32),
(8, '2019-07-08', 'Outletwatch', 7),
(9, '2019-07-02', 'Hanipol', 6),
(10, '2019-07-03', 'Sentiell', 34);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `dostawa_magazyn`
--

CREATE TABLE `dostawa_magazyn` (
  `ID_dostawa_magazyn` int(11) NOT NULL,
  `ID_dostawy` int(11) NOT NULL,
  `ID_leku` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `dostawa_magazyn`
--

INSERT INTO `dostawa_magazyn` (`ID_dostawa_magazyn`, `ID_dostawy`, `ID_leku`) VALUES
(6, 3, 6),
(7, 4, 6),
(8, 3, 5),
(9, 7, 8),
(10, 8, 8),
(11, 8, 16),
(12, 10, 7),
(13, 9, 18),
(14, 10, 18),
(15, 3, 9),
(16, 6, 10);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `klient`
--

CREATE TABLE `klient` (
  `ID_Klienta` int(11) NOT NULL,
  `Imie` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `Nazwisko` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `PESEL` varchar(11) COLLATE utf8_polish_ci NOT NULL,
  `Telefon` varchar(9) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `klient`
--

INSERT INTO `klient` (`ID_Klienta`, `Imie`, `Nazwisko`, `PESEL`, `Telefon`) VALUES
(8, 'Michal', 'Nowak', '12321312321', '321312312'),
(10, 'Andrzej', 'Kowalski', '78472132132', '237183728'),
(12, 'Tomasz', 'Kopyto', '74128213213', '213873271'),
(13, 'Adrian', 'Pedrow', '38271932179', '237921739'),
(14, 'Bartosz', 'Michalski', '21839213213', '328195931'),
(15, 'Piotr', 'Cegla', '32179327193', '237291379'),
(16, 'Zbyszek', 'Balon', '38192372312', '123781237'),
(17, 'Kamil', 'Woloszyn', '73812378263', '123687213'),
(18, 'Marcin', 'Bigos', '31829321791', '123793217'),
(19, 'Stefan', 'Lepski', '90712653162', '897521321');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `magazyn_leki`
--

CREATE TABLE `magazyn_leki` (
  `ID_leku` int(11) NOT NULL,
  `Nazwa` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `Cena` decimal(10,0) NOT NULL,
  `Ilosc` int(11) NOT NULL,
  `Data_waznosci` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `magazyn_leki`
--

INSERT INTO `magazyn_leki` (`ID_leku`, `Nazwa`, `Cena`, `Ilosc`, `Data_waznosci`) VALUES
(5, 'Apap', '6', 12, '2019-06-28'),
(6, 'Ibuprom', '12', 6, '2019-06-23'),
(7, 'Cirrus', '23', 19, '2019-10-24'),
(8, 'Letizen', '19', 4, '2019-10-01'),
(9, 'Jovesto', '29', 31, '2020-06-18'),
(10, 'Kofepar', '11', 3, '2020-10-15'),
(11, 'Amol', '41', 2, '2019-12-31'),
(12, 'Hitaxa', '32', 6, '2019-12-12'),
(13, 'Kodeina', '31', 7, '2020-08-07'),
(14, 'Nimesil', '21', 44, '2020-08-18'),
(15, 'Aktywia', '13', 1, '2019-11-08'),
(16, 'Doreta', '29', 66, '2019-11-01'),
(17, 'Nolicin', '17', 7, '2019-09-13'),
(18, 'Gabapentin', '25', 32, '2019-11-08');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `recepta`
--

CREATE TABLE `recepta` (
  `ID_recepty` int(11) NOT NULL,
  `Leki` text COLLATE utf8_polish_ci NOT NULL,
  `Klient_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `recepta`
--

INSERT INTO `recepta` (`ID_recepty`, `Leki`, `Klient_ID`) VALUES
(8, 'Apap, Ibuprom', 8),
(9, 'Jovesto, Letizen, Cirrus', 10),
(10, 'Hitaxa, Amol, Kofepar', 15),
(11, 'Kodeina, Nimesil, Aktywia, Doreta', 18),
(12, 'Nolicin, Gabapentin, Apap', 19);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `recepta_magazyn`
--

CREATE TABLE `recepta_magazyn` (
  `Id_recepta_magazyn` int(11) NOT NULL,
  `ID_recepty` int(11) NOT NULL,
  `ID_leku` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `recepta_magazyn`
--

INSERT INTO `recepta_magazyn` (`Id_recepta_magazyn`, `ID_recepty`, `ID_leku`) VALUES
(9, 8, 5),
(10, 8, 6),
(11, 9, 9),
(12, 9, 8),
(13, 9, 7),
(14, 10, 12),
(15, 10, 11),
(16, 10, 10),
(17, 11, 13),
(18, 11, 14),
(19, 11, 15),
(20, 11, 16),
(21, 12, 17),
(22, 12, 18),
(23, 12, 5);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `dostawa`
--
ALTER TABLE `dostawa`
  ADD PRIMARY KEY (`ID_dostawy`);

--
-- Indeksy dla tabeli `dostawa_magazyn`
--
ALTER TABLE `dostawa_magazyn`
  ADD PRIMARY KEY (`ID_dostawa_magazyn`),
  ADD KEY `Dostawa-magazyn_fk0` (`ID_dostawy`),
  ADD KEY `Dostawa-magazyn_fk1` (`ID_leku`);

--
-- Indeksy dla tabeli `klient`
--
ALTER TABLE `klient`
  ADD PRIMARY KEY (`ID_Klienta`),
  ADD UNIQUE KEY `PESEL` (`PESEL`);

--
-- Indeksy dla tabeli `magazyn_leki`
--
ALTER TABLE `magazyn_leki`
  ADD PRIMARY KEY (`ID_leku`);

--
-- Indeksy dla tabeli `recepta`
--
ALTER TABLE `recepta`
  ADD PRIMARY KEY (`ID_recepty`),
  ADD KEY `Recepta_fk0` (`Klient_ID`);

--
-- Indeksy dla tabeli `recepta_magazyn`
--
ALTER TABLE `recepta_magazyn`
  ADD PRIMARY KEY (`Id_recepta_magazyn`),
  ADD KEY `Recepta-magazyn_fk0` (`ID_recepty`),
  ADD KEY `Recepta-magazyn_fk1` (`ID_leku`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `dostawa`
--
ALTER TABLE `dostawa`
  MODIFY `ID_dostawy` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT dla tabeli `dostawa_magazyn`
--
ALTER TABLE `dostawa_magazyn`
  MODIFY `ID_dostawa_magazyn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT dla tabeli `klient`
--
ALTER TABLE `klient`
  MODIFY `ID_Klienta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT dla tabeli `magazyn_leki`
--
ALTER TABLE `magazyn_leki`
  MODIFY `ID_leku` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT dla tabeli `recepta`
--
ALTER TABLE `recepta`
  MODIFY `ID_recepty` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT dla tabeli `recepta_magazyn`
--
ALTER TABLE `recepta_magazyn`
  MODIFY `Id_recepta_magazyn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `dostawa_magazyn`
--
ALTER TABLE `dostawa_magazyn`
  ADD CONSTRAINT `Dostawa-magazyn_fk0` FOREIGN KEY (`ID_dostawy`) REFERENCES `dostawa` (`ID_dostawy`),
  ADD CONSTRAINT `Dostawa-magazyn_fk1` FOREIGN KEY (`ID_leku`) REFERENCES `magazyn_leki` (`ID_leku`);

--
-- Ograniczenia dla tabeli `recepta`
--
ALTER TABLE `recepta`
  ADD CONSTRAINT `Recepta_fk0` FOREIGN KEY (`Klient_ID`) REFERENCES `klient` (`ID_Klienta`);

--
-- Ograniczenia dla tabeli `recepta_magazyn`
--
ALTER TABLE `recepta_magazyn`
  ADD CONSTRAINT `Recepta-magazyn_fk0` FOREIGN KEY (`ID_recepty`) REFERENCES `recepta` (`ID_recepty`),
  ADD CONSTRAINT `Recepta-magazyn_fk1` FOREIGN KEY (`ID_leku`) REFERENCES `magazyn_leki` (`ID_leku`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- --------------------------------------------------------

DELIMITER $$
--
-- Procedury
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `dodaj_dostawa` (IN `nazwa` VARCHAR(50), IN `data` DATE, IN `ilosc` INT(11))  NO SQL
BEGIN
    	INSERT INTO dostawa(Nazwa_hurtowni, Data_dostarczenia, Ilosc)
        	VALUES (nazwa, data, ilosc);
    END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `dodaj_klienta` (IN `imie` VARCHAR(50), IN `nazwisko` VARCHAR(50), IN `pesel` VARCHAR(11), IN `telefon` VARCHAR(9))  BEGIN
    	INSERT INTO klient(Imie, Nazwisko, PESEL, Telefon)
        	VALUES (imie, nazwisko, pesel, telefon);
            
    END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `dodaj_magazyn` (IN `nazwa` VARCHAR(50), IN `cena` DECIMAL(10), IN `ilosc` INT(11), IN `data` DATE)  NO SQL
BEGIN
    	INSERT INTO magazyn_leki(Nazwa, Cena, Ilosc, Data_waznosci)
        	VALUES (nazwa, cena, ilosc, data);
    END$$

DELIMITER ;

-- --------------------------------------------------------
