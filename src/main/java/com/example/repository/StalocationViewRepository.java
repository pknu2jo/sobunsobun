package com.example.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.StalocationView;

@Repository
public interface StalocationViewRepository extends JpaRepository<StalocationView, String>{
    
}
