package com.aey.papers_and_notes_api.infrastructure.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ord01_orders")
public class OrderJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", unique = true)
    private UUID orderId;

    @Column(name = "ord_st_is_active")
    private Boolean isActive;

    @Column(name = "ord_dt_created_at")
    private Date createdAt;

    @Column(name = "ord_dt_updated_at")
    private Date updatedAt;

    @ManyToOne()
    @JoinColumn(
            name = "ord_fk_status",
            referencedColumnName = "order_status_id",
            insertable = false,
            updatable = false
    )
    private OrderStatusJpa orderStatus;

}
