package com.demo.portailsaisie.backend.core.dto;

import jakarta.annotation.Nullable;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface ResponsibilityCenterDto {

    Long getId();

    String getReference();

    @Nullable
    String getLabel();

}
