package com.devstack.SmartDine.entity;


import com.devstack.SmartDine.entity.enums.ReceiptStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private UUID id;
    private String receiptNumber;
    private LocalDate receivedDate;
    private BigDecimal totalAmount;
    private String notes;
    private String receivedBy;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private ReceiptStatus status = ReceiptStatus.PENDING;
}
