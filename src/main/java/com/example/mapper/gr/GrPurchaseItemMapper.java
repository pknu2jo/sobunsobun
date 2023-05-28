package com.example.mapper.gr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.ItemImage;
import com.example.entity.gr.grpurchaseview;

@Mapper
public interface GrPurchaseItemMapper {

    // 공구가 많이 열린 물품 5개
    public List<Map<String, Object>> selectManyPurchaseItem();

    // // 물품 대표이미지 가져오기
    // public ItemImage selectItemImageOne(Long itemno);

    // 전체 개수( 페이지네이션용)
    public long countMyOrderList(String id);

    // 페이지네이션
    public List<grpurchaseview> selectMyOrderListPage(Map<String, Object> map);

}
