package com.devstack.SmartDine.api;

import com.devstack.SmartDine.entity.Product;
import com.devstack.SmartDine.repository.ProductRepository;
import com.devstack.SmartDine.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final ProductRepository productRepository;

    /** Check available stock for a product (used by Order Service) */
    @GetMapping("/products/{productId}/stock")
    public ResponseEntity<Map<String, Object>> checkStock(@PathVariable Long productId) {
        int stock = inventoryService.checkAvailableStock(productId);
        return ResponseEntity.ok(Map.of("productId", productId, "availableStock", stock));
    }

    /** Deduct stock (called internally by Order Service) */
    @PostMapping("/products/{productId}/deduct")
    public ResponseEntity<Void> deductStock(
            @PathVariable Long productId,
            @RequestParam int quantity,
            @RequestParam String orderId) {
        inventoryService.deductStock(productId, quantity, orderId);
        return ResponseEntity.ok().build();
    }

    /** Add stock (called when GRN is confirmed) */
    @PostMapping("/products/{productId}/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addStock(
            @PathVariable Long productId,
            @RequestParam int quantity,
            @RequestParam String receiptId,
            @RequestParam String performedBy) {
        inventoryService.addStock(productId, quantity, receiptId, performedBy);
        return ResponseEntity.ok().build();
    }

    /** Get all low-stock products */
    @GetMapping("/products/low-stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Product>> getLowStockProducts() {
        return ResponseEntity.ok(inventoryService.getLowStockProducts());
    }

    /** Get all products */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    /** Get product by id */
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
