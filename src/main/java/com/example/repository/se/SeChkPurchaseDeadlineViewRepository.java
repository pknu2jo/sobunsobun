package com.example.repository.se;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.se.SeChkPurchaseDeadlineView;

@Repository
public interface SeChkPurchaseDeadlineViewRepository extends JpaRepository<SeChkPurchaseDeadlineView, BigDecimal> {
    
    List<SeChkPurchaseDeadlineView> findAll();
    
}
