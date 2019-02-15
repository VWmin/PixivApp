package com.vwmin.min.sharedpreferencestest.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.fragment.frag.FragRcmdFU;

public class RecommendFragment  extends BaseFragment {
    private Fragment[] fragments;
    private ViewPager viewPager;
    private TabLayout tab;
    private static final String TITLES[] = {"为你推荐"};

    @Override
    public int setLayoutId() {
        return R.layout.fragment_viewpager;
    }

    @Override
    public void initView(View view) {
        fragments = new Fragment[]{new FragRcmdFU()};

        // 通过view Pager翻页
        viewPager = view.findViewById(R.id.viewPager_mine);
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

        tab = view.findViewById(R.id.tabPage_mine);
        tab.setTabMode(TabLayout.MODE_FIXED);
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
