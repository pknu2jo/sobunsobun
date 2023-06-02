package com.example.service.ikh;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.mapper.ikh.IkhDeliveryMapper;

public class IkhDeliveryServiceImpl implements IkhDeliveryService{

    @Autowired
    IkhDeliveryMapper ikhDeliveryMapper;

    // 배송상태설정
    @Override
    public int updateStatus(BigDecimal deliveryNo, BigDecimal no) {
        try {
            return ikhDeliveryMapper.updateStatus(deliveryNo, no);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }    
}
