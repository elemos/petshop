# petshop

Antes de inciar o projeto é necessário rodar o script PetShopBD para criar as tabelas do banco.
Buildar o jar do projeto.
A partir rodar o docker pelo aplicativo desktop, ou pelo comando docker-compose up --build.
Criar um novo usuário admin e cliente e interagir com o resto da aplicação.


To do

- Implementar novos endpoints para consultas dinamicas, como listas os endereços por bairro ou cidade.
- Implementar persistencia de dados nos deletes, atualmente ao deletar um cliente não é feito o delete de seus endreços contatos e pets. 
- Implementar restante dos testes unitários, inclusive para testar casos de autenticação.
- Melhorar documentação (comentários)
- Melhorar tratamento nos retornos para casos de erros (dado não encontrado, etc)

 
