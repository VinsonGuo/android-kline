package com.guoziwei.klinelib.chart;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.guoziwei.klinelib.R;
import com.guoziwei.klinelib.model.HisData;
import com.guoziwei.klinelib.util.DateUtils;
import com.guoziwei.klinelib.util.DoubleUtil;

import java.util.Locale;

/**
 * Created by dell on 2017/9/25.
 */

public class KLineChartInfoView extends ChartInfoView {

    private TextView mTvOpenPrice;
    private TextView mTvClosePrice;
    private TextView mTvHighPrice;
    private TextView mTvLowPrice;
    private TextView mTvChangeRate;
    private TextView mTvVol;
    private TextView mTvTime;
    private View mVgChangeRate;

    public KLineChartInfoView(Context context) {
        this(context, null);
    }

    public KLineChartInfoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KLineChartInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_kline_chart_info, this);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvOpenPrice = (TextView) findViewById(R.id.tv_open_price);
        mTvClosePrice = (TextView) findViewById(R.id.tv_close_price);
        mTvHighPrice = (TextView) findViewById(R.id.tv_high_price);
        mTvLowPrice = (TextView) findViewById(R.id.tv_low_price);
        mTvChangeRate = (TextView) findViewById(R.id.tv_change_rate);
        mTvVol = (TextView) findViewById(R.id.tv_vol);
        mVgChangeRate = findViewById(R.id.vg_change_rate);
    }

    @Override
    public void setData(double lastClose, HisData data) {
        mTvTime.setText(DateUtils.formatDate(data.getDate()));
        mTvClosePrice.setText(DoubleUtil.formatDecimal(data.getClose()));
        mTvOpenPrice.setText(DoubleUtil.formatDecimal(data.getOpen()));
        mTvHighPrice.setText(DoubleUtil.formatDecimal(data.getHigh()));
        mTvLowPrice.setText(DoubleUtil.formatDecimal(data.getLow()));
//        mTvChangeRate.setText(String.format(Locale.getDefault(), "%.2f%%", (data.getClose()- data.getOpen()) / data.getOpen() * 100));
        if (lastClose == 0) {
            mVgChangeRate.setVisibility(GONE);
        } else {
            mTvChangeRate.setText(String.format(Locale.getDefault(), "%.2f%%", (data.getClose() - lastClose) / lastClose * 100));
        }
        mTvVol.setText(data.getVol() + "");
        removeCallbacks(mRunnable);
        postDelayed(mRunnable, 2000);
    }

}
