package com.example.service.km;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.Storage;
import com.example.dto.kmPurchaseView;
import com.example.entity.ItemImage;
import com.example.mapper.km.KmCustomerMapper;
import com.example.repository.km.kmItemImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KmCustomerServiceImpl implements KmCustomerService {

    final KmCustomerMapper cMapper;
    final kmItemImageRepository imageRepository;

// 물품 상세 조회 페이지

    // 상품 정보 가져오기
    @Override
    public Map<String, Object> selectOneItem(long no) {
        try {
            return cMapper.selectOneItem(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 상품 번호에 해당하는 이미지 번호 가져오기
    @Override
    public List<Long> selectItemImageNoList(long itemno) {
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
    public List<kmPurchaseView> selectPurchaseList(long itemno) {
        try {
            return cMapper.selectPurchaseList(itemno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 모든 보관소 정보 가져오기
    public List<Storage> selectStorageList() {
        try {
            return cMapper.selectStorageList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

// 결제 페이지
    // 공구 정보 한개 가져오기
    public kmPurchaseView selectOnePurchase(long purchaseNo) {
        try {
            return cMapper.selectOnePurchase(purchaseNo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 보관소 번호에 해당하는 보관소 정보 가져오기
    public String selectOneStorage(long storageNo) {
        try {
            return cMapper.selectOneStorage(storageNo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

// 이미지
    // 이미지 번호에 대한 정보 다 가져오기
    public ItemImage findById(BigDecimal no) {
        try {
            return imageRepository.findById(no).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    // itemno에 해당하는 모든 이미지 가져오기
    public List<ItemImage> findByItemNo_noOrderByNoAsc(BigDecimal no) {
        try {
            return imageRepository.findByItemNo_noOrderByNoAsc(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // itemno에 해당하는 이미지 중 가장 오래된 이미지 가져오기
    // public ItemImage findTop1ByItemNo_noOrderByNoAsc(BigDecimal no) {
    //     try {
    //         return imageRepository.findTop1ByItemNo_noOrderByNoAsc(no);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }
    
}
