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
    
}
