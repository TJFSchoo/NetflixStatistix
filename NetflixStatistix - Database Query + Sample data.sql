USE master
DROP DATABASE IF EXISTS NetflixStatistix;
CREATE DATABASE NetflixStatistix;
GO
USE NetflixStatistix;


/* Tables */

CREATE TABLE Abonnee(
AbonneeID int NOT NULL PRIMARY KEY,
Email nvarchar(50) NOT NULL,
Wachtwoord nvarchar(50) NOT NULL,
Naam nvarchar(20) NOT NULL,
Straat nvarchar(40) NOT NULL,
Huisnummer nvarchar(8) NOT NULL,
Postcode nvarchar(7) NOT NULL,
Woonplaats nvarchar(20) NOT NULL
);

CREATE TABLE Profiel(
Profielnaam nvarchar(30) NOT NULL,
Geboortedatum date NOT NULL,
AbonneeID int CONSTRAINT Profiel_FK REFERENCES Abonnee(AbonneeID) ON DELETE CASCADE ON UPDATE CASCADE,
PRIMARY KEY (Profielnaam, AbonneeID)
);

CREATE TABLE Programma(
ProgrammaID int NOT NULL,
Titel nvarchar(50) NOT NULL,
PRIMARY KEY(ProgrammaID, Titel)
);

CREATE TABLE Film(
FilmID int NOT NULL PRIMARY KEY,
ProgrammaID int NOT NULL,
Titel nvarchar(50) NOT NULL,
GeschikteLeeftijd int NOT NULL,
Taal nvarchar(20) NOT NULL,
Tijdsduur time(7) NOT NULL,
Genre nvarchar(20) NOT NULL,
FOREIGN KEY(ProgrammaID,Titel) REFERENCES Programma(ProgrammaID,Titel) ON DELETE CASCADE ON UPDATE CASCADE,
);

CREATE TABLE Serie(
SerieNaam nvarchar(40) NOT NULL PRIMARY KEY,
Seizoenen int NOT NULL,
GeschikteLeeftijd int NOT NULL,
Taal nvarchar(20) NOT NULL,
Genre nvarchar(20) NOT NULL,
LijktOp nvarchar(40),             /* LET OP: Nog niet op NOT NULL, mogelijk empty */
);

CREATE TABLE Aflevering(
AfleveringID int NOT NULL PRIMARY KEY,
Serie nvarchar(40) CONSTRAINT Serie_FK REFERENCES Serie(SerieNaam) ON DELETE CASCADE ON UPDATE CASCADE,
ProgrammaID int NOT NULL,
Titel nvarchar(50) NOT NULL,
SeizoenEnAflevering nvarchar(6) NOT NULL,			  /* Bedoeling is bijv. S01E04 */		
Tijdsduur time(7) NOT NULL,
FOREIGN KEY(ProgrammaID,Titel) REFERENCES Programma(ProgrammaID,Titel) ON DELETE CASCADE ON UPDATE CASCADE,
);

CREATE TABLE BekekenProgramma(
Percentage int NOT NULL,
Profielnaam nvarchar(30) NOT NULL,
AbonneeID int NOT NULL,
LaatstBekeken datetime NOT NULL,
ProgrammaID int NOT NULL,
Titel nvarchar(50) NOT NULL,
FOREIGN KEY(Profielnaam,AbonneeID) REFERENCES Profiel(Profielnaam,AbonneeID) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(ProgrammaID,Titel) REFERENCES Programma(ProgrammaID,Titel) ON DELETE CASCADE ON UPDATE CASCADE,
PRIMARY KEY(Profielnaam,AbonneeID,ProgrammaID)
);



/* Accounts */

/* Fam. van Raalte <- BB Sample*/
INSERT INTO Abonnee (AbonneeID,Email,Wachtwoord,Naam,Straat,Huisnummer,Postcode,Woonplaats) 
VALUES ('1215426','VanRaalte@hotmail.com','pandabeer123','Fam. van Raalte','Schopenhauerdijkje','5','3991 ML','Houten');

