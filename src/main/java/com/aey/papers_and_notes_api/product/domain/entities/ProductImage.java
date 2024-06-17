package com.aey.papers_and_notes_api.product.domain.entities;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductImage {
    private Integer imageId;
    private String url;
    private String description;
    private UUID productId;
}
