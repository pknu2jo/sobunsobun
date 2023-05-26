package com.example.service.se;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.CategoryAll;
import com.example.mapper.se.SeCategoryAllMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeCategoryServiceImpl implements SeCategoryService {

    final SeCategoryAllMapper cMapper;

    @Override
    public List<CategoryAll> selectLCategory() {
        try {
            return cMapper.selectLCategory();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CategoryAll> selectMCategory() {
        try {
            return cMapper.selectMCategory();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CategoryAll> selectSCategory() {
        try {
            return cMapper.selectSCategory();
        } catch (Exception e) {
            return null;
        }
    }

    // 물품목록용 -------------------------------------------------------------
    // 소분류에 해당하는 중분류, 대분류 불러오기
    @Override
    public CategoryAll selectByScode(long scode) {
        try {
            return cMapper.selectByScode(scode);
        } catch (Exception e) {
            return null;
        }
    }
    
}
