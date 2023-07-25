use petshop;

create table if not exists User (
    cpf varchar(14) PRIMARY KEY not null,
    nome varchar(100) ,
    ps longtext ,
    tipo integer
    );
    
 INSERT INTO User() values ('000.000.000-01', 'PetShop Owner', 'd1b65b70b06395cc5da1b7ccca3f16ac', 0);   
 
 create table if not exists Client (
	id integer PRIMARY KEY auto_increment,
    cpf varchar(14) ,
    nome varchar(100),
    dtcadastro date,
    
    FOREIGN KEY (cpf) REFERENCES User(cpf)
    );
 
  INSERT INTO Client() values (null,'000.000.000-01', 'PetShop Owner', '2023-07-24');   
    
  create table if not exists Endereco (
	id integer PRIMARY KEY auto_increment,
    id_cliente integer,
    logradouro varchar(500),
    cidade varchar(300),
    bairro varchar(200),
    complemento varchar (100),
    tag varchar(50),
    
    FOREIGN KEY (id_cliente) REFERENCES Client(id)
    );
    
  create table if not exists Contato (
	id integer PRIMARY KEY auto_increment,
    id_cliente integer,
    tipo varchar(50),
	valor varchar (400),
    tag varchar(50),
    
    FOREIGN KEY (id_cliente) REFERENCES Client(id)
    );    
 
create table if not exists Raca (
	id integer PRIMARY KEY auto_increment,
	descricao varchar (400)
    );    
 
create table if not exists Pets (
	id integer PRIMARY KEY auto_increment,
    id_cliente integer,
	id_raca integer,
    dtnascimento date,
	nome varchar (400),

    
    FOREIGN KEY (id_cliente) REFERENCES Client(id),
	FOREIGN KEY (id_raca) REFERENCES Raca(id)
    );    
    
create table if not exists Atendimento (
	id integer PRIMARY KEY auto_increment,
    id_pet integer,
    dtatendimento date,
	descricao varchar (400),
    valor double,

    FOREIGN KEY (id_pet) REFERENCES Pets(id)
    );    
    