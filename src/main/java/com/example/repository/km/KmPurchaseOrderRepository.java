package com.example.repository.km;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.PurchaseOrderEntity;
import com.example.entity.km.KmOrderNoProjection;

@Repository
public interface KmPurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity, String>{
    
    // purchaseNo, memId에 해당하는 주문 번호 가져오기
    KmOrderNoProjection findByCustomerEntity_idAndPurchaseEntity_no(String memid, BigDecimal purchaseno);
}
