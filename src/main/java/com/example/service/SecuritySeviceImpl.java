package com.example.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dto.Admin;
import com.example.dto.Customer;
import com.example.dto.Seller;
import com.example.mapper.SecurityMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecuritySeviceImpl implements UserDetailsService {
    
    final String format = "SecurityServiceImpl => {}";
    final SecurityMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info(format, username);

        Customer customer = mapper.selectCustomerOne(username);
        
        if(customer != null) {
            System.out.println("id : " + customer.getId() + ", pw : " + customer.getPw());

            return User.builder()
                        .username(customer.getId())
                        .password(customer.getPw())
                        .roles("CUSTOMER")
                        .build();
        }

        Seller seller = mapper.selectSellerOne(username);
        if(seller != null) {
            System.out.println("id : " + seller.getNo() + ", pw : " + seller.getPw());

            return User.builder()
                        .username(seller.getNo())
                        .password(seller.getPw())
                        .roles("SELLER")
                        .build();
        }

        Admin admiin = mapper.selectAdminOne(username);
        if(admiin != null) {
            System.out.println("id : " + admiin.getId() + ", pw : " + admiin.getPw());

            return User.builder()
                        .username(admiin.getId())
                        .password(admiin.getPw())
                        .roles("ADMIN")
                        .build();
        }

        return User.builder()
                    .username("_").password("_").build();
    }


}
