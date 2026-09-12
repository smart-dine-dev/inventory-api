package com.devstack.SmartDine.entity;

import com.devstack.SmartDine.entity.enums.ReferenceType;
import com.devstack.SmartDine.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Records every stock movement for a product.
 * Covers stock-in (receiving), stock-out (order fulfillment, wastage, adjustment).
 */
@Entity
@Table(name = "stock_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private Integer quantity; // Positive for IN, negative for OUT

    @Column(nullable = false)
    private Integer stockBefore; // Stock level before this transaction

    @Column(nullable = false)
    private Integer stockAfter;  // Stock level after this transaction

    @Column(length = 255)
    private String referenceId; // e.g., Order ID, Receipt ID, Adjustment ID

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType; // ORDER, RECEIPT, WASTAGE, ADJUSTMENT, RETURN

    @Column(length = 500)
    private String notes;

    @Column(length = 100)
    private String performedBy; // User/system that triggered this transaction

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

}
