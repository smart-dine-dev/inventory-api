package com.devstack.SmartDine.repository;

import com.devstack.SmartDine.entity.StockTransaction;
import com.devstack.SmartDine.entity.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {

    List<StockTransaction> findByProductIdOrderByCreatedAtDesc(Long productId);

    List<StockTransaction> findByTransactionType(TransactionType type);

    List<StockTransaction> findByProductIdAndCreatedAtBetween(Long productId,
                                                               LocalDateTime from,
                                                               LocalDateTime to);

    List<StockTransaction> findByReferenceId(String referenceId);
}
