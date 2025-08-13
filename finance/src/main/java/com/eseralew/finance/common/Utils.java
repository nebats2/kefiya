package com.kefiya.home.common;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static double totalYears(String fromDate, String toDate) {
        LocalDate startDate = LocalDate.parse(fromDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(toDate, DateTimeFormatter.ISO_LOCAL_DATE);
        Period period = Period.between(startDate, endDate);

        int years = period.getYears();
        int months = period.getMonths();

        return (double) (years * 12 + months) /12;
    }

    public static LocalDate date(String date){
        String dateString = "2025-06-22";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.parse(dateString, formatter);
    }

    public static long getMinuteDifference(LocalDateTime from) throws BaseException {
        if(from == null){
            throw new BaseException(ErrorMessage.invalid_date);
        }
        return  Duration.between(from, LocalDateTime.now()).toMinutes();
    }
    public static long getMinuteDifference(LocalDateTime start, LocalDateTime end) throws BaseException {
        if(start == null || end == null){
            throw new BaseException(ErrorMessage.invalid_date);
        }
        return Duration.between(start, end).toMinutes();
    }
}
