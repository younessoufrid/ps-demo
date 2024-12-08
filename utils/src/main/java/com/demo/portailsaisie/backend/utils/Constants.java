package com.demo.portailsaisie.backend.utils;

public class Constants {
    public static final String MESSAGE_AUTHENTICATION_FAILED = "Authentication failed.";

    public static final String JWT_CLAIM_USER_ROLES = "grantedAuthorities";
    public static final String JWT_SOURCE_CLAIM = "source";
    public static final String JWT_SOURCE_OIDC = "OIDC";

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_AUTHORIZATION_PREFIX = "Bearer ";

    public static final String PUBLIC_KEY_START = "-----BEGIN PUBLIC KEY-----";
    public static final String PUBLIC_KEY_END = "-----END PUBLIC KEY-----";

    public static final String PRIVATE_KEY_START = "-----BEGIN PRIVATE KEY-----";
    public static final String PRIVATE_KEY_END = "-----END PRIVATE KEY-----";

    public static final String EXCEL_MEDIA_TYPE = "application/vnd.ms-excel";
    public static final String CSV_MEDIA_TYPE = "text/csv";

    public static final String ROLE_PREFIX = "ROLE_";
    public static final String KEYCLOAK_PUBLIC_KEY = "public_key";
}
