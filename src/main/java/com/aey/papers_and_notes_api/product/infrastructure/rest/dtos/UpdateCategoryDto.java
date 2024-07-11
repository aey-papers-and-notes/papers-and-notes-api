package com.aey.papers_and_notes_api.product.infrastructure.rest.dtos;

import com.aey.papers_and_notes_api.product.domain.entities.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateCategoryDto {
    @JsonProperty
    private String name;

    public static UpdateCategoryDto fromEntity(Category category) {
        return UpdateCategoryDto.builder()
                .name(category.getName())
                .build();
    }
}
