package com.padilha.payroll.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "payrollReport",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "payrollId"),
        })
@JsonTypeName("payrollReport")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT, use=JsonTypeInfo.Id.NAME)
public class PayrollReport {
    @Id
    @JsonIgnore
    private Long payrollId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "payrollId", referencedColumnName = "payrollId")
    @JsonProperty("employeeReports")
    private List<EmployeeReport> employeeReportList;

    public Long getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(Long payrollId) {
        this.payrollId = payrollId;
    }

    public List<EmployeeReport> getEmployeeReportList() {
        return employeeReportList;
    }

    public void setEmployeeReportList(List<EmployeeReport> employeeReportList) {
        this.employeeReportList = employeeReportList;
    }
}
