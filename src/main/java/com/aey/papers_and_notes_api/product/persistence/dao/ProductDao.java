package com.aey.papers_and_notes_api.product.persistence.dao;

import com.aey.papers_and_notes_api.product.domain.repositories.ProductRepository;
import com.aey.papers_and_notes_api.product.persistence.models.ProductJpa;
import com.aey.papers_and_notes_api.product.persistence.repositories.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductDao implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Autowired
    ProductDao(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public List<ProductJpa> findAllProducts() {
        return this.productJpaRepository.findAll();
    }

    @Override
    public Optional<ProductJpa> findOneProductById(UUID productId) {
        return this.productJpaRepository.findById(productId);
    }

    @Override
    public Optional<ProductJpa> createProduct(ProductJpa product) {
        return Optional.of(this.productJpaRepository.saveAndFlush(product));
    }

    @Override
    public Optional<ProductJpa> updateProduct(ProductJpa product) {
        return Optional.of(this.productJpaRepository.saveAndFlush(product));
    }

    @Override
    public void deleteProduct(UUID productId) {
        this.productJpaRepository.deleteById(productId);
    }

    @Override
    public void disableProduct(ProductJpa product) {

    }
}
