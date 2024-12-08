package com.demo.portailsaisie.backend.core.dto.summary;


import jakarta.annotation.Nullable;
import org.immutables.value.Value;

import java.util.Date;

@Value.Immutable
public interface SummaryItemDto {
    @Nullable
    Double getQuantity();
    Date getDate();
}
