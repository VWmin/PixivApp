package com.vwmin.min.sharedpreferencestest.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.fragment.rank.FragRank_daily;
import com.vwmin.min.sharedpreferencestest.fragment.rank.FragRank_monthly;
import com.vwmin.min.sharedpreferencestest.fragment.rank.FragRank_weekly;

public class RankFragment  extends BaseFragment {
    private Fragment[] fragments;
    private ViewPager viewPager;
    private TabLayout tab;
    private static final String TITLES[] = {"每日", "每周", "每月"};



    @Override
    public int setLayoutId() {
        return R.layout.fragment_rank;
    }

    @Override
    public void initView(View view) {
        fragments = new Fragment[]{new FragRank_daily(), new FragRank_weekly(), new FragRank_monthly()};
        tab = view.findViewById(R.id.tabPage_rank);

        viewPager = view.findViewById(R.id.viewPager_rank);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments[i];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return TITLES[position];
            }
        });


        tab.setupWithViewPager(viewPager);

    }

    @Override
    public void initData() {

    }
}
