package com.devstack.SmartDine.repository;


import com.devstack.SmartDine.entity.StockReceipt;
import com.devstack.SmartDine.entity.enums.ReceiptStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockReceiptRepository extends JpaRepository<StockReceipt, Long> {

    Optional<StockReceipt> findByReceiptNumber(String receiptNumber);

    List<StockReceipt> findBySupplierId(Long supplierId);

    List<StockReceipt> findByStatus(ReceiptStatus status);
}
