package com.guoziwei.klinelib.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formatData(long time) {
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formatDate = dateFormat2.format(time);
        return formatDate;
    }

    public static String formatDataOnly(long time) {
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = dateFormat2.format(time);
        return formatDate;
    }


    public static String formatDateTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = dateFormat.format(date);
        return formatDate;
    }

    public static String formatDateTime(long date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = dateFormat.format(date);
        return formatDate;
    }


    public static String formatTime(long millis) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String formatDate = dateFormat.format(millis);
        return formatDate;
    }


}
