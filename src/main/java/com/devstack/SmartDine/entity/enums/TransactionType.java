package com.devstack.SmartDine.entity.enums;

public enum TransactionType {
    STOCK_IN,        // Received new stock
    STOCK_OUT,       // Used for an order
    WASTAGE,         // Spoiled / wasted
    ADJUSTMENT_IN,   // Manual positive correction
    ADJUSTMENT_OUT,  // Manual negative correction
    RETURN_TO_SUPPLIER // Returned to supplier
}
