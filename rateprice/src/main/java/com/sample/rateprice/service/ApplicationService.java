package com.sample.rateprice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.rateprice.model.Rate;
import com.sample.rateprice.model.Rates;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Class where relies all business logic for each endpoint
 */
@Service
public class ApplicationService {

    ObjectMapper mapper = new ObjectMapper();
    // Rates Object Singleton - Retain the json file converted into Rate Object
    public List<Rate> rates = new LinkedList<>();
    public ApplicationService() throws Exception {
        try {
            File file = Paths.get("rates.json").toFile();
            if (file != null) {
                Rates ratesArray = mapper.readValue(file, Rates.class);
                rates = new LinkedList<>(Arrays.asList(ratesArray.getRates()));
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

    /**
     * Save the rates overwriting the previous one saved in json file
     * @param ratesArray
     * @throws Exception
     */
    public void save(Rate[] ratesArray) throws Exception {
        if (ratesArray != null && ratesArray.length > 0) {
            try {
                Rates ratesJson = new Rates();
                ratesJson.setRates(ratesArray);
                rates = new LinkedList<>(Arrays.asList(ratesArray));
                // convert rate to JSON file
                mapper.writeValue(Paths.get("rates.json").toFile(), ratesJson);
            } catch (IOException e) {
                throw new Exception();
            }
        }
    }

    /**
     * Method to retrieve all saved rates
     * @return
     */
    public Rates getRates() {
        Rates ratesReturn = new Rates();
        Rate[] rateArray = new Rate[rates.size()];
        rateArray = rates.toArray(rateArray);
        ratesReturn.setRates(rateArray);
        return ratesReturn;
    }

    /**
     * Method to return a Price from dates passed by parameters
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public Rate getRatePrice(ZonedDateTime start, ZonedDateTime end) throws Exception {
        // Does not support different days
        long diffDays = ChronoUnit.DAYS.between(start, end);
        if (diffDays != 0) {
            throw new Exception();
        }
        // Find match rates with dates
        DayOfWeek daySearched = start.getDayOfWeek();
        //ISO-8601
        String searchDayShort = daySearched.getDisplayName(TextStyle.SHORT, Locale.US).toLowerCase();
        List<Rate> foundRates = rates.stream().filter(rate1 -> {
            HashSet<String> days = rate1.getArrayDays();
            // Testing Day range
            if (days.contains(searchDayShort)) {
                // Testing hour range
                return rate1.isRateInHourRange(start, end);
            }
            return false;
        }).collect(Collectors.toList());

        // It does not support multiple rates
        if (foundRates.size() != 1) {
            throw new Exception();
        }

        return foundRates.get(0);
    }
}
