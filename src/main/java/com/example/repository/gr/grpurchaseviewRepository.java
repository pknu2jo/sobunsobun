package com.example.repository.gr;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.gr.grpurchaseview;

@Repository
public interface grpurchaseviewRepository extends JpaRepository<grpurchaseview, String> {

    // 내가 주문한 상품 목록
    List<grpurchaseview> findByMemid(String id);
}
