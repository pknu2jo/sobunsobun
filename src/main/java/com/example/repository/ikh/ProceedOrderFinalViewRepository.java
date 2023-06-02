package com.example.repository.ikh;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.ikh.ProceedOrderFinalView;

public interface ProceedOrderFinalViewRepository extends JpaRepository<ProceedOrderFinalView, BigDecimal>{
    
    List<ProceedOrderFinalView> findByNo(String no);
}
