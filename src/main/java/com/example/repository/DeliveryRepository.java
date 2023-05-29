package com.example.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.DeliveryEntity;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, BigDecimal>{    
    
}
