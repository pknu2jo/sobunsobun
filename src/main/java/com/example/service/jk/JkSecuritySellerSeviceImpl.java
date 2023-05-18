package com.example.service.jk;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dto.SellerUser;
import com.example.entity.Seller;
import com.example.repository.jk.JkSellerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JkSecuritySellerSeviceImpl implements UserDetailsService {
    
    final String format = "SecurityServiceImpl => {}";
    final JkSellerRepository sRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(format, username);

        Seller obj = sRepository.findById( username ).orElse(null);
        log.info(format, obj.toString());

        if(obj != null) { // 아이디(사업자번호)가 있는경우
            log.info(format, obj.toString());
            // Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList("SELLER");
            return User.builder()
            .username(obj.getNo()) // 아이디
            .password(obj.getPw()) // 암호
            .roles("SELLER").build(); // 권한
        }
        
        // 아이디(사업자번호)가 없는 경우는 User로 처리 (User는 정보 null 상태)
        return User.builder()
                    .username("_")
                    .password("_")
                    .roles("_")
                    .build();
    }


}
