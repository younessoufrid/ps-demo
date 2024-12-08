package v1;

import com.demo.portailsaisie.backend.core.domain.Article;
import com.demo.portailsaisie.backend.core.repository.ArticleRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.ArticleService;
import com.demo.portailsaisie.backend.core.service.v1.ArticleServiceImpl;
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
public class ArticleServiceTest {

    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    private final String EXISTING_ARTICLE_REFERENCE = "article_ref_1";
    private final String NON_EXISTING_ARTICLE_REFERENCE = "article_ref_2";
    private final Long ID = 1000L;


    @BeforeAll
    public void init() {
        articleService = new ArticleServiceImpl(articleRepository);

        Article existingArticle = Article.builder()
                .id(ID)
                .referenceBu(EXISTING_ARTICLE_REFERENCE)
                .build();

        when(articleRepository.findByReferenceBu(EXISTING_ARTICLE_REFERENCE))
                .thenReturn(Optional.of(existingArticle));
        when(articleRepository.findByReferenceBu(NON_EXISTING_ARTICLE_REFERENCE))
                .thenReturn(Optional.empty());
        when(articleRepository.save(any(Article.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void saveIfNotExistWithArticleExists() {
        Article article = Article.builder()
                .referenceBu(EXISTING_ARTICLE_REFERENCE)
                .build();
        Article savedArticle = articleService.saveIfNotExist(article);

        assertEquals(article.getReferenceBu(), savedArticle.getReferenceBu());
    }

    @Test
    void saveIfNotExistWithArticleNotExists() {
        Article article = Article.builder()
                .referenceBu(NON_EXISTING_ARTICLE_REFERENCE)
                .build();
        Article savedArticle = articleService.saveIfNotExist(article);

        assertEquals(article, savedArticle);
    }

}
