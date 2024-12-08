package com.demo.portailsaisie.backend.core.service.v1;

import com.demo.portailsaisie.backend.core.domain.Client;
import com.demo.portailsaisie.backend.core.domain.ResponsibilityCenter;
import com.demo.portailsaisie.backend.core.domain.SalesSite;
import com.demo.portailsaisie.backend.core.dto.ResponsibilityCenterDto;
import com.demo.portailsaisie.backend.core.mapping.ResponsibilityCenterMapper;
import com.demo.portailsaisie.backend.core.repository.ResponsibilityCenterRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.ResponsibilityCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResponsibilityCenterServiceImpl implements ResponsibilityCenterService {

    private final ResponsibilityCenterRepository repository;

    private final ResponsibilityCenterMapper mapper;

    public ResponsibilityCenterDto findById(Long id) {
        return mapper.map(repository.findById(id).orElseThrow());
    }

    public ResponsibilityCenterDto findByReference(String reference) {
        return mapper.map(repository.findByReference(reference).orElseThrow());
    }

    @Override
    public List<ResponsibilityCenterDto> findAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    @Override
    public List<ResponsibilityCenterDto> findBySaleSiteRef(String saleSiteRef) {
        return repository.findBySalesSites_Reference(saleSiteRef)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public ResponsibilityCenter save(ResponsibilityCenter responsibilityCenter) {
        return repository.save(responsibilityCenter);
    }

    @Override
    public ResponsibilityCenter saveIfNotExist(ResponsibilityCenter responsibilityCenter) {
        if (responsibilityCenter.getReference() != null) {
            Optional<ResponsibilityCenter> existingResponsibilityCenter = repository.findByReference(responsibilityCenter.getReference());
            return existingResponsibilityCenter.orElseGet(() -> repository.save(responsibilityCenter));
        }
        return save(responsibilityCenter);
    }

    @Override
    public ResponsibilityCenter addSalesSite(ResponsibilityCenter responsibilityCenter, SalesSite salesSite) {

        if (responsibilityCenter.getSalesSites() == null) {
            responsibilityCenter.setSalesSites(new ArrayList<>());
        }
        boolean siteRcExist = responsibilityCenter.getSalesSites().stream()
                .anyMatch(s -> s.getReference().equals(salesSite.getReference()));
        if (!siteRcExist) {
            responsibilityCenter.getSalesSites().add(salesSite);
            return save(responsibilityCenter);
        }
        return responsibilityCenter;
    }

    @Override
    public ResponsibilityCenter addClient(ResponsibilityCenter responsibilityCenter, Client client) {

        if (responsibilityCenter.getClients() == null) {
            responsibilityCenter.setClients(new ArrayList<>());
        }
        boolean clientRcExist = responsibilityCenter.getClients().stream()
                .anyMatch(c -> c.getReference().equals(client.getReference()));
        if (!clientRcExist) {
            responsibilityCenter.getClients().add(client);
            return save(responsibilityCenter);
        }
        return responsibilityCenter;

    }

}
