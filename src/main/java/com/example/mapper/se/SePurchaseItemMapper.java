package com.example.mapper.se;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.entity.CustomerAddressEntity;


@Mapper
public interface SePurchaseItemMapper {
    
    // --------------------------------------------------------------------------------------------------------
    // 공구가 많이 열린 물품 n개
    public List<Map<String, Object>> selectManyPurchaseItem(long no);

    // --------------------------------------------------------------------------------------------------------
    // 기한이 얼마 안 남은 공구 n 개
    public List<Map<String, Object>> selectDeadLinePurchaseItem(long no);

    // --------------------------------------------------------------------------------------------------------
    // 내 주위 실시간 공구 5개
    public List<Map<String, Object>> selectAroundPurchaseItem(CustomerAddressEntity obj);

    // --------------------------------------------------------------------------------------------------------
    // 물품목록 - 검색어
    public List<Map<String, Object>> selectSearchItem(Map<String, Object> map);
    // 물품목록 - 소분류
    public List<Map<String, Object>> selectScodeItem(Map<String, Object> map);
    // 물품목록 - 소분류 BEST
    public List<Map<String, Object>> selectScodeItemBest(long scode);

    // 물품이 현재 공구 중인지 확인
    public long selectPurchaseChk(long itemno);

}
