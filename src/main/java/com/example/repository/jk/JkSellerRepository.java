package com.example.repository.jk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Seller;

@Repository
public interface JkSellerRepository extends JpaRepository<Seller, String> {

    
}
