package com.padilha.payroll.repository;

import com.padilha.payroll.model.PayrollReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayrollRepository extends JpaRepository<PayrollReport, Long> {

    Optional<PayrollReport> findById(Long id);
}
