package com.example.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dto.Customer;
import com.example.mapper.km.CustomerMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecuritySeviceImpl implements UserDetailsService {
    
    final String format = "SecurityServiceImpl => {}";
    final CustomerMapper cMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info(format, username);

        Customer customer = cMapper.selectCustomerOne(username);

        System.out.println("id : " + username + ", pw : " + customer.getPw());
        
        if(customer != null) {
            return User.builder()
                        .username(customer.getId())
                        .password(customer.getPw())
                        .roles("CUSTOMER")
                        .build();
        }

        return User.builder()
                    .username("_").password("_").build();
    }


}
