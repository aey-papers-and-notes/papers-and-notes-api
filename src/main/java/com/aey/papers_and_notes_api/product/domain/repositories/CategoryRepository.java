package com.aey.papers_and_notes_api.product.domain.repositories;

import com.aey.papers_and_notes_api.product.domain.entities.Category;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CategoryRepository {
    Set<Category> findAllCategories(Integer limit, Integer offset);
    Integer countAllAvailableCategories();
    Optional<Category> findById(Integer categoryId);
    Set<Category> findAllCategoriesByProductId(UUID productId);
    Optional<Category> createCategory(Category category);
    Optional<Category> saveCategoryById(Category category);
    Optional<Category> updateCategoryState(Category category);
}
