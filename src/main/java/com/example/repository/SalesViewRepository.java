package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.SalesView;

public interface SalesViewRepository extends JpaRepository<SalesView, String>{
    
}
