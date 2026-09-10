package com.devstack.SmartDine.entity;

import com.devstack.SmartDine.entity.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true, length = 50)
    private String SKU;
    @Column(nullable = false, length = 150)
    private String name;
    @Column(length = 500)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costPrice;
    @Column(length = 20)
    private String unit;
    @Column(nullable = false)
    @Builder.Default
    private Integer currentStock=0;

    @Column(nullable = false)
    @Builder.Default
    private Integer minimumStock=20;

    @Column(nullable = false)
    @Builder.Default
    private Integer maximumStock=500;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ProductStatus status = ProductStatus.ACTIVE;
    @Column(nullable = false)
    @Builder.Default
    private Boolean availableForOrder=true;
    private String imageUrl;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
