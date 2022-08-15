package com.org.carder.utill;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * ==============================================================
 * Utility methods for date/time calculations
 * ==============================================================
 **/

public class DateTimeUtil {

    public static DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate getFormattedDate(String date) {
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(date);
        } catch (Exception e) {
        }
        return (localDate);
    }

    public static Boolean checkValidDate(String date) {
        try {
          LocalDate.parse(date, FORMAT_DATE);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String getLocalDate(){
        String conDate;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        conDate = formatter.format(date);
        return conDate;
    }

    public  static LocalDate getSriLankaDate(){
        return getFormattedDate(getLocalDate());
    }

}
