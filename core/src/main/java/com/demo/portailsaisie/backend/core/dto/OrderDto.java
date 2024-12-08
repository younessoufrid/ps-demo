package com.demo.portailsaisie.backend.core.dto;

import jakarta.annotation.Nullable;
import org.immutables.value.Value;

@Value.Immutable
public interface OrderDto {

    Long getId();

    @Nullable
    String getReference();

    @Nullable
    String getLabel();

    String getNumber();

    @Value.Default
    default Boolean isShow(){
        return true;
    }

    Double getTotalPrice();
}
