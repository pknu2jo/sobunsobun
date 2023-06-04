package com.example.repository.km;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.PurchaseEntity;

@Repository
public interface KmPurchaseRepository extends JpaRepository<PurchaseEntity, BigDecimal> {

    // storageno=?와 deliveryno=3(배달 완료)인 공구 리스트 가져오기
    public List<PurchaseEntity> findByStorageEntity_noAndDeliveryEntity_noAndReceiveStateOrderByNoAsc(
                                BigDecimal storageno, BigDecimal deliveryno, BigDecimal receivestate);

    // 2-1. deliveryno=3(배달 완료), purchaseno = ?, receivestate=?인 PURCHASE의 번호, 참여자수 가져오기
    public List<PurchaseEntity> findByNoAndDeliveryEntity_noAndReceiveStateOrderByNoAsc(
                                                    BigDecimal no,
                                                    BigDecimal deliveryno, BigDecimal receivestate);


    @Query(value = " SELECT p.NO purchaseno " +
            " FROM purchase p  INNER JOIN PURCHASESTATUS p2 " +
            " ON p.NO = p2.PURCHASENO " +
            " WHERE p.DELIVERYNO = 3 AND p2.state=1 AND p2.memid= :memid " +
            " ORDER BY purchaseno ASC", nativeQuery = true)
    public List<BigDecimal> selectPurchaseNoByMemId(@Param("memid") String memid);
}
