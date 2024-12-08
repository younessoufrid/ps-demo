package com.demo.portailsaisie.backend.core.service.v1;

import com.demo.portailsaisie.backend.core.domain.Product;
import com.demo.portailsaisie.backend.core.repository.ProductRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product saveIfNotExist(Product product) {
        if (product.getReference() != null) {
            Optional<Product> existingProduct = repository.findByReference(product.getReference());
            return existingProduct.orElseGet(() -> repository.save(product));
        }
        return save(product);
    }

}
