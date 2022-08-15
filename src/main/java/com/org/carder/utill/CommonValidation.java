package com.org.carder.utill;


public class CommonValidation {

    public static boolean stringNullValidation(String inputString) {
        return inputString == null || inputString.isEmpty();
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
