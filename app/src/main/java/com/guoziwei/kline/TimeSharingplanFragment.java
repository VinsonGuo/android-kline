package com.guoziwei.kline;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guoziwei.kline.chart.TimeSharingplanChart;
import com.guoziwei.kline.model.HisData;
import com.guoziwei.kline.util.DataUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 分时图Fragment
 */
public class TimeSharingplanFragment extends Fragment {


    private TimeSharingplanChart mChart;

    public TimeSharingplanFragment() {
        // Required empty public constructor
    }

    public static TimeSharingplanFragment newInstance() {
        TimeSharingplanFragment fragment = new TimeSharingplanFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mChart = new TimeSharingplanChart(getActivity());
        initData();
        refreshData();
        return mChart;
    }

    private void refreshData() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mChart.post(new Runnable() {
                    @Override
                    public void run() {
                        mChart.refreshEntry((float) (56.8 + 0.1 * Math.random()));
                    }
                });
            }
        }, 1000, 1000);
    }

    protected void initData() {

        final List<HisData> list = DataUtils.parseHisData(getActivity(), mChart.getLastData());
        if (list == null || list.isEmpty()) {
            mChart.setNoDataText("加载失败");
            return;
        }
        mChart.addEntries(list);
    }



   /* @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PriceRefreshEvent event) {
        if (TextUtils.equals(event.getSymbol(), mSymbol)) {
            Quote quote = StaticStore.getQuote(mSymbol, mIsDemo);
            mChart.refreshEntry((float) quote.getLastPrice());
        }
    }*/

}
