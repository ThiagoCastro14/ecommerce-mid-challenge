CREATE INDEX idx_orders_user_id ON tb_orders(user_id);
CREATE INDEX idx_orders_created_at ON tb_orders(created_at);
CREATE INDEX idx_itemorder_product_id ON tb_item_order(product_id);
CREATE INDEX idx_product_category ON tb_products(category);
