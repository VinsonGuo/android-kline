package com.guoziwei.kline;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.guoziwei.klinelib.chart.ChartInfoViewHandler;
import com.guoziwei.klinelib.chart.InfoViewListener;
import com.guoziwei.klinelib.chart.KLineXValueFormatter;
import com.guoziwei.klinelib.chart.YValueFormatter;
import com.guoziwei.klinelib.util.DataUtils;


public class FullScreenKLineChartFragment extends BaseFullScreenChartFragment {


    public FullScreenKLineChartFragment() {
        // Required empty public constructor
    }

    public static FullScreenKLineChartFragment newInstance() {
        FullScreenKLineChartFragment fragment = new FullScreenKLineChartFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    protected void initData() {
        xAxisVolume.setValueFormatter(new KLineXValueFormatter(mData));
        mChartPrice.setOnChartValueSelectedListener(new InfoViewListener(mContext, 56.01, mData, mKInfo, mChartVolume));
        mChartVolume.setOnChartValueSelectedListener(new InfoViewListener(mContext, 56.01, mData, mLineInfo, mChartPrice));
        mChartPrice.setOnTouchListener(new ChartInfoViewHandler(mChartPrice));
        axisLeftPrice.setValueFormatter(new YValueFormatter(0.01));
        mData.addAll(DataUtils.parseHisData(Util.getJsonString(mContext), null));
        setLimitLine(56.01);
        initChartKData(mChartPrice);
        initChartVolumeData(mChartVolume);
    }
}

