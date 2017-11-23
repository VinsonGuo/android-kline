package com.guoziwei.klinelib.chart;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.BuildConfig;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.guoziwei.klinelib.R;
import com.guoziwei.klinelib.model.HisData;
import com.guoziwei.klinelib.util.DateUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * 分时图
 * Created by guoziwei on 2017/5/13.
 */
public class TimeSharingplanChart extends RelativeLayout {

    /**
     * 实线
     */
    public static final int TYPE_FULL = 0;
    /**
     * 虚线
     */
    public static final int TYPE_DASHED = 1;
    /**
     * 均线
     */
    public static final int TYPE_AVE = 2;

    public static final int FULL_SCREEN_SHOW_COUNT = 160;
    public static final int PADDING_COUNT = 30;
    public static final int DATA_SET_PRICE = 0;
    public static final int DATA_SET_PADDING = 1;
    public static final int DATA_SET_AVE = 2;
    private List<HisData> mList = new ArrayList<>();
    private AppLineChart mChart;
    private Context mContext;
    private int mLineColor = getResources().getColor(R.color.third_text_color);
    private int transparentColor = getResources().getColor(R.color.transparent);
    private int candleGridColor = getResources().getColor(R.color.color_333333);
    private int mTextColor = getResources().getColor(R.color.second_text_color);

    /**
     * 上一次的价格
     */
    private float mLastPrice;


