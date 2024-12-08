package com.demo.portailsaisie.backend.core.dto.saisie;

import com.demo.portailsaisie.backend.core.enums.UniteVente;
import jakarta.annotation.Nullable;
import org.immutables.value.Value;

import java.util.Date;

@Value.Immutable
public interface SaisieDto {
     Long getId();
     @Nullable
     String getTextLigne();
     @Nullable
     String getDesignation();
     Double getQuantity();
     Boolean getShow();
     Date getDate();
     Long getLine();
     Long getIdOrder();
     Double getPriceNetHT();
     UniteVente getUniteVente();
     @Nullable
     Double getTotalMonthQte();
     @Nullable
     Double getTotalPrice();
}
