package com.guoziwei.kline;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart;
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
import com.guoziwei.kline.chart.AppCombinedChart;
import com.guoziwei.kline.chart.ChartInfoView;
import com.guoziwei.kline.chart.CoupleChartGestureListener;
import com.guoziwei.kline.chart.LineChartXMarkerView;
import com.guoziwei.kline.model.HisData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/10/26.
 */

public class BaseFullScreenChartFragment extends Fragment {

    public static final int MAX_COUNT_LINE = 200;
    public static final int MIN_COUNT_LINE = 100;
    public static final int MAX_COUNT_K = 100;
    public static final int MIN_COUNT_K = 60;

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
    private int textColor;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_full_screen_line_chart, container, false);
        mChartPrice = (AppCombinedChart) v.findViewById(R.id.line_chart);
        mChartVolume = (AppCombinedChart) v.findViewById(R.id.bar_chart);
        mLineInfo = (ChartInfoView) v.findViewById(R.id.line_info);
        mKInfo = (ChartInfoView) v.findViewById(R.id.k_info);
        textColor = ContextCompat.getColor(mContext, R.color.main_text_color);
        mChartVolume.setNoDataText(getString(R.string.loading));
        mChartPrice.setNoDataText(getString(R.string.loading));
        initChartPrice();
        initChartVolume();
        initChartListener();

        return v;
    }

    protected void initChartPrice() {
        mChartPrice.setBackgroundColor(getResources().getColor(R.color.chart_background));
        mChartVolume.setBackgroundColor(getResources().getColor(R.color.chart_background));
        mChartPrice.setScaleEnabled(true);//启用图表缩放事件
        mChartPrice.setDrawBorders(false);//是否绘制边线
        mChartPrice.setBorderWidth(1);//边线宽度，单位dp
        mChartPrice.setDragEnabled(true);//启用图表拖拽事件
        mChartPrice.setScaleYEnabled(false);//启用Y轴上的缩放
//        mChartPrice.setBorderColor(getResources().getColor(R.color.border_color));//边线颜色
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
        xAxisPrice.enableGridDashedLine(10f, 10f, 0f);//绘制成虚线，只有在关闭硬件加速的情况下才能使用

        //左边y
        axisLeftPrice = mChartPrice.getAxisLeft();
        axisLeftPrice.setLabelCount(5, true); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisLeftPrice.setDrawLabels(true);//是否显示Y坐标轴上的刻度，默认是true
        axisLeftPrice.setDrawGridLines(false);//是否显示Y坐标轴上的刻度竖线，默认是true
        /*轴不显示 避免和border冲突*/
        axisLeftPrice.setDrawAxisLine(false);//是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        axisLeftPrice.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART); //参数是INSIDE_CHART(Y轴坐标在内部) 或 OUTSIDE_CHART(在外部（默认是这个）)
        axisLeftPrice.setTextColor(textColor);


        //右边y
        axisRightPrice = mChartPrice.getAxisRight();
        axisRightPrice.setLabelCount(5, true);//参考上面
        axisRightPrice.setDrawLabels(false);//参考上面
        axisRightPrice.setDrawGridLines(false);//参考上面
        axisRightPrice.setDrawAxisLine(false);//参考上面
        axisRightPrice.setTextColor(textColor);
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
//        mChartVolume.setAutoScaleMinMaxEnabled(true);
        Legend lineChartLegend = mChartVolume.getLegend();
        lineChartLegend.setEnabled(false);//是否绘制 Legend 图例

        //x轴
        xAxisVolume = mChartVolume.getXAxis();
        xAxisVolume.setDrawLabels(true);
        xAxisVolume.setDrawAxisLine(false);
        xAxisVolume.setDrawGridLines(false);
        xAxisVolume.setTextColor(textColor);
        xAxisVolume.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisVolume.setLabelCount(5, true);
        xAxisVolume.setAvoidFirstLastClipping(true);


        //左边y
        axisLeftVolume = mChartVolume.getAxisLeft();
        axisLeftVolume.setDrawLabels(true);//参考上面
        axisLeftVolume.setDrawGridLines(false);//参考上面
        /*轴不显示 避免和border冲突*/
        axisLeftVolume.setLabelCount(3, true);
        axisLeftVolume.setDrawAxisLine(false);//参考上面
        axisLeftVolume.setTextColor(textColor);
        axisLeftVolume.setAxisMinimum(0);
        axisLeftVolume.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeftVolume.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value > 10000) {
                    return (int) (value / 10000) + "万";
                } else if (value > 1000) {
                    return (int) (value / 1000) + "千";
                }
                return (int) value + "";
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
    }

    protected void initChartKData(AppCombinedChart combinedChartX) {

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


        /*注老版本LineData参数可以为空，最新版本会报错，修改进入ChartData加入if判断*/
        LineData lineData = new LineData(setLine(1, lineJJEntries), setLine(2, paddingEntries), setLine(5, ma5Entries)
                , setLine(10, ma10Entries), setLine(20, ma20Entries), setLine(30, ma30Entries));
        CandleData candleData = new CandleData(setKLine(0, lineCJEntries));
        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        combinedData.setData(candleData);
        combinedChartX.setData(combinedData);

        combinedChartX.setVisibleXRange(MAX_COUNT_K, MIN_COUNT_K);
        combinedChartX.notifyDataSetChanged();
        combinedChartX.invalidate();
        combinedChartX.moveViewToX(combinedData.getEntryCount());
    }

    protected void initChartPriceData(AppCombinedChart combinedChartX) {

        if (mData == null || mData.isEmpty()) {
            return;
        }

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
        sets.add(setLine(0, lineCJEntries));
        sets.add(setLine(1, lineJJEntries));
        sets.add(setLine(2, paddingEntries));
        /*注老版本LineData参数可以为空，最新版本会报错，修改进入ChartData加入if判断*/
        LineData lineData = new LineData(sets);

        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        combinedChartX.setData(combinedData);

        combinedChartX.setVisibleXRange(MAX_COUNT_LINE, MIN_COUNT_LINE);

        combinedChartX.notifyDataSetChanged();
        combinedChartX.invalidate();
        combinedChartX.moveViewToX(combinedData.getEntryCount());
    }

    /**
     *
     * @param type 0 分时图的线 1 均线 5 ma5 ....
     */
    @android.support.annotation.NonNull
    private LineDataSet setLine(int type, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + type);
        lineDataSetMa.setDrawValues(false);
        if (type == 0) {
            lineDataSetMa.setColor(getResources().getColor(R.color.third_text_color));
            lineDataSetMa.setCircleColor(ContextCompat.getColor(mContext, R.color.third_text_color));
        } else if (type == 1) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ave_color));
            lineDataSetMa.setCircleColor(getResources().getColor(R.color.transparent));
        } else if (type == 5) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma5));
            lineDataSetMa.setCircleColor(getResources().getColor(R.color.transparent));
        } else if (type == 10) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma10));
            lineDataSetMa.setCircleColor(getResources().getColor(R.color.transparent));
        } else if (type == 20) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma20));
            lineDataSetMa.setCircleColor(getResources().getColor(R.color.transparent));
        } else if (type == 30) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma30));
            lineDataSetMa.setCircleColor(getResources().getColor(R.color.transparent));
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
    private CandleDataSet setKLine(int type, ArrayList<CandleEntry> lineEntries) {
        CandleDataSet set1 = new CandleDataSet(lineEntries, "ma" + type);
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
        if (type != 0) {
            set1.setVisible(false);
        }
        return set1;
    }

    protected void initChartVolumeData(CombinedChart combinedChartX) {

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<Entry> paddingEntries = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            HisData t = mData.get(i);
            barEntries.add(new BarEntry(i, t.getVol(), t));
        }
        int maxCount = mChartPrice.getData().getCandleData() == null ? MAX_COUNT_LINE : MAX_COUNT_K;
        if (!mData.isEmpty() && mData.size() < maxCount) {
            for (int i = mData.size(); i < maxCount; i++) {
                paddingEntries.add(new BarEntry(i, 0));
            }
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "成交量");
        barDataSet.setDrawValues(false);//是否在线上绘制数值
        List<Integer> list = new ArrayList<>();
        list.add(getResources().getColor(R.color.increasing_color));
        list.add(getResources().getColor(R.color.decreasing_color));
        barDataSet.setColors(list);//可以给树状图设置多个颜色，判断条件在BarChartRendererdraw的DataSet方法做了修改
        BarData barData = new BarData(barDataSet);
        LineData lineData = new LineData(setLine(2, paddingEntries));
        CombinedData combinedData = new CombinedData();
        combinedData.setData(barData);
        combinedData.setData(lineData);
        combinedChartX.setData(combinedData);

        if (mChartPrice.getData().getCandleData() != null) {
            combinedChartX.setVisibleXRange(MAX_COUNT_K, MIN_COUNT_K);
        } else {
            combinedChartX.setVisibleXRange(MAX_COUNT_LINE, MIN_COUNT_LINE);
        }
        setOffset();
        combinedChartX.notifyDataSetChanged();
        combinedChartX.invalidate();
        combinedChartX.moveViewToX(combinedData.getEntryCount());
    }

    protected void refreshData(float price) {
        try {
            CombinedData data = mChartPrice.getData();
            if (data == null) return;
            LineData lineData = data.getLineData();
            if (lineData == null) return;
            ILineDataSet set = lineData.getDataSetByIndex(0);
            if (set.removeLast()) {
                set.addEntry(new Entry(set.getEntryCount(), price));
            }
            mChartPrice.notifyDataSetChanged();
            mChartPrice.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void addLineData(List<HisData> hisDatas) {
        try {
            LineData priceData = mChartPrice.getData().getLineData();
            ILineDataSet priceSet = priceData.getDataSetByIndex(0);
            ILineDataSet aveSet = priceData.getDataSetByIndex(1);
            IBarDataSet volSet = mChartVolume.getData().getBarData().getDataSetByIndex(0);
            for (int i = 0; i < hisDatas.size(); i++) {
                HisData hisData = hisDatas.get(i);
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
                mChartPrice.notifyDataSetChanged();
                mChartPrice.invalidate();
                mChartVolume.notifyDataSetChanged();
                mChartVolume.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void addKData(List<HisData> hisDatas) {
        try {
            LineData priceData = mChartPrice.getData().getLineData();
            ILineDataSet aveSet = priceData.getDataSetByIndex(0);
            CandleData kData = mChartPrice.getData().getCandleData();
            ICandleDataSet kSet = kData.getDataSetByIndex(0);
            IBarDataSet volSet = mChartVolume.getData().getBarData().getDataSetByIndex(0);
            for (int i = 0; i < hisDatas.size(); i++) {
                HisData hisData = hisDatas.get(i);
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
                mChartPrice.notifyDataSetChanged();
                mChartPrice.invalidate();
                mChartVolume.notifyDataSetChanged();
                mChartVolume.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setOffset() {
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
     * 向图表中添加基准线
     */
    protected void setLimitLine(double lastClose) {
        LimitLine limitLine = new LimitLine((float) lastClose);
        limitLine.enableDashedLine(5, 10, 0);
        axisLeftPrice.addLimitLine(limitLine);
    }
}
