package com.example.service.gr;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.SellerEntity;

@Service
public interface GrAdminCompanyService {

    public List<SellerEntity> findAll();

    public SellerEntity findByNo(String no);

}
