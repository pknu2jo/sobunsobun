package com.example.repository.km;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.km.KmAdminProductView;

@Repository
public interface KmAdminProductViewRepository  
        extends JpaRepository<KmAdminProductView, BigDecimal>{
    
    // 모든 지점 선택 시, 수령 상태에 따른 공구 주문 가져오기
    List<KmAdminProductView> findByReceivestate(BigDecimal receivestate);

    // 지점(storageno)별, 수령 상태에 따른 공구 주문 가져오기
    List<KmAdminProductView> findByStoragenoAndReceivestate(BigDecimal storageno, BigDecimal receivestate);
    
    // memid별 검색 시 공구 주문 가져오기
    List<KmAdminProductView> findByMemid(String searchvalue);
    
    // purchaseno별  검색 시 공구 주문 가져오기
    List<KmAdminProductView> findByPurchaseno(BigDecimal searchvalue);
    
    // @Query(value = " SELECT * FROM KMADMINPRODUCTVIEW k " +
    //                 " WHERE memid = :searchvalue ", nativeQuery = true)
    // public List<KmAdminProductView> searchPurchaseByMemid(@Param("searchvalue") String searchvalue);

    // // purchaseno별  검색 시 공구 주문 가져오기
    // @Query(value = " SELECT * FROM KMADMINPRODUCTVIEW k " +
    //                 " WHERE :searchoption = :searchvalue ", nativeQuery = true)
    // public List<KmAdminProductView> searchPurchaseByPurchaseno(@Param("searchvalue") String searchvalue);

    
    
}
