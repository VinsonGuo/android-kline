package com.guoziwei.kline;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.guoziwei.klinelib.chart.KLineView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = findViewById(R.id.tab);
        ViewPager viewPager = findViewById(R.id.view_pager);
        Fragment[] fragments = {/*TickChartFragment.newInstance(), CandleStickChartFragment.newInstance(),*/ KLineChartFragment.newInstance(1), KLineChartFragment.newInstance(0)};
        String[] titles = {/*"闪电图", "k线图简易版",*/ "分时图专业版", "k线图专业版"};
        viewPager.setOffscreenPageLimit(fragments.length);
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
    }
}
