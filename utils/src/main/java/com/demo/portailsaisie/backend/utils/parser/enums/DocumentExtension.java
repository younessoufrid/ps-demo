package com.demo.portailsaisie.backend.utils.parser.enums;

public enum DocumentExtension {

    EXCEL(DocumentExtension.Values.EXCEL),
    OLD_EXCEL(DocumentExtension.Values.OLD_EXCEL),
    CSV(DocumentExtension.Values.CSV);

    DocumentExtension(String value) {
        if (!this.name().equals(value))
            throw new IllegalArgumentException("Incorrect use of DocumentExtension");
    }

    public static class Values {
        public static final String EXCEL = ".xlsx";
        public static final String OLD_EXCEL = ".xls";
        public static final String CSV = ".csv";

        private Values(){}
    }
}
