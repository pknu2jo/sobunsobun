package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "CNOTIFICATION")
@SequenceGenerator(name = "seq_cnoti_no", sequenceName = "seq_cnoti_no", initialValue = 1, allocationSize = 1)
public class CNotificationEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cnoti_no")
    @Column(name = "no")
    private BigDecimal no;

    @Column(name = "type")
    private String type;

    @Column(name = "content")
    private String content;

    @Column(name = "url")
    private String url;

    @CreationTimestamp
    @Column(name = "regdate", insertable = true, updatable = false)
    private Date regdate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "memid", referencedColumnName = "id")
    private CustomerEntity customerEntity;

}
