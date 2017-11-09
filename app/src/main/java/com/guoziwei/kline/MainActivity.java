package com.guoziwei.kline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container1, FullScreenLineChartFragment.newInstance())
                .replace(R.id.fl_container2, FullScreenKLineChartFragment.newInstance())
                .replace(R.id.fl_container3, TimeSharingplanFragment.newInstance())
                .replace(R.id.fl_container4, CandleStickChartFragment.newInstance())
                .commitAllowingStateLoss();
    }
}
