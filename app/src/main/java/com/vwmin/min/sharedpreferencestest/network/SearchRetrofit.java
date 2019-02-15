package com.vwmin.min.sharedpreferencestest.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.vwmin.min.sharedpreferencestest.ActivityCollection;
import com.vwmin.min.sharedpreferencestest.data.UserInfo;
import com.vwmin.min.sharedpreferencestest.response.IllustResponse;
import com.vwmin.min.sharedpreferencestest.response.IllustsResponse;
import com.vwmin.min.sharedpreferencestest.response.LoginResponse;
import com.vwmin.min.sharedpreferencestest.response.SearchResponse;
import com.vwmin.min.sharedpreferencestest.utils.AfterComplete;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class tmpRetrofit {
    private static final String APP_BASE_URL = "https://api.imjad.cn";
    private static final int DEFAULT_TIMEOUT = 5;



    private AppApi appApi;

    private tmpRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkhttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(APP_BASE_URL)
                .build();
        appApi = retrofit.create(AppApi.class);
    }

    // 访问Retro时创建单例
    private static class SingletonHolder{
        private static final tmpRetrofit INSTANCE = new tmpRetrofit();
    }

    // 获取单例
    public static tmpRetrofit getInstance(){
        return tmpRetrofit.SingletonHolder.INSTANCE;
    }


    private static OkHttpClient getOkhttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)) // 请求拦截器 这里只用来打印request和response
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }


    // 大概是搜索把
    public void getSearchAsTag(Observer<SearchResponse> observer,
                          String word, String order, String page){
        appApi.getSearch("search", word, order, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    private interface AppApi{
        @GET("/pixiv/v1")
        Observable<SearchResponse> getSearch(@Query("type") String type,
                @Query("word") String word,
                @Query("order") String order,
                @Query("page") String page
        );
    }



}


