package com.aey.papers_and_notes_api.domain.entities;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private UUID orderId;
    private Integer orderStatusId;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;
}
