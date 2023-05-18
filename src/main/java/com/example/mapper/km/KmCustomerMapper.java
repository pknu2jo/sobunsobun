package com.example.mapper.km;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.Item;
import com.example.dto.Purchase;

@Mapper
public interface KmCustomerMapper {

// 물품 상세 조회 페이지

    // 상품 정보 가져오기
    public Item selectOneItem(long no);

    // 상품 번호에 해당하는 이미지 번호 가져오기
    public List<Item> selectItemImageNoList(long itemno);

    // 상품에 대한 열린 공구 가져오기 -> 남은 인원
    public int countRemainingPerson(long purchaseno);

    // 상품에 대한 열린 공구 가져오기 => 공구번호, 참여인원, 마감기한, 보관소 코드, 보관소이름
    public List<Purchase> selectPurchaseList(long itemno);
        
}
