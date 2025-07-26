# 🧠 TaskManager API

API REST para gerenciamento de tarefas e alocação de pessoas, desenvolvida em Java com Spring Boot e PostgreSQL.

---

## 🚀 Tecnologias utilizadas

- Java 17
- Spring Boot 3.x
- PostgreSQL
- JPA / Hibernate
- Maven
- JUnit 5 + Mockito
- Lombok

---

## 📁 Estrutura do Projeto

```
src
├── controller      # Endpoints REST
├── dto             # Objetos de transferência de dados
├── model           # Entidades JPA
├── repository      # Interfaces de acesso a dados
├── service         # Regras de negócio
└── test            # Testes unitários com JUnit e Mockito
```

---

## ⚙️ Como rodar o projeto localmente

1. **Clone o repositório**

```bash
git clone https://github.com/seu-usuario/taskmanager-api.git
cd taskmanager-api
```

2. **Configure o banco PostgreSQL**

Crie um banco chamado `taskmanager` e um usuário com permissões:

```sql
CREATE DATABASE taskmanager;
CREATE USER taskuser WITH PASSWORD '123456';
GRANT ALL PRIVILEGES ON DATABASE taskmanager TO taskuser;
```

3. **Atualize o `application.properties` (se necessário)**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager
spring.datasource.username=taskuser
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
```

4. **Rode o projeto**

```bash
./mvnw spring-boot:run
```

---

## 📬 Endpoints principais

### 🧑 Pessoas

| Método | Rota                     | Descrição                                     |
|--------|--------------------------|-----------------------------------------------|
| POST   | `/pessoas`               | Cadastrar uma nova pessoa                     |
| PUT    | `/pessoas/{id}`          | Atualizar uma pessoa                          |
| DELETE | `/pessoas/{id}`          | Remover uma pessoa                            |
| GET    | `/pessoas`               | Listar todas as pessoas                       |
| GET    | `/pessoas/resumo`        | Total de horas gastas por pessoa              |
| GET    | `/pessoas/gastos`        | Média de horas por tarefa (nome e período)    |
| GET    | `/pessoas/departamentos` | Total de pessoas e tarefas por departamento   |

### ✅ Exemplo de busca por média:

```
GET /pessoas/gastos?nome=maria&inicio=2025-07-01&fim=2025-07-31
```

---

### 📋 Tarefas

| Método | Rota                         | Descrição                                       |
|--------|------------------------------|-------------------------------------------------|
| POST   | `/tarefas`                   | Cadastrar nova tarefa                           |
| PUT    | `/tarefas/alocar/{id}`       | Alocar pessoa automaticamente pela tarefa       |
| PUT    | `/tarefas/finalizar/{id}`    | Finalizar a tarefa                              |
| GET    | `/tarefas/pendentes`         | Listar 3 tarefas mais antigas sem alocação      |

---

## ✅ Testes

Execute os testes com:

```bash
./mvnw test
```

Cobertura:
- Serviços (`PessoaService`, `TarefaService`)
- Controllers (`PessoaController`, `TarefaController`)
- MockMvc e JUnit 5

---

## 📌 Observações

- Evita ciclos infinitos usando `@JsonManagedReference` / `@JsonBackReference`
- Pronto para deploy em ambientes como Heroku, Railway, etc.
- Pode ser integrado com Swagger futuramente

---

## ✍️ Autor

Desenvolvido por [Seu Nome](https://github.com/seu-usuario)