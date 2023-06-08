package com.example.service.gr;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.SellerEntity;
import com.example.repository.gr.grcompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GrAdminCompanyImpl implements GrAdminCompanyService {

    final grcompanyRepository gcRepository;

    @Override
    public List<SellerEntity> findAll() {
        try {
            return gcRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SellerEntity findByNo(String no) {
        try {
            return gcRepository.findByNo(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
