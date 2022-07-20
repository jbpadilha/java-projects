package com.padilha.payroll.controllers;

import com.padilha.payroll.helpers.FileHelper;
import com.padilha.payroll.model.MessageResponse;
import com.padilha.payroll.model.PayrollReport;
import com.padilha.payroll.service.PayrollService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/payroll")
@SecurityRequirement(name = "payroll-api")
public class PayrollController {

    private static final Logger logger = LoggerFactory.getLogger(PayrollController.class);

    @Autowired
    PayrollService payrollService;

    @PostMapping(value = "/data/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<MessageResponse> uploadEmployeesData(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (FileHelper.hasCSVFormat(file)) {
            try {
                payrollService.save(file);
                message = "Uploaded successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));

            } catch (Exception e) {
                message = "Could not upload the file " + file.getOriginalFilename() +
                        " with the employees data: " + e.getMessage() + "." ;
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
            }
        }
        message = "Please follow the instructions and upload a csv file with employee data!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<?> getPayrollReport(@PathVariable Long id) {
        try{
            PayrollReport payrollReport = payrollService.getPayroll(id);
            if (payrollReport == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(payrollReport, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting Payroll by indicated Id");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Error"));
    }

    @GetMapping("/data/all")
    public ResponseEntity<List<PayrollReport>> getAllPayrollReport() {
        try {
            List<PayrollReport> payrollReportList = payrollService.listPayrolls();
            return new ResponseEntity<>(payrollReportList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting Payrolls list");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
