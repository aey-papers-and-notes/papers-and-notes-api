package com.aey.papers_and_notes_api.product.infrastructure.persistence.models;

import com.aey.papers_and_notes_api.common.utils.StringListConverter;
import com.aey.papers_and_notes_api.product.domain.entities.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.usertype.UserType;

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

    @Column(name = "prd_tx_images_url", columnDefinition = "text[]")
    private List<String> imagesUrl;

    @Column(name = "prd_dt_created_at")
    private Date createdAt;

    @Column(name = "prd_dt_updated_at")
    private Date updatedAt;

    @Column(name = "prd_st_is_active")
    private Boolean isActive;

    @Column(name = "prd_fk_brand_id")
    private Integer brandId;

    @ManyToOne()
    @JoinColumn(
            name = "prd_fk_brand_id",
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "prod05_tags",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagJpa> tags;

    public static ProductJpa fromEntity(Product product) {
        return ProductJpa.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .stock(product.getStock())
                .price(product.getPrice())
                .imagesUrl(product.getImagesUrl())
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
                .imagesUrl(imagesUrl)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .isActive(isActive)
                .brandId(brandId)
                .build();
    }
}

