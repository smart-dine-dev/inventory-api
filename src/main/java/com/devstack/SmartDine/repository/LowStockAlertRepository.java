package com.devstack.SmartDine.repository;


import com.devstack.SmartDine.entity.LowStockAlert;
import com.devstack.SmartDine.entity.enums.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LowStockAlertRepository extends JpaRepository<LowStockAlert, Long> {

    List<LowStockAlert> findByStatus(AlertStatus status);

    List<LowStockAlert> findByProductIdAndStatus(Long productId, AlertStatus status);

    boolean existsByProductIdAndStatus(Long productId, AlertStatus status);
}
