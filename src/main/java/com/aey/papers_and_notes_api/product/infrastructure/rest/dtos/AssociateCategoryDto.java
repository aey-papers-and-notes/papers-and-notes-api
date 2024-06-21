package com.aey.papers_and_notes_api.product.infrastructure.rest.dtos;

import com.aey.papers_and_notes_api.product.domain.entities.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssociateCategoryDto {

    @JsonProperty
    private Integer categoryId;

    @JsonProperty
    private String name;

    public Category toEntity() {
        return Category.builder()
                .categoryId(categoryId)
                .name(name)
                .build();
    }

}
