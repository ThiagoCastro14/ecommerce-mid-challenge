# 🛒 E-commerce Mid Challenge

Sistema de gerenciamento de produtos e pedidos para e-commerce com autenticação JWT e controle de estoque.

---

## Tecnologias Utilizadas

| Tecnologia | Versão | Função |
|------------|--------|--------|
| **Java** | 17 | Linguagem principal |
| **Spring Boot** | 3.5.7 | Framework principal |
| **Spring Security + JWT** | 6.5.6 | Autenticação e Autorização |
| **Spring Data JPA** | 6.2.12 | Persistência e ORM |
| **Flyway** | 10.17.0 | Migração e versionamento do banco |
| **MySQL** | 8.0 | Banco de dados relacional |
| **Maven** | 3.9+ | Gerenciador de dependências |

---

## Como Executar o Projeto

### Pré-requisitos

- Java 17+ instalado
- Maven 3.9+ instalado
- MySQL 8.0+ rodando

### Passo 1: Clonar o Repositório

```bash
git clone https://github.com/ThiagoCastro14/ecommerce-mid-challenge.git
cd ecommerce-mid-challenge
```

### Passo 2: Criar o Banco de Dados

No MySQL (Workbench, CLI ou DBeaver):

```sql
CREATE DATABASE ecommerce_mid_challenge;
```

### Passo 3: Configurar Credenciais

Edite o arquivo `src/main/resources/application.yml` com suas credenciais do MySQL:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ecommerce_mid_challenge
    username: root
    password: root  # Altere para sua senha
```

### Passo 4: Executar a Aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: **http://localhost:8080**

### Passo 5: Popular Dados Iniciais

Execute o SQL abaixo no MySQL para criar usuários de teste:

```sql
-- Inserir roles
INSERT INTO tb_roles (id, name) VALUES 
(UUID(), 'ADMIN'),
(UUID(), 'USER');

-- Inserir usuário ADMIN (senha: 123456)
INSERT INTO tb_users (id, name, email, password) VALUES 
(UUID(), 'Admin', 'admin@ecommerce.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy');

-- Inserir mais 4 usuários
INSERT INTO tb_users (id, name, email, password) VALUES 
(UUID(), 'Maria Santos', 'maria@ecommerce.com', '$2a$12$ofdlr/7qzcUmJqVnLFPNBOlGe09G23CgoEV3EC.g54B7CkIkyeim.'), -- Senha 12345
(UUID(), 'Pedro Oliveira', 'pedro@ecommerce.com', '$2a$12$mVNeeWh9Xmt1JnQLSKTvU.K7o23S9un2jz73A0EFkbz103nVCOE3e'), -- Senha 54321
(UUID(), 'Ana Costa', 'ana@ecommerce.com', '$2a$12$Z49io/9nAmPCwoZbxbJqceAtlu2s1gbPBaTf5uN4cCmY7.hS59cdy'), -- Senha 555555
(UUID(), 'Carlos Lima', 'carlos@ecommerce.com', '$2a$12$UF5Pixo8xw.HsbbB/GNi1ekn7EywBdSzMR5RL8Prf04WBuV58Xlpu'); -- Senha 01234


-- Associar roles aos usuários
INSERT INTO tb_user_roles (user_id, role_id) 
SELECT u.id, r.id 
FROM tb_users u, tb_roles r 
WHERE u.email = 'admin@ecommerce.com' AND r.name = 'ADMIN';

-- Associar todos com a role USER
INSERT INTO tb_user_roles (user_id, role_id) 
SELECT u.id, r.id 
FROM tb_users u, tb_roles r 
WHERE u.email IN ('maria@ecommerce.com', 'pedro@ecommerce.com', 'ana@ecommerce.com', 'carlos@ecommerce.com') 
AND r.name = 'USER';
```

---

## Testando a API

### 1. Fazer Login

**Endpoint:** `POST http://localhost:8080/api/auth/login`

**Body (JSON):**
```json
{
  "email": "admin@ecommerce.com",
  "password": "123456"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 2. Usar o Token nas Requisições

Copie o token recebido e configure no Postman:

- Aba **Authorization**
- Type: **Bearer Token**
- Token: cole o token recebido (sem o prefixo "Bearer")

### 3. Exemplos de Endpoints

**Criar Produto (ADMIN):**
```
POST http://localhost:8080/api/products
```
Body:
```json
{
  "name": "Notebook Dell",
  "description": "Notebook Dell Inspiron 15",
  "price": 3500.00,
  "category": "Eletrônicos",
  "stockQuantity": 10
}
```

**Criar Pedido (USER ou ADMIN):**
```
POST http://localhost:8080/api/orders
```
Body:
```json
{
  "items": [
    {
      "productId": "uuid-do-produto",
      "quantity": 2
    }
  ]
}
```

**Pagar Pedido:**
```
POST http://localhost:8080/api/orders/{orderId}/pay
```

---

## Endpoints Principais

### Autenticação
- `POST /api/auth/login` - Login (público)

### Produtos
- `GET /api/products` - Listar produtos (público)
- `GET /api/products/{id}` - Buscar produto (público)
- `POST /api/products` - Criar produto (ADMIN)
- `PUT /api/products/{id}` - Atualizar produto (ADMIN)
- `DELETE /api/products/{id}` - Deletar produto (ADMIN)

### Pedidos
- `POST /api/orders` - Criar pedido (autenticado)
- `GET /api/orders` - Listar meus pedidos (autenticado)
- `POST /api/orders/{id}/pay` - Pagar pedido (autenticado)

### Relatórios
- `GET /api/admin/reports/top-buyers` - Top compradores (ADMIN)
- `GET /api/admin/reports/average-ticket` - Ticket médio (ADMIN)
- `GET /api/admin/reports/monthly-revenue` - Faturamento mensal (ADMIN)

---

## Autor

**Thiago Castro**  
GitHub: [@ThiagoCastro14](https://github.com/ThiagoCastro14)
