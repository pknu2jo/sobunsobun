package com.example.mapper.se;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.dto.CategoryAll;

@Mapper
public interface CategoryAllMapper {

    // 대분류 가져오기
    @Select({
        " SELECT code lcode, name lname FROM LCATEGORY "
    })
    public List<CategoryAll> selectLCategory();

    // 중분류 가져오기
    @Select({
        " SELECT code mcode, name mname FROM MCATEGORY "
    })
    public List<CategoryAll> selectMCategory();

    // 소분류 가져오기
    @Select({
        " SELECT code scode, name sname FROM SCATEGORY "
    })
    public List<CategoryAll> selectSCategory();
    
}
