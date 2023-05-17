package com.example.repository.se;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Customer;

@Repository
public interface SeCustomerRepository extends JpaRepository<Customer, String> {

    int countById(String id);
    
}
