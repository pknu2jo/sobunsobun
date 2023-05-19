package com.example.mapper.se;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SePurchaseItemMapper {
    
    // 공구가 많이 열린 물품 5개
    public List<Map<String, Object>> findManyPurchaseItem();
}
