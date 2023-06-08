package com.example.service.ikh;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.DeliveryEntity;
import com.example.entity.SellerEntity;
import com.example.entity.ikh.DeliveryView;

@Service
public interface IkhDeliveryService {    

    // 배송상태설정
    public int updateStatus(BigDecimal deliveryNo, BigDecimal no);

    // 기간 설정용 아이디 찾기
    public SellerEntity findByNo(String no);

    // 리스트 전체 받기
    public List<DeliveryEntity> findAll();

    public long countByDeliveryAndNo(BigDecimal delivery, String no);
        
    // 0
    public List<DeliveryView> findByNoZe(String no);
    // 1
    public List<DeliveryView> findByNoAndItemcode(String no, BigDecimal itemcode);
    public List<DeliveryView> findByNoAndItemnameContaining(String no, String itemname);
    public List<DeliveryView> findByNoAndAddressContaining(String no, String address);
    public List<DeliveryView> findByNoAndPurchaseno(String no, BigDecimal purchaseno);
    public List<DeliveryView> findByNoAndDelivery(String no, BigDecimal delivery);
    public List<DeliveryView> findByNoAndRegdateBetween(String no, Date startDate, Date endDate);

    // 2
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContaining(String no, BigDecimal itemcode, String itemname);
    public List<DeliveryView> findByNoAndItemcodeAndAddressContaining(String no, BigDecimal itemcode, String address);
    public List<DeliveryView> findByNoAndItemcodeAndPurchaseno(String no, BigDecimal itemcode, BigDecimal purchaseno);
    public List<DeliveryView> findByNoAndItemcodeAndDelivery(String no, BigDecimal itemcode, BigDecimal delivery);
    public List<DeliveryView> findByNoAndItemcodeAndRegdateBetween(String no, BigDecimal itemcode, Date startDate, Date endDate);

    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContaining(String no, String itemname, String address);
    public List<DeliveryView> findByNoAndItemnameContainingAndPurchaseno(String no, String itemname, BigDecimal purchaseno);
    public List<DeliveryView> findByNoAndItemnameContainingAndDelivery(String no, String itemname, BigDecimal delivery);
    public List<DeliveryView> findByNoAndItemnameContainingAndRegdateBetween(String no, String itemname, Date startDate, Date endDate);
    
    public List<DeliveryView> findByNoAndAddressContainingAndPurchaseno(String no, String address, BigDecimal purchaseno);
    public List<DeliveryView> findByNoAndAddressContainingAndDelivery(String no, String address, BigDecimal delivery);
    public List<DeliveryView> findByNoAndAddressContainingAndRegdateBetween(String no, String address, Date startDate, Date endDate);

    public List<DeliveryView> findByNoAndPurchasenoAndDelivery(String no, BigDecimal purchaseno, BigDecimal delivery);
    public List<DeliveryView> findByNoAndPurchasenoAndRegdateBetween(String no, BigDecimal purchaseno, Date startDate, Date endDate);

    public List<DeliveryView> findByNoAndDeliveryAndRegdateBetween(String no, BigDecimal delivery, Date startDate, Date endDate);
    
    // 3
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContaining
    (String no, BigDecimal itemcode, String itemname, String address);
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndPurchaseno
    (String no, BigDecimal itemcode, String itemname, BigDecimal purchaseno);
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndDelivery
    (String no, BigDecimal itemcode, String itemname, BigDecimal delivery);
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndRegdateBetween
    (String no, BigDecimal itemcode, String itemname, Date startDate, Date endDate);

    public List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndPurchaseno
    (String no, BigDecimal itemcode, String address, BigDecimal purchaseno);
    public List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndDelivery
    (String no, BigDecimal itemcode, String address, BigDecimal delivery);
    public List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndRegdateBetween
    (String no, BigDecimal itemcode, String address, Date startDate, Date endDate);
    
    public List<DeliveryView> findByNoAndItemcodeAndPurchasenoAndDelivery
    (String no, BigDecimal itemcode, BigDecimal purchaseno, BigDecimal delivery);
    public List<DeliveryView> findByNoAndItemcodeAndPurchasenoAndRegdateBetween
    (String no, BigDecimal itemcode, BigDecimal purchaseno, Date startDate, Date endDate);

