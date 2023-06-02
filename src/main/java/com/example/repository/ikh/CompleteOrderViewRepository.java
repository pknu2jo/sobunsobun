package com.example.repository.ikh;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.ikh.CompleteOrderView;

public interface CompleteOrderViewRepository extends JpaRepository<CompleteOrderView, BigDecimal>{
    
    List<CompleteOrderView> findByNo(String no);
}
