package com.aey.papers_and_notes_api.product.domain.entities;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
    private Integer imageId;
    private String imageUrl;
}
