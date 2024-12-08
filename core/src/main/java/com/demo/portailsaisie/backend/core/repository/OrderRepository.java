package com.demo.portailsaisie.backend.core.repository;

import com.demo.portailsaisie.backend.core.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByClient_ResponsibilityCenters_reference(String reference);
    List<Order> findByClient_id(Long id);
    List<Order> findByAffair_id(Long id);
    List<Order> findByAffair_idAndClient_id(Long AffairId, Long ClientId);
    Optional<Order> findByNumber(String reference);
}