INSERT INTO Profiel (Profielnaam,Geboortedatum,AbonneeID) VALUES
('Frank','1968-01-25','1215426'),
('Madelief','2001-08-19','1215426');

/* J. van Betlehem <- BB Sample*/
INSERT INTO Abonnee (AbonneeID,Email,Wachtwoord,Naam,Straat,Huisnummer,Postcode,Woonplaats) 
VALUES ('5602533','VanBetlehem@gmail.com','goodlordjesus123xx','J. van Betlehem','Nietzschestraat','99','8542 BE','Breda');

INSERT INTO Profiel (Profielnaam,Geboortedatum,AbonneeID) VALUES 
('Petrus','1999-06-26','5602533'),
('Paulus','1999-06-26','5602533');

/* F. de Kat <- BB Sample*/
INSERT INTO Abonnee (AbonneeID,Email,Wachtwoord,Naam,Straat,Huisnummer,Postcode,Woonplaats) 
VALUES ('5285824','shadowslayer123killzone@outlook.com','trump4prez','F. de Kat','Kantlaan','11','8542 CD','Breda');

INSERT INTO Profiel (Profielnaam,Geboortedatum,AbonneeID) VALUES
('Fritz','1968-08-19','5285824'),
('Diana','1988-12-25','5285824');

/* T. Smits */
INSERT INTO Abonnee (AbonneeID,Email,Wachtwoord,Naam,Straat,Huisnummer,Postcode,Woonplaats) 
VALUES ('5281337','mastercode@outlook.com','cryptoAuti1993xx','T. Smits','Codelaan','15','1337 AA','Breda');

INSERT INTO Profiel (Profielnaam,Geboortedatum,AbonneeID) VALUES
('Tom','1993-05-11','5281337');

/* Fam. Huizenbregts */
INSERT INTO Abonnee (AbonneeID,Email,Wachtwoord,Naam,Straat,Huisnummer,Postcode,Woonplaats) 
VALUES ('5281338','huizenbregts@outlook.com','a7483j39r0','Fam. Huizenbregts','Stadtlaan','99','2314 BB','Breda');

INSERT INTO Profiel (Profielnaam,Geboortedatum,AbonneeID) VALUES
('Gustian','1977-04-02','5281338');

/* Fam. Zjozczyk */
INSERT INTO Abonnee (AbonneeID,Email,Wachtwoord,Naam,Straat,Huisnummer,Postcode,Woonplaats) 
VALUES ('5281339','zjozczyk@czechmail.cz','nozdrovja','Fam. Zjozczyk','Stadtlaan','92','2314 BB','Breda');

INSERT INTO Profiel (Profielnaam,Geboortedatum,AbonneeID) VALUES
('Zcykzy','1978-05-01','5281339');

/* DJ Henry */
INSERT INTO Abonnee (AbonneeID,Email,Wachtwoord,Naam,Straat,Huisnummer,Postcode,Woonplaats) 
VALUES ('5281341','djmasters1919@gmail.com','tiestosux12','DJ Henry','Discostraat','112','2424 CC','Breda');

INSERT INTO Profiel (Profielnaam,Geboortedatum,AbonneeID) VALUES
('DJ Henry','1990-05-01','5281341');



/* Content */

