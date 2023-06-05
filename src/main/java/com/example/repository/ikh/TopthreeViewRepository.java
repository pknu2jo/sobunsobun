package com.example.repository.ikh;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.ikh.TopthreeView;

@Repository
public interface TopthreeViewRepository extends JpaRepository<TopthreeView, BigDecimal>{
    
    // 사업자 번호에 맞는 테이블 구하기
    List<TopthreeView> findByNo(String no);
}
