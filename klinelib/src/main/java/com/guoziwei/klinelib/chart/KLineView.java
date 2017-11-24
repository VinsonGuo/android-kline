package com.guoziwei.klinelib.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.guoziwei.klinelib.R;
import com.guoziwei.klinelib.model.HisData;
import com.guoziwei.klinelib.util.DataUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 专业版K线图
 * Created by guoziwei on 2017/10/26.
 */
public class KLineView extends LinearLayout {


    public static final int NORMAL_LINE = 0;
    /**
     * 均线
     */
    public static final int AVE_LINE = 1;
    /**
     * 隐藏的线
     */
    public static final int INVISIABLE_LINE = 6;

    public static final int HIGHLIGHT_LINE = 3;

    public static final int MA5 = 5;
    public static final int MA10 = 10;
    public static final int MA20 = 20;
    public static final int MA30 = 30;

    public int MAX_COUNT_LINE = 300;
    public int MIN_COUNT_LINE = 50;
    public int MAX_COUNT_K = 200;
    public int MIN_COUNT_K = 30;

    protected AppCombinedChart mChartPrice;
    protected AppCombinedChart mChartVolume;
    protected XAxis xAxisPrice;
    protected YAxis axisRightPrice;
    protected YAxis axisLeftPrice;
    protected XAxis xAxisVolume;
    protected YAxis axisRightVolume;
    protected YAxis axisLeftVolume;
    protected List<HisData> mData = new ArrayList<>(300);
    protected ChartInfoView mLineInfo;
    protected ChartInfoView mKInfo;
    protected Context mContext;
    private int mAxisColor;
    private int mTransparentColor;

    /**
     * 上一次的最新价
     */
    private double mLastPrice;

    /**
     * 昨收价
     */
    private double mLastClose;

    /**
     * 报价的精度
     */
    private int mDigits = 2;

    public KLineView(Context context) {
        this(context, null);
    }

