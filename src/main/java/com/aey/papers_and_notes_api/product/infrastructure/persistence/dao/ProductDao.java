package com.aey.papers_and_notes_api.product.infrastructure.persistence.dao;

import com.aey.papers_and_notes_api.product.domain.entities.Product;
import com.aey.papers_and_notes_api.product.domain.repositories.ProductRepository;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.models.ProductJpa;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.queries.ProductQuery;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.repositories.ProductJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
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

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> findAllProducts(Integer limit, Integer offset) {
        List<Object[]> result = entityManager
                .createNativeQuery(ProductQuery.PAGINATION_PRODUCT)
                .setParameter(ProductQuery.PARAM_PRODUCT_LIMIT, limit)
                .setParameter(ProductQuery.PARAM_PRODUCT_OFFSET, offset)
                .getResultList();

        if (result.isEmpty()) {
            return new ArrayList<>();
        }

        return result.stream().map(r -> Product.builder()
                .productId((UUID) r[0])
                .name((String) r[1])
                .description((String) r[2])
                .price((Float) r[3])
                .stock((Integer) r[4])
                .createdAt((Date) r[5])
                .updatedAt((Date) r[6])
                .isActive((Boolean) r[7])
                .brandId((Integer) r[8])
                .build()
        ).toList();
    }

    @Override
    public Integer countAllAvailableProducts() {
        Long count = (Long) entityManager
                .createNativeQuery(ProductQuery.COUNT_AVAILABLE_PRODUCTS)
                .getSingleResult();
        return count.intValue();
    }

    @Override
    public Optional<Product> findOneProductById(UUID productId) {
        return productJpaRepository
                .findById(productId)
                .map(ProductJpa::toEntity);
    }

    @Override
    public Optional<Product> createProduct(Product product) {
        return Optional.of(
                productJpaRepository
                        .save(ProductJpa.fromEntity(product))
                        .toEntity()
        );
    }

    @Override
    public Optional<Product> updateProductState(Product product) {
        return Optional.of(
                productJpaRepository
                        .save(ProductJpa.fromEntity(product))
                        .toEntity()
        );
    }

    @Override
    public Optional<Product> updateProduct(Product product) {
        return Optional.of(
                productJpaRepository
                        .saveAndFlush(ProductJpa.fromEntity(product))
                        .toEntity()
        );
    }
}
