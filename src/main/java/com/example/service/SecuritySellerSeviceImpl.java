package com.example.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dto.Seller;
import com.example.dto.SellerUser;
import com.example.mapper.SecurityMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecuritySellerSeviceImpl implements UserDetailsService {
    
    final String format = "SecurityServiceImpl => {}";
    final SecurityMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info(format, username);

        Seller seller = mapper.selectSellerOne( username );

        if(seller != null) {
            System.out.println("no : " + seller.getNo() + ", pw : " + seller.getPw());

            Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList("SELLER");
            return new SellerUser(seller.getNo(), seller.getPw(), role);
        }

        return User.builder()
                    .username("_").password("_").roles("_").build();
    }


}
