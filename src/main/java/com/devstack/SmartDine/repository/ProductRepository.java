package com.devstack.SmartDine.repository;


import com.devstack.SmartDine.entity.Product;
import com.devstack.SmartDine.entity.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByRestaurantId(Long restaurantId);

    List<Product> findByStatus(ProductStatus status);

    List<Product> findByAvailableForOrderTrue();

    // Products that are below their minimum stock level
    @Query("SELECT p FROM Product p WHERE p.currentStock <= p.minimumStock AND p.status = 'ACTIVE'")
    List<Product> findLowStockProducts();

    // Search by name (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);
}
