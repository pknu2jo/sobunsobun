package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.SalesView;
import com.example.entity.SalesViewProjection;
import java.util.List;


public interface SalesViewRepository extends JpaRepository<SalesView, String>{
    // 전체 매출
    @Query(value = "SELECT sum(sv.itemprice) FROM SalesView sv")
    public long sumByItemprice();

    @Query("SELECT sv.regdate AS monthly, SUM(sv.itemprice) AS amount FROM SalesView sv GROUP BY sv.regdate")
    List<SalesViewProjection> findMonthlySales();
}
