package com.guoziwei.klinelib.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guoziwei.klinelib.model.HisData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/11/9.
 */

public class DataUtils {

    private static Gson sGson = new Gson();

    /**
     * 解析数据，并计算均线 ma等数值
     */
    public static List<HisData> parseHisData(String jsonString, HisData lastData) {
        final List<HisData> list = sGson.fromJson(jsonString, new TypeToken<List<HisData>>() {
        }.getType());

        List<Double> ma5List = calculateMA(5, list);
        List<Double> ma10List = calculateMA(10, list);
        List<Double> ma20List = calculateMA(20, list);
        List<Double> ma30List = calculateMA(30, list);

        // 这里是计算均线的公式
        int amountVol = 0;
        if (lastData != null) {
            amountVol = lastData.getAmountVol();
        }
        for (int i = 0; i < list.size(); i++) {
            HisData hisData = list.get(i);
            // 将ma设置在model中
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

//    function calculateMA (dayCount, data) {
//        var result = []
//        for (var i = 0, len = data.values.length; i < len; i++) {
//            if (i < dayCount) {
//                result.push('-')
//                continue
//            }
//            var sum = 0
//            for (var j = 0; j < dayCount; j++) {
//                sum += data.values[i - j][1]
//            }
//            result.push(+(sum / dayCount).toFixed(3))
//        }
//        return result
//    }

    // [item.sDate, item.Open, item.Close, item.Low, item.High, item.Vol]
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
}
