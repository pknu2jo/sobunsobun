package com.example.dto;

import java.math.BigDecimal;
import java.util.List;

import com.example.entity.Lcategory;
import com.example.entity.Mcategory;
import com.example.entity.Scategory;

import lombok.Data;

@Data
public class Category {
    
    List<Lcategory> Llist = null;
    List<Mcategory> Mlist = null;
    List<Scategory> Slist = null;


    // final long[] LcateCode = {0,100,200,300,400};
    // final String[] LcateName = {"전체", "생활용품","패션의류/잡화", "식품","헬스/건강식품"};

    private int page=1;
    
    private BigDecimal Lcode= BigDecimal.valueOf(0);
    private BigDecimal Mcode= BigDecimal.valueOf(0);
    private BigDecimal Scode= BigDecimal.valueOf(0);
    
    private String text="";
}
