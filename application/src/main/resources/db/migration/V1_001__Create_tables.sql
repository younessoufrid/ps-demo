CREATE TABLE a_order_artocc (
                                        ligne double precision,
                                        price_net_ht double precision,
                                        quantity integer,
                                        unite_vente character varying(5),
                                        id bigint NOT NULL,
                                        id_article_occurrence bigint NOT NULL,
                                        id_order bigint NOT NULL,
                                        text_ligne character varying(255),
                                        type_livraison character varying(255),
                                        CONSTRAINT a_order_artocc_unite_vente_check CHECK (((unite_vente)::text = ANY ((ARRAY['UN'::character varying, 'CEN'::character varying, 'MIL'::character varying])::text[])))
);

--
-- Name: a_order_artocc_id_seq; Type: SEQUENCE;
--

CREATE SEQUENCE a_order_artocc_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: a_order_artocc_id_seq; Type: SEQUENCE OWNED BY;
--

ALTER SEQUENCE a_order_artocc_id_seq OWNED BY a_order_artocc.id;


--
-- Name: a_responsibility_center_client; Type: TABLE;
--

CREATE TABLE a_responsibility_center_client (
                                                        id_client bigint NOT NULL,
                                                        id_responsibility_center bigint NOT NULL
);

--
-- Name: a_responsibility_center_sales_site; Type: TABLE;
--

CREATE TABLE a_responsibility_center_sales_site (
                                                            id_responsibility_center bigint NOT NULL,
                                                            id_sales_site bigint NOT NULL
);


--
-- Name: a_user_authorities; Type: TABLE;
--

CREATE TABLE a_user_authorities (
                                            id_authority bigint NOT NULL,
                                            id_user bigint NOT NULL
);


--
-- Name: a_user_responsibility_center; Type: TABLE;
--

CREATE TABLE a_user_responsibility_center (
                                                      id_responsibility_center bigint NOT NULL,
                                                      id_user bigint NOT NULL
);


--
-- Name: affair; Type: TABLE;
--

CREATE TABLE affair (
                                end_service timestamp(6) without time zone,
                                id bigint NOT NULL,
                                id_client bigint,
                                start_service timestamp(6) without time zone,
                                reference character varying(64),
                                label character varying(128)
);


--
-- Name: affair_id_seq; Type: SEQUENCE;
--

CREATE SEQUENCE affair_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: affair_id_seq; Type: SEQUENCE OWNED BY;
--

ALTER SEQUENCE affair_id_seq OWNED BY affair.id;


--
-- Name: article; Type: TABLE;
--

CREATE TABLE article (
                                 id bigint NOT NULL,
                                 product_id bigint,
                                 reference_bu character varying(64),
                                 label character varying(128)
);


--
-- Name: article_id_seq; Type: SEQUENCE;
--

CREATE SEQUENCE article_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: article_id_seq; Type: SEQUENCE OWNED BY;
--

ALTER SEQUENCE article_id_seq OWNED BY article.id;


--
-- Name: article_occurrence; Type: TABLE;
--

CREATE TABLE article_occurrence (
                                            article_id bigint,
                                            id bigint NOT NULL,
                                            reference character varying(64),
                                            activity character varying(128),
                                            designation character varying(255)
);


--
-- Name: article_occurrence_id_seq; Type: SEQUENCE;
--

CREATE SEQUENCE article_occurrence_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: article_occurrence_id_seq; Type: SEQUENCE OWNED BY;
--

ALTER SEQUENCE article_occurrence_id_seq OWNED BY article_occurrence.id;


--
-- Name: authority; Type: TABLE;
--

CREATE TABLE authority (
                                   id bigint NOT NULL,
                                   authority character varying(64)
);


--
-- Name: authority_id_seq; Type: SEQUENCE;
--

CREATE SEQUENCE authority_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: authority_id_seq; Type: SEQUENCE OWNED BY;
--

ALTER SEQUENCE authority_id_seq OWNED BY authority.id;


--
-- Name: client; Type: TABLE;
--

