package com.demo.portailsaisie.backend.core.service.v1;

import com.demo.portailsaisie.backend.core.domain.Project;
import com.demo.portailsaisie.backend.core.repository.ProjectRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;

    @Override
    public Project save(Project project) {
        return repository.save(project);
    }

    @Override
    public Project saveIfNotExist(Project project) {
        if (project.getReference() != null) {
            Optional<Project> existingProject = repository.findByReference(project.getReference());
            return existingProject.orElseGet(() -> repository.save(project));
        }
        return save(project);
    }

}
