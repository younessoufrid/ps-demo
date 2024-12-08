package v1;

import com.demo.portailsaisie.backend.core.domain.AOrderArticleOccurrence;
import com.demo.portailsaisie.backend.core.repository.AOrderArticleOccurrenceRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.AOrderArticleOccurrenceService;
import com.demo.portailsaisie.backend.core.service.v1.AOrderArticleOccurrenceServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import v1.config.TestConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
public class AOrderOccurrenceServiceTest {
    private AOrderArticleOccurrenceService aOrderArticleOccurrenceService;
    @Autowired
    private AOrderArticleOccurrenceRepository aOrderArticleOccurrenceRepository;

    @BeforeAll
    public void init() {
        aOrderArticleOccurrenceService = new AOrderArticleOccurrenceServiceImpl(aOrderArticleOccurrenceRepository);
        when(aOrderArticleOccurrenceService.save(any(AOrderArticleOccurrence.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

    }

    //
    @Test
    void saveIfNotExist() {
        AOrderArticleOccurrence aOrderArticleOccurrence = AOrderArticleOccurrence.builder()
                .build();
        AOrderArticleOccurrence savedAOrderOccurrence = aOrderArticleOccurrenceService.saveIfNotExist(aOrderArticleOccurrence);

        assertEquals(aOrderArticleOccurrence, savedAOrderOccurrence);
    }

}
