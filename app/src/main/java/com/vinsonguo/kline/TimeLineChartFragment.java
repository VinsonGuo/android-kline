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
import java.util.Timer;
import java.util.TimerTask;


public class TimeLineChartFragment extends Fragment {


    private TimeLineView mTimeLineView;
    private int mType;

    public TimeLineChartFragment() {
        // Required empty public constructor
    }

    public static TimeLineChartFragment newInstance(int type) {
        TimeLineChartFragment fragment = new TimeLineChartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTimeLineView = new TimeLineView(getContext());
        mTimeLineView.setDateFormat("HH:mm");
        int count = 241;
        mTimeLineView.setCount(count, count, count);
        initData();
        return mTimeLineView;
    }

    protected void initData() {
        final List<HisData> hisData = Util.get1Day(getContext());
        mTimeLineView.setLastClose(hisData.get(0).getClose());
        mTimeLineView.initData(hisData);

        /*new Timer().schedule(new TimerTask() {
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
        }, 1000, 500);*/

       /* new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mTimeLineView.post(new Runnable() {
                    @Override
                    public void run() {
                        mTimeLineView.refreshData((float) (hisData.get(0).getClose() + 10 * Math.random()));
                    }
                });
            }
        }, 1000, 1000);*/
    }

}
