package com.guoziwei.kline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class FullScreenChartActivity extends AppCompatActivity {

    public static void launch(Context context, int index) {
        Intent intent = new Intent(context, FullScreenChartActivity.class);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_chart);
        TabLayout tabLayout = findViewById(R.id.tab);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        KLineChartFragment dayKFragment = KLineChartFragment.newInstance(1);
        KLineChartFragment weekKFragment = KLineChartFragment.newInstance(7);
        Fragment[] fragments = {TimeLineChartFragment.newInstance(1), FiveDayChartFragment.newInstance(),
                dayKFragment, weekKFragment,
                KLineChartFragment.newInstance(30)};
        String[] titles = {"分时图", "5Day", "日K", "周K", "月"};
        viewPager.setOffscreenPageLimit(fragments.length);
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(getIntent().getIntExtra("index", 0));
    }
}
