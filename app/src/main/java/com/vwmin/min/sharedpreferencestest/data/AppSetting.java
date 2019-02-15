package com.vwmin.min.sharedpreferencestest.data;

public class AppSetting {
    private static String savePath = "/myPixiv/";

    public static String getSavePath() {
        return savePath;
    }

    public static void setSavePath(String savePath) {
        AppSetting.savePath = savePath;
    }

}
