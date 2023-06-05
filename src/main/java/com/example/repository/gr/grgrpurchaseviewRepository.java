
package com.example.repository.gr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.gr.grgrpurchaseview;

@Repository
public interface grgrpurchaseviewRepository extends JpaRepository<grgrpurchaseview, String> {

    // 내가 주문한 상품 목록
    List<grgrpurchaseview> findByMemid(String id);

}
