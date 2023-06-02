package com.example.entity.se;

import java.math.BigDecimal;

public interface SePurchaseStatusProjection {
    BigDecimal getState();

    CustomerEntity getCustomerEntity();

    interface CustomerEntity {
        String getId();
    }
    
}
