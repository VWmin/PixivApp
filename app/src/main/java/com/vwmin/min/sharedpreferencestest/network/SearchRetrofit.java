package com.vwmin.min.sharedpreferencestest.network;

import com.vwmin.min.sharedpreferencestest.response.SearchResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class SearchRetrofit {
    private static final String APP_BASE_URL = "https://api.imjad.cn";
    private static final int DEFAULT_TIMEOUT = 5;



    private SearchApi searchApi;

    private SearchRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkhttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(APP_BASE_URL)
                .build();
        searchApi = retrofit.create(SearchApi.class);
    }

    // 访问Retro时创建单例
    private static class SingletonHolder{
        private static final SearchRetrofit INSTANCE = new SearchRetrofit();
    }

    // 获取单例
    public static SearchRetrofit getInstance(){
        return SearchRetrofit.SingletonHolder.INSTANCE;
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
        searchApi.getSearch("search", word, order, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    private interface SearchApi {
        @GET("/pixiv/v1")
        Observable<SearchResponse> getSearch(@Query("type") String type,
                @Query("word") String word,
                @Query("order") String order,
                @Query("page") String page
        );
    }



}


