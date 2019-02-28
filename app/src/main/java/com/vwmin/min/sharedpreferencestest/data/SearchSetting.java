package com.vwmin.min.sharedpreferencestest.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.vwmin.min.sharedpreferencestest.activity.ActivityCollection;

public class SearchSetting {
    private static SharedPreferences searchSetting;

    public static final String PARTIAL_MATCH_FOR_TAGS = "partial_match_for_tags";
    public static final String EXACT_MATCH_FOR_TAGS = "exact_match_for_tags";
    public static final String TITLE_AND_CAPTION = "title_and_caption";

    public static final String NO_SCREEN = "";
    public static final String USER_500 = "500users入り";
    public static final String USER_1000 = "1000users入り";
    public static final String USER_2000 = "2000users入り";
    public static final String USER_5000 = "5000users入り";
    public static final String USER_10000 = "10000users入り";
    public static final String USER_50000 = "50000users入り";

    private static final int TYPE_MANGA = 0, TYPE_NOVEL = 1, TYPE_USER = 2;



    private SearchSetting(){}

    private static class Holder{private static final SearchSetting INSTANCE = new SearchSetting();}

    public static SearchSetting getInstance(Context context){
        searchSetting = context.getSharedPreferences("SearchSetting", 0);
        return Holder.INSTANCE;
    }



    /* bean */

    public String getSearch_target() {
        return searchSetting.getString("SEARCH_TARGET", "partial_match_for_tags");

    }

    public void setSearch_target(String search_target) {
        searchSetting.edit().putString("SEARCH_TARGET", search_target).apply();
    }

    public String getScreen() {
        return searchSetting.getString("SCREEN", "");

    }

    public void setScreen(String screen) {
        searchSetting.edit().putString("SCREEN", screen).apply();
    }
}