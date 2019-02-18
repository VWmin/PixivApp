package com.vwmin.min.sharedpreferencestest.response;

import android.view.View;

import com.vwmin.min.sharedpreferencestest.data.ViewHistory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


// 将IllustsResponse转换成Illust集合, 或将IllustResponse转换成单个illust对象
public class Illust implements Serializable {
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


    public static List<Illust> parserIllustsResponse(IllustsResponse illustsResponse){
        List<Illust> tmpIllustList = new ArrayList<>();
        List<IllustBean> illustBeanList = illustsResponse.getIllusts();
        for(IllustBean illustBean:illustBeanList)
            tmpIllustList.add(parserIllustBean(illustBean));
        return tmpIllustList;

    }

    public static Illust parserIllustResponse(IllustResponse illustResponse){

        return parserIllustBean(illustResponse.getIllust());

    }

    private static Illust parserIllustBean(IllustBean illustBean){
        Illust tmpIllust = new Illust();
        tmpIllust.setIllust_id(illustBean.getId());
        tmpIllust.setMedium_url(illustBean.getImage_urls().getMedium());
        tmpIllust.setPage_count(illustBean.getPage_count());
        tmpIllust.setUser_name(illustBean.getUser().getName());
        tmpIllust.setUser_id(illustBean.getUser().getId());
        tmpIllust.setUser_profile(illustBean.getUser().getProfile_image_urls().getMedium());
        tmpIllust.setUser_isFollowed(illustBean.getUser().isIs_followed());
        tmpIllust.setTotal_viewed(illustBean.getTotal_view());
        tmpIllust.setTotal_bookmarks(illustBean.getTotal_bookmarks());
        tmpIllust.setTitle(illustBean.getTitle());
        tmpIllust.setCaption(illustBean.getCaption());
        tmpIllust.setWidth(illustBean.getWidth());
        tmpIllust.setHeight(illustBean.getHeight());
        tmpIllust.setBookmarked(illustBean.isIs_bookmarked());

        List<String> tmpMeta_pages = new ArrayList<>();
        if (illustBean.getPage_count()>1)
            for (int i = 0; i < illustBean.getMeta_pages().size(); i++)
                tmpMeta_pages.add(illustBean.getMeta_pages().get(i).getImage_urls().getOriginal());
        else
            tmpMeta_pages.add(illustBean.getMeta_single_page().getOriginal_image_url());


        tmpIllust.setMeta_pages(tmpMeta_pages);

        return tmpIllust;
    }

    public static Illust parserViewHistory(ViewHistory viewHistory){
        Illust tmpIllust = new Illust();
        tmpIllust.setIllust_id(viewHistory.getIllust_id());
        tmpIllust.setMedium_url(viewHistory.getMedium_url());
        tmpIllust.setPage_count(viewHistory.getPage_count());
        tmpIllust.setUser_name(viewHistory.getUser_name());
        tmpIllust.setUser_id(viewHistory.getUser_id());
        tmpIllust.setUser_profile(viewHistory.getUser_profile());
        tmpIllust.setUser_isFollowed(viewHistory.isUser_isFollowed());
        tmpIllust.setTotal_viewed(viewHistory.getTotal_viewed());
        tmpIllust.setTotal_bookmarks(viewHistory.getTotal_bookmarks());
        tmpIllust.setBookmarked(viewHistory.isBookmarked());
        tmpIllust.setTitle(viewHistory.getTitle());
        tmpIllust.setCaption(viewHistory.getCaption());
        tmpIllust.setWidth(viewHistory.getWidth());
        tmpIllust.setHeight(viewHistory.getHeight());
        tmpIllust.setMeta_pages(viewHistory.getMeta_pages());
        return tmpIllust;
    }

    public static List<Illust> parserSearchResponse(SearchResponse searchResponse){
        List<Illust> Illust = new ArrayList<>();
        for(SearchResponse.ResponseBean s:searchResponse.getResponse()){
//            if(s.getAge_limit().equals("r18")) continue;
            Illust tmp = new Illust();
            tmp.setIllust_id(s.getId());
            tmp.setMedium_url(s.getImage_urls().getPx_480mw().replace("480x960", "540x540_70"));
            tmp.setPage_count(s.getPage_count());
            tmp.setUser_name(s.getUser().getName());
            tmp.setUser_id(s.getUser().getId());
            tmp.setUser_profile(s.getUser().getProfile_image_urls().getPx_50x50());
            tmp.setUser_isFollowed(s.getUser().isIs_following());
            tmp.setTotal_viewed(s.getStats().getViews_count());
            tmp.setTotal_bookmarks(s.getStats().getFavorited_count().getPublicX());
            tmp.setBookmarked(s.isIs_liked());
            tmp.setTitle(s.getTitle());
            tmp.setCaption(s.getCaption());
            tmp.setWidth(s.getWidth());
            tmp.setHeight(s.getHeight());
            List<String> metaPages = new ArrayList<>();
            metaPages.add(s.getImage_urls().getLarge());
            tmp.setMeta_pages(metaPages);
            Illust.add(tmp);
        }
        return Illust;
    }

    public int getIllust_id() {
        return illust_id;
    }

    private void setIllust_id(int illust_id) {
        this.illust_id = illust_id;
    }

    public String getMedium_url() {
        return medium_url;
    }

    private void setMedium_url(String medium_url) {
        this.medium_url = medium_url;
    }

    public int getPage_count() {
        return page_count;
    }

    private void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public String getUser_name() {
        return user_name;
    }

    private void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_id() {
        return user_id;
    }

    private void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_profile() {
        return user_profile;
    }

    private void setUser_profile(String user_profile) {
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

    private void setTotal_viewed(int total_viewed) {
        this.total_viewed = total_viewed;
    }

    public int getTotal_bookmarks() {
        return total_bookmarks;
    }

    private void setTotal_bookmarks(int total_bookmarks) {
        this.total_bookmarks = total_bookmarks;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    private void setCaption(String caption) {
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

    private void setMeta_pages(List<String> meta_pages) {
        this.meta_pages = meta_pages;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }
}
