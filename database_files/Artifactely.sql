-- Selezione del database
CREATE DATABASE IF NOT EXISTS Artifactely;
USE Artifactely;

-- Disabilita temporaneamente i controlli delle chiavi esterne
SET FOREIGN_KEY_CHECKS = 0;

-- Elimina le tabelle se esistono, nell'ordine corretto
DROP TABLE IF EXISTS OrdineItem;
DROP TABLE IF EXISTS CarrelloItem;
DROP TABLE IF EXISTS Immagine_Prodotto;
DROP TABLE IF EXISTS Ordine;
DROP TABLE IF EXISTS Carrello;
DROP TABLE IF EXISTS Prodotto;
DROP TABLE IF EXISTS Utente;

-- Riabilita i controlli delle chiavi esterne
SET FOREIGN_KEY_CHECKS = 1;

-- Creazione della tabella Utente
CREATE TABLE Utente (
                        email VARCHAR(50) PRIMARY KEY NOT NULL,
                        username VARCHAR(50) NOT NULL,
                        password VARCHAR(200) NOT NULL,
                        nome VARCHAR(50) NOT NULL,
                        cognome VARCHAR(50) NOT NULL,
                        amministratore TINYINT(1) NOT NULL DEFAULT 0
);

-- Creazione della tabella Prodotto
CREATE TABLE Prodotto (
                          id_prodotto INT PRIMARY KEY AUTO_INCREMENT,
                          nome VARCHAR(100) NOT NULL,
                          descrizione TEXT NOT NULL,
                          prezzo DECIMAL(10,2) NOT NULL,
                          valido TINYINT(1) NOT NULL DEFAULT 1,
                          in_evidenza TINYINT(1) NOT NULL DEFAULT 0,
                          data_creazione DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Creazione della tabella Immagine_Prodotto
CREATE TABLE Immagine_Prodotto (
                                   id_immagine INT NOT NULL AUTO_INCREMENT,
                                   id_prodotto INT NOT NULL,
                                   immagine VARCHAR(255) NOT NULL,
                                   PRIMARY KEY (id_immagine),
                                   FOREIGN KEY (id_prodotto) REFERENCES Prodotto(id_prodotto) ON DELETE CASCADE
);

-- Creazione della tabella Carrello
CREATE TABLE Carrello (
                          id_carrello INT PRIMARY KEY AUTO_INCREMENT,
                          email VARCHAR(50) NOT NULL,
                          data_creazione DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (email) REFERENCES Utente(email) ON DELETE CASCADE
);

-- Creazione della tabella CarrelloItem
CREATE TABLE CarrelloItem (
                              id_carrello INT NOT NULL,
                              id_prodotto INT NOT NULL,
                              quantita INT NOT NULL,
                              PRIMARY KEY (id_carrello, id_prodotto),
                              FOREIGN KEY (id_carrello) REFERENCES Carrello(id_carrello) ON DELETE CASCADE,
                              FOREIGN KEY (id_prodotto) REFERENCES Prodotto(id_prodotto)
);

-- Creazione della tabella Ordine
CREATE TABLE Ordine (
                        id_ordine INT PRIMARY KEY AUTO_INCREMENT,
                        email VARCHAR(50) NOT NULL,
                        data_ordine DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        totale DECIMAL(10,2) NOT NULL,
                        stato VARCHAR(50) NOT NULL,
                        indirizzo_spedizione VARCHAR(255) NOT NULL,
                        metodo_pagamento VARCHAR(50) NOT NULL,
                        ultime_quattro_cifre VARCHAR(4) NULL,
                        FOREIGN KEY (email) REFERENCES Utente(email) ON DELETE CASCADE
);

-- Creazione della tabella OrdineItem
CREATE TABLE OrdineItem (
                            id_ordine INT NOT NULL,
                            id_prodotto INT NOT NULL,
                            quantita INT NOT NULL,
                            prezzo_unitario DECIMAL(10,2) NOT NULL,
                            PRIMARY KEY (id_ordine, id_prodotto),
                            FOREIGN KEY (id_ordine) REFERENCES Ordine(id_ordine) ON DELETE CASCADE,
                            FOREIGN KEY (id_prodotto) REFERENCES Prodotto(id_prodotto)
);

-- Inserimento di un utente amministratore di default
INSERT INTO Utente (email, username, password, nome, cognome, amministratore)
VALUES ('admin@example.com', 'admin', '$2a$12$jL.nXXqz6OLLg8JfdTojveGUsWWvZFBVpSWddeQ3Y88ssjW2nr9eS' , 'Admin', 'User', 1);

INSERT INTO Utente (email, username, password, nome, cognome, amministratore)
VALUES ('johndoe@example.com', 'johndoe', '$2a$12$jL.nXXqz6OLLg8JfdTojveGUsWWvZFBVpSWddeQ3Y88ssjW2nr9eS', 'John', 'Doe', 0);

-- Inserimento di Prodotti e Immagini
-- Prodotto 1: EgyptianStone
INSERT INTO Prodotto (nome, descrizione, prezzo, valido, in_evidenza, data_creazione) VALUES
    ('EgyptianStone',
     "Scopri la magia della storia con la nostra antica Testa di Amuleto Faraonica Egizia! Questo pezzo unico, fatto a mano, vanta dettagli intricati che ti trasportano all'epoca dei maestosi faraoni. Perfetto per collezionisti appassionati e amanti della storia, questo squisito pezzo è più di un semplice decoro: è un viaggio nel tempo. Decora il tuo spazio con un simbolo di potere e mistero, lasciandoti ispirare dall'antica arte. Possiedi un tesoro senza tempo e percepisci il fascino dell’antico Egitto ogni giorno.",
     299.99, 1, 1, DEFAULT);

SET @last_product_id = LAST_INSERT_ID();

INSERT INTO Immagine_Prodotto (id_prodotto, immagine) VALUES
                                                          (@last_product_id, 'EgyptianStone1.webp'),
                                                          (@last_product_id, 'EgyptianStone2.webp');

-- Prodotto 2: Death Whistle
INSERT INTO Prodotto (nome, descrizione, prezzo, valido, in_evidenza, data_creazione) VALUES
    ('Death Whistle',
     "Questi fischietti e flauti in argilla sono repliche di sculture antiche delle culture Maya e Azteca, molti ritrovati in antiche tombe da archeologi. Inizialmente si pensava fossero giocattoli, ma in seguito si è scoperto che erano per lo più oggetti di guerra utilizzati per spaventare i nemici. Esiste una vasta gamma di fischietti: alcuni a forma di animali, di diverse dimensioni e forme, utilizzabili come flauti, altri raffiguranti teschi che riproducono il suono della morte o un grido umano, e teste di giaguaro che emettono un incredibile ruggito di giaguaro.",
     149.99, 1, 1, DEFAULT);

SET @last_product_id = LAST_INSERT_ID();

INSERT INTO Immagine_Prodotto (id_prodotto, immagine) VALUES
                                                          (@last_product_id, 'DeathWhistle1.webp'),
                                                          (@last_product_id, 'DeathWhistle2.webp');

-- Prodotto 3: Vintage Typewriter
INSERT INTO Prodotto (nome, descrizione, prezzo, valido, in_evidenza, data_creazione) VALUES
    ('Vintage Typewriter',
     "Ti offriamo una macchina da scrivere vintage, un pezzo squisito di rilevanza storica e d’ufficio.
     Questo raro oggetto da collezione presenta un’artigianalità e un design intricati, che riflettono gli stili funzionali ed estetici della sua epoca.
     La macchina è in buone condizioni, con foto dettagliate a disposizione per maggiore chiarezza.
     È un’aggiunta perfetta per collezionisti di attrezzature da ufficio d’epoca, reperti storici o decorazioni uniche per la casa.
     Che venga usata per scrivere o come elemento decorativo, questa macchina da scrivere porta un tocco di fascino retrò e storia in qualsiasi spazio."
        ,
     399.99, 1, 1, DEFAULT);

SET @last_product_id = LAST_INSERT_ID();

INSERT INTO Immagine_Prodotto (id_prodotto, immagine) VALUES
                                                          (@last_product_id, 'Typewriter1.webp'),
                                                          (@last_product_id, 'Typewriter2.webp');

-- Prodotti senza evidenza (in_evidenza = 0)

-- Prodotto 4: Antique Nautical Compass
INSERT INTO Prodotto (nome, descrizione, prezzo, valido, in_evidenza, data_creazione) VALUES
    ('Antique Nautical Compass',
     "Il nostro compasso garantisce che i meccanismi interni siano perfettamente protetti, offrendo allo stesso tempo eleganza e una resistenza alla corrosione che lo rende duraturo e piacevole alla vista. Il compasso racchiude una varietà di caratteristiche uniche in un piccolo oggetto a un prezzo contenuto. Il lato superiore del pezzo in ottone massiccio da 2,25” di diametro è inciso con un calendario centenario (2000-2099), mentre la parte interna e posteriore possono essere personalizzate con il tuo nome o un breve messaggio. Perfetti da regalare a te o ai tuoi cari.",
     89.99, 1, 0, DEFAULT);

SET @last_product_id = LAST_INSERT_ID();

INSERT INTO Immagine_Prodotto (id_prodotto, immagine) VALUES
                                                          (@last_product_id, 'NauticalCompass1.webp'),
                                                          (@last_product_id, 'NauticalCompass2.webp');

-- Prodotto 5: Historical Ring
INSERT INTO Prodotto (nome, descrizione, prezzo, valido, in_evidenza, data_creazione) VALUES
    ('Historical Ring',
     "L’anello antico originale. Un regalo perfetto per intenditori o amanti della storia antica. Dimensioni: diametro 18 mm. Guarda tutte le foto per comprendere appieno l’aspetto e la condizione dell’oggetto. Siamo lieti di accoglierti nel nostro negozio, dove puoi acquistare numerosi reperti storici interessanti e toccare la storia con mano. Tutti i reperti sono stati trovati con un metal detector e sono originali. Qui troverai splendidi regali per i tuoi cari e amici, oltre a poter arricchire la tua collezione. Buoni acquisti. :)",
     59.99, 1, 0, DEFAULT);

SET @last_product_id = LAST_INSERT_ID();

INSERT INTO Immagine_Prodotto (id_prodotto, immagine) VALUES
                                                          (@last_product_id, 'HistoricalRing1.webp'),
                                                          (@last_product_id, 'HistoricalRing2.webp');

-- Prodotto 6: Columbian Moche Temple
INSERT INTO Prodotto (nome, descrizione, prezzo, valido, in_evidenza, data_creazione) VALUES
    ('Columbian Moche Temple',
     "Accompagnato da un certificato di autenticità e una politica di restituzione senza condizioni di un anno - Autentico reperto precolombiano dalla costa settentrionale del Perù. L'artefatto raffigura una scena suggestiva di un rituale sciamanico di ascesa al tempio. Questo vaso a manico singolo della cultura Moche (Mochica) risale alla fase 3 (Periodo Intermedio Tardo), circa 400-600 d.C. Il modesto scalatore ha un naso pronunciato e occhi a mandorla che guardano verso il cielo. Il vaso presenta una colorazione marrone rossiccia con dettagli in crema dipinti.",
     649.99, 1, 0, DEFAULT);

SET @last_product_id = LAST_INSERT_ID();

INSERT INTO Immagine_Prodotto (id_prodotto, immagine) VALUES
                                                          (@last_product_id, 'Columbian1.webp'),
                                                          (@last_product_id, 'Columbian2.webp');
-- Prodotto 7: Indiana Jones Diary
INSERT INTO Prodotto (nome, descrizione, prezzo, valido, in_evidenza, data_creazione) VALUES
    ('Indiana Jones Diary',
     "Di alta qualità e completamente nuovo. Come fan di Indiana Jones, amo collezionare copie di oggetti di scena dai diari di Indiana Jones e l'Ultima Crociata, e questo è il diario del Santo Graal, una riproduzione accurata dell'originale. Ora puoi avere un ottimo accessorio per il cosplay per Halloween, convention di fumetti o ogni volta che desideri impersonare Indiana Jones o suo padre Henry Jones! Materiale: carta. Colore: marrone. Dimensioni: 4 x 7 pollici / 17 x 10,5 cm. Pacchetto include: 1 pezzo di diario.",
     34.99, 1, 0, DEFAULT);

SET @last_product_id = LAST_INSERT_ID();

INSERT INTO Immagine_Prodotto (id_prodotto, immagine) VALUES
                                                          (@last_product_id, 'Indiana1.webp'),
                                                          (@last_product_id, 'Indiana2.webp');

-- **Creazione di un carrello per l'utente johndoe**
INSERT INTO Carrello (email, data_creazione) VALUES ('johndoe@example.com', DEFAULT);

SET @id_carrello_john = LAST_INSERT_ID();

-- **Aggiunta di articoli al carrello dell'utente johndoe**
INSERT INTO CarrelloItem (id_carrello, id_prodotto, quantita) VALUES
                                                                  (@id_carrello_john, 1, 2), -- 2 unità del prodotto con id 1
                                                                  (@id_carrello_john, 2, 1); -- 1 unità del prodotto con id 2

-- **Creazione di un ordine per l'utente johndoe**
INSERT INTO Ordine (email, data_ordine, totale, stato, indirizzo_spedizione, metodo_pagamento, ultime_quattro_cifre)
VALUES ('johndoe@example.com', DEFAULT, 489.98, 'Consegnato', 'Via Roma 1, 00100 Roma', 'carta', '1234');

SET @id_ordine_john = LAST_INSERT_ID();

-- **Aggiunta di articoli all'ordine di johndoe**
INSERT INTO OrdineItem (id_ordine, id_prodotto, quantita, prezzo_unitario) VALUES
                                                                               (@id_ordine_john, 3, 1, 399.99), -- 1 unità del prodotto con id 3
                                                                               (@id_ordine_john, 4, 1, 89.99);  -- 1 unità del prodotto con id 4

-- **Creazione di un secondo ordine per l'utente johndoe**
INSERT INTO Ordine (email, data_ordine, totale, stato, indirizzo_spedizione, metodo_pagamento, ultime_quattro_cifre)
VALUES ('johndoe@example.com', DEFAULT, 94.98, 'In elaborazione', 'Via Roma 1, 00100 Roma', 'paypal', NULL);

SET @id_ordine_john2 = LAST_INSERT_ID();

-- **Aggiunta di articoli al secondo ordine di johndoe**
INSERT INTO OrdineItem (id_ordine, id_prodotto, quantita, prezzo_unitario) VALUES
                                                                               (@id_ordine_john2, 5, 1, 59.99), -- 1 unità del prodotto con id 5
                                                                               (@id_ordine_john2, 7, 1, 34.99);  -- 1 unità del prodotto con id 7
