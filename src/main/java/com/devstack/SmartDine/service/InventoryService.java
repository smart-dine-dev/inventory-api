package com.devstack.SmartDine.service;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    public void deleteStock(UUID productId);
    public void deductStock(UUID productId, int qty, UUID orderId);
    public void addStock(UUID productId, int qty, UUID receiptId, String performedBy);
    public void checkAndCreateLowStockAlert(UUID productId);
    public void resolveAlert(UUID productId);
    public List<Produc> resolveAlert(UUID productId);
}
