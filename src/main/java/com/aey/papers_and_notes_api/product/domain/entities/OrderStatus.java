package com.aey.papers_and_notes_api.product.domain.entities;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus {
    private Integer orderStatusId;
    private String name;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;
}
