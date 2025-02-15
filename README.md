# Task Board API

## Descrição
Este é um projeto de API REST desenvolvido com **Spring Boot** para gerenciamento de tarefas em um **Quadro de Tarefas** (*Task Board*). A API permite criar, listar e organizar tarefas em listas dentro de um quadro.

O projeto utiliza as seguintes tecnologias:
- **Spring Boot** - Framework principal para a aplicação.
- **Spring Data JPA** - Para manipulação de dados.
- **H2 Database** - Banco de dados em memória para desenvolvimento e testes.
- **Flyway** - Gerenciamento de migrações do banco de dados.
- **Lombok** - Para reduzir a verbosidade do código.
- **Hibernate** - Implementação do JPA para ORM.
- **Swagger** - Documentação interativa da API.

## Modelagem de Dados
A API possui três entidades principais:
1. **Whiteboard** (*Quadro de Tarefas*): Representa um quadro que contém listas de tarefas.
2. **TaskList** (*Lista de Tarefas*): Contém as tarefas organizadas dentro de um quadro.
3. **Task** (*Tarefa*): Representa uma tarefa individual com prioridade e prazo.

### Diagrama de Classe (Mermaid)
```mermaid
tclassDiagram
    class Whiteboard {
        +Integer id
        +String name
        +List<TaskList> taskLists
    }

    class TaskList {
        +Integer id
        +String name
        +String description
        +Whiteboard whiteboard
        +List<Task> tasks
    }

    class Task {
        +Integer id
        +String name
        +String description
        +Priority priority
        +LocalDateTime deadLine
        +LocalDateTime createdAt
        +TaskList taskList
    }

    Whiteboard "1" --o "*" TaskList
    TaskList "1" --o "*" Task
```

## Caso de Uso
O sistema foi projetado para facilitar a organização de tarefas em projetos. Um usuário pode:
1. Criar um **Quadro de Tarefas** (*Whiteboard*).
2. Adicionar **Listas de Tarefas** (*TaskList*) dentro do quadro.
3. Criar e gerenciar **Tarefas** (*Task*) dentro das listas, definindo prioridades e prazos.

### Exemplo de Uso
1. O desenvolvedor cria um novo quadro chamado "Sprint 1".
2. Ele adiciona listas de tarefas como "A Fazer", "Em Progresso" e "Concluído".
3. Dentro da lista "A Fazer", ele adiciona tarefas como "Criar API de autenticação" e "Configurar Banco de Dados".

## Configuração e Execução
### Requisitos
- Java 17+
- Maven

### Como executar o projeto
1. Clone este repositório:
   ```sh
   git clone git@github.com:JoseLSousa/TaskBoard-Spring.git
   cd task-board
   ```
2. Execute o projeto com Maven:
   ```sh
   mvn spring-boot:run
   ```
3. A API estará disponível em `http://localhost:8080`

## Documentação da API com Swagger
O projeto conta com **Swagger** para facilitar o teste e a exploração dos endpoints.

- A documentação interativa pode ser acessada em:
  ```
  http://localhost:8080/swagger-ui.html
  ```

## Migrações de Banco de Dados
O Flyway gerencia a criação e evolução do banco de dados. As migrações estão localizadas em `src/main/resources/db/migration/` e são executadas automaticamente ao iniciar a aplicação.

