package com.vinsonguo.kline;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vinsonguo.kline.model.KModel;
import com.vinsonguo.kline.model.LineModel;
import com.vinsonguo.klinelib.model.HisData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/**
 * 数据解析的示例，数据来自于R.raw.his_data的json
 * Created by guoziwei on 2017/11/23.
 */

public class Util {

    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault());
    private static SimpleDateFormat sFormat1 = new SimpleDateFormat("HHmm", Locale.getDefault());
    private static SimpleDateFormat sFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static SimpleDateFormat sFormat3 = new SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault());

    public static List<HisData> getHisData(Context context) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        String json = writer.toString();
        final List<Model> list = new Gson().fromJson(json, new TypeToken<List<Model>>() {
        }.getType());
        List<HisData> hisData = new ArrayList<>(100);
        for (Model m : list) {
            HisData data = new HisData();
            data.setHigh(m.getHigh());
            data.setLow(m.getLow());
            data.setOpen(m.getOpen());
            data.setClose(m.getClose());
            data.setVol(m.getVol());
            try {
                data.setDate(sFormat.parse(m.getsDate()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            hisData.add(data);
        }
        return hisData;
    }


    public static List<HisData> get1Day(Context context) {
        InputStream is = context.getResources().openRawResource(R.raw.oneday);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String json = writer.toString();
        final List<LineModel> list = new Gson().fromJson(json, new TypeToken<List<LineModel>>() {
        }.getType());
        List<HisData> hisData = new ArrayList<>(100);
        for (int i = 0; i < list.size(); i++) {
            LineModel m = list.get(i);
            HisData data = new HisData();
            data.setClose(m.getPrice());
            data.setVol(m.getVolume());
            data.setOpen(i == 0 ? 0 : list.get(i - 1).getPrice());
            try {
                data.setDate(sFormat1.parse(m.getTime()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            hisData.add(data);
        }
        return hisData;
    }

    public static List<List<HisData>> get5Day(Context context) {
        InputStream is = context.getResources().openRawResource(R.raw.fiveday);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String json = writer.toString();
        final List<LinkedHashMap<String, List<LineModel>>> list = new Gson().fromJson(json, new TypeToken<List<LinkedHashMap<String, List<LineModel>>>>() {
        }.getType());
        List<List<HisData>> fivedays = new ArrayList<>(5);

        for (int i = 0; i < list.size(); i++) {

            List<HisData> hisData = new ArrayList<>(100);
            List<LineModel> lineModels = list.get(i).values().iterator().next();
            String time = list.get(i).keySet().iterator().next();

            for (int j = 0; j < lineModels.size(); j++) {
                LineModel m = lineModels.get(j);
                HisData data = new HisData();
                data.setClose(m.getPrice());
                data.setVol(m.getVolume());
                data.setOpen(j == 0 ? 0 : lineModels.get(j - 1).getPrice());
                try {
                    data.setDate(sFormat3.parse(time + m.getTime()).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                hisData.add(data);
            }
            fivedays.add(hisData);
        }
        return fivedays;
    }

    public static List<HisData> getK(Context context, int day) {
        int res = R.raw.day_k;
        if (day == 7) {
            res = R.raw.week_k;
        } else if (day == 30) {
            res = R.raw.month_k;
        }
        InputStream is = context.getResources().openRawResource(res);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String json = writer.toString();
        final List<KModel> list = new Gson().fromJson(json, new TypeToken<List<KModel>>() {
        }.getType());
        List<HisData> hisData = new ArrayList<>(100);
        for (int i = 0; i < list.size(); i++) {
            KModel m = list.get(i);
            HisData data = new HisData();
            data.setClose(m.getPrice_c());
            data.setOpen(m.getPrice_o());
            data.setHigh(m.getPrice_h());
            data.setLow(m.getPrice_l());
            data.setVol(m.getVolume());
            try {
                data.setDate(sFormat2.parse(m.getTime()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            hisData.add(data);
        }
        return hisData;
    }
}
