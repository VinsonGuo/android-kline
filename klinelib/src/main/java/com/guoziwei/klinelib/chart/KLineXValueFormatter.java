package com.guoziwei.klinelib.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.guoziwei.klinelib.model.HisData;
import com.guoziwei.klinelib.util.DateUtils;

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
            return DateUtils.formatTime(mData.get((int) value).getDate());
        }
        return "";
    }
}
