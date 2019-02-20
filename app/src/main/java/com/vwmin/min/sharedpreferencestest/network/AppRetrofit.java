package com.vwmin.min.sharedpreferencestest.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.vwmin.min.sharedpreferencestest.activity.ActivityCollection;
import com.vwmin.min.sharedpreferencestest.response.IllustResponse;
import com.vwmin.min.sharedpreferencestest.response.IllustsResponse;
import com.vwmin.min.sharedpreferencestest.response.LoginResponse;
import com.vwmin.min.sharedpreferencestest.response.NullResponse;
import com.vwmin.min.sharedpreferencestest.response.TrendTagsResponse;
import com.vwmin.min.sharedpreferencestest.utils.AfterComplete;
import com.vwmin.min.sharedpreferencestest.data.UserInfo;


import java.util.List;
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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public class AppRetrofit {

    private static final String APP_BASE_URL = "https://app-api.pixiv.net";
    private static final long ONE_HOUR = 3500 * 1000;
    private static final int DEFAULT_TIMEOUT = 5;
    private static final String LOGIN_PARAM_1 = "MOBrBDS8blbauoSck0ZfDbtuzpyT";
    private static final String LOGIN_PARAM_2 = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj";
    private static final String LOGIN_PARAM_3 = "pixiv";
    private static final boolean LOGIN_PARAM_4 = true;
    private static final String LOGIN_PARAM_5 = "password";
    private static final String LOGIN_PARAM_6 = "refresh_token";


    private AppApi appApi;

    private AppRetrofit(){
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
        private static final AppRetrofit INSTANCE = new AppRetrofit();
    }

    // 获取单例
    public static AppRetrofit getInstance(){
        return SingletonHolder.INSTANCE;
    }


    private static OkHttpClient getOkhttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)) // 请求拦截器 这里只用来打印request和response
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }


    /* 以下为网络接口 */

    // 获取登陆界面的背景图
    public void getLoginBg(Observer<IllustsResponse> observer){
        appApi.getLoginBg()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 获取 为你推荐
    public void getRecommend(Observer<IllustsResponse> observer,
                             String authorization,
                             String filter,
                             boolean include_ranking_illusts){
        appApi.getRecommendIllust(authorization, filter, include_ranking_illusts)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 获取下拉界面时的下一批推荐
    public void getNext(Observer<IllustsResponse> observer,
                        String authorization,
                        String nextUrl){
        appApi.getNext(authorization, nextUrl)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    // 按照mode获取排行榜
    public void getRank(Observer<IllustsResponse> observer,
                        String authorization,
                        String filter,
                        String mode){
        appApi.getRank(authorization, filter, mode)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 按照id获取一张图
    public void getSingleIllust(Observer<IllustResponse> observer,
                                String authorization,
                                String filter,
                                int illust_id){
        appApi.getSingleIllust(authorization, filter, illust_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 收藏图片
    public void starIllust(Observer<NullResponse> observer,
                           String authorization,
                           String id,
                           String restrict,
                           List<String> tags){
        appApi.starIllust(authorization, id, restrict, tags)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

     // 取消收藏
    public void unStarIllust(Observer<NullResponse> observer,
                             String authorization,
                             String id){
        appApi.unStarIllust(authorization, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 关注用户
    public void followUser(Observer<NullResponse> observer,
                           String authorization,
                           String userId,
                           String restrict){
        appApi.followUser(authorization, userId, restrict)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 取消关注
    public void unFollowUser(Observer<NullResponse> observer,
                           String authorization,
                           String userId){
        appApi.unFollowUser(authorization, userId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // 获取热门标签
    public void getTrendTags(Observer<TrendTagsResponse> observer,
                             String authorization,
                             String filter){
        appApi.getTrendTags(authorization, filter)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    // 如果token过期则重拿token, 否则执行传入函数动作
    public void chkToken(Context context, AfterComplete afterComplete){
        if(System.currentTimeMillis() - UserInfo.getInstance(context).getLastTokenTime() >= ONE_HOUR){

            Observer<LoginResponse> observer = new Observer<LoginResponse>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Toast.makeText(context, "正在重新获取登录权限", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNext(LoginResponse loginResponse) {
                    // 重新保存
                    if (loginResponse != null && loginResponse.getResponse() != null) {
                        boolean isRemember = UserInfo.getInstance(context).isRemember();
                        String pwd = "";
                        if(isRemember){
                            pwd = UserInfo.getInstance(context).getPassword();
                        }
                        UserInfo.getInstance(context).saveUserInfo(loginResponse, pwd, isRemember);
                    }
                    else {
                        Toast.makeText(context, "网络似乎出了点问题，请重新登陆", Toast.LENGTH_LONG).show();
//                        ActivityCollection.forceOffline();
                        ActivityCollection.finishAll();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Log.d("yyy", "error: "+e.getMessage());
                    Toast.makeText(context,
                            "连接失败，网络状态呀，账号密码啊错了（别问我为什么不知道具体情况，问就杀了P站做服务器的）",
                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void onComplete() {
                    afterComplete.onSuccess();
                }
            };
            LoginRetrofit.getInstance().refreshToken(
                            observer,
                            LOGIN_PARAM_1,
                            LOGIN_PARAM_2,
                            LOGIN_PARAM_6,
                            UserInfo.getInstance(context).getRefreshToken(),
                            UserInfo.getInstance(context).getDeviceToken(),
                            LOGIN_PARAM_4);

        }
        else{
            afterComplete.onSuccess();
        }

    }

    private interface AppApi {

        /**
         * 登陆界面滚动背景图
         *
         * @return
         */
        @GET("/v1/walkthrough/illusts")
        Observable<IllustsResponse> getLoginBg();


        /**
         * 推荐榜单
         *
         * @param authorization
         * @param filter
         * @param include_ranking_illusts
         * @return
         */
        @GET("/v1/illust/recommended")
        Observable<IllustsResponse> getRecommendIllust(@Header("Authorization") String authorization,
                                                       @Query("filter") String filter,
                                                       @Query("include_ranking_illusts") boolean include_ranking_illusts);
        /**
         * 下拉界面时抓取下一批推荐
         * @param authorization
         * @param nextUrl
         * @return
         *
         * */

        @GET
        Observable<IllustsResponse> getNext(@Header("Authorization") String authorization,
                                            @Url String nextUrl);


        /**
         * 排行榜
         *
         * @param authorization
         * @param filter
         * @param mode 可选有"day", "week", "month", "day_male", "day_female", "week_original", "week_rookie"
         * @return
         */
        @GET("/v1/illust/ranking")
        Observable<IllustsResponse> getRank(@Header("Authorization") String authorization,
                                            @Query("filter") String filter,
                                            @Query("mode") String mode);

        /**
         * 获取一张单图
         * */
        @GET("/v1/illust/detail")
        Observable<IllustResponse> getSingleIllust(@Header("Authorization") String authorization,
                                                   @Query("filter") String filter,
                                                   @Query("illust_id") int illust_id);

        /**
         * 收藏
         *
         * */
        @FormUrlEncoded
        @POST("/v2/illust/bookmark/add")
        Observable<NullResponse> starIllust(@Header("Authorization") String authorization,
                                      @Field("illust_id") String id,
                                      @Field("restrict") String restrict,
                                      @Field("tags[]") List<String> tags);

        /**
         * 取消收藏
         *
         * */
        @FormUrlEncoded
        @POST("/v1/illust/bookmark/delete")
        Observable<NullResponse> unStarIllust(@Header("Authorization") String authorization,
                                        @Field("illust_id") String id);


        /**
         * 关注
         *
         */
        @FormUrlEncoded
        @POST("/v1/user/follow/add")
        Observable<NullResponse> followUser(@Header("Authorization") String authorization,
                                            @Field("user_id") String userId,
                                            @Field("restrict") String restrict);


        /**
         * 取消关注
         *
         */
        @FormUrlEncoded
        @POST("/v1/user/follow/delete")
        Observable<NullResponse> unFollowUser(@Header("Authorization") String authorization,
                                              @Field("user_id") String userId);


        /**
         * 获取热门标签
         *
         */
        @GET("/v1/trending-tags/illust")
        Observable<TrendTagsResponse> getTrendTags(@Header("Authorization") String authorization,
                                                   @Query("filter") String filter);

//        /**
//         * 按标签搜索
//         *
//         */
//        @GET("/v1/search/illust")
//        Observable<>


    }


}
