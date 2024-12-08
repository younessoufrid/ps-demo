package com.demo.portailsaisie.backend.core.dto;

import org.immutables.value.Value;

@Value.Immutable
public interface ArticleDto {

    Long getId();
    String getReferenceBu();
    String getLabel();

}