CREATE TABLE client (
                                id bigint NOT NULL,
                                reference character varying(64),
                                name character varying(128)
);


--
-- Name: client_id_seq; Type: SEQUENCE;
--

CREATE SEQUENCE client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: client_id_seq; Type: SEQUENCE OWNED BY;
--

ALTER SEQUENCE client_id_seq OWNED BY client.id;


--
-- Name: order; Type: TABLE;
--

CREATE TABLE "order" (
                                 show boolean,
                                 total_price double precision,
                                 id bigint NOT NULL,
                                 id_affair bigint,
                                 number character varying(64),
                                 reference character varying(64),
                                 label character varying(128)
);


--
-- Name: order_id_seq; Type: SEQUENCE;
--

CREATE SEQUENCE order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: order_id_seq; Type: SEQUENCE OWNED BY;
--

ALTER SEQUENCE order_id_seq OWNED BY "order".id;


--
-- Name: product; Type: TABLE;
--

CREATE TABLE product (
                                 id bigint NOT NULL,
                                 reference character varying(64),
                                 label character varying(128)
);


--
-- Name: product_id_seq; Type: SEQUENCE;
--

CREATE SEQUENCE product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: product_id_seq; Type: SEQUENCE OWNED BY;
--

ALTER SEQUENCE product_id_seq OWNED BY product.id;


--
-- Name: project; Type: TABLE;
--

CREATE TABLE project (
                                 id bigint NOT NULL,
                                 reference character varying(64),
                                 label character varying(128)
);


--
-- Name: project_id_seq; Type: SEQUENCE;
--

CREATE SEQUENCE project_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: project_id_seq; Type: SEQUENCE OWNED BY;
--

ALTER SEQUENCE project_id_seq OWNED BY project.id;


--
-- Name: responsibility_center; Type: TABLE;
--

CREATE TABLE responsibility_center (
                                               id bigint NOT NULL,
                                               reference character varying(64),
                                               label character varying(128)
);


--
-- Name: responsibility_center_id_seq; Type: SEQUENCE;
--

CREATE SEQUENCE responsibility_center_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: responsibility_center_id_seq; Type: SEQUENCE OWNED BY;
--

ALTER SEQUENCE responsibility_center_id_seq OWNED BY responsibility_center.id;


--
-- Name: sales_site; Type: TABLE;
--

CREATE TABLE sales_site (
                                    id bigint NOT NULL,
                                    reference character varying(64),
                                    label character varying(128)
);


--
-- Name: sales_site_id_seq; Type: SEQUENCE;
--

CREATE SEQUENCE sales_site_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: sales_site_id_seq; Type: SEQUENCE OWNED BY;
--

ALTER SEQUENCE sales_site_id_seq OWNED BY sales_site.id;


--
-- Name: user; Type: TABLE;
--

CREATE TABLE "user" (
                                enabled boolean,
                                id bigint NOT NULL,
                                firstname character varying(64),
                                lastname character varying(64),
                                username character varying(64)
);


--
-- Name: user_id_seq; Type: SEQUENCE;
--

CREATE SEQUENCE user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: user_id_seq; Type: SEQUENCE OWNED BY;
--

ALTER SEQUENCE user_id_seq OWNED BY "user".id;


--
-- Name: a_order_artocc id; Type: DEFAULT;
--

ALTER TABLE ONLY a_order_artocc ALTER COLUMN id SET DEFAULT nextval('a_order_artocc_id_seq'::regclass);


--
-- Name: affair id; Type: DEFAULT;
--

ALTER TABLE ONLY affair ALTER COLUMN id SET DEFAULT nextval('affair_id_seq'::regclass);


--
-- Name: article id; Type: DEFAULT;
--

ALTER TABLE ONLY article ALTER COLUMN id SET DEFAULT nextval('article_id_seq'::regclass);


--
-- Name: article_occurrence id; Type: DEFAULT;
--

ALTER TABLE ONLY article_occurrence ALTER COLUMN id SET DEFAULT nextval('article_occurrence_id_seq'::regclass);


