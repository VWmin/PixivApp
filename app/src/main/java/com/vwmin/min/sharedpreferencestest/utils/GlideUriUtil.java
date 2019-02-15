package com.vwmin.min.sharedpreferencestest.utils;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.vwmin.min.sharedpreferencestest.response.IllustBean;


import java.util.HashMap;



/**
 * 为每次图片请求添加Referer
 */
public class GlideUriUtil {

    private static final String REFERER_KEY = "Referer";
    private static final String REFERER_VALUE = "https://app-api.pixiv.net/";

    private static Headers HEADERS = () -> {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(REFERER_KEY, REFERER_VALUE);
        return hashMap;
    };



    public static GlideUrl getImgByIllustBean(IllustBean illustBean){
        return new GlideUrl(illustBean.getImage_urls().getMedium(), HEADERS);
    }

    public static GlideUrl getImgByUrl(String imageUrl){
        return new GlideUrl(imageUrl, HEADERS);
    }



}
