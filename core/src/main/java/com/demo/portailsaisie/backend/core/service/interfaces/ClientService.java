package com.demo.portailsaisie.backend.core.service.interfaces;

import com.demo.portailsaisie.backend.core.domain.Client;
import com.demo.portailsaisie.backend.core.dto.ClientDto;

import java.util.List;

public interface ClientService extends GenericService<Client>{
    ClientDto findById(Long id);
    List<ClientDto> findByResponsibilityCenterReference(String reference);
    List<ClientDto> findByOrderId(Long id);
    List<ClientDto> findByAffairId(Long id);
}
