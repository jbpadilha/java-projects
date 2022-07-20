package com.padilha.payroll.service;

import com.padilha.payroll.dto.EmployeeDto;
import com.padilha.payroll.helpers.FileHelper;
import com.padilha.payroll.helpers.JobGroupEnum;
import com.padilha.payroll.model.EmployeeReport;
import com.padilha.payroll.model.PayPeriod;
import com.padilha.payroll.model.PayrollReport;
import com.padilha.payroll.repository.EmployeeReportRepository;
import com.padilha.payroll.repository.PayrollRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

import static com.padilha.payroll.helpers.FileHelper.extractPayrollId;

@Service
public class PayrollService {
    private static final Logger logger = LoggerFactory.getLogger(PayrollService.class);

    @Autowired
    PayrollRepository payrollRepository;

    @Autowired
    EmployeeReportRepository employeeReportRepository;

    /**
     * Save the File Content into Database
     * @param file
     */
    public void save(MultipartFile file) {
        try {
            Long payrollId = extractPayrollId(file.getOriginalFilename());
            if (payrollId == null) {
                throw new Exception("Same Payroll Id is not allowed to upload!");
            }
            // Check if payroll Report already exist
            Optional<PayrollReport> payrollReportObj = payrollRepository.findById(payrollId);
            if (payrollReportObj.isPresent()) {
                throw new Exception("Payroll Report already Exist!");
            }
            PayrollReport payrollReport = new PayrollReport();
            payrollReport.setPayrollId(payrollId);
            // Convert to Employee Data
            List<EmployeeDto> employeeDtos = FileHelper.fileToUploadDto(file.getInputStream());
            payrollReport = addEmployeeReport(payrollReport, employeeDtos);
            payrollRepository.save(payrollReport);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Method to Add Employee Report to Payroll
     * @param payrollReport
     * @param employeeDtos
     * @return
     */
    private PayrollReport addEmployeeReport(PayrollReport payrollReport, List<EmployeeDto> employeeDtos) {
        List<EmployeeReport> employeeReports = new ArrayList<>();
        payrollReport.setEmployeeReportList(employeeReports);

        // Hash to easily identify Employee
        HashMap<Long, EmployeeReport> employeeReportHashMap = new HashMap<>();

        for (EmployeeDto employeeDto : employeeDtos) {
            Long employeeId = Long.parseLong(employeeDto.getEmployeeId());

            // Check if Payroll exist in the same Period
            EmployeeReport employeeReport = employeeReportHashMap.get(employeeId);
            PayPeriod payPeriod = getPaymentPeriod(employeeDto.getDate());
            if (employeeReport == null || !employeeReport.getPayPeriod().getStartDate().isEqual(payPeriod.getStartDate())) {
                employeeReport = new EmployeeReport();
                employeeReport.setEmployeeId(employeeId);
                employeeReport.setPayPeriod(getPaymentPeriod(employeeDto.getDate()));
                employeeReport.setAmountPaid(calculatePaymentEmployee(employeeDto.getHours(), employeeDto.getJobGroup()));
                employeeReport.setJobGroup(employeeDto.getJobGroup());
                employeeReport.setPayrollId(payrollReport.getPayrollId());
                employeeReports.add(employeeReport);
            } else {
                BigDecimal currAmount = calculatePaymentEmployee(employeeDto.getHours(), employeeDto.getJobGroup());
                employeeReport.setAmountPaid(employeeReport.getAmountPaid().add(currAmount));
            }
            employeeReportHashMap.put(employeeId, employeeReport);
        }
        employeeReports.sort(Comparator.comparingLong(EmployeeReport::getEmployeeId));
        return payrollReport;
    }

    /**
     * Method to Calculate the amount paid for the employee based on class
     * @param hours
     * @param jobGroup
     * @return
     */
    private BigDecimal calculatePaymentEmployee(String hours, String jobGroup) {
        JobGroupEnum jobGroupEnum = JobGroupEnum.valueOf(jobGroup);
        BigDecimal total = jobGroupEnum.getNumVal().multiply(BigDecimal.valueOf(Double.parseDouble(hours)));
        return total;
    }

    /**
     * Return Pre-Set PayPeriod for Bi-weekly payments (1-15 / 16-30)
     * @param currentDate
     * @return
     */
    private PayPeriod getPaymentPeriod(String currentDate) {
        DateTimeFormatter ALL_POSSIBLE_DATES = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("d/MM/yyyy"))
                .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy"))
                .appendOptional(DateTimeFormatter.ofPattern("dd/M/yyyy")).toFormatter();
        LocalDate currLocalDate = LocalDate.parse(currentDate, ALL_POSSIBLE_DATES);

        LocalDate middleMonth = LocalDate.of(currLocalDate.getYear(), currLocalDate.getMonth(), 16);

        PayPeriod payPeriod = new PayPeriod();
        if (currLocalDate.isBefore(middleMonth)) {
            payPeriod.setStartDate(LocalDate.of(currLocalDate.getYear(), currLocalDate.getMonth(), 1));
            payPeriod.setEndDate(LocalDate.of(currLocalDate.getYear(), currLocalDate.getMonth(), 15));
        } else {
            payPeriod.setStartDate(LocalDate.of(currLocalDate.getYear(), currLocalDate.getMonth(), 16));
            payPeriod.setEndDate(payPeriod.getStartDate().withDayOfMonth(1).plusMonths(1).minusDays(1));
        }
        return payPeriod;
    }

    /**
     * Get Payroll by Id
     * @param payrollId
     * @return
     */
    public PayrollReport getPayroll(Long payrollId) {
        try {
            Optional<PayrollReport> payrollReportOpt = payrollRepository.findById(payrollId);
            if (payrollReportOpt.isPresent()) {
                PayrollReport payrollReport = payrollReportOpt.get();
                return payrollReport;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * Get List og Existing Payrolls Report
     * @return
     */
    public List<PayrollReport> listPayrolls() {
        List<PayrollReport> payrollReportList = new ArrayList<>();
        try  {
            payrollReportList = payrollRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return payrollReportList;
    }
}
