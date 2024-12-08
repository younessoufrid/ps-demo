package com.demo.portailsaisie.backend.core.service.v1;

import com.demo.portailsaisie.backend.core.domain.Affair;
import com.demo.portailsaisie.backend.core.domain.Client;
import com.demo.portailsaisie.backend.core.domain.Order;
import com.demo.portailsaisie.backend.core.dto.AffairDto;
import com.demo.portailsaisie.backend.core.mapping.AffairMapper;
import com.demo.portailsaisie.backend.core.repository.AffairRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.AffairService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AffairServiceImpl implements AffairService {

    private final AffairRepository repository;
    private final AffairMapper mapper;


    @Override
    public AffairDto findById(Long id) {
        return mapper.map(
                repository.findById(id)
                        .orElseThrow()
        );
    }

    @Override
    public List<AffairDto> findByClientId(Long id) {
        return repository.findByClients_id(id)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public List<AffairDto> findByClientResponsibilityCenterReference(String reference) {
        return repository.findByClients_ResponsibilityCenters_reference(reference)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public List<AffairDto> findByOrderId(Long id) {
        return repository.findByOrders_id(id)
                .stream()
                .map(mapper::map)
                .toList();
    }

    public Affair save(Affair affair) {
        return repository.save(affair);
    }
    public Affair saveIfNotExist(Affair affair) {
        if (affair.getReference() != null) {
            Optional<Affair> existingAffair = repository.findByReference(affair.getReference());
            return existingAffair.orElseGet(() -> repository.save(affair));
        }
        return save(affair);
    }

    public Affair addClient(Affair affair, Client client) {
        if (affair.getClients() == null) {
            affair.setClients(new ArrayList<>());
        }
        boolean clientAffairExist = affair.getClients().stream()
                .anyMatch(c -> c.getReference().equals(client.getReference()));

        if (!clientAffairExist) {
            affair.getClients().add(client);
            return save(affair);
        }
        return affair;
    }

    public Affair addOrder(Affair affair, Order order) {
        if (affair.getOrders() == null) {
            affair.setOrders(new ArrayList<>());
        }
        boolean orderAffairExist = affair.getOrders().stream()
                .anyMatch(o -> o.getNumber().equals(order.getNumber()));
        if (!orderAffairExist) {
            affair.getOrders().add(order);
            return save(affair);
        }
        return affair;
    }
}
