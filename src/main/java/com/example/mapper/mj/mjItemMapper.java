package com.example.mapper.mj;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.Item;


@Mapper
public interface mjItemMapper {

    /** 일괄삭제 */
    public int deleteItemBatch( BigDecimal [] itemno);

    /** 일괄수정 */
    public int updateItemBatch(List<Item> list);
}
