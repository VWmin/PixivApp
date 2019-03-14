package com.vwmin.min.sharedpreferencestest.data;


import android.content.Context;
import android.content.SharedPreferences;

import com.vwmin.min.sharedpreferencestest.response.LoginResponse;

// 用SharedPreference储存的信息
public class UserInfo {
    private static SharedPreferences userPref;

    private UserInfo(){}

    private static class SingletonHolder{
        private static final UserInfo INSTANCE = new UserInfo();
    }

    public static UserInfo getInstance(Context context){
        userPref = context.getSharedPreferences("userInfo", 0);
        return SingletonHolder.INSTANCE;
    }

    public void saveUserInfo(LoginResponse loginResponse, String pwd, boolean isRemember){
        SharedPreferences.Editor userPrefEditor = userPref.edit();

        /* 用户信息 */
        userPrefEditor.putBoolean("is_remember", isRemember);

        userPrefEditor.putString("id",
                loginResponse.getResponse().getUser().getId());
        userPrefEditor.putString("name",
                loginResponse.getResponse().getUser().getName());
        userPrefEditor.putString("account",
                loginResponse.getResponse().getUser().getAccount());
        userPrefEditor.putBoolean("is_premium",
                loginResponse.getResponse().getUser().isIs_premium());
        userPrefEditor.putString("password", pwd);

        userPrefEditor.putString("email",
                loginResponse.getResponse().getUser().getMail_address());
        userPrefEditor.putString("profile_url",
                loginResponse.getResponse().getUser().getProfile_image_urls().getPx_170x170());

        /* Token */
        userPrefEditor.putLong("last_token_time",
                System.currentTimeMillis());
        userPrefEditor.putString("Authorization",
                String.format("Bearer %s", loginResponse.getResponse().getAccess_token()));
        userPrefEditor.putString("device_token",
                loginResponse.getResponse().getDevice_token());
        userPrefEditor.putString("refresh_token",
                loginResponse.getResponse().getRefresh_token());


        userPrefEditor.apply();

    }

    public void deleteUserInfo(){
        SharedPreferences.Editor userPrefEditor = userPref.edit();
        userPrefEditor.clear();
        userPrefEditor.apply();
    }


    public String getID(){return userPref.getString("id", null);}

    public String getAuthorization(){return userPref.getString("Authorization", "");}

    public Long getLastTokenTime(){return userPref.getLong("last_token_time", 0);}

    public boolean isRemember(){return userPref.getBoolean("is_remember", false);}

    public String getAccount(){return userPref.getString("account", "");}

    public String getProfileUrl(){return userPref.getString("profile_url", null);}

    public String getName(){return userPref.getString("name", "404");}

    public String getEmail(){return userPref.getString("email", "404");}

    public String getPassword(){return userPref.getString("password", "");}

    public String getRefreshToken(){return userPref.getString("refresh_token", "");}

    public String getDeviceToken(){return userPref.getString("device_token", "");}





}
