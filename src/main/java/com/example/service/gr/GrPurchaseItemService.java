package com.example.service.gr;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.GrDate;
import com.example.entity.gr.grgrpurchaseview;

@Service
public interface GrPurchaseItemService {

    // 공구가 많이 열린 물품 5개
    public List<Map<String, Object>> selectManyPurchaseItem();

    // 전체 개수( 페이지네이션용)
    public long countMyOrderList(String id);

    // 페이지네이션
    public List<grgrpurchaseview> selectMyOrderListPage(Map<String, Object> map);

    // 기한내 구매목록 개수 불러오기
    public long countMyOrderListDate(GrDate grDate);

    // 기한내 구매목록 불러오기
    public List<grgrpurchaseview> MyOrderList(GrDate grDate);

    // 기한내 구매목록 불러오기 + 페이지네이션
    public List<grgrpurchaseview> searchMyOrderList(GrDate grdate);

    // 내가 주문한 상품 목록
    public List<grgrpurchaseview> findByMemid(String id);

}
