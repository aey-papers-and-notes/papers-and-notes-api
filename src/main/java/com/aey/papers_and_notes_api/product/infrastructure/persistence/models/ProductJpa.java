package com.aey.papers_and_notes_api.product.infrastructure.persistence.models;

import com.aey.papers_and_notes_api.product.domain.entities.Product;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
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
public class ProductJpa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", unique = true, nullable = false)
    private UUID productId;

    @Column(name = "prd_tx_name")
    private String name;

    @Column(name = "prd_tx_description")
    private String description;

    @Column(name = "prd_nu_price")
    private Float price;

    @Column(name = "prd_nu_stock")
    private Integer stock;

    @Column(name = "prd_dt_created_at")
    private Date createdAt;

    @Column(name = "prd_dt_updated_at")
    private Date updatedAt;

    @Column(name = "prd_st_is_active")
    private Boolean isActive;

    @Column(
            name = "prd_fk_brand_id",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private Integer brandId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "prd_fk_brand_id",
            referencedColumnName = "brand_id",
            insertable = false,
            updatable = false
    )
    private BrandJpa brand;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "prod04_products_categories",
            joinColumns = @JoinColumn(name = "rpc_fk_product_id", referencedColumnName = "product_id", insertable = false, updatable = false),
            inverseJoinColumns =  @JoinColumn(name = "rpc_fk_category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    )
    private List<CategoryJpa> tags;

    public static ProductJpa fromEntity(Product product) {
        return ProductJpa.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .stock(product.getStock())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .isActive(product.getIsActive())
                .brandId(product.getBrandId())
                .build();
    }

    public Product toEntity() {
        return Product.builder()
                .productId(productId)
                .name(name)
                .description(description)
                .stock(stock)
                .price(price)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .isActive(isActive)
                .brandId(brandId)
                .build();
    }
}

