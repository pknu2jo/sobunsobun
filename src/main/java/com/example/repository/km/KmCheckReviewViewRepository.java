package com.example.repository.km;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.km.KmCheckReviewView;

@Repository
public interface KmCheckReviewViewRepository  
        extends JpaRepository<KmCheckReviewView, String>{
    

    // 리뷰 작성 여부 확인하기 (위에서 purchaseNo 받아옴)
    KmCheckReviewView findByMemidAndPurchaseno(String memid, BigDecimal purchaseno);
}
