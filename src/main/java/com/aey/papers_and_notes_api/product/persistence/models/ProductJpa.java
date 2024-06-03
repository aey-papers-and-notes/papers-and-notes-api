package com.aey.papers_and_notes_api.product.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prod01_products")
public class ProductJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", unique = true)
    private UUID productId;

    @Column(name = "prd_tx_name")
    private String name;

    @Column(name = "prd_tx_description")
    private String description;

    @Column(name = "prd_nu_price")
    private Float price;

    @Column(name = "prd_nu_stock")
    private Integer stock;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "prd_tx_images_url")
    private List<String> imagesUrl;

    @Column(name = "prd_dt_created_at")
    private Date createdAt;

    @Column(name = "prd_dt_updated_at")
    private Date updatedAt;

    @Column(name = "prd_st_is_active")
    private Boolean isActive;

    @ManyToOne()
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "brand_id",
            insertable = false,
            updatable = false
    )
    private BrandJpa brand;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "prod03_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoryJpa> categories;
}

