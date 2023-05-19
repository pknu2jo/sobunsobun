package com.example.repository.mj;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Item;


@Repository
public interface ItemRepository extends JpaRepository<Item, BigDecimal> {
    // // select * from item i where regno=#{regno} order by no desc;
    List<Item> findAllByRegNoOrderByNoDesc(String regno);

    /** 소분류별 아이템조회 */
    List<Item> findAllByRegNoAndScategoryCode_codeOrderByNoDesc(String regno, String scode);
    // List<Item> findAllByScategoryCode_code(String scode);
}
