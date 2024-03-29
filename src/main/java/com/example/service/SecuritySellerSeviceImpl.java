package com.example.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.SellerEntity;
import com.example.mapper.SecurityMapper;
import com.example.repository.jk.JkSellerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecuritySellerSeviceImpl implements UserDetailsService {

    final JkSellerRepository sRepository;
    final SecurityMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("JkSellerRepository username => {}", username);

        // 업체 정보 불러오기
        SellerEntity obj = sRepository.findById(username).orElse(null);

        if (obj != null) { // 아이디(사업자번호)가 있는경우
            log.info("JkSellerRepository Seller => {}", obj.toString());

            return User.builder().username(obj.getNo()).password(obj.getPw()).roles("SELLER").build();
        }

        // 아이디(사업자번호)가 없는 경우는 User로 처리 (User는 정보 null 상태)
        return User.builder()
                .username("_")
                .password("_")
                .roles("_")
                .build();
    }

}
