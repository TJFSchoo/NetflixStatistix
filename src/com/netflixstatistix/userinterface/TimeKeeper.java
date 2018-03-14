package com.netflixstatistix.userinterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TimeKeeper {

       public int getTime(String format) {
           Calendar rightNow = Calendar.getInstance();
           int time = 0;
           switch (format) {
               case "hour": time = rightNow.get(Calendar.HOUR_OF_DAY);
                   break;
               case "minute": time = rightNow.get(Calendar.MINUTE);
                   break;
               case "second": time = rightNow.get(Calendar.SECOND);
                   break;

               default: time = rightNow.get(Calendar.HOUR_OF_DAY);

           }
           return time;
       }

       public String greeting() {
           int hour = getTime("hour");

           if (hour>=0 && hour<=6) {
               return "Goedenavond";
           } else if (hour>=6 && hour<=12){
               return "Goedemorgen";
           } else if (hour>=12 && hour<=17){
               return "Goedemiddag";
           } else if (hour>=18 && hour<=24) {
               return "Goedenavond";
           } else {
               return "Hallo";
           }
       }

}
