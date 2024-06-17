package com.aey.papers_and_notes_api.product.infrastructure.rest.dto;


import com.aey.papers_and_notes_api.product.domain.entities.ProductImage;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDto {
    private Integer imageId;
    private String url;
    private String description;

    public static ProductImageDto fromEntity(ProductImage productImage) {
        return ProductImageDto.builder()
                .imageId(productImage.getImageId())
                .url(productImage.getUrl())
                .description(productImage.getDescription())
                .build();
    }
}
