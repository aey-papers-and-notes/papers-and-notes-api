package com.aey.papers_and_notes_api.product.infrastructure.rest.dtos;


import com.aey.papers_and_notes_api.product.domain.entities.ProductImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDto {

    @JsonProperty
    private Integer imageId;

    @JsonProperty
    private String url;

    @JsonProperty
    private String description;

    public static ProductImageDto fromEntity(ProductImage productImage) {
        return ProductImageDto.builder()
                .imageId(productImage.getImageId())
                .url(productImage.getUrl())
                .description(productImage.getDescription())
                .build();
    }
}
