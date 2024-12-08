package com.demo.portailsaisie.backend.core.repository;

import com.demo.portailsaisie.backend.core.domain.Affair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AffairRepository extends JpaRepository<Affair, Long> {

    List<Affair> findByClients_ResponsibilityCenters_reference(String reference);
    List<Affair> findByClients_id(Long id);
    List<Affair> findByOrders_id(Long id);
    Optional<Affair> findByReference(String reference);
}