/* Programma's (hoofdzakelijk gebruikt voor BekekenProgramma's, ID's gerelateerd aan voorbeelddata op BB) */
INSERT INTO Programma (ProgrammaID,Titel) VALUES 
('1010', 'The Abominable Bride'),
('8001', 'The Life of Brian'),
('8002', 'Pulp Fiction'),
('8004', 'Pruimebloesem'),
('8008', 'Reservoir Dogs'),
('8010', 'The Good, the Bad and the Ugly'),
('8011', 'Andy Warhols Dracula'),
('8012', 'Ober'),
('8014', 'Der Untergang'),
('8016', 'De helaasheid der dingen'),
('8017', 'A Clockwork Orange'),
('1001', 'A Study in Pink'),
('1002', 'The Blind Banker'),
('1003', 'The Great Game'),
('1004', 'A Scandal in Belgravia'),
('1005', 'The Hounds of Baskerville'),
('1006', 'The Reichenbach Fall'),
('1007', 'The Empty Hearse'),
('1008', 'The Sign of Three'),
('1009', 'His Last Vow'),
('2000', 'Pilot'),
('2001', 'Cat''s in the Bag...'),
('2002', '...And the Bag''s in the River'),
('2003', 'Cancer Man'),
('2004', 'Gray Matter'),
('2005', 'Crazy Handful of Nothin'''),
('2006', 'A No-Rough-Stuff-Type Deal'),
('2007', 'Seven Thirty-Seven'),
('2008', 'Grilled'),
('2009', 'Bit by a Dead Bee'),
('2010', 'Down'),
('2011', 'Breakage'),
('2012', 'Peekaboo'),
('2013', 'Negro Y Azul'),
('2014', 'Better Call Saul'),
('2015', '4 Days Out'),
('2016', 'Over'),
('2017', 'Mandala'),
('2018', 'Phoenix'),
('2019', 'ABQ'),
('3001', 'The Crocodile''s Dilemma'),
('3002', 'The Rooster Prince'),
('3003', 'A Muddy Road'),
('3004', 'Eating the Blame'),
('3005', 'The Six Ungraspables'),
('3006', 'Buridan''s Ass'),
('3007', 'Who Shaves the Barber?'),
('3008', 'The Heap'),
('3009', 'A Fox, a Rabbit, and a Cabbage'),
('3010', 'Morton''s Fork'),
('3101', 'Waiting for Dutch'),
('3102', 'Before the Law'),
('3103', 'The Myth of Sisyphus'),
('3104', 'The Gift of the Magi'),
('3105', 'Fear and Trembling'),
('3106', 'Rhinoceros'),
('3107', 'Did you do this? No, you did it!'),
('3108', 'Loplop'),
('3109', 'The Castle'),
('3110', 'Palindrome');

/* Series */
INSERT INTO Serie (SerieNaam, Seizoenen, GeschikteLeeftijd, Taal, Genre, LijktOp) VALUES 
('Sherlock', '1', '12', 'Engels', 'Detective', 'Fargo'),
('Breaking Bad', '2', '16', 'Engels-Amerikaans', 'Spanning', 'Fargo'),
('Fargo', '1', '16', 'Engels-Amerikaans', 'Spanning', 'Breaking Bad');


/* Afleveringen */
INSERT INTO Aflevering (AfleveringID, Serie, ProgrammaID, SeizoenEnAflevering, Titel, Tijdsduur) VALUES 
('00001', 'Sherlock', '1001', 'S01E01', 'A Study in Pink', '01:28:00'),
('00002', 'Sherlock', '1002', 'S01E02', 'The Blind Banker', '01:28:00'),
('00003', 'Sherlock', '1003', 'S01E03', 'The Great Game', '01:28:00'),
('00004', 'Sherlock', '1004', 'S01E04', 'A Scandal in Belgravia', '01:28:00'),
('00005', 'Sherlock', '1005', 'S01E05', 'The Hounds of Baskerville', '01:28:00'),
('00006', 'Sherlock', '1006', 'S01E06', 'The Reichenbach Fall', '01:28:00'),
('00007', 'Sherlock', '1007', 'S01E07', 'The Empty Hearse', '01:28:00'),
('00008', 'Sherlock', '1008', 'S01E08', 'The Sign of Three', '01:28:00'),
('00009', 'Sherlock', '1009', 'S01E09', 'His Last Vow', '01:28:00'),

