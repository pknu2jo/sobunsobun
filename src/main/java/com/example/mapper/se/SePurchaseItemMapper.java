package com.example.mapper.se;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.SeAroundPurchaseView;
import com.example.dto.SeDeadlinePurchaseDdayView;
import com.example.dto.SeManyPurchaseItemView;
import com.example.dto.SeSelectItemListView;
import com.example.entity.CustomerAddressEntity;


@Mapper
public interface SePurchaseItemMapper {
    
    // --------------------------------------------------------------------------------------------------------
    // 공구가 많이 열린 물품 n개
    public List<Map<String, Object>> selectManyPurchaseItem();
    public List<SeManyPurchaseItemView> selectManyPurchaseItem1(long no);

    // --------------------------------------------------------------------------------------------------------
    // 기한이 얼마 안 남은 공구 n 개
    public List<SeDeadlinePurchaseDdayView> selectDeadLinePurchaseItem(long no);

    // --------------------------------------------------------------------------------------------------------
    // 내 주위 실시간 공구 5개
    public List<SeAroundPurchaseView> selectAroundPurchaseItem(CustomerAddressEntity obj);

    // --------------------------------------------------------------------------------------------------------
    // 물품목록 - 검색어
    public List<SeSelectItemListView> selectSearchItem(SeSelectItemListView obj);
    // 물품목록 - 검색어 - 전체 개수 (페이지네이션용)
    public long selectSearchItemCnt(SeSelectItemListView obj);
    // 물품목록 - 소분류
    public List<SeSelectItemListView> selectScodeItem(SeSelectItemListView obj);
    // 물품목록 - 소분류 BEST
    public List<SeSelectItemListView> selectScodeItemBest(long scode);
    // 물품목록 - 소분류 - 전체 개수 (페이지네이션용)
    public long selectScodeItemCnt(SeSelectItemListView obj);

    // 물품이 현재 공구 중인지 확인
    public long selectPurchaseChk(long itemno);

}
