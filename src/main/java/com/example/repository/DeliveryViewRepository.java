package com.example.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.DeliveryView;

public interface DeliveryViewRepository extends JpaRepository<DeliveryView, BigDecimal>{
    
    long countByDeliveryAndNo(BigDecimal delivery, String no);
    
}
