package com.example.mapper.se;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SePurchaseItemMapper {
    
    // 공구가 많이 열린 물품 8개
    @Select({
        " SELECT * FROM (SELECT *, ROW_NUMBER() OVER(ORDER BY PURCHASECNT DESC) ROWNNUM FROM SEMANYPURCHASEITEMVIEW s) WHERE ROWNNUM <= 8 ORDER BY ROWNUM ASC "
    })
    public List<Map<String, Object>> selectManyPurchaseItem();

    // --------------------------------------------------------------------------------------------------------
    // 물품 이미지 불러오기
    // 1. 물품 번호를 보내면 해당하는 대표이미지 번호를 반환
    public Long selectItemImageOne(BigDecimal itemno);

    // 2. 이미지 번호를 보내면 해당하는 이미지 정보를 반환
    // public ItemImage

}
