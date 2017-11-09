package com.guoziwei.kline.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.guoziwei.kline.model.HisData;
import com.guoziwei.kline.util.DateUtils;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by dell on 2017/10/28.
 */

public class KLineXValueFormatter implements IAxisValueFormatter {
    private List<HisData> mData;

    public KLineXValueFormatter(List<HisData> hisDatas) {
        mData = hisDatas;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (mData != null && value < mData.size() && value >= 0) {
            DateTime dateTime = new DateTime(mData.get((int) value).getsDate());
            return DateUtils.formatTime(dateTime.getMillis());
        }
        return "";
    }
}
