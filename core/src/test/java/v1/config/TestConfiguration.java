package v1.config;

import com.demo.portailsaisie.backend.core.mapping.*;
import com.demo.portailsaisie.backend.core.repository.*;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;

@AutoConfigureMockMvc(addFilters = false)
public class TestConfiguration {
    @Bean
    public AffairRepository affairRepository() {
        return Mockito.mock(AffairRepository.class);
    }

    @Bean
    public AffairMapper affairMapper() {
        return Mockito.mock(AffairMapper.class);
    }

    @Bean
    public AOrderArticleOccurrenceRepository aOrderArticleOccurrenceRepository() {
        return Mockito.mock(AOrderArticleOccurrenceRepository.class);
    }

    @Bean
    public ArticleOccurrenceRepository articleOccurrenceRepository() {
        return Mockito.mock(ArticleOccurrenceRepository.class);
    }

    @Bean
    public ArticleRepository articleRepository() {
        return Mockito.mock(ArticleRepository.class);
    }

    @Bean
    public ClientRepository clientRepository() {
        return Mockito.mock(ClientRepository.class);
    }

    @Bean
    public ClientMapper clientMapper() {
        return Mockito.mock(ClientMapper.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public ProductRepository productRepository() {
        return Mockito.mock(ProductRepository.class);
    }

    @Bean
    public ProjectRepository projectRepository() {
        return Mockito.mock(ProjectRepository.class);
    }

    @Bean
    public ResponsibilityCenterRepository responsibilityCenterRepository() {
        return Mockito.mock(ResponsibilityCenterRepository.class);
    }

    @Bean
    public SalesSiteRepository salesSiteRepository() {
        return Mockito.mock(SalesSiteRepository.class);
    }

    @Bean
    public SaleSiteMapper saleSiteMapper() {
        return Mockito.mock(SaleSiteMapper.class);
    }

    @Bean
    public ResponsibilityCenterMapper responsibilityCenterMapper() {
        return Mockito.mock(ResponsibilityCenterMapper.class);
    }

    @Bean
    public ArticleOccurrenceMapper articleOccurrenceMapper() {
        return Mockito.mock(ArticleOccurrenceMapper.class);
    }

    @Bean
    public OrderMapper orderMapper() {
        return Mockito.mock(OrderMapper.class);
    }
    @Bean
    public SaisieMapper saisieMapper() {
        return Mockito.mock(SaisieMapper.class);
    }

    @Bean
    public SummaryMapper summaryMapper() {
        return Mockito.mock(SummaryMapper.class);
    }

}
