package com.aey.papers_and_notes_api.product.domain.entities;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OderDetail {
    private UUID orderDetailId;
    private Integer quantity;
    private Float total;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;
}
