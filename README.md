 # Aplicativo de Lista de Verificação 
 Este projeto é um aplicativo de lista de verificação desenvolvido usando Spring Boot. Ele fornece uma API RESTful para gerenciar categorias e tarefas.
 ## Tecnologias Utilizadas 
 * Spring Boot: Framework para construir aplicações Spring independentes e prontas para produção.
 *  Spring Data JPA: Para acesso e persistência de dados com Hibernate.
 *   Banco de Dados : MySql
 *    * Maven: Ferramenta de gerenciamento de dependências e automação de builds.
*  * Java 17: Versão da linguagem de programação.
*  ## Estrutura do Projeto
*   O projeto segue uma estrutura padrão de aplicação Spring Boot:
* syeknom.Checklist.controller: Lida com requisições HTTP recebidas e retorna respostas.
* * syeknom.Checklist.service: Contém a lógica de negócios da aplicação.
*  * syeknom.Checklist.repository: Fornece interfaces para operações de acesso a dados.
*  * syeknom.Checklist.model: Define os modelos de dados (entidades).
*  * syeknom.Checklist.dto: Objetos de Transferência de Dados para corpos de requisição e resposta.
*  * ​​syeknom.Checklist.exception: Tratamento global de exceções.
*  ## Configuração e Instalação
*  . Clone o repositório: git clone cd Checklist-App-master
*  . Navegue até o diretório do aplicativo: cd Checklist-App

 Compile o projeto usando o Maven: ./mvnw clean install 
 ## Como Executar
 . Execute o aplicativo Spring Boot: ./mvnw spring-boot:run

 O aplicativo será iniciado na porta 8080 por padrão. Você pode acessar os endpoints da API em `http://localhost:8080`.


 ## Endpoints da API (Exemplos)

* **Categorias:**
 * `GET /api/categories`: Obter todas as categorias.

 * `POST /api/categories`: Criar uma nova categoria.

 * `GET /api/categories/{id}`: Obter uma categoria por ID.

 * PUT /api/categories/{id}: Atualizar uma categoria.
 *  * DELETE /api/categories/{id}: Excluir uma categoria.
    *  * Tarefas:
*  * GET /api/tasks: Obter todas as tarefas.
* * POST /api/tasks: Criar uma nova tarefa.
*  * GET /api/tasks/{id}: Obter uma tarefa por ID.
*  * PUT /api/tasks/{id}: Atualiza uma tarefa.
*  * DELETE /api/tasks/{id}: Exclui uma tarefa.
* * PATCH /api/tasks/{id}/status: Atualiza o status de uma tarefa.
