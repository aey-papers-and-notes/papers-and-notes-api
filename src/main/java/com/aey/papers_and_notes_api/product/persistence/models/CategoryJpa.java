package com.aey.papers_and_notes_api.product.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prod03_categories")
public class CategoryJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id", unique = true)
    private UUID orderId;

    @Column(name = "cat_tx_name")
    private String name;

    @Column(name = "cat_st_is_active")
    private Boolean isActive;

    @Column(name = "cat_dt_created_at")
    private Date createdAt;

    @Column(name = "cat_dt_updated_at")
    private Date updatedAt;
}