    public KLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_kline, this);
        mChartPrice = (AppCombinedChart) findViewById(R.id.line_chart);
        mChartVolume = (AppCombinedChart) findViewById(R.id.bar_chart);
        mLineInfo = (ChartInfoView) findViewById(R.id.line_info);
        mKInfo = (ChartInfoView) findViewById(R.id.k_info);
        mLineInfo.setChart(mChartPrice, mChartVolume);
        mKInfo.setChart(mChartPrice, mChartVolume);
        mAxisColor = ContextCompat.getColor(mContext, R.color.axis_color);
        mTransparentColor = getResources().getColor(android.R.color.transparent);
        mChartVolume.setNoDataText(context.getString(R.string.loading));
        mChartPrice.setNoDataText(context.getString(R.string.loading));
        initChartPrice();
        initChartVolume();
        initChartListener();
    }


    protected void initChartPrice() {
        mChartPrice.setScaleEnabled(true);//启用图表缩放事件
        mChartPrice.setDrawBorders(false);//是否绘制边线
        mChartPrice.setBorderWidth(1);//边线宽度，单位dp
        mChartPrice.setDragEnabled(true);//启用图表拖拽事件
        mChartPrice.setScaleYEnabled(false);//启用Y轴上的缩放
        mChartPrice.getDescription().setEnabled(false);//右下角对图表的描述信息
        mChartPrice.setAutoScaleMinMaxEnabled(true);
        LineChartXMarkerView mvx = new LineChartXMarkerView(mContext, mData);
        mvx.setChartView(mChartPrice);
        mChartPrice.setXMarker(mvx);
        Legend lineChartLegend = mChartPrice.getLegend();//主要控制左下方的图例的
        lineChartLegend.setEnabled(false);//是否绘制 Legend 图例

        //x轴
        xAxisPrice = mChartPrice.getXAxis();//控制X轴的
        xAxisPrice.setDrawLabels(false);//是否显示X坐标轴上的刻度，默认是true
        xAxisPrice.setDrawAxisLine(false);//是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxisPrice.setDrawGridLines(false);//是否显示X坐标轴上的刻度竖线，默认是true

        //左边y
        axisLeftPrice = mChartPrice.getAxisLeft();
        axisLeftPrice.setLabelCount(5, true); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisLeftPrice.setDrawLabels(true);//是否显示Y坐标轴上的刻度，默认是true
        axisLeftPrice.setDrawGridLines(false);//是否显示Y坐标轴上的刻度竖线，默认是true
        /*轴不显示 避免和border冲突*/
        axisLeftPrice.setDrawAxisLine(false);//是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        axisLeftPrice.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART); //参数是INSIDE_CHART(Y轴坐标在内部) 或 OUTSIDE_CHART(在外部（默认是这个）)
        axisLeftPrice.setTextColor(mAxisColor);
        axisLeftPrice.setValueFormatter(new YValueFormatter(mDigits));


        //右边y
        axisRightPrice = mChartPrice.getAxisRight();
        axisRightPrice.setLabelCount(5, true);//参考上面
        axisRightPrice.setDrawLabels(false);//参考上面
        axisRightPrice.setDrawGridLines(false);//参考上面
        axisRightPrice.setDrawAxisLine(false);//参考上面
        axisRightPrice.setTextColor(mAxisColor);
        axisRightPrice.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART); //参数是INSIDE_CHART(Y轴坐标在内部) 或 OUTSIDE_CHART(在外部（默认是这个）)

    }


    protected void initChartVolume() {
        mChartVolume.setScaleEnabled(true);//启用图表缩放事件
        mChartVolume.setDrawBorders(false);//是否绘制边线
        mChartVolume.setBorderWidth(1);//边线宽度，单位dp
        mChartVolume.setDragEnabled(true);//启用图表拖拽事件
        mChartVolume.setScaleYEnabled(false);//启用Y轴上的缩放
//        mChartVolume.setBorderColor(getResources().getColor(R.color.border_color));//边线颜色
        mChartVolume.getDescription().setEnabled(false);//右下角对图表的描述信息
        mChartVolume.setAutoScaleMinMaxEnabled(true);
        Legend lineChartLegend = mChartVolume.getLegend();
        lineChartLegend.setEnabled(false);//是否绘制 Legend 图例

        //x轴
        xAxisVolume = mChartVolume.getXAxis();
        xAxisVolume.setDrawLabels(true);
        xAxisVolume.setDrawAxisLine(false);
        xAxisVolume.setDrawGridLines(false);
        xAxisVolume.setTextColor(mAxisColor);
        xAxisVolume.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisVolume.setLabelCount(5, true);
        xAxisVolume.setAvoidFirstLastClipping(true);

        xAxisVolume.setValueFormatter(new KLineXValueFormatter(mData));

        //左边y
        axisLeftVolume = mChartVolume.getAxisLeft();
        axisLeftVolume.setDrawLabels(true);//参考上面
        axisLeftVolume.setDrawGridLines(false);//参考上面
        /*轴不显示 避免和border冲突*/
        axisLeftVolume.setLabelCount(3, true);
        axisLeftVolume.setDrawAxisLine(false);//参考上面
        axisLeftVolume.setTextColor(mAxisColor);
        axisLeftVolume.setAxisMinimum(0);
        axisLeftVolume.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeftVolume.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String s;
                if (value > 10000) {
                    s = (int) (value / 10000) + "万";
                } else if (value > 1000) {
                    s = (int) (value / 1000) + "千";
                } else {
                    s = (int) value + "";
                }
                return String.format(Locale.getDefault(), "%1$5s", s);
            }
        });


        //右边y
        axisRightVolume = mChartVolume.getAxisRight();
        axisRightVolume.setDrawLabels(false);//参考上面
        axisRightVolume.setDrawGridLines(false);//参考上面
        axisRightVolume.setDrawAxisLine(false);//参考上面


    }

    private void initChartListener() {
        // 将K线控的滑动事件传递给交易量控件
        mChartPrice.setOnChartGestureListener(new CoupleChartGestureListener(mChartPrice, mChartVolume));
//        // 将交易量控件的滑动事件传递给K线控件
        mChartVolume.setOnChartGestureListener(new CoupleChartGestureListener(mChartVolume, mChartPrice));
        mChartPrice.setOnChartValueSelectedListener(new InfoViewListener(mContext, mLastClose, mData, mKInfo, mChartVolume));
        mChartVolume.setOnChartValueSelectedListener(new InfoViewListener(mContext, mLastClose, mData, mKInfo, mChartPrice));
        mChartPrice.setOnTouchListener(new ChartInfoViewHandler(mChartPrice));
        mChartVolume.setOnTouchListener(new ChartInfoViewHandler(mChartVolume));
    }

    public void initChartKData(List<HisData> hisDatas) {
        mData.clear();
        mData.addAll(DataUtils.calculateHisData(hisDatas));

        ArrayList<CandleEntry> lineCJEntries = new ArrayList<>(MAX_COUNT_K);
        ArrayList<Entry> lineJJEntries = new ArrayList<>(MAX_COUNT_K);
        ArrayList<Entry> ma5Entries = new ArrayList<>(MAX_COUNT_K);
        ArrayList<Entry> ma10Entries = new ArrayList<>(MAX_COUNT_K);
        ArrayList<Entry> ma20Entries = new ArrayList<>(MAX_COUNT_K);
        ArrayList<Entry> ma30Entries = new ArrayList<>(MAX_COUNT_K);
        ArrayList<Entry> paddingEntries = new ArrayList<>(MAX_COUNT_K);

        for (int i = 0; i < mData.size(); i++) {
            HisData hisData = mData.get(i);
            lineCJEntries.add(new CandleEntry(i, (float) hisData.getHigh(), (float) hisData.getLow(), (float) hisData.getOpen(), (float) hisData.getClose()));
            lineJJEntries.add(new Entry(i, (float) hisData.getAvePrice()));
            ma5Entries.add(new Entry(i, (float) hisData.getMa5()));
            ma10Entries.add(new Entry(i, (float) hisData.getMa10()));
            ma20Entries.add(new Entry(i, (float) hisData.getMa20()));
            ma30Entries.add(new Entry(i, (float) hisData.getMa30()));
        }

        if (!mData.isEmpty() && mData.size() < MAX_COUNT_K) {
            for (int i = mData.size(); i < MAX_COUNT_K; i++) {
                paddingEntries.add(new Entry(i, (float) mData.get(mData.size() - 1).getClose()));
            }
        }

        LineData lineData = new LineData(
                setLine(AVE_LINE, lineJJEntries),
                setLine(INVISIABLE_LINE, paddingEntries),
                setLine(MA5, ma5Entries),
                setLine(MA10, ma10Entries),
                setLine(MA20, ma20Entries),
                setLine(MA30, ma30Entries));
        CandleData candleData = new CandleData(setKLine(NORMAL_LINE, lineCJEntries));
        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        combinedData.setData(candleData);
        mChartPrice.setData(combinedData);

        mChartPrice.setVisibleXRange(MAX_COUNT_K, MIN_COUNT_K);
        mChartPrice.notifyDataSetChanged();
        mChartPrice.invalidate();
        mChartPrice.moveViewToX(combinedData.getEntryCount());
        initChartVolumeData();
    }

    public void initChartPriceData(List<HisData> hisDatas) {

        mData.clear();
        mData.addAll(DataUtils.calculateHisData(hisDatas));

        ArrayList<Entry> lineCJEntries = new ArrayList<>(MAX_COUNT_LINE);
        ArrayList<Entry> lineJJEntries = new ArrayList<>(MAX_COUNT_LINE);
        ArrayList<Entry> paddingEntries = new ArrayList<>(MAX_COUNT_LINE);

        for (int i = 0; i < mData.size(); i++) {
            lineCJEntries.add(new Entry(i, (float) mData.get(i).getClose()));
            lineJJEntries.add(new Entry(i, (float) mData.get(i).getAvePrice()));
        }
        if (!mData.isEmpty() && mData.size() < MAX_COUNT_LINE) {
            for (int i = mData.size(); i < MAX_COUNT_LINE; i++) {
                paddingEntries.add(new Entry(i, (float) mData.get(mData.size() - 1).getClose()));
            }
        }
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(setLine(NORMAL_LINE, lineCJEntries));
        sets.add(setLine(AVE_LINE, lineJJEntries));
        sets.add(setLine(INVISIABLE_LINE, paddingEntries));
        /*注老版本LineData参数可以为空，最新版本会报错，修改进入ChartData加入if判断*/
        LineData lineData = new LineData(sets);

        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        mChartPrice.setData(combinedData);

        mChartPrice.setVisibleXRange(MAX_COUNT_LINE, MIN_COUNT_LINE);

        mChartPrice.notifyDataSetChanged();
        mChartPrice.invalidate();
        mChartPrice.moveViewToX(combinedData.getEntryCount());
        initChartVolumeData();
    }

    /**
     * @param type 0 分时图的线 1 均线 5 ma5 ....
     */
    @android.support.annotation.NonNull
    public LineDataSet setLine(int type, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + type);
        lineDataSetMa.setDrawValues(false);
        if (type == NORMAL_LINE) {
            lineDataSetMa.setColor(getResources().getColor(R.color.normal_line_color));
            lineDataSetMa.setCircleColor(ContextCompat.getColor(mContext, R.color.normal_line_color));
        } else if (type == AVE_LINE) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ave_color));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == HIGHLIGHT_LINE) {
            lineDataSetMa.setVisible(false);
            lineDataSetMa.setHighlightEnabled(true);
        } else if (type == MA5) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma5));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == MA10) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma10));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == MA20) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma20));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == MA30) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma30));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else {
            lineDataSetMa.setVisible(false);
            lineDataSetMa.setHighlightEnabled(false);
        }
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setCircleRadius(1f);

        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setDrawCircleHole(false);

        return lineDataSetMa;
    }

    @android.support.annotation.NonNull
    public CandleDataSet setKLine(int type, ArrayList<CandleEntry> lineEntries) {
        CandleDataSet set1 = new CandleDataSet(lineEntries, "KLine" + type);
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
        if (type != NORMAL_LINE) {
            set1.setVisible(false);
        }
        return set1;
    }

    private void initChartVolumeData() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<Entry> paddingEntries = new ArrayList<>();
        ArrayList<Entry> highlightEntries = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            HisData t = mData.get(i);
            barEntries.add(new BarEntry(i, t.getVol(), t));
            highlightEntries.add(new Entry(i, 0));
        }
        int maxCount = mChartPrice.getData().getCandleData() == null ? MAX_COUNT_LINE : MAX_COUNT_K;
        if (!mData.isEmpty() && mData.size() < maxCount) {
            for (int i = mData.size(); i < maxCount; i++) {
                paddingEntries.add(new Entry(i, 0));
            }
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "成交量");
        barDataSet.setHighLightAlpha(255);
        barDataSet.setHighLightColor(getResources().getColor(R.color.highlight_color));
        barDataSet.setDrawValues(false);//是否在线上绘制数值
        barDataSet.setColors(getResources().getColor(R.color.increasing_color), getResources().getColor(R.color.decreasing_color));
        BarData barData = new BarData(barDataSet);
        LineData lineData = new LineData(setLine(HIGHLIGHT_LINE, highlightEntries), setLine(INVISIABLE_LINE, paddingEntries));
        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        combinedData.setData(barData);
        mChartVolume.setData(combinedData);

        if (mChartPrice.getData().getCandleData() != null) {
            mChartVolume.setVisibleXRange(MAX_COUNT_K, MIN_COUNT_K);
        } else {
            mChartVolume.setVisibleXRange(MAX_COUNT_LINE, MIN_COUNT_LINE);
        }

        setOffset();
        mChartVolume.notifyDataSetChanged();
        mChartVolume.invalidate();
        mChartVolume.moveViewToX(combinedData.getEntryCount());
    }

    /**
     * 刷新最后的一个价格
     */
    public void refreshData(float price) {
        try {
            if (price <= 0 || price == mLastPrice) {
                return;
            }
            mLastPrice = price;
            CombinedData data = mChartPrice.getData();
            if (data == null) return;
            LineData lineData = data.getLineData();
            if (lineData != null) {
                ILineDataSet set = lineData.getDataSetByIndex(0);
                if (set.removeLast()) {
                    set.addEntry(new Entry(set.getEntryCount(), price));
                }
            }
            CandleData candleData = data.getCandleData();
            if (candleData != null) {
                ICandleDataSet set = candleData.getDataSetByIndex(0);
                if (set.removeLast()) {
                    HisData hisData = mData.get(mData.size() - 1);
                    hisData.setClose(price);
                    hisData.setHigh(Math.max(hisData.getHigh(), price));
                    hisData.setLow(Math.min(hisData.getLow(), price));
                    set.addEntry(new CandleEntry(set.getEntryCount(), (float) hisData.getHigh(), (float) hisData.getLow(), (float) hisData.getOpen(), (float) price));
                }
            }
            mChartPrice.notifyDataSetChanged();
            mChartPrice.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addKData(HisData hisData) {
        try {
            hisData = DataUtils.calculateHisData(hisData, mData);
            CombinedData combinedData = mChartPrice.getData();
            LineData priceData = combinedData.getLineData();
            ILineDataSet aveSet = priceData.getDataSetByIndex(0);
            ILineDataSet ma5Set = priceData.getDataSetByIndex(2);
            ILineDataSet ma10Set = priceData.getDataSetByIndex(3);
            ILineDataSet ma20Set = priceData.getDataSetByIndex(4);
            ILineDataSet ma30Set = priceData.getDataSetByIndex(5);
            CandleData kData = combinedData.getCandleData();
            ICandleDataSet kSet = kData.getDataSetByIndex(0);
            IBarDataSet volSet = mChartVolume.getData().getBarData().getDataSetByIndex(0);
            if (mData.contains(hisData)) {
                int index = mData.indexOf(hisData);
                kSet.removeEntry(index);
                aveSet.removeEntry(index);
                volSet.removeEntry(index);
                mData.remove(index);
            }
            mData.add(hisData);
            kSet.addEntry(new CandleEntry(kSet.getEntryCount(), (float) hisData.getHigh(), (float) hisData.getLow(), (float) hisData.getOpen(), (float) hisData.getClose()));
            aveSet.addEntry(new Entry(aveSet.getEntryCount(), (float) hisData.getAvePrice()));
            volSet.addEntry(new BarEntry(volSet.getEntryCount(), hisData.getVol()));
            ma5Set.addEntry(new BarEntry(ma5Set.getEntryCount(), (float) hisData.getMa5()));
            ma10Set.addEntry(new BarEntry(ma10Set.getEntryCount(), (float) hisData.getMa10()));
            ma20Set.addEntry(new BarEntry(ma20Set.getEntryCount(), (float) hisData.getMa20()));
            ma30Set.addEntry(new BarEntry(ma30Set.getEntryCount(), (float) hisData.getMa30()));


            mChartPrice.getXAxis().setAxisMinimum(-0.5f);
            mChartPrice.getXAxis().setAxisMaximum(mData.size() - 0.5f);
            mChartVolume.getXAxis().setAxisMinimum(-0.5f);
            mChartVolume.getXAxis().setAxisMaximum(mData.size() - 0.5f);

            mChartPrice.notifyDataSetChanged();
            mChartPrice.invalidate();
            mChartVolume.notifyDataSetChanged();
            mChartVolume.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLineData(HisData hisData) {
        try {
            hisData = DataUtils.calculateHisData(hisData, mData);
            CombinedData combinedData = mChartPrice.getData();
            LineData priceData = combinedData.getLineData();
            ILineDataSet priceSet = priceData.getDataSetByIndex(0);
            ILineDataSet aveSet = priceData.getDataSetByIndex(1);
            IBarDataSet volSet = mChartVolume.getData().getBarData().getDataSetByIndex(0);
            if (mData.contains(hisData)) {
                int index = mData.indexOf(hisData);
                priceSet.removeEntry(index);
                aveSet.removeEntry(index);
                volSet.removeEntry(index);
                mData.remove(index);
            }
            mData.add(hisData);
            priceSet.addEntry(new Entry(priceSet.getEntryCount(), (float) hisData.getClose()));
            aveSet.addEntry(new Entry(aveSet.getEntryCount(), (float) hisData.getAvePrice()));
            volSet.addEntry(new BarEntry(volSet.getEntryCount(), hisData.getVol()));

            mChartPrice.getXAxis().setAxisMinimum(-0.5f);
            mChartPrice.getXAxis().setAxisMaximum(mData.size() - 0.5f);
            mChartVolume.getXAxis().setAxisMinimum(-0.5f);
            mChartVolume.getXAxis().setAxisMaximum(mData.size() - 0.5f);

            mChartPrice.notifyDataSetChanged();
            mChartPrice.invalidate();
            mChartVolume.notifyDataSetChanged();
            mChartVolume.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 对齐两个图表
     */
    private void setOffset() {
        float lineLeft = mChartPrice.getViewPortHandler().offsetLeft();
        float barLeft = mChartVolume.getViewPortHandler().offsetLeft();
        float lineRight = mChartPrice.getViewPortHandler().offsetRight();
        float barRight = mChartVolume.getViewPortHandler().offsetRight();
        float offsetLeft, offsetRight;
 /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (barLeft < lineLeft) {
            offsetLeft = Utils.convertPixelsToDp(lineLeft - barLeft);
            mChartVolume.setExtraLeftOffset(offsetLeft);
        } else {
            offsetLeft = Utils.convertPixelsToDp(barLeft - lineLeft);
            mChartPrice.setExtraLeftOffset(offsetLeft);
        }
  /*注：setExtra...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (barRight < lineRight) {
            offsetRight = Utils.convertPixelsToDp(lineRight);
            mChartVolume.setExtraRightOffset(offsetRight);
        } else {
            offsetRight = Utils.convertPixelsToDp(barRight);
            mChartPrice.setExtraRightOffset(offsetRight);
        }

    }


    /**
     * 设置分时图元素的数量
     */
    public void setLineCount(int max, int min) {
        MAX_COUNT_LINE = max;
        MIN_COUNT_LINE = min;
    }

    /**
     * 设置K线图元素的数量
     */
    public void setKCount(int max, int min) {
        MAX_COUNT_K = max;
        MIN_COUNT_K = min;
    }

    /**
     * 向图表中添加基准线
     */
    public void setLimitLine(double lastClose) {
        LimitLine limitLine = new LimitLine((float) lastClose);
        limitLine.enableDashedLine(5, 10, 0);
        limitLine.setLineColor(getResources().getColor(R.color.limit_color));
        axisLeftPrice.addLimitLine(limitLine);
    }

    public void setLimitLine() {
        setLimitLine(mLastClose);
    }

    public void setLastClose(double lastClose) {
        mLastClose = lastClose;
        mChartPrice.setOnChartValueSelectedListener(new InfoViewListener(mContext, mLastClose, mData, mKInfo, mChartVolume));
        mChartVolume.setOnChartValueSelectedListener(new InfoViewListener(mContext, mLastClose, mData, mKInfo, mChartPrice));
    }


    public void setDigits(int digits) {
        mDigits = digits;
    }

}
