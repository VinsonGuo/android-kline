package com.vinsonguo.klinelib.chart;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.highlight.Highlight;

/**
 * Created by dell on 2017/10/27.
 */

public class ChartInfoViewHandler implements View.OnTouchListener {

    private BarLineChartBase mChart;
    private final GestureDetector mDetector;

    private boolean mIsLongPress = false;

    public ChartInfoViewHandler(BarLineChartBase chart) {
        mChart = chart;
        mDetector = new GestureDetector(mChart.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                mIsLongPress = true;
                Highlight h = mChart.getHighlightByTouchPoint(e.getX(), e.getY());
                if (h != null) {
                    mChart.highlightValue(h, true);
                    mChart.disableScroll();
                }
            }

        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            mIsLongPress = false;
        }
        if (mIsLongPress && event.getAction() == MotionEvent.ACTION_MOVE) {
            Highlight h = mChart.getHighlightByTouchPoint(event.getX(), event.getY());
            if (h != null) {
                mChart.highlightValue(h, true);
                mChart.disableScroll();
            }
            return true;
        }
        return false;
    }
}
