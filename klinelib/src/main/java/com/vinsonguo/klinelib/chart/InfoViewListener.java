package com.vinsonguo.klinelib.chart;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.vinsonguo.klinelib.model.HisData;
import com.vinsonguo.klinelib.util.DisplayUtils;

import java.util.List;

/**
 * Created by dell on 2017/9/28.
 */

public class InfoViewListener implements OnChartValueSelectedListener {

    private List<HisData> mList;
    private double mLastClose;
    private ChartInfoView mInfoView;
    private int mWidth;
    /**
     * if otherChart not empty, highlight will disappear after 3 second
     */
    private Chart[] mOtherChart;

    public InfoViewListener(Context context, double lastClose, List<HisData> list, ChartInfoView infoView) {
        mWidth = DisplayUtils.getWidthHeight(context)[0];
        mLastClose = lastClose;
        mList = list;
        mInfoView = infoView;
    }

    public InfoViewListener(Context context, double lastClose, List<HisData> list, ChartInfoView infoView, Chart... otherChart) {
        mWidth = DisplayUtils.getWidthHeight(context)[0];
        mLastClose = lastClose;
        mList = list;
        mInfoView = infoView;
        mOtherChart = otherChart;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        int x = (int) e.getX();
        if (x < mList.size()) {
            mInfoView.setVisibility(View.VISIBLE);
            mInfoView.setData(mLastClose, mList.get(x));
        }
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mInfoView.getLayoutParams();
        if (h.getXPx() < mWidth / 2) {
            lp.gravity = Gravity.RIGHT;
        } else {
            lp.gravity = Gravity.LEFT;
        }
        mInfoView.setLayoutParams(lp);
        if (mOtherChart != null) {
            for (Chart aMOtherChart : mOtherChart) {
                aMOtherChart.highlightValues(new Highlight[]{new Highlight(h.getX(), Float.NaN, h.getDataSetIndex())});
            }
        }
    }

    @Override
    public void onNothingSelected() {
        mInfoView.setVisibility(View.GONE);
        if (mOtherChart != null) {
            for (int i = 0; i < mOtherChart.length; i++) {
                mOtherChart[i].highlightValues(null);
            }
        }
    }
}
