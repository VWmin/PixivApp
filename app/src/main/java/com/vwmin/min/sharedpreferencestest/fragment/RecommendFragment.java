package com.vwmin.min.sharedpreferencestest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.fragment.recommned.FragHotTags;
import com.vwmin.min.sharedpreferencestest.fragment.recommned.FragRcmdFU;

public class RecommendFragment  extends BaseFragment {
    private Fragment[] fragments;
    private ViewPager viewPager;
    private TabLayout tab;
    private final String TITLES[] = {"为你推荐", "热门标签"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);



        return view;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initView(View view) {
        fragments = new Fragment[]{new FragRcmdFU(), new FragHotTags()};

        // 通过view Pager翻页
        viewPager = view.findViewById(R.id.viewPager_recommend);
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

        tab = view.findViewById(R.id.tabPage_recommend);
        tab.setupWithViewPager(viewPager);

    }

    @Override
    public void initData() {

    }

    public Fragment[] getFragments() {
        return fragments;
    }

    public void setFragments(Fragment[] fragments) {
        this.fragments = fragments;
    }
}
