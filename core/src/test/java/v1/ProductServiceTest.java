package v1;

import com.demo.portailsaisie.backend.core.domain.Product;
import com.demo.portailsaisie.backend.core.repository.ProductRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.ProductService;
import com.demo.portailsaisie.backend.core.service.v1.ProductServiceImpl;
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
public class ProductServiceTest {

    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private final String EXISTING_PRODUCT_REFERENCE = "product_ref_1";
    private final String NON_EXISTING_PRODUCT_REFERENCE = "product_ref_2";
    private final Long ID = 1000L;


    @BeforeAll
    public void init() {
        productService = new ProductServiceImpl(productRepository);

        Product existingProduct = Product.builder()
                .id(ID)
                .reference(EXISTING_PRODUCT_REFERENCE)
                .build();

        when(productRepository.findByReference(EXISTING_PRODUCT_REFERENCE))
                .thenReturn(Optional.of(existingProduct));
        when(productRepository.findByReference(NON_EXISTING_PRODUCT_REFERENCE))
                .thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void saveIfNotExistWithProductExists() {
        Product product = Product.builder()
                .reference(EXISTING_PRODUCT_REFERENCE)
                .build();
        Product savedProduct = productService.saveIfNotExist(product);

        assertEquals(product.getReference(), savedProduct.getReference());
    }

    @Test
    void saveIfNotExistWithProductNotExists() {
        Product product = Product.builder()
                .reference(NON_EXISTING_PRODUCT_REFERENCE)
                .build();
        Product savedProduct = productService.saveIfNotExist(product);

        assertEquals(product, savedProduct);
    }

}
