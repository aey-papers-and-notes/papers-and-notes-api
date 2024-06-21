package com.aey.papers_and_notes_api.product.infrastructure.persistence.dao;

import com.aey.papers_and_notes_api.product.domain.entities.Category;
import com.aey.papers_and_notes_api.product.domain.entities.Product;
import com.aey.papers_and_notes_api.product.domain.repositories.CategoryRepository;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.models.CategoryJpa;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.queries.CategoryQuery;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.queries.ProductQuery;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.repositories.CategoryJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CategoryDao implements CategoryRepository {
    private EntityManager entityManager;
    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryDao(CategoryJpaRepository categoryJpaRepository, EntityManager entityManager) {
        this.categoryJpaRepository = categoryJpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Category> findById(Integer categoryId) {
        return categoryJpaRepository.findById(categoryId)
                .map(CategoryJpa::toEntity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Category> findAllCategoriesByProductId(UUID productId) {
        List<Object[]> result = entityManager
                .createNativeQuery(CategoryQuery.CATEGORIES_BY_PRODUCT_ID)
                .setParameter(CategoryQuery.PARAM_PRODUCT_ID, productId)
                .getResultList();

        if (result.isEmpty()) {
            return new HashSet<>();
        }

        return result.stream().map(r -> Category.builder()
                .categoryId((Integer) r[0])
                .name((String) r[1])
                .isActive((Boolean) r[2])
                .createdAt((Date) r[3])
                .updatedAt((Date) r[4])
                .build()
        ).collect(Collectors.toSet());
    }
}
