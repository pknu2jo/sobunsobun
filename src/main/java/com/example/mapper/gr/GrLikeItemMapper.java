package com.example.mapper.gr;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.dto.LikeItem;

@Mapper
public interface GrLikeItemMapper {

    // 관심물품등록
    public int insertLikeItem(@Param("list") List<LikeItem> list);

}
