package com.demo.portailsaisie.backend.core.repository;

import com.demo.portailsaisie.backend.core.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByReference(String reference);
}
