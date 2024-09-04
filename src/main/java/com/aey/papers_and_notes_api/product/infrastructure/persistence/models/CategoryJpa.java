package com.aey.papers_and_notes_api.product.infrastructure.persistence.models;

import com.aey.papers_and_notes_api.product.domain.entities.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prod03_categories")
public class CategoryJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id", unique = true, nullable = false)
    private Integer categoryId;

    @Column(name = "cat_tx_name")
    private String name;

    @Column(name = "cat_tx_description")
    private String description;

    @Column(name = "cat_st_is_active")
    private Boolean isActive;

    @Column(name = "cat_dt_created_at")
    private Date createdAt;

    @Column(name = "cat_dt_updated_at")
    private Date updatedAt;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<ProductJpa> products;

    public static CategoryJpa fromEntity(Category category) {
        return CategoryJpa.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .isActive(category.getIsActive())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public Category toEntity() {
        return Category.builder()
                .categoryId(categoryId)
                .name(name)
                .description(description)
                .isActive(isActive)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
