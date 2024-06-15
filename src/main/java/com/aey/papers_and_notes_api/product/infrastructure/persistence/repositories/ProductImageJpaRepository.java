package com.aey.papers_and_notes_api.product.infrastructure.persistence.repositories;

import com.aey.papers_and_notes_api.product.infrastructure.persistence.models.ProductImageJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageJpaRepository extends JpaRepository<ProductImageJpa, Integer> {
}
