package com.example.repository.ik;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.ik.SalesView;
import com.example.entity.ik.SalesViewProjection;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SalesViewRepository extends JpaRepository<SalesView, String>{
    // 전체 매출(전체)
    @Query(value = "SELECT sum(sv.itemprice) FROM SalesView sv Where sv.no = :no")
    public long sumByItemprice(String no);

    // 월간 매출(전체)
    @Query("SELECT sv.regdate AS monthly, SUM(sv.itemprice) AS amount FROM SalesView sv where sv.no = :no GROUP BY sv.regdate")
    List<SalesViewProjection> findMonthlySales(String no);

    // 한 물품에 대한 전체 매출
    @Query(value = "SELECT sum(sv.itemprice) FROM SalesView sv Where sv.no = :no AND sv.itemno = :itemno")
    public long sumByItempriceAndItemno(String no, BigDecimal itemno);

    // 월간 매출(전체)
    @Query("SELECT sv.regdate AS monthly, SUM(sv.itemprice) AS amount FROM SalesView sv where sv.no = :no AND sv.itemno = :itemno GROUP BY sv.regdate")
    List<SalesViewProjection> findMonthlySalesAndItemno(String no, BigDecimal itemno);
}
