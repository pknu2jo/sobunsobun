package com.example.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.StagenderView;

@Repository
public interface StagenderViewRepository extends JpaRepository<StagenderView, BigDecimal>{
    // 사업자 번호와 아이템 
    long countByGenderAndItemcodeAndNo(String gender, BigDecimal itemcode, String no);
}
