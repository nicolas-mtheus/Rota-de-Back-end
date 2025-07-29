#📚 API REST para Sistema de Gerenciamento de Biblioteca
✨ Visão Geral do Projeto
Este projeto consiste em uma API RESTful completa para um sistema de gerenciamento de biblioteca. Desenvolvida com Spring Boot, a aplicação oferece funcionalidades robustas para o gerenciamento de Pessoas (usuários) e Livros, além de permitir o empréstimo e a devolução de obras.

A API foi construída com foco em boas práticas de desenvolvimento, incluindo segurança com Spring Security, tratamento adequado de exceções e integração com serviços externos para enriquecimento de dados. A documentação interativa é fornecida via Swagger UI.

🚀 Funcionalidades
A API implementa os seguintes recursos principais:

Gerenciamento de Pessoas (Usuários):

CRUD Completo: Criação, listagem, busca por ID, atualização e exclusão de registros de pessoas.

Autenticação: Pessoas podem se autenticar na API utilizando suas credenciais (e-mail e senha).

Integração ViaCEP: Preenchimento automático de dados de endereço (logradouro, bairro, cidade, estado) a partir do CEP informado, utilizando a API externa do ViaCEP.

Gerenciamento de Livros:

CRUD Completo: Criação, listagem, busca por ID, atualização e exclusão de registros de livros.

Gerenciamento de Empréstimos:

Relação Muitos-para-Muitos (M:N): Modelagem de empréstimos entre Pessoas e Livros utilizando uma tabela de associação.

Empréstimo de Livros: Registro de quando uma Pessoa pega um Livro emprestado.

Devolução de Livros: Registro da data de devolução real de um livro emprestado.

Segurança (Spring Security):

Sistema de autenticação baseado em usuário/senha com criptografia de senhas (BCrypt).

Proteção de endpoints, permitindo acesso público apenas para o cadastro de novas pessoas (POST /pessoas).

Outras rotas exigem autenticação HTTP Basic.

Tratamento de Exceções:

Implementação de um GlobalExceptionHandler para centralizar o tratamento de erros.

Retorno de mensagens de erro claras e status HTTP adequados (400 Bad Request para regras de negócio violadas, 404 Not Found para recursos não encontrados, 500 Internal Server Error para erros internos).

Validação de regras de negócio (ex: data de devolução no passado, livro já emprestado).

Documentação Interativa (Swagger UI):

Documentação completa e interativa dos endpoints da API, acessível via navegador.

💻 Tecnologias Utilizadas
Java 17

Spring Boot 3.2.0

Spring Data JPA: Para persistência de dados e interação com o banco de dados.

Spring Security: Para autenticação e autorização.

Spring Cloud OpenFeign: Para consumo da API externa do ViaCEP.

MySQL 8: Sistema de gerenciamento de banco de dados.

Maven: Gerenciador de dependências e construção do projeto.

Lombok: Para reduzir boilerplate code (getters, setters, construtores).

Jakarta Validation: Para validação de dados de entrada.

Springdoc OpenAPI (Swagger UI): Para documentação interativa da API.

⚙️ Pré-requisitos
Antes de rodar a aplicação, certifique-se de ter instalado:

Java Development Kit (JDK) 17 ou superior.

Apache Maven 3.x ou superior.

Um servidor MySQL 8.x rodando localmente (ou em nuvem).

Um cliente MySQL (ex: phpMyAdmin, MySQL Workbench) para criar o banco de dados.

🚀 Como Rodar a Aplicação
Clone o Repositório:

Bash

git clone https://github.com/nicolas-mtheus/Rota-de-Back-end.git
cd Rota-de-Back-end/apiProjeto
(Ajuste o cd para o diretório apiProjeto se você não o clonou diretamente).

Configurar o Banco de Dados:

Crie um banco de dados MySQL chamado GerenciadorDeLivros (ou o nome configurado no application.properties).

SQL

CREATE DATABASE GerenciadorDeLivros;
Atualize as credenciais de acesso ao banco de dados no arquivo src/main/resources/application.properties:

Properties

spring.datasource.url=jdbc:mysql://localhost:3306/GerenciadorDeLivros?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=sua_senha_do_mysql
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update # Manter em 'update' para que as tabelas sejam criadas automaticamente
Compilar o Projeto:

Bash

mvn clean install
Este comando baixará as dependências, compilará o código e criará o arquivo .jar na pasta target/.

Executar a Aplicação:

Bash

mvn spring-boot:run
A aplicação será iniciada e o Tomcat estará escutando na porta 8080.

📖 Acesso à Documentação da API (Swagger UI)
Para acessar a documentação interativa da API via Swagger UI, navegue para:

http://localhost:8080/swagger-ui.html

Importante: Devido à configuração de segurança da aplicação (Spring Security), para interagir com os endpoints da API através do Swagger UI, é necessário que você possua um usuário cadastrado no banco de dados.

Ao tentar realizar uma requisição, o Swagger UI (ou o navegador) solicitará credenciais de autenticação. Utilize o nome de usuário e a senha de um usuário previamente cadastrado no seu banco de dados para autenticar-se e ter acesso aos recursos da API.

Como Obter um Usuário para Login (Cadastro):
Use um cliente REST (como Insomnia ou Postman).

Envie uma requisição POST para http://localhost:8080/pessoas com o Body JSON de uma nova pessoa (incluindo email e senha).

Esta rota é pública (No Auth).

Exemplo de Body:

JSON

{
  "nome": "Usuario API",
  "cpf": "11122233344",
  "cep": "37701000",
  "email": "usuario@api.com",
  "senha": "senhaforte123"
}
Após o cadastro bem-sucedido, utilize o email (usuario@api.com) e a senha (senhaforte123) desta pessoa para se autenticar no pop-up do navegador ou nas requisições do Swagger/Insomnia (usando Basic Authentication).

🛠️ Testes e Exemplos de Uso
Todos os endpoints da API (/pessoas, /livros, /emprestimos) estão protegidos por autenticação, exceto o POST /pessoas para cadastro.

Você pode usar o Swagger UI (após login) ou um cliente REST como Insomnia/Postman para testar:

POST /pessoas: Cadastrar nova pessoa (PÚBLICO - No Auth)

GET /pessoas: Listar todas as pessoas (AUTENTICADO - Basic Auth)

GET /pessoas/{id}: Buscar pessoa por ID (AUTENTICADO - Basic Auth)

PUT /pessoas/{id}: Atualizar pessoa (AUTENTICADO - Basic Auth)

DELETE /pessoas/{id}: Deletar pessoa (AUTENTICADO - Basic Auth)

(Repita o padrão de testes para /livros e /emprestimos - todos exigirão Basic Auth.)

🧑‍💻 Autor
Nicolas Matheus Lima Oliveira

Seu perfil no GitHub

[Linkedin : (https://www.linkedin.com/in/nicolas-matheus-36a444276)]

📄 Licença
Este projeto está licenciado sob a licença MIT.

