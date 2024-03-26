package com.yashsales.utility;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

public class DateUtils {
	
    public static Timestamp getCurrentTimestamp() {
    	 Timestamp ts =new Timestamp(System.currentTimeMillis());
    	 Calendar c=Calendar.getInstance();
    	 c.setTimeInMillis(ts.getTime());
    	 c.set(Calendar.HOUR_OF_DAY, 0);
    	 c.set(Calendar.MINUTE, 0);
    	 c.set(Calendar.SECOND, 0);
    	 c.set(Calendar.MILLISECOND, 0);
    	 ts.setTime(c.getTimeInMillis());
    	 return ts; 
	}
    
    public static Timestamp getFullCurrentTimestamp() {
   	 Timestamp ts =new Timestamp(System.currentTimeMillis());
   	 return ts; 
	}
    
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