--
-- Name: authority id; Type: DEFAULT;
--

ALTER TABLE ONLY authority ALTER COLUMN id SET DEFAULT nextval('authority_id_seq'::regclass);


--
-- Name: client id; Type: DEFAULT;
--

ALTER TABLE ONLY client ALTER COLUMN id SET DEFAULT nextval('client_id_seq'::regclass);


--
-- Name: order id; Type: DEFAULT;
--

ALTER TABLE ONLY "order" ALTER COLUMN id SET DEFAULT nextval('order_id_seq'::regclass);


--
-- Name: product id; Type: DEFAULT;
--

ALTER TABLE ONLY product ALTER COLUMN id SET DEFAULT nextval('product_id_seq'::regclass);


--
-- Name: project id; Type: DEFAULT;
--

ALTER TABLE ONLY project ALTER COLUMN id SET DEFAULT nextval('project_id_seq'::regclass);


--
-- Name: responsibility_center id; Type: DEFAULT;
--

ALTER TABLE ONLY responsibility_center ALTER COLUMN id SET DEFAULT nextval('responsibility_center_id_seq'::regclass);


--
-- Name: sales_site id; Type: DEFAULT;
--

ALTER TABLE ONLY sales_site ALTER COLUMN id SET DEFAULT nextval('sales_site_id_seq'::regclass);


--
-- Name: user id; Type: DEFAULT;
--

ALTER TABLE ONLY "user" ALTER COLUMN id SET DEFAULT nextval('user_id_seq'::regclass);


--
-- Data for Name: a_order_artocc; Type: TABLE DATA;
--

COPY a_order_artocc (ligne, price_net_ht, quantity, unite_vente, id, id_article_occurrence, id_order, text_ligne, type_livraison) FROM stdin;
\.


--
-- Data for Name: a_responsibility_center_client; Type: TABLE DATA;
--

COPY a_responsibility_center_client (id_client, id_responsibility_center) FROM stdin;
\.


--
-- Data for Name: a_responsibility_center_sales_site; Type: TABLE DATA;
--

COPY a_responsibility_center_sales_site (id_responsibility_center, id_sales_site) FROM stdin;
\.


--
-- Data for Name: a_user_authorities; Type: TABLE DATA;
--

COPY a_user_authorities (id_authority, id_user) FROM stdin;
\.


--
-- Data for Name: a_user_responsibility_center; Type: TABLE DATA;
--

COPY a_user_responsibility_center (id_responsibility_center, id_user) FROM stdin;
\.


--
-- Data for Name: affair; Type: TABLE DATA;
--

COPY affair (end_service, id, id_client, start_service, reference, label) FROM stdin;
\.


--
-- Data for Name: article; Type: TABLE DATA;
--

COPY article (id, product_id, reference_bu, label) FROM stdin;
\.


--
-- Data for Name: article_occurrence; Type: TABLE DATA;
--

COPY article_occurrence (article_id, id, reference, activity, designation) FROM stdin;
\.


--
-- Data for Name: authority; Type: TABLE DATA;
--

COPY authority (id, authority) FROM stdin;
\.


--
-- Data for Name: client; Type: TABLE DATA;
--

COPY client (id, reference, name) FROM stdin;
\.


--
-- Data for Name: order; Type: TABLE DATA;
--

COPY "order" (show, total_price, id, id_affair, number, reference, label) FROM stdin;
\.


--
-- Data for Name: product; Type: TABLE DATA;
--

COPY product (id, reference, label) FROM stdin;
\.


--
-- Data for Name: project; Type: TABLE DATA;
--

COPY project (id, reference, label) FROM stdin;
\.


--
-- Data for Name: responsibility_center; Type: TABLE DATA;
--

COPY responsibility_center (id, reference, label) FROM stdin;
\.


--
-- Data for Name: sales_site; Type: TABLE DATA;
--

COPY sales_site (id, reference, label) FROM stdin;
\.


--
-- Data for Name: user; Type: TABLE DATA;
--

