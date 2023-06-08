package com.example.service.ikh;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.entity.ikh.BestSellView;
import com.example.entity.ikh.SalesViewProjection;
import com.example.entity.ikh.StagenderView;
import com.example.entity.ikh.TopthreeView;
import com.example.entity.ikh.TotaltableView;
import com.example.repository.ikh.BestSellViewRepository;
import com.example.repository.ikh.SalesViewRepository;
import com.example.repository.ikh.StagenderViewRepository;
import com.example.repository.ikh.StalocationViewRepository;
import com.example.repository.ikh.TopthreeViewRepository;
import com.example.repository.ikh.TotalgenderViewRepository;
import com.example.repository.ikh.TotallocationViewRepository;
import com.example.repository.ikh.TotaltableViewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IkhItemServiceImpl implements IkhItemService{

    final TotalgenderViewRepository tgvRepository; //
    final TotallocationViewRepository tlvRepository; //
    final TotaltableViewRepository tvRepository;
    final SalesViewRepository svRepository;
    final StagenderViewRepository sgvRepository;
    final StalocationViewRepository slvRepository;
    final BestSellViewRepository bsvRepository;
    final TopthreeViewRepository ttvRepository;

    @Override
    public long countByGenderAndNo(String gender, String no) {
        try {
            return tgvRepository.countByGenderAndNo(gender, no);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public long countByNoAndLocationContaining(String no, String location) {
        try {
            return tlvRepository.countByNoAndLocationContaining(no, location);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<TotaltableView> findByNo(String no) {
        try {
            return tvRepository.findByNo(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TotaltableView> findBest(String no, Pageable pageable) {
        try {
            return tvRepository.findBest(no, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long sumByItemprice(String no) {
        try {
            return svRepository.sumByItemprice(no);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<SalesViewProjection> findMonthlySales(String no) {
        try {
            return svRepository.findMonthlySales(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long sumByItempriceAndItemno(String no, BigDecimal itemno) {
        try {
            return svRepository.sumByItempriceAndItemno(no, itemno);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<SalesViewProjection> findMonthlySalesAndItemno(String no, BigDecimal itemno) {
        try {
            return svRepository.findMonthlySalesAndItemno(no, itemno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long countByGenderAndItemcodeAndNo(String gender, BigDecimal itemcode, String no) {
        try {
            return sgvRepository.countByGenderAndItemcodeAndNo(gender, itemcode, no);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public StagenderView findByItemcode(BigDecimal itemcode) {
        try {
            return sgvRepository.findByItemcode(itemcode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long countByNoAndItemcodeAndLocationContaining(String no, BigDecimal itemcode, String location) {
        try {
            return slvRepository.countByNoAndItemcodeAndLocationContaining(no, itemcode, location);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public BestSellView findByNobest(String no) {
        try {
            return bsvRepository.findByNo(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TopthreeView> findByNoTh(String no) {
        try {
            return ttvRepository.findByNo(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
