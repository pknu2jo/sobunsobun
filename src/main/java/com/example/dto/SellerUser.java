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
public class SellerUser extends User {

    private String no; // username
    private String password; // password
    private Collection<GrantedAuthority> authorities; // role
    private String name;
    private String phone;
    private String email;
    private String address;

    public SellerUser(String username, String password, Collection<GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SellerUser(String username, String password, 
                        Collection<GrantedAuthority> authorities, String name,
                        String phone, String email, String address) {
        super(username, password, authorities);
        this.no = username;
        this.password = password;
        this.authorities = authorities;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }
}