    public List<DeliveryView> findByNoAndItemcodeAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, BigDecimal delivery, Date startDate, Date endDate);
    
    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndPurchaseno
    (String no, String itemname, String address, BigDecimal purchaseno);
    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndDelivery
    (String no, String itemname, String address, BigDecimal delivery);
    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndRegdateBetween
    (String no, String itemname, String address, Date startDate, Date endDate);
    
    public List<DeliveryView> findByNoAndItemnameContainingAndPurchasenoAndDelivery
    (String no, String itemname, BigDecimal purchaseno, BigDecimal delivery);
    public List<DeliveryView> findByNoAndItemnameContainingAndPurchasenoAndRegdateBetween
    (String no, String itemname, BigDecimal purchaseno, Date startDate, Date endDate);

    public List<DeliveryView> findByNoAndItemnameContainingAndDeliveryAndRegdateBetween
    (String no, String itemname, BigDecimal delivery, Date startDate, Date endDate);
    
    public List<DeliveryView> findByNoAndAddressContainingAndPurchasenoAndDelivery
    (String no, String address, BigDecimal purchaseno, BigDecimal delivery);
    public List<DeliveryView> findByNoAndAddressContainingAndPurchasenoAndRegdateBetween
    (String no, String address, BigDecimal purchaseno, Date startDate, Date endDate);
    public List<DeliveryView> findByNoAndAddressContainingAndDeliveryAndRegdateBetween
    (String no, String address, BigDecimal delivery, Date startDate, Date endDate);

    public List<DeliveryView> findByNoAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, BigDecimal purchaseno, BigDecimal delivery, Date startDate, Date endDate);
    // 4    
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchaseno
    (String no, BigDecimal itemcode, String itemname, String address, BigDecimal purchaseno);
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndDelivery
    (String no, BigDecimal itemcode, String itemname, String address, BigDecimal delivery);
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndRegdateBetween
    (String no, BigDecimal itemcode, String itemname, String address, Date startDate, Date endDate);

    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndDelivery
    (String no, BigDecimal itemcode, String itemname, BigDecimal purchaseno, BigDecimal delivery);
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndRegdateBetween
    (String no, BigDecimal itemcode, String itemname, BigDecimal purchaseno, Date startDate, Date endDate);

    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, String itemname, BigDecimal delivery, Date startDate, Date endDate);
    
    public List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndPurchasenoAndDelivery
    (String no, BigDecimal itemcode, String address, BigDecimal purchaseno, BigDecimal delivery);
    public List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndPurchasenoAndRegdateBetween
    (String no, BigDecimal itemcode, String address, BigDecimal purchaseno, Date startDate, Date endDate);
    public List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, String address, BigDecimal delivery, Date startDate, Date endDate);

    public List<DeliveryView> findByNoAndItemcodeAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, BigDecimal purchaseno, BigDecimal delivery, Date startDate, Date endDate);
    
    // 2345
    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndDelivery
    (String no, String itemname, String address, BigDecimal purchaseno, BigDecimal delivery);
    // 2346
    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndRegdateBetween
    (String no, String itemname, String address, BigDecimal purchaseno, Date startDate, Date endDate);
    // 2356
    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndDeliveryAndRegdateBetween
    (String no, String itemname, String address, BigDecimal delivery, Date startDate, Date endDate);
    // 2456
    public List<DeliveryView> findByNoAndItemnameContainingAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, String itemname, BigDecimal purchaseno, BigDecimal delivery, Date startDate, Date endDate);
    //3456
    public List<DeliveryView> findByNoAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, String address, BigDecimal purchaseno, BigDecimal delivery, Date startDate, Date endDate);

    // 5
    // 1. Itemcode  2. ItemnameContaining 3. AddressContaining 4. Purchaseno 5. Delivery 6. Regdate
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndDelivery
    (String no, BigDecimal itemcode, String Itemname, String address, BigDecimal purchase, BigDecimal delivery);
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndRegdateBetween
    (String no, BigDecimal itemcode, String Itemname, String address, BigDecimal purchase, Date startDate, Date endDate);
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, String Itemname, String address, BigDecimal delivery, Date startDate, Date endDate);
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, String Itemname, BigDecimal purchase, BigDecimal delivery, Date startDate, Date endDate);
    public List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, String address, BigDecimal purchase, BigDecimal delivery, Date startDate, Date endDate);
    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, String Itemname, String address, BigDecimal purchase, BigDecimal delivery, Date startDate, Date endDate);

    // 6
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, String Itemname, String address, BigDecimal purchase, BigDecimal delivery, Date startDate, Date endDate);
}
