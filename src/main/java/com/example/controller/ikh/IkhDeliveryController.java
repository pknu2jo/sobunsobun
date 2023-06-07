package com.example.controller.ikh;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.ikh.DeliverySearch;
import com.example.entity.DeliveryEntity;
import com.example.entity.SellerEntity;

import com.example.entity.ikh.DeliveryView;

import com.example.mapper.ikh.IkhDeliveryMapper;
import com.example.repository.DeliveryRepository;
import com.example.repository.SellerRepository;

import com.example.repository.ikh.DeliveryViewRepository;
import com.example.repository.jk.JkSellerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = "/seller")
@RequiredArgsConstructor
@Slf4j
public class IkhDeliveryController {
    // 배송    
    final DeliveryViewRepository dvRepository;
    final SellerRepository selRepository;
    final DeliveryRepository dRepository;
    final IkhDeliveryMapper ikhDeliveryMapper;
    final JkSellerRepository sellerRepository;
// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    // 배송
    @GetMapping(value="/delivery/search.do")
    public String deliverysearchGET(Model model, @AuthenticationPrincipal User user,
                @RequestParam(name="itemcode", defaultValue = "") BigDecimal itemcode,
                @RequestParam(name="itemname", defaultValue = "") String itemname,
                @RequestParam(name="address", defaultValue = "") String address,
                @RequestParam(name="purchaseno", defaultValue = "") BigDecimal purchaseno,
                @RequestParam(name="status", defaultValue = "") BigDecimal status,
                @RequestParam(name="firstdate", defaultValue = "") String firstdate,
                @RequestParam(name="seconddate", defaultValue = "") String seconddate){        
        try {        
            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            SellerEntity seller = sellerRepository.findById(user.getUsername()).orElse(null);
            model.addAttribute("companyName", seller.getName().toString());
            model.addAttribute("user", user);

            // 배송상태별, 전체
            long one = dvRepository.countByDeliveryAndNo(BigDecimal.valueOf(0), seller.getNo());
            long two = dvRepository.countByDeliveryAndNo(BigDecimal.valueOf(1), seller.getNo());
            long three = dvRepository.countByDeliveryAndNo(BigDecimal.valueOf(2), seller.getNo());
            long four = dvRepository.countByDeliveryAndNo(BigDecimal.valueOf(3), seller.getNo());
            long total = one+two+three+four;
            model.addAttribute("one", one);
            model.addAttribute("two", two);
            model.addAttribute("three", three);
            model.addAttribute("four", four);
            model.addAttribute("total", total);
            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 검색어 유지를 위한 기능            
            
            DeliverySearch dvs = new DeliverySearch();
            dvs.setItemcode(itemcode);
            dvs.setItemname(itemname);
            dvs.setAddress(address);
            dvs.setPurchaseno(purchaseno);
            dvs.setStatus(status);
            dvs.setFirstdate(firstdate);
            dvs.setSeconddate(seconddate);
            model.addAttribute("dvs", dvs);
            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 배송상태
            List<DeliveryEntity> dlist = dRepository.findAll();
            model.addAttribute("statuslist", dlist);           

            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 기간 설정
            LocalDate today = LocalDate.now();            
            SellerEntity sellers =  selRepository.findByNo(seller.getNo());
            LocalDate sdate =  new java.sql.Date(sellers.getRegdate().getTime()).toLocalDate();
            model.addAttribute("sdate", sdate); // 업체의 생성 날짜 보내주기
            model.addAttribute("today", today); // 오늘 날짜 보내주기
            Date date1 = new Date();
            Date date2 = new Date();

            if(!firstdate.equals("") && !seconddate.equals("")){                
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date1 = dateFormat.parse(firstdate);
                date2 = dateFormat.parse(seconddate);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                date1 = calendar.getTime();

                calendar.setTime(date2);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                date2 = calendar.getTime();
            }
            
            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 테이블             
            int a=0, b=0, c=0, d=0, e=0, f=0;            
            //sum 구하기 (입력값이 있냐 없냐 확인)
            if(itemcode != null){
                a=1;                    
            }
            if(!itemname.equals("")){
                b=1;
            }
            if(!address.equals("")){
                c=1;
            }
            if(purchaseno != null){
                d=1;
            }                
            if(status != null){
                e=1;
            }                   
            if(!firstdate.equals("")){
                f=1;
            }
            int sum = a+b+c+d+e+f;

            List<DeliveryView> list = new ArrayList<>();
            // 입력값을 토대로 검색 조건 선택
            if(sum==0){
                list = dvRepository.findByNo(seller.getNo());
            }
            else if(sum==1){
                if(a == 1 ){
                    list = dvRepository.findByNoAndItemcode(seller.getNo(), itemcode);
                }
                else if(b==1){
                    list = dvRepository.findByNoAndItemnameContaining(seller.getNo(), itemname);
                }
                else if(c==1){
                    list = dvRepository.findByNoAndAddressContaining(seller.getNo(), address);
                }
                else if(d==1){
                    list = dvRepository.findByNoAndPurchaseno(seller.getNo(), purchaseno);
                }
                else if(e==1){
                    list = dvRepository.findByNoAndDelivery(seller.getNo(), status);
                }
                else if(f==1){
                    list = dvRepository.findByNoAndRegdateBetween(seller.getNo(), date1, date2);
                }
            }
            else if(sum==2){
                if(a==1){
                    if (b==1){
                        list = dvRepository.findByNoAndItemcodeAndItemnameContaining(seller.getNo(), itemcode, itemname);
                    }
                    else if(c==1){
                        list = dvRepository.findByNoAndItemcodeAndAddressContaining(seller.getNo(), itemcode, address);
                    }
                    else if(d==1){
                        list = dvRepository.findByNoAndItemcodeAndPurchaseno(seller.getNo(), itemcode, purchaseno);
                    }
                    else if(e==1){
                        list = dvRepository.findByNoAndItemcodeAndDelivery(seller.getNo(), itemcode, status);
                    }
                    else if(f==1){
                        list = dvRepository.findByNoAndItemcodeAndRegdateBetween(seller.getNo(), itemcode, date1, date2);
                    }
                }
                else if(b==1){
                    if(c==1){
                        list = dvRepository.findByNoAndItemnameContainingAndAddressContaining(seller.getNo(), itemname, address);
                    }
                    else if(d==1){
                        list = dvRepository.findByNoAndItemnameContainingAndPurchaseno(seller.getNo(), itemname, purchaseno);
                    }
                    else if(e==1){
                        list = dvRepository.findByNoAndItemnameContainingAndDelivery(seller.getNo(), itemname, status);
                    }
                    else if(f==1){
                        list = dvRepository.findByNoAndItemnameContainingAndRegdateBetween(seller.getNo(), itemname, date1, date2);
                    }
                }
                else if(c==1){
                    if(d==1){
                        list = dvRepository.findByNoAndAddressContainingAndPurchaseno(seller.getNo(), address, purchaseno);
                    }
                    else if(e==1){
                        list = dvRepository.findByNoAndAddressContainingAndDelivery(seller.getNo(), address, status);
                    }
                    else if(f==1){
                        list = dvRepository.findByNoAndAddressContainingAndRegdateBetween(seller.getNo(), address, date1, date2);
                    }
                }
                else if(d==1){
                    if(e==1){
                        list = dvRepository.findByNoAndPurchasenoAndDelivery(seller.getNo(), purchaseno, status);
                    }
                    else if(f==1){
                        list = dvRepository.findByNoAndPurchasenoAndRegdateBetween(seller.getNo(), purchaseno, date1, date2);
                    }
                }
                else if(e==1){
                    if(f==1){
                        list = dvRepository.findByNoAndDeliveryAndRegdateBetween(seller.getNo(), status, date1, date2);
                    }
                }
            }
            else if(sum==3){
                if(a==1){
                    if(b==1){
                        if(c==1){
                            list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContaining(seller.getNo(), itemcode, itemname, address);
                        }
                        else if(d==1){
                            list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndPurchaseno(seller.getNo(), itemcode, itemname, purchaseno);
                        }
                        else if(e==1){
                            list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndDelivery(seller.getNo(), itemcode, itemname, status);
                        }
                        else if(f==1){
                            list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndRegdateBetween(seller.getNo(), itemcode, itemname, date1, date2);
                        }
                    }
                    else if(c==1){
                        if(d==1){
                            list = dvRepository.findByNoAndItemcodeAndAddressContainingAndPurchaseno(seller.getNo(), itemcode, address, purchaseno);
                        }
                        else if(e==1){
                            list = dvRepository.findByNoAndItemcodeAndAddressContainingAndDelivery(seller.getNo(), itemcode, address, status);
                        }
                        else if(f==1){
                            list = dvRepository.findByNoAndItemcodeAndAddressContainingAndRegdateBetween(seller.getNo(), itemcode, address, date1, date2);
                        }
                    }
                    else if(d==1){
                        if(e==1){
                            list = dvRepository.findByNoAndItemcodeAndPurchasenoAndDelivery(seller.getNo(), itemcode, purchaseno, status);
                        }
                        else if(f==1){
                            list = dvRepository.findByNoAndItemcodeAndPurchasenoAndRegdateBetween(seller.getNo(), itemcode, purchaseno, date1, date2);
                        }
                    }
                    else if(e==1){
                        if(f==1){
                            list = dvRepository.findByNoAndItemcodeAndDeliveryAndRegdateBetween(seller.getNo(), itemcode, status, date1, date2);
                        }
                    }
                }
                else if(b==1){
                    if(c==1){
                        if(d==1){
                            list = dvRepository.findByNoAndItemnameContainingAndAddressContainingAndPurchaseno(seller.getNo(), itemname, address, purchaseno);
                        }
                        else if(e==1){
                            list = dvRepository.findByNoAndItemnameContainingAndAddressContainingAndDelivery(seller.getNo(), itemname, address, status);
                        }
                        else if(f==1){
                            list = dvRepository.findByNoAndItemnameContainingAndAddressContainingAndRegdateBetween(seller.getNo(), itemname, address, date1, date2);
                        }
                    }
                    else if(d==1){
                        if(e==1){
                            list = dvRepository.findByNoAndItemnameContainingAndPurchasenoAndDelivery(seller.getNo(), itemname, purchaseno, status);
                        }
                        else if(f==1){
                            list = dvRepository.findByNoAndItemnameContainingAndPurchasenoAndRegdateBetween(seller.getNo(), itemname, purchaseno, date1, date2);
                        }
                    }
                    else if(e==1){
                        if(f==1){
                            list = dvRepository.findByNoAndItemnameContainingAndDeliveryAndRegdateBetween(seller.getNo(), itemname, status, date1, date2);
                        }
                    }
                }
                else if(c==1){
                    if(d==1){
                        if(e==1){
                            list = dvRepository.findByNoAndAddressContainingAndPurchasenoAndDelivery(seller.getNo(), address, purchaseno, status);
                        }
                        else if(f==1){
                            list = dvRepository.findByNoAndItemnameContainingAndDeliveryAndRegdateBetween(seller.getNo(), address, purchaseno, date1, date2);
                        }
                    }
                    else if(e==1){
                        if(f==1){
                            list = dvRepository.findByNoAndAddressContainingAndDeliveryAndRegdateBetween(seller.getNo(), address, status, date1, date2);
                        }
                    }
                }
                else if(d==1){
                    if(e==1){
                        if(f==1){
                            list = dvRepository.findByNoAndPurchasenoAndDeliveryAndRegdateBetween(seller.getNo(), purchaseno, status, date1, date2);
                        }
                    }
                }                
            }
            else if(sum==4){
                if(a==1){
                    if(b==1){
                        if(c==1){
                            if(d==1){
                                list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchaseno(seller.getNo(), itemcode, itemname, address, purchaseno);
                            }
                            else if(e==1){
                                list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndDelivery(seller.getNo(), itemcode, itemname, address, status);
                            }
                            else if(f==1){
                                list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndRegdateBetween(seller.getNo(), itemcode, itemname, address, date1, date2);
                            }
                        }
                        if(d==1){
                            if(e==1){
                                list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndDelivery(seller.getNo(), itemcode, itemname, purchaseno, status);
                            }
                            else if(f==1){
                                list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndRegdateBetween(seller.getNo(), itemcode, itemname, purchaseno, date1, date2);
                            }
                        }
                        if(e==1){
                            if(f==1){
                                list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndDeliveryAndRegdateBetween(seller.getNo(), itemcode, itemname, status, date1, date2);
                            }
                        }
                    }
                    else if(c==1){
                        if(d==1){
                            if(e==1){
                                list = dvRepository.findByNoAndItemcodeAndAddressContainingAndPurchasenoAndDelivery(seller.getNo(), itemcode, address, purchaseno, status);
                            }
                            else if(f==1){
                                list = dvRepository.findByNoAndItemcodeAndAddressContainingAndPurchasenoAndRegdateBetween(seller.getNo(), itemcode, address, purchaseno, date1, date2);
                            }
                        }
                        else if(e==1){
                            if(f==1){
                                list = dvRepository.findByNoAndItemcodeAndAddressContainingAndDeliveryAndRegdateBetween(seller.getNo(), itemcode, address, status, date1, date2);
                            }
                        }
                    }
                    else if(d==1){
                        if(e==1){
                            if(f==1){
                                list = dvRepository.findByNoAndItemcodeAndPurchasenoAndDeliveryAndRegdateBetween(seller.getNo(), itemcode, purchaseno, status, date1, date2);
                            }
                        }
                    }
                }
                else if(b==1){
                    if(c==1){
                        if(d==1){
                            if(e==1){
                                list = dvRepository.findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndDelivery(seller.getNo(), itemname, address, purchaseno, status);
                            }
                            else if(f==1){
                                list = dvRepository.findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndRegdateBetween(seller.getNo(), itemname, address, purchaseno, date1, date2);
                            }
                        }
                        if(e==1){
                            if(f==1){
                                list = dvRepository.findByNoAndItemnameContainingAndAddressContainingAndDeliveryAndRegdateBetween(seller.getNo(), itemname, address, status, date1, date2);
                            }
                        }
                    }
                    if(d==1){
                        if(e==1){
                            if(f==1){
                                list = dvRepository.findByNoAndItemnameContainingAndPurchasenoAndDeliveryAndRegdateBetween(seller.getNo(), itemname, purchaseno, status, date1, date2);
                            }
                        }
                    }
                }
                else if(c==1){
                    if(d==1){
                        if(e==1){
                            if(f==1){
                                list = dvRepository.findByNoAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween(seller.getNo(), address, purchaseno, status, date1, date2);
                            }
                        }
                    }
                }
            }
            else if(sum==5){
                if(a==1){
                    if(b==1){
                        // 123
                        if(c==1){
                            //1234
                            if(d==1){
                                if(e==1){
                                    list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndDelivery(seller.getNo(), itemcode, itemname, address, purchaseno, status);
                                }
                                else if(f==1){
                                    list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndRegdateBetween(seller.getNo(), itemcode, itemname, address, purchaseno, date1, date2);
                                }
                            }
                            //1235
                            else if(e==1){
                                if(f==1){
                                    list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndDeliveryAndRegdateBetween(seller.getNo(), itemcode, itemname, address, status, date1, date2);
                                }
                            }
                        }
                        // 124
                        else if(d==1){
                            if(e==1){
                                if(f==1){
                                    list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndDeliveryAndRegdateBetween(seller.getNo(), itemcode, itemname, purchaseno, status, date1, date2);
                                }
                            }
                        }
                    }
                    //13
                    else if(c==1){
                        if(d==1){
                            if(e==1){
                                if(f==1){
                                    list = dvRepository.findByNoAndItemcodeAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween(seller.getNo(), itemcode, address, purchaseno, status, date1, date2);
                                }
                            }
                        }
                    }
                }
                else if(b==1){
                    if(c==1){
                        if(d==1){
                            if(e==1){
                                if(f==1){
                                    list = dvRepository.findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween(seller.getNo(), itemname, address, purchaseno, status, date1, date2);
                                }
                            }
                        }
                    }
                }
            }
            else{
                list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween(seller.getNo(), itemcode, itemname, address, purchaseno, status, date1, date2);
            }

            model.addAttribute("list", list);
            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            return "/ikh/seller/delivery/search";
        } catch (Exception e) {
            e.printStackTrace();
            return "/jk/seller/login";
        }
    }
    
    @PostMapping(value="/delivery/search.do")
    public String deliverysearchPost(
        @RequestParam(name="btndv") long enid,
        @RequestParam(name="dvstatus") long dvstatus) {
        try {
            log.info("endi {}", enid); // purchasestatus.no
            log.info("dvstatus {}", dvstatus); // 배송상태 deliveryno

            BigDecimal deliveryNo = BigDecimal.valueOf(dvstatus);
            BigDecimal no = BigDecimal.valueOf(enid);
            
            int ret = ikhDeliveryMapper.updateStatus(deliveryNo, no);

            if(ret==1){
                return "redirect:/seller/delivery/search.do";
            }
            else{
                return "/jk/seller/login";
            }
            // return "redirect:/seller/delivery/search.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "/jk/seller/login";
        }
    }
    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
}