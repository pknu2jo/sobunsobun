package com.example.service.gr;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.ItemImage;

@Service
public interface GrPurchaseItemService {

    // 공구가 많이 열린 물품 5개
    public List<Map<String, Object>> selectManyPurchaseItem();

    // // 물품 대표이미지 가져오기
    // public ItemImage selectItemImageOne(Long itemno);

}
