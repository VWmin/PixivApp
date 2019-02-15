package com.vwmin.min.sharedpreferencestest.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.fragment.frag.FragRank;

public class RankFragment extends BaseFragment {
    private Fragment[] fragments;
    private ViewPager viewPager;
    private TabLayout tab;
    private static final String TITLES[] = {"每日", "每周", "每月", "男性向", "女性向", "原创", "新人", "冲冲冲"};



    @Override
    public int setLayoutId() {
        return R.layout.fragment_viewpager;
    }

    @Override
    public void initView(View view) {
        tab = view.findViewById(R.id.tabPage_mine);
        viewPager = view.findViewById(R.id.viewPager_mine);

        fragments = new Fragment[]{
                FragRank.getInstance("day"),
                FragRank.getInstance("week"),
                FragRank.getInstance("month"),
                FragRank.getInstance("day_male"),
                FragRank.getInstance("day_female"),
                FragRank.getInstance("week_original"),
                FragRank.getInstance("week_rookie"),
                FragRank.getInstance("day_r18"),
        };

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
    @Override
    public void onResume() {
        super.onResume();
//        ((MainActivity) Objects.requireNonNull(getActivity())).setTableLayout(viewPager);
    }
}
