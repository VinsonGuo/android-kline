package com.guoziwei.klinelib.util;

import com.guoziwei.klinelib.model.HisData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/11/9.
 */

public class DataUtils {


    /**
     * 计算均线 ma等数值
     */
    public static List<HisData> calculateHisData(List<HisData> list, HisData lastData) {


        List<Double> ma5List = calculateMA(5, list);
        List<Double> ma10List = calculateMA(10, list);
        List<Double> ma20List = calculateMA(20, list);
        List<Double> ma30List = calculateMA(30, list);

        int amountVol = 0;
        if (lastData != null) {
            amountVol = lastData.getAmountVol();
        }
        for (int i = 0; i < list.size(); i++) {
            HisData hisData = list.get(i);

            hisData.setMa5(ma5List.get(i));
            hisData.setMa10(ma10List.get(i));
            hisData.setMa20(ma20List.get(i));
            hisData.setMa30(ma30List.get(i));

            amountVol += hisData.getVol();
            hisData.setAmountVol(amountVol);
            if (i > 0) {
                double total = hisData.getVol() * hisData.getClose() + list.get(i - 1).getTotal();
                hisData.setTotal(total);
                double avePrice = total / amountVol;
                hisData.setAvePrice(avePrice);
            } else if (lastData != null) {
                double total = hisData.getVol() * hisData.getClose() + lastData.getTotal();
                hisData.setTotal(total);
                double avePrice = total / amountVol;
                hisData.setAvePrice(avePrice);
            } else {
                hisData.setAmountVol(hisData.getVol());
                hisData.setAvePrice(hisData.getClose());
                hisData.setTotal(hisData.getAmountVol() * hisData.getAvePrice());
            }

        }
        return list;
    }

    public static List<HisData> calculateHisData(List<HisData> list) {
        return calculateHisData(list, null);
    }

    /**
     * according to the history data list, calculate a new data
     */
    public static HisData calculateHisData(HisData newData, List<HisData> hisDatas) {

        HisData lastData = hisDatas.get(hisDatas.size() - 1);
        int amountVol = lastData.getAmountVol();

        newData.setMa5(calculateLastMA(5, hisDatas));
        newData.setMa10(calculateLastMA(10, hisDatas));
        newData.setMa20(calculateLastMA(20, hisDatas));
        newData.setMa30(calculateLastMA(30, hisDatas));

        amountVol += newData.getVol();
        newData.setAmountVol(amountVol);

        double total = newData.getVol() * newData.getClose() + lastData.getTotal();
        newData.setTotal(total);
        double avePrice = total / amountVol;
        newData.setAvePrice(avePrice);

        return newData;
    }

    /**
     * calculate MA value, return a double list
     * @param dayCount for example: 5, 10, 20, 30
     */
    public static List<Double> calculateMA(int dayCount, List<HisData> data) {
        List<Double> result = new ArrayList<>(data.size());
        for (int i = 0, len = data.size(); i < len; i++) {
            if (i < dayCount) {
                result.add(Double.NaN);
                continue;
            }
            double sum = 0;
            for (int j = 0; j < dayCount; j++) {
                sum += data.get(i - j).getOpen();
            }
            result.add(+(sum / dayCount));
        }
        return result;
    }

    /**
     * calculate last MA value, return a double value
     */
    public static double calculateLastMA(int dayCount, List<HisData> data) {
        double result = Double.NaN;
        for (int i = 0, len = data.size(); i < len; i++) {
            if (i < dayCount) {
                result = Double.NaN;
                continue;
            }
            double sum = 0;
            for (int j = 0; j < dayCount; j++) {
                sum += data.get(i - j).getOpen();
            }
            result = (+(sum / dayCount));
        }
        return result;
    }

}
