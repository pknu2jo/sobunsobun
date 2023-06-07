package com.example.repository.km;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.km.KmAdminProductSimpleView;

@Repository
public interface KmAdminProductSimpleViewRepository  
        extends JpaRepository<KmAdminProductSimpleView, BigDecimal>{
    
    // 모든 지점 선택 시, 수령 상태에 따른 공구 주문 가져오기
    List<KmAdminProductSimpleView> findByReceivestateOrderByPurchasenoAsc(BigDecimal receivestate);

    // 지점(storageno)별, 수령 상태에 따른 공구 주문 가져오기
    List<KmAdminProductSimpleView> findByStoragenoAndReceivestateOrderByPurchasenoAsc(BigDecimal storageno, BigDecimal receivestate);
 
    
}
