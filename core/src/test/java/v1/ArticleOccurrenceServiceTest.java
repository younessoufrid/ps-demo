package v1;

import com.demo.portailsaisie.backend.core.domain.ArticleOccurrence;
import com.demo.portailsaisie.backend.core.mapping.ArticleOccurrenceMapper;
import com.demo.portailsaisie.backend.core.mapping.OrderMapper;
import com.demo.portailsaisie.backend.core.mapping.SummaryMapper;
import com.demo.portailsaisie.backend.core.mapping.SaisieMapper;
import com.demo.portailsaisie.backend.core.repository.AOrderArticleOccurrenceRepository;
import com.demo.portailsaisie.backend.core.repository.ArticleOccurrenceRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.ArticleOccurrenceService;
import com.demo.portailsaisie.backend.core.service.v1.ArticleOccurrenceServiceImpl;
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
public class ArticleOccurrenceServiceTest {

    private ArticleOccurrenceService articleOccurrenceService;

    @Autowired
    private ArticleOccurrenceRepository articleOccurrenceRepository;

    @Autowired
    private ArticleOccurrenceMapper articleOccurrenceMapper;

    @Autowired
    private SaisieMapper saisieMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SummaryMapper summaryMapper;

    @Autowired
    private AOrderArticleOccurrenceRepository aOrderArticleOccurrenceRepository;

    private final String EXISTING_ARTICLE_OCCURRENCE_REFERENCE = "articleOcc_ref_1";
    private final String NON_EXISTING_ARTICLE_OCCURRENCE_REFERENCE = "articleOcc_ref_2";
    private final Long ID = 1000L;


    @BeforeAll
    public void init() {
        articleOccurrenceService = new ArticleOccurrenceServiceImpl(aOrderArticleOccurrenceRepository ,articleOccurrenceRepository, articleOccurrenceMapper, orderMapper, saisieMapper, summaryMapper);

        ArticleOccurrence existingOccurrence = ArticleOccurrence.builder()
                .id(ID)
                .reference(EXISTING_ARTICLE_OCCURRENCE_REFERENCE)
                .build();

        when(articleOccurrenceRepository.findByReference(EXISTING_ARTICLE_OCCURRENCE_REFERENCE))
                .thenReturn(Optional.of(existingOccurrence));
        when(articleOccurrenceRepository.findByReference(NON_EXISTING_ARTICLE_OCCURRENCE_REFERENCE))
                .thenReturn(Optional.empty());
        when(articleOccurrenceRepository.save(any(ArticleOccurrence.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void saveIfNotExistWithArticleOccurrenceExists() {
        ArticleOccurrence articleOccurrence = ArticleOccurrence.builder()
                .reference(EXISTING_ARTICLE_OCCURRENCE_REFERENCE)
                .build();
        ArticleOccurrence savedArticleOccurrence = articleOccurrenceService.saveIfNotExist(articleOccurrence);

        assertEquals(articleOccurrence.getReference(), savedArticleOccurrence.getReference());
    }

    @Test
    void saveIfNotExistWithArticleOccurrenceNotExists() {
        ArticleOccurrence articleOccurrence = ArticleOccurrence.builder()
                .reference(NON_EXISTING_ARTICLE_OCCURRENCE_REFERENCE)
                .build();
        ArticleOccurrence savedArticleOccurrence = articleOccurrenceService.saveIfNotExist(articleOccurrence);

        assertEquals(articleOccurrence, savedArticleOccurrence);
    }

}
