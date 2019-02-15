package com.vwmin.min.sharedpreferencestest.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.vwmin.min.sharedpreferencestest.ActivityCollection;
import com.vwmin.min.sharedpreferencestest.response.IllustBean;
import com.vwmin.min.sharedpreferencestest.response.IllustResponse;
import com.vwmin.min.sharedpreferencestest.response.IllustsResponse;
import com.vwmin.min.sharedpreferencestest.response.LoginResponse;
import com.vwmin.min.sharedpreferencestest.utils.AfterComplete;
import com.vwmin.min.sharedpreferencestest.data.UserInfo;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
                // 添加请求头
//                      .addInterceptor(chain -> {
//                             Request localRequest = chain.request().newBuilder()
//                            .addHeader(HEADER_NAME, HEADER_VALUE)
//                            .addHeader(HEADER_NAME2, HEADER_VALUE2)
//                            .build();
//                         return chain.proceed(localRequest);
//                      })
                .build();
    }


    /* TODO:以下为网络接口 */

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


    // 如果token过期则重拿token, 否则执行传入函数动作
    public void chkToken(Context context, AfterComplete afterComplete){
        if(System.currentTimeMillis() - UserInfo.getInstance(context).getPref().getLong("last_token_time", 0) >= ONE_HOUR){

            Observer<LoginResponse> observer = new Observer<LoginResponse>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Toast.makeText(context, "正在重新获取登录权限", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNext(LoginResponse loginResponse) {
                    // 重新保存
                    if (loginResponse != null && loginResponse.getResponse() != null) {
                        boolean isRemember = UserInfo.getInstance(context).getPref().getBoolean("is_remember", false);
                        String pwd = "";
                        if(isRemember){
                            pwd = UserInfo.getInstance(context).getPref().getString("password", "");
                        }
                        UserInfo.getInstance(context).saveUserInfo(loginResponse, pwd, isRemember);
                    }
                    else {
                        Toast.makeText(context, "网络似乎出了点问题，请重新登陆", Toast.LENGTH_LONG).show();
                        ActivityCollection.forceOffline();
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
                    afterComplete.GO();
                }
            };
            LoginRetrofit.getInstance().refreshToken(
                            observer,
                            LOGIN_PARAM_1,
                            LOGIN_PARAM_2,
                            LOGIN_PARAM_6,
                            UserInfo.getInstance(context).getPref().getString("refresh_token", ""),
                            UserInfo.getInstance(context).getPref().getString("device_token", ""),
                            LOGIN_PARAM_4);

        }
        else{
            afterComplete.GO();
        }

    }


}
