package com.example.repository.se;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.se.SeManyPurchaseItemView;

@Repository
public interface SeManyPurchaseItemViewRepository extends JpaRepository<SeManyPurchaseItemView, BigDecimal>{

    // 공구가 많이 일어난 물품 5개
    List<SeManyPurchaseItemView> findTop2ByOrderByPurchasecntDesc();
    
}
