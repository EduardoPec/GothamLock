# 🦇 Wayne Security System

**Wayne Security** é uma aplicação web full stack desenvolvida para as **Indústrias Wayne**, com o objetivo de gerenciar o **controle de acesso a áreas restritas**, supervisionar o **inventário de recursos** e registrar **logs de segurança** de forma moderna, escalável e confiável.

O sistema utiliza **Spring Boot 3.5.7**, **Java 21**, **Spring Security** e **Spring Data JPA**, com **banco H2** para testes e um **frontend em HTML, CSS e JavaScript** inspirado na identidade visual das Indústrias Wayne 🦇.

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

### 🎨 Frontend
- HTML5, CSS3 e JavaScript
- Interface em **dark mode** com cores inspiradas na **Wayne Enterprises**
- Layout responsivo com **Dashboard, Inventário e Logs**
- Ícones e elementos minimalistas com destaque em **amarelo (`#FFCC00`)**

---

## ⚙️ Dependências (Maven)

| Tipo | Dependência | Descrição |
|------|--------------|------------|
| 🧩 **Core Web** | `spring-boot-starter-web` | Criação de APIs REST e servidor embutido (Tomcat). |
| 💾 **Persistência** | `spring-boot-starter-data-jpa` | Integração com JPA/Hibernate. |
| 🔐 **Segurança** | `spring-boot-starter-security` | Autenticação e autorização. |
| 🧠 **Banco de Dados** | `com.h2database:h2` | Banco em memória para testes. |
| 🧪 **Testes** | `spring-boot-starter-test` | Frameworks JUnit, AssertJ e Mockito. |
| 🔒 **Testes de Segurança** | `spring-security-test` | Suporte a testes de autenticação. |

### 🔧 Plugins e Propriedades
- **Parent:** `org.springframework.boot:spring-boot-starter-parent:3.5.7`
- **Java Version:** `21`
- **Build Plugin:** `spring-boot-maven-plugin`
- **Propriedade adicional:** `jjwt.version = 0.12.3` *(para futura implementação de JWT)*

---

## 🧩 Estrutura do Projeto
```
├── backend/
│ ├── src/
│ │ ├── main/java/com/wayne/waynesecurity/
│ │ │ ├── config/ # Configurações de segurança e perfis
│ │ │ ├── controllers/ # Endpoints REST
│ │ │ ├── dto/ # Data Transfer Objects
│ │ │ ├── model/ # Entidades JPA
│ │ │ ├── repositories/ # Repositórios JPA
│ │ │ ├── services/ # Regras de negócio
│ │ │ └── exceptions/ # Tratamento global de erros
│ │ └── resources/
│ │ ├── static/ # Frontend (HTML, CSS, JS)
| | ├── application-test.properties
│ │ └── application.properties
│ └── pom.xml
│
├── frontend/ # (caso o frontend seja mantido separado)
│ ├── login.html
│ ├── dashboard.html
│ ├── inventory.html
| ├── users.html
| ├── accesslogs.html
| ├── img/
│ ├── css/
│ └── js/
│
└── README.md
```

---

## 🔐 Funcionalidades Principais

### 👥 Gestão de Usuários
- Criação e autenticação de usuários com **senha criptografada** (`BCryptPasswordEncoder`)
- Perfis de acesso definidos por **Role** (`ADMIN_SEGURANCA`, `GERENTE`, `FUNCIONARIO`)
- Proteção de rotas via **Spring Security**

### 🧠 Controle de Acesso
- Registro automático de **logs de acesso (`AccessLog`)**  
- Armazena área, tipo (entrada/saída), resultado (autorizado/negado) e data/hora  
- Associação direta com o usuário responsável

### ⚙️ Inventário
- CRUD completo para gerenciamento de recursos internos (`Inventory`) e para cadastro de usuários (`User`)
- Tipos de recurso: `VEICULO`, `EQUIPAMENTO`, `DISPOSITIVO`
- Status: `DISPONIVEL`, `EM_USO`, `MANUTENCAO`

### 📊 Dashboard
- Exibição de métricas e estatísticas de acessos e recursos
- Design moderno e responsivo
- Botão de logout estilizado com feedback visual

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

## ⚙️ Execução do Projeto

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

## 👤 Usuários de Teste

O projeto já inclui dados pré-carregados no banco H2 (definidos em `TestConfig.java`) para facilitar a validação das funcionalidades de autenticação e controle de acesso.

