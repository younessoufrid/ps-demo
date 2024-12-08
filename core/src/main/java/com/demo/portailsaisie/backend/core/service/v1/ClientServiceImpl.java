package com.demo.portailsaisie.backend.core.service.v1;


import com.demo.portailsaisie.backend.core.domain.Client;
import com.demo.portailsaisie.backend.core.dto.ClientDto;
import com.demo.portailsaisie.backend.core.mapping.ClientMapper;
import com.demo.portailsaisie.backend.core.repository.ClientRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;

    @Override
    public ClientDto findById(Long id) {
        return mapper.map(
                repository.findById(id)
                        .orElseThrow()
        );
    }

    @Override
    public List<ClientDto> findByResponsibilityCenterReference(String reference) {
        return repository.findByResponsibilityCenters_reference(reference)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public List<ClientDto> findByOrderId(Long id) {
        return repository.findByOrders_id(id)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public List<ClientDto> findByAffairId(Long id) {
        return repository.findByAffairs_id(id)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public Client save(Client client) {
        return repository.save(client);
    }

    @Override
    public Client saveIfNotExist(Client client) {
        if (client.getReference() != null) {
            Optional<Client> existingClient = repository.findByReference(client.getReference());
            return existingClient.orElseGet(() -> repository.save(client));
        }
        return save(client);
    }
}