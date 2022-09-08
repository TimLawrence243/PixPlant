package com.example.pixplanttrackerv1.Converter;

import java.util.Date;
import java.util.regex.Pattern;

import androidx.room.TypeConverter;

public class DateConverter {
    //Convert Long to Date.  Date is human readable but Long is stored in database
    @TypeConverter
    public static Date toDate(Long dateLong){
        if(dateLong != null){
            return new Date(dateLong);
        }
        else{
            System.out.println("NULL DATE ENTERED");
            return new Date();
        }
    }

    //Convert Date to Long - Long is stored in database
    @TypeConverter
    public static Long fromDate(Date date){
        if(date != null){
            return new Long(date.getTime());
        }
        else{
            System.out.println("NULL LONG ENTERED");
            return new Long(0);
        }
    }

    public static boolean datePatternMatches(String date){
        //Regex for date matching MM/DD/YYYY
        Pattern pattern = Pattern.compile("^(((0[13578]|(10|12))/(0[1-9]|[1-2][0-9]|3[0-1]))|(02/(0[1-9]|[1-2][0-9]))|((0[469]|11)/(0[1-9]|[1-2][0-9]|30)))/[0-9]{4}$");
        return pattern.matcher(date).matches();
    }

    public static boolean needsWaterToday(Long today, Long create, int daysBetween){
        if((today - create) % daysBetween == 0){
            return true;
        } else {
            return false;
        }
    }

    public static boolean needsWaterTomorrow(Long today, Long create, int daysBetween){
        if(daysBetween - ((today - create) % daysBetween) == 1){
            return true;
        } else {
            return false;
        }
    }

    public static boolean needsWaterYesterday(Long today, Long create, int daysBetween){
        if(daysBetween - ((today - create) % daysBetween) == (daysBetween - 1)){
            return true;
        } else {
            return false;
        }
    }

}
