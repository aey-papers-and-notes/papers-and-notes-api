package com.aey.papers_and_notes_api.product.infrastructure.rest.dtos;

import com.aey.papers_and_notes_api.product.domain.entities.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private Date createdAt;

    @JsonProperty
    private Date updatedAt;

    @JsonProperty
    private Boolean isActive;

    @JsonProperty
    private Integer brandId;

    @JsonProperty
    private List<ProductImageDto> productImages;

    @JsonProperty
    private List<CategoryDto> categories;


    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .stock(product.getStock())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .isActive(product.getIsActive())
                .brandId(product.getBrandId())
                .productImages(product.getProductImages().stream().map(ProductImageDto::fromEntity).toList())
                .categories(product.getCategories().stream().map(CategoryDto::fromEntity).collect(Collectors.toList()))
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

