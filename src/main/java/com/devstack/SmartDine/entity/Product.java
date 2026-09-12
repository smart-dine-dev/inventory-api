package com.devstack.SmartDine.entity;

import com.devstack.SmartDine.entity.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a final sellable product (e.g., Bun, Rice Packet, Orange Juice).
 * These are the items that customers order from the restaurant.
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice; // Selling price per unit

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costPrice; // Cost to produce/purchase

    @Column(length = 20)
    private String unit; // e.g., "pcs", "ml", "g", "kg", "pack"

    @Column(nullable = false)
    @Builder.Default
    private Integer currentStock = 0; // Current available stock quantity

    @Column(nullable = false)
    @Builder.Default
    private Integer minimumStock = 10; // Low stock alert threshold

    @Column(nullable = false)
    @Builder.Default
    private Integer maximumStock = 500; // Max stock to hold

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ProductStatus status = ProductStatus.ACTIVE;

    @Column(nullable = false)
    @Builder.Default
    private Boolean availableForOrder = true; // Whether this product is available to order right now

    @Column(length = 255)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StockTransaction> stockTransactions;
}
