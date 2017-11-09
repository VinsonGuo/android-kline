package com.guoziwei.kline.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guoziwei.kline.R;
import com.guoziwei.kline.model.HisData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

/**
 * Created by dell on 2017/11/9.
 */

public class DataUtils {

    private static Gson sGson = new Gson();

    /**
     * 解析数据，增加了均线
     */
    public static List<HisData> parseHisData(Context context, HisData lastData) {
        InputStream is = context.getResources().openRawResource(R.raw.his_data);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        String jsonString = writer.toString();
        final List<HisData> list = sGson.fromJson(jsonString, new TypeToken<List<HisData>>() {
        }.getType());
        int amountVol = 0;
        if (lastData != null) {
            amountVol = lastData.getAmountVol();
        }
        for (int i = 0; i < list.size(); i++) {
            HisData hisData = list.get(i);
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
}
