use petshop;

create table if not exists User (
    cpf varchar(14) PRIMARY KEY not null,
    nome Text(100) ,
    ps Text ,
    tipo Text
    );

 create table if not exists Client (
	id integer PRIMARY KEY auto_increment,
    cpf varchar(14) ,
    nome Text(100),
    dtcadastro date,
    
    FOREIGN KEY (cpf) REFERENCES User(cpf)
    );

  create table if not exists Endereco (
	id integer PRIMARY KEY auto_increment,
    id_cliente integer,
    logradouro Text(500),
    cidade Text(300),
    bairro Text(200),
    complemento Text (100),
    tag Text(50),
    
    FOREIGN KEY (id_cliente) REFERENCES Client(id)
    );
  

  create table if not exists Contato (
	id integer PRIMARY KEY auto_increment,
    id_cliente integer,
    tipo Text(50),
	valor Text (400),
    tag Text(50),
    
    FOREIGN KEY (id_cliente) REFERENCES Client(id)
    );    

create table if not exists Raca (
	id integer PRIMARY KEY auto_increment,
	descricao Text (400)
    );    
 

create table if not exists Pets (
	id integer PRIMARY KEY auto_increment,
    id_cliente integer,
	id_raca integer,
    dtnascimento date,
	nome Text (400),

    );    
    
create table if not exists Atendimento (
	id integer PRIMARY KEY auto_increment,
    id_pet integer,
    id_cliente integer,
    dtatendimento date,
	descricao Text (400),
    valor double,

    );    

    