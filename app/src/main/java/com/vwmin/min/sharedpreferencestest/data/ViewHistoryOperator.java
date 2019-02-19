package com.vwmin.min.sharedpreferencestest.data;

import android.util.Log;
import android.view.View;

import com.vwmin.min.sharedpreferencestest.response.Illust;

import org.litepal.Operator;

import java.util.List;

import static org.litepal.Operator.aesKey;
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
            matches.get(0).setTime();
            matches.get(0).updateAll("illust_id = ?", illust.getIllust_id()+"");
            Log.d("ViewHistoryOperator", "refreshTime: found");

        }

    }

    public static void refreshFollowStatus(Illust illust){
        List<ViewHistory> matches = where("illust_id = ?",
                illust.getIllust_id()+"").find(ViewHistory.class);
        if(matches.size()!=0){
            long time = matches.get(0).getTime();
            ViewHistory viewHistory = new ViewHistory();
            viewHistory.init(illust);
            viewHistory.setTime(time);
            matches.get(0).delete();
            viewHistory.save();
        }
    }

    public static void refreshStarStatus(Illust illust){
        // 因为一直莫名其妙地无法更新本地数据，最后选择了删掉原有的再重新写入。。。
//        ViewHistory newViewHistory = new ViewHistory(illust);
//        Log.d("refreshStarStatus", "newViewHistory_star: "+newViewHistory.isBookmarked());
//        newViewHistory.updateAll("illust_id = ?", illust.getIllust_id()+"");

        List<ViewHistory> matches = where("illust_id = ?",
                illust.getIllust_id()+"").find(ViewHistory.class);
        if(matches.size()!=0){
            long time = matches.get(0).getTime();
            ViewHistory viewHistory = new ViewHistory();
            viewHistory.init(illust);
            viewHistory.setTime(time);
            matches.get(0).delete();
            viewHistory.save();
        }

//        ViewHistory viewHistory = new ViewHistory();
//        viewHistory.setBookmarked(illust.isBookmarked());
//        viewHistory.updateAll("illust_id =?", illust.getIllust_id()+"");
        Log.d("refreshStarStatus", "newViewHistory_star: "+illust.isBookmarked());



        List<ViewHistory> matches2 = where("illust_id = ?",
                illust.getIllust_id()+"").find(ViewHistory.class);
        if(matches2.size()!=0) {
            Log.d("refreshStarStatus", "afterUpdate: " + matches2.get(0).isBookmarked()
                    + "; size: " + matches2.size());
        }
    }
}
