package com.demo.portailsaisie.backend.core.dto;

import org.immutables.value.Value;

@Value.Immutable
public interface AuthorityDto {

    Long getId();
    String getRole();
}
