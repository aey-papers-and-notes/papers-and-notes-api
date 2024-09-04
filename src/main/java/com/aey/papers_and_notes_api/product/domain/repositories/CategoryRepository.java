package com.aey.papers_and_notes_api.product.domain.repositories;

import com.aey.papers_and_notes_api.product.domain.entities.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {
    List<Category> findAllCategories(Integer limit, Integer offset);
    Integer countAllAvailableCategories();
    Optional<Category> findById(Integer categoryId);
    List<Category> findAllCategoriesByProductId(UUID productId);
    Optional<Category> createCategory(Category category);
    Optional<Category> saveCategoryById(Category category);
    Optional<Category> updateCategoryState(Category category);
}
