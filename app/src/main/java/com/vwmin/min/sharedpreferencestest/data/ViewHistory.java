package com.vwmin.min.sharedpreferencestest.data;



import com.vwmin.min.sharedpreferencestest.response.Illust;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;


import java.util.List;


// 一个实例对应一条记录
public class ViewHistory extends LitePalSupport implements Comparable<ViewHistory> {

    private long id;
    private long time;
    @Column(unique = true)
    private int illust_id;
    private String medium_url;
    private int page_count;
    private String user_name;
    private int user_id;
    private String user_profile;
    private boolean user_isFollowed;
    private int total_viewed;
    private int total_bookmarks;
    private boolean bookmarked;
    private String title;
    private String caption;
    private int width;
    private int height;
    private List<String> meta_pages;

    @Column(ignore = true)
    private Illust illust;

    public ViewHistory(Illust illust){
        illust_id = illust.getIllust_id();
        time = System.currentTimeMillis();
        medium_url = illust.getMedium_url();
        page_count = illust.getPage_count();
        user_name = illust.getUser_name();
        user_id = illust.getUser_id();
        user_isFollowed = illust.isUser_isFollowed();
        user_profile = illust.getUser_profile();
        total_viewed = illust.getTotal_viewed();
        total_bookmarks = illust.getTotal_bookmarks();
        title = illust.getTitle();
        caption = illust.getCaption();
        meta_pages = illust.getMeta_pages();
        time = System.currentTimeMillis();

    }

    public ViewHistory(){}

    public Illust getIllust() {
        return illust;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIllust_id() {
        return illust_id;
    }

    private void setIllust_id(int illust_id) {
        this.illust_id = illust_id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMedium_url() {
        return medium_url;
    }

    public void setMedium_url(String medium_url) {
        this.medium_url = medium_url;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_cnt) {
        this.page_count = page_cnt;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_profile() {
        return user_profile;
    }

    public void setUser_profile(String user_profile) {
        this.user_profile = user_profile;
    }

    public boolean isUser_isFollowed() {
        return user_isFollowed;
    }

    public void setUser_isFollowed(boolean user_isFollowed) {
        this.user_isFollowed = user_isFollowed;
    }

    public int getTotal_viewed() {
        return total_viewed;
    }

    public void setTotal_viewed(int total_viewed) {
        this.total_viewed = total_viewed;
    }

    public int getTotal_bookmarks() {
        return total_bookmarks;
    }

    public void setTotal_bookmarks(int total_bookmarks) {
        this.total_bookmarks = total_bookmarks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<String> getMeta_pages() {
        return meta_pages;
    }

    public void setMeta_pages(List<String> meta_pages) {
        this.meta_pages = meta_pages;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    @Override
    public int compareTo(ViewHistory other){
        return Long.compare(other.time, this.time);
    }

}
