package com.aey.papers_and_notes_api.product.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ord02_order_status")
public class OrderStatusJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_status_id", unique = true)
    private Integer orderStatusId;

    @Column(name = "ros_tx_name")
    private String name;

    @Column(name = "ros_st_is_active")
    private Boolean isActive;

    @Column(name = "ros_dt_created_at")
    private Date createdAt;

    @Column(name = "ros_dt_updated_at")
    private Date updatedAt;
}
