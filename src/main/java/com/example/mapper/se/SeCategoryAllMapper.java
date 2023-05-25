package com.example.mapper.se;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.CategoryAll;

@Mapper
public interface SeCategoryAllMapper {

    // 대분류 가져오기
    public List<CategoryAll> selectLCategory();

    // 중분류 가져오기
    public List<CategoryAll> selectMCategory();

    // 소분류 가져오기
    public List<CategoryAll> selectSCategory();
    
}
