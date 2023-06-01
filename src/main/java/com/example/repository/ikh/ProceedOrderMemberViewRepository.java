package com.example.repository.ikh;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.ikh.ProceedOrderMemberView;

public interface ProceedOrderMemberViewRepository extends JpaRepository<ProceedOrderMemberView, BigDecimal>{
    
    List<ProceedOrderMemberView> findByNo(String no);

    List<ProceedOrderMemberView> findByNoAndPno(String no, BigDecimal pno);
}
