package com.guoziwei.kline.chart;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.Chart;
import com.guoziwei.kline.model.HisData;

/**
 * Created by dell on 2017/10/25.
 */

public abstract class ChartInfoView extends LinearLayout {
    public ChartInfoView(Context context) {
        super(context);
    }

    public ChartInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChartInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void setData(double lastClose, HisData data);

    public abstract void setChart(Chart chart);
}
