package com.aey.papers_and_notes_api.product.infrastructure.persistence.models;

import com.aey.papers_and_notes_api.product.domain.entities.Product;
import com.aey.papers_and_notes_api.product.domain.entities.ProductImage;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prod07_products_images")
public class ProductImageJpa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id", unique = true, nullable = false)
    private Integer imageId;

    @Column(name = "img_tx_url")
    private String url;

    @Column(name = "img_tx_description")
    private String description;

    @Column(name = "img_fk_product_id")
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "img_fk_product_id",
            referencedColumnName = "product_id",
            insertable = false,
            updatable = false
    )
    private ProductJpa product;

    public static ProductImageJpa fromEntity(ProductImage productImage) {
        return ProductImageJpa.builder()
                .imageId(productImage.getImageId())
                .url(productImage.getUrl())
                .description(productImage.getDescription())
                .productId(productImage.getProductId())
                .build();
    }

    public ProductImage toEntity() {
        return ProductImage.builder()
                .imageId(imageId)
                .url(url)
                .description(description)
                .productId(productId)
                .build();
    }
}
