package com.example.service.se;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.mapper.se.SePurchaseItemMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SePurchaseItemServiceImpl implements SePurchaseItemService {

    final SePurchaseItemMapper piMapper;
    
    // 공구가 많이 열린 물품 8개
    @Override
    public List<Map<String, Object>> selectManyPurchaseItem() {
        try {
            return piMapper.selectManyPurchaseItem();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
