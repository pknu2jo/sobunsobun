package com.example.mapper.mj;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface mjItemMapper {

    /** 일괄삭제 */
    public int deleteItemBatch( long [] itemno);

}
