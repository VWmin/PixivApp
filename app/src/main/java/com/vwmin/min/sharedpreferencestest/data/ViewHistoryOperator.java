package com.vwmin.min.sharedpreferencestest.data;

import android.util.Log;

import com.vwmin.min.sharedpreferencestest.response.Illust;

import org.litepal.Operator;

import java.util.List;
import static org.litepal.Operator.where;

public class ViewHistoryOperator {

    public static void refreshClickTime(Illust illust){
        List<ViewHistory> matches = where("illust_id = ?",
                String.valueOf(illust.getIllust_id())).find(ViewHistory.class);
        if(matches.size()==0) {
            ViewHistory viewHistory = new ViewHistory(illust);
            viewHistory.save();
            Log.d("ViewHistoryOperator", "refreshTime: notFound");
        }else{
            matches.get(0).setTime(System.currentTimeMillis());
            matches.get(0).updateAll("illust_id = ?", String.valueOf(illust.getIllust_id()));
            Log.d("ViewHistoryOperator", "refreshTime: found");

        }

    }

    public static void refreshFollowStatus(Illust illust){
//        Operator.updateAll(ViewHistory.class, new ViewHistory(illust), "illust_id = ?", illust.getIllust_id()+"");
        ViewHistory newViewHistory = new ViewHistory(illust);
        newViewHistory.setTime(System.currentTimeMillis());
        newViewHistory.updateAll("illust_id = ?", illust.getIllust_id()+"");
//        List<ViewHistory> matches = where("illust_id = ?",
//                String.valueOf(illust.getIllust_id())).find(ViewHistory.class);
//        if(matches.size()!=0) {
//            matches.get(0).setUser_isFollowed(illust.isUser_isFollowed());
//            matches.get(0).updateAll("illust_id = ?", String.valueOf(illust.getIllust_id()));
//            Log.d("ViewHistoryOperator", "found");
//
//        }else{
//            Log.d("ViewHistoryOperator", "notFound");
//        }
    }

    public static void refreshStarStatus(Illust illust){
        ViewHistory newViewHistory = new ViewHistory(illust);
        newViewHistory.setTime(System.currentTimeMillis());
        newViewHistory.updateAll("illust_id = ?", illust.getIllust_id()+"");
//        List<ViewHistory> matches = where("illust_id = ?",
//                String.valueOf(illust.getIllust_id())).find(ViewHistory.class);
//        if(matches.size()!=0){
//            matches.get(0).setBookmarked(illust.isBookmarked());
//            matches.get(0).updateAll("illust_id = ?", String.valueOf(illust.getIllust_id()));
//            Log.d("ViewHistoryOperator", "found");
//        }else{
//            Log.d("ViewHistoryOperator", "notFound");
//        }
    }
}
