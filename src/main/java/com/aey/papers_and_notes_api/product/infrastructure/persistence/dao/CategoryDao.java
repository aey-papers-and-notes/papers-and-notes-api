package com.aey.papers_and_notes_api.product.infrastructure.persistence.dao;

import com.aey.papers_and_notes_api.product.domain.entities.Category;
import com.aey.papers_and_notes_api.product.domain.repositories.CategoryRepository;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.models.CategoryJpa;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.repositories.CategoryJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CategoryDao implements CategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryDao(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    public Optional<Category> findById(Integer categoryId) {
        return categoryJpaRepository.findById(categoryId)
                .map(CategoryJpa::toEntity);
    }
}
