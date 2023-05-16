package com.example.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"password"})
public class CustomerUser extends User {

    private String id; // username
    private String password; // password
    private Collection<GrantedAuthority> authorities; // role
    private String nickname;

    public CustomerUser(String username, String password, Collection<GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomerUser(String username, String password, 
                        Collection<GrantedAuthority> authorities, String nickname) {
        super(username, password, authorities);
        this.id = username;
        this.password = password;
        this.authorities = authorities;
        this.nickname = nickname;
    }

    
}
