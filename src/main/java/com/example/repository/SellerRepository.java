package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.SellerEntity;

public interface SellerRepository extends JpaRepository<SellerEntity, String>{
    SellerEntity findByNo(String no);
}
