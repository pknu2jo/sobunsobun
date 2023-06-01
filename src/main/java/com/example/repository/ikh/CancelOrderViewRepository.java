package com.example.repository.ikh;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.ikh.CancelOrderView;

public interface CancelOrderViewRepository extends JpaRepository<CancelOrderView, BigDecimal>{
    
    List<CancelOrderView> findByNo(String no);
}
