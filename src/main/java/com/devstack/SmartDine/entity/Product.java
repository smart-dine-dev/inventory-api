package com.devstack.SmartDine.entity;

import com.devstack.SmartDine.entity.enums.ProductStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

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
    private UUID id;
    private String SKU;
    private String name;
    private String description;
    private Category category;
    private BigDecimal unitPrice;
    private BigDecimal costPrice;
    private String unit;
    private Integer currentStock=0;
    private Integer minimumStock=20;
    private Integer maximumStock=500;
    private ProductStatus status = ProductStatus.ACTIVE;
    private Boolean availableForOrder;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
