package com.aey.papers_and_notes_api.product.infrastructure.rest.dtos;

import com.aey.papers_and_notes_api.product.domain.entities.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {

    @JsonProperty
    @NotNull(message = "Product name must be not null")
    @NotEmpty(message = "Product name must not be empty")
    @NotBlank(message = "Product name must not be blank")
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    @NotNull(message = "Stock must be not null")
    @Positive(message = "Stock must be a positive number")
    private Integer stock;

    @JsonProperty
    @NotNull(message = "Price must be not null")
    @Positive(message = "Price must be a positive number")
    private Float price;

    @JsonProperty
    private Integer brandId;

    @JsonProperty
    private List<UploadProductImageDto> productImages;

    @JsonProperty
    private List<AssociateCategoryDto> categories;


    public static CreateProductDto fromEntity(Product product) {
        return CreateProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .stock(product.getStock())
                .price(product.getPrice())
                .brandId(product.getBrandId())
                .build();
    }

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .description(description)
                .stock(stock)
                .price(price)
                .brandId(brandId)
                .build();
    }
}
