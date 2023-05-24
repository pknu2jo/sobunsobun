package com.example.repository.ik;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.ik.OrderView;

public interface OrderViewRepository extends JpaRepository<OrderView, String>{
    
    List<OrderView> findByNo(String no);
    
    List<OrderView> findByNoAndStateBetween(String no, BigDecimal minState, BigDecimal maxState);

    List<OrderView> findByNoAndState(String no, BigDecimal state);
}
