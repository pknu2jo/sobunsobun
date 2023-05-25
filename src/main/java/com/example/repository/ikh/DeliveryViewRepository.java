package com.example.repository.ikh;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.ikh.DeliveryView;

public interface DeliveryViewRepository extends JpaRepository<DeliveryView, BigDecimal>{
    
    long countByDeliveryAndNo(BigDecimal delivery, String no);
    
    List<DeliveryView> findByNo(String no);
}
