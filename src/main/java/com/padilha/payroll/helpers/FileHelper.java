package com.padilha.payroll.helpers;

import com.padilha.payroll.dto.EmployeeDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
    public static String TYPE = "text/csv";
    private static String[] HEADER = { "date", "hours worked", "employee id", "job group" };
    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
    public static List<EmployeeDto> fileToUploadDto(InputStream file) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
                List<EmployeeDto> employeeDtos = new ArrayList<>();
                Iterable<CSVRecord> csvRecords = csvParser.getRecords();
                for (CSVRecord csvRecord : csvRecords) {
                    EmployeeDto employeeDto = new EmployeeDto(
                            csvRecord.get(HEADER[0]),
                            csvRecord.get(HEADER[1]),
                            csvRecord.get(HEADER[2]),
                            csvRecord.get(HEADER[3])
                    );
                    employeeDtos.add(employeeDto);
                }
                return employeeDtos;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    /**
     * Extract Payroll Id from fileName
     * @param filename
     * @return
     */
    public static Long extractPayrollId(String filename) {
        try {
            String payrollId = filename.replaceAll("[^0-9]", "");
            return Long.parseLong(payrollId);
        } catch (Exception e) {
            return null;
        }
    }
}
