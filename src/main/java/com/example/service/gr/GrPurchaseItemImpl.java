package com.example.service.gr;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.GrDate;
import com.example.dto.ItemImage;
import com.example.entity.gr.grgrpurchaseview;
import com.example.entity.gr.grpurchaseview;
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

}
