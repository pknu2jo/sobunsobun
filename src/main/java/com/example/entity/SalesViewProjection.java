package com.example.entity;

import java.math.BigDecimal;

public interface SalesViewProjection {
    String getMonthly();
    BigDecimal getAmount();
}
