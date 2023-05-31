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

    // 아이디와 아이템번호가 같을 경우 카운트
    // 있으면 1, 없으면 0
    int getCountByMemidAndItemno(String id, long itemno);
}
