package com.padilha.payroll.dto;

public class EmployeeDto {

    private String date;
    private String hours;
    private String employeeId;
    private String jobGroup;

    public EmployeeDto(String date, String hours, String employeeId, String jobGroup) {
        this.date = date;
        this.hours = hours;
        this.employeeId = employeeId;
        this.jobGroup = jobGroup;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
}
