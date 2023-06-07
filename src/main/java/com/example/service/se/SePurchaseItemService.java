package com.example.service.se;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.SeAroundPurchaseView;
import com.example.dto.SeDeadlinePurchaseDdayView;
import com.example.dto.SeManyPurchaseItemView;
import com.example.dto.SeSelectItemListView;
import com.example.entity.ItemImage;
import com.example.entity.JjimEntity;
import com.example.entity.PurchaseStatusEntity;
import com.example.entity.se.SeChkPurchaseDeadlineView;

@Service
public interface SePurchaseItemService {

    // 공구가 많이 열린 물품 n개
    public List<Map<String, Object>> selectManyPurchaseItem();
    public List<SeManyPurchaseItemView> selectManyPurchaseItem1(long no);

    // 기한이 얼마 안 남은 공구 n 개
    public List<SeDeadlinePurchaseDdayView> selectDeadLinePurchaseItem(long no);

    // 물품 대표이미지 가져오기
    public ItemImage selectItemImageOne(BigDecimal itemno);

    // 내 주위 실시간 공구 5개
    public List<SeAroundPurchaseView> selectAroundPurchaseItem(String id);

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

    // 물품이 찜 상태인지 확인
    public long countByCustomerEntity_idAndItemEntity_no(String id, BigDecimal no);

    // 찜 save
    public int saveJjim(JjimEntity jjimEntity);

    // 찜 delete
    public int deleteJjim(String id, BigDecimal no);

    // 최초 개설된 공구인지 확인(알림용)
    public long selectPurchaseOpenChk(long purchaseno);

    // 마감일 지난 공구 확인(스케쥴러)
    public List<SeChkPurchaseDeadlineView> findAllAfterDeadline();

    // 마감 지난 공구 취소 insert(스케쥴러)
    public List<PurchaseStatusEntity> saveAllPurchaseStatus(List<PurchaseStatusEntity> list);

}
