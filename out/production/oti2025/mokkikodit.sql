DROP DATABASE IF EXISTS mokkikodit;
CREATE DATABASE mokkikodit CHARACTER SET utf8mb4;
USE mokkikodit;

CREATE TABLE OSOITE (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Katuosoite VARCHAR(255) NOT NULL,
    Kaupunki VARCHAR(255) NOT NULL,
    Maa VARCHAR(255) NOT NULL,
    Postinumero VARCHAR(10) NOT NULL
);

CREATE TABLE HENKILOKUNTA (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Kayttajatunnus VARCHAR(255) NOT NULL,
    Salasana VARCHAR(255) NOT NULL,
    Kayttoikeus VARCHAR(255) NOT NULL
);

CREATE TABLE ASIAKAS (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Osoite_ID INT,
    Nimi VARCHAR(255) NOT NULL,
    Sahkoposti VARCHAR(255) NOT NULL,
    Puhelinnumero VARCHAR(20),
    FOREIGN KEY (Osoite_ID) REFERENCES OSOITE(ID)
);

CREATE TABLE MOKKI (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Osoite_id INT NOT NULL,
    Vuokrahinta FLOAT NOT NULL,
    Nimi VARCHAR(255) NOT NULL,
    Tila ENUM('Saatavissa', 'Varattu') NOT NULL,
    FOREIGN KEY (Osoite_id) REFERENCES OSOITE(ID)
);

CREATE TABLE LASKU (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Viitenumero VARCHAR(255) NOT NULL,
    Erapaiva DATE NOT NULL,
    Maksaja VARCHAR(255) NOT NULL,
    Saaja VARCHAR(255) NOT NULL,
    Y_tunnus VARCHAR(255),
    ALV_prosentti DECIMAL(5,2) NOT NULL,
    Maara DECIMAL(10,2) NOT NULL
);

CREATE TABLE VARAUS (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Lasku_ID INT NOT NULL,
    Mokki_ID INT NOT NULL,
    Asiakas_ID INT NOT NULL,
    Aloituspaivamaara DATE NOT NULL,
    Paattymispaivamaara DATE NOT NULL,
    Henkilomaara INT NOT NULL,
    FOREIGN KEY (Lasku_ID) REFERENCES LASKU(ID),
    FOREIGN KEY (Mokki_ID) REFERENCES MOKKI(ID),
    FOREIGN KEY (Asiakas_ID) REFERENCES ASIAKAS(ID)
);

INSERT INTO OSOITE (ID, Katuosoite, Kaupunki, Maa, Postinumero) VALUES
(1, 'Järvitie 12', 'Kuopio', 'Suomi', '70100'),
(2, 'Metsäkatu 45', 'Kuopio', 'Suomi', '70100'),
(3, 'Rantatie 78', 'Kuopio', 'Suomi', '70100'),
(4, 'Kivimäentie 23', 'Kuopio', 'Suomi', '70100'),
(5, 'Aurinkotie 56', 'Kuopio', 'Suomi', '70100'),
(6, 'Kuusitie 89', 'Kuopio', 'Suomi', '70100'),
(7, 'Linnankatu 34', 'Kuopio', 'Suomi', '70100'),
(8, 'Hiekkaranta 67', 'Kuopio', 'Suomi', '70100'),
(9, 'Kalliotie 90', 'Kuopio', 'Suomi', '70100'),
(10, 'Niittykatu 11', 'Kuopio', 'Suomi', '70100'),
(11, 'Kalevankatu 25', 'Helsinki', 'Suomi', '00100'),
(12, 'Satamakatu 8', 'Turku', 'Suomi', '20100'),
(13, 'Kauppakatu 14', 'Tampere', 'Suomi', '33100'),
(14, 'Rantakatu 33', 'Oulu', 'Suomi', '90100'),
(15, 'Kirkkokatu 7', 'Jyväskylä', 'Suomi', '40100'),
(16, 'Mannerheimintie 56', 'Lahti', 'Suomi', '15100'),
(17, 'Väinönkatu 12', 'Joensuu', 'Suomi', '80100'),
(18, 'Pohjoisranta 4', 'Pori', 'Suomi', '28100'),
(19, 'Asemakatu 22', 'Rovaniemi', 'Suomi', '96100'),
(20, 'Kaptensgatan 19', 'Vaasa', 'Suomi', '65100');

INSERT INTO HENKILOKUNTA (Kayttajatunnus, Salasana, Kayttoikeus) VALUES
('admin', 'admin', 'Admin'),
('mokkikodit_001', 'sauna2025', 'Staff'),
('mokkikodit_002', 'salasana123', 'Staff'),
('mokkikodit_003', 'kuopiorules4567', 'Staff'),
('mokkikodit_004', 'torille95', 'Staff'),
('mokkikodit_005', 'avain987', 'Staff'),
('mokkikodit_006', 'maanantaifani1', 'Staff'),
('mokkikodit_007', 'espanja321', 'Staff'),
('mokkikodit_008', 'oti25', 'Staff'),
('mokkikodit_009', 'hyvasuomi1', 'Staff');

