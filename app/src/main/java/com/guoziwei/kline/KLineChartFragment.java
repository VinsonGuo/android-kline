package com.guoziwei.kline;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guoziwei.klinelib.chart.KLineView;
import com.guoziwei.klinelib.model.HisData;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class KLineChartFragment extends Fragment {


    private KLineView mKLineView;
    private int mType;

    public KLineChartFragment() {
        // Required empty public constructor
    }

    public static KLineChartFragment newInstance(int type) {
        KLineChartFragment fragment = new KLineChartFragment();
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
        mKLineView = new KLineView(getContext());
        initData();
        return mKLineView;
    }

    protected void initData() {
        final List<HisData> hisData = Util.getHisData(getContext());
        mKLineView.setLastClose(56.81);
        if (mType == 0) {
            mKLineView.initChartKData(hisData);
        } else {
            mKLineView.initChartPriceData(hisData);
        }
        mKLineView.setLimitLine();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mKLineView.post(new Runnable() {
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
                        if (mType == 0) {
                            mKLineView.addKData(newData);
                        } else {
                            mKLineView.addLineData(newData);
                        }
                    }
                });
            }
        }, 5000, 5000);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mKLineView.post(new Runnable() {
                    @Override
                    public void run() {
                        mKLineView.refreshData((float) (56.8 + 0.1 * Math.random()));
                    }
                });
            }
        }, 1000, 1000);
    }

}
