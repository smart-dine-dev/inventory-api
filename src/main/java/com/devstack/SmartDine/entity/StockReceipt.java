package com.devstack.SmartDine.entity;


import com.devstack.SmartDine.entity.enums.ReceiptStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="stock_receipt")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true, length = 18)
    private String receiptNumber;
    @Column(nullable = false)
    private LocalDate receivedDate;
    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;
    @Column(length = 500)
    private String notes;
    @Column(length = 100)
    private String receivedBy;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ReceiptStatus status = ReceiptStatus.PENDING;
}
