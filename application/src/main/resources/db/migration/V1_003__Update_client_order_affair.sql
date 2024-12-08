ALTER TABLE "order" ADD COLUMN id_client bigint;
ALTER TABLE ONLY "order"
    ADD CONSTRAINT fk_order_client FOREIGN KEY (id_client) REFERENCES client(id);
