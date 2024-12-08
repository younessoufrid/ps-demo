package v1;

import com.demo.portailsaisie.backend.core.domain.SalesSite;
import com.demo.portailsaisie.backend.core.mapping.SaleSiteMapper;
import com.demo.portailsaisie.backend.core.repository.SalesSiteRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.SalesSiteService;
import com.demo.portailsaisie.backend.core.service.v1.SalesSiteServiceImpl;
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
public class SalesSiteServiceTest {

    private SalesSiteService salesSiteService;

    @Autowired
    private SalesSiteRepository salesSiteRepository;

    @Autowired
    private SaleSiteMapper saleSiteMapper;

    private final String EXISTING_SALES_SITE_REFERENCE = "sales_site_ref_1";
    private final String NON_EXISTING_SALES_SITE_REFERENCE = "sales_site_ref_2";
    private final Long ID = 1000L;


    @BeforeAll
    public void init() {
        salesSiteService = new SalesSiteServiceImpl(salesSiteRepository, saleSiteMapper);

        SalesSite existingSalesSite = SalesSite.builder()
                .id(ID)
                .reference(EXISTING_SALES_SITE_REFERENCE)
                .build();

        when(salesSiteRepository.findByReference(EXISTING_SALES_SITE_REFERENCE))
                .thenReturn(Optional.of(existingSalesSite));
        when(salesSiteRepository.findByReference(NON_EXISTING_SALES_SITE_REFERENCE))
                .thenReturn(Optional.empty());
        when(salesSiteRepository.save(any(SalesSite.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void saveIfNotExistWithSalesSiteExists() {
        SalesSite salesSite = SalesSite.builder()
                .reference(EXISTING_SALES_SITE_REFERENCE)
                .build();
        SalesSite savedSalesSite = salesSiteService.saveIfNotExist(salesSite);

        assertEquals(salesSite.getReference(), savedSalesSite.getReference());
    }

    @Test
    void saveIfNotExistWithSalesSiteNotExists() {
        SalesSite salesSite = SalesSite.builder()
                .reference(NON_EXISTING_SALES_SITE_REFERENCE)
                .build();
        SalesSite savedSalesSite = salesSiteService.saveIfNotExist(salesSite);

        assertEquals(salesSite, savedSalesSite);
    }

}