    private IAxisValueFormatter xValueFormatter = new IAxisValueFormatter() {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if (mList != null && value < mList.size()) {
                DateTime dateTime = new DateTime(mList.get((int) value).getsDate());
                return DateUtils.formatTime(dateTime.getMillis());
            }
            return "";
        }
    };
    /**
     * 具体信息的View
     */
    private LineChartInfoView mInfoView;

    public TimeSharingplanChart(Context context) {
        super(context);
        init(context);
    }

    public TimeSharingplanChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LineChart getChart() {
        return mChart;
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_mp_line_chart, this);
        mChart = (AppLineChart) findViewById(R.id.line_chart);
        mInfoView = (LineChartInfoView) findViewById(R.id.info);
        setupSettingParameter();
    }

    public void addEntries(List<HisData> list) {
        mList.clear();
        mList.addAll(list);
        LineData data = new LineData();
        ILineDataSet setSell = data.getDataSetByIndex(DATA_SET_PRICE);
        if (setSell == null) {
            setSell = createSet(TYPE_FULL);
            data.addDataSet(setSell);
        }

        ILineDataSet paddingSet = data.getDataSetByIndex(DATA_SET_PADDING);
        if (paddingSet == null) {
            paddingSet = createSet(DATA_SET_PADDING);
            data.addDataSet(paddingSet);
        }

        ILineDataSet aveSet = data.getDataSetByIndex(DATA_SET_AVE);
        if (aveSet == null) {
            aveSet = createSet(DATA_SET_AVE);
            data.addDataSet(aveSet);
        }

        for (int i = 0; i < mList.size(); i++) {
            HisData hisData = mList.get(i);
            data.addEntry(new Entry(i, (float) hisData.getAvePrice()), DATA_SET_AVE);
            data.addEntry(new Entry(setSell.getEntryCount(), (float) hisData.getClose()), DATA_SET_PRICE);
        }

        int size;
        if (mList.size() < FULL_SCREEN_SHOW_COUNT - PADDING_COUNT) {//小于最大的个数，补充个数与最大的相同
            size = FULL_SCREEN_SHOW_COUNT;
        } else { // 大于最大个数，向后面添加20个
            size = mList.size() + PADDING_COUNT;
        }

        for (int i = mList.size(); i < size; i++) {
            data.addEntry(new Entry(i, (float) mList.get(mList.size() - 1).getClose()), DATA_SET_PADDING);
        }

        mChart.setData(data);

        Highlight chartHighlighter = new Highlight(setSell.getEntryCount() + paddingSet.getEntryCount(), (float) mList.get(mList.size() - 1).getClose(), DATA_SET_PADDING);
        mChart.highlightValue(chartHighlighter);

        mChart.notifyDataSetChanged();
        mChart.invalidate();

        ViewPortHandler port = mChart.getViewPortHandler();
        mChart.setViewPortOffsets(0, port.offsetTop(), port.offsetRight(), port.offsetBottom());

        mChart.moveViewToX(data.getEntryCount());
        mChart.setVisibleXRange(FULL_SCREEN_SHOW_COUNT, 50);
    }


    /**
     * 刷新最后一个
     *
     * @param bid 价格
     */
    public void refreshEntry(float bid) {
        if (bid <= 0 || bid == mLastPrice) {
            return;
        }
        mLastPrice = bid;
        LineData data = mChart.getData();

        if (data != null) {
            ILineDataSet setSell = data.getDataSetByIndex(DATA_SET_PRICE);
            if (setSell == null) {
                setSell = createSet(TYPE_FULL);
                data.addDataSet(setSell);
            }

            data.removeEntry(setSell.getEntryCount(), DATA_SET_PRICE);
            Entry entry = new Entry(setSell.getEntryCount(), bid);
            data.addEntry(entry, DATA_SET_PRICE);

            ILineDataSet paddingSet = data.getDataSetByIndex(DATA_SET_PADDING);
            if (paddingSet == null) {
                paddingSet = createSet(TYPE_DASHED);
                data.addDataSet(paddingSet);
            }

            int count = paddingSet.getEntryCount();
            paddingSet.clear();
            for (int i = 0; i < count; i++) {
                paddingSet.addEntry(new Entry(setSell.getEntryCount() + i, bid));
            }

            Highlight chartHighlighter = new Highlight(setSell.getEntryCount() + paddingSet.getEntryCount(), bid, DATA_SET_PADDING);
            mChart.highlightValue(chartHighlighter);

            data.notifyDataChanged();
            mChart.notifyDataSetChanged();
            mChart.invalidate();


        }
    }


    /**
     * 增加一个entry
     */
    public void addEntry(HisData hisData) {
        LineData data = mChart.getData();

        if (data != null) {
            ILineDataSet setSell = data.getDataSetByIndex(DATA_SET_PRICE);
            if (setSell == null) {
                setSell = createSet(TYPE_FULL);
                data.addDataSet(setSell);
            }
            ILineDataSet aveSet = data.getDataSetByIndex(DATA_SET_AVE);
            if (aveSet == null) {
                aveSet = createSet(DATA_SET_AVE);
                data.addDataSet(aveSet);
            }

            int index = mList.indexOf(hisData);
            if (index >= 0) {
                mList.remove(hisData);
                data.removeEntry(index, DATA_SET_PRICE);
                data.removeEntry(index, DATA_SET_AVE);
            }
            mList.add(hisData);
            float price = (float) hisData.getClose();
            data.addEntry(new Entry(setSell.getEntryCount(), price), DATA_SET_PRICE);
            data.addEntry(new Entry(setSell.getEntryCount(), (float) hisData.getAvePrice()), DATA_SET_AVE);

            // 给padding添加entry
            ILineDataSet paddingSet = data.getDataSetByIndex(DATA_SET_PADDING);
            if (paddingSet == null) {
                paddingSet = createSet(TYPE_DASHED);
                data.addDataSet(paddingSet);
            }

            int count = paddingSet.getEntryCount();
            // 如果count大于规定的值，将count减少1，从而让实线向前走
            if (count > PADDING_COUNT && index < 0) {
                count--;
            }
            paddingSet.clear();
            for (int i = 0; i < count; i++) {
                paddingSet.addEntry(new Entry(setSell.getEntryCount() + i, price));
            }

            Highlight chartHighlighter = new Highlight(setSell.getEntryCount() + paddingSet.getEntryCount(), price, DATA_SET_PADDING);
            mChart.highlightValue(chartHighlighter);

            data.notifyDataChanged();
            mChart.notifyDataSetChanged();
            mChart.invalidate();
        }
    }

    private ILineDataSet createSet(int type) {
        LineDataSet set = new LineDataSet(null, String.valueOf(type));
//        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        if (type == TYPE_FULL) {
            set.setHighLightColor(mLineColor);
            set.setDrawHighlightIndicators(true);
//            set.setDrawVerticalHighlightIndicator(false);
            set.setHighlightLineWidth(0.5f);
            set.setCircleColor(mLineColor);
            set.setCircleRadius(1.5f);
            set.setDrawCircleHole(false);
            set.setDrawFilled(true);
            set.setColor(mLineColor);
            set.setLineWidth(1f);
            set.setFillDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_chart_fade));
        } else if (type == TYPE_AVE) {
            set.setHighlightEnabled(true);
            set.setColor(ContextCompat.getColor(mContext, R.color.ave_color));
            set.setLineWidth(1f);
            set.setCircleRadius(1.5f);
            set.setDrawCircleHole(false);
            set.setCircleColor(transparentColor);
            set.setLineWidth(0.5f);
        } else {
            set.setHighlightEnabled(true);
            set.setDrawVerticalHighlightIndicator(false);
            set.setHighLightColor(transparentColor);
            set.setColor(mLineColor);
            set.enableDashedLine(3, 40, 0);
            set.setDrawCircleHole(false);
            set.setCircleColor(transparentColor);
            set.setLineWidth(1f);
            set.setVisible(true);
        }
        set.setDrawCircles(false);
        set.setDrawValues(false);
        return set;
    }

    private void setupSettingParameter() {
        mChart.setDrawGridBackground(false);
        mChart.setBackgroundColor(ContextCompat.getColor(mContext, R.color.chart_background));
        LineChartXMarkerView mvx = new LineChartXMarkerView(mContext, mList);
        mvx.setChartView(mChart);
        mChart.setXMarker(mvx);
        mChart.setNoDataText(getContext().getString(R.string.loading));
        mChart.setNoDataTextColor(ContextCompat.getColor(mContext, R.color.third_text_color));
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setScaleYEnabled(false);
        mChart.setAutoScaleMinMaxEnabled(true);
        mChart.setLogEnabled(BuildConfig.DEBUG);

        LineChartYMarkerView mv = new LineChartYMarkerView(mContext, 0.01);
        mv.setChartView(mChart);
        mChart.setMarker(mv);
        mChart.setOnChartValueSelectedListener(new InfoViewListener(mContext, 56.86, mList, mInfoView));
        mChart.setOnTouchListener(new ChartInfoViewHandler(mChart));
        mChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent event, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                mChart.setDragEnabled(true);
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {
//                mChart.setDragEnabled(false);
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


        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(true);
        rightAxis.setGridColor(candleGridColor);
        rightAxis.setTextColor(mTextColor);
        rightAxis.setGridLineWidth(0.5f);
        rightAxis.enableGridDashedLine(5, 5, 0);
        rightAxis.setLabelCount(6, true);
        rightAxis.setDrawAxisLine(false);

        rightAxis.setValueFormatter(new YValueFormatter(0.01));
        Legend legend = mChart.getLegend();
        legend.setEnabled(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(mTextColor);
        xAxis.setGridColor(candleGridColor);
        xAxis.setLabelCount(5, true);
        xAxis.setAvoidFirstLastClipping(true);

        xAxis.setValueFormatter(xValueFormatter);

    }

    public void setNoDataText(String text) {
        mChart.setNoDataText(text);
    }


    /**
     * 获取最后一个数据
     */
    public HisData getLastData() {
        try {
            return mList.get(mList.size() - 1);
        } catch (Exception e) {
            return null;
        }
    }
}
