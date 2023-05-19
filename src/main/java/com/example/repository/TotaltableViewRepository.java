package com.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.TotaltableView;

@Repository
public interface TotaltableViewRepository extends JpaRepository<TotaltableView, BigDecimal>{
    
    List<TotaltableView> findByNo(String no);
}
