package com.aey.papers_and_notes_api.product.infrastructure.persistence.repositories;

import com.aey.papers_and_notes_api.product.infrastructure.persistence.models.ProductJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductJpa, UUID> {
}
