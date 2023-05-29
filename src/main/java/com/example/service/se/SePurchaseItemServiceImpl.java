package com.example.service.se;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.SeAroundPurchaseView;
import com.example.dto.SeDeadlinePurchaseDdayView;
import com.example.dto.SeManyPurchaseItemView;
import com.example.dto.SeSelectItemListView;
import com.example.entity.CustomerAddressEntity;
import com.example.entity.ItemImage;
import com.example.mapper.se.SePurchaseItemMapper;
import com.example.repository.se.SeCustomerAddressRepository;
import com.example.repository.se.SeCustomerRepository;
import com.example.repository.se.SeItemImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SePurchaseItemServiceImpl implements SePurchaseItemService {

    final SePurchaseItemMapper piMapper;
    final SeItemImageRepository iiRepository;
    final SeCustomerRepository cRepository;
    final SeCustomerAddressRepository caRepository;
    
    // --------------------------------------------------------------------------------------------
    // 공구가 많이 열린 물품 n개
    @Override
    public List<Map<String, Object>> selectManyPurchaseItem() {
        try {
            return piMapper.selectManyPurchaseItem();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SeManyPurchaseItemView> selectManyPurchaseItem1(long no) {
        try {
            return piMapper.selectManyPurchaseItem1(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // --------------------------------------------------------------------------------------------
    // 기한이 얼마 안 남은 공구 n 개
    @Override
    public List<SeDeadlinePurchaseDdayView> selectDeadLinePurchaseItem(long no) {
        try {
            return piMapper.selectDeadLinePurchaseItem(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // --------------------------------------------------------------------------------------------
    // 물품 대표이미지 가져오기
    @Override
    public ItemImage selectItemImageOne(BigDecimal itemno) {
        try {
            List<ItemImage> obj = iiRepository.findByItemNo_noAndFilenameNotLikeOrderByNoAsc(itemno, "%상세%");
            if(obj.isEmpty()){
                return null;
            }
            return obj.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // --------------------------------------------------------------------------------------------
    // 내 주위 실시간 공구 5개
    @Override
    public List<SeAroundPurchaseView> selectAroundPurchaseItem(String id) {
        try {
            CustomerAddressEntity obj = caRepository.findByCustomer_id(id).get(0);
            return piMapper.selectAroundPurchaseItem(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // --------------------------------------------------------------------------------------------
    // 물품목록 - 검색어
    @Override
    public List<SeSelectItemListView> selectSearchItem(SeSelectItemListView obj) {
       try {
        return piMapper.selectSearchItem(obj);
       } catch (Exception e) {
            e.printStackTrace();
            return null;
       }
    }

    // 물품목록 - 소분류
    @Override
    public List<SeSelectItemListView> selectScodeItem(SeSelectItemListView obj) {
        try {
            return piMapper.selectScodeItem(obj);
        } catch (Exception e) {
             e.printStackTrace();
             return null;
        }
    }

    // 물품목록 - 소분류 BEST
    @Override
    public List<SeSelectItemListView> selectScodeItemBest(long scode) {
        try {
            return piMapper.selectScodeItemBest(scode);
        } catch (Exception e) {
             e.printStackTrace();
             return null;
        }
    }

    // 물품이 현재 공구 중인지 확인
    @Override
    public long selectPurchaseChk(long itemno) {
        try {
            return piMapper.selectPurchaseChk(itemno);
        } catch (Exception e) {
             e.printStackTrace();
             return 0L;
        }
    }


    
}