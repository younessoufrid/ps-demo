package v1;

import com.demo.portailsaisie.backend.core.domain.ResponsibilityCenter;
import com.demo.portailsaisie.backend.core.mapping.ResponsibilityCenterMapper;
import com.demo.portailsaisie.backend.core.repository.ResponsibilityCenterRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.ResponsibilityCenterService;
import com.demo.portailsaisie.backend.core.service.v1.ResponsibilityCenterServiceImpl;
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
public class ResponsibilityCenterServiceTest {

    private ResponsibilityCenterService responsibilityCenterService;
    @Autowired
    private ResponsibilityCenterMapper responsibilityCenterMapper;

    @Autowired
    private ResponsibilityCenterRepository responsibilityCenterRepository;

    private final String EXISTING_RC_REFERENCE = "rc_ref_1";
    private final String NON_EXISTING_RC_REFERENCE = "rc_ref_2";
    private final Long ID = 1000L;


    @BeforeAll
    public void init() {
        responsibilityCenterService = new ResponsibilityCenterServiceImpl(
                responsibilityCenterRepository,
                responsibilityCenterMapper
        );

        ResponsibilityCenter existingRC = ResponsibilityCenter.builder()
                .id(ID)
                .reference(EXISTING_RC_REFERENCE)
                .build();

        when(responsibilityCenterRepository.findByReference(EXISTING_RC_REFERENCE))
                .thenReturn(Optional.of(existingRC));
        when(responsibilityCenterRepository.findByReference(NON_EXISTING_RC_REFERENCE))
                .thenReturn(Optional.empty());
        when(responsibilityCenterRepository.save(any(ResponsibilityCenter.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void saveIfNotExistWithRCExists() {
        ResponsibilityCenter rc = ResponsibilityCenter.builder()
                .reference(EXISTING_RC_REFERENCE)
                .build();
        ResponsibilityCenter savedRC = responsibilityCenterService.saveIfNotExist(rc);

        assertEquals(rc.getReference(), savedRC.getReference());
    }

    @Test
    void saveIfNotExistWithRCNotExists() {
        ResponsibilityCenter rc = ResponsibilityCenter.builder()
                .reference(NON_EXISTING_RC_REFERENCE)
                .build();
        ResponsibilityCenter savedRC = responsibilityCenterService.saveIfNotExist(rc);

        assertEquals(rc, savedRC);
    }

}
