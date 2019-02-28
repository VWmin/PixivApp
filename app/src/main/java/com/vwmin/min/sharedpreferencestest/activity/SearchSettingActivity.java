package com.vwmin.min.sharedpreferencestest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vwmin.min.sharedpreferencestest.R;
import com.vwmin.min.sharedpreferencestest.data.AppSetting;
import com.vwmin.min.sharedpreferencestest.data.SearchSetting;

public class SearchSettingActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private Toolbar toolbar;

    private RadioGroup target;
    private RadioGroup screen;

    private RadioButton partial_match_for_tags;
    private RadioButton exact_match_for_tags;
    private RadioButton title_and_caption;

    private RadioButton no_screen;
    private RadioButton user_500;
    private RadioButton user_1000;
    private RadioButton user_2000;
    private RadioButton user_5000;
    private RadioButton user_10000;
    private RadioButton user_50000;

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

        partial_match_for_tags = findViewById(R.id.partial_match_for_tags);
        exact_match_for_tags = findViewById(R.id.exact_match_for_tags);
        title_and_caption = findViewById(R.id.title_and_caption);

        no_screen = findViewById(R.id.no_screen);
        user_500 = findViewById(R.id._500user);
        user_1000 = findViewById(R.id._1000user);
        user_2000 = findViewById(R.id._2000user);
        user_5000 = findViewById(R.id._5000user);
        user_10000 = findViewById(R.id._10000user);
        user_50000 = findViewById(R.id._50000user);

    }

    @Override
    void setListener() {
        toolbar.setNavigationOnClickListener(v -> finish());
        target.setOnCheckedChangeListener(this);
        screen.setOnCheckedChangeListener(this);
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
                SearchSetting.getInstance(this).setSearch_target(SearchSetting.PARTIAL_MATCH_FOR_TAGS);
                break;

            case R.id.exact_match_for_tags:
                SearchSetting.getInstance(this).setSearch_target(SearchSetting.EXACT_MATCH_FOR_TAGS);
                break;

            case R.id.title_and_caption:
                SearchSetting.getInstance(this).setSearch_target(SearchSetting.TITLE_AND_CAPTION);
                break;

            case R.id._500user:
                SearchSetting.getInstance(this).setScreen(SearchSetting.USER_500);
                break;

            case R.id._1000user:
                SearchSetting.getInstance(this).setScreen(SearchSetting.USER_1000);
                break;

            case R.id._2000user:
                SearchSetting.getInstance(this).setScreen(SearchSetting.USER_2000);
                break;

            case R.id._5000user:
                SearchSetting.getInstance(this).setScreen(SearchSetting.USER_5000);
                break;

            case R.id._10000user:
                SearchSetting.getInstance(this).setScreen(SearchSetting.USER_10000);
                break;

            case R.id._50000user:
                SearchSetting.getInstance(this).setScreen(SearchSetting.USER_50000);
                break;
        }
        setResult(SearchActivity.RESULTCODE_OK);
    }
}
