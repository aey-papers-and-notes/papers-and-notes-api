package com.aey.papers_and_notes_api.product.infrastructure.rest.dtos;

import com.aey.papers_and_notes_api.product.domain.entities.Category;
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
@ToString
public class CreateCategoryDto {

    @JsonProperty
    @NotNull(message = "Category name must be not null")
    @NotEmpty(message = "Category name must not be empty")
    @NotBlank(message = "Category name must not be blank")
    private String name;

    @JsonProperty
    private String description;

    public static CreateCategoryDto fromEntity(Category category) {
        return CreateCategoryDto.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
