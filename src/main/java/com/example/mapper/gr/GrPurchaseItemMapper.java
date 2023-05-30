package com.example.mapper.gr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.dto.GrDate;
import com.example.dto.ItemImage;
import com.example.entity.gr.grpurchaseview;

@Mapper
public interface GrPurchaseItemMapper {

    // 공구가 많이 열린 물품 5개
    public List<Map<String, Object>> selectManyPurchaseItem();

    // 전체 개수( 페이지네이션용)
    public long countMyOrderList(String id);

    // 페이지네이션
    public List<grpurchaseview> selectMyOrderListPage(Map<String, Object> map);

    // 기한내 구매목록 개수 불러오기
    public long countMyOrderListDate(GrDate grDate);

    // test
    @Select({ " SELECT * FROM grpurchaseview WHERE memId=#{memId} ORDER BY regdate desc" })
    public List<grpurchaseview> selectById(String id);

    // 기한내 구매목록 불러오기
    public List<grpurchaseview> MyOrderList(GrDate grDate);

    // 기한내 구매목록 불러오기
    public List<grpurchaseview> searchMyOrderList(GrDate grdate);

}
