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

    /** 물품 일괄수정 */
    List<Item> findByNo(BigDecimal no);

    /** 사업자 번호에 해당하는 물품 갯수 가져오기 */
    long countByRegNo(String regNo);

}
