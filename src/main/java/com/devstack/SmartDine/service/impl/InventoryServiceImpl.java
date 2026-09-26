package com.devstack.SmartDine.service.impl;


import com.devstack.SmartDine.entity.LowStockAlert;
import com.devstack.SmartDine.entity.Product;
import com.devstack.SmartDine.entity.StockTransaction;
import com.devstack.SmartDine.entity.enums.AlertStatus;
import com.devstack.SmartDine.entity.enums.ProductStatus;
import com.devstack.SmartDine.entity.enums.ReferenceType;
import com.devstack.SmartDine.entity.enums.TransactionType;
import com.devstack.SmartDine.exception.InsufficientStockException;
import com.devstack.SmartDine.exception.ResourceNotFoundException;
import com.devstack.SmartDine.repository.LowStockAlertRepository;
import com.devstack.SmartDine.repository.ProductRepository;
import com.devstack.SmartDine.repository.StockTransactionRepository;
import com.devstack.SmartDine.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;
    private final StockTransactionRepository transactionRepository;
    private final LowStockAlertRepository lowStockAlertRepository;

    /**
     * Deduct stock when an order is placed (called by Order Service).
     */
    @Transactional
    public void deductStock(Long productId, int quantity, String orderId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));

        if (product.getCurrentStock() < quantity) {
            throw new InsufficientStockException(
                    "Insufficient stock for product: " + product.getName() +
                    ". Available: " + product.getCurrentStock() + ", Requested: " + quantity);
        }

        int stockBefore = product.getCurrentStock();
        product.setCurrentStock(stockBefore - quantity);

        // Update availability flag
        if (product.getCurrentStock() == 0) {
            product.setAvailableForOrder(false);
            product.setStatus(ProductStatus.OUT_OF_STOCK);
        }

        productRepository.save(product);

        // Record the transaction
        StockTransaction tx = StockTransaction.builder()
                .product(product)
                .transactionType(TransactionType.STOCK_OUT)
                .quantity(-quantity)
                .stockBefore(stockBefore)
                .stockAfter(product.getCurrentStock())
                .referenceId(orderId)
                .referenceType(ReferenceType.ORDER)
                .performedBy("ORDER_SERVICE")
                .build();
        transactionRepository.save(tx);

        // Check for low stock
        checkAndCreateLowStockAlert(product);
        log.info("Stock deducted: product={}, qty={}, order={}", product.getName(), quantity, orderId);
    }

    /**
     * Add stock when a GRN is confirmed.
     */
    @Transactional
    public void addStock(Long productId, int quantity, String receiptId, String performedBy) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));

        int stockBefore = product.getCurrentStock();
        product.setCurrentStock(stockBefore + quantity);

        // Re-activate product if it was out of stock
        if (product.getStatus() == ProductStatus.OUT_OF_STOCK) {
            product.setStatus(ProductStatus.ACTIVE);
            product.setAvailableForOrder(true);
        }

        productRepository.save(product);

        StockTransaction tx = StockTransaction.builder()
                .product(product)
                .transactionType(TransactionType.STOCK_IN)
                .quantity(quantity)
                .stockBefore(stockBefore)
                .stockAfter(product.getCurrentStock())
                .referenceId(receiptId)
                .referenceType(ReferenceType.RECEIPT)
                .performedBy(performedBy)
                .build();
        transactionRepository.save(tx);

        // Resolve any open alerts for this product
        resolveAlerts(product);
        log.info("Stock added: product={}, qty={}, receipt={}", product.getName(), quantity, receiptId);
    }

    /**
     * Check current stock and raise a low-stock alert if needed.
     */
    private void checkAndCreateLowStockAlert(Product product) {
        if (product.getCurrentStock() <= product.getMinimumStock()) {
            boolean openAlertExists = lowStockAlertRepository
                    .existsByProductIdAndStatus(product.getId(), AlertStatus.OPEN);
            if (!openAlertExists) {
                LowStockAlert alert = LowStockAlert.builder()
                        .product(product)
                        .stockAtAlert(product.getCurrentStock())
                        .minimumStockThreshold(product.getMinimumStock())
                        .status(AlertStatus.OPEN)
                        .build();
                lowStockAlertRepository.save(alert);
                log.warn("Low stock alert created for product: {}", product.getName());
            }
        }
    }

    private void resolveAlerts(Product product) {
        List<LowStockAlert> openAlerts = lowStockAlertRepository
                .findByProductIdAndStatus(product.getId(), AlertStatus.OPEN);
        openAlerts.forEach(alert -> {
            alert.setStatus(AlertStatus.RESOLVED);
            alert.setNotes("Resolved after stock replenishment");
        });
        lowStockAlertRepository.saveAll(openAlerts);
    }

    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }

    public int checkAvailableStock(Long productId) {
        return productRepository.findById(productId)
                .map(Product::getCurrentStock)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));
    }
}
