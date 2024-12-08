package v1;

import com.demo.portailsaisie.backend.core.domain.Project;
import com.demo.portailsaisie.backend.core.repository.ProjectRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.ProjectService;
import com.demo.portailsaisie.backend.core.service.v1.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import v1.config.TestConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
public class ProjectServiceTest {

    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    private final String EXISTING_PROJECT_REFERENCE = "project_ref_1";
    private final String NON_EXISTING_PROJECT_REFERENCE = "project_ref_2";
    private final Long ID = 1000L;


    @BeforeAll
    public void init() {
        projectService = new ProjectServiceImpl(projectRepository);

        Project existingProject = Project.builder()
                .reference(EXISTING_PROJECT_REFERENCE)
                .build();

        when(projectRepository.findByReference(EXISTING_PROJECT_REFERENCE))
                .thenReturn(Optional.of(existingProject));
        when(projectRepository.findByReference(NON_EXISTING_PROJECT_REFERENCE))
                .thenReturn(Optional.empty());
        when(projectRepository.save(any(Project.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void saveIfNotExistWithProjectExists() {
        Project project = Project.builder()
                .id(ID)
                .reference(EXISTING_PROJECT_REFERENCE)
                .build();
        Project savedProject = projectService.saveIfNotExist(project);

        assertEquals(project.getReference(), savedProject.getReference());
    }

    @Test
    void saveIfNotExistWithProjectNotExists() {
        Project project = Project.builder()
                .reference(NON_EXISTING_PROJECT_REFERENCE)
                .build();
        Project savedProject = projectService.saveIfNotExist(project);

        assertEquals(project, savedProject);
    }

}
