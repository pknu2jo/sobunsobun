package com.example.repository.km;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.PurchaseStatusEntity;
import com.example.entity.km.KmPurchaseStatusIdProjection;

@Repository
public interface KmPurchaseStatusRepository extends JpaRepository<PurchaseStatusEntity, BigDecimal>{
    
    // 리뷰 등록 전 구매한 상품이 맞는지 확인하기
    @Query(value = " SELECT purchaseno FROM ( " +
    " SELECT * FROM (SELECT *, ROW_NUMBER() OVER " +
    " (PARTITION BY memid, purchaseNo ORDER BY NO desc) recent " +
    " FROM PURCHASESTATUS p WHERE itemno = :itemno ORDER BY memid DESC) " +
    " ) WHERE recent=1 AND state=1 AND memid = :memid ORDER BY purchaseno ASC"
    , nativeQuery = true)
    public List<BigDecimal> selectCheckOrder(
                                    @Param("itemno") long itemno,
                                    @Param("memid") String memid
                                );

    // purchaseno=?와 state=1인 PURCHASESTATUS의 memid들 가져오기 
    List<KmPurchaseStatusIdProjection> findByPurchaseEntity_noAndStateOrderByCustomerEntity_idAsc(BigDecimal storageno, BigDecimal state);

}
