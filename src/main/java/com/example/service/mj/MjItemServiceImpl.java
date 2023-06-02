package com.example.service.mj;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Item;
import com.example.entity.Lcategory;
import com.example.entity.Mcategory;
import com.example.entity.Scategory;
import com.example.entity.mj.ItemCategoryView;
import com.example.mapper.mj.mjItemMapper;
import com.example.repository.mj.ItemCategoryViewRepository;
import com.example.repository.mj.ItemRepository;
import com.example.repository.mj.LcateRepository;
import com.example.repository.mj.McateRepository;
import com.example.repository.mj.ScateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MjItemServiceImpl implements MjItemService {
    
    final ItemCategoryViewRepository icViewRepository;
    final ItemRepository iRepository;
    final LcateRepository lRepository;
    final McateRepository mRepository;
    final ScateRepository sRepository;
    final mjItemMapper iMapper;

    /** 사업자 번호에 해당하는 물품 갯수 가져오기 */
    @Override
    public long countItems(String regno) {
        try {
            return iRepository.countByRegNo(regno);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }



    /** 물품 등록 */
    @Override
    public Item saveItem(Item obj) {
        try {
            return iRepository.save(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // /** 물품 일괄수정 */
    // @Override
    // public List<Item> findByNo(BigDecimal no) {
    //     try {
    //         return iRepository.findByNo(no);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    /** 각 대분류코드에 해당하는 중분류 리스트 */
    @Override
    public List<Mcategory> findByLcategoryCode_code(BigDecimal code) {
        try {
            return mRepository.findByLcategoryCode_code(code);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 각 대분류코드,중분류코드에 해당하는 소분류 리스트 */
    @Override
    public List<Scategory> findByMcategoryCode_code(BigDecimal mcode) {
        try {
            return sRepository.findByMcategoryCode_code(mcode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // @Override
    // public List<Scategory> findByMcategoryCode_codeAndMcategoryCode_LcategoryCode_code(BigDecimal mcode,
    //         BigDecimal lcode) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'findByMcategoryCode_codeAndMcategoryCode_LcategoryCode_code'");
    // }

    /** 업체 사업자번호에 해당하는 전체 물품조회, 내림차순 정렬 */
    @Override
    public List<ItemCategoryView> findAllByRegNoOrderByNoDesc(String regNo) {
        try {
            return icViewRepository.findAllByRegNoOrderByNoDesc(regNo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 대분류별 물품조회, 내림차순 정렬 */
    @Override
    public List<ItemCategoryView> findAllByRegNoAndLcategoryCodeOrderByNoDesc(String regNo, BigDecimal lcode) {
        try {
            return icViewRepository.findAllByRegNoAndLcategoryCodeOrderByNoDesc(regNo, lcode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 중분류별 물품조회, 내림차순 정렬 */
    @Override
    public List<ItemCategoryView> findAllByRegNoAndMcategoryCodeOrderByNoDesc(String regNo, BigDecimal mcode) {
        try {
            return icViewRepository.findAllByRegNoAndMcategoryCodeOrderByNoDesc(regNo, mcode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 소분류별 물품조회, 내림차순 정렬 */
    @Override
    public List<ItemCategoryView> findAllByRegNoAndScategoryCodeOrderByNoDesc(String regNo, BigDecimal scode) {
        try {
            return icViewRepository.findAllByRegNoAndScategoryCodeOrderByNoDesc(regNo, scode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /** 물품번호에 대한 물품 정보 가져오기 */
    @Override
    public Item findByItemNo(BigDecimal no) {
        try {
            return iRepository.findById(no).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 물품 일괄 수정 */
    @Override
    public List<Item> updateItems(List<Item> list) {
        try {
            return iRepository.saveAll(list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 물품번호N개에 대한 물품정보가져오기 */
    @Override
    public List<Item> findAllByItemNo(List<BigDecimal> chk) {
        try {
            return iRepository.findAllById(chk);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 물품 일괄 삭제 */
    @Override
    public int deleteItemBatch(long[] no) {
        try {
            return iMapper.deleteItemBatch(no);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /** 대분류 전체 가져오기 */
    @Override
    public List<Lcategory> findAllLcategory() {
        try {
            return lRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    


    

}
