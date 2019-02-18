package com.vwmin.min.sharedpreferencestest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.data.ViewHistory;
import com.vwmin.min.sharedpreferencestest.data.ViewHistoryOperator;
import com.vwmin.min.sharedpreferencestest.fragment.frag.FragPerPic;
import com.vwmin.min.sharedpreferencestest.response.Illust;

import java.util.ArrayList;
import java.util.List;

import static org.litepal.Operator.find;
import static org.litepal.Operator.getDatabase;
import static org.litepal.Operator.where;

@SuppressWarnings("deprecation")
public class PicDetailActivity extends BaseActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private Intent intent;
    private List<Illust> illustList;
    private int position;
    private List<FragPerPic> frags = new ArrayList<>();

    @Override
    public void finish() {
        Log.d("broadCast", "发送了更新历史记录的广播");
        sendBroadcast(new Intent("com.vwmin.min.sharedpreferencestest.REFRESH_VIEW_HISTORY"));
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayout();
        setControl();
        setListener();

        getDatabase();

    }

    @Override
    void setLayout() {
        setContentView(R.layout.activity_pic_detail);
    }

    @Override
    void setControl() {
        toolbar = findViewById(R.id.toolbar_picDetail);
        viewPager = findViewById(R.id.viewPager_picDetail);

        intent = getIntent();
        illustList = (List<Illust>) intent.getSerializableExtra("list");
        position = intent.getIntExtra("position", 0);

//        Log.d("yyyy", "I choose position "+position);

        for(Illust illust:illustList)
            frags.add(FragPerPic.newInstance(illust));


        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return frags.get(i);
            }

            @Override
            public int getCount() {
                return frags.size();
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                ViewHistoryOperator.refreshClickTime(illustList.get(i));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        viewPager.setCurrentItem(position);


    }

    @Override
    void setListener() {
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    public void mySetTitle(){
        toolbar.setTitle(illustList.get(viewPager.getCurrentItem()).getTitle());
    }


}
