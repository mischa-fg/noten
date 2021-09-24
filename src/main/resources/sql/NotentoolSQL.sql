/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  mischa
 * Created: 24.09.2021
 */
DROP DATABASE IF EXISTS Notentool;
Create Database Notentool;
Use Notentool;
Create Table teilnehmer
(
ID smallint(6) auto_increment,
Anrede varchar(6),
Name varchar(100),
Vorname varchar(100),
Geburtsdatum date,
Email varchar(50),
OEBezeichnung varchar(50),
Foto varchar(250),
Primary Key (ID)
);
Create Table klasse
(
	ID int auto_increment,
	Klassenname varchar(70),
	Primary Key (ID)
);
Create Table kursleiter
(
	ID smallint(6) auto_increment,
	Name varchar(25),
	Vorname varchar(25),
	Primary Key (ID)
);
Create Table module
(
	ID smallint(6) auto_increment,
	Modulnummer varchar(15),
	Beschreibung varchar(150),
	Bezeichnung varchar(70),
	Primary Key (ID)
);
Create Table kurse
(
	ID int auto_increment,
	Datum_von date,
	Datum_bis date,
	Module_FK smallint(6),
	Kursleiter_FK smallint(6),
	Klasse_FK int,
	Primary Key (ID),
	FOREIGN Key(Module_FK) REFERENCES module(ID),
	FOREIGN Key(Kursleiter_FK) REFERENCES kursleiter(ID),
	FOREIGN Key(Klasse_FK) REFERENCES klasse(ID)
);
CREATE TABLE keyuser
(
	ID int auto_increment,
	Username VARCHAR(100),
	Passwort varchar(100),
	Apikey varchar(100),
	PRIMARY Key (ID)
);
CREATE TABLE schulfach
(
	ID int auto_increment,
	Fachname VARCHAR(45),
	BMS BOOLEAN,
	INF BOOLEAN,
	PRIMARY Key(ID)
);
CREATE TABLE pruefung
(
	ID smallint(6) auto_increment,
	Titel varchar(100),
	BMS BOOLEAN,
	Schulfach_FK int,
	PRIMARY KEY (ID),
	FOREIGN Key (Schulfach_FK) REFERENCES schulfach (ID)
);
CREATE TABLE note
(
	ID smallint(6) auto_increment,
	Note DECIMAL(5,2),
	zaehlt BOOLEAN,
	Pruefung_FK smallint(6),
	Teilnehmer_FK smallint(6),
	PRIMARY Key(ID),
	FOREIGN KEY(Pruefung_FK) REFERENCES pruefung(ID),
	FOREIGN KEY(Teilnehmer_FK) REFERENCES teilnehmer(ID)
);
CREATE TABLE schulfach_gesamtnote
(
	ID smallint(6) auto_increment,
	Note DECIMAL(5,2),
	Schulfach_FK int,
	Teilnehmer_FK smallint(6),
	PRIMARY KEY(ID),
	FOREIGN KEY(Schulfach_FK) REFERENCES schulfach(ID),
	FOREIGN KEY(Teilnehmer_FK) REFERENCES teilnehmer(ID)
);
CREATE TABLE frage
(
	ID smallint(6) auto_increment,
	Frage text,
	multiple BOOLEAN,
	PRIMARY KEY (ID)
);
CREATE TABLE rueckmeldung
(
	ID smallint(6) auto_increment,
	DatumAbgeschlossen datetime,
	Klasse_FK int,
	Module_FK smallint(6),
	Kursleiter_FK smallint(6),
	Teilnehmer_FK smallint(6),
	PRIMARY KEY (ID),
	FOREIGN KEY(Klasse_FK) REFERENCES klasse(ID),
	FOREIGN KEY(Module_FK) REFERENCES module(ID),
	FOREIGN KEY(Kursleiter_FK) REFERENCES kursleiter(ID),
	FOREIGN KEY(Teilnehmer_FK) REFERENCES teilnehmer(ID)
);
CREATE TABLE rueckmeldung2frage
(
	ID smallint(6) auto_increment,
	AntwortText MEDIUMTEXT,
	AntwortZahl int,
	Frage_FK smallint(6),
	Rueckmeldung_FK smallint(6),
	PRIMARY KEY(ID),
	FOREIGN KEY(Frage_FK) REFERENCES frage (ID),
	FOREIGN KEY (Rueckmeldung_FK) REFERENCES rueckmeldung(ID)
);
--Insert Kursleitertabelle
INSERT INTO `kursleiter` (`ID`, `Name`, `Vorname`) VALUES (NULL, 'Rohrer', 'Dominik'), (NULL, 'Heuberger', 'Daniel'), (NULL, 'Fiechter', 'Sascha'), (NULL, 'Ilg', 'Andreas'), (NULL, 'Weber', 'Martin'), (NULL, 'Bruggisser', 'Florian'), (NULL, 'Schmid', 'Sandra'), (NULL, 'Pirola', 'Carlo David'), (NULL, 'Senn', 'Pius'), (NULL, 'Schläpfer', 'Ueli'), (NULL, 'Negwer', 'Joerg'), (NULL, 'D''Allens', 'Patricia'), (NULL, 'Surber', 'Barbara'), (NULL, 'Wäber', 'Fabian'), (NULL, 'Bolli', 'Hanspeter');
INSERT INTO `kursleiter` (`ID`, `Name`, `Vorname`) VALUES (NULL, 'Widmer', 'Andreas');
--Insert Klassetabelle
INSERT INTO `klasse` (`ID`, `Klassenname`) VALUES (NULL, 'S19'), (NULL, 'S17'), (NULL, 'S20'), (NULL, 'S18'), (NULL, 'S21'), (NULL, 'A17'), (NULL, 'A18'), (NULL, 'A19'), (NULL, 'A20'), (NULL, 'A21'), (NULL, 'B18'), (NULL, 'B17'), (NULL, 'B19'), (NULL, 'B20'), (NULL, 'B21'), (NULL, 'M19'), (NULL, 'M20'), (NULL, 'M21'), (NULL, 'W18'), (NULL, 'W19'), (NULL, 'W20'), (NULL, 'W21'), (NULL, 'R21');
INSERT INTO `klasse` (`ID`, `Klassenname`) VALUES (NULL, 'P21');
--Insert Module
INSERT INTO `module` (`ID`, `Modulnummer`, `Beschreibung`, `Bezeichnung`) VALUES (NULL, 'POf', NULL, 'Project Office'), (NULL, '187', NULL, 'ICT-Arbeitsplatz'), (NULL, 'BWT', NULL, 'Basics Webtechnologien'), (NULL, '101', NULL, 'Web Modul 101'), (NULL, 'CT', NULL, 'Coach-Trainer Tag'), (NULL, '304', NULL, 'Modul 304'), (NULL, '403', NULL, 'Cobol Modul 403'), (NULL, 'BNS', NULL, 'Basics Netzwerk Server'), (NULL, 'BBC', NULL, 'Basics Bildbearbeitung'), (NULL, '*', NULL, 'Spezialtag');
INSERT INTO `module` (`ID`, `Modulnummer`, `Beschreibung`, `Bezeichnung`) VALUES (NULL, 'OfG', NULL, 'Office Grundlagen'), (NULL, '130', NULL, 'Modul 130'), (NULL, 'CS', NULL, 'Cyber Security'), (NULL, '127', NULL, 'Modul 127'), (NULL, '105', NULL, 'Modul 105'), (NULL, 'M101', NULL, 'Modul 101'), (NULL, 'CG', NULL, 'C-Grundlagen'), (NULL, '184', NULL, 'Modul 184'), (NULL, 'M187', NULL, 'Modul 187'), (NULL, '106', NULL, 'Modul 106'), (NULL, '223', NULL, 'Modul 223'), (NULL, '269', NULL, 'Modul 269'), (NULL, 'M403', NULL, 'Modul 403');
--Insert Fragen
INSERT INTO `frage` (`ID`, `Frage`, `multiple`) VALUES (NULL, 'Die Dozentin/ der Dozent  zeigt mir klar auf, was ich in diesem Modul lernen kann (Lernziele).', '1'), (NULL, 'Die von der Dozentin/ dem Dozent angebotenen Inhalte sind verständlich vorgetragen und entsprechen dem aktuellem Erkenntnisstand des betreffenden Faches.', '1'), (NULL, 'Der Dozentin/ dem Dozent gelingt es mein Interesse  zu wecken und eine konzentrierte, engagierte Mitarbeit herbeizuführen.', '1'), (NULL, 'Die Beziehung der Dozentin/ des Dozenten zu mir ist wertschätzend, freundlich und respektvoll.', '1'), (NULL, 'Die Dozentin/ der Dozent sorgt für Abwechslung in Bezug der Methodik und fördert den Praxisbezug.', '1'), (NULL, 'Die Dozentin/ der Dozent  fördert eine gute Arbeitsatmosphäre und fordert einen respektvollen Umgang zwischen allen Beteiligten.', '1'), (NULL, 'Der inhaltliche Aufbau ist für mich verständlich strukturiert und gut nachvollziehbar.', '1'), (NULL, 'LearningView hat mich bei meinem Lernerfolg wesentlich unterstützt.', '1'), (NULL, 'Der inhaltliche Aufbau ist für mich verständlich strukturiert und gut nachvollziehbar.\r\n\r\n', '1'), (NULL, 'Das Arbeiten mit LearningView ist unkompliziert.', '1'), (NULL, 'Mein Mitdenken und meine Initiative wird von der Dozentin/ dem Dozenten gefördert und unterstützt.', '1'), (NULL, 'Ich bin in der Lage den wesentlichen Inhalt dieses Moduls in der Praxis anzuwenden.', '1'), (NULL, 'Ich bin mit meinem Lernerfolg zufrieden.', '1'), (NULL, 'Was hat mich in meinem Lernen unterstützt? ___', '0'), (NULL, 'Was hat mich in meinem Lernen behindert? ___', '0'), (NULL, 'Wie habe ich mich selbst eingebracht? ___', '0');
--Insert Schulfach
INSERT INTO `schulfach` (`ID`, `Fachname`, `BMS`, `INF`) VALUES (NULL, 'Mathematik', '1', '0'), (NULL, 'Deutsch', '1', '0'), (NULL, 'Informatik', '0', '1');
--Insert Pruefungen
INSERT INTO `pruefung` (`ID`, `Titel`, `BMS`, `Schulfach_FK`) VALUES (NULL, 'Quadratische Funktionen', NULL, '1'), (NULL, 'Buchtest Wilhelm Tell', NULL, '2'), (NULL, 'Modul 226a', NULL, '3');
--Insert Noten
INSERT INTO `note` (`ID`, `Note`, `zaehlt`, `Pruefung_FK`, `Teilnehmer_FK`) VALUES (NULL, '5.2', '1', '2', '1'), (NULL, '5', '1', '3', '1'), (NULL, '3.5', '1', '1', '1'), (NULL, '3.4', '1', '3', '2'), (NULL, '5.5', '1', '1', '2'), (NULL, '4.5', '1', '2', '2');

