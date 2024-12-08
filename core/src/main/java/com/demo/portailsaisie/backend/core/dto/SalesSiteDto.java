package com.demo.portailsaisie.backend.core.dto;

import jakarta.annotation.Nullable;
import org.immutables.value.Value;

@Value.Immutable
public interface SalesSiteDto {

    Long getId();

    String getReference();

    @Nullable
    String getLabel();
}
