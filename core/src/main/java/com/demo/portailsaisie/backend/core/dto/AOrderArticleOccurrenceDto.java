package com.demo.portailsaisie.backend.core.dto;

import com.demo.portailsaisie.backend.core.enums.UniteVente;
import jakarta.annotation.Nullable;
import org.immutables.value.Value;

import java.util.Date;

@Value.Immutable
public interface AOrderArticleOccurrenceDto {

    Long getId();
    @Nullable
    Double getQuantity();
    Double getPriceNetHT();
    Long getLine();
    @Nullable
    String getTextLigne();
    @Nullable
    String getDesignation();
    @Nullable
    UniteVente getUniteVente();
    @Nullable
    String getTypeLivraison();
    @Nullable
    Date getDate();
    ArticleOccurrenceDto getArticleOccurrence();
    OrderDto getOrder();
    Boolean getShow();
    @Nullable
    Double getTotalMonthQte();
    @Nullable
    Double getTotalPrice();

    Boolean getSoumis();
}
