package com.demo.portailsaisie.backend.core.dto;

import jakarta.annotation.Nullable;
import org.immutables.value.Value;

@Value.Immutable
public interface ArticleOccurrenceDto {

    Long getId();
    @Nullable
    String getReference();
    @Nullable
    String getActivity();
}
