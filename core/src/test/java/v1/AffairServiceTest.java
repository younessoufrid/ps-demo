package v1;

import com.demo.portailsaisie.backend.core.domain.Affair;
import com.demo.portailsaisie.backend.core.mapping.AffairMapper;
import com.demo.portailsaisie.backend.core.repository.AffairRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.AffairService;
import com.demo.portailsaisie.backend.core.service.v1.AffairServiceImpl;
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
public class AffairServiceTest {
    private AffairService affairService;

    @Autowired
    private AffairRepository affairRepository;

    @Autowired
    private AffairMapper affairMapper;
    private final String EXISTING_AFFAIR_REFERENCE = "reference_1";
    private final String NON_EXISTING_AFFAIR_REFERENCE = "reference_2";
    private final Long ID = 1000L;

    @BeforeAll
    public void init() {
        affairService = new AffairServiceImpl(affairRepository,affairMapper);
        Affair existingAffair = Affair.builder()
                .id(ID)
                .reference(EXISTING_AFFAIR_REFERENCE)
                .build();

        when(affairRepository.findByReference(EXISTING_AFFAIR_REFERENCE))
                .thenReturn(Optional.of(existingAffair));
        when(affairRepository.findByReference(NON_EXISTING_AFFAIR_REFERENCE))
                .thenReturn(Optional.empty());
        when(affairRepository.save(any(Affair.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        ;

    }

    //
    @Test
    void saveIfNotExistWithAffairExists() {
        Affair affair = Affair.builder()
                .reference(EXISTING_AFFAIR_REFERENCE)
                .build();
        Affair savedAffair = affairService.saveIfNotExist(affair);

        assertEquals(affair.getReference(), savedAffair.getReference());
    }

    //
    @Test
    void saveIfNotExistWithAffairNotExists() {

        Affair affair = Affair.builder()
                .reference(NON_EXISTING_AFFAIR_REFERENCE)
                .build();
        Affair savedAffair = affairService.saveIfNotExist(affair);

        assertEquals(affair, savedAffair);
    }

}
