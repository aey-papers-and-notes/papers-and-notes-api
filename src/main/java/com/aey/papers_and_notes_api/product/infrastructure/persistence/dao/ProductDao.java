package com.aey.papers_and_notes_api.product.infrastructure.persistence.dao;

import com.aey.papers_and_notes_api.product.domain.repositories.ProductRepository;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.models.ProductJpa;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.queries.ProductQuery;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.repositories.ProductJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDao implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final EntityManager entityManager;

    @Autowired
    ProductDao(
            ProductJpaRepository productJpaRepository,
            EntityManager entityManager
    ) {
        this.productJpaRepository = productJpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<ProductJpa> findAllProducts(Integer limit, Integer offset) {
        List<Object[]> result = this.entityManager
                .createNativeQuery(ProductQuery.PAGINATION_PRODUCT)
                .setParameter(ProductQuery.PARAM_PRODUCT_LIMIT, limit)
                .setParameter(ProductQuery.PARAM_PRODUCT_OFFSET, offset)
                .getResultList();

        if (result.isEmpty()) {
            return new ArrayList<>();
        }

        return result.stream().map(r -> (
                ProductJpa.builder()
                        .productId((UUID) r[0])
                        .name((String) r[1])
                        .description((String) r[2])
                        .stock((Integer) r[3])
                        .price((Float) r[4])
                        .imagesUrl((List<String>) r[5])
                        .createdAt((Date) r[6])
                        .updatedAt((Date) r[7])
                        .isActive((Boolean) r[8])
                        .build()
        )).toList();
    }

    public Integer countAllAvailableProducts() {
        Long count = (Long) entityManager
                .createNativeQuery(ProductQuery.COUNT_AVAILABLE_PRODUCTS)
                .getSingleResult();
        return count.intValue();
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
