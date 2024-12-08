package com.demo.portailsaisie.backend.utils.security;

import lombok.Getter;

@Getter
public enum KeycloakJwtFormat {
    EXP("exp"),
    IAT("iat"),
    JTI("jti"),
    ISS("iss"),
    AUD("aud"),
    SUB("sub"),
    TYP("typ"),
    AZP("azp"),
    SESSION_STATE("session_state"),
    ACR("acr"),
    ALLOWED_ORIGINS("allowed-origins"),
    REALM_ACCESS("realm_access"),
    RESOURCE_ACCESS("resource_access"),
    SCOPE("scope"),
    SID("sid"),
    EMAIL_VERIFIED("email_verified"),
    NAME("name"),
    PREFERRED_USERNAME("preferred_username"),
    GIVEN_NAME("given_name"),
    FAMILY_NAME("family_name"),
    EMAIL("email");

    private final String value;

    KeycloakJwtFormat(String value){
        this.value = value;
    }
}
