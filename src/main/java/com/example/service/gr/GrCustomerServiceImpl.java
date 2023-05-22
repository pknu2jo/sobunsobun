package com.example.service.gr;

import org.springframework.stereotype.Service;

import com.example.mapper.gr.GrCustomerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GrCustomerServiceImpl implements GrCustomerService {

    final GrCustomerMapper cMapper;

    // 전체 공구 참여 횟수 카운트
    @Override
    public int countPurchase(String id) {
        try {
            return cMapper.countPurchase(id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
