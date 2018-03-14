package com.guoziwei.kline;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class FullScreenChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_chart);
        TabLayout tabLayout = findViewById(R.id.tab);
        ViewPager viewPager = findViewById(R.id.view_pager);
        Fragment[] fragments = {TimeLineChartFragment.newInstance(1), KLineChartFragment.newInstance(0)};
        String[] titles = {"分时图专业版", "k线图专业版"};
        viewPager.setOffscreenPageLimit(fragments.length);
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
    }
}
