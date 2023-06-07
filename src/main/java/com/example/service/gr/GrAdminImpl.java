package com.example.service.gr;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.CustomerEntity;
import com.example.repository.gr.grcustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GrAdminImpl implements GrAdminService {

    final grcustomerRepository gcRepository;

    @Override
    public List<CustomerEntity> findAll() {
        try {
            return gcRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int countTodayCustomers() {
        try {
            return gcRepository.countTodayCustomers();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int countByQuitchk() {
        try {
            return gcRepository.countByQuitchk();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int countByPurchase() {
        try {
            return gcRepository.countByPurchase();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // --------------------------------------------------------------------------------

}
