package com.padilha.payroll.helpers;

import java.math.BigDecimal;

public enum JobGroupEnum {
    A(new BigDecimal(20.00)),
    B(new BigDecimal(30.00));
    private BigDecimal payment;

    JobGroupEnum(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getNumVal() {
        return payment;
    }
}
