package com.example.service.se;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.se.SeManyPurchaseItemView;
import com.example.repository.se.SeManyPurchaseItemViewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SePurchaseItemServiceImpl implements SePurchaseItemService {

    final SeManyPurchaseItemViewRepository piRepository;
    
    // 공구가 많이 일어난 물품 5개
    @Override
    public List<SeManyPurchaseItemView> findManyPurchaseItem() {
        try {
            return piRepository.findTop2ByOrderByPurchasecntDesc();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
