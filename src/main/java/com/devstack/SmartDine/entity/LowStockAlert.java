package com.devstack.SmartDine.entity;

import com.devstack.SmartDine.entity.enums.AlertStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Generated when a product's stock falls below its minimumStock threshold.
 * Can be used to trigger notifications (via Notification Service).
 */
@Entity
@Table(name = "low_stock_alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LowStockAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer stockAtAlert; // Stock level when alert was triggered

    @Column(nullable = false)
    private Integer minimumStockThreshold;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AlertStatus status = AlertStatus.OPEN;

    private LocalDateTime resolvedAt;

    @Column(length = 300)
    private String notes;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