('01001', 'Breaking Bad', '2000', 'S01E01', 'Pilot', '00:58:00'),
('01002', 'Breaking Bad', '2001', 'S01E02', 'Cat''s in the Bag...', '00:48:00'),
('01003', 'Breaking Bad', '2002', 'S01E03', '...And the Bag''s in the River', '00:48:00'),
('01004', 'Breaking Bad', '2003', 'S01E04', 'Cancer Man', '00:48:00'),
('01005', 'Breaking Bad', '2004', 'S01E05', 'Gray Matter', '00:48:00'),
('01006', 'Breaking Bad', '2005', 'S01E06', 'Crazy Handful of Nothin''', '00:48:00'),
('01007', 'Breaking Bad', '2006', 'S01E07', 'A No-Rough-Stuff-Type Deal', '00:48:00'),
('01008', 'Breaking Bad', '2007', 'S02E01', 'Seven Thirty-Seven', '00:48:00'),
('01009', 'Breaking Bad', '2008', 'S02E02', 'Grilled', '00:48:00'),
('01010', 'Breaking Bad', '2009', 'S02E03', 'Bit by a Dead Bee', '00:48:00'),
('01011', 'Breaking Bad', '2010', 'S02E04', 'Down', '00:48:00'),
('01012', 'Breaking Bad', '2011', 'S02E05', 'Breakage', '00:48:00'),
('01013', 'Breaking Bad', '2012', 'S02E06', 'Peekaboo', '00:48:00'),
('01014', 'Breaking Bad', '2013', 'S02E07', 'Negro Y Azul', '00:48:00'),
('01015', 'Breaking Bad', '2014', 'S02E08', 'Better Call Saul', '00:48:00'),
('01016', 'Breaking Bad', '2015', 'S02E09', '4 Days Out', '00:48:00'),
('01017', 'Breaking Bad', '2016', 'S02E10', 'Over', '00:48:00'),
('01018', 'Breaking Bad', '2017', 'S02E11', 'Mandala', '00:48:00'),
('01019', 'Breaking Bad', '2018', 'S02E12', 'Phoenix', '00:48:00'),
('01020', 'Breaking Bad', '2019', 'S02E13', 'ABQ', '00:48:00'),

('02001', 'Fargo', '3001', 'S01E01', 'The Crocodile''s Dilemma', '01:08:00'),
('02002', 'Fargo', '3002', 'S01E02', 'The Rooster Prince', '01:08:00'),
('02003', 'Fargo', '3003', 'S01E03', 'A Muddy Road', '01:08:00'),
('02004', 'Fargo', '3004', 'S01E04', 'Eating the Blame', '01:08:00'),
('02005', 'Fargo', '3005', 'S01E05', 'The Six Ungraspables', '01:08:00'),
('02006', 'Fargo', '3006', 'S01E06', 'Buridan''s Ass', '01:08:00'),
('02007', 'Fargo', '3007', 'S01E07', 'Who Shaves the Barber?', '01:08:00'),
('02008', 'Fargo', '3008', 'S01E08', 'The Heap', '01:08:00'),
('02009', 'Fargo', '3009', 'S01E09', 'A Fox, a Rabbit, and a Cabbage', '01:08:00'),
('02010', 'Fargo', '3010', 'S01E10', 'Morton''s Fork', '01:08:00'),
('02011', 'Fargo', '3101', 'S02E01', 'Waiting for Dutch', '01:08:00'),
('02012', 'Fargo', '3102', 'S02E02', 'Before the Law', '01:08:00'),
('02013', 'Fargo', '3103', 'S02E03', 'The Myth of Sisyphus', '01:08:00'),
('02014', 'Fargo', '3104', 'S02E04', 'The Gift of the Magi', '01:08:00'),
('02015', 'Fargo', '3105', 'S02E05', 'Fear and Trembling', '01:08:00'),
('02016', 'Fargo', '3106', 'S02E06', 'Rhinoceros', '01:08:00'),
('02017', 'Fargo', '3107', 'S02E07', 'Did you do this? No, you did it!', '01:08:00'),
('02018', 'Fargo', '3108', 'S02E08', 'Loplop', '01:08:00'),
('02019', 'Fargo', '3109', 'S02E09', 'The Castle', '01:08:00'),
('02020', 'Fargo', '3110', 'S02E10', 'Palindrome', '01:08:00');