| 🧍 Nome | 📧 Email | 🔑 Senha | 🧾 Função (Role) |
|----------|-----------|-----------|------------------|
| Bruce Wayne | bruce@wayne.com | 123 | ADMIN_SEGURANCA |
| Alfred Pennyworth | alfred@wayne.com | 123 | GERENTE |
| Dick Grayson | dick@wayne.com | 123 | FUNCIONARIO |

💡 **Dica:**  
Esses usuários são criados automaticamente ao rodar o backend no perfil de teste (`spring.profiles.active=test`).  
A autenticação é protegida via **Spring Security**, com senhas criptografadas utilizando `BCryptPasswordEncoder`.

---

## 🧠 Boas Práticas Aplicadas

O projeto **Wayne Security** foi desenvolvido seguindo padrões modernos de arquitetura e boas práticas de desenvolvimento com **Spring Boot**, garantindo legibilidade, segurança e escalabilidade do código.

### 🔹 Organização e Arquitetura
- Separação clara entre camadas **Controller → Service → Repository**, garantindo coesão e desacoplamento.
- Estrutura de pacotes organizada por responsabilidade (`config`, `controllers`, `dto`, `model`, `repositories`, `services`, `exceptions`).
- Utilização de **DTOs (Data Transfer Objects)** para evitar a exposição direta das entidades JPA.

### 🔹 Segurança e Boas Práticas de API
- Utilização do **Spring Security** com senhas criptografadas via `BCryptPasswordEncoder`.
- Aplicação de `@JsonIgnore` em campos sensíveis como `password`, impedindo a exposição de dados confidenciais em respostas JSON.
- Controle de acesso com perfis definidos por enum (`Role`), garantindo permissões específicas para cada tipo de usuário.
- Endpoint de logout funcional e protegido contra requisições CSRF.

### 🔹 Persistência e Integridade de Dados
- Utilização de **JPA/Hibernate** para persistência, com entidades bem mapeadas e relacionamentos bidirecionais tratados com `@JsonIgnore` quando necessário.
- Banco de dados **H2 em memória** no perfil de teste, permitindo execução rápida e independente.
- Carregamento automático de dados para teste via `TestConfig`.

### 🔹 Tratamento de Erros
- Implementação de `ResourceExceptionHandler` para captura e retorno padronizado de exceções.
- Exceções personalizadas (`ResourceNotFoundException`, `DatabaseException`, `SecurityException`) para melhor legibilidade e manutenção.

### 🔹 Código Limpo e Padronizado
- Uso consistente de `Optional` e `ResponseEntity` nas camadas de serviço e controle.
- Padronização de respostas REST seguindo o formato **HTTP Status + corpo JSON detalhado**.
- Adoção de nomes autoexplicativos e padronizados em inglês (ex.: `AccessLogService`, `UserRepository`, `InventoryController`).
- Configuração do projeto via `application.properties` com variáveis centralizadas.

---

💡 **Resumo:**  
O projeto segue os princípios de **Clean Code**, **SOLID** e **Boas Práticas REST**, garantindo um código claro, modular e de fácil evolução.

---

## 🦾 Próximos Passos

O projeto **Wayne Security** já está totalmente funcional, mas novas melhorias estão planejadas para torná-lo ainda mais robusto, seguro e escalável.  

### 🔹 Melhorias Técnicas
- Implementar autenticação e autorização via **JWT (JSON Web Token)** utilizando a propriedade já definida `jjwt.version = 0.12.3`.

### 🔹 Experiência do Usuário
- Criar **alertas visuais e feedbacks em tempo real** no frontend (toast notifications e loading states).
- Implementar **tema dinâmico** (modo claro/escuro) no dashboard.
- Adicionar animações suaves e melhorias de responsividade no layout.
 
### 🔹 Integração e Deploy
- Configurar **CI/CD com GitHub Actions** para build e testes automatizados.
- Fazer o **deploy do backend**, e o **frontend**.
- Adicionar **variáveis de ambiente** para conexões seguras e gestão de perfis (`dev`, `test`, `prod`).

---

💡 **Objetivo Futuro:**  
Transformar o **Wayne Security** em uma plataforma completa de monitoramento corporativo, com dashboards dinâmicos, autenticação JWT, relatórios exportáveis e integração com banco de dados PostgreSQL.

---

## 👨‍💻 Autor

**Desenvolvido por Eduardo Peçanha**  
💼 Desenvolvedor Backend | ☕ Java | ⚡ Spring Boot | 💻 Futuro Desenvolvedor Full-Stack  
📧 E-mail: eduardopecanha05@gmail.com  
🌐 [LinkedIn](https://www.linkedin.com/in/eduardopecanhasantos/) | [GitHub](https://github.com/EduardoPec)