COPY "user" (enabled, id, firstname, lastname, username) FROM stdin;
\.


--
-- Name: a_order_artocc_id_seq; Type: SEQUENCE SET;
--

SELECT pg_catalog.setval('a_order_artocc_id_seq', 1, false);


--
-- Name: affair_id_seq; Type: SEQUENCE SET;
--

SELECT pg_catalog.setval('affair_id_seq', 1, false);


--
-- Name: article_id_seq; Type: SEQUENCE SET;
--

SELECT pg_catalog.setval('article_id_seq', 1, false);


--
-- Name: article_occurrence_id_seq; Type: SEQUENCE SET;
--

SELECT pg_catalog.setval('article_occurrence_id_seq', 1, false);


--
-- Name: authority_id_seq; Type: SEQUENCE SET;
--

SELECT pg_catalog.setval('authority_id_seq', 1, false);


--
-- Name: client_id_seq; Type: SEQUENCE SET;
--

SELECT pg_catalog.setval('client_id_seq', 1, false);


--
-- Name: order_id_seq; Type: SEQUENCE SET;
--

SELECT pg_catalog.setval('order_id_seq', 1, false);


--
-- Name: product_id_seq; Type: SEQUENCE SET;
--

SELECT pg_catalog.setval('product_id_seq', 1, false);


--
-- Name: project_id_seq; Type: SEQUENCE SET;
--

SELECT pg_catalog.setval('project_id_seq', 1, false);


--
-- Name: responsibility_center_id_seq; Type: SEQUENCE SET;
--

SELECT pg_catalog.setval('responsibility_center_id_seq', 1, false);


--
-- Name: sales_site_id_seq; Type: SEQUENCE SET;
--

SELECT pg_catalog.setval('sales_site_id_seq', 1, false);


--
-- Name: user_id_seq; Type: SEQUENCE SET;
--

SELECT pg_catalog.setval('user_id_seq', 1, false);


--
-- Name: a_order_artocc a_order_artocc_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY a_order_artocc
    ADD CONSTRAINT a_order_artocc_pkey PRIMARY KEY (id);


--
-- Name: affair affair_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY affair
    ADD CONSTRAINT affair_pkey PRIMARY KEY (id);


--
-- Name: article_occurrence article_occurrence_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY article_occurrence
    ADD CONSTRAINT article_occurrence_pkey PRIMARY KEY (id);


--
-- Name: article article_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY article
    ADD CONSTRAINT article_pkey PRIMARY KEY (id);


--
-- Name: authority authority_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY authority
    ADD CONSTRAINT authority_pkey PRIMARY KEY (id);


--
-- Name: client client_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);


--
-- Name: order order_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT order_pkey PRIMARY KEY (id);


--
-- Name: product product_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: project project_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY project
    ADD CONSTRAINT project_pkey PRIMARY KEY (id);


--
-- Name: responsibility_center responsibility_center_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY responsibility_center
    ADD CONSTRAINT responsibility_center_pkey PRIMARY KEY (id);


--
-- Name: sales_site sales_site_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY sales_site
    ADD CONSTRAINT sales_site_pkey PRIMARY KEY (id);


--
-- Name: user user_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- Name: a_responsibility_center_sales_site fk1cj208hqs34y0o2nbfo2iscvd; Type: FK CONSTRAINT;
--

ALTER TABLE ONLY a_responsibility_center_sales_site
    ADD CONSTRAINT fk1cj208hqs34y0o2nbfo2iscvd FOREIGN KEY (id_responsibility_center) REFERENCES responsibility_center(id);


--
-- Name: affair fk236he1a998541vwwqlmygkgrq; Type: FK CONSTRAINT;
--

ALTER TABLE ONLY affair
    ADD CONSTRAINT fk236he1a998541vwwqlmygkgrq FOREIGN KEY (id_client) REFERENCES client(id);


--
-- Name: a_user_responsibility_center fk379so78ipj5rfj0et71ajb236; Type: FK CONSTRAINT;
--

