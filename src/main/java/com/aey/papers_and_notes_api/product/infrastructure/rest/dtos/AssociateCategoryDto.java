package com.aey.papers_and_notes_api.product.infrastructure.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssociateCategoryDto {

    @JsonProperty
    @NotNull(message = "Category id must be not null")
    @NotEmpty(message = "Category id must not be empty")
    @NotBlank(message = "Category id must not be blank")
    private Integer categoryId;

    @JsonProperty()
    private String name;

}
