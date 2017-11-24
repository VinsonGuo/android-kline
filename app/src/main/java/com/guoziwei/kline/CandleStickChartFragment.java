package com.guoziwei.kline;


import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.guoziwei.klinelib.chart.AppCombinedChart;
import com.guoziwei.klinelib.chart.ChartInfoView;
import com.guoziwei.klinelib.chart.ChartInfoViewHandler;
import com.guoziwei.klinelib.chart.InfoViewListener;
import com.guoziwei.klinelib.chart.KLineChartInfoView;
import com.guoziwei.klinelib.chart.KLineXValueFormatter;
import com.guoziwei.klinelib.chart.LineChartXMarkerView;
import com.guoziwei.klinelib.chart.LineChartYMarkerView;
import com.guoziwei.klinelib.chart.YValueFormatter;
import com.guoziwei.klinelib.model.HisData;
import com.guoziwei.klinelib.util.DataUtils;
import com.guoziwei.klinelib.util.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * K线图Fragment
 */
public class CandleStickChartFragment extends Fragment {

    public static final int MAX_COUNT = 60;

    private AppCombinedChart mChart;
    private ChartInfoView mInfoView;
    private LineChartXMarkerView mMvx;
    private List<HisData> mList = new ArrayList<>();
    private double mLastClose;

    public CandleStickChartFragment() {
        // Required empty public constructor
    }

    public static CandleStickChartFragment newInstance() {
        CandleStickChartFragment fragment = new CandleStickChartFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout fl = new FrameLayout(getActivity());
        mChart = new AppCombinedChart(getActivity());
        mInfoView = new KLineChartInfoView(getActivity());
        mInfoView.setLayoutParams(new FrameLayout.LayoutParams(DisplayUtils.dip2px(getActivity(), 120), ViewGroup.LayoutParams.WRAP_CONTENT));
        mInfoView.setVisibility(View.GONE);
        fl.addView(mChart);
        fl.addView(mInfoView);

        mList = DataUtils.calculateHisData(Util.getHisData(getActivity()), null);
        initChart();
        fullData(mList);

        return fl;
    }

    private void initChart() {
        mChart.getDescription().setEnabled(false);
        mChart.setNoDataText(getString(R.string.loading));
        mChart.setNoDataTextColor(ContextCompat.getColor(getActivity(), R.color.chart_no_data_color));

        mChart.setScaleYEnabled(false);

        mChart.setAutoScaleMinMaxEnabled(true);
        mChart.setPinchZoom(true);
        mChart.setDrawGridBackground(false);

        mMvx = new LineChartXMarkerView(getActivity(), mList);
        mMvx.setChartView(mChart);
        mChart.setXMarker(mMvx);

        LineChartYMarkerView mv = new LineChartYMarkerView(getActivity(), 2);
        mv.setChartView(mChart);
        mChart.setMarker(mv);

        int whiteColor = ContextCompat.getColor(getActivity(), R.color.main_text_color);
        int gridColor = ContextCompat.getColor(getActivity(), R.color.chart_grid_color);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextColor(whiteColor);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(5, true);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setValueFormatter(new KLineXValueFormatter(mList));

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTextColor(whiteColor);
        rightAxis.setLabelCount(6, true);
        rightAxis.setDrawGridLines(true);
        rightAxis.setGridColor(gridColor);
        rightAxis.setGridLineWidth(0.5f);
        rightAxis.setDrawAxisLine(false);
        rightAxis.enableGridDashedLine(5, 5, 0);
        rightAxis.setValueFormatter(new YValueFormatter(2));
//        rightAxis.setDrawAxisLine(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);


        mChart.getLegend().setEnabled(false);
        mChart.setOnChartValueSelectedListener(new InfoViewListener(getActivity(), mLastClose, mList, mInfoView));
        mChart.setOnTouchListener(new ChartInfoViewHandler(mChart));
        mChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                mChart.setDragEnabled(true);
//                mInfoView.setVisibility(View.GONE);
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {
            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });
    }


    private void fullData(List<HisData> datas) {
        //调整x轴第一个和最后一个的位置，否则会显示不完整
        mChart.getXAxis().setAxisMinimum(-0.5f);
        mChart.getXAxis().setAxisMaximum(datas.size() - 0.5f);
        ArrayList<CandleEntry> yVals1 = new ArrayList<>();
        ArrayList<Entry> aveList = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            HisData data = datas.get(i);
            yVals1.add(new CandleEntry(
                    i, (float) data.getHigh(),
                    (float) data.getLow(),
                    (float) data.getOpen(),
                    (float) data.getClose()
            ));
            aveList.add(new Entry(i, (float) data.getAvePrice()));
        }

        // K线
        CandleDataSet set1 = new CandleDataSet(yVals1, "日期");
        set1.setDrawIcons(false);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setShadowColor(Color.DKGRAY);
        set1.setShadowWidth(0.7f);
        set1.setDecreasingColor(ContextCompat.getColor(getContext(), R.color.decreasing_color));
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setShadowColorSameAsCandle(true);
        set1.setIncreasingColor(ContextCompat.getColor(getContext(), R.color.increasing_color));
        set1.setIncreasingPaintStyle(Paint.Style.FILL);
        set1.setNeutralColor(ContextCompat.getColor(getContext(), R.color.increasing_color));
        //set1.setHighlightLineWidth(1f);
        set1.setDrawValues(false);
        CandleData cd = new CandleData(set1);

        // 均线
        LineDataSet lineDataSet = new LineDataSet(aveList, "均线");
        lineDataSet.setCircleColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        lineDataSet.setColor(ContextCompat.getColor(getActivity(), R.color.ave_color));
        lineDataSet.setLineWidth(1f);
        lineDataSet.setDrawCircleHole(false);

        CombinedData data = new CombinedData();
        data.setData(cd);
        data.setData(new LineData(lineDataSet));
        mChart.setData(data);
        mChart.highlightValue(null);
        mInfoView.setVisibility(View.GONE);
        mChart.setVisibleXRange(MAX_COUNT, 20);
        ViewPortHandler port = mChart.getViewPortHandler();
        mChart.setViewPortOffsets(0, port.offsetTop(), port.offsetRight(), port.offsetBottom());
        mChart.notifyDataSetChanged();
        mChart.invalidate();
        mChart.moveViewToX(mChart.getCandleData().getEntryCount());
    }

    public void setLastClose(double lastClose) {
        mLastClose = lastClose;
    }
}
