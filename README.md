# 🦇 Gotham Lock

**Gotham Lock** é uma aplicação web full stack desenvolvida para as **Indústrias Wayne**, com o objetivo de gerenciar o **controle de acesso a áreas restritas**, supervisionar o **inventário de recursos** e registrar **logs de segurança** de forma moderna, escalável e confiável.

O sistema utiliza **Spring Boot 3.5.7**, **Java 21**, **Spring Security** e **Spring Data JPA**, com **banco H2** para testes e um **frontend em HTML, CSS e JavaScript** inspirado na identidade visual das Indústrias Wayne.

---

## 🚀 Tecnologias Utilizadas

### 🖥️ Backend
- ☕ **Java 21**
- ⚙️ **Spring Boot 3.5.7**
- 🌐 **Spring Web**
- 💾 **Spring Data JPA (Hibernate)**
- 🔐 **Spring Security**
- 🧠 **H2 Database** (banco em memória para testes)
- 🧰 **Maven & Spring Boot Plugin**
- ✅ **Bean Validation** (validações de entrada de dados)

### 🎨 Frontend
- HTML5, CSS3 e JavaScript
- Interface em **dark mode** com cores inspiradas na **Wayne Enterprises**
- Layout responsivo com **Dashboard, Inventário e Logs**
- Ícones e elementos minimalistas com destaque em **amarelo (`#FFCC00`)**

---

## 📘 Documentação da API (Swagger)

O projeto conta com **documentação interativa da API** utilizando **Swagger/OpenAPI**, facilitando testes, entendimento dos endpoints e integração com outras aplicações.

### 🔗 Acesso ao Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```

### 🔐 Segurança no Swagger
- A API utiliza **Basic Authentication**
- Todos os endpoints protegidos exigem autenticação
- O Swagger está configurado com **SecurityScheme HTTP Basic**
- É possível autenticar diretamente pelo botão **Authorize 🔒** da interface

---

## ⚙️ Dependências (Maven)

| Tipo | Dependência | Descrição |
|------|--------------|------------|
| 🧩 **Core Web** | `spring-boot-starter-web` | Criação de APIs REST e servidor embutido (Tomcat). |
| 💾 **Persistência** | `spring-boot-starter-data-jpa` | Integração com JPA/Hibernate. |
|    **Documentação** | `springdoc-openapi-starter-webmvc-ui` | Swagger / OpenAPI |
| 🔐 **Segurança** | `spring-boot-starter-security` | Autenticação e autorização. |
| 🧠 **Banco de Dados** | `com.h2database:h2` | Banco em memória para testes. |
| ✅ **Validação** | `spring-boot-starter-validation` | Validação de dados com Bean Validation. |
| 🧪 **Testes** | `spring-boot-starter-test` | Frameworks JUnit, AssertJ e Mockito. |
| 🔒 **Testes de Segurança** | `spring-security-test` | Suporte a testes de autenticação. |

---

## 🧩 Estrutura do Projeto

```
📂 Projeto Wayne Security

├── backend/
│   ├── src/
│   │   ├── main/java/com/wayne/waynesecurity/
│   │   │   ├── config/                # Configurações de segurança e perfis (Spring Security, OpenApiConfig, profiles, beans)  
│   │   │   ├── controllers/           # Controladores REST (endpoints HTTP)
|   |   |   |     └── exceptions/      # Tratamento global de exceções e erros personalizados
│   │   │   ├── model/                 # Entidades JPA e mapeamentos do domínio
│   │   │   │   └── enums/             # Enumerações (Roles, Status, Tipos, etc.)
|   |   |   |   └── dto/               # Data Transfer Objects (entrada e saída da API)
│   │   │   ├── repositories/          # Interfaces JPA para acesso ao banco de dados
│   │   │   └── services/              # Regras de negócio e integração entre camadas
│   │   │     └── exceptions/          # Exceções personalizadas usadas nos serviços
│   │   │
│   │   └── resources/
│   │       ├── static/                  # Arquivos estáticos (HTML, CSS, JS)
│   │       ├── application.properties   # Configuração padrão
│   │       └── application-test.properties   # Configuração para perfil de testes
│   │
│   └── pom.xml                        # Gerenciador de dependências Maven
│
├── frontend/                          # (caso o frontend seja separado)
│   ├── login.html                     # Página de login
│   ├── dashboard.html                 # Painel principal
│   ├── inventory.html                 # Controle de inventário
│   ├── users.html                     # Gestão de usuários
│   ├── accesslogs.html                # Logs de acesso
│   ├── img/                           # Imagens e ícones
│   ├── css/                           # Estilos
│   └── js/                            # Scripts JavaScript
│
└── README.md                          # Documentação principal do projeto

```

