package com.example.mapper.mj;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface mjItemImageMapper {

    int deleteImageBatch(long[] imageno);

}
