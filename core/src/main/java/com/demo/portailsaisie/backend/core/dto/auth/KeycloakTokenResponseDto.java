package com.demo.portailsaisie.backend.core.dto.auth;

import org.immutables.value.Value;

@Value.Immutable
public interface KeycloakTokenResponseDto {
    String getAccess_token();
    String getRefresh_token();
    String getExpires_in();
    String getRefresh_expires_in();
    String getScope();
    String getToken_type();
}
