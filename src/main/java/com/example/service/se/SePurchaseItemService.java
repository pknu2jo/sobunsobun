package com.example.service.se;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.se.SeManyPurchaseItemView;

@Service
public interface SePurchaseItemService {

    // 공구가 많이 일어난 물품 5개
    public List<SeManyPurchaseItemView> findManyPurchaseItem();
    
}