/* Films */
INSERT INTO Film (FilmID, Titel, ProgrammaID, GeschikteLeeftijd, Taal, Tijdsduur, Genre) VALUES
('00001', 'The Abominable Bride', '1010', '12', 'Engels', '01:29:00', 'Detective'),
('00002', 'The Life of Brian', '8001', '12', 'Engels', '01:34:00', 'Humor'),
('00003', 'Pulp Fiction', '8002', '16', 'Engels-Amerikaans', '02:34:00', 'Misdaad'),
('00004', 'Pruimebloesem', '8004', '18', 'Nederlands', '01:20:00', 'Erotiek'),
('00005', 'Reservoir Dogs', '8008', '16', 'Engels-Amerikaans', '01:39:00', 'Misdaad'),
('00006', 'The Good, the Bad and the Ugly', '8010', '12', 'Engels-Amerikaans', '02:41:00', 'Western'),
('00007', 'Andy Warhols Dracula', '8011', '16', 'Engels-Amerikaans', '01:41:00', 'Humor'),
('00008', 'Ober', '8012', '6', 'Nederlands', '01:37:00', 'Humor'),
('00009', 'Der Untergang', '8014', '16', 'Duits', '02:58:00', 'Oorlog'),
('00010', 'De helaasheid der dingen', '8016', '12', 'Vlaams', '01:48:00', 'Humor'),
('00011', 'A Clockwork Orange', '8017', '16', 'Engels', '02:16:00', 'Sci-Fi');


/* Bekeken */

INSERT INTO BekekenProgramma (ProgrammaID,Titel,Profielnaam,AbonneeID,Percentage,LaatstBekeken) VALUES
('1001','A Study in Pink', 'Frank', '1215426', '100','20170512 11:15:00'),
('1002','The Blind Banker',  'Frank', '1215426', '100','20160315 15:11:00'),
('1003','The Great Game', 'Frank', '1215426', '78','20170501 09:16:00'),

('1001','A Study in Pink', 'Madelief', '1215426', '100','20180112 13:15:00'),
('1002','The Blind Banker', 'Madelief', '1215426', '60','20170113 19:15:00'),
('3001','The Crocodile''s Dilemma', 'Madelief', '1215426', '91','20170322 15:15:00'),
('2001','Cat''s in the Bag...', 'Madelief', '1215426', '100','20170201 11:15:00'),
('2002','...And the Bag''s in the River', 'Madelief', '1215426', '100','20170808 19:15:00'),
('2003','Cancer Man', 'Madelief', '1215426', '100','20170809 20:15:00'),
('2004','Gray Matter', 'Madelief', '1215426', '22','20170810 23:15:00'),

('3001','The Crocodile''s Dilemma', 'Petrus', '5602533','100','20170501 16:15:00'),
('3002','The Rooster Prince',  'Petrus', '5602533','100','20170502 16:15:00'),
('3010','Morton''s Fork', 'Petrus', '5602533','60','20170503 16:15:00'),
('8001','The Life of Brian',  'Petrus', '5602533','100','20170504 16:15:00'),
('8002','Pulp Fiction',  'Petrus', '5602533','99','20170505 16:15:00'),

('3001','The Crocodile''s Dilemma', 'Paulus','5602533', '100','20170611 08:15:00'),
('3002','The Rooster Prince', 'Paulus','5602533', '74','20170712 07:15:00'),
('3010','Morton''s Fork', 'Paulus','5602533', '60','20170713 07:15:00'),
('8001','The Life of Brian', 'Paulus','5602533', '100','20170714 08:15:00'),
('2019','ABQ', 'Paulus','5602533', '10','20170715 07:15:00'),

