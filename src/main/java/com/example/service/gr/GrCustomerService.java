package com.example.service.gr;

import org.springframework.stereotype.Service;

@Service
public interface GrCustomerService {

    // 전체 공구 참여 횟수 카운트
    public int countPurchase(String id);

}
