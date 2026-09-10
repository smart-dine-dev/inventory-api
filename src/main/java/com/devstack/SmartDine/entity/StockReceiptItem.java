package com.devstack.SmartDine.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="stock_receipt_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockReceiptItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_receipt_id", nullable = false)
    private StockReceipt stockReceipt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(nullable = false)
    private Integer orderedQuantity;
    @Column(nullable = false)
    private Integer receivedQuantity;
    @Column(precision = 10, scale = 2)
    private BigDecimal unitCost;
    @Column(precision = 10, scale = 2)
    private BigDecimal totalCost;
    private LocalDate expiryDate;
    @Column(length = 10)
    private String batchNumber;
    @Column(length = 300)
    private String notes;

}
