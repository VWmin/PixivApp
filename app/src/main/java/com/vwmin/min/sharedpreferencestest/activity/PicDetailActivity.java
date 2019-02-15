package com.vwmin.min.sharedpreferencestest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.rey.material.widget.ImageView;
import com.vwmin.min.sharedpreferencestest.data.ViewHistory;
import com.vwmin.min.sharedpreferencestest.fragment.FragPerPic;
import com.vwmin.min.sharedpreferencestest.response.Illust;
import com.vwmin.min.sharedpreferencestest.response.IllustBean;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayout();
        setControl();
        setListener();

        getDatabase();

    }

    @Override
    void setLayout() {
        setContentView(R.layout.activity_picdetail);
    }

    @Override
    void setControl() {
        toolbar = findViewById(R.id.toolbar_picDetail);
        viewPager = findViewById(R.id.viewPager_picDetail);

        intent = getIntent();
        illustList = (List<Illust>) intent.getSerializableExtra("list");
        position = intent.getIntExtra("position", 0);

//        Log.d("yyyy", "I choose position "+position);

        for(Illust illust:illustList){
            frags.add(FragPerPic.newInstance(illust));
        }



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
                // 更新图片点击时间
                List<ViewHistory> matches = where("illust_id = ?",
                        String.valueOf(illustList.get(i).getIllust_id())).find(ViewHistory.class);
                if(matches.size()==0) {
                    ViewHistory viewHistory = new ViewHistory(illustList.get(i));
                    viewHistory.save();
                }else{
                    matches.get(0).setTime(System.currentTimeMillis());
                    matches.get(0).updateAll("illust_id = ?", String.valueOf(illustList.get(i).getIllust_id()));
                }
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
