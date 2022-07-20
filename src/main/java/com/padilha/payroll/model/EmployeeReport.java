package com.padilha.payroll.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.padilha.payroll.helpers.JsonNumberFormat;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employeeReport")
@TypeDef(name = "json", typeClass = JsonType.class)
public class EmployeeReport {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Column(name="employeeId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long employeeId;

    @JsonIgnore
    @Column(name="payrollId")
    private Long payrollId;

    @JsonSerialize(using = JsonNumberFormat.class)
    private BigDecimal amountPaid;

    @Column(name="payPeriod", columnDefinition = "json")
    @Type(type = "json")
    private PayPeriod payPeriod;

    @JsonIgnore
    private String jobGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(Long payrollId) {
        this.payrollId = payrollId;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public PayPeriod getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(PayPeriod payPeriod) {
        this.payPeriod = payPeriod;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
}
