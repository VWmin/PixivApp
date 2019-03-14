package com.vwmin.min.sharedpreferencestest.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.RadioGroup;

import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.data.SearchSetting;

public class SearchSettingActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private Toolbar toolbar;

    private RadioGroup target, screen;

    private String tmpTarget, tmpScreen; //临时的选择数据，确认后保存

    private Button cancel, confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));

        setLayout();
        setControl();
        setListener();

        initChoose();

    }

    @Override
    void setLayout() {
        setContentView(R.layout.activity_search_setting);
    }

    @Override
    void setControl() {
        toolbar = findViewById(R.id.search_toolbar);

        target = findViewById(R.id.search_target);
        screen = findViewById(R.id.screen);

        cancel = findViewById(R.id.search_cancel);
        confirm = findViewById(R.id.search_confirm);
    }

    @Override
    void setListener() {
        toolbar.setNavigationOnClickListener(v -> finish());
        target.setOnCheckedChangeListener(this);
        screen.setOnCheckedChangeListener(this);
        cancel.setOnClickListener(v->finish());
        confirm.setOnClickListener(v->{
            save();
            finish();
        });
    }


    private void initChoose(){
        String selectedSearchTarget, selectedScreen;
        selectedSearchTarget = SearchSetting.getInstance(this).getSearch_target();
        selectedScreen = SearchSetting.getInstance(this).getScreen();

        switch (selectedSearchTarget){
            case SearchSetting.PARTIAL_MATCH_FOR_TAGS:
                target.check(R.id.partial_match_for_tags);
                break;

            case SearchSetting.EXACT_MATCH_FOR_TAGS:
                target.check(R.id.exact_match_for_tags);
                break;

            case SearchSetting.TITLE_AND_CAPTION:
                target.check(R.id.title_and_caption);
                break;
        }

        switch (selectedScreen){
            case SearchSetting.NO_SCREEN:
                screen.check(R.id.no_screen);
                break;

            case SearchSetting.USER_500:
                screen.check(R.id._500user);
                break;

            case SearchSetting.USER_1000:
                screen.check(R.id._1000user);
                break;

            case SearchSetting.USER_2000:
                screen.check(R.id._2000user);
                break;

            case SearchSetting.USER_5000:
                screen.check(R.id._5000user);
                break;

            case SearchSetting.USER_10000:
                screen.check(R.id._10000user);
                break;

            case SearchSetting.USER_50000:
                screen.check(R.id._50000user);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.partial_match_for_tags:
                tmpTarget = SearchSetting.PARTIAL_MATCH_FOR_TAGS;
//                SearchSetting.getInstance(this).setSearch_target(SearchSetting.PARTIAL_MATCH_FOR_TAGS);
                break;

            case R.id.exact_match_for_tags:
                tmpTarget = SearchSetting.EXACT_MATCH_FOR_TAGS;
//                SearchSetting.getInstance(this).setSearch_target(SearchSetting.EXACT_MATCH_FOR_TAGS);
                break;

            case R.id.title_and_caption:
                tmpTarget = SearchSetting.TITLE_AND_CAPTION;
//                SearchSetting.getInstance(this).setSearch_target(SearchSetting.TITLE_AND_CAPTION);
                break;

            case R.id.no_screen:
                tmpScreen = SearchSetting.NO_SCREEN;
//                SearchSetting.getInstance(this).setScreen(SearchSetting.NO_SCREEN);
                break;

            case R.id._500user:
                tmpScreen = SearchSetting.USER_500;
//                SearchSetting.getInstance(this).setScreen(SearchSetting.USER_500);
                break;

            case R.id._1000user:
                tmpScreen = SearchSetting.USER_1000;
//                SearchSetting.getInstance(this).setScreen(SearchSetting.USER_1000);
                break;

            case R.id._2000user:
                tmpScreen = SearchSetting.USER_2000;
//                SearchSetting.getInstance(this).setScreen(SearchSetting.USER_2000);
                break;

            case R.id._5000user:
                tmpScreen = SearchSetting.USER_5000;
//                SearchSetting.getInstance(this).setScreen(SearchSetting.USER_5000);
                break;

            case R.id._10000user:
                tmpScreen = SearchSetting.USER_10000;
//                SearchSetting.getInstance(this).setScreen(SearchSetting.USER_10000);
                break;

            case R.id._50000user:
                tmpScreen = SearchSetting.USER_50000;
//                SearchSetting.getInstance(this).setScreen(SearchSetting.USER_50000);
                break;
        }
    }

    private void save(){
        SearchSetting.getInstance(this).setSearch_target(tmpTarget);
        SearchSetting.getInstance(this).setScreen(tmpScreen);
        setResult(SearchActivity.RESULTCODE_OK);
    }

}
