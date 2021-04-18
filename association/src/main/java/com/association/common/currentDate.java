package com.association.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDateTime;

public class currentDate {
//    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
//    public Date getCurrentDate(){
//        Date date=new Date(System.currentTimeMillis());
//        String validityDate2 = simpleDateFormat.format(date);
//        java.sql.Date result=java.sql.Date.valueOf(validityDate2);
//        return result;
//    }
    public LocalDateTime getCurrentDate(){
        LocalDateTime now = LocalDateTime.now();
        return now;
    }
}
