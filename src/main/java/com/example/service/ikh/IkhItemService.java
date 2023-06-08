package com.example.service.ikh;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.example.entity.ikh.BestSellView;
import com.example.entity.ikh.SalesViewProjection;
import com.example.entity.ikh.StagenderView;
import com.example.entity.ikh.TopthreeView;
import com.example.entity.ikh.TotaltableView;

@Service
public interface IkhItemService {    
    // 사업자등록번호에 맞는 성별 인원수 구하기
    public long countByGenderAndNo(String gender, String no);

    // 사업자등록번호에 맞는 지역별 인원수 구하기
    // SELECT count(*) FROM TOTALLOCATION t WHERE LOCATION LIKE '%남구%' AND NO='4564546544'
    public long countByNoAndLocationContaining(String no, String location);

    // 사업자 번호에 맞는 테이블 구하기
    List<TotaltableView> findByNo(String no);

    // 상위3개 구하기    
    List<TotaltableView> findBest(@Param("no") String no, Pageable pageable);

    // 전체 매출(전체)
    public long sumByItemprice(String no);

    // 월간 매출(전체)
    public List<SalesViewProjection> findMonthlySales(String no);

    // 한 물품에 대한 전체 매출
    public long sumByItempriceAndItemno(String no, BigDecimal itemno);

    // 월간 매출(전체)
    public List<SalesViewProjection> findMonthlySalesAndItemno(String no, BigDecimal itemno);

    // 사업자 번호와 아이템 
    public long countByGenderAndItemcodeAndNo(String gender, BigDecimal itemcode, String no);
    
    // 물품 명 받아오기
    public StagenderView findByItemcode(BigDecimal itemcode);

    // 사업자등록번호, 아이템 코드 에 맞는 지역별 인원수 구하기    
    public long countByNoAndItemcodeAndLocationContaining(String no, BigDecimal itemcode ,String location);

    // 사업자 번호에 맞는 테이블 구하기(가장 잘팔린거)
    public BestSellView findByNobest(String no);

    // 사업자 번호에 맞는 테이블 구하기(탑 3)
    List<TopthreeView> findByNoTh(String no);
}
