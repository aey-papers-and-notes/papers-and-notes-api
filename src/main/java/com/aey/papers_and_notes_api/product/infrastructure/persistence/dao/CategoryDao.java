package com.aey.papers_and_notes_api.product.infrastructure.persistence.dao;

import com.aey.papers_and_notes_api.product.domain.entities.Category;
import com.aey.papers_and_notes_api.product.domain.repositories.CategoryRepository;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.models.CategoryJpa;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.queries.CategoryQuery;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.repositories.CategoryJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CategoryDao implements CategoryRepository {

    private final EntityManager entityManager;
    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryDao(CategoryJpaRepository categoryJpaRepository, EntityManager entityManager) {
        this.categoryJpaRepository = categoryJpaRepository;
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Category> findAllCategories(Integer limit, Integer offset) {
        List<Object[]> result = entityManager
                .createNativeQuery(CategoryQuery.PAGINATION_CATEGORIES)
                .setParameter(CategoryQuery.PARAM_CATEGORY_LIMIT, limit)
                .setParameter(CategoryQuery.PARAM_CATEGORY_OFFSET, offset)
                .getResultList();

        if (result.isEmpty()) {
            return new HashSet<>();
        }

        return result.stream().map(r -> Category.builder()
                .categoryId((Integer) r[0])
                .name((String) r[1])
                .description((String) r[2])
                .isActive((Boolean) r[3])
                .createdAt((Date) r[4])
                .updatedAt((Date) r[5])
                .build()
        ).collect(Collectors.toSet());
    }

    @Override
    public Integer countAllAvailableCategories() {
        Long count = (Long) entityManager
                .createNativeQuery(CategoryQuery.COUNT_AVAILABLE_CATEGORIES)
                .getSingleResult();
        return count.intValue();
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

    @Override
    public Optional<Category> createCategory(Category category) {
        return Optional.of(
                categoryJpaRepository
                        .save(CategoryJpa.fromEntity(category))
                        .toEntity()
        );
    }

    @Override
    public Optional<Category> saveCategoryById(Category category) {
        return Optional.of(
                categoryJpaRepository
                        .saveAndFlush(CategoryJpa.fromEntity(category))
                        .toEntity()
        );
    }

    @Override
    public Optional<Category> updateCategoryState(Category category) {
        return Optional.of(
                categoryJpaRepository
                        .saveAndFlush(CategoryJpa.fromEntity(category))
                        .toEntity()
        );
    }
}
