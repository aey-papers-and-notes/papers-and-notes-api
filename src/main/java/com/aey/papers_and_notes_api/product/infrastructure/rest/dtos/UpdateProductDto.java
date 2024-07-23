package com.aey.papers_and_notes_api.product.infrastructure.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {
    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private Integer stock;

    @JsonProperty
    private Float price;

    @JsonProperty
    private Integer brandId;
}
