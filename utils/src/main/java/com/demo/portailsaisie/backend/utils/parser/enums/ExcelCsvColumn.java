package com.demo.portailsaisie.backend.utils.parser.enums;

public enum ExcelCsvColumn {
    SALES_SITE(Values.SALES_SITE),
    CLIENT_COMMAND(Values.CLIENT_COMMAND),
    CLIENT_NAME(Values.CLIENT_NAME),
    DATE(Values.DATE),
    SERVICE_START_DATE(Values.SERVICE_START_DATE),
    SERVICE_END_DATE(Values.SERVICE_END_DATE),
    AFFAIR(Values.AFFAIR),
    COMMAND_NUMBER(Values.COMMAND_NUMBER),
    COMMAND_REFERENCE(Values.ORDER_REFERENCE),
    LINE(Values.LINE),
    ARTICLE(Values.ARTICLE),
    BU_REFERENCE(Values.BU_REFERENCE),
    DESIGNATION(Values.DESIGNATION),
    LINE_TEXT(Values.LINE_TEXT),
    QUANTITY(Values.QUANTITY),
    SALE_UNIT(Values.SALE_UNIT),
    NET_PRICE_HT(Values.NET_PRICE_HT),
    DELIVERY_TYPE(Values.DELIVERY_TYPE),
    CDR(Values.CDR),
    ACTIVITY(Values.ACTIVITY),
    PROJECT(Values.PROJECT),
    PRODUCT(Values.PRODUCT),
    CLIENT(Values.CLIENT);

    ExcelCsvColumn(String value) {
        if (!this.name().equals(value))
            throw new IllegalArgumentException("Incorrect use of Columns");
    }

    public static class Values {
        public static final String SALES_SITE = "Site vente";
        public static final String CLIENT_COMMAND = "Client commande";
        public static final String CLIENT_NAME = "Nom client commande";
        public static final String DATE = "Date";
        public static final String SERVICE_START_DATE = "Debut prestation";
        public static final String SERVICE_END_DATE = "Fin prestation";
        public static final String AFFAIR = "Affaire";
        public static final String COMMAND_NUMBER = "No commande";
        public static final String ORDER_REFERENCE = "Ref commande client";
        public static final String LINE = "Ligne";
        public static final String ARTICLE = "Article";
        public static final String BU_REFERENCE = "Ref BU";
        public static final String DESIGNATION = "Designation";
        public static final String LINE_TEXT = "Texte ligne";
        public static final String QUANTITY = "Quantite";
        public static final String SALE_UNIT = "Unite vente";
        public static final String NET_PRICE_HT = "Prix net HT";
        public static final String DELIVERY_TYPE = "Type livraison";
        public static final String CDR = "CDR";
        public static final String ACTIVITY = "Activite";
        public static final String PROJECT = "Projet";
        public static final String PRODUCT = "Produit";
        public static final String CLIENT = "Client";

        private Values(){}
    }

}
