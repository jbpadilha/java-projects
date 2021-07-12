package com.sample.rateprice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.rateprice.model.Rates;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
class RatepriceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Test for Put Service")
	void putTest() throws JsonProcessingException {
		String sampleJson = "{\n" +
				"  \"rates\": [\n" +
				"    {\n" +
				"      \"days\": \"mon,tues,thurs\",\n" +
				"      \"times\": \"0900-2100\",\n" +
				"      \"tz\": \"America/Chicago\",\n" +
				"      \"price\": 1500\n" +
				"    },\n" +
				"    {\n" +
				"      \"days\": \"fri,sat,sun\",\n" +
				"      \"times\": \"0900-2100\",\n" +
				"      \"tz\": \"America/Chicago\",\n" +
				"      \"price\": 2000\n" +
				"    },\n" +
				"    {\n" +
				"      \"days\": \"wed\",\n" +
				"      \"times\": \"0600-1800\",\n" +
				"      \"tz\": \"America/Chicago\",\n" +
				"      \"price\": 1750\n" +
				"    },\n" +
				"    {\n" +
				"      \"days\": \"mon,wed,sat\",\n" +
				"      \"times\": \"0100-0500\",\n" +
				"      \"tz\": \"America/Chicago\",\n" +
				"      \"price\": 1000\n" +
				"    },\n" +
				"    {\n" +
				"      \"days\": \"sun,tues\",\n" +
				"      \"times\": \"0100-0700\",\n" +
				"      \"tz\": \"America/Chicago\",\n" +
				"      \"price\": 925\n" +
				"    },\n" +
				"    {\n" +
				"      \"days\": \"wed\",\n" +
				"      \"times\": \"0500-2000\",\n" +
				"      \"tz\": \"America/Chicago\",\n" +
				"      \"price\": 100\n" +
				"    }\n" +
				"  ]\n" +
				"}";

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Rates rates = objectMapper.readValue(sampleJson, Rates.class);
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Rates> entity = new HttpEntity<Rates>(rates,headers);

			final String baseUrl = "http://localhost:5000/rates";
			URI uri = new URI(baseUrl);
			restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class).getBody();

			ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
			//Verify request succeed
			Assertions.assertEquals(200, result.getStatusCodeValue());
			Assertions.assertEquals(true, result.getBody().contains("100"));
		} catch (HttpClientErrorException e) {
			Assertions.assertEquals(400, e.getRawStatusCode());
			Assertions.assertEquals(true, e.getResponseBodyAsString().contains("Missing request header"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			Assertions.assertEquals(true, e.getMessage().contains("Illegal character"));
		}
	}

	@Test
	@DisplayName("Test for GET price Service")
	void price() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			final String baseUrl = "http://localhost:5000/price";
			URI uri = new URI(baseUrl);

			// Parameters
			Map<String, String> params = new HashMap<>();
			params.put("start", "2015-07-01T07:00:00-05:00");
			params.put("end", "2015-07-01T12:00:00-05:00");

			ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
			//Verify request succeed
			Assertions.assertEquals(200, result.getStatusCodeValue());
			Assertions.assertEquals(true, result.getBody().contains("1750"));

		} catch (HttpClientErrorException e) {
			Assertions.assertEquals(400, e.getRawStatusCode());
			Assertions.assertEquals(true, e.getResponseBodyAsString().contains("Missing request header"));
		} catch (URISyntaxException e) {
			Assertions.assertEquals(true, e.getMessage().contains("Illegal character"));
		}

	}

}
