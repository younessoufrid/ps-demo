ALTER TABLE article DROP COLUMN reference;

ALTER TABLE article_occurrence DROP COLUMN designation;
ALTER TABLE a_order_artocc
    ADD COLUMN designation character varying(256);
