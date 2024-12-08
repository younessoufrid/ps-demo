package com.demo.portailsaisie.backend.core.dto;


import jakarta.annotation.Nullable;
import org.hibernate.annotations.Bag;
import org.immutables.value.Value;

import java.util.Date;

@Value.Immutable
public interface AffairDto {
    Long getId();
    String getReference();
    @Nullable
    String getLabel();
    @Nullable
    Date getStartService();
    @Nullable
    Date getEndService();
}
