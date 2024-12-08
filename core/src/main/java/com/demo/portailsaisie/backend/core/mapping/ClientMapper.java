package com.demo.portailsaisie.backend.core.mapping;


import com.demo.portailsaisie.backend.core.domain.Client;
import com.demo.portailsaisie.backend.core.dto.ClientDto;
import org.mapstruct.Mapper;

@Mapper(uses = {ResponsibilityCenterMapper.class})
public interface ClientMapper {

    ClientDto map(Client client);
}
