package com.vinsonguo.kline;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinsonguo.klinelib.chart.TimeLineView;
import com.vinsonguo.klinelib.model.HisData;

import java.util.List;


public class FiveDayChartFragment extends Fragment {


    private TimeLineView mTimeLineView;

    public FiveDayChartFragment() {
        // Required empty public constructor
    }

    public static FiveDayChartFragment newInstance() {
        FiveDayChartFragment fragment = new FiveDayChartFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTimeLineView = new TimeLineView(getContext());
        mTimeLineView.setDateFormat("yyyy-MM-dd");
        int count =241 * 5;
        mTimeLineView.setCount(count, count, count);
        initData();
        return mTimeLineView;
    }

    protected void initData() {
        final List<List<HisData>> hisData = Util.get5Day(getContext());
        mTimeLineView.setLastClose(hisData.get(0).get(0).getClose());
        mTimeLineView.initDatas(hisData.get(0), hisData.get(1), hisData.get(2), hisData.get(3), hisData.get(4));

       /* new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mTimeLineView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = (int) (Math.random() * 100);
                        HisData data = hisData.get(index);
                        HisData lastData = hisData.get(hisData.size() - 1);
                        HisData newData = new HisData();
                        newData.setVol(data.getVol());
                        newData.setClose(data.getClose());
                        newData.setHigh(Math.max(data.getHigh(), lastData.getClose()));
                        newData.setLow(Math.min(data.getLow(), lastData.getClose()));
                        newData.setOpen(lastData.getClose());
                        newData.setDate(System.currentTimeMillis());
                        hisData.add(newData);
                        mTimeLineView.addData(newData);
                    }
                });
            }
        }, 5000, 5000);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mTimeLineView.post(new Runnable() {
                    @Override
                    public void run() {
                        mTimeLineView.refreshData((float) (56.8 + 0.1 * Math.random()));
                    }
                });
            }
        }, 1000, 1000);*/
    }

}
