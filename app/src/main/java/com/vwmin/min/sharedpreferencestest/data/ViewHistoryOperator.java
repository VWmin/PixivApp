package com.vwmin.min.sharedpreferencestest.data;

import android.util.Log;

import com.vwmin.min.sharedpreferencestest.response.Illust;
import java.util.List;
import static org.litepal.Operator.where;

public class ViewHistoryOperator {

    public static void refreshClickTime(Illust illust){
        List<ViewHistory> matches = where("illust_id = ?",
                String.valueOf(illust.getIllust_id())).find(ViewHistory.class);
        if(matches.size()==0) {
            ViewHistory viewHistory = new ViewHistory(illust);
            viewHistory.save();
        }else{
            matches.get(0).setTime(System.currentTimeMillis());
            matches.get(0).updateAll("illust_id = ?", String.valueOf(illust.getIllust_id()));
        }

    }

    public static void refreshFollowStatus(int illustId, boolean status){
        List<ViewHistory> matches = where("illust_id = ?",
                String.valueOf(illustId)).find(ViewHistory.class);
        if(matches.size()!=0) {
            matches.get(0).setUser_isFollowed(status);
            matches.get(0).updateAll("illust_id = ?", String.valueOf(illustId));
            Log.d("ViewHistoryOperator", "found");

        }else{
            Log.d("ViewHistoryOperator", "notFound");
        }
    }

    public static void refreshStarStatus(int illustId, boolean status){
        List<ViewHistory> matches = where("illust_id = ?",
                String.valueOf(illustId)).find(ViewHistory.class);
        if(matches.size()!=0){
            matches.get(0).setBookmarked(status);
            matches.get(0).updateAll("illust_id = ?", String.valueOf(illustId));
            Log.d("ViewHistoryOperator", "found");
        }else{
            Log.d("ViewHistoryOperator", "notFound");
        }
    }
}
