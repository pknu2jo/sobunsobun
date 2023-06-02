package com.example.repository.ikh;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.ikh.CompleteOrderMemberView;

public interface CompleteOrderMemberViewRepository extends JpaRepository<CompleteOrderMemberView, BigDecimal>{
    
    List<CompleteOrderMemberView> findByNo(String no);

    List<CompleteOrderMemberView> findByNoAndPno(String no, BigDecimal pno);
}
