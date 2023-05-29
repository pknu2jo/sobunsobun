package com.example.repository.mj;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Scategory;

@Repository
public interface ScateRepository extends JpaRepository<Scategory, BigDecimal>{
    
    /** 각 대분류코드,중분류코드에 해당하는 소분류 리스트 */
    List<Scategory> findByMcategoryCode_code(BigDecimal mcode);
    List<Scategory> findByMcategoryCode_codeAndMcategoryCode_LcategoryCode_code(BigDecimal mcode, BigDecimal lcode );
}
