package com.vinsonguo.klinelib.util;

import com.vinsonguo.klinelib.model.HisData;
import com.vinsonguo.klinelib.model.KDJ;
import com.vinsonguo.klinelib.model.MACD;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/11/9.
 */

public class DataUtils {


    /**
     * calculate average price and ma data
     */
    public static List<HisData> calculateHisData(List<HisData> list, HisData lastData) {


        List<Double> ma5List = calculateMA(5, list);
        List<Double> ma10List = calculateMA(10, list);
        List<Double> ma20List = calculateMA(20, list);
        List<Double> ma30List = calculateMA(30, list);
        MACD macd = new MACD(list);
        List<Double> bar = macd.getMACD();
        List<Double> dea = macd.getDEA();
        List<Double> dif = macd.getDIF();
        KDJ kdj = new KDJ(list);
        ArrayList<Double> d = kdj.getD();
        ArrayList<Double> k = kdj.getK();
        ArrayList<Double> j = kdj.getJ();

        long amountVol = 0;
        if (lastData != null) {
            amountVol = lastData.getAmountVol();
        }
        for (int i = 0; i < list.size(); i++) {
            HisData hisData = list.get(i);

            hisData.setMa5(ma5List.get(i));
            hisData.setMa10(ma10List.get(i));
            hisData.setMa20(ma20List.get(i));
            hisData.setMa30(ma30List.get(i));

            hisData.setMacd(bar.get(i));
            hisData.setDea(dea.get(i));
            hisData.setDif(dif.get(i));

            hisData.setD(d.get(i));
            hisData.setK(k.get(i));
            hisData.setJ(j.get(i));

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
        long amountVol = lastData.getAmountVol();

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

        MACD macd = new MACD(hisDatas);
        List<Double> bar = macd.getMACD();
        newData.setMacd(bar.get(bar.size() - 1));
        List<Double> dea = macd.getDEA();
        newData.setDea(dea.get(dea.size() - 1));
        List<Double> dif = macd.getDIF();
        newData.setDif(dif.get(dif.size() - 1));
        KDJ kdj = new KDJ(hisDatas);
        ArrayList<Double> d = kdj.getD();
        newData.setD(d.get(d.size() - 1));
        ArrayList<Double> k = kdj.getK();
        newData.setK(k.get(k.size() - 1));
        ArrayList<Double> j = kdj.getJ();
        newData.setJ(j.get(j.size() - 1));

        return newData;
    }

    /**
     * calculate MA value, return a double list
     * @param dayCount for example: 5, 10, 20, 30
     */
    public static List<Double> calculateMA(int dayCount, List<HisData> data) {
        dayCount--;
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
        dayCount--;
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
