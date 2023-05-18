package com.example.service.km;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Item;
import com.example.dto.Purchase;
import com.example.mapper.km.KmCustomerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KmCustomerServiceImpl implements KmCustomerService {

    final KmCustomerMapper cMapper;
    // final ItemRepository item;

// 물품 상세 조회 페이지

    // 상품 정보 가져오기
    @Override
    public Item selectOneItem(long no) {
        try {
            return cMapper.selectOneItem(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 상품 번호에 해당하는 이미지 번호 가져오기
    @Override
    public List<Item> selectItemImageNoList(long itemno) {
        try {
            return cMapper.selectItemImageNoList(itemno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // 상품에 대한 열린 공구 가져오기 => 남은인원
    @Override
    public int countRemainingPerson(long purchaseno) {
        try {
            return cMapper.countRemainingPerson(purchaseno);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

     // 상품에 대한 열린 공구 가져오기 => 공구번호, 참여인원, 마감기한, 보관소 코드, 보관소이름
    @Override
    public List<Purchase> selectPurchaseList(long itemno) {
        try {
            return cMapper.selectPurchaseList(itemno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    

    
}
