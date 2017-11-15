package com.guoziwei.kline;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by guoziwei on 2017/11/15.
 */
public class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public SimpleFragmentPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        mFragments = Arrays.asList(fragments);
    }

    public SimpleFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    public SimpleFragmentPagerAdapter(FragmentManager fm, Fragment[] fragments, String[] titles) {
        this(fm, fragments);
        mTitles = Arrays.asList(titles);
    }

    public SimpleFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        this(fm, fragments);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null && mTitles.size() > 0) {
            return mTitles.get(position);
        }
        return super.getPageTitle(position);
    }

}
