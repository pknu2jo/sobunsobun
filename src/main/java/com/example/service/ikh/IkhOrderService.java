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

@Service
public interface IkhOrderService {

    // 공구 전체 파악
    public List<OrderView> findByNo(String no);

    // 공구 개수
    public List<OrderView> findByNoAndState(String no, BigDecimal state);

    // 완료된 공구
    public List<CompleteOrderView> findByNoCO(String no);

    // 진행중 공구
    public List<ProceedOrderFinalView> findByNoPOF(String no);

    // 취소된 공구
    public List<CancelOrderView> findByNoCan(String no);

    // 완료된 공구 멤버
    public List<CompleteOrderMemberView> findByNoAndPnoCom(String no, BigDecimal pno);

    // 진행중 공구 멤버
    public List<ProceedOrderMemberView> findByNoAndPnoPro(String no, BigDecimal pno);

    // 취소된 공구 멤버
    public List<CancelOrderMemberView> findByNoAndPnoCan(String no, BigDecimal pno);
}
