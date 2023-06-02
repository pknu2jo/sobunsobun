package com.example.mapper.ikh;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IkhDeliveryMapper {
    
    public int updateStatus(BigDecimal deliveryNo, BigDecimal no);
}
