package com.example.repository.km;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.ReviewEntity;

@Repository
public interface KmReviewRepository extends JpaRepository<ReviewEntity, BigDecimal> {

    // 이미지 등록을 위한 리뷰 객체 받기
    public ReviewEntity findByPurchaseOrderEntity_no(String orderNo);

    // 물품별 리뷰 전체 조회하기
    // public List<ReviewEntity> findByItemEntity_noOrderByNoDesc(BigDecimal itemNo, Pageable Pageable);
    public List<ReviewEntity> findByItemEntity_noOrderByNoDesc(BigDecimal itemNo);
}
