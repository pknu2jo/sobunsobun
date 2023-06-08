package com.example.service.ikh;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.DeliveryEntity;
import com.example.entity.SellerEntity;
import com.example.entity.ikh.DeliveryView;
import com.example.mapper.ikh.IkhDeliveryMapper;
import com.example.repository.DeliveryRepository;
import com.example.repository.SellerRepository;
import com.example.repository.ikh.DeliveryViewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IkhDeliveryServiceImpl implements IkhDeliveryService{
    
    final IkhDeliveryMapper ikhDeliveryMapper;
    final SellerRepository selRepository;
    final DeliveryRepository dRepository;
    final DeliveryViewRepository dvRepository;

    // 배송상태설정
    @Override
    public int updateStatus(BigDecimal deliveryNo, BigDecimal no) {
        try {
            return ikhDeliveryMapper.updateStatus(deliveryNo, no);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public SellerEntity findByNo(String no) {
        try {
            return selRepository.findByNo(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryEntity> findAll() {
        try {
            return dRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long countByDeliveryAndNo(BigDecimal delivery, String no) {
        try {
            return dvRepository.countByDeliveryAndNo(delivery, no);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<DeliveryView> findByNoZe(String no) {
        try {
            return dvRepository.findByNo(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcode(String no, BigDecimal itemcode) {
        try {
            return dvRepository.findByNoAndItemcode(no, itemcode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContaining(String no, String itemname) {
        try {
            return dvRepository.findByNoAndItemnameContaining(no, itemname);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndAddressContaining(String no, String address) {
        try {
            return dvRepository.findByNoAndAddressContaining(no, address);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndPurchaseno(String no, BigDecimal purchaseno) {
        try {
            return dvRepository.findByNoAndPurchaseno(no, purchaseno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndDelivery(String no, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndDelivery(no, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndRegdateBetween(String no, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndRegdateBetween(no, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContaining(String no, BigDecimal itemcode, String itemname) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContaining(no, itemcode, itemname);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndAddressContaining(String no, BigDecimal itemcode, String address) {
        try {
            return dvRepository.findByNoAndItemcodeAndAddressContaining(no, itemcode, address);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndPurchaseno(String no, BigDecimal itemcode, BigDecimal purchaseno) {
        try {
            return dvRepository.findByNoAndItemcodeAndPurchaseno(no, itemcode, purchaseno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndDelivery(String no, BigDecimal itemcode, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndItemcodeAndDelivery(no, itemcode, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndRegdateBetween(String no, BigDecimal itemcode, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndRegdateBetween(no, itemcode, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContaining(String no, String itemname, String address) {
        try {
            return dvRepository.findByNoAndItemnameContainingAndAddressContaining(no, itemname, address);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContainingAndPurchaseno(String no, String itemname, BigDecimal purchaseno) {
        try {
            return dvRepository.findByNoAndItemnameContainingAndPurchaseno(no, itemname, purchaseno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContainingAndDelivery(String no, String itemname, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndItemnameContainingAndDelivery(no, itemname, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContainingAndRegdateBetween(String no, String itemname, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemnameContainingAndRegdateBetween(no, itemname, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndAddressContainingAndPurchaseno(String no, String address, BigDecimal purchaseno) {
        try {
            return dvRepository.findByNoAndAddressContainingAndPurchaseno(no, address, purchaseno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndAddressContainingAndDelivery(String no, String address, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndAddressContainingAndDelivery(no, address, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndAddressContainingAndRegdateBetween(String no, String address, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndAddressContainingAndRegdateBetween(no, address, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndPurchasenoAndDelivery(String no, BigDecimal purchaseno, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndPurchasenoAndDelivery(no, purchaseno, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndPurchasenoAndRegdateBetween(String no, BigDecimal purchaseno, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndPurchasenoAndRegdateBetween(no, purchaseno, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndDeliveryAndRegdateBetween(String no, BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndDeliveryAndRegdateBetween(no, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContaining(String no,
            BigDecimal itemcode, String itemname, String address) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContaining(no, itemcode, itemname, address);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndPurchaseno(String no, BigDecimal itemcode,
            String itemname, BigDecimal purchaseno) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContainingAndPurchaseno(no, itemcode, itemname, purchaseno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndDelivery(String no, BigDecimal itemcode,
            String itemname, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContainingAndDelivery(no, itemcode, itemname, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndRegdateBetween(String no, BigDecimal itemcode,
            String itemname, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContainingAndRegdateBetween(no, itemcode, itemname, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndPurchaseno(String no, BigDecimal itemcode,
            String address, BigDecimal purchaseno) {
        try {
            return dvRepository.findByNoAndItemcodeAndAddressContainingAndPurchaseno(no, itemcode, address, purchaseno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndDelivery(String no, BigDecimal itemcode,
            String address, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndItemcodeAndAddressContainingAndDelivery(no, itemcode, address, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndRegdateBetween(String no, BigDecimal itemcode,
            String address, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndAddressContainingAndRegdateBetween(no, itemcode, address, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndPurchasenoAndDelivery(String no, BigDecimal itemcode,
            BigDecimal purchaseno, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndItemcodeAndPurchasenoAndDelivery(no, itemcode, purchaseno, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndPurchasenoAndRegdateBetween(String no, BigDecimal itemcode,
            BigDecimal purchaseno, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndPurchasenoAndRegdateBetween(no, itemcode, purchaseno, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndDeliveryAndRegdateBetween(String no, BigDecimal itemcode,
            BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndDeliveryAndRegdateBetween(no, itemcode, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndPurchaseno(String no, String itemname,
            String address, BigDecimal purchaseno) {
        try {
            return dvRepository.findByNoAndItemnameContainingAndAddressContainingAndPurchaseno(no, itemname, address, purchaseno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndDelivery(String no, String itemname,
            String address, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndItemnameContainingAndAddressContainingAndDelivery(no, itemname, address, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndRegdateBetween(String no,
            String itemname, String address, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemnameContainingAndAddressContainingAndRegdateBetween(no, itemname, address, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContainingAndPurchasenoAndDelivery(String no, String itemname,
            BigDecimal purchaseno, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndItemnameContainingAndPurchasenoAndDelivery(no, itemname, purchaseno, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContainingAndPurchasenoAndRegdateBetween(String no, String itemname,
            BigDecimal purchaseno, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemnameContainingAndPurchasenoAndRegdateBetween(no, itemname, purchaseno, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContainingAndDeliveryAndRegdateBetween(String no, String itemname,
            BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemnameContainingAndDeliveryAndRegdateBetween(no, itemname, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndAddressContainingAndPurchasenoAndDelivery(String no, String address,
            BigDecimal purchaseno, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndAddressContainingAndPurchasenoAndDelivery(no, address, purchaseno, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndAddressContainingAndPurchasenoAndRegdateBetween(String no, String address,
            BigDecimal purchaseno, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndAddressContainingAndPurchasenoAndRegdateBetween(no, address, purchaseno, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndAddressContainingAndDeliveryAndRegdateBetween(String no, String address,
            BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndAddressContainingAndDeliveryAndRegdateBetween(no, address, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndPurchasenoAndDeliveryAndRegdateBetween(String no, BigDecimal purchaseno,
            BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndPurchasenoAndDeliveryAndRegdateBetween(no, purchaseno, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchaseno(String no,
            BigDecimal itemcode, String itemname, String address, BigDecimal purchaseno) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchaseno(no, itemcode, itemname, address, purchaseno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndDelivery(String no,
            BigDecimal itemcode, String itemname, String address, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndDelivery(no, itemcode, itemname, address, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndRegdateBetween(String no,
            BigDecimal itemcode, String itemname, String address, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndRegdateBetween(no, itemcode, itemname, address, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndDelivery(String no,
            BigDecimal itemcode, String itemname, BigDecimal purchaseno, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndDelivery(no, itemcode, itemname, purchaseno, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndRegdateBetween(String no,
            BigDecimal itemcode, String itemname, BigDecimal purchaseno, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndRegdateBetween(no, itemcode, itemname, purchaseno, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndDeliveryAndRegdateBetween(String no,
            BigDecimal itemcode, String itemname, BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContainingAndDeliveryAndRegdateBetween(no, itemcode, itemname, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndPurchasenoAndDelivery(String no,
            BigDecimal itemcode, String address, BigDecimal purchaseno, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndItemcodeAndAddressContainingAndPurchasenoAndDelivery(no, itemcode, address, purchaseno, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndPurchasenoAndRegdateBetween(String no,
            BigDecimal itemcode, String address, BigDecimal purchaseno, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndAddressContainingAndPurchasenoAndRegdateBetween(no, itemcode, address, purchaseno, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndDeliveryAndRegdateBetween(String no,
            BigDecimal itemcode, String address, BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndAddressContainingAndDeliveryAndRegdateBetween(no, itemcode, address, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndPurchasenoAndDeliveryAndRegdateBetween(String no,
            BigDecimal itemcode, BigDecimal purchaseno, BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndPurchasenoAndDeliveryAndRegdateBetween(no, itemcode, purchaseno, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndDelivery(String no,
            String itemname, String address, BigDecimal purchaseno, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndDelivery(no, itemname, address, purchaseno, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndRegdateBetween(String no,
            String itemname, String address, BigDecimal purchaseno, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndRegdateBetween(no, itemname, address, purchaseno, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndDeliveryAndRegdateBetween(String no,
            String itemname, String address, BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemnameContainingAndAddressContainingAndDeliveryAndRegdateBetween(no, itemname, address, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContainingAndPurchasenoAndDeliveryAndRegdateBetween(String no,
            String itemname, BigDecimal purchaseno, BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemnameContainingAndPurchasenoAndDeliveryAndRegdateBetween(no, itemname, purchaseno, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween(String no,
            String address, BigDecimal purchaseno, BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween(no, address, purchaseno, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndDelivery(
            String no, BigDecimal itemcode, String Itemname, String address, BigDecimal purchase, BigDecimal delivery) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndDelivery(no, itemcode, Itemname, address, purchase, delivery);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndRegdateBetween(
            String no, BigDecimal itemcode, String Itemname, String address, BigDecimal purchase, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndRegdateBetween(no, itemcode, Itemname, address, purchase, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndDeliveryAndRegdateBetween(
            String no, BigDecimal itemcode, String Itemname, String address, BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndDeliveryAndRegdateBetween(no, itemcode, Itemname, address, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndDeliveryAndRegdateBetween(
            String no, BigDecimal itemcode, String Itemname, BigDecimal purchase, BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndDeliveryAndRegdateBetween(no, itemcode, Itemname, purchase, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween(
            String no, BigDecimal itemcode, String address, BigDecimal purchase, BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween(no, itemcode, address, purchase, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween(
            String no, String Itemname, String address, BigDecimal purchase, BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween(no, Itemname, address, purchase, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryView> findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween(
            String no, BigDecimal itemcode, String Itemname, String address, BigDecimal purchase, BigDecimal delivery, Date startDate, Date endDate) {
        try {
            return dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween(no, itemcode, Itemname, address, purchase, delivery, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
}    
}
