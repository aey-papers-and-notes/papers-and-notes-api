package com.aey.papers_and_notes_api.product.infrastructure.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveProductImageDto {

    @JsonProperty
    private String url;

    @JsonProperty
    private String description;
}
