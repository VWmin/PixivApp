package com.vwmin.min.sharedpreferencestest.data;



import com.vwmin.min.sharedpreferencestest.response.Illust;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;


import java.util.List;


// 一个实例对应一条记录
public class ViewHistory extends Illust implements Comparable<ViewHistory> {

    private long time;

    public ViewHistory(Illust illust){
        setIllust_id(illust.getIllust_id());
        setMedium_url(illust.getMedium_url());
        setPage_count(illust.getPage_count());
        setUser_name(illust.getUser_name());
        setUser_id(illust.getUser_id());
        setUser_profile(illust.getUser_profile());
        setUser_isFollowed(illust.isUser_isFollowed());
        setTotal_viewed(illust.getTotal_viewed());
        setTotal_bookmarks(illust.getTotal_bookmarks());
        setBookmarked(illust.isBookmarked());
        setTitle(illust.getTitle());
        setCaption(illust.getCaption());
        setWidth(illust.getWidth());
        setHeight(illust.getHeight());
        setMeta_pages(illust.getMeta_pages());
        setTime();
    }

    public ViewHistory(){}

    public long getTime() {
        return time;
    }

    public void setTime() {
        this.time = System.currentTimeMillis();
    }

    @Override
    public int compareTo(ViewHistory other){
        return Long.compare(other.time, this.time);
    }

}
