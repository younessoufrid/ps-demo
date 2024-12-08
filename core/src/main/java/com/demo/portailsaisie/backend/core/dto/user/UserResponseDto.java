package com.demo.portailsaisie.backend.core.dto.user;

import jakarta.annotation.Nullable;
import org.immutables.value.Value;

@Value.Immutable
public interface UserResponseDto {
    Long getId();
    @Nullable
    String getFirstname();
    @Nullable
    String getLastname();
    String getUsername();
}