ALTER TABLE ONLY a_user_responsibility_center
    ADD CONSTRAINT fk379so78ipj5rfj0et71ajb236 FOREIGN KEY (id_responsibility_center) REFERENCES responsibility_center(id);


--
-- Name: a_responsibility_center_sales_site fk7a4obloirbye18vap05aj7hc8; Type: FK CONSTRAINT;
--

ALTER TABLE ONLY a_responsibility_center_sales_site
    ADD CONSTRAINT fk7a4obloirbye18vap05aj7hc8 FOREIGN KEY (id_sales_site) REFERENCES sales_site(id);


--
-- Name: article fkbxy24ht14wxvuhu2fo3w4de3b; Type: FK CONSTRAINT;
--

ALTER TABLE ONLY article
    ADD CONSTRAINT fkbxy24ht14wxvuhu2fo3w4de3b FOREIGN KEY (product_id) REFERENCES product(id);


--
-- Name: article_occurrence fkcfmunkm81yag4rb1y3nrf58u1; Type: FK CONSTRAINT;
--

ALTER TABLE ONLY article_occurrence
    ADD CONSTRAINT fkcfmunkm81yag4rb1y3nrf58u1 FOREIGN KEY (article_id) REFERENCES article(id);


--
-- Name: a_user_responsibility_center fkck38w2udbk8ynjvb94n5mu0o6; Type: FK CONSTRAINT;
--

ALTER TABLE ONLY a_user_responsibility_center
    ADD CONSTRAINT fkck38w2udbk8ynjvb94n5mu0o6 FOREIGN KEY (id_user) REFERENCES "user"(id);


--
-- Name: order fkdl6uijf8o8jnrpvg140g8uy0e; Type: FK CONSTRAINT;
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT fkdl6uijf8o8jnrpvg140g8uy0e FOREIGN KEY (id_affair) REFERENCES affair(id);


--
-- Name: a_user_authorities fkeg1vy2m34d8kxsxkmydgkspb1; Type: FK CONSTRAINT;
--

ALTER TABLE ONLY a_user_authorities
    ADD CONSTRAINT fkeg1vy2m34d8kxsxkmydgkspb1 FOREIGN KEY (id_user) REFERENCES "user"(id);


--
-- Name: a_responsibility_center_client fki9cm28h629ni0lqv29w0qlrjl; Type: FK CONSTRAINT;
--

ALTER TABLE ONLY a_responsibility_center_client
    ADD CONSTRAINT fki9cm28h629ni0lqv29w0qlrjl FOREIGN KEY (id_client) REFERENCES client(id);


--
-- Name: a_responsibility_center_client fklhw4k4fb81xm67g1tlykmdvr6; Type: FK CONSTRAINT;
--

ALTER TABLE ONLY a_responsibility_center_client
    ADD CONSTRAINT fklhw4k4fb81xm67g1tlykmdvr6 FOREIGN KEY (id_responsibility_center) REFERENCES responsibility_center(id);


--
-- Name: a_order_artocc fklox30c23irkw46j1gth22lpgp; Type: FK CONSTRAINT;
--

ALTER TABLE ONLY a_order_artocc
    ADD CONSTRAINT fklox30c23irkw46j1gth22lpgp FOREIGN KEY (id_order) REFERENCES "order"(id);


--
-- Name: a_order_artocc fknshiqebfnffyrsl0w4n2f33le; Type: FK CONSTRAINT;
--

ALTER TABLE ONLY a_order_artocc
    ADD CONSTRAINT fknshiqebfnffyrsl0w4n2f33le FOREIGN KEY (id_article_occurrence) REFERENCES article_occurrence(id);


--
-- Name: a_user_authorities fkqohtlbb4ldip5g1xrvxrta4g7; Type: FK CONSTRAINT;
--

ALTER TABLE ONLY a_user_authorities
    ADD CONSTRAINT fkqohtlbb4ldip5g1xrvxrta4g7 FOREIGN KEY (id_authority) REFERENCES authority(id);
