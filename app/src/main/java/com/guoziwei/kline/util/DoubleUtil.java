package com.guoziwei.kline.util;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by hou on 2015/8/14.
 */
public class DoubleUtil {

    public static double parseDouble(String parserDouble) {
        try {
            return Double.parseDouble(parserDouble);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String format2Decimal(Double d) {
        NumberFormat instance = DecimalFormat.getInstance();
        instance.setMinimumFractionDigits(2);
        instance.setMaximumFractionDigits(2);
        return instance.format(d);
    }

    public static String formatDecimal(Double d) {
        NumberFormat instance = DecimalFormat.getInstance();
        instance.setMinimumFractionDigits(0);
        instance.setMaximumFractionDigits(8);
        return instance.format(d).replace(",", "");
    }

    public static String getStringByTick(double num, double tick) {
        int digit = getDigitByTick(tick);
        return getStringByDigits(num, digit);
    }


    public static int getDigitByTick(double tick) {
        if (tick == 1) {
            return 0;
        }
        String s = String.valueOf(tick);
        if (TextUtils.isEmpty(s)) {
            return 0;
        }
        return s.length() - 2;
    }

    /**
     * 根据精度得到一个小数的字符串
     *
     * @param num    double小数
     * @param digits 精度
     */
    public static String getStringByDigits(double num, int digits) {
        if (digits == 0) {
            return (int) num + "";
        } else {
            NumberFormat instance = DecimalFormat.getInstance();
            instance.setMinimumFractionDigits(digits);
            instance.setMaximumFractionDigits(digits);
            return instance.format(num).replace(",", "");
        }
    }
}
