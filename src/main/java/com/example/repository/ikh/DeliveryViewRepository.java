package com.example.repository.ikh;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.ikh.DeliveryView;

public interface DeliveryViewRepository extends JpaRepository<DeliveryView, BigDecimal>{    
    // no /\ itemcode itemname address purchaseno delivery regdate
    long countByDeliveryAndNo(BigDecimal delivery, String no);
    



    
    // 0
    List<DeliveryView> findByNo(String no);

    // 1
    List<DeliveryView> findByNoAndItemcode(String no, BigDecimal itemcode);
    List<DeliveryView> findByNoAndItemnameContaining(String no, String itemname);
    List<DeliveryView> findByNoAndAddressContaining(String no, String address);
    List<DeliveryView> findByNoAndPurchaseno(String no, BigDecimal purchaseno);
    List<DeliveryView> findByNoAndDelivery(String no, BigDecimal delivery);
    List<DeliveryView> findByNoAndRegdateBetween(String no, Date startDate, Date endDate);

    // 2
    List<DeliveryView> findByNoAndItemcodeAndItemnameContaining(String no, BigDecimal itemcode, String itemname);
    List<DeliveryView> findByNoAndItemcodeAndAddressContaining(String no, BigDecimal itemcode, String address);
    List<DeliveryView> findByNoAndItemcodeAndPurchaseno(String no, BigDecimal itemcode, BigDecimal purchaseno);
    List<DeliveryView> findByNoAndItemcodeAndDelivery(String no, BigDecimal itemcode, BigDecimal delivery);
    List<DeliveryView> findByNoAndItemcodeAndRegdateBetween(String no, BigDecimal itemcode, Date startDate, Date endDate);

    List<DeliveryView> findByNoAndItemnameContainingAndAddressContaining(String no, String itemname, String address);
    List<DeliveryView> findByNoAndItemnameContainingAndPurchaseno(String no, String itemname, BigDecimal purchaseno);
    List<DeliveryView> findByNoAndItemnameContainingAndDelivery(String no, String itemname, BigDecimal delivery);
    List<DeliveryView> findByNoAndItemnameContainingAndRegdateBetween(String no, String itemname, Date startDate, Date endDate);
    
    List<DeliveryView> findByNoAndAddressContainingAndPurchaseno(String no, String address, BigDecimal purchaseno);
    List<DeliveryView> findByNoAndAddressContainingAndDelivery(String no, String address, BigDecimal delivery);
    List<DeliveryView> findByNoAndAddressContainingAndRegdateBetween(String no, String address, Date startDate, Date endDate);

    List<DeliveryView> findByNoAndPurchasenoAndDelivery(String no, BigDecimal purchaseno, BigDecimal delivery);
    List<DeliveryView> findByNoAndPurchasenoAndRegdateBetween(String no, BigDecimal purchaseno, Date startDate, Date endDate);

    List<DeliveryView> findByNoAndDeliveryAndRegdateBetween(String no, BigDecimal delivery, Date startDate, Date endDate);
    
    // 3
    List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContaining
    (String no, BigDecimal itemcode, String itemname, String address);
    List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndPurchaseno
    (String no, BigDecimal itemcode, String itemname, BigDecimal purchaseno);
    List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndDelivery
    (String no, BigDecimal itemcode, String itemname, BigDecimal delivery);
    List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndRegdateBetween
    (String no, BigDecimal itemcode, String itemname, Date startDate, Date endDate);

    List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndPurchaseno
    (String no, BigDecimal itemcode, String address, BigDecimal purchaseno);
    List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndDelivery
    (String no, BigDecimal itemcode, String address, BigDecimal delivery);
    List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndRegdateBetween
    (String no, BigDecimal itemcode, String address, Date startDate, Date endDate);
    
    List<DeliveryView> findByNoAndItemcodeAndPurchasenoAndDelivery
    (String no, BigDecimal itemcode, BigDecimal purchaseno, BigDecimal delivery);
    List<DeliveryView> findByNoAndItemcodeAndPurchasenoAndRegdateBetween
    (String no, BigDecimal itemcode, BigDecimal purchaseno, Date startDate, Date endDate);

