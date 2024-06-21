package com.aey.papers_and_notes_api.product.domain.repositories;

import com.aey.papers_and_notes_api.product.domain.entities.Category;

import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> findById(Integer categoryId);
}
