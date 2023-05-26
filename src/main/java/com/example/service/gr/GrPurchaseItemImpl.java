package com.example.service.gr;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.ItemImage;
import com.example.mapper.gr.GrPurchaseItemMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GrPurchaseItemImpl implements GrPurchaseItemService {

    final GrPurchaseItemMapper piMapper;

    @Override
    public List<Map<String, Object>> selectManyPurchaseItem() {
        try {
            return piMapper.selectManyPurchaseItem();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // @Override
    // public ItemImage selectItemImageOne(Long itemno) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method
    // 'selectItemImageOne'");
    // }

}
