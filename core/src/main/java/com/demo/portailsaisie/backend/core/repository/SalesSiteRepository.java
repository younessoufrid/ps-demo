package com.demo.portailsaisie.backend.core.repository;

import com.demo.portailsaisie.backend.core.domain.SalesSite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesSiteRepository extends JpaRepository<SalesSite, Long> {
    Optional<SalesSite> findByReference(String reference);
}
