package com.example.repository.gr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.AdminEntity;

@Repository
public interface gradminRepository extends JpaRepository<AdminEntity, String> {

}
