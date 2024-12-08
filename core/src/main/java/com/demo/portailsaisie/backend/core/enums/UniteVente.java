package com.demo.portailsaisie.backend.core.enums;

public enum UniteVente {
    UN(1),
    CEN(100),
    MIL(1000);
    private int value;

    UniteVente(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
