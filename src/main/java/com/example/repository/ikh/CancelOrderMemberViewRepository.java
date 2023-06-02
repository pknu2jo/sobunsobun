package com.example.repository.ikh;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.ikh.CancelOrderMemberView;

public interface CancelOrderMemberViewRepository extends JpaRepository<CancelOrderMemberView, BigDecimal>{
    
    List<CancelOrderMemberView> findByNo(String no);

    List<CancelOrderMemberView> findByNoAndPno(String no, BigDecimal pno);
}
