package com.example.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dto.Admin;
import com.example.mapper.SecurityMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityAdminSeviceImpl implements UserDetailsService {

    final String format = "SecurityServiceImpl => {}";
    final SecurityMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info(format, username);

        Admin admin = mapper.selectAdminOne(username);

        if (admin != null) {
            System.out.println("id : " + admin.getId() + ", pw : " + admin.getPw());

            return User.builder()
                    .username(admin.getId())
                    .password(admin.getPw())
                    .roles("ADMIN")
                    .build();
        }

        return User.builder()
                .username("_").password("_").roles("_").build();
    }

}
