package com.example.service.ikh;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.ikh.CancelOrderMemberView;
import com.example.entity.ikh.CancelOrderView;
import com.example.entity.ikh.CompleteOrderMemberView;
import com.example.entity.ikh.CompleteOrderView;
import com.example.entity.ikh.OrderView;
import com.example.entity.ikh.ProceedOrderFinalView;
import com.example.entity.ikh.ProceedOrderMemberView;
import com.example.repository.ikh.CancelOrderMemberViewRepository;
import com.example.repository.ikh.CancelOrderViewRepository;
import com.example.repository.ikh.CompleteOrderMemberViewRepository;
import com.example.repository.ikh.CompleteOrderViewRepository;
import com.example.repository.ikh.OrderViewRepository;
import com.example.repository.ikh.ProceedOrderFinalViewRepository;
import com.example.repository.ikh.ProceedOrderMemberViewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IkhOrderServiceImpl implements IkhOrderService{
    
    final OrderViewRepository ovRepository;
    final CompleteOrderViewRepository covRepository;
    final ProceedOrderFinalViewRepository porfvRepository;
    final CancelOrderViewRepository cancelRepository;
    final CompleteOrderMemberViewRepository comvRepository;
    final ProceedOrderMemberViewRepository promvRepository; 
    final CancelOrderMemberViewRepository cancellRepository;

    // 공구 전체 파악
    @Override
    public List<OrderView> findByNo(String no) {
       try {
            return ovRepository.findByNo(no);
       } catch (Exception e) {
            e.printStackTrace();
            return null;
       }
    }

    // 공구 개수
    @Override
    public List<OrderView> findByNoAndState(String no, BigDecimal state) {
        try {
            return ovRepository.findByNoAndState(no, state);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 완료된 공구
    @Override
    public List<CompleteOrderView> findByNoCO(String no) {
        try {
            return covRepository.findByNo(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 진행중 공구
    @Override
    public List<ProceedOrderFinalView> findByNoPOF(String no) {
        try {
            return porfvRepository.findByNo(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 취소된 공구
    @Override
    public List<CancelOrderView> findByNoCan(String no) {
        try {
            return cancelRepository.findByNo(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 완료된 공구 멤버
    @Override
    public List<CompleteOrderMemberView> findByNoAndPnoCom(String no, BigDecimal pno) {
        try {
            return comvRepository.findByNoAndPno(no, pno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 진행중 공구 멤버
    @Override
    public List<ProceedOrderMemberView> findByNoAndPnoPro(String no, BigDecimal pno) {
        try {
            return promvRepository.findByNoAndPno(no, pno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 취소된 공구 멤버
    @Override
    public List<CancelOrderMemberView> findByNoAndPnoCan(String no, BigDecimal pno) {
        try {
            return cancellRepository.findByNoAndPno(no, pno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }    

}
