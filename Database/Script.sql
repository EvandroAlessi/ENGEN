Create database Cinema;

use Cinema;

CREATE TABLE IF NOT EXISTS Clientes(
	ClienteID INT NOT NULL auto_increment,
    CPF VARCHAR(18) NOT NULL,
	Nome VARCHAR(126) NOT NULL,
	DataNascimento Date NOT NULL,
	Pontuacao INT NOT NULL,
  
	PRIMARY KEY (ClienteID)
);

CREATE TABLE IF NOT EXISTS Produtos(
	ProdutoID INT NOT NULL auto_increment,
    Descricao VARCHAR(18) NOT NULL,
	Preco FLOAT NOT NULL,
	PrecoPontuacao INT NULL,
  
	PRIMARY KEY (ProdutoID)
);

CREATE TABLE IF NOT EXISTS Filmes (
	FilmeID INT NOT NULL auto_increment,
	Titulo VARCHAR(126) NOT NULL,
	Diretor VARCHAR(126) NOT NULL,
	Genero VARCHAR(126) NOT NULL,
	Idioma VARCHAR(126) NOT NULL,
	Duracao INT NOT NULL,
  
	PRIMARY KEY (FilmeID)
);

CREATE TABLE IF NOT EXISTS Salas (
	SalaID INT NOT NULL auto_increment,
    Numero INT NOT NULL,
	Capacidade INT NOT NULL,
    
	PRIMARY KEY (SalaID)
);

CREATE TABLE IF NOT EXISTS Sessoes (
	SessaoID INT NOT NULL auto_increment,
    FilmeID INT NOT NULL,
	SalaID INT NOT NULL,
	Ingressos INT NOT NULL,
	Data DateTime NOT NULL,
	ValorIngresso Numeric(5,2) NOT NULL,
    
	PRIMARY KEY (SessaoID),
    FOREIGN KEY fk_filme(FilmeID)
	   REFERENCES Filmes(FilmeID)
	   ON UPDATE RESTRICT
	   ON DELETE RESTRICT,
	FOREIGN KEY fk_sala(SalaID)
	   REFERENCES Salas(SalaID)
	   ON UPDATE RESTRICT
	   ON DELETE RESTRICT
);

INSERT INTO Produtos(Descricao, Preco, PrecoPontuacao) values
('Refrigerante', 12.6,12),
('Crack',26, 99),
('Doce',56,20),
('Salgado',23, 84),
('Pipoca',5.6,140),
('Mac',8.9,5),
('Huan',4.4,12),
('Café',0.5,23);

INSERT INTO Clientes(Nome, CPF, DataNascimento, Pontuacao) values
("Evandro",'10562708960', '1997-11-15',0),
("Eric",'10562708960','1998-10-10', 99),
("Leandro",'10562708960','1996-03-09',20),
("Junior",'10562708960','1989-04-12', 84),
("Luiza",'10562708960','1864-10-11',140),
("Pedro",'10562708960','1956-01-02',5),
("Maria",'10562708960','2001-06-11',12),
("Vini",'10562708960','2000-12-03',23);

INSERT INTO filmes(Titulo, Diretor, Genero, Idioma, Duracao) values
("Vingadores","Tarantino","Comedia","PTBR",110),
("Click","Adolf","Acao","PTBR",99),
("Rei Leao","Mark","Comedia","PTBR",120),
("Senhor dos Aneis","Lucio","Drama","PTBR",84),
("Harry Potter","Maria Chiquinha","Drama","PTBR",140),
("Lagoa Azul","Lobao","Suspense","PTBR",100),
("Os Incriveis","Silvio","Comedia","PTBR",122),
("De Volta a Lagoa Azul","Flavio","Acao","PTBR",123);

INSERT INTO salas(Numero, Capacidade) values
(1,50),
(2,40),
(3,60),
(4,88),
(5,44),
(6,15),
(7,20),
(8,30);

INSERT INTO sessoes(FilmeID, SalaID, Ingressos, Data, valorIngresso) values
(1,1,10,'2019-06-20 11:00:00',20.99),
(2,2,12,'2019-08-08 10:00:00',15.99),
(3,3,20,'2019-09-17 12:30:00',14.99),
(4,4,11,'2019-10-19 08:00:00',17.99),
(5,5,8,'2019-11-30 07:00:00',18.99),
(6,6,13,'2019-12-12 09:30:00',16.99),
(7,7,18,'2019-08-07 14:00:00',2.90),
(8,8,19,'2019-07-03 15:00:00',5.90);