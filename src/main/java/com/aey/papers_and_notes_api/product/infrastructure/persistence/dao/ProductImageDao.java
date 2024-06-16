package com.aey.papers_and_notes_api.product.infrastructure.persistence.dao;

import com.aey.papers_and_notes_api.product.domain.entities.ProductImage;
import com.aey.papers_and_notes_api.product.domain.repositories.ProductImageRepository;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.models.ProductImageJpa;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.queries.ProductImageQuery;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.queries.ProductQuery;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.repositories.ProductImageJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductImageDao implements ProductImageRepository {

    private final ProductImageJpaRepository productImageJpaRepository;
    private final EntityManager entityManager;

    public ProductImageDao(
            EntityManager entityManager,
            ProductImageJpaRepository productImageJpaRepository
    ) {
        this.productImageJpaRepository = productImageJpaRepository;
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductImage> findAllProductImagesByProductId(UUID productId) {
        List<Object[]> result = this.entityManager
                .createNativeQuery(ProductImageQuery.PAGINATION_PRODUCT_IMAGES)
                .setParameter(ProductImageQuery.PARAM_PRODUCT_ID, productId)
                .getResultList();

        if (result.isEmpty()) {
            return new ArrayList<>();
        }

        return result.stream().map(r -> ProductImage.builder()
                .imageId((Integer) r[0])
                .url((String) r[1])
                .description((String) r[2])
                .build()
        ).toList();
    }

    @Override
    public Optional<ProductImage> findProductImageById(Integer productImageId) {
        return this.productImageJpaRepository
                .findById(productImageId)
                .map(ProductImageJpa::toEntity);
    }

}
