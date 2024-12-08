package com.demo.portailsaisie.backend.core.repository;

import com.demo.portailsaisie.backend.core.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByResponsibilityCenters_reference(String reference);
    List<Client> findByOrders_id(Long id);
    List<Client> findByAffairs_id(Long id);
    Optional<Client> findByReference(String reference);
}
