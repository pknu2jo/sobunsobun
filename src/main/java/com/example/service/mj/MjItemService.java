package com.example.service.mj;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Item;
import com.example.entity.Lcategory;
import com.example.entity.Mcategory;
import com.example.entity.Scategory;
import com.example.entity.mj.ItemCategoryView;

@Service
public interface MjItemService {
    
    /** 물품번호에 대한 물품 정보 가져오기 */
    public Item findByItemNo(BigDecimal no);

    /** 물품 일괄 수정 */
    public List<Item> updateItems(List<Item> list); 
    
    /** 물품번호N개에 대한 물품정보가져오기 */
    public List<Item> findAllByItemNo(List<BigDecimal> chk);

    /** 물품 일괄 삭제 */
    public int deleteItemBatch(long[] no);

    /** 대분류 전체 가져오기 */
    public List<Lcategory> findAllLcategory();

    /** 업체 사업자번호에 해당하는 전체물품 조회, 내림차순 정렬*/
    // public List<Item> findAllByRegNoOrderByNoDesc(String regno);

    /** 물품 일괄수정 */
    // public List<Item> findByNo(BigDecimal no);

    /** 각 대분류코드에 해당하는 중분류 리스트 */
    public List<Mcategory> findByLcategoryCode_code(BigDecimal code);

    /** 각 대분류코드,중분류코드에 해당하는 소분류 리스트 */
    public List<Scategory> findByMcategoryCode_code(BigDecimal mcode);

    /** 업체 사업자번호에 해당하는 전체 물품조회, 내림차순 정렬 */
    public List<ItemCategoryView> findAllByRegNoOrderByNoDesc(String regNo);

    /** 대분류별 물품조회 */
    public List<ItemCategoryView> findAllByRegNoAndLcategoryCodeOrderByNoDesc(String regNo, BigDecimal lcode);
    
    /** 중분류별 물품조회 */
    public List<ItemCategoryView> findAllByRegNoAndMcategoryCodeOrderByNoDesc(String regNo, BigDecimal mcode);

    /** 소분류별 물품조회 */
    public List<ItemCategoryView> findAllByRegNoAndScategoryCodeOrderByNoDesc(String regNo, BigDecimal scode);



    // // 물품 상세 조회 페이지----------------------------------------------------------------------------------------

    // // 물품 조회
    //     // 상품 정보 가져오기
    //     public Map<String, Object> selectOneItem(long no);

    //     // 상품 번호에 해당하는 이미지 번호 가져오기
    //     public List<Long> selectItemImageNoList(long itemno);
        
    //     // 상품에 대한 열린 공구 가져오기 => 남은인원
    //     public int countRemainingPerson(long purchaseno);

}
