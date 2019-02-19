package com.vwmin.min.sharedpreferencestest.utils;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.vwmin.min.sharedpreferencestest.response.Illust;
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



    public static GlideUrl getImgByIllust(Illust illust){
        if(illust.getMedium_url()!=null)
            return new GlideUrl(illust.getMedium_url(), HEADERS);
        else
            return new GlideUrl("https://github.com/LitePalFramework/LitePal/blob/master/sample/src/main/logo/mini_logo.png");
    }

    public static GlideUrl getImgByUrl(String imageUrl){
        if(imageUrl != null)
            return new GlideUrl(imageUrl, HEADERS);
        else
            return new GlideUrl("https://github.com/LitePalFramework/LitePal/blob/master/sample/src/main/logo/mini_logo.png");

    }



}
