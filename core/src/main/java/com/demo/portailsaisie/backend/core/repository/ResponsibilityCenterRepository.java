package com.demo.portailsaisie.backend.core.repository;

import com.demo.portailsaisie.backend.core.domain.ResponsibilityCenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResponsibilityCenterRepository extends JpaRepository<ResponsibilityCenter, Long> {
    Optional<ResponsibilityCenter> findByReference(String reference);
    List<ResponsibilityCenter> findBySalesSites_Reference(String reference);
}
