-- dump.sql

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


-- Produtos de exemplo
INSERT INTO tb_products (id, name, description, price, category, stock_quantity, created_at, updated_at) VALUES
(UUID(), 'Notebook Dell Inspiron', 'Notebook Dell Inspiron 15 Intel i5', 3500.00, 'Eletrônicos', 10, NOW(), NOW()),
(UUID(), 'Mouse Logitech MX Master', 'Mouse sem fio ergonômico', 450.00, 'Periféricos', 50, NOW(), NOW()),
(UUID(), 'Teclado Mecânico Keychron', 'Teclado mecânico RGB', 650.00, 'Periféricos', 30, NOW(), NOW()),
(UUID(), 'Monitor LG 27 4K', 'Monitor 27 polegadas 4K', 2200.00, 'Monitores', 15, NOW(), NOW()),
(UUID(), 'Cadeira Gamer DXRacer', 'Cadeira ergonômica para gamers', 1800.00, 'Móveis', 8, NOW(), NOW());