package com.demo.portailsaisie.backend.core.dto.summary;

import com.demo.portailsaisie.backend.core.enums.UniteVente;
import jakarta.annotation.Nullable;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface SummaryDto {
    @Nullable
    String getDesignation();

    @Nullable
    Double getPriceNetHT();

    @Nullable
    UniteVente getUniteVente();

    @Nullable
    Long getLine();

    @Nullable
    Double getTotalMonthQte();

    @Nullable
    Double getTotalPrice();

    @Nullable
    Boolean getSoumis();

    @Nullable
    List<SummaryItemDto> getSummaryItems();
}
