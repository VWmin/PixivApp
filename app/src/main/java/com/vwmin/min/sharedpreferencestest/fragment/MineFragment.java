package com.vwmin.min.sharedpreferencestest.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rey.material.widget.ProgressView;
import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.fragment.mine.FragShowHistory;

public class MineFragment extends BaseFragment {
    private Fragment[] fragments;
    private ViewPager viewPager;
    private TabLayout tab;
    private static final String TITLES[] = {"浏览记录"};


    @Override
    public int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View view) {
        viewPager = view.findViewById(R.id.viewPager_mine);
        tab = view.findViewById(R.id.tabPage_mine);
        fragments = new Fragment[]{new FragShowHistory()};

        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments[i];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

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
