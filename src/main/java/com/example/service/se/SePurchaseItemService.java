package com.example.service.se;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface SePurchaseItemService {

    // 공구가 많이 열린 물품 8개
    public List<Map<String, Object>> selectManyPurchaseItem();
    
}
