package com.example.service.jk;

import org.springframework.stereotype.Service;

import com.example.dto.Seller;
import com.example.entity.SellerEntity;
import com.example.mapper.jk.SellerMapper;
import com.example.repository.jk.JkSellerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 클래스에서만 가능, 인터페이스에서는 안됨.
public class JkSellerServiceImpl implements JkSellerService {

    final SellerMapper sMapper;
    final JkSellerRepository sRepository;

    @Override
    public int joinSeller(Seller obj) {
        try {
            return sMapper.joinSeller(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public Seller sellerLogin(Seller obj) {
        try {
            return sMapper.sellerLogin(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int updateSellerinfo(Seller obj) {
        try {
            return sMapper.updateSellerinfo(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public int updateSellerPw(Seller obj) {
        try {
            return sMapper.updateSellerPw(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public int deleteSeller(Seller obj) {
        try {
            return sMapper.deleteSeller(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public int findSellerPw(Seller obj) {
        try {
            return sMapper.findSellerPw(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Seller findSellerInfo(String sellerId) {
        try {
            return sMapper.findSellerInfo(sellerId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public int countByNo(String id) {
        try {
            return sRepository.countByNo(id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;

        }
    }

    @Override
    public SellerEntity findByNo(String id) {
        try {
            return sRepository.findByNo(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
}
