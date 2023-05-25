package com.example.mapper.se;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.entity.CustomerAddressEntity;


@Mapper
public interface SePurchaseItemMapper {
    
    // --------------------------------------------------------------------------------------------------------
    // 공구가 많이 열린 물품 8개
    public List<Map<String, Object>> selectManyPurchaseItem();

    // --------------------------------------------------------------------------------------------------------
    // 기한이 얼마 안 남은 공구 n 개
    public List<Map<String, Object>> selectDeadLinePurchaseItem(long no);

    // --------------------------------------------------------------------------------------------------------
    // 내 주위 실시간 공구 5개
    public List<Map<String, Object>> selectAroundPurchaseItem(CustomerAddressEntity obj);

}
