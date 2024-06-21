package com.aey.papers_and_notes_api.product.domain.entities;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductCategory {
    private Integer id;
    private UUID productId;
    private Integer categoryId;
}
