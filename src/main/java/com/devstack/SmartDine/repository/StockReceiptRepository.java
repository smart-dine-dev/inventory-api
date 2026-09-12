package com.devstack.SmartDine.repository;

import com.devstack.SmartDine.entity.StockReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockReceiptRepository extends JpaRepository<StockReceipt, UUID> {
}
