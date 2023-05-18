package com.example.service.se;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.CategoryAll;

@Service
public interface SeCategoryService {
    
    // 대분류 가져오기
    public List<CategoryAll> selectLCategory();

    // 중분류 가져오기
    public List<CategoryAll> selectMCategory();

    // 소분류 가져오기
    public List<CategoryAll> selectSCategory();
    
}
