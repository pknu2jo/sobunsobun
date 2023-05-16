package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Data;

@Data
@Immutable // 뷰
@Entity
@Table(name = "STAGENDER")
public class StagenderView {
    @Id
    @Column(name = "NO")
    private String no;
    
    @Column(name = "ITEMNAME")
    private String itemname;

    @Column(name = "GENDER")
    private String gender;
}
