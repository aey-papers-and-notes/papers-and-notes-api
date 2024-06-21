package com.aey.papers_and_notes_api.product.domain.repositories;

import com.aey.papers_and_notes_api.product.domain.entities.Category;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CategoryRepository {
    Optional<Category> findById(Integer categoryId);
    Set<Category> findAllCategoriesByProductId(UUID productId);
}
