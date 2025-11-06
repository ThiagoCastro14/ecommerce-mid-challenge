-- Índice composto para otimizar queries de relatórios que filtram por status e data
CREATE INDEX idx_orders_status_created_at ON tb_orders(status, created_at);

-- Índice para otimizar queries que agrupam por usuário e status
CREATE INDEX idx_orders_user_status ON tb_orders(user_id, status);