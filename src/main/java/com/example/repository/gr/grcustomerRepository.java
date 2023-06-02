package com.example.repository.gr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.AdminEntity;
import com.example.entity.CustomerEntity;

public interface grcustomerRepository extends JpaRepository<CustomerEntity, String> {

    List<CustomerEntity> findAll();

}
