-- a_order_artocc table
ALTER TABLE a_order_artocc ALTER COLUMN quantity TYPE FLOAT  USING quantity::float;
ALTER TABLE a_order_artocc ADD COLUMN show BOOLEAN;
ALTER TABLE a_order_artocc ADD COLUMN date TIMESTAMP;
ALTER TABLE a_order_artocc ALTER COLUMN text_ligne TYPE VARCHAR(300);
ALTER TABLE a_order_artocc ALTER COLUMN ligne TYPE  BIGINT USING quantity::bigint;


-- order table
ALTER TABLE "order" DROP COLUMN show;
