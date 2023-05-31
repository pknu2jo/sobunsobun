package com.example.repository.gr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.JjimEntity;

@Repository
public interface grjjimRepository extends JpaRepository<JjimEntity, String> {

}