---


---

## 🔐 Funcionalidades Principais

### 👤 Autenticação e Segurança
- Endpoint `/auth/me` retorna dados do usuário autenticado.  
- Sistema de autenticação via **Spring Security + BCrypt**.  
- Permissões baseadas em **roles** (`ADMIN_SEGURANCA`, `GERENTE`, `FUNCIONARIO`).  
- Tratamento customizado de acessos negados (`CustomAccessDeniedHandler`) retornando JSON padronizado.  
- **Validação de login e bloqueio de endpoints protegidos** por perfil.  

## 👥 Gestão de Usuários
- CRUD completo de usuários com senhas criptografadas.
- DTOs separados para **Request** (criação) e **Response** (retorno sem senha).
- Respostas padronizadas com `StandardError` e `ValidationError`.

### 🧠 Controle de Acesso (Access Logs)
- Registro automático de entradas/saídas e resultados (`AUTORIZADO` ou `NEGADO`).
- Campos validados via Bean Validation (`AccessLogRequestDTO`).
- Associação com usuários registrados.

### ⚙️ Inventário
- CRUD de itens internos com `InventoryType` (`EQUIPAMENTO`, `VEICULO`, `DISPOSITIVO`).
- Status (`DISPONIVEL`, `EM_USO`, `MANUTENCAO`) atualizável.
- Camada de serviço protegida e validada.

### 📊 Dashboard
- Estatísticas em tempo real sobre acessos e recursos.
- Integração com o backend via REST.
- Layout responsivo e moderno.

---

## 🧪 Perfis e Banco de Dados

| Ambiente | Banco | Console |
|-----------|--------|---------|
| `test` | H2 (in-memory) | [http://localhost:8080/h2-console](http://localhost:8080/h2-console) |

**Credenciais padrão**
- JDBC URL: jdbc:h2:mem:waynedb
- User: sa
- Password: (em branco)

---

## 👤 Usuários de Teste

| Nome | E-mail | Senha | Função |
|------|--------|--------|--------|
| Bruce Wayne | bruce@wayne.com | 123 | ADMIN_SEGURANCA |
| Alfred Pennyworth | alfred@wayne.com | 123 | GERENTE |
| Dick Grayson | dick@wayne.com | 123 | FUNCIONARIO |

---

## 🧠 Boas Práticas e Padrões Aplicados

- Arquitetura **MVC em camadas** (Controller → Service → Repository).  
- Uso de **DTOs** e `@Valid` para segurança dos dados.  
- **Tratamento global de exceções** com `GlobalExceptionHandler`.  
- **Logs estruturados** e mensagens de erro coerentes.  
- **Senhas criptografadas** (`BCryptPasswordEncoder`).  
- **Respostas padronizadas** via `StandardError`/`ValidationError`.  
- **Enum mapeados por nome (STRING)** para legibilidade e compatibilidade.  
- **Injeção de dependência via construtor** (boa prática de imutabilidade).  
- **Clean Code e SOLID principles** adotados em toda a base.

---

## Execução do Projeto

### ▶️ 1. Clonar o repositório

```bash
git clone https://github.com/seuusuario/waynesecurity.git
```
```
cd waynesecurity/backend
```

### ▶️ 2. Executar o backend 
```
mvn spring-boot:run
```
- Servidor disponível em:
```
http://localhost:8080
```
### ▶️ 3. Executar o frontend
```
http://localhost:8080/login.html
```

---

## 👨‍💻 Autor

**Desenvolvido por Eduardo Peçanha**  
💼 Desenvolvedor Backend | Java | Spring Boot | Desenvolvedor Full-Stack  
📧 E-mail: eduardopecanha05@gmail.com  
🌐 [LinkedIn](https://www.linkedin.com/in/eduardopecanhasantos/) | [GitHub](https://github.com/EduardoPec)


