package com.demo.portailsaisie.backend.core.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.immutables.value.Value;

@Value.Immutable
public interface AuthRequestDto {
     String getUsername();
     String getPassword();
}
