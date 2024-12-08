-- Adding a new column reference with NOT NULL constraint into Article.
ALTER TABLE article
    ADD COLUMN reference character varying(64) NOT NULL UNIQUE;


-- Adding a reference to the project Table in order table.
ALTER TABLE "order"
    ADD COLUMN id_project bigint;

ALTER TABLE "order"
    ADD CONSTRAINT FK_ORDER_REF_PROJECT
        FOREIGN KEY (id_project)
            REFERENCES project(id);


-- turn relationship between client and affair from OneToMany into ManyToMany
    -- drop id_client foreign key constraint.
ALTER TABLE affair DROP CONSTRAINT fk236he1a998541vwwqlmygkgrq;
ALTER TABLE affair DROP COLUMN id_client;
    -- create relationship table between client and affair.
CREATE TABLE a_client_affair (
    id_client bigint NOT NULL,
    id_affair bigint NOT NULL,
    PRIMARY KEY (id_client, id_affair)
);
ALTER TABLE a_client_affair
    ADD CONSTRAINT FK_A_CLIENT_AFFAIR_REF_CLIENT
        FOREIGN KEY (id_client)
            REFERENCES client(id);
ALTER TABLE a_client_affair
    ADD CONSTRAINT FK_A_CLIENT_AFFAIR_REF_AFFAIR
        FOREIGN KEY (id_affair)
            REFERENCES affair(id);

--
ALTER TABLE a_order_artocc
    ADD CONSTRAINT uq_ligne_id_order UNIQUE (ligne, id_order);

-- adding reference to project
ALTER TABLE project
    ADD CONSTRAINT uq_project_reference UNIQUE (reference);
ALTER TABLE project
    ALTER COLUMN reference SET NOT NULL;

-- adding pk to a_responsibility_center_client
ALTER TABLE a_responsibility_center_client
    ADD CONSTRAINT pk_responsibility_center_client PRIMARY KEY (id_client, id_responsibility_center);

-- adding pk to a_responsibility_center_sales_site
ALTER TABLE a_responsibility_center_sales_site
    ADD CONSTRAINT pk_responsibility_center_sales_site PRIMARY KEY (id_responsibility_center, id_sales_site);