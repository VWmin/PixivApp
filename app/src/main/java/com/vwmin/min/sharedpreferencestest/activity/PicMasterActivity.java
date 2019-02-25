package com.vwmin.min.sharedpreferencestest.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.fragment.frag.FragPicMaster;

import java.util.ArrayList;
import java.util.List;

public class PicMasterActivity extends BaseActivity {

    private TextView textView;
    private ViewPager viewPager;
    private Fragment[] fragments = null;
    private List<String> urls = new ArrayList<>();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE );// 状态栏和导航栏的一些参数设置 这里分别是将布局扩展到状态栏后面和稳定布局

        setLayout();
        setControl();
        setListener();
    }

    @Override
    void setLayout() {
        setContentView(R.layout.activity_pic_master);
    }

    @Override
    void setControl() {
        textView = findViewById(R.id.textView_picMaster);
        viewPager = findViewById(R.id.viewPager_picMaster);
        toolbar = findViewById(R.id.toolbar_picMaster);

        urls = getIntent().getStringArrayListExtra("urls");
        fragments = new Fragment[urls.size()];
        for(int i=0; i<urls.size(); i++)
            fragments[i] = FragPicMaster.getInstance(urls.get(i));

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments[i];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        });
        textView.setText(String.format("%d/%d", 1, fragments.length));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                textView.setText(String.format("%d/%d",  viewPager.getCurrentItem()+1,fragments.length));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    void setListener() {
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}
