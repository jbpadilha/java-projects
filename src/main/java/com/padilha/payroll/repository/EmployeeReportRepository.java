package com.padilha.payroll.repository;

import com.padilha.payroll.model.EmployeeReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeReportRepository  extends JpaRepository<EmployeeReport, Long> {
}