    List<DeliveryView> findByNoAndItemcodeAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, BigDecimal delivery, Date startDate, Date endDate);
    
    List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndPurchaseno
    (String no, String itemname, String address, BigDecimal purchaseno);
    List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndDelivery
    (String no, String itemname, String address, BigDecimal delivery);
    List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndRegdateBetween
    (String no, String itemname, String address, Date startDate, Date endDate);
    
    List<DeliveryView> findByNoAndItemnameContainingAndPurchasenoAndDelivery
    (String no, String itemname, BigDecimal purchaseno, BigDecimal delivery);
    List<DeliveryView> findByNoAndItemnameContainingAndPurchasenoAndRegdateBetween
    (String no, String itemname, BigDecimal purchaseno, Date startDate, Date endDate);

    List<DeliveryView> findByNoAndItemnameContainingAndDeliveryAndRegdateBetween
    (String no, String itemname, BigDecimal delivery, Date startDate, Date endDate);
    
    List<DeliveryView> findByNoAndAddressContainingAndPurchasenoAndDelivery
    (String no, String address, BigDecimal purchaseno, BigDecimal delivery);
    List<DeliveryView> findByNoAndAddressContainingAndPurchasenoAndRegdateBetween
    (String no, String address, BigDecimal purchaseno, Date startDate, Date endDate);
    List<DeliveryView> findByNoAndAddressContainingAndDeliveryAndRegdateBetween
    (String no, String address, BigDecimal delivery, Date startDate, Date endDate);

    List<DeliveryView> findByNoAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, BigDecimal purchaseno, BigDecimal delivery, Date startDate, Date endDate);
    // 4    
    List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchaseno
    (String no, BigDecimal itemcode, String itemname, String address, BigDecimal purchaseno);
    List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndDelivery
    (String no, BigDecimal itemcode, String itemname, String address, BigDecimal delivery);
    List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndRegdateBetween
    (String no, BigDecimal itemcode, String itemname, String address, Date startDate, Date endDate);

    List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndDelivery
    (String no, BigDecimal itemcode, String itemname, BigDecimal purchaseno, BigDecimal delivery);
    List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndRegdateBetween
    (String no, BigDecimal itemcode, String itemname, BigDecimal purchaseno, Date startDate, Date endDate);

    List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, String itemname, BigDecimal delivery, Date startDate, Date endDate);
    
    List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndPurchasenoAndDelivery
    (String no, BigDecimal itemcode, String address, BigDecimal purchaseno, BigDecimal delivery);
    List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndPurchasenoAndRegdateBetween
    (String no, BigDecimal itemcode, String address, BigDecimal purchaseno, Date startDate, Date endDate);
    List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, String address, BigDecimal delivery, Date startDate, Date endDate);

    List<DeliveryView> findByNoAndItemcodeAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, BigDecimal purchaseno, BigDecimal delivery, Date startDate, Date endDate);
    
    // 2345
    List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndDelivery
    (String no, String itemname, String address, BigDecimal purchaseno, BigDecimal delivery);
    // 2346
    List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndRegdateBetween
    (String no, String itemname, String address, BigDecimal purchaseno, Date startDate, Date endDate);
    // 2356
    List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndDeliveryAndRegdateBetween
    (String no, String itemname, String address, BigDecimal delivery, Date startDate, Date endDate);
    // 2456
    List<DeliveryView> findByNoAndItemnameContainingAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, String itemname, BigDecimal purchaseno, BigDecimal delivery, Date startDate, Date endDate);
    //3456
    List<DeliveryView> findByNoAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, String address, BigDecimal purchaseno, BigDecimal delivery, Date startDate, Date endDate);

    // 5
    // 1. Itemcode  2. ItemnameContaining 3. AddressContaining 4. Purchaseno 5. Delivery 6. Regdate
    List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndDelivery
    (String no, BigDecimal itemcode, String Itemname, String address, BigDecimal purchase, BigDecimal delivery);
    List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndRegdateBetween
    (String no, BigDecimal itemcode, String Itemname, String address, BigDecimal purchase, Date startDate, Date endDate);
    List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, String Itemname, String address, BigDecimal delivery, Date startDate, Date endDate);
    List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, String Itemname, BigDecimal purchase, BigDecimal delivery, Date startDate, Date endDate);
    List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, String address, BigDecimal purchase, BigDecimal delivery, Date startDate, Date endDate);
    List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, String Itemname, String address, BigDecimal purchase, BigDecimal delivery, Date startDate, Date endDate);

    // 6
    List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween
    (String no, BigDecimal itemcode, String Itemname, String address, BigDecimal purchase, BigDecimal delivery, Date startDate, Date endDate);
}
