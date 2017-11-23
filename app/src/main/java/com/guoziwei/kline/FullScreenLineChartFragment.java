package com.guoziwei.kline;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.guoziwei.klinelib.chart.ChartInfoViewHandler;
import com.guoziwei.klinelib.chart.InfoViewListener;
import com.guoziwei.klinelib.chart.KLineXValueFormatter;
import com.guoziwei.klinelib.chart.YValueFormatter;
import com.guoziwei.klinelib.util.DataUtils;


public class FullScreenLineChartFragment extends BaseFullScreenChartFragment {


    public FullScreenLineChartFragment() {
        // Required empty public constructor
    }

    public static FullScreenLineChartFragment newInstance() {
        FullScreenLineChartFragment fragment = new FullScreenLineChartFragment();
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
        axisLeftPrice.setValueFormatter(new YValueFormatter(2));
        mData.addAll(DataUtils.calculateHisData(Util.getHisData(mContext), null));
        setLimitLine(56.01);
        initChartPriceData(mChartPrice);
        initChartVolumeData(mChartVolume);
    }

}
