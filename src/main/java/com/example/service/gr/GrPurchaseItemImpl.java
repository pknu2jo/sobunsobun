package com.example.service.gr;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.GrDate;
import com.example.entity.gr.grgrpurchaseview;
import com.example.mapper.gr.GrPurchaseItemMapper;
import com.example.repository.gr.grgrpurchaseviewRepository;
import com.example.repository.gr.grjjimRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GrPurchaseItemImpl implements GrPurchaseItemService {

    final GrPurchaseItemMapper piMapper;
    final grgrpurchaseviewRepository grpRepository;
    final grjjimRepository gjRepository;

    @Override
    public List<Map<String, Object>> selectManyPurchaseItem() {
        try {
            return piMapper.selectManyPurchaseItem();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 전체 개수(페이지네이션용)
    @Override
    public long countMyOrderList(String id) {
        try {
            return piMapper.countMyOrderList(id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 페이지네이션
    @Override
    public List<grgrpurchaseview> selectMyOrderListPage(Map<String, Object> map) {
        try {
            return piMapper.selectMyOrderListPage(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long countMyOrderListDate(GrDate grDate) {
        try {
            return piMapper.countMyOrderListDate(grDate);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<grgrpurchaseview> MyOrderList(GrDate grDate) {
        try {
            return piMapper.MyOrderList(grDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<grgrpurchaseview> searchMyOrderList(GrDate grdate) {
        try {
            return piMapper.searchMyOrderList(grdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 내가 주문한 상품 목록
    @Override
    public List<grgrpurchaseview> findByMemid(String id) {
        try {
            return grpRepository.findByMemid(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
