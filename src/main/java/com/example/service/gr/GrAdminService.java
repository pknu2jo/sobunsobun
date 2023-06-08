package com.example.service.gr;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.CustomerEntity;

@Service
public interface GrAdminService {

    public List<CustomerEntity> findAll();

    public int countTodayCustomers();

    public int countByQuitchk();

    public int countByPurchase();

    public CustomerEntity save1(CustomerEntity obj);

    // public CustomerEntity findById(String id);

    // ------------------------------------------------------

}
