ALTER TABLE a_order_artocc ADD COLUMN init boolean;
ALTER TABLE a_order_artocc DROP CONSTRAINT uq_ligne_id_order;
ALTER TABLE a_order_artocc ADD CONSTRAINT uq_ligne_id_order_date unique (date, id_order, ligne);