package com.guoziwei.kline;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.guoziwei.klinelib.chart.KLineView;
import com.guoziwei.klinelib.chart.OnLoadMoreListener;
import com.guoziwei.klinelib.model.HisData;

import java.util.List;


public class KLineChartFragment extends Fragment {


    private KLineView mKLineView;
    private int mDay;

    public KLineChartFragment() {
        // Required empty public constructor
    }

    public static KLineChartFragment newInstance(int day) {
        KLineChartFragment fragment = new KLineChartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("day", day);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDay = getArguments().getInt("day");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kline_chart, container, false);
        mKLineView = v.findViewById(R.id.kline);
        RadioGroup rgIndex = v.findViewById(R.id.rg_index);
        mKLineView.setDateFormat("yyyy-MM-dd");
        rgIndex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.cb_vol) {
                    showVolume();
                } else if (checkedId == R.id.cb_macd) {
                    showMacd();
                } else if (checkedId == R.id.cb_kdj) {
                    showKdj();
                }
            }
        });
        initData();
        ((RadioButton) rgIndex.getChildAt(0)).setChecked(true);
        return v;
    }

    public void showVolume() {

        mKLineView.post(new Runnable() {
            @Override
            public void run() {
                mKLineView.showVolume();
            }
        });
    }

    public void showMacd() {
        mKLineView.post(new Runnable() {
            @Override
            public void run() {
                mKLineView.showMacd();
            }
        });
    }

    public void showKdj() {
        mKLineView.post(new Runnable() {
            @Override
            public void run() {
                mKLineView.showKdj();
            }
        });
    }

    protected void initData() {
        final List<HisData> hisData = Util.getK(getContext(), mDay);
        List<HisData> subHisData = hisData.subList(50, hisData.size());
        mKLineView.initData(subHisData);
        mKLineView.setLimitLine();
        mKLineView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Toast.makeText(getContext(), "触发加载更多", Toast.LENGTH_SHORT).show();
                mKLineView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "加载更多完成", Toast.LENGTH_SHORT).show();
                        mKLineView.addDatasFirst(hisData.subList(0, 50));
                        mKLineView.loadMoreComplete();
                        mKLineView.setOnLoadMoreListener(null);
                    }
                }, 3000);
            }
        });

       /* new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mKLineView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = (int) (Math.random() * hisData.size());
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
                        mKLineView.addData(newData);
                    }
                });
            }
        }, 1000, 1000);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mKLineView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = (int) (Math.random() * (hisData.size()));
                        HisData data = hisData.get(index);
                        mKLineView.refreshData((float) data.getClose());
                    }
                });
            }
        }, 500, 1000);*/
    }

}
