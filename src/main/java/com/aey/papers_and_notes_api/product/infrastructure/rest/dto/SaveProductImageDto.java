package com.aey.papers_and_notes_api.product.infrastructure.rest.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveProductImageDto {
    private String url;
    private String description;
}
