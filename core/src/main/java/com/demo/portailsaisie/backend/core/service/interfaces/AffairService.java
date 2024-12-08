package com.demo.portailsaisie.backend.core.service.interfaces;

import com.demo.portailsaisie.backend.core.domain.Affair;
import com.demo.portailsaisie.backend.core.domain.Client;
import com.demo.portailsaisie.backend.core.domain.Order;
import com.demo.portailsaisie.backend.core.dto.AffairDto;

import java.util.List;

public interface AffairService extends GenericService<Affair> {
    Affair addClient(Affair affair, Client client);
    Affair addOrder(Affair affair, Order order);

    AffairDto findById(Long id);
    List<AffairDto> findByClientId(Long id);
    List<AffairDto> findByClientResponsibilityCenterReference(String reference);
    List<AffairDto> findByOrderId(Long id);
}