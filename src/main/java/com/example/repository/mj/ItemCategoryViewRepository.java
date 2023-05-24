package com.example.repository.mj;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.mj.ItemCategoryView;

public interface ItemCategoryViewRepository extends JpaRepository<ItemCategoryView, BigDecimal>{
    
    /** 전체 물품조회 */
    List<ItemCategoryView> findAllByRegNoOrderByNoDesc(String regNo);

    /** 대분류별 물품조회 */
    List<ItemCategoryView> findAllByRegNoAndLcategoryCodeOrderByNoDesc(String regNo, BigDecimal lcode);
    
    /** 중분류별 물품조회 */
    List<ItemCategoryView> findAllByRegNoAndMcategoryCodeOrderByNoDesc(String regNo, BigDecimal mcode);

    /** 소분류별 물품조회 */
    List<ItemCategoryView> findAllByRegNoAndScategoryCodeOrderByNoDesc(String regNo, BigDecimal scode);

    
}
