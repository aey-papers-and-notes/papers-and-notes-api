package com.aey.papers_and_notes_api.product.domain.entities;

import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.CategoryDto;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.ProductImageDto;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private UUID productId;
    private String name;
    private String description;
    private Float price;
    private Integer stock;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isActive;
    private Integer brandId;
    private List<ProductImage> productImages;
    private Set<Category> categories;

    public static Product build(Product product) {
        return Product.builder()
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
}
