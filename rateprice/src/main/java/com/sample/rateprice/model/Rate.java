package com.sample.rateprice.model;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

/**
 * Class Rate which contains all Rates available
 * @author Joao
 */
@Component
public class Rate {

    private String days;
    private String times;
    private String tz;
    private String price;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Method to return an Array of Days available for Rate
     * @return
     */
    public HashSet<String> getArrayDays() {
        String[] days = this.days.split(",");
        return new HashSet<>(Arrays.asList(days));
    }

    /**
     * Method created to return if the start/end DateTime is between the Rate times
     * @param start
     * @param end
     * @return
     */
    public boolean isRateInHourRange(ZonedDateTime start, ZonedDateTime end) {
        try {
            String[] times = this.times.split("-");
            if (times.length == 2) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH[:][.]mm", Locale.ROOT);
                LocalTime timeStart = LocalTime.parse(times[0], formatter);
                LocalTime timeEnd = LocalTime.parse(times[1], formatter);
                if (start.toLocalTime().isAfter(timeStart) && end.toLocalTime().isBefore(timeEnd)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
