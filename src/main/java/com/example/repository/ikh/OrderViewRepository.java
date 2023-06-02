package com.example.repository.ikh;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.ikh.OrderView;

public interface OrderViewRepository extends JpaRepository<OrderView, BigDecimal>{
    
    List<OrderView> findByNo(String no);

    List<OrderView> findByNoAndState(String no, BigDecimal state);
}
