package com.devstack.SmartDine.repository;

import com.devstack.SmartDine.entity.LowStockAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface LowStockAlertRepository extends JpaRepository<LowStockAlert, UUID> {
}
