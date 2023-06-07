package com.example.entity.se;

public interface SeJjimProjection {

    CustomerEntity getCustomerEntity();

    interface CustomerEntity {
        String getId();
    }

}

