package com.vwmin.min.sharedpreferencestest.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vwmin.min.sharedpreferencestest.data.UserInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

// 管理活动的类
public class ActivityCollection {

    private static List<Activity> activities = new ArrayList<>();

    static void addActivity(Activity activity){
        activities.add(activity);
    }

    static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    // 在程序的任何地方直接调用这个函数即可直接枪毙程序
    public static void finishAll(){
        Iterator<Activity> iterator = activities.iterator();
        // 这里用foreach会报错 不知道为啥 别用就是
        while(iterator.hasNext()){
            Activity activity = iterator.next();
            activity.finish();
        }
        activities.clear();

    }

    public static void forceOffline(){
        UserInfo.getInstance(activities.get(0)).deleteUserInfo();
        Intent intent = new Intent("com.vwmin.min.sharedpreferencestest.FORCE_OFFLINE");
        activities.get(0).sendBroadcast(intent);
    }

    public static Context getTopContext(){return activities.get(0);}
}

