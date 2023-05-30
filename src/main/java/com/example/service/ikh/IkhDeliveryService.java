package com.example.service.ikh;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public interface IkhDeliveryService {    

    // 배송상태설정
    public int updateStatus(BigDecimal deliveryNo, BigDecimal no);
}
