package com.example.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ITEM")
@SequenceGenerator(name = "SEQ_ITEM_NO", sequenceName = "SEQ_ITEM_NO", initialValue = 1, allocationSize = 1)
public class Item {

    private long no;
    private String name;
    private long price;
    private long quantity;
    private Date regdate;
    private String regNo;
    private long scategoryCode;
}
