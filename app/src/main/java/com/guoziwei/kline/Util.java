package com.guoziwei.kline;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guoziwei.klinelib.model.HisData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by dell on 2017/11/23.
 */

public class Util {

    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault());

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
}
