package com.example.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dto.Admin;
import com.example.dto.Customer;
import com.example.dto.CustomerUser;
import com.example.dto.Seller;
import com.example.dto.SellerUser;
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

        Customer customer = mapper.selectCustomerOne( username );
        Seller seller = mapper.selectSellerOne( username );
        Admin admin = mapper.selectAdminOne( username );

        if(customer != null) {
            System.out.println("id : " + customer.getId() + ", pw : " + customer.getPw());

            Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList("CUSTOMER");
            return new CustomerUser(customer.getId(), customer.getPw(), 
                                    role, customer.getNickname());
        } else if(seller != null) {
            System.out.println("no : " + seller.getNo() + ", pw : " + seller.getPw());

            Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList("SELLER");
            return new SellerUser(seller.getNo(), seller.getPw(), 
                                  role, seller.getName(), seller.getPhone(), 
                                  seller.getEmail(), seller.getAddress());
        } else if(admin != null) {
            System.out.println("id : " + admin.getId() + ", pw : " + admin.getPw());

            return User.builder()
                        .username(admin.getId())
                        .password(admin.getPw())
                        .roles("ADMIN", "SELLER")
                        .build();
        }

        return User.builder()
                    .username("_").password("_").build();
    }


}