('1001','A Study in Pink', 'Fritz','5285824', '100','20170110 22:00:00'),
('1002','The Blind Banker', 'Fritz','5285824', '100','20170110 22:00:00'),
('1010','The Abominable Bride', 'Fritz','5285824', '5','20170110 22:00:00'),

('8002','Pulp Fiction',  'Diana','5285824', '100','20170911 23:15:00'),
('1001','A Study in Pink', 'Diana','5285824', '45','20170912 22:15:00'),

('1010', 'The Abominable Bride','Tom','5281337','99','20170101 11:15:00'),
('8001', 'The Life of Brian','Tom','5281337','78','20170102 11:15:00'),
('8002', 'Pulp Fiction','Tom','5281337','22','20170103 11:15:00'),
('8004', 'Pruimebloesem','Tom','5281337','55','20170104 11:15:00'),
('8008', 'Reservoir Dogs','Tom','5281337','23','20170105 11:15:00'),
('8010', 'The Good, the Bad and the Ugly','Tom','5281337','60','20170106 11:15:00'),
('8011', 'Andy Warhols Dracula','Tom','5281337','100','20170107 11:15:00'),
('8012', 'Ober','Tom','5281337','100','20170108 11:15:00'),
('8014', 'Der Untergang','Tom','5281337','97','20170109 11:15:00'),
('8016', 'De helaasheid der dingen','Tom','5281337','98','20170110 11:15:00'),
('8017', 'A Clockwork Orange','Tom','5281337','96','20170111 11:15:00'),
('1001', 'A Study in Pink','Tom','5281337','98','20170112 11:15:00'),
('1002', 'The Blind Banker','Tom','5281337','98','20170113 11:15:00'),
('1003', 'The Great Game','Tom','5281337','12','20170114 11:15:00'),
('1004', 'A Scandal in Belgravia','Tom','5281337','24','20170115 11:15:00'),
('1005', 'The Hounds of Baskerville','Tom','5281337','1','20170116 11:15:00'),
('1006', 'The Reichenbach Fall','Tom','5281337','21','20170117 11:15:00'),
('1007', 'The Empty Hearse','Tom','5281337','42','20170118 11:15:00'),
('1008', 'The Sign of Three','Tom','5281337','25','20170119 11:15:00'),
('1009', 'His Last Vow','Tom','5281337','99','20170120 11:15:00'),
('2000', 'Pilot','Tom','5281337','99','20170121 11:15:00'),
('2001', 'Cat''s in the Bag...','Tom','5281337','99','20170122 11:15:00'),
('2002', '...And the Bag''s in the River','Tom','5281337','100','20170123 11:15:00'),
('2003', 'Cancer Man','Tom','5281337','100','20170124 11:15:00'),
('2004', 'Gray Matter','Tom','5281337','100','20170125 11:15:00'),
('2005', 'Crazy Handful of Nothin''','Tom','5281337','67','20170126 11:15:00'),
('2006', 'A No-Rough-Stuff-Type Deal','Tom','5281337','44','20170127 11:15:00'),
('2007', 'Seven Thirty-Seven','Tom','5281337','100','20170128 11:15:00'),
('2008', 'Grilled','Tom','5281337','21','20170129 11:15:00'),
('2009', 'Bit by a Dead Bee','Tom','5281337','17','20170130 11:15:00'),
('2010', 'Down','Tom','5281337','89','20170131 11:15:00'),
('2011', 'Breakage','Tom','5281337','99','20170201 11:15:00'),
('2012', 'Peekaboo','Tom','5281337','99','20170202 11:15:00'),
('2013', 'Negro Y Azul','Tom','5281337','99','20170203 11:15:00'),
('2014', 'Better Call Saul','Tom','5281337','21','20170204 11:15:00'),
('2015', '4 Days Out','Tom','5281337','44','20170205 11:15:00'),
('2016', 'Over','Tom','5281337','12','20170206 11:15:00'),
('2017', 'Mandala','Tom','5281337','1','20170207 11:15:00'),
('2018', 'Phoenix','Tom','5281337','12','20170208 11:15:00'),
('2019', 'ABQ','Tom','5281337','13','20170209 11:15:00'),
('3001', 'The Crocodile''s Dilemma','Tom','5281337','99','20170210 11:15:00'),
('3002', 'The Rooster Prince','Tom','5281337','98','20170211 11:15:00'),
('3003', 'A Muddy Road','Tom','5281337','99','20170212 11:15:00'),
('3004', 'Eating the Blame','Tom','5281337','98','20170213 11:15:00'),
('3005', 'The Six Ungraspables','Tom','5281337','13','20170214 11:15:00'),
('3006', 'Buridan''s Ass','Tom','5281337','25','20170215 11:15:00'),
('3007', 'Who Shaves the Barber?','Tom','5281337','11','20170216 11:15:00'),
('3008', 'The Heap','Tom','5281337','87','20170217 11:15:00'),
('3009', 'A Fox, a Rabbit, and a Cabbage','Tom','5281337','90','20170218 11:15:00'),
('3010', 'Morton''s Fork','Tom','5281337','91','20170219 11:15:00'),
('3101', 'Waiting for Dutch','Tom','5281337','100','20170220 11:15:00'),
('3102', 'Before the Law','Tom','5281337','100','20170221 11:15:00'),
('3103', 'The Myth of Sisyphus','Tom','5281337','40','20170222 11:15:00'),
('3104', 'The Gift of the Magi','Tom','5281337','41','20170223 11:15:00'),
('3105', 'Fear and Trembling','Tom','5281337','100','20170224 11:15:00'),
('3106', 'Rhinoceros','Tom','5281337','100','20170225 11:15:00'),
('3107', 'Did you do this? No, you did it!','Tom','5281337','100','20170226 11:15:00'),
('3108', 'Loplop','Tom','5281337','100','20170227 11:15:00'),
('3109', 'The Castle','Tom','5281337','88','20170228 11:15:00'),
('3110', 'Palindrome','Tom','5281337','31','20170301 11:15:00'),

