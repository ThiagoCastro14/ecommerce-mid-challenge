-- dump.sql

-- Inserir roles
INSERT INTO tb_roles (id, name) VALUES 
(UUID(), 'ADMIN'),
(UUID(), 'USER');

-- Inserir usuário ADMIN (senha: 123456)
INSERT INTO tb_users (id, name, email, password) VALUES 
(UUID(), 'Admin', 'admin@ecommerce.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy');

-- Inserir usuário USER (senha: 123456)
INSERT INTO tb_users (id, name, email, password) VALUES 
(UUID(), 'User', 'user@ecommerce.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy');

-- Associar roles
INSERT INTO tb_user_roles (user_id, role_id) 
SELECT u.id, r.id 
FROM tb_users u, tb_roles r 
WHERE u.email = 'admin@ecommerce.com' AND r.name = 'ADMIN';

INSERT INTO tb_user_roles (user_id, role_id) 
SELECT u.id, r.id 
FROM tb_users u, tb_roles r 
WHERE u.email = 'user@ecommerce.com' AND r.name = 'USER';

-- Produtos de exemplo
INSERT INTO tb_products (id, name, description, price, category, stock_quantity, created_at, updated_at) VALUES
(UUID(), 'Notebook Dell Inspiron', 'Notebook Dell Inspiron 15 Intel i5', 3500.00, 'Eletrônicos', 10, NOW(), NOW()),
(UUID(), 'Mouse Logitech MX Master', 'Mouse sem fio ergonômico', 450.00, 'Periféricos', 50, NOW(), NOW()),
(UUID(), 'Teclado Mecânico Keychron', 'Teclado mecânico RGB', 650.00, 'Periféricos', 30, NOW(), NOW()),
(UUID(), 'Monitor LG 27 4K', 'Monitor 27 polegadas 4K', 2200.00, 'Monitores', 15, NOW(), NOW()),
(UUID(), 'Cadeira Gamer DXRacer', 'Cadeira ergonômica para gamers', 1800.00, 'Móveis', 8, NOW(), NOW());