INSERT INTO ASIAKAS (ID, Osoite_ID, Nimi, Sahkoposti, Puhelinnumero) VALUES
(1, 11, 'Ilmari Ilveskallio', 'ilveskallio@gmail.com', '+358465551234'),
(2, 12, 'Aino Aaltojärvi', 'a.aaltojarvi@hotmail.com', '+358409876543'),
(3, 13, 'Onni Oravakangas', 'onni.o@lumipuu.fi', '+358412348765'),
(4, 14, 'Helmi Haukijärvi', 'helmi_h@gmail.com', '+358503216549'),
(5, 15, 'Elias Esikko', 'elias.e@sinervuo.fi', '+358441122334'),
(6, 16, 'Linnea Lehtola', 'linnea78@hotmail.com', '+358456677889'),
(7, 17, 'Valto Vuorinen', 'valto.v@kotikallio.fi', '+358429988776'),
(8, 18, 'Saimi Salmela', 'saimi_s@gmail.com', '+358474455667'),
(9, 19, 'Taavi Tunturila', 'taavi.t@revonlampi.fi', '+358407788990'),
(10, 20, 'Lempi Laaksonen', 'lempi.l@hotmail.com', '+358432233445');

INSERT INTO MOKKI (Osoite_id, Vuokrahinta, Nimi, Tila) VALUES
(1, 120.00, 'Järvenrantamökki', 'Saatavissa'),
(2, 150.00, 'Metsäpirtti', 'Varattu'),
(3, 100.00, 'Rantatupa', 'Saatavissa'),
(4, 180.00, 'Kivimökki', 'Saatavissa'),
(5, 130.00, 'Aurinkolahti', 'Varattu'),
(6, 160.00, 'Kuusikoto', 'Saatavissa'),
(7, 110.00, 'Linnamökki', 'Saatavissa'),
(8, 140.00, 'Hiekkaranta', 'Varattu'),
(9, 170.00, 'Kalliotupa', 'Saatavissa'),
(10, 125.00, 'Niittykoti', 'Saatavissa');

INSERT INTO LASKU (Viitenumero, Erapaiva, Maksaja, Saaja, Y_tunnus, ALV_prosentti, Maara) VALUES
('MK20250601001', '2025-06-08', 'Ilmari Ilveskallio', 'Mokkikodit Oy', NULL, 25.5, 840.00),
('MK20250610002', '2025-06-17', 'Aino Aaltojärvi', 'Mokkikodit Oy', NULL, 25.5, 750.00),
('MK20250620003', '2025-06-27', 'Onni Oravakangas', 'Mokkikodit Oy', '1234567-8', 25.5, 500.00),
('MK20250701004', '2025-07-08', 'Helmi Haukijärvi', 'Mokkikodit Oy', NULL, 25.5, 1260.00),
('MK20250710005', '2025-07-17', 'Elias Esikko', 'Mokkikodit Oy', '9876543-2', 25.5, 650.00),
('MK20250720006', '2025-07-27', 'Linnea Lehtola', 'Mokkikodit Oy', NULL, 25.5, 800.00),
('MK20250801007', '2025-08-08', 'Valto Vuorinen', 'Mokkikodit Oy', '5432109-6', 25.5, 770.00),
('MK20250810008', '2025-08-17', 'Saimi Salmela', 'Mokkikodit Oy', NULL, 25.5, 700.00),
('MK20250820009', '2025-08-27', 'Taavi Tunturila', 'Mokkikodit Oy', '6789012-3', 25.5, 850.00),
('MK20250901010', '2025-09-08', 'Lempi Laaksonen', 'Mokkikodit Oy', NULL, 24.0, 875.00);

INSERT INTO VARAUS (Lasku_ID, Mokki_ID, Asiakas_ID,Henkilomaara,Aloituspaivamaara, Paattymispaivamaara) VALUES
(1, 1, 1, 4,'2025-06-01', '2025-06-07'),
(2, 2, 2,1, '2025-06-10', '2025-06-15'),
(3, 3, 3,2, '2025-06-20', '2025-06-25'),
(4, 4, 4,1, '2025-07-01', '2025-07-08'),
(5, 5, 5,4, '2025-07-10', '2025-07-15'),
(6, 6, 6,2, '2025-07-20', '2025-07-25'),
(7, 7, 7,3, '2025-08-01', '2025-08-07'),
(8, 8, 8,1, '2025-08-10', '2025-08-15'),
(9, 9, 9,5, '2025-08-20', '2025-08-25'),
(10, 10, 10,2, '2025-09-01', '2025-09-07');
