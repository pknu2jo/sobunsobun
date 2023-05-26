package com.example.repository.mj;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Item;


@Repository
public interface ItemRepository extends JpaRepository<Item, BigDecimal> {
    // // select * from item i where regno=#{regno} order by no desc;
    List<Item> findAllByRegNoOrderByNoDesc(String regno);

    /** 물품 일괄수정 */
    List<Item> findByNo(BigDecimal no);


    /** 가장최근에 등록한 물품 번호 조회 */
    // Item findTop1ByNoAndByRegNoOrderByNoAsc(BigDecimal no);
    
    // // 프로젝션 test
    // ItemProjection findByMcategoryCode_code(BigDecimal mcode);
    // List<ItemProjection> findByRegNoAndMcategoryCode_code(String regno, BigDecimal lcode);

    /** 중분류별 아이템조회 */
    // List<Item> findAllByRegNoAndScategoryCode_McategoryCode_codeOrderByNoDesc(String regno, BigDecimal mcode);


    /** 소분류별 아이템조회 */
    // List<Item> findAllByRegNoAndScategoryCode_codeOrderByNoDesc(String regno, BigDecimal scode);
    // List<Item> findAllByScategoryCode_code(String scode);

    // /** 물품번호로 물품삭제 */
    // int deleteByNo(List<BigDecimal> chk);
    // int deleteAllByRegNo(List<BigDecimal> chk);


    // Optional<T> findByNo(ID id)
    

}
