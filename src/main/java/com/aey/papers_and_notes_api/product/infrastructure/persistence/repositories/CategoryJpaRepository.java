package com.aey.papers_and_notes_api.product.infrastructure.persistence.repositories;

import com.aey.papers_and_notes_api.product.infrastructure.persistence.models.CategoryJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryJpa, Integer> {
}
