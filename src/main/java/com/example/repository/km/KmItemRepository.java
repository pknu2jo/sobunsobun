package com.example.repository.km;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Item;

public interface KmItemRepository extends JpaRepository<Item, BigDecimal> {
    
}
