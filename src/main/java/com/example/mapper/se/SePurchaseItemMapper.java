package com.example.mapper.se;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface SePurchaseItemMapper {
    
    // --------------------------------------------------------------------------------------------------------
    // 공구가 많이 열린 물품 8개
    @Select({
        " SELECT * FROM (SELECT *, ROW_NUMBER() OVER(ORDER BY PURCHASECNT DESC) ROWNNUM FROM SEMANYPURCHASEITEMVIEW s) WHERE ROWNNUM <= 8 ORDER BY ROWNUM ASC "
    })
    public List<Map<String, Object>> selectManyPurchaseItem();

    // --------------------------------------------------------------------------------------------------------
    // 기한이 얼마 안 남은 공구 n 개
    @Select({
        " SELECT * FROM (SELECT *, ROW_NUMBER() OVER(ORDER BY DEADLINE ASC) ROWNNUM FROM SEDEADLINEPURCHASEVIEW s) WHERE ROWNNUM <= #{no} ORDER BY ROWNUM ASC "
    })
    public List<Map<String, Object>> selectDeadLinePurchaseItem(long no);


}
