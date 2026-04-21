package com.example.redesocial_bancodesangue.model;

public enum TipoSanguineo {
    APositivo("A+"),
    ANegtivo("A-"),
    BPositivo("B+"),
    BNegativo("B-"),
    ABPositivo("AB+"),
    ABNegativo("AB-"),
    OPositivo("O+"),
    ONegativo("O-");

    private final String s;

    TipoSanguineo(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }
}
