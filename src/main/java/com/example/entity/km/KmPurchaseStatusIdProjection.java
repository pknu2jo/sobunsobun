package com.example.entity.km;

public interface KmPurchaseStatusIdProjection {
    
    // get+변수명()
    CustomerEntity getCustomerEntity();

    interface CustomerEntity {
        String getId();
    }
}
