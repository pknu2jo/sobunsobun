package com.example.repository.gr;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.CustomerEntity;

@Repository
public interface grcustomerRepository extends JpaRepository<CustomerEntity, String> {

    List<CustomerEntity> findAll();

    // CustomerEntity findById();
}
