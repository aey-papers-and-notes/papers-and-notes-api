package com.aey.papers_and_notes_api.product.infrastructure.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryAssociationDto {

    @JsonProperty
    @NotNull(message = "Category id must be not null")
    private Set<CategoryDto> categories;

}
