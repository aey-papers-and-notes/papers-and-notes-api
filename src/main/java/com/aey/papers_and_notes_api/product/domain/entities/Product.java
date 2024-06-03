package com.aey.papers_and_notes_api.product.domain.entities;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private UUID productId;
    private String name;
    private String description;
    private Float price;
    private Integer stock;
    private List<String> imagesUrl;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isActive;
    private Integer brandId;
}
