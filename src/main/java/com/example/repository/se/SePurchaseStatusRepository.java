package com.example.repository.se;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.PurchaseStatusEntity;
import com.example.entity.se.SePurchaseStatusProjection;

@Repository
public interface SePurchaseStatusRepository extends JpaRepository<PurchaseStatusEntity, BigDecimal>{
    
    List<SePurchaseStatusProjection> findByPurchaseEntity_NoAndState(BigDecimal purchaseno, BigDecimal state);

}
