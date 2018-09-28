package com.vinsonguo.klinelib.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatDate(long time, String format) {
        DateFormat dateFormat2 = new SimpleDateFormat(format, Locale.getDefault());
        String formatDate = dateFormat2.format(time);
        return formatDate;
    }


    public static String formatDate(long time) {
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formatDate = dateFormat2.format(time);
        return formatDate;
    }


    public static String formatDateTime(long date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String formatDate = dateFormat.format(date);
        return formatDate;
    }


    public static String formatTime(long millis) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String formatDate = dateFormat.format(millis);
        return formatDate;
    }


}
