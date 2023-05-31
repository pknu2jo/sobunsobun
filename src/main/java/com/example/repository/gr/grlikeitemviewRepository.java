package com.example.repository.gr;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.gr.grlikeitemview;

@Repository
public interface grlikeitemviewRepository extends JpaRepository<grlikeitemview, BigDecimal> {

    // 찜한 상품 목록
    List<grlikeitemview> findByMemid(String id, Pageable pageable);

    // 찜한 상품 목록 수
    int countByMemid(String id);
}
