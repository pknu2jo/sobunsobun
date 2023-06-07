package com.example.service.gr;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.GrDate;
import com.example.entity.JjimEntity;
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

    // 삭제
    public void deleteByCustomerEntity_idAndItemEntity_no(String memid, BigDecimal itemno);

    // 아이템번호, 아이디 해당하는 상품 찾기
    public JjimEntity findByCustomerEntity_idAndItemEntity_no(String memid, BigDecimal itemno);

    // 아이디와 아이템번호가 같을 경우 카운트
    // 있으면 1, 없으면 0
    public int countByCustomerEntity_idAndItemEntity_no(String memid, BigDecimal itemno);

}
