package com.padilha.payroll;

import com.padilha.payroll.model.PayrollReport;
import com.padilha.payroll.repository.PayrollRepository;
import com.padilha.payroll.service.PayrollService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.parameters.P;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PayrollApplicationTests {

	@InjectMocks
	PayrollService payrollService;

	@MockBean
	PayrollRepository payrollRepository;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@BeforeEach
	public void setUp(WebApplicationContext context) {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	private MockMultipartFile getFile() throws IOException {
		String resourceName = "time-report-42.csv";
		File file = new File("./src/test/resources/time-report-42.csv");
		InputStream inputStream = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", resourceName, String.valueOf(MediaType.MULTIPART_FORM_DATA), inputStream);
		return mockMultipartFile;
	}

	@Test
	public void testSaveFile() throws Exception{
		MockMultipartFile mockMultipartFile = getFile();
		try {
			payrollService.save(mockMultipartFile);
		} catch (Exception e) {
			fail("Expected not Exception");
		}

	}

	@Test
	public void testGetPayrollReport() throws Exception {
		PayrollReport payrollReport = new PayrollReport();
		payrollReport.setPayrollId(42L);
		when(payrollRepository.findById(any())).thenReturn(Optional.of(payrollReport));
		mvc.perform(get("/payroll/report/42")).andDo(print()).andExpect(status().isOk());
	}


	@Test
	public void testGetAllReport() throws Exception{
		mvc.perform(get("/payroll/data/all")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists());
	}

}
