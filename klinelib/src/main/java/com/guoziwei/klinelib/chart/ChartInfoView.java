package com.guoziwei.klinelib.chart;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.Chart;
import com.guoziwei.klinelib.model.HisData;

/**
 * Created by dell on 2017/10/25.
 */

public abstract class ChartInfoView extends LinearLayout {


    public Chart[] mLineCharts;
    protected Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            setVisibility(GONE);
            if (mLineCharts != null) {
                for (Chart chart : mLineCharts) {
                    chart.highlightValue(null);
                }
            }
        }
    };

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

    public void setChart(Chart... chart) {
        mLineCharts = chart;
    }
}
