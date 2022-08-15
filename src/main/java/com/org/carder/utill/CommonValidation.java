package com.org.carder.utill;

import java.time.LocalDateTime;

public class CommonValidation {

    public static boolean stringNullValidation(String inputString) {
        return inputString == null || inputString.isEmpty();
    }

    public static String sinceDateValidation(String since) {
        if (since.trim().equals("")) {
        } else {
            String sinceDate = since.trim();
            if (sinceDate.length() != 10) {
            } else {
                int delimeterCount = 0;
                char ch = '-';
                for (int x = 0; x < sinceDate.length(); x++) {
                    if (sinceDate.charAt(x) == ch) {
                        delimeterCount++;
                    }
                }
                if (delimeterCount != 2) {
                }
            }
        }
        return "";
    }

    public static boolean integerIsNull(Integer intVal) {
        return intVal == null;
    }

    public static boolean isNegative(Integer value) {
        return value < 1;
    }

    public static boolean isValidDate(String fromDate, String toDate) {
        LocalDateTime firstDate = null;
        LocalDateTime secondDate = null;
        if (!CommonValidation.stringNullValidation(fromDate)) {
            firstDate = DateTimeUtil.getFormattedDateTime(fromDate);
        } else {
            firstDate = DateTimeUtil.getSriLankaTime();
        }
        if (!CommonValidation.stringNullValidation(toDate)) {
            secondDate = DateTimeUtil.getFormattedDateTime(toDate);
        }
        return secondDate.isAfter(firstDate);
    }

    public static boolean validNic(String nic){
        final String NIC1 = "^[0-9]{9}[VX]$";
        final String NIC2 = "^[0-9]{12}$";
        if(nic.matches(NIC1) || nic.matches(NIC2))
        return false;
        return true;
    }

    public static boolean isNumber(String number){
        return !number.matches("^\\d+$");
    }

    public static boolean validPassword(String validPassword){
        final String PASSWORD = "[a-zA-Z0-9\\p{all}]{0,50}";
       if (validPassword.isEmpty() || validPassword == null){
           if (validPassword.trim().length() < 8 ){
               if (!validPassword.matches(PASSWORD)){
                   return false;
               }
           }else
               return false;
       }else
           return false;

       return true;
    }

    public static boolean isValidMobile(String mobileNum){
        final String MOBILE = "^07[0-9]{8}$";
        final String TELEPHONE = "^011[0-9]{7}$";
        if(mobileNum.matches(MOBILE)) {
            return false;
        }
        else if (mobileNum.matches(TELEPHONE)){
            return false;
        }
        return true;
    }

}
