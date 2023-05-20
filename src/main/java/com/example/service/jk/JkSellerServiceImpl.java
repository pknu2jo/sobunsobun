package com.example.service.jk;

import org.springframework.stereotype.Service;

import com.example.dto.Seller;
import com.example.mapper.jk.SellerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 클래스에서만 가능함, 인터페이스에서는 안됨.
public class JkSellerServiceImpl implements JkSellerService {

    final SellerMapper sMapper;

    @Override
    public int joinSeller(Seller obj) {
        try {
            return sMapper.joinSeller(obj);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'sellerRegister'");
        }

    }

    @Override
    public Seller sellerLogin(Seller obj) {
        try {
            return sMapper.sellerLogin(obj);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'sellerLogin'");
        }

    }

    @Override
    public int updateSellerinfo(Seller obj) {
        try {
            return sMapper.updateSellerinfo(obj);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'updateSellerInfo'");
        }

    }

    @Override
    public int updateSellerPw(Seller obj) {
        try {
            return sMapper.updateSellerPw(obj);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'updateSellerPw'");
        }

    }

    @Override
    public int deleteSeller(Seller obj) {
        try {
            return sMapper.deleteSeller(obj);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'sellerUnRegister'");
        }

    }

    @Override
    public int findSellerPw(Seller obj) {
        try {
            return sMapper.findSellerPw(obj);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'findSellerPw'");
        }
    }

}
