package com.example.service.se;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.SeAroundPurchaseView;
import com.example.dto.SeDeadlinePurchaseDdayView;
import com.example.dto.SeManyPurchaseItemView;
import com.example.dto.SeSelectItemListView;
import com.example.entity.CustomerAddressEntity;
import com.example.entity.ItemImage;
import com.example.entity.JjimEntity;
import com.example.entity.PurchaseStatusEntity;
import com.example.entity.se.SeChkPurchaseDeadlineView;
import com.example.mapper.se.SePurchaseItemMapper;
import com.example.repository.se.SeChkPurchaseDeadlineViewRepository;
import com.example.repository.se.SeCustomerAddressRepository;
import com.example.repository.se.SeCustomerRepository;
import com.example.repository.se.SeItemImageRepository;
import com.example.repository.se.SeJjimRepository;
import com.example.repository.se.SePurchaseStatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SePurchaseItemServiceImpl implements SePurchaseItemService {

    final SePurchaseItemMapper piMapper;
    final SeItemImageRepository iiRepository;
    final SeCustomerRepository cRepository;
    final SeCustomerAddressRepository caRepository;
    final SeJjimRepository jjimRepository;
    final SeChkPurchaseDeadlineViewRepository chkPurchaseDeadlineViewRepository;
    final SePurchaseStatusRepository purchaseStatusRepository;

    
    // --------------------------------------------------------------------------------------------
    // 공구가 많이 열린 물품 n개
    @Override
    public List<Map<String, Object>> selectManyPurchaseItem() {
        try {
            return piMapper.selectManyPurchaseItem();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SeManyPurchaseItemView> selectManyPurchaseItem1(long no) {
        try {
            return piMapper.selectManyPurchaseItem1(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // --------------------------------------------------------------------------------------------
    // 기한이 얼마 안 남은 공구 n 개
    @Override
    public List<SeDeadlinePurchaseDdayView> selectDeadLinePurchaseItem(long no) {
        try {
            return piMapper.selectDeadLinePurchaseItem(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // --------------------------------------------------------------------------------------------
    // 물품 대표이미지 가져오기
    @Override
    public ItemImage selectItemImageOne(BigDecimal itemno) {
        try {
            List<ItemImage> obj = iiRepository.findByItemNo_noAndFilenameNotLikeOrderByNoAsc(itemno, "%상세%");
            if(obj.isEmpty()){
                return null;
            }
            return obj.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // --------------------------------------------------------------------------------------------
    // 내 주위 실시간 공구 5개
    @Override
    public List<SeAroundPurchaseView> selectAroundPurchaseItem(String id) {
        try {
            CustomerAddressEntity obj = caRepository.findByCustomer_id(id).get(0);
            return piMapper.selectAroundPurchaseItem(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // --------------------------------------------------------------------------------------------
    // 물품목록 - 검색어
    @Override
    public List<SeSelectItemListView> selectSearchItem(SeSelectItemListView obj) {
       try {
        return piMapper.selectSearchItem(obj);
       } catch (Exception e) {
            e.printStackTrace();
            return null;
       }
    }

    // 물품목록 - 검색어 - 전체 개수 (페이지네이션용)
    @Override
    public long selectSearchItemCnt(SeSelectItemListView obj) {
        try {
        return piMapper.selectSearchItemCnt(obj);
       } catch (Exception e) {
            e.printStackTrace();
            return 0L;
       }
    }

    // 물품목록 - 소분류
    @Override
    public List<SeSelectItemListView> selectScodeItem(SeSelectItemListView obj) {
        try {
            return piMapper.selectScodeItem(obj);
        } catch (Exception e) {
             e.printStackTrace();
             return null;
        }
    }

    // 물품목록 - 소분류 BEST
    @Override
    public List<SeSelectItemListView> selectScodeItemBest(long scode) {
        try {
            return piMapper.selectScodeItemBest(scode);
        } catch (Exception e) {
             e.printStackTrace();
             return null;
        }
    }

    // 물품목록 - 소분류 - 전체 개수 (페이지네이션용)
    @Override
    public long selectScodeItemCnt(SeSelectItemListView obj) {
        try {
            return piMapper.selectScodeItemCnt(obj);
        } catch (Exception e) {
             e.printStackTrace();
             return 0L;
        }
    }

    // 물품이 현재 공구 중인지 확인
    @Override
    public long selectPurchaseChk(long itemno) {
        try {
            return piMapper.selectPurchaseChk(itemno);
        } catch (Exception e) {
             e.printStackTrace();
             return 0L;
        }
    }

    // 물품이 찜 상태인지 확인
    @Override
    public long countByCustomerEntity_idAndItemEntity_no(String id, BigDecimal no) {
        try {
            return jjimRepository.countByCustomerEntity_idAndItemEntity_no(id, no);
        } catch (Exception e) {
             e.printStackTrace();
             return 0L;
        }
    }

    // 찜 save
    @Override
    public int saveJjim(JjimEntity jjimEntity) {
        try {
            int ret = 0;
            JjimEntity obj = jjimRepository.save(jjimEntity);
            if(obj != null) {
                ret = 1;
            }
            else {
                ret = 0;
            }
            return ret;
        } catch (Exception e) {
             e.printStackTrace();
             return -1;
        }
    }

    // 찜 delete
    @Override
    public int deleteJjim(String id, BigDecimal no) {
        try {
            return jjimRepository.deleteByCustomerEntity_idAndItemEntity_no(id, no);
        } catch (Exception e) {
             e.printStackTrace();
             return -1;
        }
    }

    
    // --------------------------------------------------------------------------------------------------------
    // 최초 개설된 공구인지 확인(알림용)
    @Override
    public long selectPurchaseOpenChk(long purchaseno) {
        try {
            return piMapper.selectPurchaseOpenChk(purchaseno);
        } catch (Exception e) {
             e.printStackTrace();
             return -1;
        }
    }


    // 마감일 지난 공구 확인(스케쥴러)
    @Override
    public List<SeChkPurchaseDeadlineView> findAllAfterDeadline() {
        try {
            return chkPurchaseDeadlineViewRepository.findAll();
        } catch (Exception e) {
             e.printStackTrace();
             return null;
        }
    }

    // 마감 지난 공구 취소 insert(스케쥴러)
    @Override
    public List<PurchaseStatusEntity> saveAllPurchaseStatus(List<PurchaseStatusEntity> list) {
        try {
            return purchaseStatusRepository.saveAll(list);
        } catch (Exception e) {
             e.printStackTrace();
             return null;
        }
    }

    
    
}
