# Controle Acadêmico – Spring Boot

Este projeto é uma aplicação web simples para gerenciamento acadêmico, desenvolvida como trabalho final da disciplina **Tópicos em Java Web – 2025.2**.

A aplicação utiliza:

- Spring Boot
- Spring Data JPA (Hibernate)
- Thymeleaf
- Spring Security

---

## 📌 Funcionalidades

### 👨‍🎓 Alunos
- Criar, editar, listar e excluir
- Campos: nome, matrícula, email, data de nascimento, status

### 📚 Disciplinas
- Criar, editar, listar e excluir (**somente ADMIN**)
- Campos: código, nome, carga horária, semestre

### 📝 Matrículas
- Registrar matrícula de aluno em disciplina
- Impede matrículas duplicadas ativas
- Exibe: aluno, disciplina, situação e nota final
- Permite alterar situação e lançar nota

### 🔐 Autenticação
- Tela de login em **`/login`**
- Cadastro de usuário em **`/register`**
- Senhas armazenadas com **BCrypt**
- Perfis disponíveis:
    - **ADMIN** → acesso total ao sistema
    - **SECRETARIA** → pode gerenciar *Alunos* e *Matrículas*

---

## 💻 Requisitos

Para rodar o projeto, você precisa ter instalado:

- ☕ **Java 17+**
- 🧱 **Maven 3.8+**
- 🐬 **MySQL 8+**

---

## 🌐 URLs Principais

| Recurso | URL |
|--------|-----|
| 🏠 Home | `/` |
| 🔐 Login | `/login` |
| 🆕 Registrar usuário | `/register` |
| 👨‍🎓 Alunos | `/alunos` |
| 📚 Disciplinas | `/disciplinas` |
| 📝 Matrículas | `/matriculas` |

---

## 🛠 Como Rodar o Projeto

### 1) Clonar o repositório

```
git clone https://github.com/VictorFranklinM/TJW-Final.git
cd TJW-Final
```

### 2) Configuração da Database
- Crie o banco **controle_academico**
- Confirme que o arquivo **`src/main/resources/application.properties`** está assim:
```
spring.datasource.url=jdbc:mysql://localhost:3306/controle_academico?useSSL=false&serverTimezone=UTC&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

### 3) Executar a aplicação
- Rodar o comando no terminal:
```
mvn spring-boot:run
```
- Ou executar a classe **`ControleAcademicoApplication
`**