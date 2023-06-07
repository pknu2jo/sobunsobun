package com.example.repository.km;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.PurchaseStatusEntity;

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


    // 네이티브는 프로젝션 못씀...
    // 공구 번호에 해당하는 memId, state 불러오기
    // @Query(value = " SELECT new com.example.dto.KmAdminPurchaseStatus(p.purchaseno, p.memid, p.state) " + 
    //                 " FROM PurchaseStatusEntity p ")
    // List<KmAdminPurchaseStatus> findMemidAndStateByPurchaseno();

    // projection을 따로 생성하지 않고, 밑에 바로 생성했다.
    @Query(value = "SELECT memid, state from " +
                    " (SELECT *, ROW_NUMBER() OVER (PARTITION BY purchaseno, memid ORDER BY NO desc) recent " +
                    " FROM PURCHASESTATUS p2 WHERE state > 0) t1 " +
                    " WHERE recent = 1 AND purchaseno=:purchaseno ", nativeQuery = true)
    List<KmAdminPurchaseStatus> findMemidAndStateByPurchaseno(@Param("purchaseno") BigDecimal purchaseno);

    interface KmAdminPurchaseStatus {
        String getMemid();
        BigDecimal getState();
    }
}
