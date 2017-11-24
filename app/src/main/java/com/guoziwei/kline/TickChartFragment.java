package com.guoziwei.kline;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guoziwei.klinelib.chart.TickChart;
import com.guoziwei.klinelib.model.HisData;
import com.guoziwei.klinelib.util.DataUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 分时图Fragment
 */
public class TickChartFragment extends Fragment {


    private TickChart mChart;

    public TickChartFragment() {
        // Required empty public constructor
    }

    public static TickChartFragment newInstance() {
        TickChartFragment fragment = new TickChartFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mChart = new TickChart(getActivity());
        initData();
        return mChart;
    }

    protected void initData() {

        final List<HisData> list = DataUtils.calculateHisData(Util.getHisData(getActivity()), mChart.getLastData());
        if (list == null || list.isEmpty()) {
            mChart.setNoDataText("加载失败");
            return;
        }
        mChart.addEntries(list);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mChart.post(new Runnable() {
                    @Override
                    public void run() {
                        mChart.refreshData((float) (56.8 + 0.1 * Math.random()));
                    }
                });
            }
        }, 1000, 1000);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mChart.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = (int) (Math.random() * 100);
                        HisData data = list.get(index);
                        HisData lastData = list.get(list.size() - 1);
                        HisData newData = new HisData();
                        newData.setVol(data.getVol());
                        newData.setClose(data.getClose());
                        newData.setHigh(data.getHigh());
                        newData.setLow(data.getLow());
                        newData.setOpen(lastData.getClose());
                        newData.setDate(System.currentTimeMillis());
                        list.add(newData);
                        mChart.addEntry(newData);
                    }
                });
            }
        }, 5000, 5000);
    }


}
