package com.vinsonguo.kline;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = findViewById(R.id.tab);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        Fragment[] fragments = {TimeLineChartFragment.newInstance(1), FiveDayChartFragment.newInstance(),
                KLineChartFragment.newInstance(1), KLineChartFragment.newInstance(7),
                KLineChartFragment.newInstance(30)};
        String[] titles = {"分时图", "5Day", "日K", "周K", "月"};
        viewPager.setOffscreenPageLimit(fragments.length);
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullScreenChartActivity.launch(MainActivity.this, viewPager.getCurrentItem());
            }
        });
    }
}
