package com.devstack.SmartDine.entity;

import com.devstack.SmartDine.entity.enums.AlertStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="low_stock_alert")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LowStockAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // product

    @Column(nullable = false)
    private Integer stockAlert;

    @Column(nullable = false)
    private Integer minimumStockThresholder;

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
