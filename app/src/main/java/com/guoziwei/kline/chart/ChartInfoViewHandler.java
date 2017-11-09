package com.guoziwei.kline.chart;

import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.BarLineChartBase;

/**
 * Created by dell on 2017/10/27.
 */

public class ChartInfoViewHandler implements View.OnTouchListener {
    private BarLineChartBase mChart;
    private long then;

    public ChartInfoViewHandler(BarLineChartBase chart) {
        mChart = chart;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            then = System.currentTimeMillis();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if ((System.currentTimeMillis() - then) > 1000) {
                mChart.setDragEnabled(false);
            }
        }
        return false;
    }
}
