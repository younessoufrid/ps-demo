package com.demo.portailsaisie.backend.core.dto.search;

import com.demo.portailsaisie.backend.core.enums.Action;
import jakarta.annotation.Nullable;
import org.immutables.value.Value;

import java.util.Date;

@Value.Immutable
public interface FilterRequestDto {
    @Nullable
    String getSaleSiteRef();
    @Nullable
    String getResponsibilityCenterRef();
    @Nullable
    Long getClientId();
    @Nullable
    Long getAffairId();
    @Nullable
    Long getOrderId();
    @Nullable
    Date getDate();
    Action getAction();

}
