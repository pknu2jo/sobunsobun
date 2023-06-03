package com.example.repository.km;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.PurchaseEntity;

@Repository
public interface KmPurchaseRepository extends JpaRepository<PurchaseEntity, BigDecimal> {
    
    // storageno=?와 deliveryno=3(배달 완료)인 공구 리스트 가져오기
    public List<PurchaseEntity> findByStorageEntity_noAndDeliveryEntity_noAndReceiveStateOrderByNoAsc(BigDecimal storageno, BigDecimal deliveryno, BigDecimal receivestate);
}