('1001', 'A Study in Pink','Gustian','5281338','11','20160112 11:15:00'),
('1002', 'The Blind Banker','Gustian','5281338','22','20160113 11:15:00'),
('1003', 'The Great Game','Gustian','5281338','32','20160114 11:15:00'),
('1004', 'A Scandal in Belgravia','Gustian','5281338','55','20160115 11:15:00'),
('1005', 'The Hounds of Baskerville','Gustian','5281338','55','20160116 11:15:00'),
('1006', 'The Reichenbach Fall','Gustian','5281338','45','20160117 11:15:00'),
('1007', 'The Empty Hearse','Gustian','5281338','47','20160118 11:15:00'),
('1008', 'The Sign of Three','Gustian','5281338','28','20160119 11:15:00'),
('1009', 'His Last Vow','Gustian','5281338','93','20160120 11:15:00'),
('2000', 'Pilot','Gustian','5281338','99','20160121 11:15:00'),
('2001', 'Cat''s in the Bag...','Gustian','5281338','99','20160122 11:15:00'),
('2002', '...And the Bag''s in the River','Gustian','5281338','100','20160123 11:15:00'),
('2003', 'Cancer Man','Gustian','5281338','100','20160124 11:15:00'),
('2004', 'Gray Matter','Gustian','5281338','100','20160125 11:15:00'),
('2005', 'Crazy Handful of Nothin''','Gustian','5281338','67','20160126 11:15:00'),
('2006', 'A No-Rough-Stuff-Type Deal','Gustian','5281338','44','20160127 11:15:00'),
('2007', 'Seven Thirty-Seven','Gustian','5281338','100','20160128 11:15:00'),
('2008', 'Grilled','Gustian','5281338','21','20160129 11:15:00'),
('2009', 'Bit by a Dead Bee','Gustian','5281338','17','20160130 11:15:00'),
('2010', 'Down','Gustian','5281338','89','20160130 11:15:00'),
('2011', 'Breakage','Gustian','5281338','99','20160201 11:15:00'),
('2012', 'Peekaboo','Gustian','5281338','99','20160202 11:15:00'),
('2013', 'Negro Y Azul','Gustian','5281338','99','20160203 11:15:00'),
('2014', 'Better Call Saul','Gustian','5281338','21','20160204 11:15:00'),
('2015', '4 Days Out','Gustian','5281338','44','20160205 11:15:00'),
('2016', 'Over','Gustian','5281338','12','20160206 11:15:00'),
('2017', 'Mandala','Gustian','5281338','10','20160207 11:15:00'),
('2018', 'Phoenix','Gustian','5281338','14','20160208 11:15:00'),
('2019', 'ABQ','Gustian','5281338','22','20160209 11:15:00'),
('3001', 'The Crocodile''s Dilemma','Gustian','5281338','99','20160210 11:15:00'),
('3002', 'The Rooster Prince','Gustian','5281338','98','20160211 11:15:00'),
('3003', 'A Muddy Road','Gustian','5281338','99','20160212 11:15:00'),
('3004', 'Eating the Blame','Gustian','5281338','62','20160213 11:15:00'),
('3005', 'The Six Ungraspables','Gustian','5281338','13','20160214 11:15:00'),
('3006', 'Buridan''s Ass','Gustian','5281338','25','20160215 11:15:00'),
('3007', 'Who Shaves the Barber?','Gustian','5281338','11','20160216 11:15:00'),
('3008', 'The Heap','Gustian','5281338','87','20160217 11:15:00'),
('3009', 'A Fox, a Rabbit, and a Cabbage','Gustian','5281338','90','20160218 11:15:00'),
('3010', 'Morton''s Fork','Gustian','5281338','91','20160219 11:15:00'),
('3101', 'Waiting for Dutch','Gustian','5281338','100','20160220 11:15:00'),
('3102', 'Before the Law','Gustian','5281338','100','20160221 11:15:00'),
('3103', 'The Myth of Sisyphus','Gustian','5281338','40','20160222 11:15:00'),
('3104', 'The Gift of the Magi','Gustian','5281338','41','20160223 11:15:00'),
('3105', 'Fear and Trembling','Gustian','5281338','100','20160224 11:15:00'),
('3106', 'Rhinoceros','Gustian','5281338','100','20160225 11:15:00'),
('3107', 'Did you do this? No, you did it!','Gustian','5281338','100','20160226 11:15:00'),
('3108', 'Loplop','Gustian','5281338','100','20160227 11:15:00'),
('3109', 'The Castle','Gustian','5281338','88','20160228 11:15:00'),
('3110', 'Palindrome','Gustian','5281338','31','20160301 11:15:00'),

('8008', 'Reservoir Dogs','Zcykzy','5281339','100','20160302 11:15:00'),
('8010', 'The Good, the Bad and the Ugly','Zcykzy','5281339','100','20160323 11:15:00'),
('8011', 'Andy Warhols Dracula','Zcykzy','5281339','100','20160322 11:15:00'),
('8012', 'Ober','Zcykzy','5281339','100','20160305 11:15:00'),
('8014', 'Der Untergang','Zcykzy','5281339','100','20160306 11:15:00'),

('8008', 'Reservoir Dogs','DJ Henry','5281341','95','20180501 11:15:00'),
('8010', 'The Good, the Bad and the Ugly','DJ Henry','5281341','98','20180501 11:15:00'),
('8011', 'Andy Warhols Dracula','DJ Henry','5281341','98','20180501 11:15:00');