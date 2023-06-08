package com.example.service.gr;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.example.dto.Customer;
import com.example.dto.CustomerAddress;
import com.example.mapper.gr.GrCustomerMapper;
import com.example.repository.gr.grjjimRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GrCustomerServiceImpl implements GrCustomerService {

    final GrCustomerMapper cMapper;
    final grjjimRepository jRepository;

    // 아이디를 이용해서 고객 정보 받아오기
    @Override
    public Customer selectCustomerOne1(String userid) {
        try {
            return cMapper.selectCustomerOne1(userid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 전체 공구 참여 횟수 카운트
    @Override
    public int countPurchase(String id) {
        try {
            return cMapper.countPurchase(id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 회원탈퇴
    @Override
    public int myaccountdrop(Customer customer) {
        try {
            return cMapper.myaccountdrop(customer);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 회원탈퇴시 주소 삭제
    @Override
    public int deletemyaddress(CustomerAddress customeraddress) {
        try {
            return cMapper.deletemyaddress(customeraddress);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 회원정보수정
    @Override
    public int updateinfo(Customer customer) {
        try {
            return cMapper.updateinfo(customer);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 회원주소조회
    @Override
    public CustomerAddress selectOneCustomerAddress(String id) {
        try {
            return cMapper.selectOneCustomerAddress(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 회원주소수정
    @Override
    public int updateaddress(CustomerAddress customeraddress) {
        try {
            return cMapper.updateaddress(customeraddress);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 회원 비밀번호 변경
    @Override
    public int updatepw(Customer customer) {
        try {
            return cMapper.updatepw(customer);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Customer searchkakao(String id) {
        try {
            return cMapper.searchkakao(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
