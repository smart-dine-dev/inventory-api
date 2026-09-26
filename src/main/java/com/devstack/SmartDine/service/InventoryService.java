package com.devstack.SmartDine.service;

import com.devstack.SmartDine.entity.Product;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    public void deductStock(Long productId, int quantity, String orderId);
    public void addStock(Long productId, int quantity, String receiptId, String performedBy);
    public List<Product> getLowStockProducts();
    public int checkAvailableStock(Long productId);
}
