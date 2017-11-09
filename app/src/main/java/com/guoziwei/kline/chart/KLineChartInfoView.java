package com.guoziwei.kline.chart;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.guoziwei.kline.R;
import com.guoziwei.kline.model.HisData;
import com.guoziwei.kline.util.DateUtils;
import com.guoziwei.kline.util.DoubleUtil;

import org.joda.time.DateTime;

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
    private Chart mChart;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            setVisibility(GONE);
            if (mChart != null) {
                mChart.highlightValue(null);
            }
        }
    };

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
    }

    @Override
    public void setData(double lastClose, HisData data) {
        mTvTime.setText(DateUtils.formatData(new DateTime(data.getsDate()).getMillis()));
        mTvClosePrice.setText(DoubleUtil.formatDecimal(data.getClose()));
        mTvOpenPrice.setText(DoubleUtil.formatDecimal(data.getOpen()));
        mTvHighPrice.setText(DoubleUtil.formatDecimal(data.getHigh()));
        mTvLowPrice.setText(DoubleUtil.formatDecimal(data.getLow()));
//        mTvChangeRate.setText(String.format(Locale.getDefault(), "%.2f%%", (data.getClose()- data.getOpen()) / data.getOpen() * 100));
        mTvChangeRate.setText(String.format(Locale.getDefault(), "%.2f%%", (data.getClose()- lastClose) / lastClose * 100));
        mTvVol.setText(data.getVol() + "");
        removeCallbacks(mRunnable);
        postDelayed(mRunnable, 2000);
    }

    @Override
    public void setChart(Chart chart) {
        mChart = chart;
    }
}
