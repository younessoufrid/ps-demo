package com.demo.portailsaisie.backend.core.dto;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface ClientDto {

    Long getId();
    String getReference();
    String getName();


}
