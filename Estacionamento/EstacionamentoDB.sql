create database Estacionamento;
use Estacionamento;

CREATE TABLE pagamentos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    status ENUM('pago', 'pendente') NOT NULL
);

create table veiculos(
vEHICLESID INT auto_increment primary key,
placa varchar(40) Not null unique,
valor int,
type ENUM('Carro', 'Moto')Not null,
);

Alter table veiculos
ADD pago boolean default false;

create table Pix(
id int auto_increment primary key,
valor decimal(10,2) NOT NULL,
 status ENUM('pago', 'pendente') NOT NULL
);

create table Dinheiro(
id int auto_increment primary key,
valor decimal(10,2) NOT NULL,
 status ENUM('pago', 'pendente') NOT NULL
);

create table relatorio(
    
);