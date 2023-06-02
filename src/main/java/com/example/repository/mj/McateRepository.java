package com.example.repository.mj;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Lcategory;
import com.example.entity.Mcategory;

@Repository
public interface McateRepository extends JpaRepository<Mcategory, BigDecimal>{
    
    /** 각 대분류코드에 해당하는 중분류 리스트 */
    List<Mcategory> findByLcategoryCode_code(BigDecimal code);
}
