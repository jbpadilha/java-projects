package com.sample.rateprice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sample.rateprice.model.Rate;
import com.sample.rateprice.model.Rates;
import com.sample.rateprice.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.prometheus.client.Counter;
import org.springframework.web.bind.annotation.*;
import io.prometheus.client.Histogram;
import java.time.ZonedDateTime;

/**
 * Application Controller which contains all API requests
 * @author Joao
 */
@RestController
public class ApplicationController {

    // request counter metric
    static final Counter REQUESTS = Counter.build()
            .name("requests_total").help("Total number of requests.").register();
    // histogram metric
    static final Histogram REQUESTS_LATENCY = Histogram.build()
            .name("requests_latency_seconds").help("Request latency in seconds.").register();

    @Autowired
    private ApplicationService applicationService;

    /**
     * /rates endpoint which overwrites the Rates Json file saved
     * @param rates
     * @return
     */
    @PutMapping("/rates")
    @ResponseBody
    public ResponseEntity<String> putRate(@RequestBody Rates rates) {
        REQUESTS.inc();
        Histogram.Timer requestTimer = REQUESTS_LATENCY.startTimer();
        if (rates == null || rates.getRates().length == 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {
            applicationService.save(rates.getRates());
        } catch (Exception e) {

        } finally {
            requestTimer.observeDuration();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * /rates Get endpoint which returns all rates available in the json file
     * @return
     */
    @GetMapping("/rates")
    @ResponseBody
    public ResponseEntity<Rates> getRate() {
        REQUESTS.inc();
        Histogram.Timer requestTimer = REQUESTS_LATENCY.startTimer();
        try{
            return new ResponseEntity(applicationService.getRates(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } finally {
            requestTimer.observeDuration();
        }
    }

    /**
     * /price endpoint which returns the price of the times passed by parameter
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/price")
    public ResponseEntity<ObjectNode> getPrice(@RequestParam("start")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime start,
                               @RequestParam("end")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime end) {
        REQUESTS.inc();
        Histogram.Timer requestTimer = REQUESTS_LATENCY.startTimer();
        ObjectNode objectNode = null;
        try {
            Rate rate = applicationService.getRatePrice(start, end);
            ObjectMapper mapper = new ObjectMapper();
            objectNode = mapper.createObjectNode();
            objectNode.put("price", rate.getPrice());
            return new ResponseEntity(objectNode, HttpStatus.OK);
        } catch (Exception e) {
            objectNode.put("Error", "unavailable");
            return new ResponseEntity(objectNode, HttpStatus.BAD_REQUEST);
        } finally {
            requestTimer.observeDuration();
        }
    }


}
