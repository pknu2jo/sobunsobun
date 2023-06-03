package com.example.repository.gr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.SellerEntity;

@Repository
public interface grcompanyRepository extends JpaRepository<SellerEntity, String> {

    List<SellerEntity> findAll();

    SellerEntity findByNo(String no);

}
