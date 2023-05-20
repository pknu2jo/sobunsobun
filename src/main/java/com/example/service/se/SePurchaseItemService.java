package com.example.service.se;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.entity.ItemImage;

@Service
public interface SePurchaseItemService {

    // 공구가 많이 열린 물품 8개
    public List<Map<String, Object>> selectManyPurchaseItem();

    // 기한이 얼마 안 남은 공구 n 개
    public List<Map<String, Object>> selectDeadLinePurchaseItem(long no);

    // 물품 대표이미지 가져오기
    public ItemImage selectItemImageOne(BigDecimal itemno);


    
}
