package com.demo.portailsaisie.backend.core.dto.auth;

import org.immutables.value.Value;

@Value.Immutable
public interface AuthResponseDto {
    String getUsername();
    String getToken();
}
