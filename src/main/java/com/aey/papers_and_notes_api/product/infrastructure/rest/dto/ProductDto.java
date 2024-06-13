package com.aey.papers_and_notes_api.product.infrastructure.rest.dto;

import com.aey.papers_and_notes_api.product.domain.entities.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @JsonProperty
    private UUID productId;

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private Integer stock;

    @JsonProperty
    private Float price;

    @JsonProperty
    private List<String> imageUrl;

    @JsonProperty
    private Date createdAt;

    @JsonProperty
    private Date updatedAt;

    @JsonProperty
    private Boolean isActive;

    @JsonProperty
    private Integer brandId;

    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .stock(product.getStock())
                .price(product.getPrice())
                .imageUrl(product.getImagesUrl())
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
                .imagesUrl(imageUrl)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .isActive(isActive)
                .brandId(brandId)
                .build();
    }
}

